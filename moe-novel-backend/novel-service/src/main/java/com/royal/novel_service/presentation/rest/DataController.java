package com.royal.novel_service.presentation.rest;

import com.royal.novel_service.application.service.NovelImportService;
import com.royal.novel_service.application.service.NovelMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/data")
public class DataController {
    private final NovelImportService novelImportService;
    private final NovelMessageService novelMessageService;

    @PostMapping("/import")
    public ResponseEntity<?> importNovels() {
        novelImportService.importNovelsFromFolder("I:\\Code\\WebDesign\\novel-data\\NovelScraper-Library\\chapters");
        return ResponseEntity.ok("Good");
    }

    @PostMapping("/sync")
    public ResponseEntity<?> syncNovels() {
        novelMessageService.syncNovel();
        return ResponseEntity.ok("Done");
    }
}
