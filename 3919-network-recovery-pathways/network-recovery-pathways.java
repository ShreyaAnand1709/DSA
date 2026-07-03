import java.util.*;

class Solution {

    static class Edge {
        int to;
        int cost;

        Edge(int to, int cost) {
            this.to = to;
            this.cost = cost;
        }
    }

    public int findMaxPathScore(int[][] edges, boolean[] online, long k) {
        int n = online.length;

        List<Edge>[] graph = new ArrayList[n];
        int[] indegree = new int[n];

        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        List<Integer> costs = new ArrayList<>();

        for (int[] e : edges) {
            int u = e[0];
            int v = e[1];
            int c = e[2];

            graph[u].add(new Edge(v, c));
            indegree[v]++;
            costs.add(c);
        }

        // Topological Sort
        int[] topo = new int[n];
        int idx = 0;

        Queue<Integer> q = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (indegree[i] == 0) {
                q.offer(i);
            }
        }

        while (!q.isEmpty()) {
            int u = q.poll();
            topo[idx++] = u;

            for (Edge e : graph[u]) {
                if (--indegree[e.to] == 0) {
                    q.offer(e.to);
                }
            }
        }

        // Check if any valid path exists at all
        if (!canReach(0, graph, topo, online, k, n)) {
            return -1;
        }

        Collections.sort(costs);

        int left = 0;
        int right = costs.size() - 1;
        int ans = 0;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            int threshold = costs.get(mid);

            if (canReach(threshold, graph, topo, online, k, n)) {
                ans = threshold;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return ans;
    }

    private boolean canReach(
            int threshold,
            List<Edge>[] graph,
            int[] topo,
            boolean[] online,
            long k,
            int n) {

        long INF = Long.MAX_VALUE / 4;
        long[] dist = new long[n];
        Arrays.fill(dist, INF);

        dist[0] = 0;

        for (int u : topo) {

            if (dist[u] == INF) {
                continue;
            }

            if (u != 0 && u != n - 1 && !online[u]) {
                continue;
            }

            for (Edge e : graph[u]) {

                if (e.cost < threshold) {
                    continue;
                }

                int v = e.to;

                if (v != 0 && v != n - 1 && !online[v]) {
                    continue;
                }

                long newCost = dist[u] + e.cost;

                if (newCost < dist[v]) {
                    dist[v] = newCost;
                }
            }
        }

        return dist[n - 1] <= k;
    }
}