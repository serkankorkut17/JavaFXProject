
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainClass extends Application{

	public static void main(String[] args) {
		launch();
	}
	
	@Override
	public void start(Stage stage) throws Exception {

		MenuPane menuPane = new MenuPane(stage);
		
		stage.setTitle("Game");
		stage.setScene(new Scene(menuPane));
		stage.show();
		
	}

}
