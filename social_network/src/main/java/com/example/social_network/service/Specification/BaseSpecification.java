package com.example.social_network.service.Specification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class BaseSpecification {
    public static <T> Specification <T> like(final String column, final String value) {
        return StringUtils.isEmpty(column) || StringUtils.isEmpty(value)
                ? null
                : (root, query, cb) ->
                cb.like(root.get(column), "%"+ value + "%");
    }

    public  static <T> Specification <T> gt(final String column, final Integer min) {
        return StringUtils.isEmpty(column) || min == null
                ? null
                : (root, query, cb) ->
                cb.gt(root.get(column), min);
    }

    public static <T> Specification <T> lt(final String column, final Integer max) {
        return StringUtils.isEmpty(column) || max == null
                ? null
                : (root, query, cb) ->
                cb.lt(root.get("age"),max);
    }
}
