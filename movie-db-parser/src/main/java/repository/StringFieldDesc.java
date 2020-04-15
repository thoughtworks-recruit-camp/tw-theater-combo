package repository;

import lombok.Getter;


import java.lang.reflect.Field;
import java.lang.reflect.Method;

@Getter
public class StringFieldDesc {
    private final Field field;
    private final Method getter;
    private final int minLength;
    private final int maxLength;

    public StringFieldDesc(Field field, Method getter, int minLength, int maxLength) {
        this.field = field;
        this.getter = getter;
        this.minLength = minLength;
        this.maxLength = maxLength;
    }
}
