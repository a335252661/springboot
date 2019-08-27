package com.example.service.layui;


import com.example.bean.UserInfo;
import com.example.bean.UserInfoListVo;
import com.example.bean.basic.LayuiPageQueryResult;
import com.example.bean.basic.MessageResult;

public interface LayuiBaseQueryApi {
    LayuiPageQueryResult<UserInfo> layuiQueryUserInfo(UserInfoListVo userInfo);

    MessageResult layuiuUpdateUserInfo(UserInfoListVo userInfo);

    MessageResult ftpDowmLoad();
}
