package mate.academy.springintro.service.impl;

import java.util.Set;
import lombok.RequiredArgsConstructor;
import mate.academy.springintro.dto.user.UserRegistrationRequestDto;
import mate.academy.springintro.dto.user.UserResponseDto;
import mate.academy.springintro.exception.EntityNotFoundException;
import mate.academy.springintro.exception.RegistrationException;
import mate.academy.springintro.mapper.UserMapper;
import mate.academy.springintro.model.Role;
import mate.academy.springintro.model.User;
import mate.academy.springintro.repository.RoleRepository;
import mate.academy.springintro.repository.UserRepository;
import mate.academy.springintro.service.AuthenticationService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new RegistrationException("User with email "
                    + requestDto.getEmail() + " already exists");
        }
        Role userRole = roleRepository.findByName(Role.RoleName.ROLE_USER)
                .orElseThrow(() -> new EntityNotFoundException("Role USER not found"));
        User user = userMapper.toModel(requestDto);
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        user.setRoles(Set.of(userRole));
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }
}
