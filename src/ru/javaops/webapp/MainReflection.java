package ru.javaops.webapp;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import ru.javaops.webapp.model.Resume;

public class MainReflection {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException,
            IllegalAccessException {
        Resume r = new Resume("uuid1");
        Method method = r.getClass().getMethod("toString");
        System.out.println(method.invoke(r));
    }
}