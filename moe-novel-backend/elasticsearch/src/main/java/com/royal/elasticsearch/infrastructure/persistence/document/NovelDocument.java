package com.royal.elasticsearch.infrastructure.persistence.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.InnerField;
import org.springframework.data.elasticsearch.annotations.MultiField;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Document(indexName = "novel-document")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NovelDocument {
    @Id
    @Field(type = FieldType.Keyword)
    private UUID novelId;

    @MultiField(mainField = @Field(type = FieldType.Text),
            otherFields = {
                    @InnerField(suffix = "sort", type = FieldType.Keyword)
            })
    private String title;

    @Field(type = FieldType.Text)
    private UUID coverImage;

    @Field(type = FieldType.Integer)
    private int totalChapters;

    @Field(type = FieldType.Integer)
    private int totalViews;

    @Field(type = FieldType.Date)
    private Instant updatedAt;

    @Field(type = FieldType.Keyword)
    private List<String> novelGenres;

    @Field(type = FieldType.Keyword)
    private List<String> novelTags;
}
