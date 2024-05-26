package org.game.domains.users.adapter.input.rest;

import lombok.RequiredArgsConstructor;
import org.game.domains.users.adapter.input.rest.dto.SignupDTO;
import org.game.domains.users.application.usecases.SignUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SignupApi {
    private final SignUseCase signUseCase;

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> getSignup(
            @RequestBody final SignupDTO request
    ) {
        signUseCase.signup(request.convert());

        return ResponseEntity.ok().build();
    }
}
