package com.dashcrypto.login.services;


import com.dashcrypto.login.models.Login;
import com.dashcrypto.login.models.entities.LoginEntity;
import com.dashcrypto.login.repositories.LoginRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.util.Optional;
import java.util.UUID;

import static com.dashcrypto.login.Utils.Messages.LOGIN_NOT_FOUND;

@Service
public class LoginService {

    private final LoginRepository loginRepository;

    public LoginService(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    public Optional<LoginEntity> findById(UUID uuid) {
        return loginRepository.findById(uuid);
    }

    public LoginEntity create(LoginEntity login) {
        return loginRepository.save(login);
    }

    public LoginEntity update(UUID uuid, Login login) {
        Optional<LoginEntity> loginEntity = loginRepository.findById(uuid);

        if (loginEntity.isPresent()) {
            LoginEntity loginModel = loginEntity.get();

            BeanUtils.copyProperties(login, loginModel);
            return loginRepository.save(loginModel);
        }

        throw new NotFoundException(LOGIN_NOT_FOUND);
    }

    public void delete(UUID uuid) {
        loginRepository.deleteById(uuid);
    }

}
