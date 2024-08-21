package org.hammasir.blog.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FilterParamsDto {
    Integer likesGreaterThan;
    LocalDate createdOnDate;
    String titleContains;

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        if (likesGreaterThan != null) {
            map.put("likesGreaterThan", likesGreaterThan);
        }
        if (createdOnDate != null) {
            map.put("createdOnDate", createdOnDate);
        }
        if (titleContains != null) {
            map.put("titleContains", titleContains);
        }
        return map;
    }
}
