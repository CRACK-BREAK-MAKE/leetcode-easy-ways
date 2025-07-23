package com.crack.snap.make.medium.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mohan Sharma
 */
public class RestoreIpAddresses {
    public List<String> restoreIpAddresses(String s) {
        if (s == null || s.isEmpty() || s.length() < 4 || s.length() > 12) {
            return List.of(); // Invalid input for IP address
        }
        var result = new ArrayList<String>();
        generateIpAddressesBacktracking(s, 0, new ArrayList<>(),result);
        return result;
    }

    private void generateIpAddressesBacktracking(String s, int start, List<String> current, List<String> result) {
        if (start == s.length()) {
            if (current.size() == 4) {
                result.add(String.join(".", current));
            }
            return;
        }

        if (current.size() >= 4) return;

        // Try all possible lengths for current part of (1-3 digits)
        // meaning if start = 0, I can try for index 0, 1, 2. Similarly, if start = 2, I can try for 2, 3, 4
        // so if start is i, I can try 3 digits less than i + 3 since they should be between [0 - 255]
        for (var end = start; end < s.length() && end < start + 3; end++) {
            var segment = s.substring(start, end + 1);
            if (isValidSegment(segment)) {
                current.add(segment);
                generateIpAddressesBacktracking(s, end + 1, current, result);
                current.removeLast();
            }
        }
    }

    private boolean isValidSegment(String segment) {
        if (segment.charAt(0) == '0' && segment.length() > 1) return false;
        int num = Integer.parseInt(segment);
        return num <= 255;
    }

    public static void main(String[] args) {
        var obj = new RestoreIpAddresses();
        System.out.println(obj.restoreIpAddresses("25525511135")); // Output ["255.255.11.135","255.255.111.35"]
        System.out.println(obj.restoreIpAddresses("0000")); // Output ["0.0.0.0"]
        System.out.println(obj.restoreIpAddresses("010010")); // Output ["0.10.0.10","0.100.1.0"]
        System.out.println(obj.restoreIpAddresses("101023")); // Output ["1.0.10.23","1.0.102.3","10.1.0.23","10.10.2.3","101.0.2.3"]
        System.out.println(obj.restoreIpAddresses("19216811")); // Output [1.92.168.11, 19.2.168.11, 19.21.68.11, 19.216.8.11, 19.216.81.1, 192.1.68.11, 192.16.8.11, 192.16.81.1, 192.168.1.1]
    }
}
