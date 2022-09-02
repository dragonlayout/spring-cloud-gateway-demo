package org.example.userservice.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/user/")
public class UserController {

    @RequestMapping(path = "doLogin")
    public SaResult doLogin(String username, String password) {
        if ("chenlong".equals(username) && "123456".equals(password)) {
            StpUtil.login(10001);
            SaTokenInfo saTokenInfo = StpUtil.getTokenInfo();
            return SaResult.data(saTokenInfo);
        }
        return SaResult.error();
    }

    @RequestMapping(path = "info")
    public String info() {
        return "chenlong";
    }

}
