package com.royal.elasticsearch.infrastructure.persistence.mapper;

import com.royal.elasticsearch.domain.User;
import com.royal.elasticsearch.infrastructure.persistence.document.UserDocument;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserDocumentMapper extends DocumentMapper<User, UserDocument> {}