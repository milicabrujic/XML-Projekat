package com.main.app.service.attribute;

import com.main.app.domain.dto.Entities;
import com.main.app.domain.model.attribute.Attribute;
import org.springframework.data.domain.Pageable;


public interface AttributeService {

    Entities getAll();

    Entities getAllBySearchParam(String searchParam, Pageable pageable);

    Attribute getOne(Long id);

    Attribute save(Attribute attribute);

    Attribute edit(Attribute attribute, Long id);

    Attribute delete(Long id);

}
