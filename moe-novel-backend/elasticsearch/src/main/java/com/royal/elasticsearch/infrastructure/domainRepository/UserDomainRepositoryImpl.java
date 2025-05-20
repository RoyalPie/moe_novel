package com.royal.elasticsearch.infrastructure.domainRepository;

import com.royal.elasticsearch.domain.User;
import com.royal.elasticsearch.domain.repository.UserDomainRepository;
import com.royal.elasticsearch.infrastructure.persistence.document.UserDocument;
import com.royal.elasticsearch.infrastructure.persistence.mapper.UserDocumentMapper;
import com.royal.elasticsearch.infrastructure.persistence.repository.UserDocumentRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class UserDomainRepositoryImpl extends AbstractDocumentRepository<User, UserDocument, UUID>
        implements UserDomainRepository {
    private final UserDocumentMapper mapper;
    private final UserDocumentRepository repository;
    public UserDomainRepositoryImpl(UserDocumentMapper mapper, UserDocumentRepository repository) {
        super(repository, mapper);
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public List<User> saveAll(List<User> domains) {
        List<UserDocument> userDocuments = mapper.toDocumentList(domains);
        return mapper.toDomainModelList(repository.saveAll(userDocuments));
    }

    @Override
    public User getById(UUID userId) {
        UserDocument userDocument = repository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return mapper.toDomainModel(userDocument);
    }

    @Override
    public void deleteById(UUID userId) {
        repository.deleteById(userId);
    }
}
