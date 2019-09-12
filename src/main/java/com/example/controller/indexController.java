package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author by cld
 * @date 2019/8/29  14:35
 * @description:
 */
@Controller
public class indexController {
    @RequestMapping("")
    public String index(ModelAndView mav, HttpServletRequest request){
        return "index2";
    }
}
