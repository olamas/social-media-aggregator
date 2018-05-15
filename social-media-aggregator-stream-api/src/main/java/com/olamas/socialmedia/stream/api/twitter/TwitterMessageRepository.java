package com.olamas.socialmedia.stream.api.twitter;

import org.springframework.data.jpa.repository.JpaRepository;
import com.olamas.socialmedia.twitter.TwitterMessage;

public interface TwitterMessageRepository extends JpaRepository<TwitterMessage, Long> {

}

