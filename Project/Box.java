/* 
 * Ömer KÝBAR 150119037
 * Serkan KORKUT 150119036
 * 
 * This class represents the boxes in the GamePane.
 * It extends ImageView to show an image.
 * Inside GamePane class we create boxes, we set the type of the boxes
 * According to given type this class sets the image of box.
 * The images for this class is under images file.
 */

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Box extends ImageView {
	// Declare boxes and load their images
	private String type;
	private static Image wall = new Image("images\\wall.png");
	private static Image mirror = new Image("images\\mirror.png");
	private static Image wood = new Image("images\\wood.png");
	private static Image empty = new Image("images\\empty.png");
	
	/** Set box type */
	public Box(String type) {
		setType(type);
	}
	
	/** Return box type */
	public String getType() {
		return type;
	}
	
	/** Set the image of the box*/
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
