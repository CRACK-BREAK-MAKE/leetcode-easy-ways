# Understanding String Template Variations

## The Key Variations in String Template

String Template patterns focus on string manipulation, pattern matching, and character-based algorithms:

1. **Character Frequency Matching**: Using maps/arrays to track character counts
2. **String Building/Manipulation**: Constructing strings efficiently
3. **Pattern Matching**: KMP, Rolling Hash, and simple pattern detection
4. **Palindrome Patterns**: Various approaches to palindrome problems
5. **String Transformation**: Converting between different string formats

## Develop an intuition about when to use each approach.

### 1. Character Frequency Matching

**When to use**: When you need to compare character distributions, find anagrams, or match patterns.

#### Template A: Exact Frequency Match
```java
public boolean isAnagram(String s1, String s2) {
    if (s1.length() != s2.length()) return false;
    
    // For lowercase letters only
    int[] count = new int[26];
    
    for (int i = 0; i < s1.length(); i++) {
        count[s1.charAt(i) - 'a']++;
        count[s2.charAt(i) - 'a']--;
    }
    
    for (int c : count) {
        if (c != 0) return false;
    }
    
    return true;
}
```

#### Template B: Frequency Map for Complex Characters
```java
public boolean areAnagrams(String s1, String s2) {
    Map<Character, Integer> map = new HashMap<>();
    
    // Count characters in first string
    for (char c : s1.toCharArray()) {
        map.put(c, map.getOrDefault(c, 0) + 1);
    }
    
    // Subtract characters from second string
    for (char c : s2.toCharArray()) {
        if (!map.containsKey(c)) return false;
        map.put(c, map.get(c) - 1);
        if (map.get(c) == 0) {
            map.remove(c);
        }
    }
    
    return map.isEmpty();
}
```

**Classic Problems**:
- **Valid Anagram**
- **Group Anagrams**
- **Find All Anagrams in a String**
- **Minimum Window Substring**

### 2. String Building and Manipulation

**When to use**: When constructing strings, especially with multiple operations or conditions.

#### Template A: StringBuilder for Efficiency
```java
public String buildString(String[] parts) {
    StringBuilder sb = new StringBuilder();
    
    for (String part : parts) {
        // Process each part
        if (shouldInclude(part)) {
            sb.append(part);
            if (needsSeparator()) {
                sb.append(',');
            }
        }
    }
    
    return sb.toString();
}
```

#### Template B: Character Array Manipulation
```java
public void reverseWords(char[] s) {
    // Reverse entire string first
    reverse(s, 0, s.length - 1);
    
    // Reverse each word
    int start = 0;
    for (int i = 0; i <= s.length; i++) {
        if (i == s.length || s[i] == ' ') {
            reverse(s, start, i - 1);
            start = i + 1;
        }
    }
}

private void reverse(char[] s, int left, int right) {
    while (left < right) {
        char temp = s[left];
        s[left] = s[right];
        s[right] = temp;
        left++;
        right--;
    }
}
```

**Classic Problems**:
- **Reverse Words in a String**
- **String Compression**
- **Remove Duplicates**
- **Valid Parentheses**

### 3. Pattern Matching Algorithms

**When to use**: When searching for patterns in text, especially with multiple occurrences.

#### Template A: Simple Pattern Search
```java
public List<Integer> findPattern(String text, String pattern) {
    List<Integer> results = new ArrayList<>();
    
    for (int i = 0; i <= text.length() - pattern.length(); i++) {
        if (matches(text, i, pattern)) {
            results.add(i);
        }
    }
    
    return results;
}

private boolean matches(String text, int start, String pattern) {
    for (int i = 0; i < pattern.length(); i++) {
        if (text.charAt(start + i) != pattern.charAt(i)) {
            return false;
        }
    }
    return true;
}
```

#### Template B: Rolling Hash (Rabin-Karp)
```java
public List<Integer> rabinKarp(String text, String pattern) {
    List<Integer> results = new ArrayList<>();
    int n = text.length(), m = pattern.length();
    
    if (m > n) return results;
    
    final int BASE = 256;
    final int MOD = 101;
    
    int patternHash = 0, textHash = 0;
    int h = 1; // BASE^(m-1) % MOD
    
    // Calculate h = pow(BASE, m-1) % MOD
    for (int i = 0; i < m - 1; i++) {
        h = (h * BASE) % MOD;
    }
    
    // Calculate hash for pattern and first window
    for (int i = 0; i < m; i++) {
        patternHash = (BASE * patternHash + pattern.charAt(i)) % MOD;
        textHash = (BASE * textHash + text.charAt(i)) % MOD;
    }
    
    // Slide the pattern over text one by one
    for (int i = 0; i <= n - m; i++) {
        if (patternHash == textHash) {
            // Check character by character
            if (text.substring(i, i + m).equals(pattern)) {
                results.add(i);
            }
        }
        
        // Calculate hash for next window
        if (i < n - m) {
            textHash = (BASE * (textHash - text.charAt(i) * h) + text.charAt(i + m)) % MOD;
            if (textHash < 0) textHash += MOD;
        }
    }
    
    return results;
}
```

### 4. Palindrome Patterns

**When to use**: For palindrome detection, construction, and related problems.

#### Template A: Expand Around Centers
```java
public String longestPalindrome(String s) {
    if (s == null || s.length() < 2) return s;
    
    int start = 0, maxLen = 0;
    
    for (int i = 0; i < s.length(); i++) {
        // Odd length palindromes
        int len1 = expandAroundCenter(s, i, i);
        // Even length palindromes
        int len2 = expandAroundCenter(s, i, i + 1);
        
        int len = Math.max(len1, len2);
        if (len > maxLen) {
            maxLen = len;
            start = i - (len - 1) / 2;
        }
    }
    
    return s.substring(start, start + maxLen);
}

private int expandAroundCenter(String s, int left, int right) {
    while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
        left--;
        right++;
    }
    return right - left - 1;
}
```

#### Template B: Manacher's Algorithm (Advanced)
```java
public String longestPalindromeManacher(String s) {
    // Preprocess string: "abc" -> "^#a#b#c#$"
    StringBuilder processed = new StringBuilder("^");
    for (char c : s.toCharArray()) {
        processed.append("#").append(c);
    }
    processed.append("#$");
    
    String T = processed.toString();
    int n = T.length();
    int[] P = new int[n]; // P[i] = length of palindrome centered at i
    int center = 0, right = 0;
    
    for (int i = 1; i < n - 1; i++) {
        int mirror = 2 * center - i;
        
        if (i < right) {
            P[i] = Math.min(right - i, P[mirror]);
        }
        
        // Try to expand palindrome centered at i
        try {
            while (T.charAt(i + (1 + P[i])) == T.charAt(i - (1 + P[i]))) {
                P[i]++;
            }
        } catch (Exception e) {
            // Boundary reached
        }
        
        // If palindrome centered at i extends past right, adjust center and right
        if (i + P[i] > right) {
            center = i;
            right = i + P[i];
        }
    }
    
    // Find the longest palindrome
    int maxLen = 0, centerIndex = 0;
    for (int i = 1; i < n - 1; i++) {
        if (P[i] > maxLen) {
            maxLen = P[i];
            centerIndex = i;
        }
    }
    
    int start = (centerIndex - maxLen) / 2;
    return s.substring(start, start + maxLen);
}
```

### 5. String Transformation

**When to use**: Converting between formats, encoding/decoding, or string normalization.

```java
public class StringTransformer {
    // Encode: ["abc", "def"] -> "3abc3def"
    public String encode(List<String> strs) {
        StringBuilder sb = new StringBuilder();
        for (String str : strs) {
            sb.append(str.length()).append(str);
        }
        return sb.toString();
    }
    
    // Decode: "3abc3def" -> ["abc", "def"]
    public List<String> decode(String s) {
        List<String> result = new ArrayList<>();
        int i = 0;
        
        while (i < s.length()) {
            // Find length
            int j = i;
            while (j < s.length() && Character.isDigit(s.charAt(j))) {
                j++;
            }
            
            int len = Integer.parseInt(s.substring(i, j));
            result.add(s.substring(j, j + len));
            i = j + len;
        }
        
        return result;
    }
}
```

## Developing Your Intuition

### Decision Framework:

**1. What's the core operation?**
- **Comparing character frequencies**: Use frequency arrays/maps
- **Building/modifying strings**: Use StringBuilder or char arrays
- **Finding patterns**: Use appropriate pattern matching algorithm
- **Palindrome detection**: Use expand around center or Manacher's

**2. What's the constraint?**
- **Only lowercase letters**: Use int[26] array
- **Any characters**: Use HashMap
- **Large text, small pattern**: Consider KMP or Rabin-Karp
- **Multiple patterns**: Consider Trie or Aho-Corasick

**3. What's the expected time complexity?**
- **O(n)**: Rolling hash, frequency counting
- **O(n²)**: Expand around center, simple pattern matching
- **O(n + m)**: KMP algorithm

## Pattern Recognition Tips

### Recognize String Template Problems by These Keywords:
- "Anagram/permutation" → Frequency matching
- "Pattern matching/search" → Pattern matching algorithms
- "Palindrome" → Expand around center
- "Encode/decode" → String transformation
- "Build/construct string" → StringBuilder
- "Character frequency" → Frequency arrays/maps

### Common Optimizations:

1. **Use char arrays instead of strings** for in-place modifications
2. **Use int[26] for lowercase letters** instead of HashMap
3. **Use StringBuilder** for multiple string concatenations
4. **Precompute hashes** for multiple pattern searches
5. **Use bit manipulation** for character sets when applicable

## Time and Space Complexity

- **Frequency matching**: O(n) time, O(1) to O(k) space
- **Pattern matching**: O(nm) simple, O(n+m) KMP, O(n) average Rabin-Karp
- **Palindrome (expand)**: O(n²) time, O(1) space
- **Palindrome (Manacher's)**: O(n) time, O(n) space
- **String building**: O(n) with StringBuilder, O(n²) with string concatenation

The key is choosing the right data structure and algorithm based on the problem constraints and expected performance.
