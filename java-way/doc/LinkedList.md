# Understanding LinkedList Pattern Variations

## The Key Variations in LinkedList Pattern

LinkedList problems involve pointer manipulation and are crucial for understanding data structure operations:

1. **Two Pointer Techniques**: Fast/slow, collision detection
2. **Reversal Patterns**: Reversing entire or partial lists
3. **Merging and Splitting**: Combining or dividing linked lists
4. **Cycle Detection and Removal**: Floyd's algorithm and variations
5. **In-Place Manipulation**: Modifying lists without extra space
6. **Dummy Node Techniques**: Simplifying edge cases

## Develop an intuition about when to use each approach.

### 1. Two Pointer Techniques

**When to use**: For cycle detection, finding middle elements, or maintaining specific distances between pointers.

#### Template A: Cycle Detection (Floyd's Algorithm)
```java
public boolean hasCycle(ListNode head) {
    if (head == null || head.next == null) return false;
    
    ListNode slow = head;
    ListNode fast = head;
    
    while (fast != null && fast.next != null) {
        slow = slow.next;
        fast = fast.next.next;
        
        if (slow == fast) {
            return true; // Cycle detected
        }
    }
    
    return false;
}
```

#### Template B: Find Cycle Start
```java
public ListNode detectCycle(ListNode head) {
    if (head == null || head.next == null) return null;
    
    // Phase 1: Detect if cycle exists
    ListNode slow = head, fast = head;
    while (fast != null && fast.next != null) {
        slow = slow.next;
        fast = fast.next.next;
        if (slow == fast) break;
    }
    
    // No cycle found
    if (fast == null || fast.next == null) return null;
    
    // Phase 2: Find cycle start
    slow = head;
    while (slow != fast) {
        slow = slow.next;
        fast = fast.next;
    }
    
    return slow; // Start of cycle
}
```

#### Template C: Find Middle Element
```java
public ListNode findMiddle(ListNode head) {
    if (head == null) return null;
    
    ListNode slow = head;
    ListNode fast = head;
    
    // For even length: slow will be at first middle
    // For odd length: slow will be at exact middle
    while (fast.next != null && fast.next.next != null) {
        slow = slow.next;
        fast = fast.next.next;
    }
    
    return slow;
}
```

#### Template D: Kth Node from End
```java
public ListNode removeNthFromEnd(ListNode head, int n) {
    ListNode dummy = new ListNode(0);
    dummy.next = head;
    
    ListNode first = dummy;
    ListNode second = dummy;
    
    // Move first pointer n+1 steps ahead
    for (int i = 0; i <= n; i++) {
        first = first.next;
    }
    
    // Move both pointers until first reaches end
    while (first != null) {
        first = first.next;
        second = second.next;
    }
    
    // Remove the nth node from end
    second.next = second.next.next;
    
    return dummy.next;
}
```

**Classic Problems**:
- **Linked List Cycle I & II**
- **Find Middle of Linked List**
- **Remove Nth Node From End**
- **Intersection of Two Linked Lists**

### 2. Reversal Patterns

**When to use**: When you need to reverse entire lists, sublists, or alternate nodes.

#### Template A: Reverse Entire List (Iterative)
```java
public ListNode reverseList(ListNode head) {
    ListNode prev = null;
    ListNode current = head;
    
    while (current != null) {
        ListNode nextTemp = current.next;
        current.next = prev;
        prev = current;
        current = nextTemp;
    }
    
    return prev; // New head
}
```

#### Template B: Reverse Between Positions
```java
public ListNode reverseBetween(ListNode head, int left, int right) {
    if (head == null || left == right) return head;
    
    ListNode dummy = new ListNode(0);
    dummy.next = head;
    ListNode prev = dummy;
    
    // Move prev to position before left
    for (int i = 0; i < left - 1; i++) {
        prev = prev.next;
    }
    
    // Start reversing from left to right
    ListNode current = prev.next;
    for (int i = 0; i < right - left; i++) {
        ListNode nextNode = current.next;
        current.next = nextNode.next;
        nextNode.next = prev.next;
        prev.next = nextNode;
    }
    
    return dummy.next;
}
```

#### Template C: Reverse Nodes in k-Group
```java
public ListNode reverseKGroup(ListNode head, int k) {
    ListNode dummy = new ListNode(0);
    dummy.next = head;
    ListNode prevGroupEnd = dummy;
    
    while (true) {
        // Check if we have k nodes left
        ListNode kthNode = getKthNode(prevGroupEnd, k);
        if (kthNode == null) break;
        
        ListNode nextGroupStart = kthNode.next;
        
        // Reverse current group
        ListNode prev = nextGroupStart;
        ListNode current = prevGroupEnd.next;
        
        while (current != nextGroupStart) {
            ListNode nextTemp = current.next;
            current.next = prev;
            prev = current;
            current = nextTemp;
        }
        
        // Connect with previous part
        ListNode originalGroupStart = prevGroupEnd.next;
        prevGroupEnd.next = kthNode;
        prevGroupEnd = originalGroupStart;
    }
    
    return dummy.next;
}

private ListNode getKthNode(ListNode start, int k) {
    while (start != null && k > 0) {
        start = start.next;
        k--;
    }
    return start;
}
```

### 3. Merging and Splitting

**When to use**: For merge sort, combining sorted lists, or partitioning lists.

#### Template A: Merge Two Sorted Lists
```java
public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
    ListNode dummy = new ListNode(0);
    ListNode current = dummy;
    
    while (l1 != null && l2 != null) {
        if (l1.val <= l2.val) {
            current.next = l1;
            l1 = l1.next;
        } else {
            current.next = l2;
            l2 = l2.next;
        }
        current = current.next;
    }
    
    // Attach remaining nodes
    current.next = (l1 != null) ? l1 : l2;
    
    return dummy.next;
}
```

#### Template B: Merge Sort for LinkedList
```java
public ListNode sortList(ListNode head) {
    if (head == null || head.next == null) return head;
    
    // Split the list into two halves
    ListNode mid = getMiddle(head);
    ListNode secondHalf = mid.next;
    mid.next = null;
    
    // Recursively sort both halves
    ListNode left = sortList(head);
    ListNode right = sortList(secondHalf);
    
    // Merge sorted halves
    return mergeTwoLists(left, right);
}

private ListNode getMiddle(ListNode head) {
    ListNode slow = head;
    ListNode fast = head;
    ListNode prev = null;
    
    while (fast != null && fast.next != null) {
        prev = slow;
        slow = slow.next;
        fast = fast.next.next;
    }
    
    return prev; // Return node before middle
}
```

#### Template C: Partition List
```java
public ListNode partition(ListNode head, int x) {
    ListNode beforeHead = new ListNode(0);
    ListNode afterHead = new ListNode(0);
    
    ListNode before = beforeHead;
    ListNode after = afterHead;
    
    while (head != null) {
        if (head.val < x) {
            before.next = head;
            before = before.next;
        } else {
            after.next = head;
            after = after.next;
        }
        head = head.next;
    }
    
    after.next = null; // Important: end the after list
    before.next = afterHead.next; // Connect before and after
    
    return beforeHead.next;
}
```

### 4. In-Place Manipulation

**When to use**: When you need to modify the list structure without using extra space.

#### Template A: Remove Duplicates from Sorted List
```java
public ListNode deleteDuplicates(ListNode head) {
    ListNode current = head;
    
    while (current != null && current.next != null) {
        if (current.val == current.next.val) {
            current.next = current.next.next;
        } else {
            current = current.next;
        }
    }
    
    return head;
}
```

#### Template B: Remove All Duplicates
```java
public ListNode deleteDuplicatesII(ListNode head) {
    ListNode dummy = new ListNode(0);
    dummy.next = head;
    ListNode prev = dummy;
    
    while (head != null) {
        if (head.next != null && head.val == head.next.val) {
            // Skip all duplicates
            int duplicateVal = head.val;
            while (head != null && head.val == duplicateVal) {
                head = head.next;
            }
            prev.next = head;
        } else {
            prev = head;
            head = head.next;
        }
    }
    
    return dummy.next;
}
```

#### Template C: Reorder List (L0→Ln→L1→Ln-1→...)
```java
public void reorderList(ListNode head) {
    if (head == null || head.next == null) return;
    
    // Step 1: Find middle
    ListNode slow = head, fast = head;
    while (fast.next != null && fast.next.next != null) {
        slow = slow.next;
        fast = fast.next.next;
    }
    
    // Step 2: Reverse second half
    ListNode secondHalf = reverseList(slow.next);
    slow.next = null;
    
    // Step 3: Merge two halves alternately
    ListNode first = head;
    ListNode second = secondHalf;
    
    while (second != null) {
        ListNode firstNext = first.next;
        ListNode secondNext = second.next;
        
        first.next = second;
        second.next = firstNext;
        
        first = firstNext;
        second = secondNext;
    }
}
```

### 5. Advanced Techniques

#### Template A: Copy List with Random Pointer
```java
public Node copyRandomList(Node head) {
    if (head == null) return null;
    
    // Step 1: Create interleaved list (A->A'->B->B'->...)
    Node current = head;
    while (current != null) {
        Node copy = new Node(current.val);
        copy.next = current.next;
        current.next = copy;
        current = copy.next;
    }
    
    // Step 2: Set random pointers for copied nodes
    current = head;
    while (current != null) {
        if (current.random != null) {
            current.next.random = current.random.next;
        }
        current = current.next.next;
    }
    
    // Step 3: Separate original and copied lists
    Node dummy = new Node(0);
    Node copyPrev = dummy;
    current = head;
    
    while (current != null) {
        copyPrev.next = current.next;
        current.next = current.next.next;
        
        copyPrev = copyPrev.next;
        current = current.next;
    }
    
    return dummy.next;
}
```

## Developing Your Intuition

### Decision Framework:

**1. What operation do you need?**
- **Find specific position**: Two pointers with different speeds
- **Reverse order**: Reversal patterns
- **Combine lists**: Merging techniques
- **Detect patterns**: Cycle detection
- **Modify in-place**: Direct pointer manipulation

**2. Do you need extra space?**
- **Yes**: Use additional data structures or recursive approaches
- **No**: Use in-place pointer manipulation

**3. What's the list structure?**
- **Singly linked**: Standard techniques
- **Doubly linked**: Can traverse backwards
- **Circular**: Special handling for cycle detection

### Dummy Node Usage:

Use dummy nodes when:
- The head might change (removal, reversal)
- You need to handle edge cases uniformly
- Building new lists from scratch

## Pattern Recognition Tips

### Recognize LinkedList Problems by These Keywords:
- "Cycle detection" → Floyd's algorithm
- "Reverse list/sublist" → Reversal patterns
- "Merge sorted lists" → Merging techniques
- "Remove nth from end" → Two pointers with gap
- "Find middle" → Fast/slow pointers
- "Partition/split" → Multiple pointers
- "Duplicate removal" → In-place manipulation

### Common Patterns:

1. **Before starting**: Check for null head
2. **Use dummy node**: When head might change
3. **Two pointers**: Different speeds or fixed gaps
4. **Three pointers**: For reversal (prev, current, next)
5. **Break and reconnect**: For complex rearrangements

## Time and Space Complexity

- **Two pointer techniques**: O(n) time, O(1) space
- **Reversal**: O(n) time, O(1) space
- **Merging**: O(n + m) time, O(1) space
- **Cycle detection**: O(n) time, O(1) space
- **In-place manipulation**: O(n) time, O(1) space

The beauty of LinkedList algorithms is achieving complex operations with minimal space complexity through clever pointer manipulation.
