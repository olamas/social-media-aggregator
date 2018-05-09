package com.olamas.socialmedia.aggregator.twitter;

import com.olamas.socialmedia.aggregator.exception.ConfigServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TwitterFilterService {

    @Autowired
    private TwitterConfigRepository twitterConfigRepository;

    public TwitterFilter setNewFilter(final TwitterFilter twitterFilter) throws ConfigServerException{
        if (twitterFilter == null || twitterFilter.getUserName() == null
                || (twitterFilter.getFromUser() == null && twitterFilter.getFilterText() == null))
            return null;

        twitterConfigRepository.addNewFilter(twitterFilter);
        return twitterFilter;
    }
}
