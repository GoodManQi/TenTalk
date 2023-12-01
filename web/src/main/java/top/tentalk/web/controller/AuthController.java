package top.tentalk.web.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.tentalk.business.pojo.query.AccountSignQuery;
import top.tentalk.common.pojo.CommonResponse;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("accountPasswordRegistration")
    public CommonResponse<String> accountPasswordRegistration(@Validated @RequestBody AccountSignQuery accountSignQuery) {
        System.out.println(accountSignQuery.toString());
        return new CommonResponse<>();
    }

}
