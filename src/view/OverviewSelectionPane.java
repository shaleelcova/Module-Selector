package view;

import java.time.LocalDate;
import java.util.Set;

import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import model.Course;
import model.Module;
import model.Name;

public class OverviewSelectionPane extends GridPane{
	private TextArea personalInfo;
	private TextArea modulesTerm1Info;
	private TextArea modulesTerm2Info;
	private Button btnSave;
	private String name;
	private String pNo;
	private String email;
	private String date;
	private String course;
	private String selectedModules;
	private String reservedModules;
	
	public OverviewSelectionPane() {
		//styling
		this.setVgap(15);
		this.setHgap(20);
		this.setAlignment(Pos.CENTER);
		
		// Create title
		Label lblOverviewSelection = new Label("Overview Selection");
		lblOverviewSelection.setStyle("-fx-font-size: 20");
		lblOverviewSelection.setTextFill(Color.web("#FFFFFF"));
		
		// Set constraints
		ColumnConstraints column0 = new ColumnConstraints();
		column0.setHalignment(HPos.CENTER);
		column0.setHgrow(Priority.ALWAYS);
		this.getColumnConstraints().addAll(column0);
		
		RowConstraints row0 = new RowConstraints();
		row0.setValignment(VPos.CENTER);
		row0.setVgrow(Priority.NEVER);
		
		RowConstraints row1 = new RowConstraints();
		row1.setValignment(VPos.CENTER);
		row1.setVgrow(Priority.ALWAYS);
		
		RowConstraints row2 = new RowConstraints();
		row2.setValignment(VPos.CENTER);
		row2.setVgrow(Priority.ALWAYS);
		
		RowConstraints row3 = new RowConstraints();
		row3.setValignment(VPos.CENTER);
		row3.setVgrow(Priority.SOMETIMES);
		
		this.getRowConstraints().addAll(row0, row1, row2, row3);
		
		// Create text area
		personalInfo = new TextArea();
		personalInfo.setText("");
		personalInfo.setEditable(false);
		modulesTerm1Info = new TextArea();
		modulesTerm1Info.setText("");
		modulesTerm1Info.setEditable(false);
		modulesTerm2Info = new TextArea();
		modulesTerm2Info.setText("");
		modulesTerm2Info.setEditable(false);
		
		// Create button
		btnSave = new Button("Save Overview");
		styleButtons();
		addBtnDisableBind(isEitherFieldEmpty());
		
		// Positioning 
		HBox modulesBox = new HBox(10);
		modulesBox.getChildren().addAll(modulesTerm1Info, modulesTerm2Info);
		HBox.setHgrow(modulesTerm1Info, Priority.ALWAYS);
		HBox.setHgrow(modulesTerm2Info, Priority.ALWAYS);
		this.add(personalInfo, 0, 1);
		this.add(modulesBox, 0, 2);
		this.add(btnSave, 0, 3);
		this.add(lblOverviewSelection, 0, 0);
		
		// create a background fill 
		String backgroundColour = "#1a2930";
        BackgroundFill background_fill = new BackgroundFill(Color.web(backgroundColour), CornerRadii.EMPTY, Insets.EMPTY); 
        Background background = new Background(background_fill); 
        this.setBackground(background); 
	}

	private void addBtnDisableBind(BooleanBinding pty) {
		btnSave.disableProperty().bind(pty);;
	}
	
	public BooleanBinding isEitherFieldEmpty() { 
		return personalInfo.textProperty().isEmpty();
				}

	private void styleButtons() {
		String colour = "#f7ce3e";
		String style = "-fx-base: " + colour;
		btnSave.setStyle(style);
	}
	
	public void update() {
		personalInfo.setText("Name:\t" + name
				+ "\nPNo:\t" + pNo
				+ "\nEmail:\t" + email
				+ "\nDate:\t" + date
				+ "\nCourse:\t" + course);
		
		modulesTerm1Info.setText("Selected Modules\n"
				+ "===============\n"
				+ selectedModules);
		
		modulesTerm2Info.setText("Reserved Modules\n"
				+ "===============\n"
				+ reservedModules);
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public void setPno(String pNo) {
		this.pNo = pNo;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public void setCourse(String course) {
		this.course = course;
	}
	
	public void setReservedModules(Set<Module> set) {
		String module = "";
		for (Module tempModule: set)
			module += tempModule.actualToString() + "\n\n";
		reservedModules = module;
	}
	
	public void setSelectedModules(Set<Module> set) {
		String module = "";
		for (Module tempModule: set)
			module += tempModule.actualToString() + "\n\n";
		selectedModules = module;
	}
	
	public void setselectedModules(String modules) {
		selectedModules = modules;
	}
	
	public void reset() {
		name = "";
		pNo = "";
		email = "";
		date = "";
		course = "";
		reservedModules = "";
		selectedModules = "";
	}
	
	public void saveHandler(EventHandler<ActionEvent> handler) {
		btnSave.setOnAction(handler);
	}

	public void load(Set<Module> allReservedModules, Set<Module> allSelectedModules, Name studentName, String email2,
			String pNumber, Course course2, LocalDate submissionDate) {
		name = studentName.getFullName();
		pNo = pNumber;
		email = email2;
		date = submissionDate.toString();
		course = course2.toString();
		setReservedModules(allReservedModules);
		setSelectedModules(allSelectedModules);
		update();
	}
	
}
