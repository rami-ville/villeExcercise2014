package fi.utu.ville.exercises.testexer;

import java.util.ArrayList;

import fi.utu.ville.exercises.model.ExerciseData;
import fi.utu.ville.standardutils.AbstractFile;

public class TestExerExerciseData implements ExerciseData {

	/**
	 * 
	 */
	private static final long serialVersionUID = -716445297446246493L;

	private final String question;
	private final ArrayList<String> eventList;
	private final ArrayList<Integer> answerList;
	private final int startYear;
	private final int endYear;
	private final int resolution;

	public TestExerExerciseData(String question, int start, int end, int res) {
		this.question = question;
		this.eventList = new ArrayList<String>();
		this.answerList = new ArrayList<Integer>();
		this.startYear = start;
		this.endYear = end;
		this.resolution = res;
	}

	public String getQuestion() {
		return question;
	}

	public ArrayList<String> getEventList() {
		return eventList;
	}

	public int getEventCount() {
		return eventList.size();
	}

	public ArrayList<Integer> getAnswerList() {
		return answerList;
	}
	
	public void setEvent(String e) {
		eventList.add(e);
	}

	public void setAnswer(Integer a) {
		answerList.add(a);
	}

	public int getStartYear() {
		return startYear;
	}

	public int getEndYear() {
		return endYear;
	}

	public int getResolution() {
		return resolution;
	}

}
