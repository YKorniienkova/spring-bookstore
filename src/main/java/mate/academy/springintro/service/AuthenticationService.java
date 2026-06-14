package mate.academy.springintro.service;

import mate.academy.springintro.dto.user.UserLoginRequestDto;
import mate.academy.springintro.dto.user.UserLoginResponseDto;

public interface AuthenticationService {
    UserLoginResponseDto authenticate(UserLoginRequestDto requestDto);
}
