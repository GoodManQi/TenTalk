package top.tentalk.web.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.tentalk.auth.annotation.RequiredLogin;
import top.tentalk.business.service.IUserService;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @Resource
    private transient IUserService userService;

    @GetMapping("login")
    public String login() {
        return userService.Sign();
    }

    @RequiredLogin
    @PostMapping("check")
    public String check() {
        return "ok";
    }

}
