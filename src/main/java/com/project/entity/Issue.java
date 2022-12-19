package com.project.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author ${author}
 * @since 2022-12-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Issue implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "issue_id", type = IdType.AUTO)
    private Integer issueId;

    private String state;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime closedAt;

    private Integer issueTime;
}
