package fi.utu.ville.exercises.testexer;

import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import fi.utu.ville.exercises.model.ExerciseException;
import fi.utu.ville.exercises.model.SubmissionVisualizer;
import fi.utu.ville.standardutils.Localizer;
import fi.utu.ville.standardutils.TempFilesManager;

public class TestExerSubmissionViewer extends VerticalLayout implements
		SubmissionVisualizer<TestExerExerciseData, TestExerSubmissionInfo> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6260031633710031462L;
	private TestExerExerciseData exer;
	private TestExerSubmissionInfo submInfo;

	private Localizer localizer;
	
	public TestExerSubmissionViewer() {
	}

	@Override
	public void initialize(TestExerExerciseData exercise,
			TestExerSubmissionInfo dataObject, Localizer localizer,
			TempFilesManager tempManager) throws ExerciseException {
		this.localizer = localizer;
		this.exer = exercise;
		this.submInfo = dataObject;
		doLayout();
	}

	private void doLayout() {
		this.addComponent(new Label(localizer.getUIText(TestExerUiConstants.QUESTION) + 
				": " + exer.getQuestion()));
		Label answ = new Label(localizer.getUIText(TestExerUiConstants.ANSWER) + 
				": "  + submInfo.getAnswer());
		answ.addStyleName(TestExerThemeConsts.ANSWER_STYLE);
		this.addComponent(answ);
	}

	@Override
	public Component getView() {
		return this;
	}

	@Override
	public String exportSubmissionDataAsText() {
		return localizer.getUIText(TestExerUiConstants.QUESTION, "\n", 
				exer.getQuestion(), submInfo.getAnswer());
		
	}

}
