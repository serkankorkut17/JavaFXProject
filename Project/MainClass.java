
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
		
		BorderPane topPane = new BorderPane();
		topPane.setMinHeight(10);
		
		Label scoreLabel = new Label();
		GamePane gamePane = new GamePane(scoreLabel);
		
		BorderPane bottomPane = new BorderPane();
		Label hit = new Label("---Text---");
		bottomPane.setLeft(hit);
		
		gamePane.setOnMouseClicked(e->
		{	hit.setText(gamePane.getPoints());;
		});
		
		Label currentLevel = new Label();
		currentLevel.setText(String.format("Level %d", gamePane.getCurrentLevel()));
		
		topPane.setLeft(currentLevel);
		topPane.setCenter(scoreLabel);
		
		Label changeLevel = new Label("Next Level");
		changeLevel.setOnMouseClicked(e->{try {
			gamePane.nextLevel();
			currentLevel.setText(String.format("Level %d", gamePane.getCurrentLevel()));
			hit.setText("---Text---");
		} catch (Exception e1) {
			
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
