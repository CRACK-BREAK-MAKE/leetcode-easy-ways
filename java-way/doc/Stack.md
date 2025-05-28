# Understanding Stack Pattern Variations

## The Key Variations in Stack Pattern

Stack is a powerful pattern for problems involving nested structures, parsing, and maintaining order:

1. **Monotonic Stack**: Maintaining increasing/decreasing order
2. **Parsing and Validation**: Brackets, expressions, nested structures
3. **Next Greater/Smaller Element**: Finding relationships between elements
4. **Function Call Simulation**: Converting recursion to iteration
5. **Expression Evaluation**: Infix, postfix, and prefix expressions
6. **Undo Operations**: Maintaining history of operations

## Develop an intuition about when to use each approach.

### 1. Monotonic Stack

**When to use**: When you need to find the next/previous greater/smaller element, or maintain elements in sorted order during traversal.

#### Template A: Next Greater Element
```java
public int[] nextGreaterElement(int[] nums) {
    int n = nums.length;
    int[] result = new int[n];
    Arrays.fill(result, -1);
    Stack<Integer> stack = new Stack<>(); // Stores indices
    
    for (int i = 0; i < n; i++) {
        // Pop elements smaller than current element
        while (!stack.isEmpty() && nums[stack.peek()] < nums[i]) {
            int index = stack.pop();
            result[index] = nums[i];
        }
        stack.push(i);
    }
    
    return result;
}
```

#### Template B: Largest Rectangle in Histogram
```java
public int largestRectangleArea(int[] heights) {
    Stack<Integer> stack = new Stack<>();
    int maxArea = 0;
    
    for (int i = 0; i <= heights.length; i++) {
        int currentHeight = (i == heights.length) ? 0 : heights[i];
        
        while (!stack.isEmpty() && heights[stack.peek()] > currentHeight) {
            int height = heights[stack.pop()];
            int width = stack.isEmpty() ? i : i - stack.peek() - 1;
            maxArea = Math.max(maxArea, height * width);
        }
        
        stack.push(i);
    }
    
    return maxArea;
}
```

#### Template C: Daily Temperatures (Monotonic Decreasing)
```java
public int[] dailyTemperatures(int[] temperatures) {
    int n = temperatures.length;
    int[] result = new int[n];
    Stack<Integer> stack = new Stack<>(); // Decreasing stack (indices)
    
    for (int i = 0; i < n; i++) {
        while (!stack.isEmpty() && temperatures[stack.peek()] < temperatures[i]) {
            int prevIndex = stack.pop();
            result[prevIndex] = i - prevIndex; // Days to wait
        }
        stack.push(i);
    }
    
    return result;
}
```

**Classic Problems**:
- **Next Greater Element I & II**
- **Daily Temperatures**
- **Largest Rectangle in Histogram**
- **Trapping Rain Water**

### 2. Parsing and Validation

**When to use**: For nested structures, balanced parentheses, and expression validation.

#### Template A: Valid Parentheses
```java
public boolean isValid(String s) {
    Stack<Character> stack = new Stack<>();
    Map<Character, Character> mapping = new HashMap<>();
    mapping.put(')', '(');
    mapping.put('}', '{');
    mapping.put(']', '[');
    
    for (char c : s.toCharArray()) {
        if (mapping.containsKey(c)) {
            // Closing bracket
            if (stack.isEmpty() || stack.pop() != mapping.get(c)) {
                return false;
            }
        } else {
            // Opening bracket
            stack.push(c);
        }
    }
    
    return stack.isEmpty();
}
```

#### Template B: Remove Invalid Parentheses (Greedy)
```java
public String removeInvalidParentheses(String s) {
    StringBuilder sb = new StringBuilder();
    int open = 0;
    
    // First pass: remove invalid closing brackets
    for (char c : s.toCharArray()) {
        if (c == '(') {
            open++;
            sb.append(c);
        } else if (c == ')' && open > 0) {
            open--;
            sb.append(c);
        } else if (c != ')') {
            sb.append(c);
        }
    }
    
    // Second pass: remove excess opening brackets
    StringBuilder result = new StringBuilder();
    int close = 0;
    for (int i = sb.length() - 1; i >= 0; i--) {
        char c = sb.charAt(i);
        if (c == ')') {
            close++;
            result.append(c);
        } else if (c == '(' && close > 0) {
            close--;
            result.append(c);
        } else if (c != '(') {
            result.append(c);
        }
    }
    
    return result.reverse().toString();
}
```

#### Template C: Decode String
```java
public String decodeString(String s) {
    Stack<Integer> countStack = new Stack<>();
    Stack<StringBuilder> stringStack = new Stack<>();
    StringBuilder current = new StringBuilder();
    int k = 0;
    
    for (char c : s.toCharArray()) {
        if (Character.isDigit(c)) {
            k = k * 10 + (c - '0');
        } else if (c == '[') {
            countStack.push(k);
            stringStack.push(current);
            current = new StringBuilder();
            k = 0;
        } else if (c == ']') {
            StringBuilder temp = current;
            current = stringStack.pop();
            int repeatCount = countStack.pop();
            for (int i = 0; i < repeatCount; i++) {
                current.append(temp);
            }
        } else {
            current.append(c);
        }
    }
    
    return current.toString();
}
```

**Classic Problems**:
- **Valid Parentheses**
- **Generate Parentheses**
- **Remove Invalid Parentheses**
- **Decode String**

### 3. Expression Evaluation

**When to use**: For mathematical expressions, calculator problems, and operator precedence.

#### Template A: Basic Calculator
```java
public int calculate(String s) {
    Stack<Integer> stack = new Stack<>();
    int result = 0;
    int number = 0;
    char operation = '+';
    
    for (int i = 0; i < s.length(); i++) {
        char c = s.charAt(i);
        
        if (Character.isDigit(c)) {
            number = number * 10 + (c - '0');
        }
        
        if (c == '+' || c == '-' || c == '*' || c == '/' || i == s.length() - 1) {
            if (operation == '+') {
                stack.push(number);
            } else if (operation == '-') {
                stack.push(-number);
            } else if (operation == '*') {
                stack.push(stack.pop() * number);
            } else if (operation == '/') {
                stack.push(stack.pop() / number);
            }
            
            operation = c;
            number = 0;
        }
    }
    
    while (!stack.isEmpty()) {
        result += stack.pop();
    }
    
    return result;
}
```

#### Template B: Evaluate Reverse Polish Notation
```java
public int evalRPN(String[] tokens) {
    Stack<Integer> stack = new Stack<>();
    
    for (String token : tokens) {
        if (isOperator(token)) {
            int b = stack.pop();
            int a = stack.pop();
            stack.push(applyOperator(a, b, token));
        } else {
            stack.push(Integer.parseInt(token));
        }
    }
    
    return stack.pop();
}

private boolean isOperator(String token) {
    return "+".equals(token) || "-".equals(token) || "*".equals(token) || "/".equals(token);
}

private int applyOperator(int a, int b, String operator) {
    switch (operator) {
        case "+": return a + b;
        case "-": return a - b;
        case "*": return a * b;
        case "/": return a / b;
        default: throw new IllegalArgumentException("Invalid operator: " + operator);
    }
}
```

### 4. Function Call Simulation

**When to use**: Converting recursive algorithms to iterative ones, or simulating call stack behavior.

#### Template A: Binary Tree Inorder Traversal (Iterative)
```java
public List<Integer> inorderTraversal(TreeNode root) {
    List<Integer> result = new ArrayList<>();
    Stack<TreeNode> stack = new Stack<>();
    TreeNode current = root;
    
    while (current != null || !stack.isEmpty()) {
        // Go to leftmost node
        while (current != null) {
            stack.push(current);
            current = current.left;
        }
        
        // Process current node
        current = stack.pop();
        result.add(current.val);
        
        // Move to right subtree
        current = current.right;
    }
    
    return result;
}
```

#### Template B: Flatten Binary Tree to Linked List
```java
public void flatten(TreeNode root) {
    if (root == null) return;
    
    Stack<TreeNode> stack = new Stack<>();
    stack.push(root);
    
    while (!stack.isEmpty()) {
        TreeNode current = stack.pop();
        
        if (current.right != null) {
            stack.push(current.right);
        }
        if (current.left != null) {
            stack.push(current.left);
        }
        
        if (!stack.isEmpty()) {
            current.right = stack.peek();
        }
        current.left = null;
    }
}
```

### 5. Undo Operations and History

#### Template A: Browser History
```java
public class BrowserHistory {
    private Stack<String> history;
    private Stack<String> future;
    
    public BrowserHistory(String homepage) {
        history = new Stack<>();
        future = new Stack<>();
        history.push(homepage);
    }
    
    public void visit(String url) {
        history.push(url);
        future.clear(); // Clear forward history
    }
    
    public String back(int steps) {
        while (steps > 0 && history.size() > 1) {
            future.push(history.pop());
            steps--;
        }
        return history.peek();
    }
    
    public String forward(int steps) {
        while (steps > 0 && !future.isEmpty()) {
            history.push(future.pop());
            steps--;
        }
        return history.peek();
    }
}
```

## Developing Your Intuition

### Decision Framework:

**1. What are you maintaining?**
- **Nested structure**: Use stack for parsing
- **Order relationship**: Use monotonic stack
- **Expression evaluation**: Use operator precedence with stack
- **Function calls**: Use stack to simulate recursion

**2. What direction are you processing?**
- **Left to right with lookback**: Monotonic stack
- **Inside out (nested)**: Parsing stack
- **Mathematical precedence**: Expression evaluation stack

**3. What do you need to remember?**
- **Previous smaller/larger elements**: Monotonic stack
- **Matching pairs**: Validation stack
- **Intermediate results**: Calculator stack
- **Path/state history**: Undo stack

## Pattern Recognition Tips

### Recognize Stack Problems by These Keywords:
- "Next greater/smaller element" → Monotonic stack
- "Valid parentheses/brackets" → Parsing stack
- "Calculator/expression evaluation" → Expression stack
- "Nested structure" → Parsing stack
- "Undo/redo operations" → History stack
- "Iterative tree traversal" → Simulation stack
- "Largest rectangle/area" → Monotonic stack

### Stack Type Decision:

1. **Monotonic Increasing Stack**:
    - Elements in stack are in increasing order
    - Use for "next smaller element" problems
    - Pop when current element is smaller

2. **Monotonic Decreasing Stack**:
    - Elements in stack are in decreasing order
    - Use for "next greater element" problems
    - Pop when current element is larger

3. **Parsing Stack**:
    - Store opening brackets/tags
    - Match with closing brackets/tags
    - Check for balanced structure

## Common Pitfalls and Solutions

1. **Empty Stack**: Always check `!stack.isEmpty()` before `peek()` or `pop()`
2. **Index vs Value**: Decide whether to store indices or actual values
3. **Monotonic Direction**: Ensure you understand increasing vs decreasing order
4. **Expression Precedence**: Handle operator precedence correctly
5. **Stack State**: Be clear about what each stack position represents

## Time and Space Complexity

- **Monotonic Stack**: O(n) time (each element pushed/popped once), O(n) space
- **Parsing**: O(n) time, O(n) space in worst case
- **Expression Evaluation**: O(n) time, O(n) space
- **Tree Simulation**: O(n) time, O(h) space where h is height

The key insight is that stack problems often involve maintaining relationships between elements or managing nested structures efficiently.
