package org.game.domains.users.adapter.input.rest;

import lombok.RequiredArgsConstructor;
import org.game.domains.users.adapter.input.rest.dto.SigninDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SigninApi {

    @PostMapping("/sign-in")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> getSignin(
            @RequestBody SigninDTO request
    ) {
        System.out.println(request);
        return ResponseEntity.ok().build();
    }
}
