package mate.academy.springintro.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mate.academy.springintro.dto.user.UserLoginRequestDto;
import mate.academy.springintro.dto.user.UserLoginResponseDto;
import mate.academy.springintro.dto.user.UserRegistrationRequestDto;
import mate.academy.springintro.dto.user.UserResponseDto;
import mate.academy.springintro.service.AuthenticationService;
import mate.academy.springintro.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication", description = "Endpoints for registration and authentication")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final UserService userService;

    @Operation(summary = "Register user", description = "Register a new user")
    @PostMapping("/registration")
    public UserResponseDto register(@RequestBody @Valid
                                    UserRegistrationRequestDto requestDto) {
        return userService.register(requestDto);
    }

    @Operation(summary = "Login user", description = "Authenticate user")
    @PostMapping("/login")
    public UserLoginResponseDto login(@RequestBody @Valid
                                          UserLoginRequestDto requestDto) {
        return authenticationService.authenticate(requestDto);
    }
}
