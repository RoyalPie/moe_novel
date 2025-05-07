package com.royal.iam_service.infrastructure.persistence.repository.impl;


import com.royal.iam_service.domain.query.SearchPermissionQuery;
import com.royal.iam_service.infrastructure.persistence.entity.PermissionEntity;
import com.royal.iam_service.infrastructure.persistence.repository.custom.PermissionEntityRepositoryCustom;
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
public class PermissionEntityRepositoryImpl implements PermissionEntityRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<PermissionEntity> search(SearchPermissionQuery searchPermissionQuery) {
        Map<String, Object> values = new HashMap<>();
        String sql = "select p from PermissionEntity p " + createWhereQuery(searchPermissionQuery.getKeyword(), values) + createOrderQuery(searchPermissionQuery.getSortBy());
        TypedQuery<PermissionEntity> query = entityManager.createQuery(sql, PermissionEntity.class);
        values.forEach(query::setParameter);
        query.setFirstResult((searchPermissionQuery.getPageIndex() - 1) * searchPermissionQuery.getPageSize());
        query.setMaxResults(searchPermissionQuery.getPageSize());
        return query.getResultList();
    }

    private String createWhereQuery(String keyword, Map<String, Object> values) {
        StringBuilder sql = new StringBuilder();
        sql.append(" where p.deleted = false");
        if (!keyword.isBlank()) {
            sql.append(
                    " and ( lower(p.scope) like :keyword"
                            + " or lower(p.resource) like :keyword )");
            values.put("keyword", encodeKeyword(keyword));
        }
        return sql.toString();
    }

    public StringBuilder createOrderQuery(String sortBy) {
        StringBuilder hql = new StringBuilder(" ");
        if (StringUtils.hasLength(sortBy)) {
            hql.append(" order by p.").append(sortBy.replace(".", " "));
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
    public Long count(SearchPermissionQuery searchPermissionQuery) {
        Map<String, Object> values = new HashMap<>();
        String sql = "select count(p) from PermissionEntity p " + createWhereQuery(searchPermissionQuery.getKeyword(), values);
        Query query = entityManager.createQuery(sql, Long.class);
        values.forEach(query::setParameter);
        return (Long) query.getSingleResult();
    }
}
