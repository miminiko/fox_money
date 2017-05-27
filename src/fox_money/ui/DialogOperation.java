package fox_money.ui;

import java.text.DecimalFormat;
import java.time.LocalDate;

import fox_money.Account;
import fox_money.Category;
import fox_money.Operation;
import fox_money.Status;
import fox_money.TypOperation;
import fox_money.User;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class DialogOperation extends Stage {
	
	private  Image foxikIcon = new Image("im_fox.jpg");
	private  User user;
	private LangKey keyW;
	
	private  Account account;
	private  Operation setOperation;

	private  DecimalFormat df = new DecimalFormat("####0.00");
	

	private static boolean haveNewAccount = false;

	
	private  Button save;
	private  Button close;

	
	private  Label accountL;
	private  ChoiceBox <Account> accountMenu;
	private  Label typeOfTransL;
	private  ChoiceBox<String> typChoice = new  ChoiceBox<String>(FXCollections.observableArrayList("plus", "minus"));
	private  Label totalL;
	private  TextField totalSumOperation = new TextField();
	private  Label currencyL;
	private  ChoiceBox<String> curChoice;
	private  TextField rateCur = new TextField();
	private  Label totalSum = new Label();
	private  Label dateL;
	private  DatePicker dateOperation = new DatePicker();
	private  Label categoryL;
	private  ComboBox <String> categoryChoice;
	private  Label subcategoryL;
	private  ComboBox <String> subcategoryChoice;
	private  Label contrAgentL;
	private  ComboBox  <String> contrAgentChoice;
	private  Label noteL;
	private  TextArea noteArea = new TextArea();
	private  CheckBox  itsPlan;
	private Status statusOperation = Status.ACTUALY;
	
	
	
	
	
	public DialogOperation(User user, Account account){
		

		this.user = user;
		if(account != null){
			this.account = account;
		}
		this.getIcons().add(foxikIcon);
		
		keyW = user.getKeyUser();
		initDetails();
		this.setScene(new Scene(getCleanScene(),390, 340));
	}
	
	private void initDetails(){
		this.setTitle(keyW.getString("dialogOperationNew"));

		accountL = new Label(keyW.getString("createOpearionAccountName"));
		typeOfTransL = new Label(keyW.getString("createOpearionAccountType"));
		totalL = new Label(keyW.getString("createOpearionAccountTotal"));
		currencyL = new Label(keyW.getString("createOpearionAccountCurrency"));
		dateL = new Label(keyW.getString("createOpearionAccountDate"));
		categoryL = new Label(keyW.getString("createOpearionAccountCategory"));
		subcategoryL = new Label(keyW.getString("createOpearionAccountSubCategory"));
		contrAgentL = new Label(keyW.getString("createOpearionAccountContragent"));
		noteL = new Label(keyW.getString("createOpearionAccountNote"));
		itsPlan = new CheckBox(keyW.getString("createOpearionAccountPlan")+ "  ");
		
		
		categoryChoice = new  ComboBox<String>(FXCollections.observableArrayList(user.getCategoryListString()));
		accountMenu = new  ChoiceBox<Account>(FXCollections.observableArrayList(user.getAccountList()));
		curChoice = new  ChoiceBox<String>(FXCollections.observableArrayList(user.getCurrencyStringList()));
		contrAgentChoice = new ComboBox<String>(FXCollections.observableArrayList(user.getContragetnList()));

		close = new Button(keyW.getString("buttonClose"));
		save = new Button(keyW.getString("buttonSave"));
	}
	
	public DialogOperation(User user, Operation o, Account account){
		
		this.user = user;
		this.getIcons().add(foxikIcon);
		this.setOperation = o;
		this.account = account;
		keyW = user.getKeyUser();
		initDetails();
		this.setScene(new Scene(geSceneForSet(),390, 340));
	}
	
	public Parent getCleanScene(){
		BorderPane r = new BorderPane();
		GridPane g = new GridPane();
		g.add(accountL, 0, 0);
		accountL.setPrefSize(150, 20);

		accountMenu.setPrefSize(200, 20);
		
		g.add(accountMenu, 1, 0);
		
		g.add(typeOfTransL, 0, 1);
		typChoice.setOnAction(e -> lookingCategory(typChoice));
		g.add(typChoice, 1, 1);
		typChoice.setPrefSize(200, 20);
		
		g.add(totalL, 0, 2);
		g.add(totalSumOperation, 1, 2);
		totalSumOperation.setPrefSize(100, 20);
		totalSumOperation.setMaxSize(200, 20);
		
		
		g.add(currencyL, 0, 3);
		HBox h = new HBox();
		h.getChildren().addAll(curChoice, rateCur);
		h.setPrefSize(100, 20);
		g.add(h, 1, 3);
		
		
		curChoice.setOnAction(e -> {
			if(accountMenu.getValue().getcAccount().getName().equals(curChoice.getValue())){
				rateCur.setText("1");
			}else if(accountMenu.getValue().getcAccount().isItMainCurrency() ){
				rateCur.setText(df.format(accountMenu.getValue().getcAccount().getRate()));
			}else if(user.getCurrencyByName(curChoice.getValue()).isItMainCurrency() ){
				rateCur.setText(df.format(1/accountMenu.getValue().getcAccount().getRate()));
			}else if(accountMenu.getValue().getcAccount().getName() != curChoice.getValue()){
				rateCur.setText(df.format(
						1/accountMenu.getValue().getcAccount().getRate()*user.getCurrencyByName(curChoice.getValue()).getRate()
								));
				
			}
		});
		accountMenu.setOnAction(e -> {
			curChoice.setValue(accountMenu.getValue().getcAccount().getName());
		});
		if(account != null){
			accountMenu.setValue(account);
		}

		totalSumOperation.setOnAction(e -> {
			if(accountMenu.getValue() != null){
				if(totalSumOperation.getText() != null && totalSumOperation.getText().length() != 0){
					try{
						double sum = Double.parseDouble(totalSumOperation.getText().replace(',','.'));
						try{
							
							double rate = Double.parseDouble(rateCur.getText().replace(',','.'));
							if(rate>0){
								totalSum.setFont(new Font(12));
								totalSum.setTextAlignment(TextAlignment.JUSTIFY);
								totalSum.setTextFill(Color.PURPLE);
								totalSum.setText(Double.toString(sum*rate) + " " + ((Account)accountMenu.getValue()).getcAccount().getName());;
							}
							
						}catch(NumberFormatException en){
//							System.out.println("Nothing");
						}
					}catch(NumberFormatException en){
//						System.out.println("Nothing");
					}
				}
			}
			
		});
		
		g.add(totalSum, 1, 5);
		
		g.add(dateL, 0, 6);
		g.add(dateOperation, 1, 6);
		dateOperation.setValue(LocalDate.now());
		dateOperation.setPrefSize(200, 20);

		
		g.add(categoryL, 0, 7);
		categoryChoice.setPrefSize(200, 20);

		categoryChoice.setOnAction(event -> lookingSubCategory( categoryChoice));
		
		g.add(categoryChoice, 1, 7);
		
		g.add(subcategoryL, 0, 8);
		subcategoryChoice = new  ComboBox<String>();
		subcategoryChoice.setPrefSize(200, 20);

		g.add(subcategoryChoice, 1, 8);
		
		categoryChoice.setEditable(true);
		subcategoryChoice.setEditable(true);

		
		contrAgentChoice.setEditable(true);
		g.add(contrAgentL, 0, 9);
		contrAgentChoice.setPrefSize(200, 20);
		
		g.add(contrAgentChoice, 1, 9);

		g.add(noteL, 0, 10);
		noteArea.setPrefSize(200, 40);
		g.add(noteArea, 1, 10);

		g.setHgap(3);
		g.setVgap(3);
		
		
		HBox hB = new HBox();

//		save.setOnAction(event -> addNewOperationToAccount());
		itsPlan.setOnAction(e -> {
			
			if(itsPlan.isSelected()){
				 statusOperation = Status.PLAN;
				 System.out.println("PLAN");
			}
			
		});
		
		hB.getChildren().addAll(itsPlan, save, close);
		hB.getChildren().forEach(n -> FlowPane.setMargin(n, new Insets(10,10,10,10)));

		hB.setAlignment(Pos.TOP_RIGHT);
		
		r.setCenter(g);
		r.setPadding(new Insets(10 , 10 , 10, 10));
		r.setBottom(hB);

		
		return r;
	}
	
	
	public Parent geSceneForSet(){
		BorderPane r = new BorderPane();
		GridPane g = new GridPane();
		g.add(accountL, 0, 0);
		accountMenu = new  ChoiceBox<Account>(FXCollections.observableArrayList(user.getAccountList()));
		accountL.setPrefSize(150, 20);
		
		accountMenu.setValue(account);
		accountMenu.setOnAction(e -> {
//			curChoice.getSelectionModel().select(accountMenu.getValue().getcAccount());
			curChoice.setValue(accountMenu.getValue().getcAccount().getName());
		});
		
		accountMenu.setPrefSize(200, 20);
		g.add(accountMenu, 1, 0);
		
		g.add(typeOfTransL, 0, 1);
		g.add(typChoice, 1, 1);
		typChoice.setPrefSize(200, 20);
		typChoice.setValue(setOperation.getTyp().toString());

		g.add(totalL, 0, 2);
		g.add(totalSumOperation, 1, 2);
		totalSumOperation.setPrefSize(100, 20);
		totalSumOperation.setMaxSize(200, 20);
		totalSumOperation.setText(Double.toString(setOperation.getSum()));

		
		g.add(currencyL, 0, 3);
		HBox h = new HBox();
		curChoice = new  ChoiceBox<String>(FXCollections.observableArrayList(user.getCurrencyStringList()));
		h.getChildren().addAll(curChoice, rateCur);
		h.setPrefSize(100, 20);
		g.add(h, 1, 3);
		
		curChoice.setOnAction(e -> {
			if(accountMenu.getValue().getcAccount().getName() == curChoice.getValue()){
				rateCur.setText("1");
			}else if(accountMenu.getValue().getcAccount().isItMainCurrency() ){
				rateCur.setText(df.format(accountMenu.getValue().getcAccount().getRate()));
			}else if(user.getCurrencyByName(curChoice.getValue()).isItMainCurrency() ){
				rateCur.setText(df.format(1/accountMenu.getValue().getcAccount().getRate()));
			}else if(accountMenu.getValue().getcAccount().getName() != curChoice.getValue()){
				rateCur.setText(df.format(
						1/accountMenu.getValue().getcAccount().getRate()*user.getCurrencyByName(curChoice.getValue()).getRate()
								));
				
			}
		});
		accountMenu.setOnAction(e -> {
//			curChoice.getSelectionModel().select(accountMenu.getValue().getcAccount());
			curChoice.setValue(accountMenu.getValue().getcAccount().getName());
		});
		
		curChoice.setValue(setOperation.getcOperation().getName());

		
		
		
		rateCur.setOnAction(e -> {
			if(accountMenu.getValue() != null){
				if(totalSumOperation.getText() != null && totalSumOperation.getText().length() != 0){
					try{
						double sum = Double.parseDouble(totalSumOperation.getText().replace(',','.'));
						try{
							double rate = Double.parseDouble(rateCur.getText().replace(',','.'));
							if(rate>0){
								totalSum.setFont(new Font(12));
								totalSum.setTextAlignment(TextAlignment.JUSTIFY);
								totalSum.setTextFill(Color.PURPLE);
								totalSum.setText(Double.toString(sum*rate) + " " + ((Account)accountMenu.getValue()).getcAccount().getName());;
							}
							
						}catch(NumberFormatException en){
//							System.out.println("Nothing");
						}
					}catch(NumberFormatException en){
//						System.out.println("Nothing");
					}
				}
			}
			
		});

		rateCur.setText(Double.toString(setOperation.getcOperation().getRate()));

		
		g.add(totalSum, 1, 5);
		
		g.add(dateL, 0, 6);
		g.add(dateOperation, 1, 6);
//		dateOperation.setValue(LocalDate.now());
		dateOperation.setPrefSize(200, 20);
		dateOperation.setValue(setOperation.getDate());

		
		g.add(categoryL, 0, 7);
		categoryChoice = new  ComboBox<String>(FXCollections.observableArrayList(user.getCategoryListString()));
		categoryChoice.setPrefSize(200, 20);
		
		
		categoryChoice.setOnAction(event -> lookingSubCategory( categoryChoice));
		
		g.add(categoryChoice, 1, 7);
		
		g.add(subcategoryL, 0, 8);
		subcategoryChoice = new  ComboBox<String>();
		subcategoryChoice.setValue(setOperation.getSubcategory());
		
		categoryChoice.setEditable(true);
		categoryChoice.setValue(setOperation.getCategory());
		System.out.println(setOperation.getCategory());
		System.out.println(categoryChoice.getValue());

		subcategoryChoice.setPrefSize(200, 20);

		g.add(subcategoryChoice, 1, 8);
		
		
		subcategoryChoice.setEditable(true);

		contrAgentChoice = new ComboBox<String>(FXCollections.observableArrayList(user.getContragetnList()));
		contrAgentChoice.setEditable(true);
		contrAgentChoice.setValue(setOperation.getContragent());

		g.add(contrAgentL, 0, 9);
		contrAgentChoice.setPrefSize(200, 20);
		
		g.add(contrAgentChoice, 1, 9);

		g.add(noteL, 0, 10);
		noteArea.setPrefSize(200, 40);
		noteArea.setText(setOperation.getNote());

		g.add(noteArea, 1, 10);

		g.setHgap(3);
		g.setVgap(3);
		
		
		HBox hB = new HBox();

		save.setOnAction(event -> addNewOperationToAccount());
		
		if(setOperation.getIsIt().equals(Status.PLAN)){
			itsPlan.setSelected(true);
		}
		
		itsPlan.setOnAction(e -> {
			
			if(itsPlan.isSelected()){
				 statusOperation = Status.PLAN;
				 System.out.println("PLAN");
			}
			
		});
		
		

		
		hB.getChildren().addAll(itsPlan, save, close);
		hB.getChildren().forEach(n -> FlowPane.setMargin(n, new Insets(10,10,10,10)));

		hB.setAlignment(Pos.TOP_RIGHT);
		
		r.setCenter(g);
		r.setPadding(new Insets(10 , 10 , 10, 10));
		r.setBottom(hB);

		
		return r;
	}
	
	
	public void addNewOperationToAccount(){
		 if(isInputValid()){
			 TypOperation typ = TypOperation.getTyp(typChoice.getValue());
			 String contrAgent =  contrAgentChoice.getValue();
			 if(contrAgent == ""){
				 contrAgent = "-";
			 }
			 else if(contrAgent == null){
				 contrAgent = "-";
			 }
			 else{
				 user.getContragetnList().add(contrAgentChoice.getValue());
			 }
			 System.out.println(statusOperation.getName());
			 double rate = 1;
			 if(accountMenu.getValue().getcAccount().isItMainCurrency()){
				 rate = Double.parseDouble(rateCur.getText().replace(',','.'));
			 }
			 else{
				 rate = 1/Double.parseDouble(rateCur.getText().replace(',','.'));
			 }
			 
			 Operation o = new Operation(dateOperation.getValue(), 
					 Double.parseDouble(totalSumOperation.getText().replace(',','.')), curChoice.getValue(), rate,
					 typ , categoryChoice.getValue(), subcategoryChoice.getValue(), 
					 contrAgent, noteArea.getText(), statusOperation
					 );
			 user.addOperation((Account)accountMenu.getValue(), o);
			 if(categoryChoice.getValue() != "") user.addNewCategoryWithSubCategory(typ, categoryChoice.getValue(), subcategoryChoice.getValue());
			 if(contrAgentChoice.getValue() != "") user.addContragetnToList(contrAgentChoice.getValue());
			 
			 haveNewAccount = true;
		 }
		 else{
			 System.out.println("������ �� ����������!!!");
		 }
	 }
	
	public void setOneOperationInAccount(){
		 if(isInputValid()){
			 String contrAgent =  contrAgentChoice.getValue();
			 if(contrAgent == ""){
				 contrAgent = "-";
			 }
			 else if(contrAgent == null){
				 contrAgent = "-";
			 }
			 else{
				 user.getContragetnList().add(contrAgentChoice.getValue());
			 }
			 
			 
			 if(account.equals(accountMenu.getValue())){
				 
				 user.deleteOperation(account, setOperation);
				 TypOperation typ = TypOperation.getTyp(typChoice.getValue());
				 Operation o = new Operation(dateOperation.getValue(), 
						 Double.parseDouble(totalSumOperation.getText().replace(',','.')), curChoice.getValue(), Double.parseDouble(rateCur.getText().replace(',','.')),
						 typ , categoryChoice.getValue(), subcategoryChoice.getValue(), 
						 contrAgent, noteArea.getText(), statusOperation
						 );
				 user.addOperation(account, o);
				 if(categoryChoice.getValue() != "") user.addNewCategoryWithSubCategory(typ, categoryChoice.getValue(), subcategoryChoice.getValue());
				 if(contrAgentChoice.getValue() != "") user.addContragetnToList(contrAgentChoice.getValue());

				 haveNewAccount = true;
				 
			 }else if(!(account.equals(accountMenu.getValue()))){
				 user.deleteOperation(account, setOperation);
				 
				 TypOperation typ = TypOperation.getTyp(typChoice.getValue());
				 Operation o = new Operation(dateOperation.getValue(), 
						 Double.parseDouble(totalSumOperation.getText().replace(',','.')), curChoice.getValue(), Double.parseDouble(rateCur.getText().replace(',','.')),
						 typ , categoryChoice.getValue(), subcategoryChoice.getValue(), 
						 contrAgent, noteArea.getText(), statusOperation
						 );
				 user.addOperation((Account)accountMenu.getValue(), o);
				 
				 if(categoryChoice.getValue() != "") user.addNewCategoryWithSubCategory(typ, categoryChoice.getValue(), subcategoryChoice.getValue());
				 if(contrAgentChoice.getValue() != "") user.addContragetnToList(contrAgentChoice.getValue());

				 haveNewAccount = true;
			 }
			 
		 }
		 else{
			 System.out.println("������ �� ����������!!!");
		 }
	 }
	
	
	 private  boolean isInputValid() {
	        String errorMessage = "";

	        if (accountMenu.getSelectionModel().getSelectedItem() == null ) {
	            errorMessage += keyW.getString("erorAccount") + "\n"; 
	            System.out.println(errorMessage);
	        }
	        if (typChoice.getValue() == null) {
	            errorMessage += keyW.getString("erorTyp") + "\n"; 
	            System.out.println(errorMessage);

	        }
	        if (totalSumOperation.getText() == null || totalSumOperation.getText().length() == 0) {
	        	errorMessage += keyW.getString("erorSum") + "\n"; 
	            System.out.println(errorMessage);

	        }
	        if (curChoice.getValue() == null) {
	        	errorMessage += keyW.getString("erorCurrency") + "\n"; 
	            System.out.println(errorMessage);

	        }
	        if (categoryChoice.getValue() == null) {
	        	errorMessage += keyW.getString("erorCategory") + "\n"; 
	            System.out.println(errorMessage);

	        }
//	        if (subcategoryL.getText() == null || subcategoryL.getText().length() == 0) {
//	            errorMessage += "No selected subcategory  operation!\n"; 
//	            System.out.println(errorMessage);
//
//	        }
	        if (rateCur.getText() == null || rateCur.getText().length() == 0) {
	        	errorMessage += keyW.getString("erorRate") + "\n"; 
	            System.out.println(errorMessage);

	        } else {
	            // try to parse the postal code into an int.
	            try {
	                Double.parseDouble(totalSumOperation.getText().replace(',','.'));

	            } catch (NumberFormatException e) {
	            	errorMessage += totalSumOperation.getText() + " " + keyW.getString("erorNumber") + "\n"; 
	            }
	            try {
	                Double.parseDouble(rateCur.getText().replace(',','.'));

	            } catch (NumberFormatException e) {
	            	System.out.println(rateCur.getText().replace(',','.'));
	                errorMessage += keyW.getString("erorRate") + "\n"; 
	            }
	        }

	        
	        if (errorMessage.length() == 0) {
	            return true;
	        } else {
	            // Show the error message.
	        	Alert alert = new Alert(AlertType.WARNING);
				alert.setContentText(keyW.getString("erorTitle"));
				alert.setTitle(keyW.getString("erorTitle"));
				alert.setHeaderText(errorMessage);
				alert.showAndWait();
	        	
	            return false;
	        }
	    }
	 
	 
	 private void lookingSubCategory(ComboBox<String> c){
		 
		 String category = c.getSelectionModel().getSelectedItem().toString();
		 if(category != null && category != ""){
			 for(Category cat : user.getCategoryList()){
				 if(category.equals(cat.getCategoryName())){
					 subcategoryChoice.setItems(FXCollections.observableArrayList(cat.getNameSubCategory()));
				 }
			 }
		 }		 
	 }
	 
	 private void lookingCategory(ChoiceBox<String> typOperation){
		 TypOperation typ = TypOperation.getTyp(typOperation.getValue());
		 if(typ != null){
			 categoryChoice.setItems(FXCollections.observableArrayList(user.getCategoryByTyp(typ)));
		 }
	 }

	public Button getSave() {
		return save;
	}

	public void setSave(Button save) {
		this.save = save;
	}

	public Button getClose() {
		return close;
	}

	public void setClose(Button close) {
		this.close = close;
	}

	public static boolean isHaveNewAccount() {
		return haveNewAccount;
	}

	public static void setHaveNewAccount(boolean haveNewAccount) {
		DialogOperation.haveNewAccount = haveNewAccount;
	}

	public  User getUser() {
		return user;
	}

	public  void setUser(User user) {
		this.user = user;
	}
	 
	 
	
}
