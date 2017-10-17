package com.tung7.ex.repository.algorithm;

import java.io.Serializable;

/**
 * TODO Fill The Description!
 *
 * @author Tung
 * @version 1.0
 * @date 2017/1/28.
 * @update
 */

public class TValue implements Serializable {
    private String value;

    public TValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TValue tValue = (TValue) o;

        return value.equals(tValue.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
