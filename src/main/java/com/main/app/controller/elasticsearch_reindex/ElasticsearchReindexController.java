package com.main.app.controller.elasticsearch_reindex;

import com.main.app.service.elasticsearch_reindex.ElasticsearchReindexService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reindex")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ElasticsearchReindexController {

    private final ElasticsearchReindexService elasticsearchReindexService;

    @GetMapping(path="/all")
    public ResponseEntity<Void> reindexAll(){
        elasticsearchReindexService.reindexAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }



}

