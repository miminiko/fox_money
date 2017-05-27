/**
 * 
 */
package fox_money.ui;

import java.util.ArrayList;


import fox_money.Category;
import fox_money.TypOperation;
import fox_money.User;
import fox_money.ui.ListCategoryCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.control.Alert.AlertType;
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
public class DialogForCategory extends Stage {
	
	private  Image foxikIcon = new Image("im_fox.jpg");
	public  Image imgDelete = new Image("deleting.png");

	private  Button saveCategory ;

	private  User user;
	private  ObservableList<Category> dataForListCategory;
	
	private  ListView<Category> viewCategory = new ListView<Category>();
	private  ListView<String> viewSubCategory = new ListView<String>();
	private  ChoiceBox<String> typCategory = new  ChoiceBox<String>(FXCollections.observableArrayList("plus", "minus"));
	private  TextField newItemCategory = new TextField();
	private  TextField newItemSubCategory = new TextField();
	
	private Button addNewCategory;
	private Button addNewSubCategory;
	
	private LangKey keyW;

	public DialogForCategory(User u){
		user = u;
		keyW = u.getKeyUser();
		initDetails();
		dataForListCategory = FXCollections.observableArrayList(user.getCategoryList());
		
		this.getIcons().add(foxikIcon);
		
		this.setScene(new Scene(borderForCategory(), 500, 400));
	}
	
	public void initDetails(){
		this.setTitle(keyW.getString("dialogCategory"));
		saveCategory = new Button(keyW.getString("buttonSave"));
		addNewCategory = new Button(keyW.getString("categoryAddNew")+"      ");
		addNewSubCategory = new Button(keyW.getString("categoryAddNewSub"));
		newItemSubCategory.setPromptText(keyW.getString("enterNewSubCategory"));
		newItemCategory.setPromptText(keyW.getString("enterNewCategory"));
	}
	
	private Parent borderForCategory(){
		 BorderPane r = new BorderPane();
		 GridPane g = new GridPane();
		 
		 Button deleteCategory = new Button();
		 deleteCategory.setAlignment(Pos.TOP_CENTER);
		
		 ImageView i = new ImageView(imgDelete);
		 i.setFitWidth(20);
		 i.setFitHeight(20);
		 deleteCategory.setGraphic(i);
		 deleteCategory.setOnAction(e -> deletinCategoryOrSubCategory());
		 g.add(deleteCategory, 1, 0);

		 viewCategory.setItems(dataForListCategory);
		 viewCategory.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		 viewCategory.setCellFactory((param -> new ListCategoryCell()));
		 viewCategory.setOnMouseClicked(e ->  showSubCategoryForEdit());
		 viewCategory.setMaxSize(210, 260);
		 g.add(viewCategory, 0, 1);
		 
		 viewCategory.setEditable(true);

		 viewCategory.setOnEditCommit(new EventHandler<ListView.EditEvent<Category>>() {
				@Override
				public void handle(ListView.EditEvent<Category> t) {
					viewCategory.getSelectionModel().getSelectedItem().setCategoryName(t.getNewValue().toString());
					viewCategory.refresh();
					 viewCategory.setItems(dataForListCategory);

				}
							
			});

		 
		 viewSubCategory.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		 viewSubCategory.setMaxSize(210, 260);
		 viewSubCategory.setEditable(true);
		 viewSubCategory.setCellFactory(TextFieldListCell.forListView());		

		 viewSubCategory.setOnEditCommit(new EventHandler<ListView.EditEvent<String>>() {
				@Override
				public void handle(ListView.EditEvent<String> t) {
					viewCategory.getSelectionModel().getSelectedItem().setNameSubCategory(viewSubCategory.getItems().get(t.getIndex()), t.getNewValue());
					viewSubCategory.getItems().set(t.getIndex(), t.getNewValue());
//					System.out.println("setOnEditCommit");
				}
							
			});

		 g.add(viewSubCategory, 2, 1);
		 

		 HBox hb1 = new HBox();
		 hb1.getChildren().add(newItemCategory);
		 hb1.getChildren().add(typCategory);
		 g.add(hb1, 0, 2);

		 
		 addNewCategory.setMinSize(90, 10);
		 addNewCategory.setOnAction(e -> addNewCategory());
		 g.add(addNewCategory, 2, 2);
		
		 g.add(newItemSubCategory, 0, 3);

		 addNewSubCategory.setMinSize(90, 10);
		 addNewSubCategory.setOnAction(e -> addNewSubCategory());
		 g.add(addNewSubCategory, 2, 3);
		 
		 g.setHgap(3);
		 g.setVgap(3);
		 
		 HBox hB = new HBox();
			hB.getChildren().add(saveCategory);
			hB.setAlignment(Pos.TOP_RIGHT);
			hB.setPadding(new Insets(5));
			
		 r.setCenter(g);
		 r.setBottom(hB);
		 r.setPadding(new javafx.geometry.Insets(5, 5, 5, 5));

		 
		 return r;
	}
	
	
	 public  void addNewCategory(){
		 String errorMessage = "";
		 
		 if(typCategory.getValue() == null){
			 errorMessage += keyW.getString("erorCategory") +" \n"; 
		 }
		 
		 if (newItemCategory.getText() == null || newItemCategory.getText().length() == 0) {
			 errorMessage += keyW.getString("erorDublicateC") +" \n"; 

	        }if (errorMessage.length() == 0) {
	        	if(itsNewCategory(newItemCategory.getText(), typCategory.getValue())){
	        		user.addCategoryUser(new Category(TypOperation.getTyp(typCategory.getValue()), newItemCategory.getText()));
		            viewCategory.refresh();
		   		 	viewCategory.setItems(FXCollections.observableArrayList(user.getCategoryList()));
		   		newItemCategory.setText("");
	        	}else{
	        		Alert alert = new Alert(AlertType.WARNING);
					alert.setContentText(keyW.getString("erorTitle"));
					alert.setTitle(keyW.getString("erorTitle"));
					alert.setHeaderText(errorMessage);
					alert.showAndWait();
	        	}

	        } else {
	            // Show the error message.
	        	Alert alert = new Alert(AlertType.WARNING);
				alert.setContentText(keyW.getString("erorTitle"));
				alert.setTitle(keyW.getString("erorTitle"));
				alert.setHeaderText(errorMessage);
				alert.showAndWait();
	        	
	        }
		 
		 
	 }
	 
	 public boolean itsNewCategory(String name, String type){
		 for(Category g : user.getCategoryList()){
			 if(g.getCategoryName().equals(name) && (g.getTyp().getName().equals(type))) return false;
		 }
		 return true;
	 }
	 
	 public  void addNewSubCategory(){
		 String errorMessage = "";
		 Category select = viewCategory.getSelectionModel().getSelectedItem();
		 if(select == null){
			 errorMessage += "No selected category!\n"; 
			 	
			 if (newItemSubCategory.getText() == null || newItemSubCategory.getText().length() == 0) {
		            errorMessage += "No valid name subcategory!\n"; 
		        }
			 
			 Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("No selected category");
				alert.setHeaderText("No selected category!");
				alert.setContentText(errorMessage);
				alert.show();
		 }else{
			 if(itsNewSubCategory(select, newItemSubCategory.getText())){
				 user.addSubCategory(select, newItemSubCategory.getText());
		            showSubCategoryForEdit();
		            newItemSubCategory.setText("");
			 }
			 else{
				 Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Duplicate");
					alert.setHeaderText("Duplicate subcategory!");
					alert.setContentText(errorMessage);
					alert.show();
			 }
		 }		 	
		 
	 }
	 
	 public boolean itsNewSubCategory(Category select, String name){
		 for(String g : select.getNameSubCategory()){
			 if(g.equals(name)) return false;
		 }
		 return true;
	 }
	 
	 public  void deletinCategoryOrSubCategory(){
		 Category selectC = viewCategory.getSelectionModel().getSelectedItem();
		 String selectS = viewSubCategory.getSelectionModel().getSelectedItem();
		 if(selectC != null & selectS == null){
			 
			 user.getCategoryList().remove(selectC);
			 viewCategory.refresh();
			 viewCategory.setItems(FXCollections.observableArrayList(user.getCategoryList()));
			 viewSubCategory.refresh();
			 
		 }
		 else if(selectS != null){
			 user.deleteSubCategory(selectC, selectS);
			 viewCategory.refresh();
	   		 viewCategory.setItems(FXCollections.observableArrayList(user.getCategoryList()));
			 showSubCategoryForEdit();
		 }
		 else{
			 Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Nothing selected");
				alert.setHeaderText("No selected category or subcategory!");
				alert.setContentText("Nothing selected");
				alert.show();
		 }

		 
	 }
	 
	 public ArrayList<Category> getCategory(){
		 return user.getCategoryList();
	 }
	 
	 public  void showSubCategoryForEdit(){
		 
		 ObservableList<String> dataForListSubCategory = FXCollections.observableArrayList(viewCategory.getSelectionModel().getSelectedItem().getNameSubCategory());
		 viewSubCategory.refresh(); 
		 viewSubCategory.setItems(dataForListSubCategory);
		 
	 }

	public  Button getSaveCategory() {
		return saveCategory;
	}

	
	 
	 
}
