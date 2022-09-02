package org.example.gatewaydemo.satoken;

import cn.dev33.satoken.fun.SaParamFunction;
import cn.dev33.satoken.reactor.filter.SaReactorFilter;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.router.SaRouterStaff;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SaTokenConfigure {

    @Bean
    public SaReactorFilter getSaReactorFilter() {
        return new SaReactorFilter()
                // 拦截地址
                .addInclude("/**")
                // 开放地址
                .addExclude("/favicon.ico")
                // 鉴权方法
                .setAuth(obj -> {
                    // 登录校验 -- 拦截所有路由，并排除/user/doLogin 用于开放登录
                    SaRouter.match("/**", "/user/doLogin", new SaParamFunction<SaRouterStaff>() {
                        @Override
                        public void run(SaRouterStaff r) {
                            StpUtil.checkLogin();
                        }
                    });

                    // 权限认证 -- 不同模块, 校验不同权限
//                    SaRouter.match("/user/**", r -> StpUtil.checkPermission("user"));
                    SaRouter.match("/admin/**", r -> StpUtil.checkPermission("admin"));
                    SaRouter.match("/goods/**", r -> StpUtil.checkPermission("goods"));
                    SaRouter.match("/orders/**", r -> StpUtil.checkPermission("orders"));
                })
                // 异常处理方法, 每次 setAuth 异常时进入
                .setError(e -> {
                    return SaResult.error(e.getMessage());
                });
    }
}
