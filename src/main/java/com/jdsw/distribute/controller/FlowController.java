package com.jdsw.distribute.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/flow")
public class FlowController {
    /**
     * 请假流程列表
     */
    @RequestMapping("/leavelist")
    public String LeaveList(){
        System.out.println("flow------------");
        return "/flow/form_basic";

    }
    @RequestMapping("/blog")
    public String blog(){
        System.out.println("blog------------");
        return "/flow/blog";

    }
}
