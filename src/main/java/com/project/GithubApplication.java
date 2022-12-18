package com.project;

import com.project.util.DataLoader;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.project.mapper")
public class GithubApplication {

    public static void main(String[] args) {
        SpringApplication.run(GithubApplication.class, args);
        String user = "PKUFlyingPig";
        String repo = "cs-self-learning";
        DataLoader.loadCommit(user, repo);
        DataLoader.loadIssue(user, repo);
        DataLoader.loadRelease(user, repo);
    }

}
