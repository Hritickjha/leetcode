import java.util.*;

class Solution {
    class Edge implements Comparable<Edge> {
        int y, x1, x2, type;
        Edge(int y, int x1, int x2, int type) {
            this.y = y; this.x1 = x1; this.x2 = x2; this.type = type;
        }
        public int compareTo(Edge other) {
            return Integer.compare(this.y, other.y);
        }
    }

    private int[] count;
    private double[] length;
    private int[] xCoords;

    public double separateSquares(int[][] squares) {
        int n = squares.length;
        Edge[] edges = new Edge[2 * n];
        TreeSet<Integer> xSet = new TreeSet<>();
        
        for (int i = 0; i < n; i++) {
            int x = squares[i][0], y = squares[i][1], l = squares[i][2];
            edges[2 * i] = new Edge(y, x, x + l, 1);
            edges[2 * i + 1] = new Edge(y + l, x, x + l, -1);
            xSet.add(x);
            xSet.add(x + l);
        }
        Arrays.sort(edges);

        // Coordinate compression for X
        xCoords = new int[xSet.size()];
        int idx = 0;
        for (int x : xSet) xCoords[idx++] = x;
        
        int m = xCoords.length;
        count = new int[4 * m];
        length = new double[4 * m];

        // First pass: Calculate total area
        double totalArea = 0;
        for (int i = 0; i < edges.length - 1; i++) {
            update(1, 0, m - 2, edges[i].x1, edges[i].x2, edges[i].type);
            totalArea += length[1] * (edges[i + 1].y - edges[i].y);
        }

        // Second pass: Find the y that splits the area
        Arrays.fill(count, 0);
        Arrays.fill(length, 0);
        double target = totalArea / 2.0;
        double currentArea = 0;

        for (int i = 0; i < edges.length - 1; i++) {
            update(1, 0, m - 2, edges[i].x1, edges[i].x2, edges[i].type);
            double stripArea = length[1] * (edges[i + 1].y - edges[i].y);
            
            if (currentArea + stripArea >= target - 1e-9) {
                if (length[1] == 0) return edges[i].y; 
                return edges[i].y + (target - currentArea) / length[1];
            }
            currentArea += stripArea;
        }

        return edges[edges.length - 1].y;
    }

    private void update(int node, int start, int end, int qL, int qR, int val) {
        if (xCoords[start] >= qR || xCoords[end + 1] <= qL) return;
        
        if (xCoords[start] >= qL && xCoords[end + 1] <= qR) {
            count[node] += val;
        } else {
            int mid = (start + end) / 2;
            update(node * 2, start, mid, qL, qR, val);
            update(node * 2 + 1, mid + 1, end, qL, qR, val);
        }

        if (count[node] > 0) {
            length[node] = xCoords[end + 1] - xCoords[start];
        } else if (start != end) {
            length[node] = length[node * 2] + length[node * 2 + 1];
        } else {
            length[node] = 0;
        }
    }
}