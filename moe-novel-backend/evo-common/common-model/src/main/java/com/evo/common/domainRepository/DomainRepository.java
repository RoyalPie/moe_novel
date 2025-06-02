package com.evo.common.domainRepository;

import java.util.List;
import java.util.Optional;

public interface DomainRepository<D, ID> {
    Optional<D> findById(ID id);

    D save(D entity);

    List<D> saveAll(List<D> entities);

    List<D> findAllByIds(List<ID> ids);

    D getById(ID id);

    D delete(ID id);
}