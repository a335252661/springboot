package com.example.controller.layui;

import com.example.service.HelloServiceApi;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author by cld
 * @date 2019/8/12  11:12
 * @description:
 */
//@RestController    //该注解是 @Controller 和 @ResponseBody 注解的合体版
@Controller
@RequestMapping("layui")
public class LayUiIndexController {

//    @Autowired
//    private SystemProperties systemPr operties;

    @Resource
    private HelloServiceApi helloServiceApi;
    //HelloServiceImpl


    //Resource,Autowired 做bean的注入时使用

    @RequestMapping("hellos")  //和@RequestMapping("/elementUIindex.html") 都可以访问
    @ResponseBody   //返回的是内容
    public String fun(){
//        helloServiceApi.fun();
        return "elementUIindex.html Spring Boot!";
    }


    @RequestMapping("")
    public String index(ModelAndView mav, HttpServletRequest request){
        return "html/layUiIndex";
    }


}
