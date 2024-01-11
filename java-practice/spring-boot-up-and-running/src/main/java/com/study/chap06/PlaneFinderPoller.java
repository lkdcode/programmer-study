package com.study.chap06;


import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@EnableScheduling
@Component
public class PlaneFinderPoller {
    private WebClient client = WebClient.create("http://localhost:7634/aircraft");
    private final RedisConnectionFactory connectionFactory;
    private final RedisOperations<String, Integer> redisOperations;

    public PlaneFinderPoller(RedisConnectionFactory connectionFactory,
                             RedisOperations<String, Integer> redisOperations) {
        this.connectionFactory = connectionFactory;
        this.redisOperations = redisOperations;
    }

//    @Scheduled(fixedRate = 1_000)
//    private void pollPlanes() {
//        connectionFactory.getConnection().serverCommands().flushDb();
//
//        client.get()
//                .retrieve()
//                .bodyToFlux(Integer.class)
//                .filter(plane -> !(plane == 0))
//                .toStream()
//                .forEach(ac -> redisOperations.opsForValue().set(ac.toString(), ac));
//
//        redisOperations.opsForValue()
//                .getOperations()
//                .keys("*")
//                .forEach(ac ->
//                        System.out.println(redisOperations.opsForValue().get(ac)));
//    }
}
