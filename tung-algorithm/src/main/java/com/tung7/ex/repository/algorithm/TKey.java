package com.tung7.ex.repository.algorithm;

import java.io.Serializable;
import java.util.Comparator;

/**
 * TODO Complete The Description!
 *
 * @author Tung
 * @version 1.0
 * @date 2017/1/28.
 * @update
 */
public class TKey implements Serializable, Comparable<TKey> {
    private long key;

    public long getKey() {
        return key;
    }

    public TKey(long key){
        this.key = key;
    }

    @Override
    public int compareTo(TKey o) {
        return new Long(key).compareTo(o.getKey());
    }

    @Override
    public String toString() {
        return String.valueOf(key);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TKey tKey = (TKey) o;
        return key == tKey.key;
    }

    @Override
    public int hashCode() {
        return (int) (key ^ (key >>> 32));
    }
}
