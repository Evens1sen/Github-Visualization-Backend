package com.project.controller;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.project.entity.Issue;
import com.project.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2022-12-16
 */
@RestController
@RequestMapping("//issue")
public class IssueController {
    @Autowired
    private IssueService issueService;

    private double variance(List<Issue> issueList, double mean) {
        double ans = 0;
        for (Issue i : issueList) {
            ans += Math.pow(i.getIssueTime() - mean, 2);
        }
        ans /= issueList.size();
        return ans;
    }

    @GetMapping("/openCount")
    public long openCount() {
        return issueService.list(new QueryWrapper<Issue>().eq("state", "open")).size();
    }

    @GetMapping("/closedCount")
    public long closedCount() {
        return issueService.list(new QueryWrapper<Issue>().eq("state", "closed")).size();
    }

    @GetMapping("/maxTime")
    public long maxTime() {
        return issueService.list().stream().mapToLong(Issue::getIssueTime).summaryStatistics().getMax();
    }

    @GetMapping("/minTime")
    public long minTime() {
        return issueService.list().stream().mapToLong(Issue::getIssueTime).summaryStatistics().getMin();
    }

    @GetMapping("/avgTime")
    public double avgTime() {
        return issueService.list().stream().mapToLong(Issue::getIssueTime).summaryStatistics().getAverage();
    }

    @GetMapping("varTime")
    public double varTime() {
        return variance(issueService.list(), avgTime());
    }

    @GetMapping("/maxClosedTime")
    public long maxClosedTime() {
        return issueService.list(new QueryWrapper<Issue>().eq("state", "closed")).stream().mapToLong(Issue::getIssueTime).summaryStatistics().getMax();
    }

    @GetMapping("/minClosedTime")
    public long minClosedTime() {
        return issueService.list(new QueryWrapper<Issue>().eq("state", "closed")).stream().mapToLong(Issue::getIssueTime).summaryStatistics().getMin();
    }

    @GetMapping("/avgClosedTime")
    public double avgClosedTime() {
        return issueService.list(new QueryWrapper<Issue>().eq("state", "closed")).stream().mapToLong(Issue::getIssueTime).summaryStatistics().getAverage();
    }

    @GetMapping("varClosedTime")
    public double varClosedTime() {
        return variance(issueService.list(new QueryWrapper<Issue>().eq("state", "closed")), avgTime());
    }

    @GetMapping("/maxOpenTime")
    public long maxOpenTime() {
        return issueService.list(new QueryWrapper<Issue>().eq("state", "open")).stream().mapToLong(Issue::getIssueTime).summaryStatistics().getMax();
    }

    @GetMapping("/minOpenTime")
    public long minOpenTime() {
        return issueService.list(new QueryWrapper<Issue>().eq("state", "open")).stream().mapToLong(Issue::getIssueTime).summaryStatistics().getMin();
    }

    @GetMapping("/avgOpenTime")
    public double avgOpenTime() {
        return issueService.list(new QueryWrapper<Issue>().eq("state", "open")).stream().mapToLong(Issue::getIssueTime).summaryStatistics().getAverage();
    }

    @GetMapping("/varOpenTime")
    public double varOpenTime() {
        return variance(issueService.list(new QueryWrapper<Issue>().eq("state", "open")), avgTime());
    }

    @GetMapping("/findIssueById")
    public Issue findIssueById(@RequestParam int id) {
        return issueService.getOne(new QueryWrapper<Issue>().eq("issue_id", id));
    }

    @GetMapping("/findTitleById")
    public String findTitleById(@RequestParam int id) {
        return findIssueById(id).getTitle();
    }
}

