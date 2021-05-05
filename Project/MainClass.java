
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainClass extends Application{

	public static void main(String[] args) {
		launch();
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		
		Label currentLevel = new Label();
		Label hit = new Label("---Text---");
		Label scoreLabel = new Label();
		Label highScoreLabel = new Label();
		Label nextLevel = new Label("Next Level");
		nextLevel.setDisable(true);
		
		BorderPane mainPane = new BorderPane();	
		BorderPane topPane = new BorderPane();
		BorderPane bottomPane = new BorderPane();
		GamePane gamePane = new GamePane(scoreLabel,nextLevel,highScoreLabel);
				
		currentLevel.setText(String.format("Level %d", gamePane.getCurrentLevel()));		
		gamePane.setOnMouseClicked(e->
		{	hit.setText(gamePane.getPoints());;
		});
		nextLevel.setOnMouseClicked(e->{try {
			gamePane.nextLevel();
			currentLevel.setText(String.format("Level %d", gamePane.getCurrentLevel()));
			hit.setText("---Text---");
			} catch (Exception e1) {	
					e1.printStackTrace();
			}});
		
		bottomPane.setLeft(hit);
		bottomPane.setRight(nextLevel);
		
		topPane.setLeft(currentLevel);
		topPane.setCenter(scoreLabel);
		topPane.setRight(highScoreLabel);
		
		mainPane.setCenter(gamePane);
		mainPane.setTop(topPane);
		mainPane.setBottom(bottomPane);
			
		Scene scene = new Scene(mainPane);
		stage.setScene(scene);
		stage.show();
	}

}
