package com.olamas.socialmedia.aggregator.login;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<SocialMediaUser, Long> {

    SocialMediaUser findByUsername(String username);
}
