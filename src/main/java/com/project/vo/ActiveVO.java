package com.project.vo;

import lombok.Data;

@Data
public class ActiveVO {

    private String x;

    private Long y;

    public ActiveVO(String x, Long y) {
        this.x = x;
        this.y = y;
    }
}
