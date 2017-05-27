/**
 * 
 */
package fox_money.ui;

import java.text.DecimalFormat;

import fox_money.Account;
import fox_money.User;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * @author endem
 *
 */
public class DialogTransfer extends Stage {
	
	private  Image foxikIcon = new Image("im_fox.jpg");
	private  User user;
	private LangKey keyW;

	private  DecimalFormat df = new DecimalFormat("####0.00");
	private boolean haveNewAccount = false;

	private  Button save = new Button("Save");
	private  Button close = new Button("Close");

//	private Stage transferOperation = new Stage();
	private Label firstAccountL;
	private ChoiceBox<Account> accountMenuFirst;
	private Label secondAccountL;
	private ChoiceBox<Account> accountMenuSecond;
	private Label sumTransferL;
	private TextField sumTransfera = new TextField();
	private ChoiceBox<String> currencyTransferChoice;
	private TextField rateCurTransfer = new TextField();
	private Label totalTransferL;
	private TextField totalSumTransfer = new TextField();
	
	public DialogTransfer(User u){
		this.user = u;
		this.getIcons().add(foxikIcon);
		this.setTitle("Transfer money");
		keyW = u.getKeyUser();
		inicial();
		this.setScene(new Scene(getTransferScene(),390, 240));

	}
	
	public void inicial(){
		firstAccountL = new Label(keyW.getString("transfer1Account"));
		secondAccountL = new Label("transfer2Account");
		sumTransferL = new Label("transferSum");
		totalTransferL = new Label("transferTotal");
	}
	
	private Parent getTransferScene(){
		
		BorderPane r = new BorderPane();
		GridPane g = new GridPane();
		g.add(firstAccountL, 0, 0);
		accountMenuFirst = new  ChoiceBox<Account>(FXCollections.observableArrayList(user.getAccountList()));
		firstAccountL.setPrefSize(130, 20);
		accountMenuFirst.setPrefSize(200, 20);
		g.add(accountMenuFirst, 1, 0);
		
		g.add(secondAccountL, 0, 1);
		accountMenuSecond =  new  ChoiceBox<Account>(FXCollections.observableArrayList(user.getAccountList()));
		g.add(accountMenuSecond, 1, 1);
		accountMenuSecond.setPrefSize(200, 20);
		
		g.add(sumTransferL, 0, 2);
		g.add(sumTransfera, 1, 2);
		sumTransfera.setPrefSize(100, 20);
		sumTransfera.setMaxSize(200, 20);
		
		
		currencyTransferChoice = new  ChoiceBox<String>(FXCollections.observableArrayList(user.getCurrencyStringList()));
		accountMenuFirst.setOnAction(e -> {
			currencyTransferChoice.setValue(((Account)accountMenuFirst.getValue()).getcAccount().toString());
			rateCurTransfer.setText(df.format(((Account)accountMenuFirst.getValue()).getcAccount().getRate()));
		});
		
		sumTransfera.setOnAction(e -> {
			try{
				double s = Double.parseDouble(sumTransfera.getText().replace(',','.'));
				double rate = Double.parseDouble(rateCurTransfer.getText().replace(',','.'));
				totalSumTransfer.setText(Double.toString(rate*s));

			}catch(NumberFormatException e1){
				System.out.println("");
			}
		});
		
		currencyTransferChoice.setPrefSize(100, 20);
		g.add(currencyTransferChoice, 0, 3);
		g.add(rateCurTransfer, 1, 3);
		
		g.add(totalTransferL, 0, 5);
		g.add(totalSumTransfer, 1, 5);
		totalSumTransfer.setEditable(false);
		totalSumTransfer.setPrefSize(200, 20);

		HBox hB = new HBox();
		 save = new Button("Save");
//		save.setOnAction(event -> 
//			addNewOperationToAccount());
		 close = new Button("Close");
		
		hB.getChildren().addAll(save, close);
		hB.getChildren().forEach(n -> FlowPane.setMargin(n, new Insets(5)));
		hB.getChildren().forEach(n -> {((Button)n).setPrefWidth(60);});
		hB.setAlignment(Pos.TOP_RIGHT);
		

		g.setHgap(3);
		g.setVgap(3);
		g.setPadding(new Insets (5,5,5,5));
		
		
		r.setCenter(g);
		r.setPadding(new Insets(25 , 25 , 25, 25));
		r.setBottom(hB);
		
		return r;		
	}
	
	
	 private boolean isForTransferInputValid() {
	        String errorMessage = "";

	        if (accountMenuFirst.getValue() == null ) {
	            errorMessage += keyW.getString("erorAccount") + "\n"; 
	        }
	        if (accountMenuSecond.getValue() == null) {
	        	errorMessage += keyW.getString("erorAccount") + "\n"; 
	        }
	        if (sumTransfera.getText() == null || sumTransfera.getText().length() == 0) {
	        	errorMessage += keyW.getString("erorSum") + "\n"; 
	        }
	        if (currencyTransferChoice.getValue() == null) {
	        	errorMessage += keyW.getString("erorRate") + "\n"; 
	        
	        }
	        if (accountMenuFirst.getValue().equals(accountMenuSecond.getValue())) {
	        	errorMessage += keyW.getString("erorAccount") + "\n"; 
	        }
	        else {
	            // try to parse the postal code into an int.
	            try {
	                Double.parseDouble(sumTransfera.getText().replace(',','.'));

	            } catch (NumberFormatException e) {
	            	errorMessage += keyW.getString("erorNumber") + " " + sumTransfera.getText() + "\n"; 
	            }
	            try {
	                Double.parseDouble(rateCurTransfer.getText().replace(',','.'));

	            } catch (NumberFormatException e) {
	            	errorMessage += keyW.getString("erorNumber") + " " + rateCurTransfer.getText() + "\n"; 

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

	 public void addTranferOperation(){
		 if(isForTransferInputValid()){
			 haveNewAccount = true;
			 System.out.println("addTranferOperation()");
			 user.addTransferOperation(
					 (Account)accountMenuFirst.getValue(), (Account)accountMenuSecond.getValue(), 
					 Double.parseDouble(sumTransfera.getText().replace(',','.')),  
					 Double.parseDouble(rateCurTransfer.getText().replace(',','.')), currencyTransferChoice.getValue());
		 }
	 }

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isHaveNewAccount() {
		return haveNewAccount;
	}

	public void setHaveNewAccount(boolean haveNewAccount) {
		this.haveNewAccount = haveNewAccount;
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
	 
	 
	 
}
