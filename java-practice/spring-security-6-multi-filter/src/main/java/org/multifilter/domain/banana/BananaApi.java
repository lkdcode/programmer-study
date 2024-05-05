package org.multifilter.domain.banana;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/banana")
public class BananaApi {

    @GetMapping("/get")
    public String getBananaGetApi() {
        System.out.println("BananaApi.getBananaApi");
        return "I'm Banana Api";
    }

    @GetMapping("/secret")
    public String getBananaSecretApi() {
        System.out.println("BananaApi.getBananaSecretApi");
        return "Invalid";
    }
}