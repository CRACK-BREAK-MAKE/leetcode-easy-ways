package com.crack.snap.make.medium.binarysearch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mohan Sharma
 *
 * Problem: Design a time-based key-value data structure that can store multiple values for the same key at different time stamps
 * and retrieve the key's value at a certain timestamp.
 *
 * TimeBaseKeyValueStore() Initializes the object of the data structure.
 * void set(String key, String value, int timestamp) Stores the key key with the value value at the given time timestamp.
 * String get(String key, int timestamp) Returns a value such that set was called previously, with timestamp_prev <= timestamp.
 * If there are multiple such values, it returns the value associated with the largest timestamp_prev. If there are no values, it returns "".
 *
 * Intution: set is pretty straight forward, we need to store the value with the timestamp for a given key. Since there
 * can be multiple values for the same key we can use Map with key = String and value = will be a list. Again when we
 * retrieve the value we need to retrieve the right most value for the given timestamp, so we need to store the values
 * in a pair of (timestamp and value). Also, the constraint already mentions the timestamp will be in increasing order in set
 * so we need to get the value nearest to the target or the right most to the target, we can follow right most binary search
 */
public class TimeBaseKeyValueStore {

    private record Pair(Integer timestamp, String value) {};

    private final Map<String, List<Pair>> keyValueStore;

    public TimeBaseKeyValueStore() {
        keyValueStore = new HashMap<>();
    }

    public void set(String key, String value, int timestamp) {
        keyValueStore.computeIfAbsent(key, k -> new ArrayList<>()).add(new Pair(timestamp, value));
    }

    public String get(String key, int timestamp) {
        if (!keyValueStore.containsKey(key)) {
            return "";
        }
        List<Pair> values = keyValueStore.get(key);
        if (values.isEmpty() || values.getFirst().timestamp > timestamp) {
            return "";
        }
        if (values.getLast().timestamp <= timestamp) {
            return values.getLast().value;
        }
        var start = 0;
        var end = values.size() - 1;
        while (start < end) {
            var mid = start + (end - start + 1) / 2; // avoid infinite loop pattern of right most binary search
            if (values.get(mid).timestamp <= timestamp) {
                start = mid;
            } else {
                end = mid - 1;
            }
        }
        return values.get(start).value;
    }
}
