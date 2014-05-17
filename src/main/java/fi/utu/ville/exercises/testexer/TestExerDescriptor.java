package fi.utu.ville.exercises.testexer;

import com.vaadin.server.Resource;

import fi.utu.ville.exercises.helpers.GsonPersistenceHandler;
import fi.utu.ville.exercises.model.ExerciseTypeDescriptor;
import fi.utu.ville.exercises.model.PersistenceHandler;
import fi.utu.ville.exercises.model.SubmissionStatisticsGiver;
import fi.utu.ville.exercises.model.SubmissionVisualizer;
import fi.utu.ville.standardutils.Localizer;

public class TestExerDescriptor implements
		ExerciseTypeDescriptor<TestExerExerciseData, TestExerSubmissionInfo> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5743225101617556960L;

	public static final TestExerDescriptor INSTANCE = new TestExerDescriptor();

	private TestExerDescriptor() {

	}

	@Override
	public PersistenceHandler<TestExerExerciseData, TestExerSubmissionInfo> newExerciseXML() {
		// You can also implement your own PersistenceHandler if you want (see JavaDoc for more info)
		return new GsonPersistenceHandler<TestExerExerciseData, TestExerSubmissionInfo>(
				getTypeDataClass(), getSubDataClass());
	}

	@Override
	public TestExerExecutor newExerciseExecutor() {
		return new TestExerExecutor();
	}

	@Override
	public TestExerEditor newExerciseEditor() {
		return new TestExerEditor();
	}

	@Override
	public Class<TestExerExerciseData> getTypeDataClass() {
		return TestExerExerciseData.class;
	}

	@Override
	public Class<TestExerSubmissionInfo> getSubDataClass() {
		return TestExerSubmissionInfo.class;
	}

	@Override
	public SubmissionStatisticsGiver<TestExerExerciseData, TestExerSubmissionInfo> newStatisticsGiver() {
		return new TestExerStatisticsGiver();
	}

	@Override
	public SubmissionVisualizer<TestExerExerciseData, TestExerSubmissionInfo> newSubmissionVisualizer() {
		return new TestExerSubmissionViewer();
	}

	@Override
	public String getTypeName(Localizer localizer) {
		return localizer.getUIText(TestExerUiConstants.NAME);
	}

	@Override
	public String getTypeDescription(Localizer localizer) {
		return localizer.getUIText(TestExerUiConstants.DESC);
	}

	@Override
	public Resource getSmallTypeIcon() {
		return TestExerIcon.SMALL_TYPE_ICON.getIcon();
	}

	@Override
	public Resource getMediumTypeIcon() {
		return TestExerIcon.SMALL_TYPE_ICON.getIcon();
	}

	@Override
	public Resource getLargeTypeIcon() {
		return TestExerIcon.SMALL_TYPE_ICON.getIcon();
	}
	
	@Override
	public boolean isManuallyGraded() {
		return false;
	}

}