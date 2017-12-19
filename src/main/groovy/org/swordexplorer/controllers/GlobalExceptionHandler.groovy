package org.swordexplorer.controllers

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.swordexplorer.exceptions.BadRequestException

@ControllerAdvice
@RestController
class GlobalExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException)
    Response<String> handleBaseException(BadRequestException e) {
        handle(e)
    }

    @ExceptionHandler(Exception)
    Response<String> handleException(Exception e) {
        handle(e)
    }

    Response<String> handle(Exception e) {
        new Response<String>(success: false, data: null, links: [:], errorMessage: e.localizedMessage)
    }

}