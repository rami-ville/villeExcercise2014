package fi.utu.ville.exercises.testexer;

import java.io.File;
import java.util.ArrayList;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import fi.utu.ville.exercises.model.Editor;
import fi.utu.ville.exercises.model.EditorHelper;
import fi.utu.ville.exercises.model.EditorHelper.EditedExerciseGiver;
import fi.utu.ville.standardutils.AFFile;
import fi.utu.ville.standardutils.AbstractFile;
import fi.utu.ville.standardutils.Localizer;
import fi.utu.ville.standardutils.SimpleFileUploader;
import fi.utu.ville.standardutils.SimpleFileUploader.UploaderListener;

public class TestExerEditor extends HorizontalLayout implements
        Editor<TestExerExerciseData> {

    /*
	 * 
	 */
    private static final long serialVersionUID = -4600841604409240872L;
    
    private static final int maxEvents = 15;
    
    private EditorHelper<TestExerExerciseData> editorHelper;

    private TextField questionText;
    private TextField  eventStartYear;
    private TextField  eventEndYear;
    private TextField  eventResolution;
	private final Table eventTable = new Table();

    private Localizer localizer;

    public TestExerEditor() {
    }

    @Override
    public Layout getView() {
        return this;
    }

    @Override
    public void initialize(Localizer localizer, TestExerExerciseData oldData,
            EditorHelper<TestExerExerciseData> editorHelper) {

        this.localizer = localizer;

        this.editorHelper = editorHelper;

        editorHelper.getTempManager().initialize();

        doLayout(oldData);
    }

    private TestExerExerciseData getCurrentExercise() {

    	TestExerExerciseData newData = new TestExerExerciseData(questionText.getValue(),
    			Integer.parseInt(eventStartYear.getValue()),
    			Integer.parseInt(eventEndYear.getValue()),
    			Integer.parseInt(eventResolution.getValue()));

		for (int i = 0; i < maxEvents && !eventTable.getItem(i).getItemProperty("Tapahtuma").getValue().equals(""); i++) {
			try {
				newData.setAnswer(Integer.parseInt((String)eventTable.getItem(i).getItemProperty("Vuosi").getValue()));
				newData.setEvent((String)eventTable.getItem(i).getItemProperty("Tapahtuma").getValue());				
			} catch (NumberFormatException nfe)
			{
				if (!eventTable.getItem(i).getItemProperty("Vuosi").getValue().equals("")) {
			    	Notification.show("WARNING: " + (String)eventTable.getItem(i).getItemProperty("Vuosi").getValue()
			    			+ " isn't an integer value. Event '"
			    			+ (String)eventTable.getItem(i).getItemProperty("Tapahtuma").getValue()
			    			+ "' wasn't saved!");									
				}
			}
		}

		return newData;
    }

    private void doLayout(TestExerExerciseData oldData) {

        this.setMargin(true);
        this.setSpacing(true);
        this.setWidth("100%");

        String oldQuestion;
        if (oldData != null) {
            oldQuestion = oldData.getQuestion();
        } else {
            oldQuestion = "";
        }

        String oldStartYear;
        if (oldData != null) {
        	oldStartYear = Integer.toString(oldData.getStartYear());
        } else {
            oldStartYear= "1900";
        }

        String oldEndYear;
        if (oldData != null) {
        	oldEndYear = Integer.toString(oldData.getEndYear());
        } else {
            oldEndYear = "2000";
        }

        String oldResolution;
        if (oldData != null) {
        	oldResolution = Integer.toString(oldData.getResolution());
        } else {
            oldResolution = "1";
        }

        VerticalLayout controlsLayout = new VerticalLayout();
        controlsLayout.setWidth("400px");

        controlsLayout.addComponent(editorHelper.getInfoEditorView());

        controlsLayout.addComponent(editorHelper
                .getControlbar(new EditedExerciseGiver<TestExerExerciseData>() {

                    @Override
                    public TestExerExerciseData getCurrExerData(
                            boolean forSaving) {
                        return getCurrentExercise();
                    }
                }));

        this.addComponent(controlsLayout);

        VerticalLayout editlayout = new VerticalLayout();

        Label questionTextCapt = new Label(
                localizer.getUIText(TestExerUiConstants.QUESTION));
        questionTextCapt.addStyleName(TestExerThemeConsts.TITLE_STYLE);

        editlayout.addComponent(questionTextCapt);

        questionText = new TextField(null, oldQuestion);

        editlayout.addComponent(questionText);

        eventStartYear= new TextField("Tapahtumien alkuvuosi", oldStartYear);
        editlayout.addComponent(eventStartYear);

        eventEndYear = new TextField("Tapahtumien loppuvuosi", oldEndYear);
        editlayout.addComponent(eventEndYear);

        eventResolution = new TextField("Tapahtumien tarkkuus", oldResolution);
        editlayout.addComponent(eventResolution);

        this.addComponent(editlayout);

        eventTable.setEditable(true);
		eventTable.addContainerProperty("Tapahtuma", String.class,  null);
		eventTable.addContainerProperty("Vuosi", String.class,  null);
        if (oldData != null) {
        	ArrayList<String> eList = oldData.getEventList();
        	ArrayList<Integer> aList = oldData.getAnswerList();
        	
        	for (int i = 0; i < eList.size(); i++) {
        		String eName = eList.get(i);
        		Integer aName = aList.get(i);

        		if (eName == null) eName = "";
        		if (aName == null) aName = 0;
    			eventTable.addItem(new Object[] {eName, Integer.toString(aName)}, new Integer(i));        		
        	}
			for (int i = eList.size(); i < maxEvents; i++) {
				eventTable.addItem(new Object[] {"", ""}, new Integer(i));
			}
        } else {
			for (int i = 0; i < maxEvents; i++) {
				eventTable.addItem(new Object[] {"", ""}, new Integer(i));
			}
        }

		this.addComponent(eventTable);
    }
}
