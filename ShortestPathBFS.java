import java.util.LinkedList;

public class ShortestPathBFS {

    public static class Cell {
        int x;
        int y;
        int dist;  	// distance
        Cell prev;  // parent cell in the path

        Cell(int x, int y, int dist, Cell prev) {
            this.x = x;
            this.y = y;
            this.dist = dist;
            this.prev = prev;
        }

        @Override
        public String toString() {
            return "(" + x + "," + y + ")";
        }
    }

    public static LinkedList<Cell> shortestPath(char[][] table) {
        int[] end = {table.length - 1, table[0].length - 1};
        int[] start = {0, 0};
        return shortestPath(table, start, end);  
    }
    // BFS, Time O(n^2), Space O(n^2)
    public static LinkedList<Cell> shortestPath(char[][] table, int[] start, int[] end) {
        int sx = start[0], sy = start[1];
        int dx = end[0], dy = end[1];

        // If start or end value is '*', return (blocked path)
        if (table[sx][sy] == '*' || table[dx][dy] == '*')
            return new LinkedList<>();

        int m = table.length;
        int n = table[0].length;
        Cell[][] cells = new Cell[m][n];

        // Initialize the cells
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (table[i][j] != '*') {
                    cells[i][j] = new Cell(i, j, Integer.MAX_VALUE, null);
                }
            }
        }

        LinkedList<Cell> queue = new LinkedList<>();
        Cell src = cells[sx][sy];
        src.dist = 0;
        queue.add(src);
        Cell dest = null;
        Cell p;

        while ((p = queue.poll()) != null) {
            // Find destination
            if (p.x == dx && p.y == dy) {
                dest = p;
                break;
            }

            // Move in all 4 directions: up, down, left, right
            visit(cells, queue, p.x + 1, p.y, p);  // down
            visit(cells, queue, p.x, p.y + 1, p);  // right
            // visit(cells, queue, p.x - 1, p.y, p);  // up
            // visit(cells, queue, p.x, p.y - 1, p);  // left
        }

        if (dest == null) {
            System.out.println("No path found.");
                return new LinkedList<>();
        } else {
            LinkedList<Cell> path = new LinkedList<>();
            p = dest;
            do {
                path.addFirst(p);
            } while ((p = p.prev) != null);
            System.out.println(path);
            return path;
        }
    }

    // Function to update cell visiting status
    static void visit(Cell[][] cells, LinkedList<Cell> queue, int x, int y, Cell parent) {
        if (x < 0 || x >= cells.length || y < 0 || y >= cells[0].length || cells[x][y] == null) {
            return;
        }

        int dist = parent.dist + 1;
        Cell p = cells[x][y];
        if (dist < p.dist) {
            p.dist = dist;
            p.prev = parent;
            queue.add(p);
        }
    }

    public static void main(String[] args) {
        char[][] table = {
            {'.', '.', '.', '.'},
            {'.', '.', '.', '.'},
            {'.', '.', '*', '*'},
            {'.', '.', '.', '.'},
        };
        int[] start = {0, 1};
        int[] end = {3, 0};
        shortestPath(table, start, end);
    }
}
