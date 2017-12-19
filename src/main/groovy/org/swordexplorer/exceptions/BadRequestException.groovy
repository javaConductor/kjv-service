package org.swordexplorer.exceptions

/**
 * Created by lee on 6/18/17.
 */
class BadRequestException extends Exception {
    BadRequestException(String message) {
        super("Error in request: $message")
    }
}
