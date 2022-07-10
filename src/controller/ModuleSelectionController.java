package controller;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.Course;
import model.Delivery;
import model.Module;
import model.StudentProfile;
import view.ModuleSelectionRootPane;
import view.OverviewSelectionPane;
import view.ReserveModulesPane;
import view.SelectModulesPane;
import view.CreateProfilePane;
import view.ModuleSelectionMenuBar;

public class ModuleSelectionController {

	//fields to be used throughout class
	private StudentProfile model;
	private ModuleSelectionRootPane view;
	private CreateProfilePane cpp;
	private ModuleSelectionMenuBar msmb;
	private SelectModulesPane smp;
	private ReserveModulesPane rmp;
	private OverviewSelectionPane osp;

	public ModuleSelectionController(StudentProfile model, ModuleSelectionRootPane view) {
		//initialise model and view fields
	    this.model = model;
		this.view = view;
		
		//initialise view subcontainer fields
		cpp = view.getCreateProfilePane();
		msmb = view.getModuleSelectionMenuBar();
		smp = view.getSelectModulesPane();
		rmp = view.getReserveModulesPane();
		osp = view.getOverviewSelectionPane();
		

		//populate combobox in create profile pane with courses using the setupAndGetCourses method below
		cpp.populateCourseComboBox(setupAndGetCourses());
		model.setCourse(cpp.getSelectedCourse());

		//attach event handlers to view using private helper method
		this.attachEventHandlers();	
	}

	//a helper method used to attach event handlers
	private void attachEventHandlers() {
		//attach an event handler to the create profile pane
		cpp.addCreateProfileHandler(new CreateProfileHandler());
		smp.addTerm1Handler(new AddTerm1Handler());
		smp.addTerm2Handler(new AddTerm2Handler());
		smp.removeTerm1Handler(new RemoveTerm1Handler());
		smp.removeTerm2Handler(new RemoveTerm2Handler());
		smp.resetHandler(new ResetHandler());
		smp.submitHandler(new SubmitHandler());
		rmp.addToReserveTerm1Handler(new AddToReserveTerm1Handler());
		rmp.addToReserveTerm2Handler(new AddToReserveTerm2Handler());
		rmp.removeFromReserveTerm1Handler(new RemoveFromReserveTerm1Handler());
		rmp.removeFromReserveTerm2Handler(new RemoveFromReserveTerm2Handler());
		rmp.confirmTerm1Handler(new ConfirmTerm1Handler());
		rmp.confirmTerm2Handler(new ConfirmTerm2Handler());
		osp.saveHandler(new SaveHandler());
		msmb.addSaveHandler(new AddSaveHandler());
		msmb.addLoadHandler(new AddLoadHandler());
		msmb.addExitHandler(e -> System.exit(0));
		msmb.addAboutHandler(e -> Popup.display());
	}
	
	private class AddLoadHandler implements EventHandler<ActionEvent>  {
		public void handle(ActionEvent e)  {
			try {
				ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("StudentProfile.data"));
				try {
					model = (StudentProfile) objectInputStream.readObject();
					if (!model.getStudentName().getFamilyName().isEmpty()) {
						cpp.load(model.getStudentName().getFirstName(), model.getStudentName().getFamilyName(), model.getEmail(), model.getCourse()
								, model.getSubmissionDate(), model.getpNumber());
						view.changeTab(0);
					}
					
					if (!model.getAllSelectedModules().isEmpty()) {
						smp.load(model.getAllSelectedModules(), setupAndGetCourses(), model.getCourse());
						view.changeTab(1);
					}
						
					if (!model.getAllReservedModules().isEmpty()) {
						rmp.clearAll();
						rmp.clearModules();
						rmp.setModules(smp.getUnselectedModulesTerm1(), smp.getUnselectedModulesTerm2());
						for (String module: smp.getUnselectedModulesTerm1().getItems())
							rmp.addUnselectedModules(model.getCourse().getModuleByCode(module.substring(0, 8).toString()));
						
						for (String module: smp.getUnselectedModulesTerm2().getItems())
							rmp.addUnselectedModules(model.getCourse().getModuleByCode(module.substring(0, 8).toString()));
						
						rmp.load(model.getAllReservedModules());
						view.changeTab(2);
					}
					
					if (!model.getAllReservedModules().isEmpty()) {
						osp.load(model.getAllReservedModules()
								, model.getAllSelectedModules()
								, model.getStudentName()
								, model.getEmail()
								, model.getpNumber()
								, model.getCourse()
								, model.getSubmissionDate());
						view.changeTab(3);
					}
					
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}
		        objectInputStream.close();
				
				} catch (IOException e1) {
				e1.printStackTrace();
				}
			}
	}
	
	private class AddSaveHandler implements EventHandler<ActionEvent>  {
		public void handle(ActionEvent e)  {
			try {
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("StudentProfile.data"));
				objectOutputStream.writeObject(model);
				objectOutputStream.close();
				} catch (IOException e1) {
				e1.printStackTrace();
				}
			}
	}
	
	private class SaveHandler implements EventHandler<ActionEvent>  {
		public void handle(ActionEvent e)  {
			FileWriter writer;
			try {
				writer = new FileWriter("model.txt");
				PrintWriter printWriter = new PrintWriter(writer);
				printWriter.print(model.toString());
				
				printWriter.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			}
	}
	
	private class ConfirmTerm1Handler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			for (Module module: rmp.getReservedModules())
				model.addReservedModule(module);
			rmp.updateReady(Delivery.TERM_1, true);
			rmp.refresh();
			}
	}
	
	private class ConfirmTerm2Handler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			for (Module module: rmp.getReservedModules())
				model.addReservedModule(module);
			rmp.updateReady(Delivery.TERM_2, true);
			rmp.refresh();
			osp.setName(model.getStudentName().getFullName());
			osp.setPno(model.getpNumber());
			osp.setEmail(model.getEmail());
			osp.setDate(model.getSubmissionDate().toString());
			osp.setCourse(model.getCourse().toString());
			osp.setReservedModules(model.getAllReservedModules());
			osp.setSelectedModules(model.getAllSelectedModules());
			osp.update();
		}
	}
	
	private class SubmitHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			
			for (Module module: smp.getSelectedModules()) 
				model.addSelectedModule(module);
			
			smp.disableSubmit();
			rmp.setModules(smp.getUnselectedModulesTerm1(), smp.getUnselectedModulesTerm2());
			
			for (String module: smp.getUnselectedModulesTerm1().getItems())
				rmp.addUnselectedModules(model.getCourse().getModuleByCode(module.substring(0, 8).toString()));
			
			for (String module: smp.getUnselectedModulesTerm2().getItems())
				rmp.addUnselectedModules(model.getCourse().getModuleByCode(module.substring(0, 8).toString()));
			
			rmp.refresh();
		}
	}
	
	private class RemoveFromReserveTerm1Handler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			String item = rmp.getReservedModulesTerm1().getSelectionModel().getSelectedItem();
			if (item != null) {
				Module module = model.getCourse().getModuleByCode(item.substring(0, 8));
				rmp.removeModuleFromReserve(module);
				rmp.updateCredits();
				rmp.updateReady(Delivery.TERM_1, false);
				rmp.refresh();
				model.clearReservedModules();
			}
		}
	}
	
	private class RemoveFromReserveTerm2Handler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			String item = rmp.getreservedModulesTerm2().getSelectionModel().getSelectedItem();
			if (item != null) {
				Module module = model.getCourse().getModuleByCode(item.substring(0, 8));
				rmp.removeModuleFromReserve(module);
				rmp.updateCredits();
				rmp.updateReady(Delivery.TERM_2, false);
				rmp.refresh();
				model.clearReservedModules();
			}
		}
	}
	
	private class AddToReserveTerm1Handler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			String item = rmp.getUnselectedModulesTerm1().getSelectionModel().getSelectedItem();
			if (item != null) {
				Module module = model.getCourse().getModuleByCode(item.substring(0, 8));
				rmp.addModuleToReserve(module);
				rmp.updateCredits();
				rmp.updateReady(Delivery.TERM_1, false);
				rmp.refresh();
			}
		}
			
	}
	
	private class AddToReserveTerm2Handler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			String item = rmp.getUnselectedModulesTerm2().getSelectionModel().getSelectedItem();
			if (item != null) {
				Module module = model.getCourse().getModuleByCode(item.substring(0, 8));
				rmp.addModuleToReserve(module);
				rmp.updateCredits();
				rmp.updateReady(Delivery.TERM_2, false);
				rmp.refresh();
			}
		}
	}
	
	//empty event handler, which can be used for creating a profile
	private class CreateProfileHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			model.clearReservedModules();
			model.clearSelectedModules();
			rmp.clearAll();
			rmp.refresh();
			osp.reset();
			osp.update();
			
			// Store the entered information to the model
			model.setCourse(cpp.getSelectedCourse());
			model.setEmail(cpp.getEmail());
			model.setpNumber(cpp.getPnumberInput());
			model.setStudentName(cpp.getName());
			model.setSubmissionDate(cpp.getDate());
			
			if (model.getCourse().getCourseName() == "Computer Science") {
				smp.setCourse(0);
			}else {
				smp.setCourse(1);
			}
			smp.setupModules(setupAndGetCourses());
			smp.resetModules();
		}

	}
	
	private class AddTerm1Handler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			String item = smp.getUnselectedModulesTerm1().getSelectionModel().getSelectedItem();
			if (item != null) {
				Module module = model.getCourse().getModuleByCode(item.substring(0, 8));
				smp.addModuleToTerm1(module);
				smp.removeUnselectedModule(module.toString());
				smp.updateModules();

			}
		}
	}
	
	private class RemoveTerm1Handler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			String item = smp.getSelectedModulesTerm1().getSelectionModel().getSelectedItem();
			if (item != null) {
				Module module = model.getCourse().getModuleByCode(item.substring(0, 8));
				smp.removeModuleFromTerm1(module);
				smp.updateModules();
				
			}	
		}
	}
	
	private class RemoveTerm2Handler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			String item = smp.getSelectedModulesTerm2().getSelectionModel().getSelectedItem();
			if (item != null) {
				Module module = model.getCourse().getModuleByCode(item.substring(0, 8));
				smp.removeModuleFromTerm2(module);
				smp.updateModules();
		
			}
		}
	}
	
	private class AddTerm2Handler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			String item = smp.getUnselectedModulesTerm2().getSelectionModel().getSelectedItem();
			if (item != null) {
				Module module = model.getCourse().getModuleByCode(item.substring(0, 8));
				smp.addModuleToTerm2(module);
				smp.removeUnselectedModule(module.toString());
				smp.updateModules();
			}

		}
	}
	
	private class ResetHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			smp.resetModules();
		}
	}


	//generates all module and course data and returns courses within an array
	private Course[] setupAndGetCourses() {
		Module imat3423 = new Module("IMAT3423", "Systems Building: Methods", 15, true, Delivery.TERM_1);
		Module ctec3451 = new Module("CTEC3451", "Development Project", 30, true, Delivery.YEAR_LONG);
		Module ctec3902_SoftEng = new Module("CTEC3902", "Rigorous Systems", 15, true, Delivery.TERM_2);	
		Module ctec3902_CompSci = new Module("CTEC3902", "Rigorous Systems", 15, false, Delivery.TERM_2);
		Module ctec3110 = new Module("CTEC3110", "Secure Web Application Development", 15, false, Delivery.TERM_1);
		Module ctec3605 = new Module("CTEC3605", "Multi-service Networks 1", 15, false, Delivery.TERM_1);	
		Module ctec3606 = new Module("CTEC3606", "Multi-service Networks 2", 15, false, Delivery.TERM_2);	
		Module ctec3410 = new Module("CTEC3410", "Web Application Penetration Testing", 15, false, Delivery.TERM_2);
		Module ctec3904 = new Module("CTEC3904", "Functional Software Development", 15, false, Delivery.TERM_2);
		Module ctec3905 = new Module("CTEC3905", "Front-End Web Development", 15, false, Delivery.TERM_2);
		Module ctec3906 = new Module("CTEC3906", "Interaction Design", 15, false, Delivery.TERM_1);
		Module imat3410 = new Module("IMAT3104", "Database Management and Programming", 15, false, Delivery.TERM_2);
		Module imat3406 = new Module("IMAT3406", "Fuzzy Logic and Knowledge Based Systems", 15, false, Delivery.TERM_1);
		Module imat3611 = new Module("IMAT3611", "Computer Ethics and Privacy", 15, false, Delivery.TERM_1);
		Module imat3613 = new Module("IMAT3613", "Data Mining", 15, false, Delivery.TERM_1);
		Module imat3614 = new Module("IMAT3614", "Big Data and Business Models", 15, false, Delivery.TERM_2);
		Module imat3428_CompSci = new Module("IMAT3428", "Information Technology Services Practice", 15, false, Delivery.TERM_2);


		Course compSci = new Course("Computer Science");
		compSci.addModule(imat3423);
		compSci.addModule(ctec3451);
		compSci.addModule(ctec3902_CompSci);
		compSci.addModule(ctec3110);
		compSci.addModule(ctec3605);
		compSci.addModule(ctec3606);
		compSci.addModule(ctec3410);
		compSci.addModule(ctec3904);
		compSci.addModule(ctec3905);
		compSci.addModule(ctec3906);
		compSci.addModule(imat3410);
		compSci.addModule(imat3406);
		compSci.addModule(imat3611);
		compSci.addModule(imat3613);
		compSci.addModule(imat3614);
		compSci.addModule(imat3428_CompSci);

		Course softEng = new Course("Software Engineering");
		softEng.addModule(imat3423);
		softEng.addModule(ctec3451);
		softEng.addModule(ctec3902_SoftEng);
		softEng.addModule(ctec3110);
		softEng.addModule(ctec3605);
		softEng.addModule(ctec3606);
		softEng.addModule(ctec3410);
		softEng.addModule(ctec3904);
		softEng.addModule(ctec3905);
		softEng.addModule(ctec3906);
		softEng.addModule(imat3410);
		softEng.addModule(imat3406);
		softEng.addModule(imat3611);
		softEng.addModule(imat3613);
		softEng.addModule(imat3614);

		Course[] courses = new Course[2];
		courses[0] = compSci;
		courses[1] = softEng;

		return courses;
	}

}
