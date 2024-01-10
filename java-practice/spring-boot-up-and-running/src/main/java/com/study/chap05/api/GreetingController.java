package com.study.chap05.api;

import com.study.chap05.domain.Greeting;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/greeting")
public class GreetingController {
    //    @Value("${greeting-name: Mirage}")
    //    private String name;
    //    @Value("${greeting-coffee: ${greeting-name} is drinking Cafe Ganador}")
    //    private String coffee;
    private final Greeting greeting;

    public GreetingController(Greeting greeting) {
        this.greeting = greeting;
    }

    @GetMapping
    String getGreeting() {
        return this.greeting.getName();
    }

    @GetMapping("/coffee")
    String getNameAndCoffee() {
        return this.greeting.getCoffee();
    }
}
