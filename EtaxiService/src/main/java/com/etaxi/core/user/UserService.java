package com.etaxi.core.user;

import com.etaxi.core.exception.InvalidUsernameException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService implements UserDetailsService {

    UserRepository userRepository;

    public void createUser(User user) {
        try {
            userRepository.save(user);
        }
        catch (Exception exception) {
            throw new InvalidUsernameException("Username is Invalid");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent())
            return user.get();
        throw new UsernameNotFoundException("Email not found.");
    }
}
