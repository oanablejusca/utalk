package com.utalk.model;


import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

public class ModelUtils {


    private ModelUtils() {
    }

    static boolean checkIfNullEquals(Object originalObject, Object comparingObject) {
        return originalObject == null ? comparingObject == null : originalObject.equals(comparingObject);
    }


    private static void checkFieldAnnotations(Object object, Predicate<Field> filterPredicate) {
        checkNullParameters(object, filterPredicate);
        Arrays.stream(object.getClass().getDeclaredFields())
                .filter(filterPredicate)
                .forEach(field -> {
                    boolean isAccessible = field.isAccessible();
                    boolean isNull = true;
                    try {
                        field.setAccessible(true);
                        isNull = field.get(object) == null;
                    } catch (IllegalAccessException exception) {
                        System.out.println("Failed to access field set accessible: " + exception.getMessage());
                    }

                    field.setAccessible(isAccessible);
                    if (isNull) {
                        System.out.println("Null parameter not allowed");
                    }
                });
    }

    public static boolean doesObjectContainMethod(Object object, String methodName) {
        return Arrays.stream(object.getClass().getDeclaredMethods())
                .anyMatch(method -> method.getName().equals(methodName));
    }

    public static Class getParameterType(Object object, String methodName) {
        Optional<Method> method = Arrays.stream(object.getClass().getDeclaredMethods())
                .filter(method1 -> method1.getName().equals(methodName))
                .findFirst();

        if (method.isPresent()) {
            return method.get().getParameterTypes()[0];
        }
        System.out.println("The requested method is not present");
        return null;
    }


    public static void checkNullFields(Object object) {
        checkNullParameters(object);
        checkFieldAnnotations(
                object,
                field -> (field.getAnnotation(NotNull.class) != null)
        );
    }

    public static void checkNullParameters(Object... objects) {
        if (Arrays.stream(objects).anyMatch(Objects::isNull)) {
            System.out.println("Null parameter not allowed");
        }
    }

}

