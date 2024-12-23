package com.gc.zelda_api.util;

import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;

public class PageUtils {

    public static <T> HashMap<String, Object> getJSONByPage(Page<T> page, String key) {
        HashMap<String, Object> map = new HashMap<>();
        List<T> content = page.getContent();

        map.put("total_elements", page.getTotalElements());
        map.put("size", content.size());
        map.put(key, content);

        return map;
    }
}
