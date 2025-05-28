# Understanding Tree Patterns

## The Core Intuition

Tree problems are fundamentally about **recursive structure** and **information flow**. The key insight is understanding whether information flows **up** (from children to parent), **down** (from parent to children), or **both directions**.

## Information Flow Patterns

### Pattern 1: Bottom-Up (Post-order thinking)
**When to use**: You need information from children to make decisions about current node
**Information flows**: Children → Parent

```java
// Template for bottom-up problems
public int dfs(TreeNode root) {
    if (root == null) return baseCase;
    
    int left = dfs(root.left);   // Get info from left subtree
    int right = dfs(root.right); // Get info from right subtree
    
    // Process current node using children's information
    return processNode(root.val, left, right);
}
```

### Pattern 2: Top-Down (Pre-order thinking)
**When to use**: You need to pass information from parent to children
**Information flows**: Parent → Children

```java
// Template for top-down problems
public void dfs(TreeNode root, int parentInfo) {
    if (root == null) return;
    
    // Process current node using parent information
    int currentInfo = processNode(root.val, parentInfo);
    
    dfs(root.left, currentInfo);  // Pass info to left child
    dfs(root.right, currentInfo); // Pass info to right child
}
```

### Pattern 3: Hybrid (Both directions)
**When to use**: Complex problems requiring both upward and downward information flow

```java
// Template for hybrid problems - often requires helper functions
public class Solution {
    private int globalResult;
    
    public int solve(TreeNode root) {
        dfs(root, 0); // 0 is initial parent info
        return globalResult;
    }
    
    private int dfs(TreeNode root, int fromParent) {
        if (root == null) return baseCase;
        
        int left = dfs(root.left, someFunction(root, fromParent));
        int right = dfs(root.right, someFunction(root, fromParent));
        
        // Update global result using both parent and children info
        globalResult = Math.max(globalResult, combineInfo(fromParent, left, right));
        
        return toParent(left, right, root.val);
    }
}
```

## Tree Problem Categories & Patterns

### Category 1: Tree Property Problems (Bottom-Up)
**Pattern**: Calculate property using children's results

#### Height/Depth Problems
```java
public int maxDepth(TreeNode root) {
    if (root == null) return 0;
    return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
}
```

#### Diameter Problems
```java
private int maxDiameter = 0;

public int diameterOfBinaryTree(TreeNode root) {
    depth(root);
    return maxDiameter;
}

private int depth(TreeNode root) {
    if (root == null) return 0;
    
    int left = depth(root.left);
    int right = depth(root.right);
    
    maxDiameter = Math.max(maxDiameter, left + right); // Update global result
    return 1 + Math.max(left, right); // Return depth to parent
}
```

#### Balance Check Problems
```java
public boolean isBalanced(TreeNode root) {
    return checkBalance(root) != -1;
}

private int checkBalance(TreeNode root) {
    if (root == null) return 0;
    
    int left = checkBalance(root.left);
    int right = checkBalance(root.right);
    
    if (left == -1 || right == -1 || Math.abs(left - right) > 1) {
        return -1; // Not balanced
    }
    
    return 1 + Math.max(left, right);
}
```

### Category 2: Path Sum Problems (Top-Down or Hybrid)

#### Root-to-Leaf Path Sum
```java
public boolean hasPathSum(TreeNode root, int targetSum) {
    if (root == null) return false;
    
    // Leaf node check
    if (root.left == null && root.right == null) {
        return root.val == targetSum;
    }
    
    int remaining = targetSum - root.val;
    return hasPathSum(root.left, remaining) || hasPathSum(root.right, remaining);
}
```

#### Any Path Sum (Hybrid - most complex)
```java
private int maxSum = Integer.MIN_VALUE;

public int maxPathSum(TreeNode root) {
    maxGain(root);
    return maxSum;
}

private int maxGain(TreeNode root) {
    if (root == null) return 0;
    
    // Only take positive gains from children
    int leftGain = Math.max(maxGain(root.left), 0);
    int rightGain = Math.max(maxGain(root.right), 0);
    
    // Check if current path through this node is maximum
    int currentPathSum = root.val + leftGain + rightGain;
    maxSum = Math.max(maxSum, currentPathSum);
    
    // Return max gain when this node is part of a path to parent
    return root.val + Math.max(leftGain, rightGain);
}
```

### Category 3: Tree Construction Problems

#### Build from Traversals
```java
public TreeNode buildTree(int[] preorder, int[] inorder) {
    Map<Integer, Integer> inorderMap = new HashMap<>();
    for (int i = 0; i < inorder.length; i++) {
        inorderMap.put(inorder[i], i);
    }
    return build(preorder, 0, 0, inorder.length - 1, inorderMap);
}

private TreeNode build(int[] preorder, int preStart, int inStart, int inEnd, 
                      Map<Integer, Integer> inorderMap) {
    if (inStart > inEnd) return null;
    
    TreeNode root = new TreeNode(preorder[preStart]);
    int rootIndex = inorderMap.get(root.val);
    int leftSize = rootIndex - inStart;
    
    root.left = build(preorder, preStart + 1, inStart, rootIndex - 1, inorderMap);
    root.right = build(preorder, preStart + leftSize + 1, rootIndex + 1, inEnd, inorderMap);
    
    return root;
}
```

### Category 4: Tree Traversal Variations

#### Level Order (BFS)
```java
public List<List<Integer>> levelOrder(TreeNode root) {
    List<List<Integer>> result = new ArrayList<>();
    if (root == null) return result;
    
    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(root);
    
    while (!queue.isEmpty()) {
        int levelSize = queue.size();
        List<Integer> currentLevel = new ArrayList<>();
        
        for (int i = 0; i < levelSize; i++) {
            TreeNode node = queue.poll();
            currentLevel.add(node.val);
            
            if (node.left != null) queue.offer(node.left);
            if (node.right != null) queue.offer(node.right);
        }
        result.add(currentLevel);
    }
    return result;
}
```

#### Vertical Order Traversal
```java
public List<List<Integer>> verticalTraversal(TreeNode root) {
    TreeMap<Integer, TreeMap<Integer, PriorityQueue<Integer>>> map = new TreeMap<>();
    dfs(root, 0, 0, map);
    
    List<List<Integer>> result = new ArrayList<>();
    for (TreeMap<Integer, PriorityQueue<Integer>> rowMap : map.values()) {
        List<Integer> column = new ArrayList<>();
        for (PriorityQueue<Integer> nodes : rowMap.values()) {
            while (!nodes.isEmpty()) {
                column.add(nodes.poll());
            }
        }
        result.add(column);
    }
    return result;
}

private void dfs(TreeNode root, int col, int row, 
                TreeMap<Integer, TreeMap<Integer, PriorityQueue<Integer>>> map) {
    if (root == null) return;
    
    map.computeIfAbsent(col, k -> new TreeMap<>())
       .computeIfAbsent(row, k -> new PriorityQueue<>())
       .offer(root.val);
    
    dfs(root.left, col - 1, row + 1, map);
    dfs(root.right, col + 1, row + 1, map);
}
```

### Category 5: Binary Search Tree Patterns

#### BST Validation
```java
public boolean isValidBST(TreeNode root) {
    return validate(root, null, null);
}

private boolean validate(TreeNode root, Integer min, Integer max) {
    if (root == null) return true;
    
    if ((min != null && root.val <= min) || (max != null && root.val >= max)) {
        return false;
    }
    
    return validate(root.left, min, root.val) && validate(root.right, root.val, max);
}
```

#### BST Search and Insert
```java
public TreeNode searchBST(TreeNode root, int val) {
    if (root == null || root.val == val) return root;
    return val < root.val ? searchBST(root.left, val) : searchBST(root.right, val);
}

public TreeNode insertIntoBST(TreeNode root, int val) {
    if (root == null) return new TreeNode(val);
    
    if (val < root.val) {
        root.left = insertIntoBST(root.left, val);
    } else {
        root.right = insertIntoBST(root.right, val);
    }
    return root;
}
```

## Decision Tree for Tree Problems

1. **Do I need information from children to decide about parent?** → Bottom-up (Post-order)
2. **Do I need to pass information from parent to children?** → Top-down (Pre-order)
3. **Do I need both parent and children information?** → Hybrid pattern
4. **Am I looking for paths in the tree?** → Consider what kind of path (root-to-leaf vs any path)
5. **Am I building/reconstructing a tree?** → Usually recursive with careful index management
6. **Do I need level-by-level processing?** → BFS with queue
7. **Is this a BST with ordering properties?** → Leverage BST properties for efficiency

## Common Tree Problem Patterns

### Pattern Recognition Keywords:
- **"Maximum/Minimum depth"** → Bottom-up height calculation
- **"Diameter"** → Bottom-up with global variable for path through each node
- **"Path sum"** → Top-down if root-to-leaf, hybrid if any path
- **"Balance check"** → Bottom-up returning height or -1 for invalid
- **"Level order"** → BFS with queue
- **"Vertical/Zigzag order"** → BFS/DFS with coordinate tracking
- **"Build tree from traversal"** → Recursive construction with index management
- **"Lowest Common Ancestor"** → Bottom-up search or path tracking
- **"Serialize/Deserialize"** → Pre-order traversal usually works best

## Time Complexity Patterns
- **Most tree traversals**: O(n) where n is number of nodes
- **BST operations**: O(h) where h is height (O(log n) balanced, O(n) worst case)
- **Tree construction**: O(n) with proper index management

## Space Complexity Patterns
- **Recursive calls**: O(h) call stack space where h is tree height
- **Level order traversal**: O(w) where w is maximum width of tree
- **Path tracking**: O(h) for storing current path

## Template Selection Guide

| Problem Type | Information Flow | Template | Key Insight |
|-------------|------------------|----------|-------------|
| Height/Depth | Bottom-up | Post-order DFS | Children info → Parent |
| Path Sum (root-to-leaf) | Top-down | Pre-order DFS | Parent info → Children |
| Diameter/Max Path | Hybrid | Post-order + global | Both directions needed |
| Level Processing | Breadth-first | BFS with queue | Process level by level |
| BST Operations | Depends | Leverage ordering | Use BST property |
| Tree Construction | Recursive | Index management | Careful boundary handling |

## Red Flags for Each Pattern

### Bottom-Up Indicators:
- Need to calculate something about subtrees first
- Words: "height", "depth", "diameter", "balance", "count nodes"

### Top-Down Indicators:
- Need to pass constraints or accumulated values down
- Words: "path sum from root", "validate with bounds", "print paths"

### Hybrid Indicators:
- Complex path problems involving any two nodes
- Need both ancestor and descendant information
- Words: "maximum path sum", "distance between nodes"

### BFS Indicators:
- Level-by-level processing needed
- Words: "level order", "minimum depth", "connect nodes at same level"
