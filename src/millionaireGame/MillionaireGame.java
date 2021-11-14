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
				boolean gameLoop = true;
				
				while(gameLoop) {
					System.out.println("\nInput P to play the game, T to view the tutorial or Q to exit.\n");
					String userOption = "";
					
					if(scnr.hasNextLine()) {
						userOption = scnr.nextLine();
					}
					
					userOption = userOption.toUpperCase();
					
					switch(userOption) {
						default:
							System.out.println("\nUnfortunately that was an invalid choice; please try again.");
							break;
						case "T":
							tutorial();
							break;
						case "P":
							quizList.clear();
							
							FileReader fr = new FileReader(quizTxt);
							BufferedReader bR = new BufferedReader(fr);
							
							init(bR);
							bR.close();
							
							game();
							break;
						case "Q":
							gameLoop = false;
							System.out.println("\nExiting Game, thanks for playing.");
							break;
					}
				}
				
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
	
	private static void tutorial() {
		System.out.println("\nTutorial:\n");
		
		System.out.println("\nWelcome to the Who Wants to be A Millionaire Console Application\n");
		System.out.println(" * How To Play:");
		System.out.println(" ** In this game, you shall be provided a series of 15 random consecutive questions that must be answered correctly in order to continue.");
		System.out.println(" ** For each answer that is answered correctly, the amount of money that is accumulated rises & is then updated and shown to the user before the next question.");
		System.out.println(" ** Alongside this, there are 2 milestones at £1000 and £32000, where the user can decide to take the money and quit the game or continue playing.");
		System.out.println(" ** Throughout the quiz, the player is provided with three lifelines\n");
		System.out.println(" ** The first lifeline is known as fifty/fifty and allows for the user to remove two wrong answers from a question.\n *** In order to use this lifeline input 5050 when answering a question.\n");
		System.out.println(" ** The second lifeline is 'Ask the Audience' and allows for the player to ask the audience which answer they think is correct.\n *** To use this lifeline input 'ata' when answering a question.\n");
		System.out.println(" ** The third lifeline is 'phone a friend' and allows the player to recieve the answer from a friend.\n *** To use this lifeline input 'phone' when answering a question.");
		
		System.out.println("\n ** Note: Whilst 'phone a friend' generally has more accuracy than 'ask the audience', the answers provided by these lifelines may still be incorrect");
		System.out.println("\n-- Each lifeline may only be used once per playthrough --");
	}
	
	private static void game() {
		boolean gameCheck = true;
		boolean choiceCheck = false;
		boolean usedFiftyFifty = false, usedAskAudience = false, usedPhoneFriend = false;
		int chosenIndex = 0;
		int questionNum = 0;
		
		while(gameCheck) {
			Random rnd = new Random();
			
			int ind = rnd.nextInt(0, quizList.size());
			
			questionNum++;
			
			choiceCheck = false;
			
			while(!choiceCheck) {
				System.out.println("\n\n" + "Question " + questionNum + ": " + quizList.get(ind).getQuestion() + "\n");
				
				for(int i = 0; i < quizList.get(ind).getAnsArr().length; i++) {
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
	
				System.out.println("\nPlease enter either A, B, C or D or use a lifeline.\nPlease refer to the tutorial for information on how to use your lifelines.\n");
				
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
					case "5050":
						if(!usedFiftyFifty) {
							fiftyFifty(quizList.get(ind).getAnsArr(), ind);
							usedFiftyFifty = true;
						}
						else {
							System.err.println("\nYou have already used this lifeline");
						}
						break;
					case "PHONE":
						if(!usedPhoneFriend) {
							phoneFriend(ind);
							usedPhoneFriend = true;
						}
						else {
							System.err.println("\nYou have already used this lifeline");
						}
						break;
					case "ATA":
						if(!usedAskAudience) {
							askAudience(questionNum, ind);
							usedAskAudience = true;
						}
						else {
							System.err.println("\nYou have already used this lifeline");
						}
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
			if(questionNum == 15) {
				congrats();
				gameCheck = false;
			}
		}
	}
	
	private static void fiftyFifty(String[] answerArray, int index) {
		
		List<String> ansList = new LinkedList<String>(Arrays.asList(answerArray));
		
		Random randIndex = new Random();
		int ansIndex = randIndex.nextInt(0, answerArray.length);
		
		if(!(ansList.get(ansIndex).equals(quizList.get(index).getCorrectAns()))){
			ansList.remove(ansIndex);
		}
		
		String[] newAnsArr = ansList.toArray(new String[0]);
		
		switch(newAnsArr.length) {
			default:
				fiftyFifty(newAnsArr, index);
				break;
			case 2:
				quizList.get(index).setAnsArr(newAnsArr);
				System.out.println("\n");
				break;
		}	
	}
	
	private static void askAudience(int qNum, int index) {
		int cap = 100;
		int cCap = ((100 - qNum) / 2);
		
		Random rndNum = new Random();
		int corrDec = cCap - qNum;
		corrDec = corrDec + (rndNum.nextInt(0, 6));
		
		cap = cap - corrDec;
		
		
		int i = 0;
		int mapCheck = 1;
		int newPercentage = 0;
		
		for(int iterate = 0; iterate < quizList.get(index).getAnsArr().length; iterate++) {
			if(quizList.get(index).getAnsArr()[iterate].equals(quizList.get(index).getCorrectAns())) {
				i = iterate;
			}
		}
		
		HashMap<Integer, String> audHash = new HashMap<Integer, String>();
		
		audHash.put(i, corrDec + "%");
		
		for(int vC = 0; vC < 4; vC++) {
			if(!audHash.containsKey(vC)) {
				if(mapCheck == 3) {
					newPercentage = cap;
				}
				else {
					newPercentage = rndNum.nextInt(0, cap + 1);	
				}
				cap = cap - newPercentage;
				audHash.put(vC, newPercentage + "%");
				
				mapCheck++;
			}
		}
		
		for(Map.Entry<Integer, String> pair : audHash.entrySet()) {
			int ansInd = pair.getKey();
			String ansPercentage = pair.getValue();
			String prefix = "";
			
			switch(ansInd) {
				default:
					break;
				case 0:
					prefix = "A) ";
					break;
				case 1:
					prefix = "B) ";
					break;
				case 2:
					prefix = "C) ";
					break;
				case 3:
					prefix = "D) ";
					break;
			}
			
			System.out.println(prefix + ansPercentage);
		}
		
		System.out.println("\n");
	}
	
	private static void phoneFriend(int index) {
		Random rnd = new Random();
		int chanceVal = rnd.nextInt(0, 10001);
		
		if(chanceVal >= 3100) {
			System.out.println("I think the answer is " + quizList.get(index).getCorrectAns());
		}
		else {
			int rndAns = rnd.nextInt(0, quizList.get(index).getAnsArr().length);
			
			System.out.println("I think the answer is " + quizList.get(index).getAnsArr()[rndAns]);
		}
		
	}
	
	private static void ansCorr(int num) {
		money = moneyAcc[(num - 1)];
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
