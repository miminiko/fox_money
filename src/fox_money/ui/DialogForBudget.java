/**
 * 
 */
package fox_money.ui;

import java.time.LocalDate;


import fox_money.Budget;
import fox_money.Category;
import fox_money.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * @author endem
 *
 */
public class DialogForBudget extends Stage {

	private  Image foxikIcon = new Image("im_fox.jpg");
	private  User user;
	private LangKey keyW;

	private  TableColumn<Budget, Double> progress;

	private  TableView<Budget> tableSpec;
	private  TableColumn<Budget, String> nameCategorySpec;
	private  TableColumn<Budget, String> nameSubCategorySpec;
//	private  TableColumn<Budget, String> typSpec;
	private  TableColumn<Budget, Double> sumSpecPlan;
	private  TableColumn<Budget, Double> sumSpecHowMatch;
	private  TableColumn<Budget, LocalDate> dataEnd ;
	
	private Label categoryName;
	private Label subcategoryHashName;
	private Label sumLabel;
	private Label dateLabel;
	
	private  ChoiceBox<String> categoryChoice;
	private  ComboBox <String> subcategoryChoice;
	private TextField sumField;
	private  DatePicker dataBeginPicker ;
	private DatePicker dateEndPicker;

	private Button addBudget;
	private Button removeBudget;
	private Button save;
	
	public DialogForBudget(User user){
		this.user =user;
		this.keyW = user.getKeyUser();
		init();
		this.setScene( new Scene(getBudgetPanel(),600, 400));
	}
	
	public void init(){
		this.getIcons().add(foxikIcon);
		this.setTitle(keyW.getString("dialogForBudget"));
		
		addBudget = new Button();
		addBudget.setOnAction(e -> addBudget());
		ImageView iAdding = new ImageView(new Image("adding.png"));
		iAdding.setFitHeight(20);
		iAdding.setFitWidth(20);
		addBudget.setGraphic(iAdding);

		removeBudget = new Button();
		Image imgDelete = new Image("deleting.png");
		ImageView iDelete = new ImageView(imgDelete);
		iDelete.setFitHeight(20);
		iDelete.setFitWidth(20);
		removeBudget.setGraphic(iDelete);
		removeBudget.setOnAction(e -> deleteBudget());
		
		save = new Button(keyW.getString("buttonSave"));
		
		categoryName = new Label(keyW.getString("tableCategory"));
		subcategoryHashName = new Label(keyW.getString("tableSubCategoty"));
		sumLabel = new Label(keyW.getString("tableSum"));
		dateLabel = new Label(keyW.getString("budgetDateBeginToEnd"));
		
		categoryChoice = new ChoiceBox<String>(FXCollections.observableArrayList(user.getCategoryListString()));
		categoryChoice.setOnAction(event -> lookingSubCategory( categoryChoice));
		subcategoryChoice = new ComboBox<>();
		sumField = new TextField();
		dataBeginPicker = new DatePicker();
		dateEndPicker = new DatePicker();
		
		nameCategorySpec =  new TableColumn<Budget, String>(keyW.getString("tableCategory"));
		nameSubCategorySpec = new TableColumn<Budget, String>(keyW.getString("tableSubCategoty"));
		sumSpecPlan = new TableColumn<Budget, Double>(keyW.getString("sumSpecHowMatch")); 
		sumSpecHowMatch = new TableColumn<Budget, Double>(keyW.getString("tableSum"));
		
		dataEnd = new TableColumn<Budget, LocalDate>(keyW.getString("budgetDateEnd"));
		progress = new TableColumn<Budget, Double>(keyW.getString("budgetProgres"));
	}
	
	private void lookingSubCategory(ChoiceBox<String> c){
		 
		 String category = c.getSelectionModel().getSelectedItem().toString();
		 if(category != null && category != ""){
			 for(Category cat : user.getCategoryList()){
				 if(category.equals(cat.getCategoryName())){
					 subcategoryChoice.setItems(FXCollections.observableArrayList(cat.getNameSubCategory()));
				 }
			 }
		 }		 
	 }
	
	public Parent getBudgetPanel(){
		BorderPane b = new BorderPane();
		b.setTop(getControlPanel());
		b.setCenter(getSpecTable());
		
		HBox hB = new HBox();
		hB.getChildren().add(save);
		hB.setAlignment(Pos.TOP_RIGHT);
		hB.setPadding(new Insets(5));
		b.setBottom(hB);
		b.setPadding(new Insets(10,10,10,10));
		
		return b;
	}
	
	public Node getControlPanel(){
		GridPane gP = new GridPane();
		
		gP.add(categoryName, 1, 0);
		gP.add(subcategoryHashName, 2, 0);
		gP.add(sumLabel, 3, 0);
		gP.add(dateLabel, 4, 0);
		
		gP.add(categoryChoice, 1, 1);
		gP.add(subcategoryChoice, 2, 1);
		categoryChoice.setPrefWidth(80);
		subcategoryChoice.setPrefWidth(80);
		sumField.setPrefWidth(80);
		gP.add(sumField, 3, 1);
		
		HBox hB = new HBox();
    	dataBeginPicker.setValue(LocalDate.now());

		dataBeginPicker.setPrefWidth(90);
		dateEndPicker.setPrefWidth(90);

		hB.getChildren().addAll(dataBeginPicker, dateEndPicker);
		gP.add(hB, 4, 1);
		gP.add(addBudget, 5, 1);
		
		gP.add(removeBudget, 0, 3);
		
		gP.setHgap(3);
		gP.setVgap(3);
		gP.setPadding(new Insets (5,5,5,5));
		return gP;
	}
	
	public Node getSpecTable(){
		tableSpec = new TableView<>();
		nameCategorySpec.setCellValueFactory(new PropertyValueFactory<Budget, String>("BudgetCategory"));
		nameSubCategorySpec.setCellValueFactory(new PropertyValueFactory<Budget, String>("BudgetSubCategory"));
		nameCategorySpec.setMinWidth(120);
		nameSubCategorySpec.setMinWidth(120);

		sumSpecHowMatch.setCellValueFactory(new PropertyValueFactory<Budget, Double>("SummBegin"));
		sumSpecPlan.setCellValueFactory(new PropertyValueFactory<Budget, Double>("SummMonth"));
		progress.setCellValueFactory(new PropertyValueFactory<Budget, Double>("Procent"));
		progress.setCellFactory(param -> new ProgressTableCell());
		dataEnd.setCellValueFactory(new PropertyValueFactory<Budget, LocalDate>("ImportantDate"));
		
		tableSpec.getColumns().add(nameCategorySpec);
		tableSpec.getColumns().add(nameSubCategorySpec);
		tableSpec.getColumns().add(sumSpecHowMatch);
		tableSpec.getColumns().add(sumSpecPlan);
		tableSpec.getColumns().add(progress);
		tableSpec.getColumns().add(dataEnd);

		tableSpec.setItems(getElements());
		tableSpec.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		return tableSpec;
	}
	
	private ObservableList<Budget> getElements(){
		return  FXCollections.observableArrayList(user.getBudgetList());
	}
	
	private void addBudget(){
		if(categoryChoice.getValue() != null){
			try {
                double sum = Double.parseDouble(sumField.getText().replace(',','.'));
                if(sum > 0){
                	user.addNewBudget(new Budget(sum, user.getMainCurrency(), 
                			categoryChoice.getValue(), subcategoryChoice.getValue(), 
                			dataBeginPicker.getValue(),
                			dateEndPicker.getValue()));
                	
                	
                	sumField.setText("");
                	categoryChoice.setValue("");
                	subcategoryChoice.setValue("");
                	dataBeginPicker.setValue(LocalDate.now());
                	dateEndPicker.setValue(LocalDate.now());
					tableSpec.refresh();
					tableSpec.setItems(getElements());
                	
                }

            } catch (NumberFormatException e) {
            	sumField.setText(sumField.getText() + " is not number");
            }
		}
		
		
	}
	
	private void deleteBudget(){
		Budget select = tableSpec.getSelectionModel().getSelectedItem();
		if(select != null){
			user.getBudgetList().remove(select);
			tableSpec.refresh();
			tableSpec.setItems(getElements());
		} else{
				 Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Nothing selected");
					alert.setHeaderText("No selected budget!");
					alert.setContentText("Nothing selected");
					alert.show();
			 }
	}

	public Button getSave() {
		return save;
	}

	public void setSave(Button save) {
		this.save = save;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}
