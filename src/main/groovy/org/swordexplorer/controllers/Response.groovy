package org.swordexplorer.controllers

/**
 * Created by lee on 6/18/17.
 */

class Response<T> {
    Boolean success
    T data
    String errorMessage
    Map<String, String> links
}