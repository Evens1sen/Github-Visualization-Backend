package com.project.service.impl;

import com.project.entity.Issue;
import com.project.mapper.IssueMapper;
import com.project.service.IssueService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2022-12-16
 */
@Service
public class IssueServiceImpl extends ServiceImpl<IssueMapper, Issue> implements IssueService {

}
