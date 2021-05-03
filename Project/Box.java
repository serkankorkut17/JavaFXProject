
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Box extends ImageView {
	private int durability;
	private String type;
	

	private static Image wall = new Image("wall.png");
	private static Image mirror = new Image("mirror.png");
	private static Image wood = new Image("wood.png");
	private static Image empty = new Image("empty.png");
	
	
	public Box(String type) {
		setType(type);
	}

	public int getDurability() {
		return durability;
	}

	public void setDurability(int durability) {
		this.durability = durability;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
		switch(type) {
		case("Wood"):
			durability = 2;
			this.setImage(wood);
			break;
		case("Mirror"):
			durability = 1;
			this.setImage(mirror);
			break;
		case("Wall"):
			durability = 0;
			this.setImage(wall);
			break;
		case("Empty"):
			durability = 0;
			this.setImage(empty);
			break;
		}
	}
}
