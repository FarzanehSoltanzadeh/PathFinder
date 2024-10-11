/*
 * Farzaneh Soltanzadeh
 * 2020-11-7
 */
public class PathCounter {

    public int counter(char[][] table, int row, int column) {
        int[][] numberOfPah = new int[row][column];
        boolean isStarInFirstRow = false;
        boolean isStarInFirstColumn = false;

        for (int i = 0; i < row; ++i) {
            for (int j = 0; j < column; ++j) {
                if (i == 0 && table[0][j] == '*') isStarInFirstRow = true;
                if (j == 0 && table[i][0] == '*') isStarInFirstColumn = true;
                if (i == 0)
                    numberOfPah[0][j] = isStarInFirstRow ? 0 : 1;
                if (j == 0)
                    numberOfPah[i][0] = isStarInFirstColumn ? 0 : 1;
                if (table[i][j] == '*') numberOfPah[i][j] = 0;
            }
        }
        
        for (int i = 1; i < row; ++i)
            for (int j = 1; j < column; ++j)
                if (table[i][j] != '*')
                    numberOfPah[i][j] = numberOfPah[i - 1][j] + numberOfPah[i][j - 1];

        return numberOfPah[row - 1][column - 1];
    }

    public static void main(String[] args) {
        PathCounter pathCounter = new PathCounter();
        
        char[][] table = {
            {'.', '.', '.', '.'},
            {'.', '.', '.', '.'},
            {'.', '.', '*', '*'},
            {'.', '.', '.', '.'},
        };

        int row = table.length;
        int column = table[0].length;

        int result = pathCounter.counter(table, row, column);
        System.out.println("Number of ways to reach the bottom-right corner: " + result);
    }
}
