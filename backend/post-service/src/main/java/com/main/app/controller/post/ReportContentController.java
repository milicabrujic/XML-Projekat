package com.main.app.controller.post;

import com.main.app.domain.dto.post.ReportContentDTO;
import com.main.app.domain.model.post.ReportContent;
import com.main.app.service.post.ReportContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/report-controller")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ReportContentController {

    @Autowired
    private ReportContentService reportContentService;

    @PostMapping
    public void create(ReportContentDTO reportContentDTO) {
        reportContentService.create(reportContentDTO);
    }

    @GetMapping
    public ResponseEntity<List<ReportContent>> get() {
        return new ResponseEntity<>(reportContentService.getAll(), HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    public void approve(@PathVariable Long id) {
        reportContentService.approve(id);
    }
}
