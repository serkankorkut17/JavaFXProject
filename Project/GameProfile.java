import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class GameProfile {
	
	boolean isStarted;
	
	public GameProfile() {
		if(!new File("profile/save.txt").exists() || !new File("profile/highscores.txt").exists() ) {
			createProfile();
		}	
	}
	public void saveProfile(int currentLevel,String hitLabel,String scoreLabel, Box[][] boxes,double volume){
		if(!new File("profile/save.txt").exists() || !new File("profile/highscores.txt").exists() ) {
			createProfile();
		}
		File file = new File("profile/");
		File saveFile = new File(file+"/save.txt");
		File saveBox = new File(file+"/boxes.txt");
		try {
			PrintWriter writer = new PrintWriter(saveFile);
			writer.write(String.format("%s/%s/%s/%s","Level "+currentLevel,hitLabel,scoreLabel,volume));
			writer.close();
			writer = new PrintWriter(saveBox);
			for (int i = 0; i < boxes.length; i++) {
				for (int j = 0; j < boxes[i].length; j++) {
					writer.println(boxes[i][j].getType() + "," + i + "," + j);
				}
			}
			writer.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}	
	}
	
	public void saveNewHighScore(int currentLevel,int score,int levelCount) throws Exception {
		String path = "profile/highscores.txt";
		Scanner scoresFile = new Scanner(new File(path));
		String[] scores = new String[levelCount];
		int i = 0;
		while(scoresFile.hasNext()) {
			scores[i] = scoresFile.nextLine();
			i++;		
		}
		scoresFile.close();
		
		scores[currentLevel-1] = currentLevel + "/" + score;
		
		if (new File(path).exists()) {
			new File(path).delete();
			
			File highScoreFile = new File(path);
			PrintWriter writer = new PrintWriter(highScoreFile);
			for (i = 0; i < scores.length; i++) {
				writer.println(scores[i]);
			}
			writer.close();
		}
	}
	
	public String getHighScore(int level) throws Exception {
		String path = "profile/highscores.txt";
		String highScore = "*";
		Scanner scoresFile = new Scanner(new File(path));
		while(scoresFile.hasNext()) {
			String line = scoresFile.nextLine();
			String[] parts = line.split("/");
			if (parts[0].equals(String.valueOf(level)))
					highScore = parts[1];
		}
		scoresFile.close();
		return highScore;
	}
	
	public void createProfile() {
		File file = new File("profile");
		file.mkdir();
		try {
			PrintWriter writer = new PrintWriter(file+"/save.txt");
			writer.println("1/---Text---/0");
			writer.close();
			writer = new PrintWriter(file+"/highscores.txt");
			for(int i = 0;i<5;i++) {
				writer.println((i+1)+"/*");
			}
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}	
	}

	public String[] loadLabels() {
		try {
			Scanner reader = new Scanner(new File("profile/save.txt"));
			String[] result = reader.nextLine().split("/");
			reader.close();
			return result;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}		
	}
	
	public static ArrayList<String> getHighScores(){
		File highScoresFile = new File("profile/highscores.txt");
		if(!highScoresFile.exists())
			return null;
		try {
			ArrayList<String> highScores = new ArrayList<String>();
			Scanner reader = new Scanner(highScoresFile);
			while(reader.hasNext()) {
				String highScore = reader.nextLine().split("/")[1];
				highScores.add(highScore);
			}
			reader.close();
			return highScores;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static double getVolume() {
		File file = new File("profile/save.txt");
		if(!file.exists()) {
			return 100;
		}
		Scanner reader;
		try {
			reader = new Scanner(file);
			double volume = Double.parseDouble(reader.nextLine().split("/")[3])*100;
			reader.close();
			return volume;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		
	}
}
