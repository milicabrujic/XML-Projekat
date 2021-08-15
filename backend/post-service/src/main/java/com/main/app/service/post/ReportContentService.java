package com.main.app.service.post;

import com.main.app.domain.dto.post.ReportContentDTO;
import com.main.app.domain.model.post.ReportContent;

import java.util.List;

public interface ReportContentService {

    void create(ReportContentDTO reportContentDTO);
    List<ReportContent> getAll();
    void approve(Long id);
}
