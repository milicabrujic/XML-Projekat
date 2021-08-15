package com.main.app.repository.post;

import com.main.app.domain.model.post.Reaction;
import com.main.app.domain.model.post.ReportContent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportContentRepository extends JpaRepository<ReportContent, Long> {

}
