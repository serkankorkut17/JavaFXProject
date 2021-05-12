package LevelEditor;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LevelMaker extends Application{

	private int box_type;
	
	public static void main(String[] args) {
		launch();
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		
		Box[][] boxes = new Box[10][10];
		
		BorderPane pane = new BorderPane();
		BorderPane topPane = new BorderPane();
		BorderPane bottomPane = new BorderPane();
		pane.setTop(topPane);
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
							if(!boxes[i][j].getType().equals("Wall")) {
								writer.println(boxes[i][j].getType() + "," + i + "," + j);
							}	
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
					int box_type = ((Box)e.getSource()).box_type;
					if(box_type == 0) {
						((Box)e.getSource()).setType("Mirror");
					}
					else if(box_type == 1) {
						((Box)e.getSource()).setType("Wood");
					}
					else if(box_type == 2) {
						((Box)e.getSource()).setType("Empty");
					}
					else {
						((Box)e.getSource()).setType("Wall");
					}
					box_type++;
					if(box_type == 4) {
						box_type = 0;
					}
					((Box)e.getSource()).box_type = box_type;
				});
			}	
		}

		
		stage.setTitle("LevelEditor");
		stage.setScene(new Scene(pane));
		stage.show();
		
	}

}
