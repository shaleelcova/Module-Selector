package view;

import java.time.LocalDate;

import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import model.Course;
import model.Name;

public class CreateProfilePane extends GridPane {

	private ComboBox<Course> cboCourse;
	private TextField txtSurname, txtFirstName, txtPnumber, txtEmail;
	private DatePicker inputDate;
	private Button btnCreate;

	public CreateProfilePane() {
		//styling
		this.setVgap(15);
		this.setHgap(20);
		this.setAlignment(Pos.CENTER);

		ColumnConstraints column0 = new ColumnConstraints();
		column0.setHalignment(HPos.RIGHT);

		this.getColumnConstraints().addAll(column0);
		
		//create labels
		Label lblTitle = new Label("Select course: ");
		Label lblPnumber = new Label("Input P number: ");
		Label lblFirstName = new Label("Input first name: ");
		Label lblSurname = new Label("Input surname: ");
		Label lblEmail = new Label("Input email: ");
		Label lblDate = new Label("Input date: ");
		
		
		lblTitle.setTextFill(Color.web("#c5c1c0"));
		lblPnumber.setTextFill(Color.web("#c5c1c0"));
		lblFirstName.setTextFill(Color.web("#c5c1c0"));
		lblSurname.setTextFill(Color.web("#c5c1c0"));
		lblEmail.setTextFill(Color.web("#c5c1c0"));
		lblDate.setTextFill(Color.web("#c5c1c0"));
		
		
		//setup combobox
		cboCourse = new ComboBox<Course>(); //will be populated via method towards end of class
		String colour = "#D6E8F2";
		String style = "-fx-base: " + colour;
		cboCourse.setStyle(style);
		
		//setup text fields
		txtFirstName = new TextField();
		txtSurname = new TextField();
		txtPnumber = new TextField();
		txtEmail = new TextField();
		
		inputDate = new DatePicker();
		inputDate.setStyle(style);
		
		//initialise create button
		btnCreate = new Button("Create Profile");
		btnCreate.setStyle("-fx-base: #f7ce3e");
		addBtnDisableBind(isEitherFieldEmpty());

		//add controls and labels to container
		this.add(lblTitle, 0, 0);
		this.add(cboCourse, 1, 0);
		this.add(lblPnumber, 0, 1);
		this.add(txtPnumber, 1, 1);
		this.add(lblFirstName, 0, 2);
		this.add(txtFirstName, 1, 2);
		this.add(lblSurname, 0, 3);
		this.add(txtSurname, 1, 3);
		this.add(lblEmail, 0, 4);
		this.add(txtEmail, 1, 4);
		this.add(lblDate, 0, 5);
		this.add(inputDate, 1, 5);	
		this.add(new HBox(), 0, 6);
		this.add(btnCreate, 1, 6);
		
        BackgroundFill background_fill = new BackgroundFill(Color.web("#1a2930"), CornerRadii.EMPTY, Insets.EMPTY); 
        Background background = new Background(background_fill); 
        this.setBackground(background); 
		
	}
	
	public void addBtnDisableBind(BooleanBinding pty) {
		btnCreate.disableProperty().bind(pty);
	}

	
	public void load(String firstName, String familyName, String email,  Course course, LocalDate date, String pNumber) {
		txtFirstName.setText(firstName);
		txtSurname.setText(familyName);
		txtEmail.setText(email);
		cboCourse.setValue(course);
		inputDate.setValue(date);
		txtPnumber.setText(pNumber);
	}
	
	//method to allow the controller to populate the course combobox
	public void populateCourseComboBox(Course[] courses) {
		cboCourse.getItems().addAll(courses);
		cboCourse.getSelectionModel().select(0); //select first course by default
	}
	
	//methods to retrieve the form selection/input
	public Course getSelectedCourse() {
		return cboCourse.getSelectionModel().getSelectedItem();
	}
	
	public String getPnumberInput() {
		return txtPnumber.getText();
	}
	
	public Name getName() {
		return new Name(txtFirstName.getText(), txtSurname.getText());
	}

	public String getEmail() {
		return txtEmail.getText();
	}
	
	public LocalDate getDate() {
		return inputDate.getValue();
	}
	
	public BooleanBinding isEitherFieldEmpty() { 
		return txtFirstName.textProperty().isEmpty().or(
			txtSurname.textProperty().isEmpty().or(
			txtPnumber.textProperty().isEmpty().or(
			txtEmail.textProperty().isEmpty())));
			}
	
	//method to attach the create profile button event handler
	public void addCreateProfileHandler(EventHandler<ActionEvent> handler) {
		btnCreate.setOnAction(handler);
		System.out.println();
	}
}
