
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;


public class GamePane extends GridPane{
	
	private ImageView[][] boxes = new ImageView[10][10];
	
	public GamePane() {
		this.setPadding(new Insets(5, 5, 5, 5));
		this.setStyle("-fx-background-color: #9c9a9a");
		this.setHgap(5);
		this.setVgap(5);
		Image wall = new Image("wall.png"); 
		for(int row = 0;row<10;row++) {
			for(int column = 0;column<10;column++) {
				boxes[row][column] = new ImageView();
				boxes[row][column].setImage(wall);
				this.add(boxes[row][column],column, row);
			}
		}	
	}
}
