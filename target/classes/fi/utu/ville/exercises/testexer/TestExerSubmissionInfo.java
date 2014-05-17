package fi.utu.ville.exercises.testexer;

import fi.utu.ville.exercises.model.SubmissionInfo;

public class TestExerSubmissionInfo implements SubmissionInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8702870727095225372L;

	private final String answer;
	private final int eventCount;
	private String[] eventAnswers;

	public TestExerSubmissionInfo(String answer, int count) {
		this.answer = answer;
		this.eventCount = count;
		this.eventAnswers = new String[count];
	}

	public String getAnswer() {
		return answer;
	}
	public int getEventCount() {
		return eventCount;
	}
	public String getEventAnswer(int i) {
		return eventAnswers[i];
	}
	public void setEventAnswer(int i, String eventAnswer) {
		eventAnswers[i] = eventAnswer;
	}

}
