package com.davidparry.chuck;

public record ApiErrorResponse(String status, String error, String message, String path) {
}
