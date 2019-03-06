package com.shuyan.learn.cors.mvc.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author will
 */
@RestController
public class MvcController {

    @RequestMapping("/test")
    public String test(){
        return "ok";
    }
}
