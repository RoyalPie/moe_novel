package com.royal.iam_service.infrastructure.persistence.repository.impl;

import com.royal.iam_service.domain.query.SearchUserQuery;
import com.royal.iam_service.infrastructure.persistence.entity.UserEntity;
import com.royal.iam_service.infrastructure.persistence.repository.custom.UserEntityRepositoryCustom;
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
public class UserEntityRepositoryImpl implements UserEntityRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<UserEntity> search(SearchUserQuery searchUserQuery) {
        Map<String, Object> values = new HashMap<>();
        String sql = "select e from UserEntity e " + createWhereQuery(searchUserQuery.getKeyword(), values) + createOrderQuery(searchUserQuery.getSortBy());
        TypedQuery<UserEntity> query = entityManager.createQuery(sql, UserEntity.class);
        values.forEach(query::setParameter);
        query.setFirstResult((searchUserQuery.getPageIndex() - 1) * searchUserQuery.getPageSize());
        query.setMaxResults(searchUserQuery.getPageSize());
        return query.getResultList();
    }

    private String createWhereQuery(String keyword, Map<String, Object> values) {
        StringBuilder sql = new StringBuilder();
        sql.append(" where e.active = true");
        if (!keyword.isBlank()) {
            sql.append(
                    " and ( lower(e.username) like :keyword"
                            + " or lower(e.email) like :keyword )");
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
    public Long count(SearchUserQuery searchUserQuery) {
        Map<String, Object> values = new HashMap<>();
        String sql = "select count(e) from UserEntity e " + createWhereQuery(searchUserQuery.getKeyword(), values);
        Query query = entityManager.createQuery(sql, Long.class);
        values.forEach(query::setParameter);
        return (Long) query.getSingleResult();
    }
}