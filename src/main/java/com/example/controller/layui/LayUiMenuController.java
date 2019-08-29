package com.example.controller.layui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author by cld
 * @date 2019/8/27  16:09
 * @description:
 */
@Controller
@RequestMapping("menuManagement")
public class LayUiMenuController {

    @RequestMapping("")
    private String menuManagement(){
        return "html/layUiMenuManagement";
    }
}
