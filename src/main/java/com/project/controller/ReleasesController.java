package com.project.controller;

import com.project.entity.Commit;
import com.project.entity.Releases;
import com.project.service.CommitService;
import com.project.service.ReleasesService;
import com.project.vo.ChartVO;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



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

    @GetMapping("/releasesCount")
    public int releasesCount() {
        return releasesService.list().size();
    }

    @GetMapping("/releasesCommit")
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

    @GetMapping("/releasesCommit/findById")
    public int commitNum(@RequestParam int id) {
        return commitCount().get(id - 1);
    }

    @GetMapping("releasesInterval")
    public List<ChartVO> releasesInterval() {
        List<ChartVO> res = new ArrayList<>();
        List<Integer> commitList = commitCount();

        int releaseNum = releasesService.count();
        for (int i = 1; i <= releaseNum; i++) {
            res.add(new ChartVO(String.format("%d-%d", i - 1, i), Long.valueOf(commitList.get(i - 1))));
        }

        return res;
    }

    @GetMapping("/commitTime")
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

    @GetMapping("commitDistribution/{time}")
    public List<ChartVO> commitDistribution(@PathVariable String time) {
        List<ChartVO> res = new ArrayList<>();
        List<Long> timeCount = commitTime();

        if (time.equals("week")) {
            res.add(new ChartVO("weekday", timeCount.get(0)));
            res.add(new ChartVO("weekend", timeCount.get(1)));
        } else {
            res.add(new ChartVO("midnight", timeCount.get(2)));
            res.add(new ChartVO("morning", timeCount.get(3)));
            res.add(new ChartVO("afternoon", timeCount.get(4)));
            res.add(new ChartVO("evening", timeCount.get(5)));
        }

        return res;
    }

    @GetMapping("/commitTime/weekday")
    public Long commitWeekday() {
        return commitTime().get(0);
    }

    @GetMapping("/commitTime/weekend")
    public Long commitWeekend() {
        return commitTime().get(1);
    }

    @GetMapping("/commitTime/midnight")
    public Long commitMidnight() {
        return commitTime().get(2);
    }

    @GetMapping("/commitTime/morning")
    public Long commitMorning() {
        return commitTime().get(3);
    }

    @GetMapping("/commitTime/afternoon")
    public Long commitAfternoon() {
        return commitTime().get(4);
    }

    @GetMapping("/commitTime/evening")
    public Long commitEvening() {
        return commitTime().get(5);
    }

}

