package mate.academy.springintro.service;

import mate.academy.springintro.dto.user.UserRegistrationRequestDto;
import mate.academy.springintro.dto.user.UserResponseDto;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto requestDto);
}
