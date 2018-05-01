package com.olamas.socialmedia.aggregator.login;

import java.util.List;

public interface UserService {

    SocialMediaUser save(SocialMediaUser user);

    SocialMediaUser findByUsername(String username);

    List<SocialMediaUser> findAll();

    void delete(Long userId);
}
