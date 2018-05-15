package com.olamas.socialmedia.aggregator.twitter;

import com.olamas.socialmedia.twitter.TwitterMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TwitterRepository  extends JpaRepository<TwitterMessage, Long>{
    public List<TwitterMessage> findTop20ByUserFilterOrderByIdStrDesc(String userFilter);
}
