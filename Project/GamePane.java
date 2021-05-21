/* 
 * Ömer KÝBAR 150119037
 * Serkan KORKUT 150119036
 * 
 * This class represents the game.
 * It extends BorderPane. We divided it into three parts.
 * topPane: Top of the game pane there is a level, score and high score labels for the game.
 * center: For the center we used a grid pane to display boxes.
 * bottomPane: Bottom of the game pane there is a labels for returning menu, display hit and passing to next level.
 * Game is saved by using GameProfile when the stage is closed or changed to the menu.
*/
import java.io.File;
import java.util.Scanner;

import javafx.animation.FadeTransition;
import javafx.animation.PathTransition;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;


public class GamePane extends BorderPane{
	private Stage stage; //By using stage field we create event handler to save game when closing and we change scene and save the game when user clicks menu label.
	private double volume;
	private boolean isFirstStart;//If the game starts first time and there is a saved game, GamePane loads saved status of the boxes.
	private String points = "";
	private int score = 0;
	private Label scoreLabel = new Label();
	private Label nextLevel = new Label("Next Level");
	private Label highScoreLabel = new Label();
	private Label currentLevelLabel = new Label();
	private Label hitLabel = new Label();
	public static final int LEVEL_COUNT = 6;//This field is representing total count of levels. To get the value without creating a GamePane from the other classes it is declared as static.
	private Box[][] boxes = new Box[10][10];//This array holds boxes inside the center grid pane.
	private int currentLevel;
    private MediaPlayer mp;//This media player plays sound when user click on a box.
    private MediaPlayer lvlMP;//This media player plays when the game is over.
    private GameProfile profile;
	public GamePane(Stage stage,double volume) throws Exception {
		//Style and color settings for the labels.
		scoreLabel.setStyle("-fx-padding:5px 10px 5px 10px;-fx-background-color:#ff9900;-fx-background-radius:10px");
		scoreLabel.setTextFill(Color.WHITE);
		nextLevel.setStyle("-fx-padding:5px 10px 5px 10px;-fx-background-color:green;-fx-background-radius:10px");
		nextLevel.setTextFill(Color.WHITE);
		highScoreLabel.setStyle("-fx-padding:5px 10px 5px 10px;-fx-background-color:#ff9900;-fx-background-radius:10px");
		highScoreLabel.setTextFill(Color.WHITE);
		currentLevelLabel.setStyle("-fx-padding:5px 10px 5px 10px;-fx-background-color:#ff9900;-fx-background-radius:10px");
		currentLevelLabel.setTextFill(Color.WHITE);
		hitLabel.setStyle("-fx-padding:5px 10px 5px 10px;-fx-background-color:#47acff;-fx-background-radius:10px");
		hitLabel.setVisible(false);//When hit label is an empty text, it is not displayed.
		
		this.stage = stage;
		// Create and register the handler to save the game when the stage is closed.
		stage.setOnCloseRequest(e->profile.saveProfile(currentLevel, hitLabel.getText(), scoreLabel.getText(), boxes,this.volume));
		
		this.profile = new GameProfile();
		//If there is no saved game it sets everything as default.
		if(!new File("profile/boxes.txt").exists()) {
			currentLevel = 1;
			scoreLabel.setText("Score: "+score);
			currentLevelLabel.setText(String.format("Level %d", this.getCurrentLevel()));			
		}
		//If there is a saved game it loads the saved game.
		else{
			String[] labels = profile.loadLabels();
			currentLevelLabel.setText(labels[0]);
			hitLabel.setText(labels[1]);
			if(!labels[1].equals("")) {
				hitLabel.setVisible(true);
			}
			scoreLabel.setText(labels[2]);
			score = Integer.parseInt(labels[2].split(" ")[1]);
			currentLevel = Integer.parseInt(labels[0].split(" ")[1]);
			isFirstStart = true;
		}
		// Create and register the handler to pass to the next level.
		nextLevel.setOnMouseClicked(e->{
			try {
			this.nextLevel();
			currentLevelLabel.setText(String.format("Level %d", this.getCurrentLevel()));
			hitLabel.setText("");
			hitLabel.setVisible(false);
			} catch (Exception e1) {	
					e1.printStackTrace();
		}});
		
		//Create a media player with the loaded sound and assign it mp.
	    Media sound = new Media(new File("sound.mp3").toURI().toString());
	    mp = new MediaPlayer(sound);
	    
	    //Create a media player with the loaded level up sound and assign lvlMp.
	    Media lvl = new Media(new File("levelup.mp3").toURI().toString());
	    lvlMP = new MediaPlayer(lvl);
	    
	    //Set the volume of each media player and assigng volume to the volume field.
	    this.volume = volume;
	    mp.setVolume(volume);
	    lvlMP.setVolume(volume);
	    
	    //Set the text of high score label by using profile.
	    highScoreLabel.setText("High Score: " + profile.getHighScore(currentLevel));
	    if(highScoreLabel.getText().split(":")[1].equals(" *")) { //If user doesn't have a high score value for the current level user can't pass to the next level.
	    	nextLevel.setDisable(true);
	    }
		
	    //Draw the game pane according to current level.	
		draw(currentLevel);
		
		if(isFinished()) { //Check if user finished game for the first opening.
			nextLevel.setDisable(false);
		}
	}
	
	public void draw(int currentLevel) throws Exception {
		//Create top pane, set it's labels and color.
		BorderPane topPane = new BorderPane();
		topPane.setLeft(currentLevelLabel);
		topPane.setCenter(scoreLabel);
		topPane.setRight(highScoreLabel);
		topPane.setStyle("-fx-background-color:#3c474f");
		this.setTop(topPane);
		
		//Create bottom pane, set it's labels and color.
		BorderPane bottomPane = new BorderPane();
		//Create menu label and set style.
		Label menuLabel = new Label("Menu");
		menuLabel.setStyle("-fx-padding:5px 10px 5px 10px;-fx-background-color:red;-fx-background-radius:10px");
		menuLabel.setTextFill(Color.WHITE);
		// Create and register the handler to save the game and change scene to menu pane.
		menuLabel.setOnMouseClicked(e->{profile.saveProfile(currentLevel, hitLabel.getText(), scoreLabel.getText(), boxes,this.volume);
			stage.close();
			stage.setScene(new Scene(new MenuPane(stage)));
			stage.show();
			stage.setOnCloseRequest(null);	
		});
		bottomPane.setCenter(hitLabel);
		bottomPane.setLeft(menuLabel);
		bottomPane.setRight(nextLevel);
		bottomPane.setStyle("-fx-background-color:#3c474f");
		this.setBottom(bottomPane);
		
		//Create center pane using grid pane and set properties.
		GridPane center = new GridPane();
		center.setPadding(new Insets(2, 2, 2, 2));
		center.setStyle("-fx-background-color: #262d33");
		center.setHgap(2);
		center.setVgap(2);
		//Initialize boxes with wall type.
		for(int row = 0;row<10;row++) {
			for(int column = 0;column<10;column++) {
				boxes[row][column] = new Box("Wall");
				center.add(boxes[row][column],column, row);
				// Create and register the handler for click on a box.
				boxes[row][column].setOnMouseClicked(e-> {
					if (e.getButton() == MouseButton.PRIMARY) {
						Box box = (Box)e.getSource();
						try {
							hitBoxes(box);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				});
			}	
		}
		//Change type of some of the boxes according to the level or save file.
		Scanner levelfile;
		if(isFirstStart) { //If there is a saved game and it is first start.
			levelfile = new Scanner(new File("profile/boxes.txt"));
			isFirstStart = false;	
		}
		else { //If there is no saved game.
			levelfile = new Scanner(new File("levels/Level"+currentLevel+".txt"));
		}
		//According to the chosen file set types for boxes.
		while(levelfile.hasNext()) {
			String line = levelfile.nextLine();
			String[] parts = line.split(",");
			boxes[Integer.parseInt(parts[1])][Integer.parseInt(parts[2])].setType(parts[0]);
		}
		levelfile.close();
		this.setCenter(center);
	}
	
	//Return the points.
	public String getPoints() {
		if (points.equals(""))
			return "";
		return points;
	}
	//Set the points
	public void setPoints(String points) {
		this.points = points;
	}
	//Hit one box and set the new type if it is a wood or mirror type.
	public void hitOneBox(Box box) {
		String type = box.getType();
		switch(type) {
		case("Wood"):	
			box.setType("Mirror");
			animation(box);
			break;
		case("Mirror"):
			box.setType("Empty");
			animation(box);
			break;
		case("Wall"):
			break;
		case("Empty"):
			break;
		}
	}
	//Animation for the boxes that are changed it's type when user click one of the boxes.
	private void animation(Box box) {
		Circle circle = new Circle(box.getX()+27, box.getY()+30, 3);
		FadeTransition ft = new FadeTransition(Duration.millis(100), box);
		ft.setFromValue(1.0);
		ft.setToValue(0.1);
		ft.setCycleCount(5);
		ft.setAutoReverse(true);
		PathTransition pt = new PathTransition();
		pt.setNode(box);
		pt.setDuration(Duration.millis(100));
		pt.setPath(circle);
		pt.setCycleCount(5);
		pt.setAutoReverse(true);
		pt.play(); 
		ft.play();
		
		FadeTransition rft = new FadeTransition(Duration.millis(100), box);
		rft.setFromValue(0.1);
		rft.setToValue(1.0);
		rft.setCycleCount(5);
		rft.setAutoReverse(true);
		PathTransition rpt = new PathTransition();
		rpt.setNode(box);
		rpt.setDuration(Duration.millis(100));
		rpt.setPath(circle);
		rpt.setCycleCount(5);
		rpt.setAutoReverse(true);
		rpt.play();
		rft.play();
	}
	//Determine the boxes that will be sent to hitOneBox method.
	public void hitBoxes(Box box) throws Exception {
		if(box.getType().equals("Wall")||box.getType().equals("Empty")) { //Wall and empty type can't be hit.
			return;
		}
		int row = 0;
		int column = 0;
		for (int i = 0; i < boxes.length; i++) {
			for (int j = 0; j < boxes[i].length; j++) {
				if (box == boxes[i][j]) {
					row = i;
					column = j;
				}	
			}
		}
		//Plays hit sound.
		mp.stop();
		mp.seek(mp.getStartTime());
		mp.play();
		
		hitOneBox(boxes[row][column]);
		int hits = 1;
		setPoints("Box:" + row + "-" + column);
			
		if(row<9 &&(boxes[row+1][column].getType().equals("Mirror") || boxes[row+1][column].getType().equals("Wood"))) {
			hitOneBox(boxes[row+1][column]);
			setPoints(getPoints() + " - Hit:" + (row+1) + "," + column);
			hits++;
		}
		if(row>0 &&(boxes[row-1][column].getType().equals("Mirror") || boxes[row-1][column].getType().equals("Wood"))) {
			hitOneBox(boxes[row-1][column]);
			setPoints(getPoints() + " - Hit:" + (row-1) + "," + column);
			hits++;
		}
			
		if(column<9 &&(boxes[row][column+1].getType().equals("Mirror") || boxes[row][column+1].getType().equals("Wood"))) {
			hitOneBox(boxes[row][column+1]);
			setPoints(getPoints() + " - Hit:" + (row) + "," + (column+1));
			hits++;
		}
			
		if(column>0 &&(boxes[row][column-1].getType().equals("Mirror") || boxes[row][column-1].getType().equals("Wood"))) {
			hitOneBox(boxes[row][column-1]);
			setPoints(getPoints() + " - Hit:" + (row) + "," + (column-1));
			hits++;
		}
			
		switch(hits) {
			case(1):
				setPoints(getPoints() + " (-3 points)");
				score -= 3;
				break;
			case(2):
				setPoints(getPoints() + " (-1 points)");
				score -= 1;
				break;
			case(3):
				setPoints(getPoints() + " (+1 points)");
				score += 1;
				break;
			case(4):
				setPoints(getPoints() + " (+2 points)");
				score += 2;
				break;
			case(5):
				score += 4;
				setPoints(getPoints() + " (+4 points)");
				break;
			}
			hitLabel.setText(this.getPoints());
			hitLabel.setVisible(true);
			scoreLabel.setText("Score: "+score);
			if(isFinished()) { //If the game is finished enable the next level and plays a sound.
				nextLevel.setDisable(false);
				mp.stop();
				lvlMP.seek(lvlMP.getStartTime());
				lvlMP.play();
				//If the score is bigger than high score set new high score as score.
				if (profile.getHighScore(getCurrentLevel()).equals("*")||Integer.parseInt(profile.getHighScore(getCurrentLevel())) < score ) {
					profile.saveNewHighScore(getCurrentLevel(), score,LEVEL_COUNT);
					highScoreLabel.setText("High Score: "+String.valueOf(profile.getHighScore(currentLevel)));
				}
			}
		}
	
	//Load the next level by increasing current level and calling draw method. And resets the labels.
	public void nextLevel() throws Exception {
		if(currentLevel+1<=LEVEL_COUNT) { //If level_count isn't exceeded increases current level.
			currentLevel++;
			draw(currentLevel);
		}
		else { //Loads the first level after the last level.
			currentLevel = 1;
			draw(currentLevel);
		}
		score = 0;
		scoreLabel.setText("Score: "+score);
		highScoreLabel.setText("High Score: " + profile.getHighScore(currentLevel));
		if(highScoreLabel.getText().split(":")[1].equals(" *")) {
	    	nextLevel.setDisable(true);
	    }
	}
	
	//Return current level
	public int getCurrentLevel() {
		return currentLevel;
	}
	//Check if the game is finished.
	private boolean isFinished() {
		for(int row = 0;row<boxes.length;row++) {
			for(int column = 0;column<boxes[0].length;column++) {
				if(!boxes[row][column].getType().equals("Wall")&&!boxes[row][column].getType().equals("Empty"))
					return false;
			}
		}
		return true;
	}
}
