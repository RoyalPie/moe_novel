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

import java.util.Date;
import java.util.UUID;

@Document(indexName = "user-document")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDocument {
    @Id
    @Field(type = FieldType.Keyword)
    private UUID userId;
    @Field(type = FieldType.Keyword)
    private UUID keycloakUserId;
    @MultiField(mainField = @Field(type = FieldType.Text),
            otherFields = {
                    @InnerField(suffix = "sort", type = FieldType.Keyword)
            })
    private String username;
    @Field(type = FieldType.Text)
    private String email;
    @Field(type = FieldType.Keyword)
    private String firstName;
    @Field(type = FieldType.Keyword)
    private String lastName;
    @Field(type = FieldType.Keyword)
    private UUID avatarFileId;
    @Field(type = FieldType.Date)
    private Date dateOfBirth;
    @Field(type = FieldType.Keyword)
    private String phoneNumber;
    @Field(type = FieldType.Keyword)
    private boolean active;
    @Field(type = FieldType.Keyword)
    private boolean deleted;

}
