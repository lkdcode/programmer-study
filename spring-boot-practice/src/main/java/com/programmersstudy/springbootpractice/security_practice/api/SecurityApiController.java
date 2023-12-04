package com.programmersstudy.springbootpractice.security_practice.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequestMapping("/security")
@RestController
@RequiredArgsConstructor
public class SecurityApiController {

    @GetMapping("/all")
    public Map<String, String> getInformation() {
        return Map.of(
                "data1", "모두의 정보1"
                , "data2", "모두의 정보2"
                , "data3", "모두의 정보3"
        );
    }

    @GetMapping("/admin")
    public Map<String, String> getInformationOnlyAdmin() {
        return Map.of(
                "data1", "Admin의 정보1"
                , "data2", "Admin의 정보2"
                , "data3", "Admin의 정보3"
        );
    }

    @GetMapping("/gold")
    public Map<String, String> getDTOOnlyGold() {
        return Map.of(
                "data1", "Gold의 정보1"
                , "data2", "Gold의 정보2"
                , "data3", "Gold의 정보3"
        );
    }
}
