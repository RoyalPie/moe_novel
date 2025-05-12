package com.royal.novel_service.application.service;

import com.evo.common.webapp.support.IdUtils;
import com.royal.novel_service.domain.Chapter;
import com.royal.novel_service.domain.Novel;
import com.royal.novel_service.domain.NovelChapter;
import com.royal.novel_service.domain.command.CreateNovelChapterCmd;
import com.royal.novel_service.domain.command.CreateOrUpdateChapterCmd;
import com.royal.novel_service.domain.repository.ChapterDomainRepository;
import com.royal.novel_service.domain.repository.NovelDomainRepository;
import com.royal.novel_service.infrastructure.support.enums.NovelStatus;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class NovelImportService {
    private final NovelDomainRepository novelRepository;
    private final ChapterDomainRepository chapterRepository;

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
        List<String> savedNovel = novelRepository.getAllName();
        for (File novelFolder : novelFolders) {
            try {
                if (savedNovel.contains(novelFolder.getName())){
                    continue;
                }
                importNovelFromFolder(novelFolder);
            } catch (Exception e) {
                System.err.println("Failed to import novel from folder " + novelFolder.getName() + ": " + e.getMessage());
                // Continue with the next folder to avoid failing the entire import
            }
        }
    }

    @Transactional
    private void importNovelFromFolder(File novelFolder) {
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

                // Try to find <h3> first…
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

                // Extract chapter number as before
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

        // Save chapters to get their IDs
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

        // Create and save Novel
        Novel novel = Novel.builder()
                .novelId(IdUtils.newUUID())
                .title(novelFolder.getName())
                .authorName("Unknown")
                .description("")
                .status(NovelStatus.ON_GOING)
                .totalViews(0)
                .totalFollows(0)
                .deleted(false)
                .novelChapters(novelChapters)
                .build();

        // Set novelId in NovelChapter entries
        UUID novelId = novel.getNovelId();
        novelChapters.forEach(novelChapter -> novelChapter.setNovelId(novelId));

        // Save novel
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
        return null; // Return null if no valid chapter number is found
    }
}