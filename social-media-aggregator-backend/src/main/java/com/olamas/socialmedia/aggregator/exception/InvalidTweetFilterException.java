package com.olamas.socialmedia.aggregator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Invalid Filter value")
public class InvalidTweetFilterException extends RuntimeException {
}
