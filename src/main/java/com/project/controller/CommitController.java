package com.project.controller;


import com.project.entity.Commit;
import com.project.service.CommitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2022-12-16
 */
@RestController
@RequestMapping("//commit")
public class CommitController {

    @Autowired
    private CommitService commitService;

    @GetMapping("/developerCount")
    public Long developerCount() {
        List<Commit> commitList = commitService.list();
        return commitList.stream().map(Commit::getAuthor).distinct().count();
    }
}

