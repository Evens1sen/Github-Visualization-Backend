package com.project.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;
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
public class Commit implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "commit_id", type = IdType.AUTO)
    private Integer commitId;

    private String sha;

    private String author;

    private LocalDateTime createdAt;


}
