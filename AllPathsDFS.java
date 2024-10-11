import java.util.ArrayList;
import java.util.List;

public class AllPathsDFS {

    public static class Cell {
        int x;
        int y;

        Cell(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "(" + x + "," + y + ")";
        }
    }

    public static List<List<Cell>> findAllPaths(char[][] table) {
        int[] end = {table.length - 1, table[0].length - 1};
        int[] start = {0, 0};
        return findAllPaths(table, start, end);  
    }
    public static List<List<Cell>> findAllPaths(char[][] table, int[] start, int[] end) {
        int sx = start[0], sy = start[1];
        int dx = end[0], dy = end[1];

        // If start or end is blocked ('*'), return
        if (table[sx][sy] == '*' || table[dx][dy] == '*') {
            return new ArrayList<>();
        }

        int m = table.length;
        int n = table[0].length;
        boolean[][] visited = new boolean[m][n]; // To mark visited cells
        List<Cell> path = new ArrayList<>(); // Current path
        List<List<Cell>> allPaths = new ArrayList<>(); // List of all paths
        
        dfs(table, sx, sy, dx, dy, visited, path, allPaths);
        System.out.println("All Possible Paths: " + allPaths);
        return allPaths;
    }

    private static void dfs(char[][] table, int x, int y, int dx, int dy, boolean[][] visited, List<Cell> path, List<List<Cell>> allPaths) {
        // Check bounds, if out of bounds or if cell is blocked ('*') or already visited, return
        if (x < 0 || x >= table.length || y < 0 || y >= table[0].length || table[x][y] == '*' || visited[x][y]) {
            return;
        }

        path.add(new Cell(x, y)); // Add current cell to path
        visited[x][y] = true;

        // If destination is reached, add the current path to allPaths
        if (x == dx && y == dy) {
            allPaths.add(new ArrayList<>(path)); // Add a copy of the current path
        } else {
            // Explore down and right
            dfs(table, x + 1, y, dx, dy, visited, path, allPaths); 
            dfs(table, x, y + 1, dx, dy, visited, path, allPaths);
        }

        // Backtrack
        path.remove(path.size() - 1);
        visited[x][y] = false;
    }

    public static void main(String[] args) {
        char[][] table = {
            {'.', '.', '.', '.'},
            {'.', '.', '.', '.'},
            {'.', '.', '*', '*'},
            {'.', '.', '.', '.'},
        };
        int[] start = {0, 0};
        int[] end = {3, 3};
        findAllPaths(table, start, end);
    }
}
