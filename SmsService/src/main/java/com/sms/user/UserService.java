package com.sms.user;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

    UserRepository userRepository;

    public User loadByModeIdAndMode(Integer modeId, UserSmsMode mode) {

        Optional<User> user = userRepository.findByModeIdAndMode(modeId, mode);

        return user.orElse(null);

    }

    public void createUser(User user) {
        userRepository.save(user);
    }

}
