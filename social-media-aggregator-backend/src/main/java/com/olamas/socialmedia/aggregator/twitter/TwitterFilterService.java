package com.olamas.socialmedia.aggregator.twitter;

import com.olamas.socialmedia.aggregator.exception.ConfigServerException;
import com.olamas.socialmedia.twitter.TwitterMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TwitterFilterService {

    @Autowired
    private TwitterConfigRepository twitterConfigRepository;

    @Autowired
    private TwitterRepository twitterRepository;

    public TwitterFilter setNewFilter(final TwitterFilter twitterFilter) throws ConfigServerException{
        if (twitterFilter == null || twitterFilter.getUserName() == null
                || (twitterFilter.getFromUser() == null && twitterFilter.getFilterText() == null))
            return null;

        twitterConfigRepository.addNewFilter(twitterFilter);
        return twitterFilter;
    }

    public List<TwitterMessage> getTweetsByUserName(String userFilter) {
        return twitterRepository.findTop20ByUserFilterOrderByIdStrDesc(userFilter);
    }
}
