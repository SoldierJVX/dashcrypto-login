package com.dashcrypto.login.controllers;

import com.dashcrypto.login.models.Login;
import com.dashcrypto.login.models.entities.LoginEntity;
import com.dashcrypto.login.services.LoginService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

import static com.dashcrypto.login.Utils.Messages.LOGIN_DELETED;
import static com.dashcrypto.login.Utils.Messages.LOGIN_NOT_FOUND;

@OpenAPIDefinition(
        info = @Info(
                title = "API Login - DashCrypto Application",
                version = "1.0",
                description = "API REST Login",
                contact = @Contact(
                        name = "João Silva",
                        email = "jv_rss@hotmail.com"
                ),
                license = @License(
                        url = "http://www.apache.org/licenses/LICENSE-2.0.html",
                        name = "Apache 2.0"
                )
        )
)
@Slf4j
@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "*", maxAge = 3600)
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/{uuid}")
    @Operation(summary = "Returns one Login")
    public ResponseEntity<Object> getLogin(@PathVariable UUID uuid){
        log.info("getLogin called: {}", uuid);
        Optional<LoginEntity> loginEntityOptional = loginService.findById(uuid);

        if(!loginEntityOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(LOGIN_NOT_FOUND);
        }

        return ResponseEntity.status(HttpStatus.OK).body(loginEntityOptional.get());
    }

    @PostMapping
    @Operation(summary = "Create a new Login")
    public ResponseEntity<Object> createLogin(@Valid @RequestBody Login login){
        log.info("createLogin called: {}", login.getEmail());
        LoginEntity loginEntity = new LoginEntity();
        BeanUtils.copyProperties(login, loginEntity);

        loginEntity = loginService.create(loginEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(loginEntity);
    }

    @PutMapping("/{uuid}")
    @Operation(summary = "Update Login")
    public ResponseEntity<Object> updateLogin(@PathVariable UUID uuid, @Valid @RequestBody Login login){
        log.info("updateLogin called: {}", uuid);
        LoginEntity loginEntity = loginService.update(uuid, login);

        return ResponseEntity.status(HttpStatus.OK).body(loginEntity);
    }

    @DeleteMapping("/{uuid}")
    @Operation(summary = "Delete Login")
    public ResponseEntity<Object> deleteLogin(@PathVariable UUID uuid){
        log.info("deleteLogin called: {}", uuid);
        Optional<LoginEntity> loginEntityOptional = loginService.findById(uuid);

        if(!loginEntityOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(LOGIN_NOT_FOUND);
        }

        loginService.delete(uuid);

        return ResponseEntity.status(HttpStatus.OK).body(LOGIN_DELETED);
    }

}
