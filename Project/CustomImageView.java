import javafx.beans.property.DoubleProperty;
import javafx.scene.image.ImageView;

public class CustomImageView extends ImageView {

	private DoubleProperty durability;
	
	public CustomImageView() {
		
	}

	public DoubleProperty getDurability() {
		return durability;
	}

	public void setDurability(DoubleProperty durability) {
		this.durability = durability;
	}
	
}
