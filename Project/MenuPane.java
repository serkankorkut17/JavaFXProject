
import java.io.File;
import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MenuPane extends GridPane {
	Stage stage;
	public MenuPane(Stage stage) {
		this.stage = stage;
		// Set the properties of menu pane
		setPadding(new Insets(100, 100, 100, 100));
		setHgap(100);
		setVgap(100);
		setStyle("-fx-background-color:#3c474f");
		
		// Place a button in the scene to adjust the game volume
		Slider volumeSlider = new Slider();
		volumeSlider.setPrefSize(300, 50);
		volumeSlider.setStyle("-fx-padding:5px 10px 5px 10px;-fx-background-color:#47acff;-fx-background-radius:10px");
		add(volumeSlider, 0, 3);
		volumeSlider.setValue(GameProfile.getVolume());
		
		Media sound = new Media(new File("sound.mp3").toURI().toString());
	    MediaPlayer mp = new MediaPlayer(sound);
	    // Create and register the handler to preview the volume 
		volumeSlider.setOnMousePressed(e->{mp.setVolume(volumeSlider.getValue()/100);mp.seek(mp.getStartTime());mp.play();});
		
		// Place a button in the scene to start the game
		Button play = new Button(new File("profile").exists() ? "Resume" : "Play");
		play.setStyle("-fx-padding:5px 10px 5px 10px;-fx-background-color:#47acff;-fx-background-radius:10px");
		play.setPrefSize(300, 50);
		add(play, 0, 0);
		// Create and register the handler to be able to start the game 
		play.setOnMouseClicked(e->{
			try {
				// Create a scene and a pane to start the game and place the scene in the stage
				stage.setScene(new Scene(new GamePane(stage,volumeSlider.getValue()/100)));	
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		
		// Place a button in the scene to start a new game
		Button newGame = new Button("New Game");
		newGame.setStyle("-fx-padding:5px 10px 5px 10px;-fx-background-color:#47acff;-fx-background-radius:10px");
		newGame.setPrefSize(300, 50);
		add(newGame, 0, 1);
		if(play.getText().equals("Play")) {
			newGame.setDisable(true);
		}
		// Create and register the handler to delete the old game save 
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
			volumeSlider.setValue(100);
		});
		
		// Place a button in the scene to see the highest score for each level
		Button highScores = new Button("High Scores");
		highScores.setStyle("-fx-padding:5px 10px 5px 10px;-fx-background-color:#47acff;-fx-background-radius:10px");
		highScores.setPrefSize(300, 50);
		add(highScores,0,2);
		
		// Create and register the handler to see the highest score for each level
		highScores.setOnMouseClicked(e->{
			// Create a pane to hold labels with the highest scores
			VBox pane = new VBox();
			pane.setStyle("-fx-background-color:#3c474f");
			pane.setPrefWidth(300);
			ArrayList<String> highScoresList = GameProfile.getHighScores();
			for(int i = 0;i<GamePane.LEVEL_COUNT;i++) {
				Label label = new Label(String.format("Level %d: %s",i+1,highScoresList == null ? "*" : highScoresList.get(i)));
				label.setTextFill(Color.WHITE);
				pane.getChildren().add(label);
			}
			// Create a new stage to see the highest scores 
			Stage highScoresStage = new Stage();
			highScoresStage.setTitle("High Scores");
			highScoresStage.setScene(new Scene(pane));
			highScoresStage.show();

		});	
	}
}
