
import java.io.File;
import java.util.Scanner;

import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;


public class GamePane extends GridPane{
	
	private Box[][] boxes = new Box[10][10];

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
				boxes[row][column] = new Box("Wall");
				this.add(boxes[row][column],column, row);
				boxes[row][column].setOnMouseClicked(e->
				{	Box box = (Box)e.getSource();
					box.setType("Wall");
				});
			}
		}
		Scanner levelfile = new Scanner(level);
		while(levelfile.hasNext()) {
			String line = levelfile.nextLine();
			String[] parts = line.split(",");
			boxes[Integer.parseInt(parts[1])][Integer.parseInt(parts[2])].setType(parts[0]);
		}
		levelfile.close();
	}
}
