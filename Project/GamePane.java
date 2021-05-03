
import java.io.File;
import java.util.Scanner;

import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;


public class GamePane extends GridPane{
	
	private ImageView[][] boxes = new ImageView[10][10];
	Image wall = new Image("wall.png");
	Image mirror = new Image("mirror.png");
	Image wood = new Image("wood.png");
	Image empty = new Image("empty.png");
	public GamePane(File level) throws Exception {
		draw(level);	
	}
	
	public void draw(File level) throws Exception {
		this.setPadding(new Insets(5, 5, 5, 5));
		this.setStyle("-fx-background-color: #9c9a9a");
		this.setHgap(5);
		this.setVgap(5);
		 
		for(int row = 0;row<10;row++) {
			for(int column = 0;column<10;column++) {
				boxes[row][column] = new ImageView();
				boxes[row][column].setImage(wall);
				this.add(boxes[row][column],column, row);
			}
		}
		Scanner levelfile = new Scanner(level);
		while(levelfile.hasNext()) {
			String line = levelfile.nextLine();
			String[] parts = line.split(",");
			switch (parts[0]) {
				case("Mirror"):
					boxes[Integer.parseInt(parts[1])][Integer.parseInt(parts[2])].setImage(mirror);
					break;
				case("Wood"):
					boxes[Integer.parseInt(parts[1])][Integer.parseInt(parts[2])].setImage(wood);
					break;
				case("Empty"):
					boxes[Integer.parseInt(parts[1])][Integer.parseInt(parts[2])].setImage(empty);
					break;
			}
		}
		levelfile.close();
	}
}
