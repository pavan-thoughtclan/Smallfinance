package com.tc.training.service.impl;


import com.tc.training.dtos.inputdto.AccountDetailsInputDto;
import com.tc.training.dtos.outputdto.UserOutputDto;
import com.tc.training.mapper.UserMapper;
import com.tc.training.model.AccountDetails;
import com.tc.training.model.User;
import com.tc.training.repo.AccountDetailsRepository;
import com.tc.training.repo.UserRepository;
import com.tc.training.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.Period;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
@Log4j2
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountDetailsRepository accountRepository;

    @Autowired
    private UserMapper userMapper;




    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%&*";


    public Mono<User> addUser(AccountDetailsInputDto accountDetailsInputDto) {

        return calculateAge(accountDetailsInputDto.getDob())
                .flatMap(age -> {
                    User user = userMapper.accountInputDtoToUser(accountDetailsInputDto);
                    user.setAge(age);
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
                    UserOutputDto dto = userMapper.userToUserDto(user);
                    return dto;
                })
                .switchIfEmpty(Flux.empty());

    }

    @Override
    public Mono<UserOutputDto> getById(UUID id) {
        return userRepository.findById(id)
                .map(user -> userMapper.userToUserDto(user));
    }


    public static Mono<Integer> calculateAge(LocalDate dob) {
        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(dob, currentDate);
        return Mono.just(period.getYears());
    }

    public static Mono<String> generateRandomPassword() {

            StringBuilder password = new StringBuilder();
            Random random = new SecureRandom();

           return Flux.range(0, 9)
                    .map(i -> {
                        int randomIndex = random.nextInt(CHARACTERS.length());
                        return CHARACTERS.charAt(randomIndex);
                    })
                    .collect(StringBuilder::new,StringBuilder::append)
                   .map(StringBuilder::toString);

    }
}
