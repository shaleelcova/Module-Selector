package view;


import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.BorderPane;


public class ModuleSelectionRootPane extends BorderPane {

	private CreateProfilePane cpp;
	private SelectModulesPane smp;
	private ModuleSelectionMenuBar msmb;
	private TabPane tp;
	private ReserveModulesPane rmp;
	private OverviewSelectionPane osp;
	
	public ModuleSelectionRootPane() {
		//create tab pane and disable tabs from being closed
		tp = new TabPane();
		tp.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		
		//create panes
		cpp = new CreateProfilePane();
		smp = new SelectModulesPane();
		rmp = new ReserveModulesPane();
		osp = new OverviewSelectionPane();
		
		
		//create tabs with panes added
		Tab t1 = new Tab("Create Profile", cpp);
		Tab t2 = new Tab("Select Modules", smp);
		Tab t3 = new Tab("Reserve Modules", rmp);
		Tab t4 = new Tab("Overview Selection", osp);
		
		
		//t1.setGraphic(new VBox());
		t1.setStyle("-fx-background-color: #328cc1;"
				+ "-fx-text-color: green;"
				+ "-fx-focus-text-color: white");	
		
		t2.setStyle("-fx-background-color: #328cc1;"
				+ "fx-text-base-color: white;"
				+ "-fx-font-color: white;");
		
		t3.setStyle("-fx-background-color: #328cc1;"
				+ "fx-text-base-color: white;"
				+ "-fx-font-color: white;");
		
		t4.setStyle("-fx-background-color: #328cc1;"
				+ "fx-text-base-color: white;"
				+ "-fx-font-color: white;");
		
				
		//add tabs to tab pane
		tp.getTabs().addAll(t1, t2, t3, t4);

		
		
		tp.getStyleClass().add("floating");
		tp.setStyle("-fx-background-color: #328cc1;"
				+ "-fx-focus-color: transparent;"
				+ "-fx-faint-focus-color: #1a2930;"
				+ "-fx-font-color: white;");
		
		//create menu bar
		msmb = new ModuleSelectionMenuBar();
		
		msmb.setStyle("-fx-background-color: #328cc1;");
		
		//add menu bar and tab pane to this root pane
		this.setTop(msmb);
		this.setCenter(tp);

	}

	//methods allowing sub-containers to be accessed by the controller.
	public CreateProfilePane getCreateProfilePane() {
		return cpp;
	}
	
	public SelectModulesPane getSelectModulesPane() {
		return smp;
	}
	
	
	public ModuleSelectionMenuBar getModuleSelectionMenuBar() {
		return msmb;
	}
	
	//method to allow the controller to change tabs
	public void changeTab(int index) {
		tp.getSelectionModel().select(index);
	}

	public ReserveModulesPane getReserveModulesPane() {
		return rmp;
	}

	public OverviewSelectionPane getOverviewSelectionPane() {
		return osp;
	}
}
