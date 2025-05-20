package com.royal.novel_service.presentation.rest;

import com.evo.common.dto.response.ApiResponses;
import com.royal.novel_service.application.dto.response.NovelDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class NovelController {
//    private final
//    @GetMapping("/novel/load-novels")
//    public ApiResponses<NovelDTO> loadNovels(){
//        NovelDTO novelDTO =
//        return ApiResponses.<NovelDTO>builder()
//                .data(novelDTO)
//                .success(true)
//                .code(200)
//                .message("Get novels successfully")
//                .timestamp(System.currentTimeMillis())
//                .status("OK")
//                .build();
//    }
}
