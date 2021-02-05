package com.main.app.util;

import com.main.app.domain.model.user.User;
import com.main.app.elastic.dto.EntityElasticDTO;
import com.main.app.elastic.dto.category.CategoryElasticDTO;
import com.main.app.elastic.dto.user.UserElasticDTO;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Util {

    public static String generateUniqueString() {
        return UUID.randomUUID().toString();
    }

    public static List<Long> entitiesToIds(Page<EntityElasticDTO> entities) {
        List<Long> ids = new ArrayList<>();
        entities.forEach(item -> {
            ids.add(item.getId());
        });
        return ids;
    }

    public static List<Long> usersToIds(Page<UserElasticDTO> entities) {
        List<Long> ids = new ArrayList<>();
        entities.forEach(item -> {
            ids.add(item.getId());
        });
        return ids;
    }


    public static List<Long> adminUsersToIds(Page<User> entities) {
        List<Long> ids = new ArrayList<>();
        entities.forEach(item -> {
            ids.add(item.getId());
        });
        return ids;
    }

    public static List<Long> categoriesToIds(Page<CategoryElasticDTO> entities) {
        List<Long> ids = new ArrayList<>();
        entities.forEach(item -> {
            ids.add(item.getId());
        });
        return ids;
    }


}
