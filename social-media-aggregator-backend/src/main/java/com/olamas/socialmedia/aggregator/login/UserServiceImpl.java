package com.olamas.socialmedia.aggregator.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public SocialMediaUser save(SocialMediaUser socialMediaUser) {
        socialMediaUser.setPassword(bCryptPasswordEncoder.encode(socialMediaUser.getPassword()));
        userRepository.save(socialMediaUser);
        return  socialMediaUser;
    }

    @Override
    public SocialMediaUser findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<SocialMediaUser> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void delete(Long userId) {
        userRepository.deleteById(userId);
    }
}
