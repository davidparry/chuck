package com.davidparry.chuck.common;

public record ApiErrorResponse(String status, String error, String message, String path) {
}
