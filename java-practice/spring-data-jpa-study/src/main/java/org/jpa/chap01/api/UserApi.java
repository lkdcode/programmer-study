package org.jpa.chap01.api;

import lombok.RequiredArgsConstructor;
import org.jpa.chap01.dto.UserRequest;
import org.jpa.chap01.entity.UserEntity;
import org.jpa.chap01.repository.UserJpaRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserApi {
    private final UserJpaRepository userJpaRepository;

    @PostMapping
    public UserEntity createUser(
            @RequestBody UserRequest.CreateDTO request
    ) {
        final UserEntity userEntity = request.toEntity();
        return userJpaRepository.save(userEntity);
    }

    @GetMapping
    public List<UserEntity> getUserList() {
        return userJpaRepository.findAll();
    }

    @DeleteMapping
    public UserEntity deleteUser(
            @RequestBody final UserRequest.DeleteDTO request
    ) {
        return userJpaRepository.findById(request.userId())
                .orElse(null);
    }

    @PutMapping
    public UserEntity updateUser(
            @RequestBody final UserRequest.UpdateDTO request
    ) {
        Optional<UserEntity> byId = userJpaRepository.findById(request.userId());

        if (byId.isPresent()) {
            UserEntity userEntity = byId.get();
            userEntity.updateName(request.name());
            userEntity.updateAge(request.age());
            return userEntity;
        }

        return null;
    }
}