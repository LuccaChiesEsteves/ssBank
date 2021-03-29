package com.bank.account.utils;

import java.util.List;
import java.util.Set;

import static java.util.Objects.isNull;

public class SsBankUtils {

    @SuppressWarnings({"rawtypes"})
    public static boolean isEmpty(Object object) {
        if (object instanceof String) {
            return isNull(object) || ((String) object).trim().length() == 0 ? true
                    : false;
        }
        if (object instanceof List) {
            return isNull(object) || ((List) object).isEmpty();
        }
        if (object instanceof Set) {
            return isNull(object) || ((Set) object).isEmpty();
        }
        return isNull(object);
    }

    public static boolean isEmpty(Object[] objects) {
        if (!isNull(objects)) {
            for (Object object : objects) {
                if (isEmpty(object)) {
                    return true;
                }
            }
        }
        return false;
    }
}