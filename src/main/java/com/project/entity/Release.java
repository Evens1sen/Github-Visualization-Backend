package com.project.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;
import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

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
public class Release implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "release_id", type = IdType.AUTO)
    private Integer releaseId;

    private LocalDateTime createdAt;

    private LocalDateTime publishedAt;


}
