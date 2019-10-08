package com.example.controller.layui;


import com.example.bean.UserInfoListVo;
import com.example.bean.basic.LayuiPageQueryResult;
import com.example.bean.basic.MessageResult;
import com.example.bean.exampleBean.UserInfo;
import com.example.service.layui.LayuiBaseQueryApi;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.FileOutputStream;
import java.io.InputStream;

@Controller
@RequestMapping("layUiBaseQuery")
public class LayUiBaseQueryController {

    @Resource
    private LayuiBaseQueryApi layuiBaseQueryApi;

    @RequestMapping("")
    private String layUiBaseQuery(){
        return "html/layUiBaseQuery";
//        return "html/layUiIndex";
    }

    @RequestMapping("layuiQuery")
    @ResponseBody
    public LayuiPageQueryResult<UserInfo> query(UserInfoListVo userInfo){
        System.out.println("开始查询");
        LayuiPageQueryResult<UserInfo> resultUserInfo = layuiBaseQueryApi.layuiQueryUserInfo(userInfo);
        return resultUserInfo;
    }


    /**
     * 行编辑修改更新
     * @param userInfo
     * @return
     */
    @RequestMapping("layuiUpdate")
    @ResponseBody
    public MessageResult layuiUpdate(UserInfoListVo userInfo){
        MessageResult result = layuiBaseQueryApi.layuiuUpdateUserInfo(userInfo);
        return result;
    }


    /**
     * 从ftp服务器下载文件
     * @return
     */
    @RequestMapping("downLoad")
    @ResponseBody
    public MessageResult downLoad(){
        MessageResult result = layuiBaseQueryApi.ftpDowmLoad();
        return result;
    }

    /**
     * 发送邮件
     * @return
     */
    @RequestMapping("sendMail")
    @ResponseBody
    public MessageResult sendMail(){
        MessageResult result = layuiBaseQueryApi.sendMail();
        return result;
    }

    @RequestMapping(value = "/uploadFile")
    @ResponseBody
    public MessageResult uploadFile(@RequestParam("file") MultipartFile file){
        FileOutputStream outfile = null;
        try {
            InputStream inputStream = file.getInputStream();

//            String name = file.getName();
            String name=file.getOriginalFilename();


            outfile = new FileOutputStream("D:\\uploadFile\\"+name);
            byte[] buf = new byte[8 * 1024];
            int len = 0;
            while ((len = inputStream.read(buf)) != -1) {
                outfile.write(buf, 0, len);
                outfile.flush();
            }

        }catch (Exception e){

        }finally {
            try{
                if(outfile != null){
                    outfile.close();
                }
            }catch(Exception e){
                System.out.println("关闭输出流错误！");
            }
        }

        MessageResult result = new MessageResult();
        result.setMessage("上传成功~！");

        return result;
    }

}
