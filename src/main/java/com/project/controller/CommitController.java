package com.project.controller;


import com.project.entity.Commit;
import com.project.service.CommitService;
import com.project.vo.ChartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
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
@RequestMapping("//commit")
public class CommitController {

    @Autowired
    private CommitService commitService;

    @GetMapping("/developerCount")
    public Long developerCount() {
        List<Commit> commitList = commitService.list();
        return commitList.stream().map(Commit::getAuthor).distinct().count();
    }

    @GetMapping("/mostActive")
    public String mostActive() {
        List<Commit> commitList = commitService.list();
        Map<String, Long> developerMap = commitList.stream()
                .collect(Collectors.groupingBy(
                        Commit::getAuthor,
                        Collectors.counting()
                ));

        return developerMap.entrySet().stream().max(Map.Entry.comparingByValue()).get().getKey();
    }

    @GetMapping("topKActice/{k}")
    public List<ChartVO> topKActive(@PathVariable int k) {
        List<Commit> commitList = commitService.list();
        Map<String, Long> developerMap = commitList.stream()
                .collect(Collectors.groupingBy(
                        Commit::getAuthor,
                        Collectors.counting()
                ));

        return developerMap.entrySet().stream()
                .sorted((o1, o2) -> Long.compare(o2.getValue(), o1.getValue()))
                .limit(5)
                .map(e -> new ChartVO(e.getKey(), e.getValue()))
                .toList();
    }

}

