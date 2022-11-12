package com.dg.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author TheFool
 */
@Slf4j
public class BeanHelper {

    public static <T, R> T copy(R source, Class<T> clazz, String... ignoreFields) {
        T instance = source == null ? null : newInstance(clazz);
        if (instance != null) {
            BeanUtils.copyProperties(source, instance, ignoreFields);
        }
        return instance;
    }

    public static <T, R> List<T> copy(List<R> source, Class<T> clazz, String... ignoreFields) {
        if (source == null) {
            return null;
        }
        if (source.isEmpty()) {
            return new ArrayList<>();
        }

        List<T> result = new ArrayList<>(source.size());
        for (R each : source) {
            T t = copy(each, clazz, ignoreFields);
            result.add(t);
        }

        return result;
    }

    public static <T> T newInstance(Class<T> clazz) {
        T result = null;
        try {
            result = clazz == null ? null : clazz.newInstance();
        } catch (Exception ex) {
            log.error("new instance error", ex);
        }
        return result;
    }

}