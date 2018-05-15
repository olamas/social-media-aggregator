package com.olamas.socialmedia.aggregator.twitter;

import com.olamas.socialmedia.aggregator.exception.ConfigServerException;
import com.olamas.socialmedia.aggregator.exception.InvalidTweetFilterException;
import com.olamas.socialmedia.aggregator.exception.ServerErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.olamas.socialmedia.twitter.TwitterMessage;

import java.util.List;


@Controller
@RequestMapping("/twitter/")
public class TwitterFilterController {

    @Autowired
    private TwitterFilterService twitterFilterService;

    @RequestMapping(path = "filter", method = RequestMethod.POST,produces = "application/json")
    public ResponseEntity<TwitterFilter> create(@RequestBody TwitterFilter filter){
        try {
            TwitterFilter twitterFilter = twitterFilterService.setNewFilter(filter);
            if(twitterFilter == null)
                throw new InvalidTweetFilterException();

            return new ResponseEntity<TwitterFilter>(twitterFilter,HttpStatus.OK);

        } catch (ConfigServerException e) {
            throw new ServerErrorException();
        }
    }

    @RequestMapping(value = "tweets/{userName}", method = RequestMethod.GET,produces = "application/json")
    public ResponseEntity<List<TwitterMessage>> tweetsByUserName(@PathVariable("userName") String userName){
        List<TwitterMessage> tweets = twitterFilterService.getTweetsByUserName(userName);
        if(tweets.isEmpty()){
            return new ResponseEntity<List<TwitterMessage>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<TwitterMessage>>(tweets,HttpStatus.OK);
    }
}
