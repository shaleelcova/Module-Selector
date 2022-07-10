package view;


import java.util.HashSet;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.Delivery;
import model.Module;

public class ReserveModulesPane extends GridPane {
	private Accordion accordion;
	private Set<Module> reservedModules;
	private Set<Module> unselectedModules;
	private Button btnAddTerm1;
	private Button btnRemoveTerm1;
	private Button btnConfirmTerm1;
	private Button btnAddTerm2;
	private Button btnRemoveTerm2;
	private Button btnConfirmTerm2;
	private Label creditsTerm1;
	private Label creditsTerm2;
	private ListView<String> unselectedModulesTerm1;
	private ListView<String> unselectedModulesTerm2;
	private ListView<String> reservedModulesTerm1;
	private ListView<String> reservedModulesTerm2;
	private boolean term1Ready;
	private boolean term2Ready;
	private TitledPane t1;
	private TitledPane t2;
	private int maxCredit;
	
	public ReserveModulesPane() {
		maxCredit = 30;
		// Create boolean that checks if a term is ready to be added to model
		term1Ready = false;
		term2Ready = false;
		
		// Create containers for reserved and unselected modules
		reservedModules = new HashSet<Module>();
		unselectedModules = new HashSet<Module>();
		
		// Create List View for each term
		unselectedModulesTerm1 = new ListView<String>();
		unselectedModulesTerm2 = new ListView<String>();
		reservedModulesTerm1 = new ListView<String>();
		reservedModulesTerm2 = new ListView<String>();
		
		// Create Label for credits
		creditsTerm1 = new Label("Reserved modules: \n" + Integer.toString(countCredit(Delivery.TERM_1)) + " Credits");
		creditsTerm2 = new Label("Reserved modules: \n" + Integer.toString(countCredit(Delivery.TERM_2)) + " Credits");
		styleCreditLabels();
		
		//styling
		this.setVgap(15);
		this.setHgap(20);
		this.setAlignment(Pos.TOP_CENTER);
		
		// Set constraints
		ColumnConstraints column0 = new ColumnConstraints();
		column0.setHgrow(Priority.ALWAYS);
		RowConstraints row = new RowConstraints();
		row.setVgrow(Priority.ALWAYS);
		this.getRowConstraints().addAll(row);
		this.getColumnConstraints().addAll(column0);
		
		// create a background fill 
        BackgroundFill background_fill = new BackgroundFill(Color.web("#1a2930"), CornerRadii.EMPTY, Insets.EMPTY); 
        Background background = new Background(background_fill); 
        this.setBackground(background); 
		HBox boxTerm1 = new HBox(20);
	
		// Create Term 1 titled pane
		VBox unselectedTerm1Box = new VBox(5);
		VBox reservedTerm1Box = new VBox(5);
		VBox confirmTerm1Box = new VBox(5);
		unselectedTerm1Box.setAlignment(Pos.CENTER);
		reservedTerm1Box.setAlignment(Pos.CENTER);
		confirmTerm1Box.setAlignment(Pos.CENTER);
		HBox.setHgrow(unselectedTerm1Box, Priority.ALWAYS);
		HBox.setHgrow(reservedTerm1Box, Priority.ALWAYS);
		HBox.setHgrow(creditsTerm1, Priority.ALWAYS);
		boxTerm1.setAlignment(Pos.CENTER);
		Label unselectedTerm1Text = new Label("Unselected Term 1 Modules");
		Label reserveTerm1Text = new Label("Reserved Term 1 Modules");
		unselectedTerm1Text.setTextFill(Color.web("#c5c1c0"));
		reserveTerm1Text.setTextFill(Color.web("#c5c1c0"));
		btnAddTerm1 = new Button ("Add");
		btnAddTerm1.setMaxSize(100, 20);
		btnRemoveTerm1 = new Button("Remove");
		btnRemoveTerm1.setMaxSize(100, 20);
		btnConfirmTerm1 = new Button ("Confirm");
		btnConfirmTerm1.setMaxSize(100, 20);
		VBox.setVgrow(unselectedModulesTerm1, Priority.ALWAYS);
		VBox.setVgrow(reservedModulesTerm1, Priority.ALWAYS);
		reservedTerm1Box.getChildren().addAll(reserveTerm1Text, reservedModulesTerm1, btnRemoveTerm1);
		unselectedTerm1Box.getChildren().addAll(unselectedTerm1Text, unselectedModulesTerm1, btnAddTerm1);
		confirmTerm1Box.getChildren().addAll(creditsTerm1, btnConfirmTerm1);
		boxTerm1.getChildren().addAll(unselectedTerm1Box, reservedTerm1Box, confirmTerm1Box);
        boxTerm1.setBackground(background); 
		
		// Create Term 2 titled pane
		HBox boxTerm2 = new HBox(20);
		VBox unselectedTerm2Box = new VBox(5);
		VBox reservedTerm2Box = new VBox(5);
		VBox confirmTerm2Box = new VBox(5);
		confirmTerm2Box.setAlignment(Pos.CENTER);
		VBox.setVgrow(unselectedTerm2Box, Priority.SOMETIMES);
		unselectedTerm2Box.setAlignment(Pos.CENTER);
		reservedTerm2Box.setAlignment(Pos.CENTER);
		HBox.setHgrow(unselectedTerm2Box, Priority.ALWAYS);
		HBox.setHgrow(reservedTerm2Box, Priority.ALWAYS);
		Label unselectedTerm2Text = new Label("Unselected Term 2 Modules");
		Label reserveTerm2Text = new Label("Reserved Term 2 Modules");
		unselectedTerm2Text.setTextFill(Color.web("#c5c1c0"));
		reserveTerm2Text.setTextFill(Color.web("#c5c1c0"));
		btnAddTerm2 = new Button ("Add");
		btnAddTerm2.setMaxSize(100, 20);
		btnRemoveTerm2 = new Button("Remove");
		btnRemoveTerm2.setMaxSize(100, 20);
		btnConfirmTerm2 = new Button ("Confirm");
		btnConfirmTerm2.setMaxSize(100, 20);
		VBox.setVgrow(unselectedModulesTerm2, Priority.ALWAYS);
		VBox.setVgrow(reservedModulesTerm2, Priority.ALWAYS);
		confirmTerm2Box.getChildren().addAll(creditsTerm2, btnConfirmTerm2);
		reservedTerm2Box.getChildren().addAll(reserveTerm2Text, reservedModulesTerm2, btnRemoveTerm2);
		unselectedTerm2Box.getChildren().addAll(unselectedTerm2Text, unselectedModulesTerm2, btnAddTerm2);
		HBox.setHgrow(creditsTerm2, Priority.ALWAYS);
		boxTerm2.setAlignment(Pos.CENTER);
		boxTerm2.getChildren().addAll(unselectedTerm2Box, reservedTerm2Box, confirmTerm2Box);
		boxTerm2.setBackground(background);
		
		t1 = new TitledPane("Term 1", boxTerm1);
		t2 = new TitledPane("Term 2", boxTerm2);
		
		t2.setDisable(true);
		
		styleButtons();
		
		t1.setStyle("-fx-focus-color: #ffffff;"
				+ "-fx-border-color: #ffffff;"
				+ "-fx-base: #ffffff");
		
		t2.setStyle("-fx-focus-color: #ffffff;"
				+ "-fx-border-color: #ffffff;"
				+ "-fx-base: #ffffff");
		
		accordion = new Accordion();
		accordion.getPanes().addAll(t1, t2);
		this.add(accordion, 0, 0);
		
		btnAddTerm1.setDisable(true);
		btnAddTerm2.setDisable(true);
		btnConfirmTerm1.setDisable(true);
		btnConfirmTerm2.setDisable(true);
		btnRemoveTerm1.setDisable(true);
		btnRemoveTerm2.setDisable(true);		
	}

	private void styleCreditLabels() {
		creditsTerm1.setStyle("-fx-font-size: 20");
		creditsTerm1.setTextFill(Color.web("#FFFFFF"));
		creditsTerm2.setStyle("-fx-font-size: 20");
		creditsTerm2.setTextFill(Color.web("#FFFFFF"));
	}
	
	private void styleButtons() {
		String colour = "#f7ce3e";
		String style = "-fx-base: " + colour;
		
		btnAddTerm1.setStyle(style);
		btnAddTerm2.setStyle(style);
		btnRemoveTerm1.setStyle(style);
		btnRemoveTerm2.setStyle(style);
		btnConfirmTerm1.setStyle(style);
		btnConfirmTerm2.setStyle(style);
	}
	
	public void updateCredits() {
		creditsTerm1.setText("Reserved modules: \n" + Integer.toString(countCredit(Delivery.TERM_1)) + " Credits");
		creditsTerm2.setText("Reserved modules: \n" + Integer.toString(countCredit(Delivery.TERM_2)) + " Credits");
		
		if (countCredit(Delivery.TERM_1) == maxCredit) 
			creditsTerm1.setTextFill(Color.web("#328cc1"));
		else 
			creditsTerm1.setTextFill(Color.web("#FFFFFF"));
		
		if (countCredit(Delivery.TERM_2) == maxCredit) 
			creditsTerm2.setTextFill(Color.web("#328cc1"));
		else 
			creditsTerm2.setTextFill(Color.web("#FFFFFF"));
	}

	public void removeModule(Module module) {
		if (module.getRunPlan() == model.Delivery.TERM_1)
			unselectedModulesTerm1.getItems().remove(module.toString());
		else
			unselectedModulesTerm1.getItems().remove(module.toString());
	}
	
	public int countCredit(model.Delivery delivery) {
		int totalCredit = 0;
		
		for (Module module: reservedModules) {
			if (module.getRunPlan() == delivery) 
				totalCredit += module.getCredits();
		}
		
		return totalCredit;
	}
	
	public void initModules() {
		for (String module: unselectedModulesTerm1.getItems())
			System.out.println(module);
		refresh();
	}
	
	public void setupReservedModules() {
		for (Module module: reservedModules ) {
			if (module.getRunPlan() == model.Delivery.TERM_1) 
				reservedModulesTerm1.getItems().add(module.toString());
			
			else if  (module.getRunPlan() == model.Delivery.TERM_2) 
				reservedModulesTerm2.getItems().add(module.toString());
			}
		}
	
	public void setupUnselectedModules() {
		for (Module module: unselectedModules) {
			if (module.getRunPlan() == model.Delivery.TERM_1 && !unselectedModulesTerm1.getItems().contains(module.toString())) 
				unselectedModulesTerm1.getItems().add(module.toString());
			
			else if (module.getRunPlan() == model.Delivery.TERM_2  && !unselectedModulesTerm2.getItems().contains(module.toString()))
				unselectedModulesTerm2.getItems().add(module.toString());
		}
	}
	
	public void clearModules() {
		unselectedModulesTerm1.getItems().clear();
		unselectedModulesTerm2.getItems().clear();
		reservedModulesTerm1.getItems().clear();
		reservedModulesTerm2.getItems().clear();
	}
	
	public void clearAll() {
		clearModules();
		reservedModules.clear();
		unselectedModules.clear();
		term1Ready = false;
		term2Ready = false;
	}
	
	public void setModules(ListView<String> unselectedModulesTerm1, ListView<String> unselectedModulesTerm2) {
		for (String module: unselectedModulesTerm1.getItems())
			this.unselectedModulesTerm1.getItems().add(module);
		
		for (String module: unselectedModulesTerm2.getItems())
			this.unselectedModulesTerm2.getItems().add(module);
	}
	
	public void setUnselectedModules(Set<Module> unselectedModules) {
		this.unselectedModules = unselectedModules;
	}
	
	public void addModuleToReserve(Module module) {
		reservedModules.add(module);
		unselectedModules.remove(module);
		clearModules();
		setupUnselectedModules();
		setupReservedModules();
	}
	
	public void removeModuleFromReserve(Module module) {
		reservedModules.remove(module);
		unselectedModules.add(module);
		clearModules();
		setupUnselectedModules();
		setupReservedModules();
	}
	
	public void refresh() {
		unselectedModulesTerm1.refresh();
		unselectedModulesTerm2.refresh();
		reservedModulesTerm1.refresh();
		reservedModulesTerm2.refresh();

		if (!term1Ready)
			t2.setDisable(true);
		else
			t2.setDisable(false);
		buttonControl();
		updateCredits();
	}
	
	public void updateReady(model.Delivery term, boolean ready) {
		if (term == model.Delivery.TERM_1)
			term1Ready = ready;
			
		else if (term == model.Delivery.TERM_2 && countCredit(term) == maxCredit)
			term2Ready = ready;
	}
		
	private void buttonControl() {
		if (freeSpaceTerm(model.Delivery.TERM_1) && !unselectedModules.isEmpty()) 
			btnAddTerm1.setDisable(false);
		else
			btnAddTerm1.setDisable(true);
		
		if (freeSpaceTerm(model.Delivery.TERM_2)) 
			btnAddTerm2.setDisable(false);
		else
			btnAddTerm2.setDisable(true);
		
		if (removableModule(model.Delivery.TERM_1))
			btnRemoveTerm1.setDisable(false);
		else
			btnRemoveTerm1.setDisable(true);

		if (removableModule(model.Delivery.TERM_2))
			btnRemoveTerm2.setDisable(false);
		else
			btnRemoveTerm2.setDisable(true);
		
		if (countCredit(model.Delivery.TERM_1) == maxCredit)
			btnConfirmTerm1.setDisable(false);
		else
			btnConfirmTerm1.setDisable(true);
		
		if (countCredit(model.Delivery.TERM_2) == maxCredit)
			btnConfirmTerm2.setDisable(false);
		else
			btnConfirmTerm2.setDisable(true);
	}
	
	public boolean removableModule(model.Delivery delivery) {
		for (Module module: reservedModules) {
			if (delivery == model.Delivery.TERM_1 && !module.isMandatory() && module.getRunPlan() == model.Delivery.TERM_1) 
				return true;
			
			else if (delivery == model.Delivery.TERM_2 && !module.isMandatory() && module.getRunPlan() == model.Delivery.TERM_2) 
				return true;
			}
		return false;
		}
	
	public boolean freeSpaceTerm(model.Delivery delivery) {
		int credits = countCredit(delivery);
		return (credits < maxCredit);
		}
	
	public boolean ready() {
		return (term1Ready && term2Ready);
	}
	
	public void addToReserveTerm1Handler(EventHandler<ActionEvent> handler) {
		btnAddTerm1.setOnAction(handler);
	}
	
	public void addToReserveTerm2Handler(EventHandler<ActionEvent> handler) {
		btnAddTerm2.setOnAction(handler);
	}
	
	public void removeFromReserveTerm1Handler(EventHandler<ActionEvent> handler) {
		btnRemoveTerm1.setOnAction(handler);
	}
	
	public void removeFromReserveTerm2Handler(EventHandler<ActionEvent> handler) {
		btnRemoveTerm2.setOnAction(handler);
	}
	
	public void confirmTerm2Handler(EventHandler<ActionEvent> handler) {
		btnConfirmTerm2.setOnAction(handler);
	}
	
	public void confirmTerm1Handler(EventHandler<ActionEvent> handler) {
		btnConfirmTerm1.setOnAction(handler);
		
	}
	
	public ListView<String> getUnselectedModulesTerm1() {
		return unselectedModulesTerm1;
	}
	
	public ListView<String> getUnselectedModulesTerm2() {
		return unselectedModulesTerm2;
	}

	public void addUnselectedModules(Module moduleByCode) {
		unselectedModules.add(moduleByCode);
	}

	public ListView<String> getReservedModulesTerm1() {
		return reservedModulesTerm1;
		
	}

	public ListView<String> getreservedModulesTerm2() {
		return reservedModulesTerm2;
	}

	public Set<Module> getReservedModules() {
		return reservedModules;
	}

	public void load(Set<Module> allReservedModules) {
		reservedModules = allReservedModules;
		refresh();
	}


}

