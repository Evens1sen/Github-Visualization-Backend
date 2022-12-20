package com.project.vo;

import lombok.Data;

@Data
public class ChartVO {

    private String x;

    private Long y;

    public ChartVO(String x, Long y) {
        this.x = x;
        this.y = y;
    }
}
