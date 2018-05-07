package com.olamas.socialmedia.aggregator.twitter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TwitterFilterService {

    @Autowired
    private TwitterConfigRepository twitterConfigRepository;

    public TwitterFilter setNewFilter(final TwitterFilter twitterFilter){
        twitterConfigRepository.addNewFilter(twitterFilter);
        return twitterFilter;
    }
}
