package com.main.app.service.post;

import com.main.app.domain.dto.post.ReportContentDTO;
import com.main.app.domain.model.post.ReportContent;
import com.main.app.repository.post.PostRepository;
import com.main.app.repository.post.ReportContentRepository;
import com.main.app.service.user.CurrentUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportContentServiceImpl implements ReportContentService{

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CurrentUserService currentUserService;

    @Autowired
    private ReportContentRepository reportContentRepository;

    @Override
    public void create(ReportContentDTO reportContentDTO) {

        ReportContent content = new ReportContent();
        content.setPost(postRepository.getOne(reportContentDTO.getPostId()));
        content.setStatus(false);
        content.setUserId(currentUserService.getCurrentUser().getId());

        reportContentRepository.save(content);
    }

    @Override
    public List<ReportContent> getAll() {
        return reportContentRepository.findAll();
    }

    @Override
    public void approve(Long id) {
        ReportContent content = reportContentRepository.getOne(id);
        content.setStatus(true);

        reportContentRepository.save(content);
    }
}
