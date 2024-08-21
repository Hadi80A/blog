package org.hammasir.blog.specification;

import org.hammasir.blog.entity.Post;
import org.springframework.cglib.core.internal.Function;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PostSpecification {

    public static Specification<Post> likesGreaterThan(int likes) {
        return (root, query, criteriaBuilder) -> 
                criteriaBuilder.greaterThan(root.get("likes"), likes);
    }

    public static Specification<Post> createdOnDate(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59, 59, 999_999_999);
        
        return (root, query, criteriaBuilder) -> 
                criteriaBuilder.between(root.get("createdDate"), startOfDay, endOfDay);
    }

    public static Specification<Post> createdToday() {
        LocalDate today = LocalDate.now();
        return createdOnDate(today);
    }

    public static Specification<Post> titleContains(String word) {
        return (root, query, criteriaBuilder) -> 
                criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + word.toLowerCase() + "%");
    }

    public static Map<String, Function<Object, Specification<Post>>> getFilterFunctions() {
        Map<String, Function<Object, Specification<Post>>> functions = new HashMap<>();
        functions.put("likesGreaterThan", value -> likesGreaterThan((Integer) value));
        functions.put("createdOnDate", value -> createdOnDate((LocalDate) value));
        functions.put("createdToday", value -> createdToday());
        functions.put("titleContains", value -> titleContains((String) value));
        return functions;
    }
}
