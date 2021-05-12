package LevelEditor;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LevelMaker extends Application{

	public static void main(String[] args) {
		launch();
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		
		Box[][] boxes = new Box[10][10];
		
		BorderPane pane = new BorderPane();
		BorderPane bottomPane = new BorderPane();
		pane.setBottom(bottomPane);
		Button save = new Button("Save");
		TextField tf = new TextField();
		bottomPane.setLeft(save);
		bottomPane.setRight(tf);
		
		save.setOnAction(e->{
			String fileName = tf.getText();
			if (!fileName.equals("")) {
				
				File saveFile = new File("LevelEditor/" + fileName + ".txt");
				try {
					PrintWriter writer = new PrintWriter(saveFile);
					for (int i = 0; i < boxes.length; i++) {
						for (int j = 0; j < boxes[i].length; j++) {
							writer.println(boxes[i][j].getType() + "," + i + "," + j);
						}
					}
					writer.close();
					
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		GridPane center = new GridPane();
		center.setPadding(new Insets(2, 2, 2, 2));
		center.setStyle("-fx-background-color: #262d33");
		center.setHgap(2);
		center.setVgap(2);
		pane.setCenter(center);
		 
		for(int row = 0;row<10;row++) {
			for(int column = 0;column<10;column++) {
				boxes[row][column] = new Box("Wall");
				center.add(boxes[row][column],column, row);
				boxes[row][column].setOnMouseClicked(e-> {
					if (e.getButton() == MouseButton.PRIMARY) {
						Box box = (Box)e.getSource();
						box.setType("Mirror");
					}
					else if (e.getButton() == MouseButton.SECONDARY) {
						Box box = (Box)e.getSource();
						box.setType("Wood");
					}
					else if (e.getButton() == MouseButton.MIDDLE) {
						Box box = (Box)e.getSource();
						box.setType("Empty");
					}
				});
			}	
		}

		
		stage.setTitle("LevelEditor");
		stage.setScene(new Scene(pane));
		stage.show();
		
	}

}
