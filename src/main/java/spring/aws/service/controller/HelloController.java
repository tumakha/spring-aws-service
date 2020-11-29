package spring.aws.service.controller;

import com.amazonaws.xray.spring.aop.XRayEnabled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@XRayEnabled
public class HelloController {

    @GetMapping("/v1/hello/get")
    public String getHello() {
        return "Getting back...";
    }

    @PostMapping("/v1/hello/post")
    public String postHello() {
        return "Posting back...";
    }

}
