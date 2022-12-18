package com.project.controller;


import com.project.entity.Releases;
import com.project.service.ReleasesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2022-12-19
 */
@RestController
@RequestMapping("//releases")
public class ReleasesController {
    @Autowired
    ReleasesService releasesService;

    @GetMapping("releasesCount")
    public String releasesCount(){
        List<Releases> releasesList = releasesService.list();
        return "All issue count is " + releasesList.stream().count();
    }

}

