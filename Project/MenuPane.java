import java.io.File;
import java.util.ArrayList;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MenuPane extends GridPane {
	Stage stage;
	GamePane gamePane;
	private Slider volumeSlider;
	public MenuPane(Stage stage) {
		this.stage = stage;
		try {
			gamePane = new GamePane(stage);
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		setPadding(new Insets(100, 100, 100, 100));
		setHgap(100);
		setVgap(100);
		setStyle("-fx-background-color: #9c9a9a");
		Button play = new Button(new File("profile").exists() ? "Resume" : "Play");
		play.setPrefSize(300, 50);
		add(play, 0, 0);
		play.setOnMouseClicked(e->{
			try {
			//	stage.setScene(new Scene(new GamePane(stage)));
				stage.setScene(new Scene(gamePane));
				
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		Button newGame = new Button("New Game");
		if(play.getText().equals("Play")) {
			newGame.setDisable(true);
		}
		newGame.setOnMouseClicked(e->{
			File file = new File("profile");
			File saveFile = new File(file+"/save.txt");
			saveFile.delete();
			File boxesFile = new File(file+"/boxes.txt");
			boxesFile.delete();
			File highscoresFile = new File(file+"/highscores.txt");
			highscoresFile.delete();
			file.delete();	
			play.setText("Play");
			((Button)(e.getSource())).setDisable(true);
		});
		newGame.setPrefSize(300, 50);
		add(newGame, 0, 1);
		
		Button highScores = new Button("High Scores");
		highScores.setOnMouseClicked(e->{
			VBox pane = new VBox();
			pane.setPrefWidth(300);
			ArrayList<Integer> highScoresList = GameProfile.getHighScores();
			for(int i = 0;i<GamePane.LEVEL_COUNT;i++) {
				pane.getChildren().add(new Label(String.format("Level %d: %s",i+1,highScoresList == null ? "-" : (highScoresList.get(i) == 0 ? "-" : String.valueOf(highScoresList.get(i))  ))));
			//	pane.getChildren().add(new Label(String.format("Level %d: %d",i+1,highScoresList == null ? 0 : highScoresList.get(i))));
			}
		
			Stage highScoresStage = new Stage();
			highScoresStage.setTitle("High Scores");
			highScoresStage.setScene(new Scene(pane));
			highScoresStage.show();

			
		});
		highScores.setPrefSize(300, 50);
		add(highScores,0,2);
		
		volumeSlider = new Slider();
		volumeSlider.setPrefSize(300, 50);
		add(volumeSlider, 0, 3);
		
		volumeSlider.setValue(gamePane.getVolume() * 100);
		volumeSlider.valueProperty().addListener(new InvalidationListener() {
			
			@Override
			public void invalidated(Observable observable) {
				gamePane.setVolume(volumeSlider.getValue() / 100);
			}
		});
		
	}
	
	public void setVolumeSlider(double volume) {
		volumeSlider.setValue(volume * 100);
	}
}
