package effective.mobile.code.services.impl;

import effective.mobile.code.entities.enums.Role;
import effective.mobile.code.entities.User;
import effective.mobile.code.repositories.UserRepository;
import effective.mobile.code.services.UserServices;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserServices {
    private final UserRepository userRepository;

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUser(String login) {
        return userRepository.findByLogin(login).get();
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }
}
