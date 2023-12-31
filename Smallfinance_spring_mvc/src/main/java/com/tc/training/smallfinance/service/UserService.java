package com.tc.training.smallfinance.service;

import com.tc.training.smallfinance.dtos.inputs.AccountDetailsInputDto;
import com.tc.training.smallfinance.dtos.outputs.UserOutputDto;
import com.tc.training.smallfinance.model.User;

import java.util.List;
import java.util.UUID;


public interface UserService {

    public User addUser(AccountDetailsInputDto accountDetailsInputDto);

    List<UserOutputDto> getAll();

    UserOutputDto getById(UUID id);

}
