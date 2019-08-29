package com.example.service.layui;


import com.example.bean.UserInfoListVo;
import com.example.bean.basic.LayuiPageQueryResult;
import com.example.bean.basic.MessageResult;
import com.example.bean.exampleBean.UserInfo;

public interface LayuiBaseQueryApi {
    LayuiPageQueryResult<UserInfo> layuiQueryUserInfo(UserInfoListVo userInfo);

    MessageResult layuiuUpdateUserInfo(UserInfoListVo userInfo);

    MessageResult ftpDowmLoad();
}
