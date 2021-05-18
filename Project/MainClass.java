
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainClass extends Application{

	public static void main(String[] args) {
		launch();
	}
	
	@Override // Override the start method in the Application class
	public void start(Stage stage) throws Exception {
		// Create a pane to access the game menu
		MenuPane menuPane = new MenuPane(stage);
		
		stage.setTitle("Game"); // Set the stage title
		stage.setScene(new Scene(menuPane)); // Create a scene and place it in the stage
		stage.show(); // Display the stage
		
	}

}
