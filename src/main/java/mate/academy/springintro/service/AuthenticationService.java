package mate.academy.springintro.service;

import mate.academy.springintro.dto.user.UserLoginRequestDto;
import mate.academy.springintro.dto.user.UserLoginResponseDto;
import mate.academy.springintro.dto.user.UserRegistrationRequestDto;
import mate.academy.springintro.dto.user.UserResponseDto;

public interface AuthenticationService {
    UserResponseDto register(UserRegistrationRequestDto requestDto);

    UserLoginResponseDto authenticate(UserLoginRequestDto requestDto);
}
