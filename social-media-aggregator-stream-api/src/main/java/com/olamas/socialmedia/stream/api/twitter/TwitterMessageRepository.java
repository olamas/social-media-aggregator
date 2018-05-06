package com.olamas.socialmedia.stream.api.twitter;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TwitterMessageRepository extends JpaRepository<TwitterMessage, Long> {

}

