package com.programmersstudy.springbootpractice.security_practice.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/security")
public class SecurityApiController {

    @GetMapping("/all")
    public Map<String, String> getInformation() {
        return Map.of(
                "data1", "모두가 볼 수 있는 정보1"
                , "data2", "모두가 볼 수 있는 정보2"
                , "data3", "모두가 볼 수 있는 정보3"
        );
    }

    @GetMapping("/admin")
    public Map<String, String> getInformationOnlyAdmin() {
        return Map.of(
                "data1", "ADMIN만 볼 수 있는 정보1"
                , "data2", "ADMIN만 볼 수 있는 정보2"
                , "data3", "ADMIN만 볼 수 있는 정보3"
        );
    }

    @GetMapping("/gold")
    public Map<String, String> getDTOOnlyGold() {
        return Map.of(
                "data1", "GOLD 만 볼 수 있는 정보1"
                , "data2", "GOLD 만 볼 수 있는 정보2"
                , "data3", "GOLD 만 볼 수 있는 정보3"
        );
    }
}
