package com.olamas.socialmedia.aggregator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR, reason="Config Server error")
public class ServerErrorException extends RuntimeException {
}
