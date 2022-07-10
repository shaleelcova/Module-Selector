
package view;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.Course;
import model.Delivery;
import model.Module;

public class SelectModulesPane extends GridPane {

	private Set<Module> selectedModules;
	private Course[] courses;
	private int courseSelected;
	private Button btnAddTerm1, btnAddTerm2, btnRemoveTerm1, btnRemoveTerm2, btnReset, btnSubmit;
	private Label creditTerm1, creditTerm2, totalCredit;
	private ListView<String> unselectedModulesTerm1, unselectedModulesTerm2, selectedModulesTerm1, selectedModulesTerm2, modulesAllYearLong;
	
	public SelectModulesPane() {
		selectedModules = new HashSet<Module>();
		unselectedModulesTerm1 = new ListView<String>();
		unselectedModulesTerm2 = new ListView<String>();
		selectedModulesTerm1 = new ListView<String>();
		selectedModulesTerm2 = new ListView<String>();
		modulesAllYearLong = new ListView<String>();
		
		//styling
		this.setVgap(15);
		this.setHgap(20);
		this.setAlignment(Pos.TOP_LEFT);
		
		ColumnConstraints column0 = new ColumnConstraints();
		ColumnConstraints column1 = new ColumnConstraints();
		ColumnConstraints column2 = new ColumnConstraints();
		ColumnConstraints column3 = new ColumnConstraints();
		
		column0.setHalignment(HPos.RIGHT);
		column1.setHalignment(HPos.CENTER);
		column2.setHalignment(HPos.LEFT);
		column3.setHalignment(HPos.LEFT);
		column3.setMinWidth(25);
		column0.setHgrow(Priority.ALWAYS);
		column1.setHgrow(Priority.ALWAYS);
		column3.setHgrow(Priority.NEVER);
		
		this.getColumnConstraints().addAll(column0, column1, column2, column3);	
		
		// UNSELECTED TERM 1 SECTION
		
		Label lblunSelTerm1 = new Label("Unselected Term 1 modules");
		lblunSelTerm1.setStyle("-fx-font-size: 15");
		btnAddTerm1 = new Button("Add");
		btnAddTerm1.setStyle("-fx-base: #f7ce3e");
		btnAddTerm1.setMaxSize(100, 20);
		unselectedModulesTerm1.setMinSize(250, 150);
        VBox unselectedModulesSelectionTerm1 = new VBox();
        unselectedModulesSelectionTerm1.setAlignment(Pos.CENTER);
        unselectedModulesSelectionTerm1.setSpacing(5);
        unselectedModulesSelectionTerm1.getChildren().addAll(lblunSelTerm1,unselectedModulesTerm1, btnAddTerm1);
        
        // SELECTED TERM 1 SECTION
		
        Label lblselTerm1   = new Label("Selected Term 1 modules");
		lblselTerm1.setStyle("-fx-font-size: 15");
        btnRemoveTerm1 = new Button("Remove");
		btnRemoveTerm1.setStyle("-fx-base: #f7ce3e");
		btnRemoveTerm1.setMaxSize(100, 20);
		selectedModulesTerm1.setMinSize(250, 150);
		VBox selectedModulesSelectionTerm1 = new VBox();
		selectedModulesSelectionTerm1.setAlignment(Pos.CENTER);
        selectedModulesSelectionTerm1.setSpacing(5);
        selectedModulesSelectionTerm1.getChildren().addAll(lblselTerm1,selectedModulesTerm1, btnRemoveTerm1);
        
        // UNSELECTED TERM 2 SECTIOM
        
        Label lblunSelTerm2 = new Label("Unselected Term 2 modules");
		lblunSelTerm2.setStyle("-fx-font-size: 15");
		btnAddTerm2 = new Button("Add");
		btnAddTerm2.setStyle("-fx-base: #f7ce3e");
		btnAddTerm2.setMaxSize(100, 20);
		unselectedModulesTerm2.setMinSize(250, 150);
		VBox unselectedModulesSelectionTerm2 = new VBox();
		unselectedModulesSelectionTerm2.setAlignment(Pos.CENTER);
        unselectedModulesSelectionTerm2.setSpacing(5);
        unselectedModulesSelectionTerm2.getChildren().addAll(lblunSelTerm2,unselectedModulesTerm2, btnAddTerm2);
        
        // SELECTED TERM 2 SECTION
        
        Label lblselTerm2   = new Label("Selected Term 2 modules");
		lblselTerm2.setStyle("-fx-font-size: 15");
        selectedModulesTerm2.setMinSize(250, 150);
        btnRemoveTerm2 = new Button("Remove");
		btnRemoveTerm2.setStyle("-fx-base: #f7ce3e");
		btnRemoveTerm2.setMaxSize(100, 20);
		VBox selectedModulesSelectionTerm2 = new VBox();
		selectedModulesSelectionTerm2.setAlignment(Pos.CENTER);
        selectedModulesSelectionTerm2.setSpacing(5);
        selectedModulesSelectionTerm2.getChildren().addAll(lblselTerm2,selectedModulesTerm2, btnRemoveTerm2);
        
		// SELECTED YEAR LONG
        
        Label lblyearLong   = new Label("Selected year long module");
		lblyearLong.setStyle("-fx-font-size: 15");
        modulesAllYearLong.setMinSize(250, 150);
		VBox modulesSelectionAllYear = new VBox();
		modulesSelectionAllYear.setAlignment(Pos.CENTER);
        modulesSelectionAllYear.setSpacing(5);
        modulesSelectionAllYear.getChildren().addAll(lblyearLong,modulesAllYearLong);
 
        // CREATE LABELS FOR CREDITS
		Label lbltotalTerm1 = new Label("Total Credit for Term 1:");
		lbltotalTerm1.setStyle("-fx-font-size: 15");
		Label lbltotalTerm2 = new Label("Total Credit for Term 2:");
		lbltotalTerm2.setStyle("-fx-font-size: 15");
		Label lbltotalYear = new Label("Total Credit for the year:");
		lbltotalYear.setStyle("-fx-font-size: 15");
		
		String colour = "#c5c1c0";
		lblunSelTerm1.setTextFill(Color.web(colour));
		lblunSelTerm2.setTextFill(Color.web(colour));
		lblselTerm1.setTextFill(Color.web(colour));
		lblselTerm2.setTextFill(Color.web(colour));
		lblyearLong.setTextFill(Color.web(colour));
		lbltotalTerm1.setTextFill(Color.web(colour));
		lbltotalTerm2.setTextFill(Color.web(colour));
		lbltotalYear.setTextFill(Color.web(colour));
		
		
			
		btnReset = new Button ("Reset");
		btnReset.setStyle("-fx-base: #f7ce3e");
		btnReset.setMaxSize(100, 20);
		
		btnSubmit = new Button("Submit");
		btnSubmit.setStyle("-fx-base: #f7ce3e");
		btnSubmit.setMaxSize(100, 20);
		
		creditTerm1 = new Label(Integer.toString(countCredit(Delivery.TERM_1)));
		creditTerm1.setStyle("-fx-font-size: 30");
		creditTerm1.setTextFill(Color.web("#FFFFFF"));
		
		creditTerm2 = new Label(Integer.toString(countCredit(Delivery.TERM_2)));
		creditTerm2.setStyle("-fx-font-size: 30");
		creditTerm2.setTextFill(Color.web("#FFFFFF"));
		
		totalCredit = new Label(Integer.toString(countCredit(Delivery.YEAR_LONG)));
		totalCredit.setStyle("-fx-font-size: 30");
		totalCredit.setTextFill(Color.web("#FFFFFF"));

		//add controls and labels to container
		this.add(unselectedModulesSelectionTerm1, 0, 0);
		this.add(selectedModulesSelectionTerm1, 1, 0);		
		this.add(unselectedModulesSelectionTerm2, 0, 1);
		this.add(selectedModulesSelectionTerm2, 1, 1);		
		this.add(modulesSelectionAllYear, 1, 2);
		this.add(lbltotalTerm1, 2, 0);
		this.add(lbltotalTerm2, 2, 1);
		this.add(lbltotalYear, 2, 2);
		this.add(btnReset, 0, 4);
		this.add(btnSubmit, 2, 4);
		this.add(creditTerm1, 3, 0);
		this.add(creditTerm2, 3, 1);
		this.add(totalCredit, 3, 2);

		// create a background fill 
        BackgroundFill background_fill = new BackgroundFill(Color.web("#1a2930"),  
                                      CornerRadii.EMPTY, Insets.EMPTY); 
        // create Background 
        Background background = new Background(background_fill); 
        // set background 
        this.setBackground(background); 
	}
	
	public boolean isSubmitReady() {
		return (countCredit(model.Delivery.TERM_1) + countCredit(model.Delivery.TERM_2) == 120);
	}
	public void resetModules() {
		clearModules();
		clearSelectedModules();
		initModules();
		updateCredit();
		refresh();
	}
	
	private void clearSelectedModules() {
		selectedModules.clear();
	}
	
	public void clearModules() {
		unselectedModulesTerm1.getItems().clear();
		unselectedModulesTerm2.getItems().clear();
		selectedModulesTerm1.getItems().clear();
		selectedModulesTerm2.getItems().clear();
		modulesAllYearLong.getItems().clear();
	}
	
	public void setCourses(Course[] courses) {
		this.courses = courses;	
	}
	
	public void refresh() {
	
		unselectedModulesTerm1.refresh();
		unselectedModulesTerm2.refresh();
		selectedModulesTerm1.refresh();
		selectedModulesTerm2.refresh();
		modulesAllYearLong.refresh();
				
		
		if (freeSpaceTerm(model.Delivery.TERM_1)) 
			btnAddTerm1.setDisable(true);
		else
			btnAddTerm1.setDisable(false);
		
		if (freeSpaceTerm(model.Delivery.TERM_2)) 
			btnAddTerm2.setDisable(true);
		else
			btnAddTerm2.setDisable(false);
		
		if (isSubmitReady()){
			btnSubmit.setDisable(false);
		}
		else
			btnSubmit.setDisable(true);
		
		checkRemovableModules();
		
	}
	
	public void disableSubmit() {
		btnSubmit.setDisable(true);
	}
	
	
	public void checkRemovableModules() {
		if (removableModule(model.Delivery.TERM_1))
			btnRemoveTerm1.setDisable(false);
		else
			btnRemoveTerm1.setDisable(true);

		if (removableModule(model.Delivery.TERM_2))
			btnRemoveTerm2.setDisable(false);
		else
			btnRemoveTerm2.setDisable(true);
	}
	
	public ListView<String> getUnselectedModulesTerm1(){
		return unselectedModulesTerm1;
	}
	
	public ListView<String> getUnselectedModulesTerm2(){
		return unselectedModulesTerm2;
	}
	
	public void updateModules() {
		clearModules();
		initModules();
		updateCredit();
		refresh();
	}
	
	private void updateCredit() {
		creditTerm1.setText(Integer.toString(countCredit(Delivery.TERM_1)));
		creditTerm2.setText(Integer.toString(countCredit(Delivery.TERM_2)));
		totalCredit.setText(Integer.toString(countCredit(Delivery.TERM_1) + countCredit(Delivery.TERM_2)));
		
		if (countCredit(Delivery.TERM_1) == 60) 
			creditTerm1.setTextFill(Color.web("#328cc1"));
		else 
			creditTerm1.setTextFill(Color.web("#FFFFFF"));
		
		if (countCredit(Delivery.TERM_2) == 60) 
			creditTerm2.setTextFill(Color.web("#328cc1"));
		else 
			creditTerm2.setTextFill(Color.web("#FFFFFF"));
		
		if (countCredit(Delivery.TERM_1) + countCredit(Delivery.TERM_2) == 120) 
			totalCredit.setTextFill(Color.web("#328cc1"));
		else 
			totalCredit	.setTextFill(Color.web("#FFFFFF"));

		
	}
	
	public boolean freeSpaceTerm(model.Delivery delivery) {
		int credits = countCredit(delivery);
		return (credits >= 60);
	}
	
	public boolean removableModule(model.Delivery delivery) {
		for (Module module: selectedModules) {
			if (delivery == model.Delivery.TERM_1 && !module.isMandatory() && module.getRunPlan() == model.Delivery.TERM_1) {
				return true;
			}
			
			if (delivery == model.Delivery.TERM_2 && !module.isMandatory() && module.getRunPlan() == model.Delivery.TERM_2) {
				return true;
			}
		}
		
		return false;
	}
	
	public int countCredit(model.Delivery delivery) {
		int totalCredit = 0;
		
		for (Module module: selectedModules) {
			if (module.getRunPlan() == delivery) 
				totalCredit += module.getCredits();
			else if (module.getRunPlan() == model.Delivery.YEAR_LONG)
				totalCredit += module.getCredits()/2;
		}
		
		return totalCredit;
	}
	
	public void removeUnselectedModule(String module) {
		unselectedModulesTerm1.getItems().remove(module);
	}
	
	public void removeSelectedModule(Module module) {
		selectedModules.remove(module);
	}
	
	public void setupSelectedModules() {
		for (Module module: selectedModules ) {
			if (module.getRunPlan() == model.Delivery.TERM_1) {
				selectedModulesTerm1.getItems().add(module.toString());
			}
			
			if (module.getRunPlan() == model.Delivery.TERM_2) {
					selectedModulesTerm2.getItems().add(module.toString());
				}
			if (module.getRunPlan() == model.Delivery.YEAR_LONG) {
					modulesAllYearLong.getItems().add(module.toString());
				}
			}

		}
	
	public void addModuleToTerm1(Module module) {
		unselectedModulesTerm1.getItems().add(module.toString());
		addModuleToSelected(module);
	}
	
	public void addModuleToTerm2(Module module) {
		unselectedModulesTerm2.getItems().add(module.toString());
		addModuleToSelected(module);
	}
	
	public void addModuleToSelected(Module module) {
		selectedModules.add(module);
	}
	
	private void initModules() {
		
		setupMandatoryModules();
		setupSelectedModules();
		setupUnselectedModules();	
		
		if (isSubmitReady())
			btnSubmit.setDisable(false);
		else
			btnSubmit.setDisable(true);
				
		checkRemovableModules();
		
	}
	
	public void setupModules(Course[] courses) {
		setCourses(courses);
		initModules();
	}
	
	public void setupMandatoryModules() {
		
		Collection<Module> moduleCollection =  courses[courseSelected].getAllModulesOnCourse();
		
		for (Module module: moduleCollection ) {
			if (module.getRunPlan() == model.Delivery.TERM_1 && module.isMandatory())  {
				selectedModules.add(module);
			}
			if (module.getRunPlan() == model.Delivery.TERM_2 && module.isMandatory()){
				selectedModules.add(module);
				}
			if (module.getRunPlan() == model.Delivery.YEAR_LONG && module.isMandatory()){
				selectedModules.add(module);
				}
			}
		}
		
	public void  setupUnselectedModules() {
		Collection<Module> moduleCollection =  courses[courseSelected].getAllModulesOnCourse();
		
		for (Module module: moduleCollection ) {
			if (module.getRunPlan() == model.Delivery.TERM_1 && !module.isMandatory() && !selectedModules.contains(module))  {
				unselectedModulesTerm1.getItems().add(module.toString());
				}
			else if (module.getRunPlan() == model.Delivery.TERM_2 && !module.isMandatory()&& !selectedModules.contains(module)){
				unselectedModulesTerm2.getItems().add(module.toString());
				}
			else if (module.getRunPlan() == model.Delivery.YEAR_LONG && !module.isMandatory()&& !selectedModules.contains(module)){
				modulesAllYearLong.getItems().add(module.toString());
				}
			}
		}
	
	public void setCourse(int course) {
		courseSelected = course;
	}
	
	//method to attach the create profile button event handler
	public void addTerm1Handler(EventHandler<ActionEvent> handler) {
		btnAddTerm1.setOnAction(handler);
	}
	
	public void addTerm2Handler(EventHandler<ActionEvent> handler) {
		btnAddTerm2.setOnAction(handler);
		}
	
	public void removeTerm1Handler(EventHandler<ActionEvent> handler) {
		btnRemoveTerm1.setOnAction(handler);
		}
	
	public void removeTerm2Handler(EventHandler<ActionEvent> handler) {
		btnRemoveTerm2.setOnAction(handler);
		}
	
	public void resetHandler(EventHandler<ActionEvent> handler) {
		btnReset.setOnAction(handler);
		}
	
	public void submitHandler(EventHandler<ActionEvent> handler) {
		btnSubmit.setOnAction(handler);
		}

	public void removeModuleFromTerm1(Module module) {
		unselectedModulesTerm1.getItems().add(module.toString());
		selectedModules.remove(module);
	}

	public ListView<String> getSelectedModulesTerm1() {
		return selectedModulesTerm1;
	}
	
	public Set<Module> getSelectedModules(){
		return selectedModules;
	}

	public void removeModuleFromTerm2(Module module) {
		unselectedModulesTerm2.getItems().add(module.toString());
		selectedModules.remove(module);
	}

	public ListView<String> getSelectedModulesTerm2() {
		return selectedModulesTerm2;
	}

	public void load(Set<Module> allSelectedModules, Course[] courses, Course course) {
		clearSelectedModules();
		clearModules();
		for (Module module: allSelectedModules)
			selectedModules.add(module);
		setCourses(courses);
		if (course.toString().equals("Computer Science"))
			setCourse(0);
		else
			setCourse(1);
		updateModules();
	}
	
}

