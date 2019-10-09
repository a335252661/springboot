package com.example.controller.elementUI;

import com.example.bean.UserInfoListVo;
import com.example.service.HelloServiceApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
@Api(description = "用户接口")
@Controller
@RequestMapping("element")
public class ElementUiIndexController {

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
//        return "elementUI/elementUIindex1";
        return "elementUI/index-elementui";
    }


    @ApiOperation(value = "新增用户" ,  notes="新增注册")
    @RequestMapping(value="/createUser",method=RequestMethod.POST,consumes= MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody   //返回的是内容
    public String createUser(UserInfoListVo userInfo){
//        System.out.println("createUser:::"+userInfo.toString());
        return "ok";
    }


}
