
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainClass extends Application{

	public static void main(String[] args) {
		launch();
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		BorderPane mainPane = new BorderPane();
		
		Pane topPane = new Pane();
		topPane.setMinHeight(10);
		
		GamePane gamePane = new GamePane();
		
		BorderPane bottomPane = new BorderPane();
	//	bottomPane.setLeft(new Label("---Text---"));
		Label hit = new Label("---Text---");
		bottomPane.setLeft(hit);
		gamePane.setOnMouseClicked(e->
		{	hit.setText(gamePane.getPoints());;
		});
		
		Label currentLevel = new Label();
		currentLevel.setText(String.format("Level %d", gamePane.getCurrentLevel()));
		topPane.getChildren().add(currentLevel);
		Label changeLevel = new Label("Next Level");
		changeLevel.setOnMouseClicked(e->{try {
			gamePane.nextLevel();
			currentLevel.setText(String.format("Level %d", gamePane.getCurrentLevel()));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}});
		bottomPane.setRight(changeLevel);
		
		mainPane.setCenter(gamePane);
		mainPane.setTop(topPane);
		mainPane.setBottom(bottomPane);
		
		Scene scene = new Scene(mainPane);
		stage.setScene(scene);
		stage.show();
	}

}
