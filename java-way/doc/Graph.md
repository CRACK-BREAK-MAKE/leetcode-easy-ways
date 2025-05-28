# Understanding Graph Patterns

## The Core Intuition

Graph problems are fundamentally about **relationships** and **connectivity**. The key insight is understanding what you're searching for: **connectivity**, **shortest paths**, **cycles**, or **optimal traversals**.

## Graph Representation Patterns

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

## Core Graph Traversal Patterns

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

## Specialized Graph Patterns

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

// DFS-based topological sort
public List<Integer> topologicalSortDFS(List<List<Integer>> adj) {
    boolean[] visited = new boolean[adj.size()];
    Stack<Integer> stack = new Stack<>();
    
    for (int i = 0; i < adj.size(); i++) {
        if (!visited[i]) {
            dfsTopological(i, adj, visited, stack);
        }
    }
    
    List<Integer> result = new ArrayList<>();
    while (!stack.isEmpty()) {
        result.add(stack.pop());
    }
    return result;
}

private void dfsTopological(int node, List<List<Integer>> adj, boolean[] visited, Stack<Integer> stack) {
    visited[node] = true;
    
    for (int neighbor : adj.get(node)) {
        if (!visited[neighbor]) {
            dfsTopological(neighbor, adj, visited, stack);
        }
    }
    stack.push(node); // Add to stack after processing all neighbors
}
```

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

#### Floyd-Warshall (All Pairs Shortest Path)
```java
public void floydWarshall(int[][] dist) {
    int n = dist.length;
    
    for (int k = 0; k < n; k++) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (dist[i][k] != Integer.MAX_VALUE && dist[k][j] != Integer.MAX_VALUE) {
                    dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
                }
            }
        }
    }
}
```

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

### Pattern 8: Minimum Spanning Tree

#### Kruskal's Algorithm
```java
public int kruskalMST(int n, int[][] edges) {
    Arrays.sort(edges, (a, b) -> a[2] - b[2]); // Sort by weight
    UnionFind uf = new UnionFind(n);
    int mstWeight = 0, edgesUsed = 0;
    
    for (int[] edge : edges) {
        int u = edge[0], v = edge[1], weight = edge[2];
        if (uf.union(u, v)) {
            mstWeight += weight;
            edgesUsed++;
            if (edgesUsed == n - 1) break; // MST complete
        }
    }
    
    return edgesUsed == n - 1 ? mstWeight : -1; // Check if MST exists
}
```

## Graph Problem Categories

### Category 1: Connectivity Problems
- **Number of Islands**: Connected components in 2D grid
- **Friend Circles**: Connected components in adjacency matrix
- **Graph Valid Tree**: Check if graph is connected and acyclic

### Category 2: Path Problems
- **Word Ladder**: BFS for shortest transformation path
- **Clone Graph**: DFS/BFS to copy graph structure
- **Path with Maximum Probability**: Modified Dijkstra

### Category 3: Cycle and Ordering Problems
- **Course Schedule**: Cycle detection in directed graph
- **Alien Dictionary**: Topological sort to find character order
- **Detect Cycle in Directed Graph**: DFS with colors

### Category 4: Shortest Path Problems
- **Network Delay Time**: Single-source shortest path (Dijkstra)
- **Cheapest Flights**: Shortest path with constraints
- **Find the City**: All-pairs shortest path (Floyd-Warshall)

### Category 5: Tree Problems in Graphs
- **Minimum Spanning Tree**: Kruskal's or Prim's algorithm
- **Critical Connections**: Find bridges in graph
- **Redundant Connection**: Union-Find to detect cycle

## Decision Tree for Graph Problems

1. **What am I looking for?**
    - **Connectivity/Components** → DFS/BFS or Union-Find
    - **Shortest Path** → BFS (unweighted) or Dijkstra/Floyd-Warshall (weighted)
    - **Cycles** → DFS with colors (directed) or DFS with parent tracking (undirected)
    - **Ordering with dependencies** → Topological Sort
    - **Minimum cost to connect all** → MST algorithms

2. **What type of graph?**
    - **Unweighted** → BFS for shortest path
    - **Weighted, non-negative** → Dijkstra
    - **Weighted, can have negative** → Bellman-Ford or Floyd-Warshall
    - **DAG** → Can use DFS-based algorithms efficiently

3. **What's the constraint?**
    - **Need all-pairs shortest path** → Floyd-Warshall
    - **Dynamic connectivity queries** → Union-Find
    - **Memory constraints** → Consider space-efficient algorithms

## Time Complexity Patterns

- **DFS/BFS**: O(V + E) where V = vertices, E = edges
- **Dijkstra**: O((V + E) log V) with priority queue
- **Floyd-Warshall**: O(V³)
- **Union-Find**: O(α(n)) per operation (nearly constant)
- **Topological Sort**: O(V + E)
- **MST (Kruskal)**: O(E log E) for sorting edges

## Space Complexity Patterns

- **Adjacency List**: O(V + E)
- **Adjacency Matrix**: O(V²)
- **Recursive DFS**: O(V) for call stack
- **BFS Queue**: O(V) in worst case

## Red Flags for Graph Problems

### Problem Statement Keywords:
- "Connected components", "islands", "groups"
- "Shortest path", "minimum cost", "fewest steps"
- "Prerequisites", "dependencies", "ordering"
- "Cycle", "loop", "circular dependency"
- "Network", "connections", "relationships"
- "Minimum spanning", "connect all with minimum cost"

### Structural Indicators:
- Nodes with relationships/connections
- Need to traverse or search through connections
- Optimization over network structure
- Dependency relationships
- Grid-based problems (often implicit graphs)

## Template Selection Guide

| Problem Type | Algorithm | When to Use | Time Complexity |
|-------------|-----------|-------------|----------------|
| Find connected components | DFS/BFS | Separate groups in graph | O(V + E) |
| Shortest path (unweighted) | BFS | Equal cost edges | O(V + E) |
| Shortest path (weighted) | Dijkstra | Non-negative weights | O((V+E) log V) |
| All pairs shortest path | Floyd-Warshall | Dense graph, all distances | O(V³) |
| Cycle detection | DFS with colors | Directed graphs | O(V + E) |
| Topological ordering | Kahn's or DFS | DAG with dependencies | O(V + E) |
| Dynamic connectivity | Union-Find | Incremental edge additions | O(α(n)) per op |
| Minimum spanning tree | Kruskal/Prim | Connect all nodes minimally | O(E log E) |
