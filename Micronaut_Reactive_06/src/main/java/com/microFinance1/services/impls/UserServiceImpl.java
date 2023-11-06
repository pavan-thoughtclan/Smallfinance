package com.microFinance1.services.impls;

import com.microFinance1.dtos.inputs.AccountDetailsInputDto;
import com.microFinance1.dtos.outputs.UserOutputDto;
import com.microFinance1.entities.User;
import com.microFinance1.mapper.UserMapper;
import com.microFinance1.repository.AccountRepository;
import com.microFinance1.repository.UserRepository;
import com.microFinance1.services.UserService;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.Period;
import java.util.Random;
import java.util.UUID;

@Singleton
public class UserServiceImpl implements UserService {
    @Inject
    private UserRepository userRepository;
    @Inject
    private AccountRepository accountRepository;
    @Inject
    private UserMapper userMapper;

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%&*";
    public Mono<User> addUser(AccountDetailsInputDto accountDetailsInputDto) {
        return calculateAge(accountDetailsInputDto.getDob())
                .flatMap(age -> {
                    User user = new User();
                    user.setAge(age);
                    user.setFirstName(accountDetailsInputDto.getFirstName());
                    user.setLastName(accountDetailsInputDto.getLastName());
                    user.setDob(accountDetailsInputDto.getDob());
                    user.setEmail(accountDetailsInputDto.getEmail());
                    user.setPanCardNumber(accountDetailsInputDto.getPanCardNumber());
                    user.setAadharCardNumber(accountDetailsInputDto.getAadharCardNumber());
                    user.setPhoneNumber(accountDetailsInputDto.getPhoneNumber());
                    return generateRandomPassword()
                            .map(password -> {
                                user.setPassword(password);
                                return user;
                            });
                })
                .flatMap(user -> userRepository.save(user));
    }

    @Override
    public Flux<UserOutputDto> getAll() {
        return userRepository.findAll()
                .map(user -> {
                    UserOutputDto dto = userMapper.mapToUserOutputDto(user);
                    return dto;
                })
                .switchIfEmpty(Flux.empty());
    }

    @Override
    public Mono<UserOutputDto> getById(UUID id) {
        return userRepository.findById(id)
                .map(user -> userMapper.mapToUserOutputDto(user));
    }

    public Mono<Integer> calculateAge(LocalDate dob) {
        return Mono.just(dob).flatMap(
                dateOfBirth -> {
                    LocalDate currentDate = LocalDate.now();
                    Period period = Period.between(dateOfBirth, currentDate);
                    return Mono.just(period.getYears());
                });
    }
    public Mono<String> generateRandomPassword() {
        return Mono.fromCallable(() -> {
            StringBuilder password = new StringBuilder();
            Random random = new SecureRandom();

            for (int i = 0; i < 9; i++) {
                int randomIndex = random.nextInt(CHARACTERS.length());
                password.append(CHARACTERS.charAt(randomIndex));
            }

            return password.toString();
        });
    }



}
