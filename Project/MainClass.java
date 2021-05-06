
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainClass extends Application{

	public static void main(String[] args) {
		launch();
	}
	
	@Override
	public void start(Stage stage) throws Exception {

		GamePane gamePane = new GamePane();
		
		Scene scene = new Scene(gamePane);
		stage.setScene(scene);
		stage.show();
	}

}
