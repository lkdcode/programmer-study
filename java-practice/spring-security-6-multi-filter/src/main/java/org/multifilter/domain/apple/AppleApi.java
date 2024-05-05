package org.multifilter.domain.apple;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/apple")
public class AppleApi {

    @GetMapping("/get")
    public String getAppleGetApi() {
        System.out.println("AppleApi.getAppleApi");
        return "I'm Apple api";
    }

    @GetMapping("/secret")
    public String getAppleSecretApi() {
        System.out.println("AppleApi.getAppleSecretApi");
        return "Invalid";
    }

}
