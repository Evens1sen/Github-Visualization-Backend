package com.project.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.project.entity.Issue;
import com.project.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
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

    @GetMapping("/issueCount")
    public String issueCount() {
        List<Issue> issueList = issueService.list();
        String allCount = String.valueOf(issueList.stream().map(Issue::getState).count());
        String openCount = String.valueOf(issueList.stream().map(Issue::getState).filter(s -> s.equals("open")).count());
        String closedCount = String.valueOf(issueList.stream().map(Issue::getState).filter(s -> s.equals("closed")).count());
        return "All issue count is " + allCount + "\nOpen issue count is " + openCount + "\nClose issue count is " + closedCount;
    }

    @GetMapping("issueTime")
    public String issueTime() {
        List<Issue> issueList = issueService.list();
        List<Issue> closedList = issueList.stream().filter(s -> s.getState().equals("closed")).collect(Collectors.toList());
        List<Issue> openList = issueList.stream().filter(s -> s.getState().equals("open")).collect(Collectors.toList());
        LongSummaryStatistics close_summary = closedList.stream().map(Issue::getIssueTime).mapToLong(x -> x).summaryStatistics();
        LongSummaryStatistics open_summary = openList.stream().map(Issue::getIssueTime).mapToLong(x -> x).summaryStatistics();
        LongSummaryStatistics issue_summary = issueList.stream().map(Issue::getIssueTime).mapToLong(x -> x).summaryStatistics();
        String ans1 = "All Issues: The max time:" + issue_summary.getMax() + "s. The min time:" + issue_summary.getMin() + "s. The average time:" + issue_summary.getAverage() + "s.\n";
        String ans2 = "The open Issues: The max time:" + open_summary.getMax() + "s. The min time:" + open_summary.getMin() + "s. The average time:" + open_summary.getAverage() + "s.\n";
        String ans3 = "The Closed Issued: The max time:" + close_summary.getMax() + "s. The min time:" + close_summary.getMin() + "s. The average time:" + close_summary.getAverage() + "s.\n";
        return ans1 + ans2 + ans3;
    }
}

