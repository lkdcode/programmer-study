package org.example.domain.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyController {
    @GetMapping("/main")
    public String getMainPage(){
        return "main";
    }
    @GetMapping("/my")
    public String  getMyPage(){
        return "my";
    }
}
