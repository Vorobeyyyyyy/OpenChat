package com.vorobeyyyyyy.openchat.security;

import com.vorobeyyyyyy.openchat.model.domain.User;
import com.vorobeyyyyyy.openchat.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class UserAuditorAware implements AuditorAware<User> {

    private UserService userService;

    @Override
    @NonNull
    public Optional<User> getCurrentAuditor() {
        return Optional.ofNullable(userService.getCurrentUser());
    }
}
