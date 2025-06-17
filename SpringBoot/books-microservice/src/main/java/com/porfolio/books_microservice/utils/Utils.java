package com.porfolio.books_microservice.utils;

import java.util.function.Consumer;

public class Utils {

    public static <T> void updateFieldIfPresent(T value, Consumer<T> setter) {
        if (value != null) {
            setter.accept(value);
        }

    }

}
