package com.project.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CommitControllerTest {

    @Autowired
    CommitController commitController;

    @Test
    void test(){
        var lst = commitController.topKActive(5);
        System.out.println(lst);
    }
}