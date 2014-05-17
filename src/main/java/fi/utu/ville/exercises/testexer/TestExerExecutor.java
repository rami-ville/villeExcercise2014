package fi.utu.ville.exercises.testexer;

import java.lang.Integer;
import java.util.ArrayList;

import com.vaadin.event.DataBoundTransferable;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptAll;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.ui.AbstractSelect.AbstractSelectTargetDetails;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.TableDragMode;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.HorizontalLayout;

import fi.utu.ville.exercises.helpers.ExerciseExecutionHelper;
import fi.utu.ville.exercises.model.ExecutionSettings;
import fi.utu.ville.exercises.model.ExecutionState;
import fi.utu.ville.exercises.model.ExecutionStateChangeListener;
import fi.utu.ville.exercises.model.Executor;
import fi.utu.ville.exercises.model.ExerciseException;
import fi.utu.ville.exercises.model.SubmissionListener;
import fi.utu.ville.exercises.model.SubmissionType;
import fi.utu.ville.standardutils.Localizer;
import fi.utu.ville.standardutils.TempFilesManager;

public class TestExerExecutor extends VerticalLayout implements
		Executor<TestExerExerciseData, TestExerSubmissionInfo> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2682119786422750060L;

	private final ExerciseExecutionHelper<TestExerSubmissionInfo> execHelper =

	new ExerciseExecutionHelper<TestExerSubmissionInfo>();

	private final TextField answerField = new TextField();

	private final Table eventTable = new Table();

	private final Table timeLine = new Table();

	private final Table answerTable = new Table();
	
	private ArrayList<Integer> correctAnswers;
	private int eventCount = 0;

	public TestExerExecutor() {

	}

	@Override
	public void initialize(Localizer localizer,
			TestExerExerciseData exerciseData, TestExerSubmissionInfo oldSubm,
			TempFilesManager materials, ExecutionSettings fbSettings)
			throws ExerciseException {
		answerField.setCaption(localizer.getUIText(TestExerUiConstants.ANSWER));
		doLayout(exerciseData, oldSubm);
	}

	private void doLayout(TestExerExerciseData exerciseData, TestExerSubmissionInfo oldAnswer) {
		
		HorizontalLayout tableLayout = new HorizontalLayout();

		eventTable.setSortEnabled(false);
		eventTable.setStyleName("myTable-theme");
		answerTable.setStyleName("myTable-theme");

		eventTable.addContainerProperty("Tapahtuma", String.class,  null);

		correctAnswers = exerciseData.getAnswerList();
		
		ArrayList<String> eList = exerciseData.getEventList();
		eventCount = eList.size();

    	for (int i = 0; i < eventCount; i++) {
    		String eName = eList.get(i);
			eventTable.addItem(new Object[] {eName}, new Integer(i));        		
    	}

    	this.addComponent(new Label(exerciseData.getQuestion()));

        eventTable.setWidth("600px");
		eventTable.setDragMode(TableDragMode.ROW);
		tableLayout.setMargin(true);
		tableLayout.setSpacing(true);
		tableLayout.addComponent(eventTable);

		timeLine.setSortEnabled(false);
		timeLine.setWidth("100px");
//		timeLine.setHeight("1000px");
		timeLine.setSizeFull();

    	timeLine.addContainerProperty("Aika", String.class,  null);
        for (int i = exerciseData.getStartYear(); i <= exerciseData.getEndYear(); i += exerciseData.getResolution()) {
            timeLine.addItem(new Object[] {Integer.toString(i)}, new Integer(i));
			}
        tableLayout.addComponent(timeLine);

		answerTable.setSortEnabled(false);
		answerTable.setWidth("200px");
		answerTable.addContainerProperty("Vuosi", String.class,  null);
		if (oldAnswer != null) {
			for (int i = 0; i < oldAnswer.getEventCount(); i++) {
				answerTable.addItem(new Object[] {oldAnswer.getEventAnswer(i)}, new Integer(i));
			}
			for (int i = oldAnswer.getEventCount(); i < eventCount; i++) {
				answerTable.addItem(new Object[] {"Valitse vuosi?"}, new Integer(i));
			}			
		} else {
			for (int i = 0; i < eventCount; i++) {
				answerTable.addItem(new Object[] {"Valitse vuosi?"}, new Integer(i));
			}			
		}
		tableLayout.addComponent(answerTable);

		this.addComponent(tableLayout);
		timeLine.setDropHandler(new DropHandler() {
			@Override
		    public AcceptCriterion getAcceptCriterion() {
		        return AcceptAll.get();
		    }
			@Override
            public void drop(DragAndDropEvent event) {
                // Wrapper for the object that is dragged
                DataBoundTransferable t = (DataBoundTransferable)
                    event.getTransferable();

                final Object sourceItemId = t.getItemId();                

                final AbstractSelectTargetDetails dropData = ((AbstractSelectTargetDetails) event
                        .getTargetDetails());
                final Object targetItemId = dropData.getItemIdOver();
                if ( targetItemId != null) {
                    answerTable.getItem(sourceItemId).getItemProperty("Vuosi").setValue(targetItemId.toString());
                }
			}
		});
	}

	@Override
	public void registerSubmitListener(
			SubmissionListener<TestExerSubmissionInfo> submitListener) {
		execHelper.registerSubmitListener(submitListener);
	}

	@Override
	public Layout getView() {
		return this;
	}

	@Override
	public void shutdown() {
		// nothing to do here
	}

	@Override
	public void askReset() {
		// nothing to do here
	}

	@Override
	public ExecutionState getCurrentExecutionState() {
		return execHelper.getState();
	}

	@Override
	public void askSubmit(SubmissionType submType) {
		double corr = 0.0;

		String answer = answerField.getValue();
		
		TestExerSubmissionInfo newSubmission = new TestExerSubmissionInfo(answer, eventCount);

		for (int i = 0; i < eventCount; i++) {
			newSubmission.setEventAnswer(i, answerTable.getItem(i).getItemProperty("Vuosi").toString());
			if (answerTable.getItem(i).getItemProperty("Vuosi").toString().equals(correctAnswers.get(i).toString())) {
				corr += 1.0;
			}
		}

		corr /= eventCount;

		execHelper.informOnlySubmit(corr, newSubmission,
				submType, null);

	}

	@Override
	public void registerExecutionStateChangeListener(
			ExecutionStateChangeListener execStateListener) {
		execHelper.registerExerciseExecutionStateListener(execStateListener);

	}

}
