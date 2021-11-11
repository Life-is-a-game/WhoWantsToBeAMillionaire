/**
 * 
 */
package millionaireGame;

/**
 * @author NathanClarke
 *
 */
public class Quiz {
	private String question;
	private String[] ansArr;
	private String correctAns;
	
	/**
	 * @param question
	 * @param ansArr
	 * @param correctAns
	 */
	public Quiz(String question, String[] ansArr, String correctAns) {
		super();
		this.question = question;
		this.ansArr = ansArr;
		this.correctAns = correctAns;
	}

	/**
	 * @return the question
	 */
	public String getQuestion() {
		return question;
	}

	/**
	 * @return the ansArr
	 */
	public String[] getAnsArr() {
		return ansArr;
	}

	/**
	 * @return the correctAns
	 */
	public String getCorrectAns() {
		return correctAns;
	}
	
	
}
