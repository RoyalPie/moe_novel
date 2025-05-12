package com.royal.novel_service.infrastructure.persistence.repository.impl;

import com.royal.novel_service.domain.query.SearchNovelQuery;
import com.royal.novel_service.infrastructure.persistence.entity.NovelEntity;
import com.royal.novel_service.infrastructure.persistence.repository.custom.NovelEntityRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class NovelEntityRepositoryImpl implements NovelEntityRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<NovelEntity> search(SearchNovelQuery searchNovelQuery) {
        Map<String, Object> values = new HashMap<>();
        String sql = "select e from NovelEntity e " + createWhereQuery(searchNovelQuery.getKeyword(), values) + createOrderQuery(searchNovelQuery.getSortBy());
        TypedQuery<NovelEntity> query = entityManager.createQuery(sql, NovelEntity.class);
        values.forEach(query::setParameter);
        query.setFirstResult((searchNovelQuery.getPageIndex() - 1) * searchNovelQuery.getPageSize());
        query.setMaxResults(searchNovelQuery.getPageSize());
        return query.getResultList();
    }

    private String createWhereQuery(String keyword, Map<String, Object> values) {
        StringBuilder sql = new StringBuilder();
        sql.append(" where e.deleted = false");
        if (!keyword.isBlank()) {
            sql.append(
                    " and ( lower(e.title) like :keyword"
                            + ")");
            values.put("keyword", encodeKeyword(keyword));
        }
        return sql.toString();
    }

    public StringBuilder createOrderQuery(String sortBy) {
        StringBuilder hql = new StringBuilder(" ");
        if (StringUtils.hasLength(sortBy)) {
            hql.append(" order by e.").append(sortBy.replace(".", " "));
        }
        return hql;
    }

    public String encodeKeyword(String keyword) {
        if (keyword == null) {
            return "%";
        }

        return "%" + keyword.trim().toLowerCase() + "%";
    }

    @Override
    public Long count(SearchNovelQuery searchUserQuery) {
        Map<String, Object> values = new HashMap<>();
        String sql = "select count(e) from NovelEntity e " + createWhereQuery(searchUserQuery.getKeyword(), values);
        Query query = entityManager.createQuery(sql, Long.class);
        values.forEach(query::setParameter);
        return (Long) query.getSingleResult();
    }
}