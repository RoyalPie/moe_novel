package com.royal.novel_service.infrastructure.persistence.repository.impl;

import com.royal.novel_service.domain.query.NovelSearchQuery;
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
import java.util.Objects;

@Repository
public class NovelEntityRepositoryImpl implements NovelEntityRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<NovelEntity> search(NovelSearchQuery searchNovelQuery) {
        Map<String, Object> values = new HashMap<>();
        String sql = "select e from NovelEntity e " + createWhereQuery(searchNovelQuery, values) + createOrderQuery(searchNovelQuery.getSortBy());
        TypedQuery<NovelEntity> query = entityManager.createQuery(sql, NovelEntity.class);
        values.forEach(query::setParameter);
        query.setFirstResult((searchNovelQuery.getPageIndex() - 1) * searchNovelQuery.getPageSize());
        query.setMaxResults(searchNovelQuery.getPageSize());
        return query.getResultList();
    }

    private String createWhereQuery(NovelSearchQuery searchNovelQuery, Map<String, Object> values) {
        StringBuilder sql = new StringBuilder();
        sql.append(" where e.deleted = false ");
        if (!searchNovelQuery.getKeyword().isBlank()) {
            sql.append(
                    "and ( lower(e.title) like :keyword"
                            + ")");
            values.put("keyword", encodeKeyword(searchNovelQuery.getKeyword()));
        }
        if (!searchNovelQuery.getAuthorName().isBlank()) {
            sql.append(
                    "and ( lower(e.authorName) like :authorName"
                            + ")");
            values.put("authorName", encodeKeyword(searchNovelQuery.getAuthorName()));
        }
        if (Objects.nonNull(searchNovelQuery.getNovelStatus())) {
            sql.append("and e.status = :status ");
            values.put("status", searchNovelQuery.getNovelStatus());
        }
        if (!searchNovelQuery.getGenre().isBlank()) {
            sql.append(
                    "and exists (" +
                            "  select 1" +
                            "  from NovelGenreEntity ng" +
                            "  join GenreEntity g on ng.genreId = g.genreId" +
                            "  where ng.novelId = e.id and g.genreName = :genre" +
                            ") ");
            values.put("genre", searchNovelQuery.getGenre().trim());
        }
        if (!searchNovelQuery.getTag().isBlank()) {
            sql.append(
                    "and exists (" +
                            "  select 1" +
                            "  from NovelTagEntity ng" +
                            "  join TagEntity g on ng.tagId = g.tagId" +
                            "  where ng.novelId = e.id and g.tagName = :tag" +
                            ") ");
            values.put("tag", searchNovelQuery.getTag().trim());
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
    public Long count(NovelSearchQuery searchUserQuery) {
        Map<String, Object> values = new HashMap<>();
        String sql = "select count(e) from NovelEntity e " + createWhereQuery(searchUserQuery, values);
        Query query = entityManager.createQuery(sql, Long.class);
        values.forEach(query::setParameter);
        return (Long) query.getSingleResult();
    }

}