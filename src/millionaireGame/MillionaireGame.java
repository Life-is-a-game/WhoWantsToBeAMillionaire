/**
 * 
 */
package millionaireGame;

import java.util.*;
import java.io.*;

/**
 * @author NathanClarke
 *
 */
public class MillionaireGame {

	/**
	 * @param args
	 */
	
	// TODO: 3 Life lines & Add more questions
	
	private static List<Integer> milestoneList = new ArrayList<Integer>(); // ArrayList for each milestone.
	private static List<Quiz> quizList = new ArrayList<Quiz>(); // ArrayList for each quiz object.
	private static File quizTxt = new File("src/Quiz.txt"); // File object that refers to Quiz.txt in the src folder.
	private static String question = ""; // String that contains the question when initialising. 
	private static String[] ansArr = new String[4]; // String array that contains potential answers.
	private static String corrAns = ""; // String which contains the correct answer for a given quiz.
	private static int money = 0; // Integer for accumulated money
	private static int[] moneyAcc = {100, 200, 300, 500, 1000, 2000, 4000, 8000, 16000, 32000, 64000, 125000, 250000, 500000, 1000000}; // Integer array for storing what the player will earn after each question.
	private static Scanner scnr = new Scanner(System.in); // Scanner object that processes user input.
	
	public static void main(String[] args) {
		if(!quizTxt.exists()) {
			System.err.println("An error has occured. Please check that the Quiz.txt file exists in the src folder.");
		}
		else {
			try {
				FileReader fr = new FileReader(quizTxt);
				BufferedReader bR = new BufferedReader(fr);
				
				init(bR);
				bR.close();
				game();
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private static void init(BufferedReader buff) throws IOException {
		milestoneList.add(1000);
		milestoneList.add(32000);
		
		String currLine;
		int i = 0;
		
		while((currLine = buff.readLine()) != null) {
			if(currLine.isBlank() && !question.equals("")) {
				String[] tempArr = Arrays.copyOf(ansArr, 4);
				quizList.add(new Quiz(question, tempArr, corrAns));
				i = 0;
				corrAns = "";
				question = "";
				
			}
			if(currLine.startsWith("Q:")) {
				String prefix = "Q:";
				String pre2 = "Q: ";
				
				String line = currLine;
				
				line = line.replace(pre2, "");
				line = line.replace(prefix, "");
				
				question = line;
			}
			else if(currLine.startsWith("A:")) {
				String prefix = "A:";
				String pre2 = "A: ";
				
				String line = currLine;
				
				line = line.replace(pre2, "");
				line = line.replace(prefix, "");
				
				if(line.contains("##")) {
					line = line.replace(" ##", "");
					corrAns = line;	
				}
				
				ansArr[i] = line;
				i++;
			}
			else if(currLine.contains("--END--")) {
				break;
			}
		}
	}
	
	private static void game() {
		boolean gameCheck = true;
		boolean choiceCheck = false;
		int chosenIndex = 0;
		int questionNum = 0;
		
		while(gameCheck) {
			Random rnd = new Random();
			
			int ind = rnd.nextInt(0, quizList.size());
			
			questionNum++;
			
			choiceCheck = false;
			
			if(questionNum == 15) {
				congrats();
				gameCheck = false;
			}
			
			while(!choiceCheck) {
				System.out.println("\n\n" + "Question " + questionNum + ": " + quizList.get(ind).getQuestion() + "\n");
				
				for(int i = 0; i < 4; i++) {
					String pre = "";
					
					switch(i) {
						case 0:
							pre = "A) ";
							break;
						case 1:
							pre = "B) ";
							break;
						case 2:
							pre = "C) ";
							break;
						case 3:
							pre = "D) ";
							break;
					}
					
					System.out.println(pre + quizList.get(ind).getAnsArr()[i]);
				}
	
				System.out.println("\nPlease enter either A, B, C or D\n");
				
				String choice = "";
				
				if(scnr.hasNextLine()) {
					choice = scnr.nextLine();
				}
				choice = choice.toUpperCase();
				
				switch(choice) {
					default:
						break;
					case "A":
						chosenIndex = 0;
						choiceCheck = true;
						break;
					case "B":
						chosenIndex = 1;
						choiceCheck = true;
						break;
					case "C":
						chosenIndex = 2;
						choiceCheck = true;
						break;
					case "D":
						chosenIndex = 3;
						choiceCheck = true;
						break;
				}
			}
			
			if(quizList.get(ind).getAnsArr()[chosenIndex].equals(quizList.get(ind).getCorrectAns())) {
				ansCorr(questionNum);
				
				if(money == milestoneList.get(1) || money == milestoneList.get(0)) {
					String decision = continuePrompt();
					
					switch(decision) {
						default: 
							break;
						case "N":
							congrats();
							gameCheck = false;
							break;
						case "Y":
							break;
					}
				}
				
				quizList.remove(ind);
			}
			else {
				ansInCorr();
				gameCheck = false;
			}
		}
	}
	
	private static void ansCorr(int num) {
		money = moneyAcc[(num - 1)];
		//System.err.println(money);
	}
	
	private static void ansInCorr() {
		if(money >= milestoneList.get(1)) {
			System.err.println("\n\nUnfortunately, you have lost the game, but you have managed to walk away with £" + milestoneList.get(1));
		}
		else if(money >= milestoneList.get(0)) {
			System.err.println("\n\nUnfortunately, you have lost the game, but you have managed to walk away with £" + milestoneList.get(0));
		}
		else {
			System.err.println("\n\nUnfortunately, you have lost the game and walked away with nothing");
		}
	}
	
	private static String continuePrompt() {
		while(true) {
			System.out.println("\nDo you wish to take the money or carry on? Input C to continue or T to take the money\n");
			String h = "";
			if(scnr.hasNextLine()) {
				h = scnr.nextLine();
			}
			h = h.toUpperCase();
			
			switch(h) {
				default:
					break;
				case "C":
					return "Y";
				case "T":
					return "N";
			}
		}
	}
	
	private static void congrats() {
		if(money == moneyAcc[14]) {
			System.out.println("Congratulations! You have beaten the game & walked away with £" + money + ".");
		}
		else {
			System.out.println("Congratulations! You have walked away with £" + money + ".");
		}
	}
}
