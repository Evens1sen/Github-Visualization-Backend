package com.project.controller;


import com.project.entity.Commit;
import com.project.service.CommitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2022-12-16
 */
@Controller
@RequestMapping("//commit")
public class CommitController {

    @Autowired
    private CommitService commitService;

    @PostMapping("/addCommit/{sha}/{author}")
    public boolean addCommit(@PathVariable String sha, @PathVariable String author) {
        Commit commit = new Commit();
        commit.setSha(sha);
        commit.setAuthor(author);
        return commitService.save(commit);
    }
}

