package com.project.controller;


import com.project.entity.Commit;
import com.project.entity.Releases;
import com.project.service.CommitService;
import com.project.service.ReleasesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

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
    private ReleasesService releasesService;
    @Autowired
    private CommitService commitService;

    @GetMapping("releasesCount")
    public int releasesCount() {
        return releasesService.list().size();
    }

    @GetMapping("releasesCommit")
    public List<Integer> commitCount() {
        List<Releases> releasesList = releasesService.list();
        List<Commit> commitList = commitService.list();
        List<Integer> ans = new ArrayList<>();
        List<Commit> tmp;
        if (releasesList.size() != 0) {
            for (int i = 0; i < releasesList.size(); i++) {
                if (i == 0) {
                    tmp = commitList.stream().filter(s -> s.getCreatedAt().isBefore(releasesList.get(0).getPublishedAt())).collect(Collectors.toList());
                } else {
                    int finalI = i;
                    tmp = commitList.stream().filter(s -> s.getCreatedAt().isBefore(releasesList.get(finalI).getPublishedAt())).filter(s -> s.getCreatedAt().isAfter(releasesList.get(finalI - 1).getPublishedAt())).collect(Collectors.toList());
                }
                ans.add(tmp.size());
            }
        }
        return ans;
    }

    @GetMapping("commitTime")
    public List<Long> commitTime() {
        List<Commit> commitList = commitService.list();
        long count_weekend = commitList.stream().filter(s -> String.valueOf(s.getCreatedAt().getDayOfWeek()).equals("SUNDAY")).count() +
                commitList.stream().filter(s -> String.valueOf(s.getCreatedAt().getDayOfWeek()).equals("SATURDAY")).count();
        long count_weekday = commitList.size() - count_weekend;
        long count_morning = commitList.stream().filter(s -> s.getCreatedAt().getHour() < 12).count() - commitList.stream().filter(s -> s.getCreatedAt().getHour() < 6).count();
        long count_afternoon = commitList.stream().filter(s -> s.getCreatedAt().getHour() < 18).count() - commitList.stream().filter(s -> s.getCreatedAt().getHour() < 12).count();
        long count_evening = commitList.size() - commitList.stream().filter(s -> s.getCreatedAt().getHour() < 18).count();
        long count_midnight = commitList.stream().filter(s -> s.getCreatedAt().getHour() < 6).count();
        List<Long> ans = new ArrayList<>() {{
            add(count_weekday); // Monday ~ Friday
            add(count_weekend); // Saturday ~ Sunday
            add(count_midnight); // 0-6
            add(count_morning);  // 6-12
            add(count_afternoon); // 12-18
            add(count_evening); // 18-24
        }};
        return ans;
    }
}

