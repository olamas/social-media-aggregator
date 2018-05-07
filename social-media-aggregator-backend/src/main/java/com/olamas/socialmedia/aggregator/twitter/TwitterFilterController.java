package com.olamas.socialmedia.aggregator.twitter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TwitterFilterController {

    @Autowired
    private TwitterFilterService twitterFilterService;

    @RequestMapping(value = "/twitter/filter", method = RequestMethod.POST)
    public TwitterFilter create(@RequestBody TwitterFilter filter){
        return twitterFilterService.setNewFilter(filter);
    }
}
