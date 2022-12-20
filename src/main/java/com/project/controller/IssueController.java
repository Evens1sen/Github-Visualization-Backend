package com.project.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.project.entity.Issue;
import com.project.service.IssueService;
import com.project.vo.ChartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
//        double mean = Double.parseDouble(tmp.substring(1, tmp.length() - 2));
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
    public String maxTime() {
        return convertTime(issueService.list().stream()
                .mapToLong(Issue::getIssueTime)
                .summaryStatistics().getMax());
    }

    @GetMapping("/minTime")
    public String minTime() {
        return convertTime(issueService.list().stream()
                .mapToLong(Issue::getIssueTime)
                .summaryStatistics().getMin());
    }

    @GetMapping("/avgTime")
    public String avgTime() {
        return convertTime((long) Math.floor(issueService.list().stream()
                .mapToLong(Issue::getIssueTime)
                .summaryStatistics().getAverage()));
    }

    @GetMapping("varTime")
    public double varTime() {
        return variance(issueService.list(),
                issueService.list()
                        .stream()
                        .mapToLong(Issue::getIssueTime)
                        .summaryStatistics().getAverage());
    }

    @GetMapping("/maxClosedTime")
    public String maxClosedTime() {
        return convertTime(issueService
                .list(new QueryWrapper<Issue>().eq("state", "closed")).stream()
                .mapToLong(Issue::getIssueTime)
                .summaryStatistics().getMax());
    }

    @GetMapping("/minClosedTime")
    public String minClosedTime() {
        return convertTime(issueService
                .list(new QueryWrapper<Issue>().eq("state", "closed")).stream()
                .mapToLong(Issue::getIssueTime)
                .summaryStatistics().getMin());
    }

    @GetMapping("/avgClosedTime")
    public String avgClosedTime() {
        return convertTime((long) Math.floor(issueService
                .list(new QueryWrapper<Issue>().eq("state", "closed")).stream()
                .mapToLong(Issue::getIssueTime)
                .summaryStatistics().getAverage()));
    }

//    @Deprecated
//    @GetMapping("/varClosedTime")
//    public double varClosedTime() {
//        return variance(issueService.list(new QueryWrapper<Issue>().eq("state", "closed")), avgTime());
//    }

    @GetMapping("/maxOpenTime")
    public String maxOpenTime() {
        return convertTime(issueService
                .list(new QueryWrapper<Issue>().eq("state", "open")).stream()
                .mapToLong(Issue::getIssueTime)
                .summaryStatistics().getMax());
    }

    @GetMapping("/minOpenTime")
    public String minOpenTime() {
        return convertTime(issueService
                .list(new QueryWrapper<Issue>().eq("state", "open")).stream()
                .mapToLong(Issue::getIssueTime)
                .summaryStatistics().getMin());
    }

    @GetMapping("/avgOpenTime")
    public String avgOpenTime() {
        return convertTime((long) Math.floor(issueService
                .list(new QueryWrapper<Issue>().eq("state", "open")).stream()
                .mapToLong(Issue::getIssueTime)
                .summaryStatistics().getAverage()));
    }

//    @Deprecated
//    @GetMapping("/varOpenTime")
//    public double varOpenTime() {
//        return variance(issueService.list(new QueryWrapper<Issue>().eq("state", "open")), avgTime());
//    }

    @GetMapping("/findIssueById")
    public Issue findIssueById(@RequestParam int id) {
        return issueService.getOne(new QueryWrapper<Issue>().eq("issue_id", id));
    }

    @GetMapping("/findTitleById")
    public String findTitleById(@RequestParam int id) {
        return findIssueById(id).getTitle();
    }

    @GetMapping("/findBodyById")
    public String findBodyById(@RequestParam int id) {
        return findIssueById(id).getBody();
    }

    @GetMapping("/findTitle")
    public List<String> findTitle() {
        return issueService.list().stream().map(Issue::getTitle).collect(Collectors.toList());
    }

    @GetMapping("/findHighFreqTitle")
    public List<ChartVO> findHighFreqTitle() {
        List<String> titleList = findTitle();
        List<String> wordList = new ArrayList<>();
        for (String i : titleList) {
            if (i != null) {
                String[] arr = i.toLowerCase().replaceAll("[\u4e00-\u9fa5]+", "").replace("[", "").replace("]", "").split(" ");
                wordList.addAll(Arrays.asList(arr));
            }
        }
        return topKFrequent(wordList, 10);
    }

    @GetMapping("/findBody")
    public List<String> findBody() {
        return issueService.list().stream().map(Issue::getBody).distinct().collect(Collectors.toList());
    }

    @GetMapping("/findHighFreqBody")
    public List<ChartVO> findHighFreqBody() {
        List<String> bodyList = findBody();
        List<String> wordList = new ArrayList<>();
        for (String i : bodyList) {
            if (i != null) {
                String[] arr = i.toLowerCase().replaceAll("[\u4e00-\u9fa5]+", "").replace("[", "").replace("]", "").split(" ");
                wordList.addAll(Arrays.asList(arr));
            }
        }
        return topKFrequent(wordList, 5);
    }

    public List<ChartVO> topKFrequent(List<String> words, int k) {
//        List<String> result = new LinkedList<>();
        List<ChartVO> result = new ArrayList<>();
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < words.size(); i++) {
            if (map.containsKey(words.get(i)))
                map.put(words.get(i), map.get(words.get(i)) + 1);
            else
                map.put(words.get(i), 1);
        }
        PriorityQueue<Map.Entry<String, Integer>> pq = new PriorityQueue<>(
                (a, b) -> Objects.equals(a.getValue(), b.getValue()) ? b.getKey().compareTo(a.getKey()) : a.getValue() - b.getValue()
        );
        for (Map.Entry<String, Integer> s : map.entrySet()) {
            if (s.getKey().equals("an") || s.getKey().equals("a") || s.getKey().equals("some") || s.getKey().equals("to") || s.getKey().equals("for") || s.getKey().equals("") || s.getKey().equals("in") || s.getKey().equals("on") || s.getKey().equals("，") || s.getKey().equals("，，") || s.getKey().equals("is") || s.getKey().equals("are") || s.getKey().equals("not") || s.getKey().equals(",") || s.getKey().equals("of") || s.getKey().equals("the") || s.getKey().equals("-") || s.getKey().equals("and")) {
                continue;
            } else {
                pq.offer(s);
            }
            if (pq.size() > k)
                pq.poll();
        }
        while (!pq.isEmpty()) {
            result.add(0, new ChartVO(pq.peek().getKey(), Long.parseLong(String.valueOf(pq.peek().getValue()))));
            pq.poll();
        }
        return result;
    }

    public String convertTime(long seconds) {
        long hour = seconds / 3600;
        seconds = seconds % 3600;
        long min = seconds / 60;
        seconds = seconds % 60;

        StringBuilder res = new StringBuilder();
        if (hour != 0) {
            res.append(String.format("%d hour ", hour));
        }
        if (min != 0) {
            res.append(String.format("%d min ", min));
        }
        if (seconds != 0) {
            res.append(String.format("%d sec", seconds));
        }

        return res.toString();
    }
}

