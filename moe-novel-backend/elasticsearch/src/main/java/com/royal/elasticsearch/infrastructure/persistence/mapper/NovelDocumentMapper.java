package com.royal.elasticsearch.infrastructure.persistence.mapper;

import com.royal.elasticsearch.domain.Novel;
import com.royal.elasticsearch.infrastructure.persistence.document.NovelDocument;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NovelDocumentMapper extends DocumentMapper<Novel, NovelDocument>{

}
