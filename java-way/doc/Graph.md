# Complete Graph Patterns Guide for LeetCode 150 & Interviews

## The Core Intuition

Graph problems are fundamentally about **relationships** and **connectivity**. The key insight is understanding what you're searching for: **connectivity**, **shortest paths**, **cycles**, or **optimal traversals**.

## 🎯 Pattern Recognition Decision Tree

```
┌─ GRAPH PROBLEM ─┐
│                 │
├─ What am I looking for?
│  ├─ Connectivity/Groups → DFS/BFS/Union-Find
│  ├─ Shortest Path → BFS/Dijkstra/Floyd-Warshall
│  ├─ Cycles → DFS with colors/parent tracking
│  ├─ Ordering → Topological Sort
│  ├─ Minimum Cost Connection → MST
│  ├─ Path Existence → BFS/DFS
│  └─ Critical Connections → Tarjan's Algorithm
│
├─ Graph Type?
│  ├─ Unweighted → BFS for shortest path
│  ├─ Weighted (non-negative) → Dijkstra
│  ├─ Weighted (negative edges) → Bellman-Ford
│  ├─ DAG → DFS-based algorithms
│  └─ Dense vs Sparse → Matrix vs List representation
│
└─ Constraints?
   ├─ All-pairs distances → Floyd-Warshall
   ├─ Dynamic connectivity → Union-Find
   ├─ Real-time queries → Precomputed structures
   └─ Memory limits → Space-efficient algorithms
```

## 📊 Graph Representation Patterns

### Adjacency List (Most Common)
```java
// For unweighted graphs
List<List<Integer>> adj = new ArrayList<>();
Map<Integer, List<Integer>> adj = new HashMap<>();

// For weighted graphs
Map<Integer, List<int[]>> adj = new HashMap<>(); // [neighbor, weight]
Map<Integer, List<Pair<Integer, Integer>>> adj = new HashMap<>();
```

### Adjacency Matrix
```java
// For dense graphs or when checking edge existence is frequent
int[][] adj = new int[n][n]; // 0/1 for unweighted, weight for weighted
boolean[][] adj = new boolean[n][n]; // For unweighted
```

### Edge List
```java
// For algorithms like Kruskal's MST
List<int[]> edges = new ArrayList<>(); // [from, to, weight]
```

## 🔍 Core Graph Traversal Patterns

### Pattern 1: DFS (Depth-First Search)
**When to use**: Path finding, connectivity, cycle detection, topological sorting
**Key insight**: Go as deep as possible before backtracking

```java
// Template for DFS
public void dfs(int node, List<List<Integer>> adj, boolean[] visited) {
    visited[node] = true;
    // Process current node
    
    for (int neighbor : adj.get(node)) {
        if (!visited[neighbor]) {
            dfs(neighbor, adj, visited);
        }
    }
}

// Iterative DFS
public void dfsIterative(int start, List<List<Integer>> adj) {
    boolean[] visited = new boolean[adj.size()];
    Stack<Integer> stack = new Stack<>();
    stack.push(start);
    
    while (!stack.isEmpty()) {
        int node = stack.pop();
        if (!visited[node]) {
            visited[node] = true;
            // Process node
            
            for (int neighbor : adj.get(node)) {
                if (!visited[neighbor]) {
                    stack.push(neighbor);
                }
            }
        }
    }
}
```
**LeetCode Problems**: 200, 695, 547, 417, 130, 133, 797
**Complexity**: Time O(V + E), Space O(V)

### Pattern 2: BFS (Breadth-First Search)
**When to use**: Shortest path in unweighted graphs, level-by-level processing
**Key insight**: Explore all nodes at distance k before nodes at distance k+1

```java
// Template for BFS
public void bfs(int start, List<List<Integer>> adj) {
    boolean[] visited = new boolean[adj.size()];
    Queue<Integer> queue = new LinkedList<>();
    queue.offer(start);
    visited[start] = true;
    
    while (!queue.isEmpty()) {
        int node = queue.poll();
        // Process current node
        
        for (int neighbor : adj.get(node)) {
            if (!visited[neighbor]) {
                visited[neighbor] = true;
                queue.offer(neighbor);
            }
        }
    }
}

// BFS with distance tracking
public int[] bfsDistance(int start, List<List<Integer>> adj) {
    int[] dist = new int[adj.size()];
    Arrays.fill(dist, -1);
    Queue<Integer> queue = new LinkedList<>();
    
    queue.offer(start);
    dist[start] = 0;
    
    while (!queue.isEmpty()) {
        int node = queue.poll();
        
        for (int neighbor : adj.get(node)) {
            if (dist[neighbor] == -1) {
                dist[neighbor] = dist[node] + 1;
                queue.offer(neighbor);
            }
        }
    }
    return dist;
}
```
**LeetCode Problems**: 127, 279, 752, 773, 1091, 317
**Complexity**: Time O(V + E), Space O(V)

## 🚀 Specialized Graph Patterns

### Pattern 3: Connected Components
**When to use**: Finding separate groups/islands in graph
**Key insight**: Each DFS/BFS from unvisited node finds one component

```java
public int countConnectedComponents(int n, int[][] edges) {
    List<List<Integer>> adj = buildGraph(n, edges);
    boolean[] visited = new boolean[n];
    int components = 0;
    
    for (int i = 0; i < n; i++) {
        if (!visited[i]) {
            dfs(i, adj, visited);
            components++;
        }
    }
    return components;
}
```
**LeetCode Problems**: 200, 547, 323, 695, 1254
**Complexity**: Time O(V + E), Space O(V)

### Pattern 4: Cycle Detection
**For Undirected Graphs**:
```java
public boolean hasCycleUndirected(List<List<Integer>> adj) {
    boolean[] visited = new boolean[adj.size()];
    
    for (int i = 0; i < adj.size(); i++) {
        if (!visited[i] && dfsHasCycle(i, -1, adj, visited)) {
            return true;
        }
    }
    return false;
}

private boolean dfsHasCycle(int node, int parent, List<List<Integer>> adj, boolean[] visited) {
    visited[node] = true;
    
    for (int neighbor : adj.get(node)) {
        if (!visited[neighbor]) {
            if (dfsHasCycle(neighbor, node, adj, visited)) return true;
        } else if (neighbor != parent) {
            return true; // Back edge found
        }
    }
    return false;
}
```

**For Directed Graphs** (using colors):
```java
public boolean hasCycleDirected(List<List<Integer>> adj) {
    int[] color = new int[adj.size()]; // 0=white, 1=gray, 2=black
    
    for (int i = 0; i < adj.size(); i++) {
        if (color[i] == 0 && dfsHasCycleDirected(i, adj, color)) {
            return true;
        }
    }
    return false;
}

private boolean dfsHasCycleDirected(int node, List<List<Integer>> adj, int[] color) {
    color[node] = 1; // Gray (processing)
    
    for (int neighbor : adj.get(node)) {
        if (color[neighbor] == 1) return true; // Back edge to gray node
        if (color[neighbor] == 0 && dfsHasCycleDirected(neighbor, adj, color)) return true;
    }
    
    color[node] = 2; // Black (processed)
    return false;
}
```
**LeetCode Problems**: 207, 210, 684, 685
**Complexity**: Time O(V + E), Space O(V)

### Pattern 5: Topological Sort
**When to use**: Ordering nodes with dependencies (DAG required)
**Key insight**: Process nodes with no incoming edges first

```java
// Kahn's Algorithm (BFS-based)
public List<Integer> topologicalSort(int n, int[][] edges) {
    List<List<Integer>> adj = new ArrayList<>();
    int[] indegree = new int[n];
    
    // Build graph and calculate indegrees
    for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
    for (int[] edge : edges) {
        adj.get(edge[0]).add(edge[1]);
        indegree[edge[1]]++;
    }
    
    Queue<Integer> queue = new LinkedList<>();
    for (int i = 0; i < n; i++) {
        if (indegree[i] == 0) queue.offer(i);
    }
    
    List<Integer> result = new ArrayList<>();
    while (!queue.isEmpty()) {
        int node = queue.poll();
        result.add(node);
        
        for (int neighbor : adj.get(node)) {
            indegree[neighbor]--;
            if (indegree[neighbor] == 0) {
                queue.offer(neighbor);
            }
        }
    }
    
    return result.size() == n ? result : new ArrayList<>(); // Check for cycle
}
```
**LeetCode Problems**: 207, 210, 269, 444, 310
**Complexity**: Time O(V + E), Space O(V)

### Pattern 6: Shortest Path Algorithms

#### Dijkstra's Algorithm (Single Source, Non-negative weights)
```java
public int[] dijkstra(int start, List<List<int[]>> adj) {
    int n = adj.size();
    int[] dist = new int[n];
    Arrays.fill(dist, Integer.MAX_VALUE);
    dist[start] = 0;
    
    PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]); // [node, distance]
    pq.offer(new int[]{start, 0});
    
    while (!pq.isEmpty()) {
        int[] curr = pq.poll();
        int node = curr[0], d = curr[1];
        
        if (d > dist[node]) continue; // Skip if we've found better path
        
        for (int[] edge : adj.get(node)) {
            int neighbor = edge[0], weight = edge[1];
            int newDist = dist[node] + weight;
            
            if (newDist < dist[neighbor]) {
                dist[neighbor] = newDist;
                pq.offer(new int[]{neighbor, newDist});
            }
        }
    }
    return dist;
}
```
**LeetCode Problems**: 743, 787, 1514, 1631, 1928
**Complexity**: Time O((V + E) log V), Space O(V)

### Pattern 7: Union-Find (Disjoint Set Union)
**When to use**: Dynamic connectivity, detecting cycles in undirected graphs
**Key insight**: Efficiently track connected components with path compression and union by rank

```java
class UnionFind {
    private int[] parent, rank;
    private int components;
    
    public UnionFind(int n) {
        parent = new int[n];
        rank = new int[n];
        components = n;
        for (int i = 0; i < n; i++) parent[i] = i;
    }
    
    public int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]); // Path compression
        }
        return parent[x];
    }
    
    public boolean union(int x, int y) {
        int rootX = find(x), rootY = find(y);
        if (rootX == rootY) return false; // Already connected
        
        // Union by rank
        if (rank[rootX] < rank[rootY]) {
            parent[rootX] = rootY;
        } else if (rank[rootX] > rank[rootY]) {
            parent[rootY] = rootX;
        } else {
            parent[rootY] = rootX;
            rank[rootX]++;
        }
        components--;
        return true;
    }
    
    public boolean connected(int x, int y) {
        return find(x) == find(y);
    }
    
    public int getComponents() {
        return components;
    }
}
```
**LeetCode Problems**: 684, 685, 721, 947, 990, 1319
**Complexity**: Time O(α(n)) per operation, Space O(n)

### Pattern 8: Grid Traversal (Missing from Original)
**When to use**: 2D grid problems, island problems, boundary problems
**Key insight**: Treat grid as implicit graph with 4-directional or 8-directional connections

```java
// 4-directional traversal
int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

public void dfsGrid(int[][] grid, int row, int col, boolean[][] visited) {
    if (row < 0 || row >= grid.length || col < 0 || col >= grid[0].length || 
        visited[row][col] || grid[row][col] == 0) {
        return;
    }
    
    visited[row][col] = true;
    // Process current cell
    
    for (int[] dir : directions) {
        int newRow = row + dir[0];
        int newCol = col + dir[1];
        dfsGrid(grid, newRow, newCol, visited);
    }
}

// BFS for grid
public void bfsGrid(int[][] grid, int startRow, int startCol) {
    int m = grid.length, n = grid[0].length;
    boolean[][] visited = new boolean[m][n];
    Queue<int[]> queue = new LinkedList<>();
    
    queue.offer(new int[]{startRow, startCol});
    visited[startRow][startCol] = true;
    
    while (!queue.isEmpty()) {
        int[] curr = queue.poll();
        int row = curr[0], col = curr[1];
        
        for (int[] dir : directions) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];
            
            if (newRow >= 0 && newRow < m && newCol >= 0 && newCol < n && 
                !visited[newRow][newCol] && grid[newRow][newCol] == 1) {
                visited[newRow][newCol] = true;
                queue.offer(new int[]{newRow, newCol});
            }
        }
    }
}
```
**LeetCode Problems**: 200, 130, 417, 733, 1020, 1254
**Complexity**: Time O(m × n), Space O(m × n)

### Pattern 9: Bipartite Graph (Missing from Original)
**When to use**: Two-coloring problems, checking if graph can be divided into two sets
**Key insight**: Use BFS/DFS to color nodes with alternating colors

```java
public boolean isBipartite(int[][] graph) {
    int n = graph.length;
    int[] color = new int[n]; // 0=uncolored, 1=red, -1=blue
    
    for (int i = 0; i < n; i++) {
        if (color[i] == 0 && !dfsColor(graph, i, 1, color)) {
            return false;
        }
    }
    return true;
}

private boolean dfsColor(int[][] graph, int node, int c, int[] color) {
    color[node] = c;
    
    for (int neighbor : graph[node]) {
        if (color[neighbor] == c) return false; // Same color as neighbor
        if (color[neighbor] == 0 && !dfsColor(graph, neighbor, -c, color)) {
            return false;
        }
    }
    return true;
}
```
**LeetCode Problems**: 785, 886
**Complexity**: Time O(V + E), Space O(V)

### Pattern 10: Tarjan's Algorithm - Finding Bridges and Articulation Points (Missing from Original)
**When to use**: Finding critical connections, bridges, cut vertices
**Key insight**: Use DFS with discovery time and low-link values

```java
public List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {
    List<List<Integer>> graph = new ArrayList<>();
    for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
    
    // Build adjacency list
    for (List<Integer> conn : connections) {
        graph.get(conn.get(0)).add(conn.get(1));
        graph.get(conn.get(1)).add(conn.get(0));
    }
    
    List<List<Integer>> bridges = new ArrayList<>();
    boolean[] visited = new boolean[n];
    int[] disc = new int[n]; // Discovery time
    int[] low = new int[n];  // Low-link value
    int[] parent = new int[n];
    Arrays.fill(parent, -1);
    
    for (int i = 0; i < n; i++) {
        if (!visited[i]) {
            bridgeUtil(i, visited, disc, low, parent, graph, bridges, new int[]{0});
        }
    }
    
    return bridges;
}

private void bridgeUtil(int u, boolean[] visited, int[] disc, int[] low, int[] parent,
                       List<List<Integer>> graph, List<List<Integer>> bridges, int[] time) {
    visited[u] = true;
    disc[u] = low[u] = ++time[0];
    
    for (int v : graph.get(u)) {
        if (!visited[v]) {
            parent[v] = u;
            bridgeUtil(v, visited, disc, low, parent, graph, bridges, time);
            
            low[u] = Math.min(low[u], low[v]);
            
            // If low value of v is more than discovery value of u, then u-v is a bridge
            if (low[v] > disc[u]) {
                bridges.add(Arrays.asList(u, v));
            }
        } else if (v != parent[u]) {
            low[u] = Math.min(low[u], disc[v]);
        }
    }
}
```
**LeetCode Problems**: 1192
**Complexity**: Time O(V + E), Space O(V)

## 📝 LeetCode 150 Problems Categorized

### Easy Problems
1. **200. Number of Islands** - Connected Components (DFS/BFS)
2. **733. Flood Fill** - Grid DFS
3. **695. Max Area of Island** - Connected Components with size

### Medium Problems
1. **207. Course Schedule** - Cycle Detection in Directed Graph
2. **210. Course Schedule II** - Topological Sort
3. **127. Word Ladder** - BFS Shortest Path
4. **133. Clone Graph** - Graph Traversal
5. **417. Pacific Atlantic Water Flow** - Multi-source DFS
6. **130. Surrounded Regions** - Boundary DFS
7. **684. Redundant Connection** - Union-Find Cycle Detection
8. **547. Number of Provinces** - Connected Components
9. **785. Is Graph Bipartite?** - Graph Coloring
10. **797. All Paths From Source to Target** - DFS Path Finding
11. **743. Network Delay Time** - Dijkstra's Algorithm
12. **787. Cheapest Flights Within K Stops** - Modified Dijkstra/Bellman-Ford

### Hard Problems
1. **269. Alien Dictionary** - Topological Sort with String Processing
2. **1192. Critical Connections in a Network** - Tarjan's Algorithm
3. **685. Redundant Connection II** - Advanced Union-Find

## 🎯 Implementation Strategies

### Strategy 1: Build Graph First
```java
// From edge list to adjacency list
public List<List<Integer>> buildGraph(int n, int[][] edges) {
    List<List<Integer>> graph = new ArrayList<>();
    for (int i = 0; i < n; i++) {
        graph.add(new ArrayList<>());
    }
    for (int[] edge : edges) {
        graph.get(edge[0]).add(edge[1]);
        // For undirected: graph.get(edge[1]).add(edge[0]);
    }
    return graph;
}
```

### Strategy 2: Grid to Graph Mapping
```java
// Convert 2D coordinates to 1D index
int index = row * cols + col;
int row = index / cols;
int col = index % cols;
```

### Strategy 3: Multi-Source BFS
```java
// Start BFS from multiple sources simultaneously
Queue<int[]> queue = new LinkedList<>();
for (int i = 0; i < m; i++) {
    for (int j = 0; j < n; j++) {
        if (grid[i][j] == 1) { // Source condition
            queue.offer(new int[]{i, j});
            visited[i][j] = true;
        }
    }
}
// Continue with normal BFS
```

## ⚠️ Common Mistakes and Tips

### Mistake 1: Not handling disconnected components
```java
// WRONG - only checks from node 0
if (dfs(0, graph, visited)) return true;

// CORRECT - check all unvisited nodes
for (int i = 0; i < n; i++) {
    if (!visited[i] && dfs(i, graph, visited)) return true;
}
```

### Mistake 2: Infinite loops in cycle detection
```java
// WRONG - no parent tracking in undirected graph
if (visited[neighbor]) return true; // This will always trigger

// CORRECT
if (visited[neighbor] && neighbor != parent) return true;
```

### Mistake 3: Wrong BFS distance calculation
```java
// WRONG - updating distance after adding to queue
queue.offer(neighbor);
dist[neighbor] = dist[node] + 1;

// CORRECT - update before adding to queue
dist[neighbor] = dist[node] + 1;
queue.offer(neighbor);
```

### Mistake 4: Not handling edge cases in grid problems
```java
// Always check bounds first
if (row < 0 || row >= m || col < 0 || col >= n) return;
if (visited[row][col] || grid[row][col] == 0) return;
```

### Mistake 5: Forgetting to reset visited array between calls
```java
// If solving multiple test cases or need fresh start
Arrays.fill(visited, false);
```

## 🚀 Template Selection Quick Reference

| Problem Keywords | Template | Algorithm | Complexity |
|-----------------|----------|-----------|------------|
| "islands", "connected components" | DFS/BFS | Connected Components | O(V + E) |
| "shortest path", "minimum steps" | BFS/Dijkstra | Shortest Path | BFS: O(V + E), Dijkstra: O((V + E) log V) |
| "prerequisites", "dependencies" | Topological Sort | Kahn's/DFS | O(V + E) |
| "cycle", "circular dependency" | DFS with colors | Cycle Detection | O(V + E) |
| "minimum cost to connect" | MST | Kruskal/Prim | O(E log E) |
| "dynamic connectivity" | Union-Find | DSU | O(α(n)) per op |
| "critical connections" | Tarjan's | Bridge Finding | O(V + E) |
| "bipartite", "two groups" | Graph Coloring | DFS/BFS | O(V + E) |

## 🧠 Memory Patterns for Interviews

### Pattern Recognition Checklist:
1. **Graph Structure Keywords**: nodes, edges, connections, network, paths
2. **Traversal Keywords**: explore, visit, search, find all
3. **Optimization Keywords**: shortest, minimum, maximum, optimal
4. **Relationship Keywords**: connected, dependent, prerequisite, neighbor

### Quick Decision Framework:
1. **Need shortest path?** → BFS (unweighted) or Dijkstra (weighted)
2. **Need to find groups?** → DFS/BFS for connected components
3. **Need ordering with dependencies?** → Topological Sort
4. **Need to detect cycles?** → DFS with appropriate tracking
5. **Need dynamic connectivity?** → Union-Find
6. **Working with grid?** → Grid DFS/BFS with direction arrays

### Time/Space Complexity Quick Reference:
- **V** = number of vertices/nodes
- **E** = number of edges
- **Most graph algorithms**: O(V + E) time
- **Dijkstra**: O((V + E) log V) time
- **Floyd-Warshall**: O(V³) time
- **Union-Find**: O(α(n)) ≈ O(1) per operation
- **Space**: Usually O(V) for visited arrays, O(V + E) for adjacency lists

This comprehensive guide covers all the essential graph patterns you'll encounter in LeetCode 150 and technical interviews. Practice implementing these templates and recognizing the patterns - most graph problems are variations of these core concepts!
