package com.smallfinance.services;

import com.smallfinance.dtos.inputs.AccountDetailsInput;
import com.smallfinance.dtos.inputs.LoginInput;
import com.smallfinance.dtos.outputs.LoginOutput;
import com.smallfinance.dtos.outputs.UserOutput;
import com.smallfinance.entities.AccountDetails;
import com.smallfinance.entities.User;
import io.micronaut.http.server.multipart.MultipartBody;

import java.util.List;
import java.util.UUID;

public interface UserService {
    User addUser(AccountDetailsInput input);
    UserOutput getById(UUID id);

    List<UserOutput> getAll();
}
