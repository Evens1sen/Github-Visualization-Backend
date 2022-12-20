package com.project.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class IssueControllerTest {

    @Autowired
    IssueController issueController;

    @Test
    void test(){
        System.out.println(issueController.convertTime(643));
    }
}