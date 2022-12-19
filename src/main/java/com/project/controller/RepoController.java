package com.project.controller;


import com.project.util.DataLoader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("//repo")
public class RepoController {

    @GetMapping("/loadRepo/{user}/{repo}")
    public void loadRepo(@PathVariable String user, @PathVariable String repo) {
        DataLoader.loadData(user, repo);
    }
}
