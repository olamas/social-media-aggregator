package com.olamas.socialmedia.aggregator.twitter;

import com.olamas.socialmedia.aggregator.exception.ConfigServerException;
import com.olamas.socialmedia.aggregator.exception.InvalidTweetFilterException;
import com.olamas.socialmedia.aggregator.exception.ServerErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/twitter/")
public class TwitterFilterController {

    @Autowired
    private TwitterFilterService twitterFilterService;

    @RequestMapping(path = "filter", method = RequestMethod.POST,produces = "application/json")
    public TwitterFilter create(@RequestBody TwitterFilter filter){
        try {
            TwitterFilter twitterFilter = twitterFilterService.setNewFilter(filter);
            if(twitterFilter == null)
                throw new InvalidTweetFilterException();

            return twitterFilter;

        } catch (ConfigServerException e) {
            throw new ServerErrorException();
        }
    }
}
