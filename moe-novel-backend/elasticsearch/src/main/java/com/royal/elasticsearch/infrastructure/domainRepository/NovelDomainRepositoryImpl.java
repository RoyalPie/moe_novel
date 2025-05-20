package com.royal.elasticsearch.infrastructure.domainRepository;

import com.royal.elasticsearch.domain.Novel;
import com.royal.elasticsearch.domain.repository.NovelDomainRepository;
import com.royal.elasticsearch.infrastructure.persistence.document.NovelDocument;
import com.royal.elasticsearch.infrastructure.persistence.mapper.NovelDocumentMapper;
import com.royal.elasticsearch.infrastructure.persistence.repository.NovelDocumentRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class NovelDomainRepositoryImpl extends AbstractDocumentRepository<Novel, NovelDocument, UUID>
        implements NovelDomainRepository {
    private final NovelDocumentMapper mapper;
    private final NovelDocumentRepository repository;

    public NovelDomainRepositoryImpl(NovelDocumentMapper mapper, NovelDocumentRepository repository) {
        super(repository, mapper);
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public Novel getById(UUID novelId){
        NovelDocument novelDocument = repository.findById(novelId).orElseThrow(()-> new RuntimeException("Novel not found"));
        return mapper.toDomainModel(novelDocument);
    }

    @Override
    public List<Novel> saveAll(List<Novel> domains){
        List<NovelDocument> novelDocuments = mapper.toDocumentList(domains);
        return mapper.toDomainModelList(repository.saveAll(novelDocuments));
    }

    @Override
    public void deleteById(UUID novelId){
        repository.deleteById(novelId);
    }

}
