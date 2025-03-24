package com.crack.snap.make.medium.array;

import java.util.ArrayList;
import java.util.List;

/**
 * Encode and Decode Strings
 *
 * Intuition:
 * For encoding, we use a delimiter-based approach where each string is encoded as:
 *    "length#string"
 * This allows us to accurately store and retrieve strings, even those containing the delimiter '#'.
 *
 * Encoding Process:
 * 1. For each string in the list:
 *    - Record its length.
 *    - Append a delimiter ('#').
 *    - Append the actual string.
 *
 * Example:
 * Input: ["abc", "def#ghi", "jkl"]
 * Encoded: "3#abc7#def#ghi3#jkl"
 *
 * Decoding Process:
 * 1. Parse the length by reading characters until '#'.
 * 2. Extract the next 'length' characters as the original string.
 * 3. Repeat until the entire encoded string is processed.
 *
 * Time and Space Complexity Analysis:
 *
 * Encoding:
 * Loop Iteration (for each string in strs):
 * - This loop runs 'n' times—once for each string in the input list.
 *
 * For each string 's':
 * - s.length() – O(1) (length of a string in Java is precomputed).
 * - encoded.append(s.length()) – O(log₁₀(length)), but since the maximum length is Integer.MAX_VALUE (~2.14 billion), this is O(1) in practice.
 * - encoded.append("#") – O(1) (a single character append).
 * - encoded.append(s) – O(len(s)) (time to copy all characters of the string).
 *
 * Total work for each string: O(1) + O(1) + O(len(s)) = O(len(s)).
 * Summing over all strings: T(n) = O(len(s₁) + len(s₂) + ... + len(sₙ)) = O(m), where 'm' is the total length of all input strings.
 *
 * Decoding:
 * - We iterate through the encoded string once, parsing the length and extracting substrings.
 * - Each character is processed once, resulting in O(m) time.
 *
 * Space Complexity (Both Methods):
 * - We require space proportional to the output: O(m) for the encoded string and O(m + n) for the decoded list (m: character data, n: metadata for storage).
 * @author Mohan Sharma
 */
public class EncodeDecodeString {

    /**
     * Encodes a list of strings to a single string.
     *
     * Intuition:
     * 1. The challenge is to encode a list of strings in a way that we can uniquely decode them later.
     * 2. A naive approach using `String.join()` and `split()` fails when strings contain the delimiter (e.g., '#').
     * 3. Instead, we prefix each string with its length and a separator (e.g., "3#abc").
     * 4. This allows us to know exactly where each string starts and ends, even if they contain special characters.
     *
     * Example:
     * Input: ["abc", "def#ghi", "jkl"]
     * Encoded: "3#abc7#def#ghi3#jkl"
     *
     * Loop Iteration (for each string in strs):
     * - This loop runs 'n' times—once for each string in the input list.
     *
     * For each string 's':
     * - s.length() – O(1) (length of a string in Java is precomputed).
     * - encoded.append(s.length()) – O(log₁₀(length)), but since the maximum length is Integer.MAX_VALUE (~2.14 billion), this is O(1) in practice.
     * - encoded.append("#") – O(1) (a single character append).
     * - encoded.append(s) – O(len(s)) (time to copy all characters of the string).
     *
     * Total work for each string: O(1) + O(1) + O(len(s)) = O(len(s)).
     * Summing over all strings: T(n) = O(len(s₁) + len(s₂) + ... + len(sₙ)) = O(m), where 'm' is the total length of all input strings.
     *
     * Time Complexity: O(m) where m is the total length of all strings.
     * - Each character is processed once during encoding.
     *
     * Space Complexity: O(m + n)
     * - O(m) for the encoded output string.
     * - O(n) for metadata (lengths of n strings).
     */
    public String encode(List<String> strs) {
        var stringBuilder = new StringBuilder();
        for (var string : strs) {
            stringBuilder.append(string.length()).append("#").append(string);
        }
        return stringBuilder.toString();
    }

    /**
     * Decodes a single string back to a list of strings.
     *
     * Intuition:
     * 1. We read the encoded string by extracting the length prefix for each substring.
     * 2. The length tells us exactly how many characters to capture after the separator ('#').
     * 3. This approach prevents misinterpretation of embedded '#' characters.
     *
     * Example:
     * Encoded: "3#abc7#def#ghi3#jkl"
     * Output: ["abc", "def#ghi", "jkl"]
     * - We iterate through the encoded string once, parsing the length and extracting substrings.
     * - Each character is processed once, resulting in O(m) time.
     * Time Complexity: O(m)
     * - Each character in the encoded string is processed exactly once.
     *
     * Space Complexity: O(m + n)
     * - O(m) for the output list of decoded strings.
     * - O(n) for maintaining intermediate metadata.
     */
    public List<String> decode(String str) {
        var result = new ArrayList<String>();
        var wordLengthIndex = 0;
        while (wordLengthIndex <  str.length()) {
            var hashIndex = str.indexOf("#", wordLengthIndex);
            var wordLength = Integer.parseInt(str.substring(wordLengthIndex, hashIndex));
            var word = str.substring(hashIndex + 1, hashIndex + 1 + wordLength);
            result.add(word);
            wordLengthIndex = hashIndex + 1 + wordLength;
        }
        return result;
    }

    public static void main(String[] args) {
        var encodeDecodeString = new EncodeDecodeString();
        var encoded = encodeDecodeString.encode(List.of("hello", "world", "java"));
        System.out.println(encoded);
        var decoded = encodeDecodeString.decode(encoded);
        System.out.println(decoded);
    }
}
