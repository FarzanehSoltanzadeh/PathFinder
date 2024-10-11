/*
 * Farzaneh-Soltanzadeh
 * 2020-12-11
 */

 import java.util.LinkedList;
import java.util.List;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
 import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
 import javafx.scene.control.*;
 import javafx.scene.image.Image;
 import javafx.scene.image.ImageView;
 import javafx.scene.layout.*;
 import javafx.stage.Modality;
 import javafx.stage.Stage;
 import javafx.stage.StageStyle;
 

 public class Main extends Application {
     private static boolean mode_2020 = false;
     private GridPane table;
     private char[][] table_Character;
 
     @Override
     public void start(Stage primaryStage) throws Exception {
 
         BorderPane root = new BorderPane();
         primaryStage.setTitle("Find Path");
 
         /* Images */
         Image homeImage = new Image("file:///E:/University/JavaFX learning/Path Project/PathProjectFX/pics/location.png");
         Image virusImage = new Image("file:///E:/University/JavaFX learning/Path Project/PathProjectFX/pics/virus.png");
         Image personImage = new Image("file:///E:/University/JavaFX learning/Path Project/PathProjectFX/pics/person.png");
 
         /* Row and column */
         // label
         Label number_of_rows_column_label = new Label("Specify the number of rows and columns");
         // textField
         TextField number_of_rows_column_textField = new TextField();
         number_of_rows_column_textField.setText("3 * 5");
         number_of_rows_column_textField.setMaxSize(50, 10);
         // button
         Button confirmButton = new Button("Confirm");
         confirmButton.setId("confirm-button");
         confirmButton.setOnAction(e -> {
             table = new GridPane();
             table.setAlignment(Pos.CENTER);
             String[] x_and_y = number_of_rows_column_textField.getText().split("\\s+\\*+\\s");
             int x_row = Integer.parseInt(x_and_y[0]);
             int y_column = Integer.parseInt(x_and_y[1]);
 
             if (x_row > 4 || x_row < 1 || y_column < 1 || y_column > 8) {
                 showAlert("Invalid input", "Number of columns must <8 and >1\nNumber of rows must be <4 and >1");
             } else {
                 table_Character = new char[x_row][y_column];
                 createTable(x_row, y_column, personImage, homeImage, virusImage);
                 setupOkButton(root);
             }
         });
 
         /* Top of BorderPane */
         HBox hBox1 = new HBox(10);
         hBox1.getChildren().addAll(number_of_rows_column_label, number_of_rows_column_textField, confirmButton);
         hBox1.setPadding(new Insets(20, 10, 20, 10));
         hBox1.setAlignment(Pos.CENTER);
 
         /* Root pane */
         root.setTop(hBox1);
         Scene scene = new Scene(root);
         scene.getStylesheets().add("stylesheets/mainStage.css");
         primaryStage.setMaximized(true);
         primaryStage.setScene(scene);
         primaryStage.show();
     }
 
     /**
      * Creates the table based on the specified number of rows and columns.
      */
     private void createTable(int x_row, int y_column, Image personImage, Image homeImage, Image virusImage) {
        
        for (int i = 0; i < x_row; ++i) {
             for (int j = 0; j < y_column; ++j) {
                 table_Character[i][j] = '.';
 
                 Button tableCell = new Button();
                 tableCell.setMinSize(175, 175);
                 if (i == 0 && j == 0) tableCell.setGraphic(new ImageView(personImage));
                 if (i == x_row - 1 && j == y_column - 1) tableCell.setGraphic(new ImageView(homeImage));
                 table.add(tableCell, j, i);
 
                 tableCell.setOnMouseClicked(event -> handleTableCellClick(event, x_row, y_column, virusImage));
             }
         }
     }
 
     /**
      * Handles the logic when a table button is clicked.
      */
     private void handleTableCellClick(javafx.scene.input.MouseEvent event, int x_row, int y_column, Image virusImage) {
         resetTableCharacter(x_row, y_column);
         Button selectedCell = (Button) event.getSource();
         int i_selectedCell_Row = GridPane.getRowIndex(selectedCell);
         int j_selectedCell_Column = GridPane.getColumnIndex(selectedCell);
         ImageView selectedCell_Image = (ImageView) selectedCell.getGraphic();
 
         if (selectedCell.getGraphic() != null && selectedCell_Image.getImage().equals(virusImage)) {
             selectedCell.setGraphic(null);
             table_Character[i_selectedCell_Row][j_selectedCell_Column] = '.';
         } else {
             selectedCell.setGraphic(new ImageView(virusImage));
             table_Character[i_selectedCell_Row][j_selectedCell_Column] = '*';
         }
     }
 

     private void resetTableCharacter(int x_row, int y_column) {
         for (int a = 0; a < x_row; ++a)
             for (int b = 0; b < y_column; ++b)
                 if (table_Character[a][b] != '.' && table_Character[a][b] != '*') 
                     table_Character[a][b] = '.';  
     }
 

     private void setupOkButton(BorderPane root) {
         Button okButton = new Button("Find!");
         okButton.setId("confirm-button");
         okButton.setMinSize(100, 30);
         okButton.setOnAction(event -> {
            int numberOfPath;
            ListView<String> coordinates_list;

             if (mode_2020) {
                numberOfPath = new PathCounter().counter(table_Character, table_Character.length, table_Character[0].length);
                coordinates_list = PathCoordinates.coordinates(table_Character, table_Character.length, table_Character[0].length, numberOfPath);
             } else {
                highlightShortestPath();
                coordinates_list = new ListView<>();
                List<List<AllPathsDFS.Cell>> allPaths = AllPathsDFS.findAllPaths(table_Character);
                for (List<AllPathsDFS.Cell> path : allPaths) {
                    StringBuilder sb = new StringBuilder();
                    for (AllPathsDFS.Cell cell : path) 
                        sb.append(cell.toString()).append(" ");
                    
                    coordinates_list.getItems().add(sb.toString());
            }
                numberOfPath = allPaths.size();
            }

            if (numberOfPath> 0) createNewWindow(numberOfPath, coordinates_list);
         });
 
         /* Center of BorderPane */
         table.setGridLinesVisible(true);
         VBox vBox2 = new VBox(15);
         vBox2.setAlignment(Pos.CENTER);
         vBox2.getChildren().addAll(table, okButton);
         root.setCenter(vBox2);
 
         /* Bottom of BorderPane */
         Label informationLabel = new Label("Click to insert the virus.\nThen press OK button.",
                 new ImageView("file:///E:/University/JavaFX learning/Path Project/PathProjectFX/pics/help.png"));
         informationLabel.setPadding(new Insets(1, 20, 27, 20));
         informationLabel.setId("infoLabel-label");
         root.setBottom(informationLabel);
     }
     
     private void highlightShortestPath() {
        LinkedList<ShortestPathBFS.Cell> path = ShortestPathBFS.shortestPath(table_Character);
        if (path.isEmpty()) {
            showAlert("No Path", "No path found from start to end.");
            return;
        }
        for (ShortestPathBFS.Cell cell : path) {
            Button cellButton = getNodeByRowColumnIndex(cell.x, cell.y, table);
            if (cellButton != null) {
                cellButton.setStyle("-fx-background-color: #FFCC00");
            }
        }
    }
    
    private Button getNodeByRowColumnIndex(final int row, final int column, GridPane gridPane) {
        for (Node node : gridPane.getChildren()) 
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) 
                return (Button) node;
            
        return null;
    }
    
     private void showAlert(String header, String content) {
         Alert alert = new Alert(Alert.AlertType.ERROR);
         alert.setHeaderText(header);
         alert.setContentText(content);
         alert.show();
     }
 
     /**
      * Creates a new window to print possible paths
      */
     private void createNewWindow(int numberOfPath, ListView<String> coordinateList) {
         Stage stage = new Stage();
         stage.setTitle("Information");
         stage.initModality(Modality.APPLICATION_MODAL);
         stage.initStyle(StageStyle.UTILITY);
 
         // Titled pane
         TitledPane tilePane = new TitledPane();
         tilePane.setText("Possible Paths from Start to End");
         tilePane.setCollapsible(false);
         // label to show number of path
         Label label = new Label("#Possible ways: " + numberOfPath);
         // listView to add coordinates
         ListView<String> coordinates = coordinateList;
 
         VBox vBox = new VBox(10);
         vBox.getChildren().addAll(label, coordinates);
         tilePane.setContent(vBox);
 
         Scene scene = new Scene(tilePane);
         scene.getStylesheets().add("stylesheets/resultStage.css");

         stage.setOnCloseRequest(event -> {
            resetButtonColors(table); // Reset the table colors when the window is closed
        });
         stage.setHeight(350);
         stage.setScene(scene);
         stage.show();
     }

     private void resetButtonColors(GridPane gridPane) {
        for (Node node : gridPane.getChildren()) {
            if (node instanceof Button) {
                Button button = (Button) node;
                button.setStyle("-fx-botton-color: transparent;");  // Reset the background color to transparent
            }
        }
    }
 
     public static void main(String[] args) {
         launch(args);
     }
 }
 