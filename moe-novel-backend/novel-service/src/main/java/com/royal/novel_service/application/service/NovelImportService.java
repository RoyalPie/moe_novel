package com.royal.novel_service.application.service;

import com.evo.common.dto.response.FileResponse;
import com.evo.common.webapp.support.IdUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.royal.novel_service.domain.Chapter;
import com.royal.novel_service.domain.Genre;
import com.royal.novel_service.domain.Novel;
import com.royal.novel_service.domain.NovelChapter;
import com.royal.novel_service.domain.NovelGenre;
import com.royal.novel_service.domain.command.CreateNovelChapterCmd;
import com.royal.novel_service.domain.command.CreateNovelGenreCmd;
import com.royal.novel_service.domain.command.CreateOrUpdateChapterCmd;
import com.royal.novel_service.domain.command.CreateOrUpdateGenreCmd;
import com.royal.novel_service.domain.repository.ChapterDomainRepository;
import com.royal.novel_service.domain.repository.GenreDomainRepository;
import com.royal.novel_service.domain.repository.NovelDomainRepository;
import com.royal.novel_service.infrastructure.adapter.storage.FileService;
import com.royal.novel_service.infrastructure.support.enums.NovelStatus;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NovelImportService {
    private final NovelDomainRepository novelRepository;
    private final ChapterDomainRepository chapterRepository;
    private final GenreDomainRepository genreRepository;
    private final ObjectMapper objectMapper;
    private final FileService fileService;

    @Transactional
    public void importNovelsFromFolder(String folderPath) {
        File novelsDir = new File(folderPath);
        if (!novelsDir.exists() || !novelsDir.isDirectory()) {
            System.err.println("Invalid directory: " + folderPath);
            return;
        }
        File[] novelFolders = novelsDir.listFiles(File::isDirectory);
        if (novelFolders == null || novelFolders.length == 0) {
            System.err.println("No novel folders found in directory: " + folderPath);
            return;
        }
        List<String> savedNovel = novelRepository.getAllName()
                .stream()
                .map(n -> n.replaceAll("[<>:\"/\\\\|?*\\x00-\\x1F]", "").trim().toLowerCase())
                .collect(Collectors.toList());
        for (File novelFolder : novelFolders) {
            try {
                String folderName = novelFolder.getName().trim().toLowerCase();
                if (savedNovel.contains(folderName)) {
                    updateNovelFromFolder(novelFolder);
                }
                else importNovelFromFolder(novelFolder);
            } catch (Exception e) {
                System.err.println("Failed to import novel from folder " + novelFolder.getName() + ": " + e.getMessage());
                // Continue with the next folder to avoid failing the entire import
            }
        }
    }

    private void updateNovelFromFolder(File novelFolder) {
        List<Chapter> chapters = new ArrayList<>();
        File[] htmlFiles = novelFolder.listFiles(f -> f.getName().endsWith(".html"));
        Novel novel = novelRepository.getByNovelName(novelFolder.getName());
        int oldChapters = novel.getTotalChapters();

        if (htmlFiles == null || htmlFiles.length == 0 || oldChapters >= htmlFiles.length) {
            System.err.println("No HTML files found in folder: " + novelFolder.getName());
            return;
        }

        for (File htmlFile : htmlFiles) {
            try {
                Document doc = Jsoup.parse(htmlFile, "UTF-8");

                Element h3 = doc.selectFirst("h3");
                String chapterTitle;
                if (h3 != null && !h3.text().isBlank()) {
                    chapterTitle = h3.text();
                } else {
                    // Fallback: derive title from filename
                    String filename = htmlFile.getName();
                    // Strip extension
                    String base = filename.lastIndexOf('.') > 0
                            ? filename.substring(0, filename.lastIndexOf('.'))
                            : filename;
                    // Optionally remove leading numbers/underscores, e.g. "001_Prologue"
                    // and replace underscores with spaces:
                    base = base.replaceAll("^\\d+_", "")
                            .replace('_', ' ');
                    chapterTitle = base;
                }

                Integer chapterNumber = extractChapterNumber(htmlFile.getName());
                if (chapterNumber == null  || chapterNumber <= oldChapters) {
                    System.err.println("Skipping file " + htmlFile.getName() + ": Invalid chapter number");
                    continue;
                }

                String content = doc.body().html();
                CreateOrUpdateChapterCmd chapterCmd = new CreateOrUpdateChapterCmd(
                        IdUtils.newUUID(),
                        chapterTitle,
                        chapterNumber,
                        content
                );
                Chapter chapter = new Chapter(chapterCmd);
                chapters.add(chapter);
            } catch (Exception e) {
                System.err.println("Error processing " + htmlFile.getName() + ": " + e.getMessage());
            }
        }


        if (chapters.isEmpty()) {
            System.err.println("No valid chapters found for novel: " + novelFolder.getName());
            return;
        }

        try {
            chapterRepository.saveAll(chapters);
        } catch (Exception e) {
            System.err.println("Failed to save chapters for novel " + novelFolder.getName() + ": " + e.getMessage());
            throw new RuntimeException("Chapter persistence failed", e);
        }

        // Create NovelChapter entries to link Novel and Chapters

        UUID novelId = novel.getNovelId();

        List<NovelChapter> novelChapters = novel.getNovelChapters();
        for (Chapter chapter : chapters) {
            if (chapter.getChapterId() == null) {
                chapter.setChapterId(IdUtils.newUUID());
            }
            CreateNovelChapterCmd createNovelChapterCmd = new CreateNovelChapterCmd(novelId, chapter.getChapterId());
            NovelChapter novelChapter = new NovelChapter(createNovelChapterCmd);
            novelChapters.add(novelChapter);
        }

        if (novelChapters.isEmpty()) {
            System.err.println("No valid novel chapters created for novel: " + novelFolder.getName());
            return;
        }
        novel.setNovelChapters(novelChapters);
        novel.setTotalChapters(novelChapters.size());
        try {
            novelRepository.save(novel);
            System.out.println("Successfully updated novel: " + novel.getTitle());
        } catch (Exception e) {
            System.err.println("Failed to save novel " + novel.getTitle() + ": " + e.getMessage());
            throw new RuntimeException("Novel persistence failed", e);
        }
    }

    @Transactional
    private void importNovelFromFolder(File novelFolder) throws IOException {
        File metaFile = new File(novelFolder, "metadata.json");
        if (!metaFile.exists()) {
            throw new IllegalStateException("metadata.json not found in " + novelFolder.getName());
        }
        Map<String, String> meta = objectMapper.readValue(
                Files.readString(metaFile.toPath()),
                new TypeReference<Map<String, String>>() {
                }
        );
        String title = meta.getOrDefault("title", novelFolder.getName());
        String authorName = meta.getOrDefault("creator", "Unknown");
        String subjectRaw = meta.getOrDefault("subject", "");
        String descriptionRaw = meta.getOrDefault("description", "");

        String description = descriptionRaw.replace("/", "\n");
        // 3) Build genre commands
        List<CreateNovelGenreCmd> genreCmds = new ArrayList<>();
        if (!subjectRaw.isBlank()) {
            for (String raw : subjectRaw.split(",")) {
                String name = raw.trim();
                if (name.isEmpty()) continue;

                // Try find existing
                Genre found = genreRepository.findByName(name);
                UUID genreId;
                if (found == null) {
                    // create new genre
                    CreateOrUpdateGenreCmd createGenre = new CreateOrUpdateGenreCmd(null, name);
                    Genre genre = new Genre(createGenre);
                    genreRepository.save(genre);
                    genreId = genre.getGenreId();
                } else {
                    genreId = found.getGenreId();
                }

                // Build the link cmd
                CreateNovelGenreCmd ngc = new CreateNovelGenreCmd(null, genreId);
                genreCmds.add(ngc);
            }
        }
        // Collect and save chapters
        List<Chapter> chapters = new ArrayList<>();
        File[] htmlFiles = novelFolder.listFiles(f -> f.getName().endsWith(".html"));
        if (htmlFiles == null || htmlFiles.length == 0) {
            System.err.println("No HTML files found in folder: " + novelFolder.getName());
            return;
        }

        for (File htmlFile : htmlFiles) {
            try {
                Document doc = Jsoup.parse(htmlFile, "UTF-8");

                Element h3 = doc.selectFirst("h3");
                String chapterTitle;
                if (h3 != null && !h3.text().isBlank()) {
                    chapterTitle = h3.text();
                } else {
                    // Fallback: derive title from filename
                    String filename = htmlFile.getName();
                    // Strip extension
                    String base = filename.lastIndexOf('.') > 0
                            ? filename.substring(0, filename.lastIndexOf('.'))
                            : filename;
                    // Optionally remove leading numbers/underscores, e.g. "001_Prologue"
                    // and replace underscores with spaces:
                    base = base.replaceAll("^\\d+_", "")
                            .replace('_', ' ');
                    chapterTitle = base;
                }

                Integer chapterNumber = extractChapterNumber(htmlFile.getName());
                if (chapterNumber == null) {
                    System.err.println("Skipping file " + htmlFile.getName() + ": Invalid chapter number");
                    continue;
                }

                String content = doc.body().html();
                CreateOrUpdateChapterCmd chapterCmd = new CreateOrUpdateChapterCmd(
                        IdUtils.newUUID(),
                        chapterTitle,
                        chapterNumber,
                        content
                );
                Chapter chapter = new Chapter(chapterCmd);
                chapters.add(chapter);
            } catch (Exception e) {
                System.err.println("Error processing " + htmlFile.getName() + ": " + e.getMessage());
            }
        }


        if (chapters.isEmpty()) {
            System.err.println("No valid chapters found for novel: " + novelFolder.getName());
            return;
        }

        try {
            chapterRepository.saveAll(chapters);
        } catch (Exception e) {
            System.err.println("Failed to save chapters for novel " + novelFolder.getName() + ": " + e.getMessage());
            throw new RuntimeException("Chapter persistence failed", e);
        }

        // Create NovelChapter entries to link Novel and Chapters
        List<NovelChapter> novelChapters = new ArrayList<>();
        for (Chapter chapter : chapters) {
            if (chapter.getChapterId() == null) {
                chapter.setChapterId(IdUtils.newUUID());
            }
            CreateNovelChapterCmd createNovelChapterCmd = new CreateNovelChapterCmd(null, chapter.getChapterId());
            NovelChapter novelChapter = new NovelChapter(createNovelChapterCmd);
            novelChapters.add(novelChapter);
        }

        if (novelChapters.isEmpty()) {
            System.err.println("No valid novel chapters created for novel: " + novelFolder.getName());
            return;
        }

        List<NovelGenre> novelGenres = new ArrayList<>();
        genreCmds.forEach(genreCmd -> {
            NovelGenre novelGenre = new NovelGenre(genreCmd);
            novelGenres.add(novelGenre);
        });
        FileResponse fileResponse = uploadCoverFile(novelFolder);
        Novel novel = Novel.builder()
                .novelId(IdUtils.newUUID())
                .title(novelFolder.getName())
                .authorName(authorName)
                .description(description)
                .status(NovelStatus.ON_GOING)
                .totalChapters(novelChapters.size())
                .totalViews(0)
                .totalFollows(0)
                .coverImage(fileResponse.getId())
                .deleted(false)
                .novelGenres(novelGenres)
                .novelChapters(novelChapters)
                .build();

        UUID novelId = novel.getNovelId();
        novelChapters.forEach(novelChapter -> novelChapter.setNovelId(novelId));
        novelGenres.forEach(novelGenre -> novelGenre.setNovelId(novelId));

        try {
            novelRepository.save(novel);
            System.out.println("Successfully imported novel: " + novel.getTitle());
        } catch (Exception e) {
            System.err.println("Failed to save novel " + novel.getTitle() + ": " + e.getMessage());
            throw new RuntimeException("Novel persistence failed", e);
        }
    }

    private Integer extractChapterNumber(String filename) {
        // Pattern to match all digits before the first underscore
        Pattern pattern = Pattern.compile("^(\\d+)_");
        Matcher matcher = pattern.matcher(filename);
        if (matcher.find()) {
            try {
                return Integer.parseInt(matcher.group(1));
            } catch (NumberFormatException e) {
                System.err.println("Invalid chapter number in filename " + filename + ": " + e.getMessage());
            }
        }
        return null;
    }

    private FileResponse uploadCoverFile(File novelFolder) {
        File[] covers = novelFolder.listFiles((d, name) -> name.toLowerCase().startsWith("cover."));
        if (covers == null || covers.length == 0) {
            return null;
        }
        File cover = covers[0];
        try {
            byte[] bytes = Files.readAllBytes(cover.toPath());
            String contentType = Files.probeContentType(cover.toPath());
            MultipartFile multipart = new MockMultipartFile(
                    "files",
                    cover.getName(),
                    contentType,
                    bytes
            );
            // calls your existing upload API
            List<FileResponse> responses = fileService.uploadFile(List.of(multipart));
            if (!responses.isEmpty()) {
                return responses.get(0);
            }
        } catch (IOException e) {
            System.err.println("Failed to read cover image: " + e.getMessage());
        }
        return null;
    }


}