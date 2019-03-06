package com.shuyan.learn.cors.annotation.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author will
 */
@RestController
public class AnnotationController {

    @RequestMapping("/test")
    @CrossOrigin(value = {"localhost"})
    public String test(){
        return "ok";
    }
}
