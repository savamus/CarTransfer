package org.example.tranfercar;

public record Result<T>(T data, boolean success, String error) {
    public static <T> Result<T> success(T data) {
        return new Result<>(data, true, "");
    }

    public static <T> Result<T> failure(String message) {
        return new Result<>(null, false, message);
    }

}

