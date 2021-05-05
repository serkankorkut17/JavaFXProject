
import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


public class GamePane extends GridPane{
	private String points = "";
	private int score;
	private Label scoreLabel;
	private Label nextLevel;
	private Label highScoreLabel;
	private final int LEVEL_COUNT = 5;
	private Box[][] boxes = new Box[10][10];
	private int currentLevel;
    MediaPlayer mediaPlayer;

	public GamePane(Label scoreLabel,Label nextLevel, Label highScoreLabel) throws Exception {	
		this.scoreLabel = scoreLabel;
		this.nextLevel = nextLevel;
		this.highScoreLabel = highScoreLabel;
		
		String music = "sound.mp3";
	    Media sound = new Media(new File(music).toURI().toString());
	    mediaPlayer = new MediaPlayer(sound);
	    
	    currentLevel = 1;
	    
		scoreLabel.setText("Score: "+score);
		highScoreLabel.setText("High Score: " + getHighScore(currentLevel));
		
		draw(currentLevel);
	}
	
	public void draw(int currentLevel) throws Exception {
		this.setPadding(new Insets(2, 2, 2, 2));
		this.setStyle("-fx-background-color: #9c9a9a");
		this.setHgap(2);
		this.setVgap(2);
		 
		for(int row = 0;row<10;row++) {
			for(int column = 0;column<10;column++) {
				boxes[row][column] = new Box("Wall");
				this.add(boxes[row][column],column, row);
				boxes[row][column].setOnMouseClicked(e->
				{	setPoints("");
					Box box = (Box)e.getSource();
					try {
						hitBoxes(box);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				});
			}
		}
		
		Scanner levelfile = new Scanner(new File(String.format("levels\\Level%d.txt", currentLevel)));
		while(levelfile.hasNext()) {
			String line = levelfile.nextLine();
			String[] parts = line.split(",");
			boxes[Integer.parseInt(parts[1])][Integer.parseInt(parts[2])].setType(parts[0]);
		}
		levelfile.close();
		
	}
	
	public String getPoints() {
		if (points.equals(""))
			return "---Text---";
		return points;
	}

	public void setPoints(String points) {
		this.points = points;
	}
	
	public void hitOneBox(Box box) {
		String type = box.getType();
		switch(type) {
		case("Wood"):
			box.setType("Mirror");
			break;
		case("Mirror"):
			box.setType("Empty");
			break;
		case("Wall"):
			break;
		case("Empty"):
			break;
		}
	}
	
	public void hitBoxes(Box box) throws Exception {
		if(box.getType().equals("Wall")||box.getType().equals("Empty")) {
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
		
		mediaPlayer.stop();
		mediaPlayer.seek(mediaPlayer.getStartTime());
		mediaPlayer.play();
		
		if (row < 9 && row > 0 && column < 9 && column > 0) {
			hitOneBox(boxes[row][column]);
			int hits = 1;
			setPoints("Box:" + row + "-" + column);
			
			if(boxes[row+1][column].getType().equals("Mirror") || boxes[row+1][column].getType().equals("Wood")) {
				hitOneBox(boxes[row+1][column]);
				setPoints(getPoints() + " - Hit:" + (row+1) + "," + column);
				hits++;
			}
			
			if(boxes[row-1][column].getType().equals("Mirror") || boxes[row-1][column].getType().equals("Wood")) {
				hitOneBox(boxes[row-1][column]);
				setPoints(getPoints() + " - Hit:" + (row-1) + "," + column);
				hits++;
			}
			
			if(boxes[row][column+1].getType().equals("Mirror") || boxes[row][column+1].getType().equals("Wood")) {
				hitOneBox(boxes[row][column+1]);
				setPoints(getPoints() + " - Hit:" + (row) + "," + (column+1));
				hits++;
			}
			
			if(boxes[row][column-1].getType().equals("Mirror") || boxes[row][column-1].getType().equals("Wood")) {
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
			scoreLabel.setText("Score: "+score);
			if(isFinished()) {
				nextLevel.setDisable(false);
				if (getHighScore(getCurrentLevel()) < score ) {
					saveNewHighScore(getCurrentLevel(), score);
				}
			}
		}
	}
	
	public void nextLevel() throws Exception {
		if(currentLevel+1<=LEVEL_COUNT) {
			currentLevel++;
			draw(currentLevel);
		}
		else {
			currentLevel = 1;
			draw(currentLevel);
		}
		score = 0;
		scoreLabel.setText("Score: "+score);
		nextLevel.setDisable(true);
		highScoreLabel.setText("High Score: " + getHighScore(currentLevel));
	}
	
	public int getCurrentLevel() {
		return currentLevel;
	}
	
	public int getHighScore(int currentLevel) throws Exception { 
		int highScore = 0;
		Scanner scoresFile = new Scanner(new File("highscores.txt"));
		while(scoresFile.hasNext()) {
			String line = scoresFile.nextLine();
			String[] parts = line.split("-");
			if (parts[0].equals(String.valueOf(currentLevel)))
					highScore = Integer.parseInt(parts[1]);
		}
		scoresFile.close();
		return highScore;
	}
	
	public void saveNewHighScore(int currentLevel, int score) throws Exception {
		Scanner scoresFile = new Scanner(new File("highscores.txt"));
		String[] scores = new String[LEVEL_COUNT];
		int i = 0;
		while(scoresFile.hasNext()) {
			scores[i] = scoresFile.nextLine();
			i++;		
		}
		scoresFile.close();
		
		scores[currentLevel-1] = currentLevel + "-" + score;
		
		if (new File("highscores.txt").exists()) {
			new File("highscores.txt").delete();
			
			File highScoreFile = new File("highscores.txt");
			PrintWriter writer = new PrintWriter(highScoreFile);
			for (i = 0; i < scores.length; i++) {
				writer.println(scores[i]);
			}
			writer.close();
		}
	}
	
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
