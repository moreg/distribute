package com.jdsw.distribute.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/chart")
public class ChartController {
    @RequestMapping("/barGraph")
    public String barGraph(){
        return "/chart/barGraph";
    }
}
