package LevelEditor;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Box extends ImageView {
	public int box_type;
	private String type;
	private static Image wall = new Image("images\\wall.png");
	private static Image mirror = new Image("images\\mirror.png");
	private static Image wood = new Image("images\\wood.png");
	private static Image empty = new Image("images\\empty.png");
	
	public Box(String type) {
		setType(type);
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
		switch(type) {
		case("Wood"):
			this.setImage(wood);
			break;
		case("Mirror"):
			this.setImage(mirror);
			break;
		case("Wall"):
			this.setImage(wall);
			break;
		case("Empty"):
			this.setImage(empty);
			break;
		}
	}
}
