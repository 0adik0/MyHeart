package com.health.my_heart.domain.model;

import javax.annotation.Nullable;

public class Result<T> {
    @Nullable
    private final Throwable throwable;
    @Nullable
    private final T data;
    private final Event event;

    public Result(Event event, @Nullable T data, @Nullable Throwable throwable) {
        this.event = event;
        this.data = data;
        this.throwable = throwable;
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(Event.SUCCESS, data, null);
    }

    public static <T> Result<T> loading() {
        return new Result<>(Event.LOADING, null, null);
    }

    public static <T> Result<T> error(Throwable t) {
        return new Result<>(Event.ERROR, null, t);
    }

    public Event getEvent() {
        return event;
    }

    @Nullable
    public T getData() {
        return data;
    }

    @Nullable
    public Throwable getThrowable() {
        return throwable;
    }
}
