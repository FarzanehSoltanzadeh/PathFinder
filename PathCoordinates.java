/*
 * Farzaneh Soltanzadeh
 * 2020-11-7
 */
import javafx.scene.control.ListView;
import java.util.Stack;

public class PathCoordinates {
    private static char[][] table;
    private static Stack<String> stack = new Stack<>();
    private static ListView<String> coordinatesList;
    private static String temp ="";

    public static void setTable(char[][] table) {
        PathCoordinates.table = table;
    }

    public static ListView<String> coordinates(char[][] myTable,int row, int column, int numberOfPath) {
        coordinatesList = new ListView<>();

        setTable(myTable);

        int i = 0 , j = 0 , counter = 0;
        table[row -1][column -1] = 'E';//end point

        while (counter < numberOfPath) {

            while (canGoDown(i, j).equals("Yes") && table[i][j] != 'y') {
                direction(i, j);
                stack.push(stringFormat(i, j));
                if (table[i][j] == 'B') table[i][j] = 'x';//B-->Down
                ++i;
            }
            if (canGoRight(i, j).equals("Yes")) {
                direction(i, j);
                stack.push(stringFormat(i, j));
                ++j;
            }

            if (table[i][j] == 'E') {
                temp = "";
                stack.forEach(x-> {
                    temp += (x+" ");
                });
                coordinatesList.getItems().add(temp);
                //stack.forEach(x -> System.out.print(x + " "));
                //System.out.println();
                ++counter;
                if (counter== numberOfPath) break;
            }

            if (canGoRight(i, j).equals("No") && canGoDown(i, j).equals("No")) {
                while (previousCell() != 'x') {
                    if (previousCell() == 'R' || previousCell() == 'B') {
                        --j;
                        stack.pop();
                    }
                    if (previousCell() == 'D') {
                        --i;
                        stack.pop();
                    }
                }
                --i;
                stack.pop();
                table[i][j] = 'y';
            }

        }//end while
        while (!stack.empty()) stack.pop();
        return coordinatesList;
    }

    public static String canGoDown(int i, int j){
        if (i+1 >= table.length) return "No";
        if (table[i+1][j] == '*') return "No";
        return "Yes";
    }

    public static String canGoRight(int i, int j){
        if (j+1 >= table[0].length) return "No";
        if (table[i][j+1] == '*') return "No";
        return "Yes";
    }

    public static String stringFormat(int i, int j){
        return "(" +i+ " , " +j+ ")";
    }

    public static char previousCell(){
        char value = '.';
        if (!stack.empty()){
            String temp = stack.peek();
            int previous_i = Integer.parseInt(String.valueOf(temp.charAt(1)));
            int previous_j = Integer.parseInt(String.valueOf(temp.charAt(5)));
            value = table[previous_i][previous_j];
        }
        return value;
    }

    public static void direction(int i, int j){
        if ( (table[i][j]=='.'||table[i][j]=='y') && canGoDown(i,j).equals("Yes") && canGoRight(i,j).equals("Yes"))
            table[i][j] ='B';//Both.Can go Dawn and Right
        if (table[i][j]=='.' && canGoDown(i,j).equals("Yes") && canGoRight(i,j).equals("No"))
            table[i][j] = 'D';//just Down
        if (table[i][j]=='.' && canGoDown(i,j).equals("No") && canGoRight(i,j).equals("Yes"))
            table[i][j] = 'R';//just Right
    }

}
