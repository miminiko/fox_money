/**
 * 
 */
package fox_money.ui;

import java.text.DecimalFormat;
import java.util.Locale;

import fox_money.Account;
import fox_money.Operation;
import fox_money.TypOperation;
import fox_money.User;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
public class AccountDialog extends Stage  {
	private  Image foxikIcon = new Image("im_fox.jpg");
	private  User u;
	private LangKey keyW;

	
	private  Account setAccount;
	private  int index;
	public  DecimalFormat df = new DecimalFormat("####0.00");

	private  boolean haveNewAccount = false;
	
	private  Button save;
	private  Button close;
	
	private  Label nameNewAccountL;
	private  TextField nameNewAccount = new TextField();
	private  Label currencyNewAccountL;
	private  ComboBox <String> currency;
	private  TextField currencyRate = new TextField();
	private  Label initialAccountL;
	private  TextField initialAmount = new TextField();
	
	
	public void init() throws Exception{
		System.out.println(Locale.getDefault());
		Locale.setDefault(Locale.US);
		
		
	}
	
	public AccountDialog(User u){
		
		this.getIcons().add(foxikIcon);
		this.u = u;
		keyW = u.getKeyUser();
		inicialDetails();
		this.setScene(new Scene(getCleanScene(),400, 200));
	}
	
	private void inicialDetails(){
		this.setTitle(keyW.getString("dialogAccountNew"));
		nameNewAccountL = new Label(keyW.getString("createAccountName"));
		currencyNewAccountL = new Label(keyW.getString("createAccountCurrency"));
		initialAccountL = new Label(keyW.getString("createAccountSum"));
		close = new Button(keyW.getString("buttonClose"));
		save = new Button(keyW.getString("buttonSave"));
	}
	
	public AccountDialog(User u, Account a){
		this.setTitle("Set account");
		this.getIcons().add(foxikIcon);
		this.u = u;
		keyW = u.getKeyUser();
		inicialDetails();
		this.setAccount = a;
		index = u.getIndexAccountInList(setAccount);
		this.setScene(new Scene(dialogSetAccount(),400, 200));
	}
	
	
	/////__��� ������ ��������
	private Parent getCleanScene(){
		BorderPane r = new BorderPane();
		currency = new ComboBox<String>(FXCollections.observableArrayList(u.getCurrencyStringList()));
		GridPane g = new GridPane();
		g.add(nameNewAccountL, 0,0);
		nameNewAccount.setText("");
		g.add(nameNewAccount, 1, 0);
		nameNewAccount.setMinSize(150, 20);
		g.add(currencyNewAccountL, 0, 1);
		HBox hBC = new HBox();
		currency.setValue(u.getMainCurrency().getName());
		currency.setMinWidth(70);
		currencyRate.setMinWidth(60);;
		currencyRate.setText("1");
		currency.setOnAction(e -> {
			currencyRate.setText(Double.toString(u.getCurrencyByName(currency.getValue()).getRate()));
		});
		

		hBC.getChildren().addAll(currency, currencyRate);
		hBC.setPrefSize(100, 20);
		g.add(hBC, 1, 1);
		g.add(initialAccountL, 0, 2);
		initialAmount.setEditable(true);
		initialAmount.setText("");
		g.add(initialAmount, 1, 2);
		initialAmount.setPrefSize(100, 20);
		
		g.setPrefSize(400, 400);
		g.setAlignment(Pos.CENTER);
		g.setPadding(new Insets(5));
		g.getChildren().forEach(node -> FlowPane.setMargin(node, new Insets(3)));
		r.setCenter(g);


		HBox hB = new HBox();
		
		hB.getChildren().addAll(save, close);
		hB.getChildren().forEach(n -> FlowPane.setMargin(n, new Insets(5)));

		hB.setAlignment(Pos.TOP_RIGHT);
		hB.setPadding(new Insets(5));
		
		g.setHgap(3);
		g.setVgap(3);
		
		r.setCenter(g);
		r.setPadding(new Insets(10 , 10 , 10, 10));
		r.setBottom(hB);
		
		
		return r;
		
	}
	
	public void addNewAccount(){
		 try{
			 double r = Double.parseDouble(currencyRate.getText().replace(',','.'));
			 try{
				 double s = Double.parseDouble(initialAmount.getText().replace(',','.'));
				 if(r>0 ){
					 
					 String c = currency.getSelectionModel().getSelectedItem().toString();
					 Account a = new Account(nameNewAccount.getText(),c, r );
					 u.addAccount(a);
					 u.addOperation(a, new Operation(s,currency.getSelectionModel().getSelectedItem().toString(), r, "Initial amount", TypOperation.PLUS ));
					 System.out.println(a.toString());
					 haveNewAccount = true;
				 }
				 else{
					 currencyRate.setText(currencyRate.getText() + " is not right number");
				 }
			 }catch(NumberFormatException e){
				 initialAmount.setText(initialAmount.getText() + " is not number");
			 }
		 }catch(NumberFormatException e){
			 currencyRate.setText(currencyRate.getText() + " is not number");
		 }
	}
	
	
	
	
	public Parent dialogSetAccount(){
			BorderPane r = new BorderPane();
			currency = new ComboBox<String>(FXCollections.observableArrayList(u.getCurrencyStringList()));
			 
			GridPane g = new GridPane();
			g.add(nameNewAccountL, 0,0);
			nameNewAccountL.setMinSize(150, 20);
			nameNewAccount.setText(setAccount.getNameAccount());
			g.add(nameNewAccount, 1, 0);
			g.add(currencyNewAccountL, 0, 1);
			HBox hBC = new HBox();
			currency.setValue(setAccount.getcAccount().getName());
			currency.setEditable(false);
			currency.setMinWidth(70);
			currencyRate.setMinWidth(60);;

			
			currencyRate.setText(df.format(setAccount.getcAccount().getRate()));
			hBC.getChildren().addAll(currency, currencyRate);
			g.add(hBC, 1, 1);
			g.add(initialAccountL, 0, 2);
			g.add(initialAmount, 1, 2);
			initialAmount.setEditable(false);
			initialAmount.setText(df.format(setAccount.getBalanceAccount()));
			g.setAlignment(Pos.CENTER);
			g.setPadding(new Insets(20));
			g.getChildren().forEach(node -> FlowPane.setMargin(node, new Insets(25, 25, 25, 25)));
			g.setHgap(3);
			g.setVgap(3);
			
			

			HBox hB = new HBox();
			
			hB.getChildren().addAll(save, close);
			hB.getChildren().forEach(n -> FlowPane.setMargin(n, new Insets(5)));
			hB.getChildren().forEach(n -> {((Button)n).setPrefWidth(60);});

			hB.setAlignment(Pos.TOP_RIGHT);
			hB.setPadding(new Insets(5));
			
			r.setCenter(g);
			r.setPadding(new Insets(10 , 10 , 10, 10));
			r.setBottom(hB);
			r.setMinSize(300, 200);

			

			return r;
	 }
	
	public void setAccount(){
		 
		 try{
			 double r = Double.parseDouble(currencyRate.getText().replace(',','.'));
			 try{
				 if(r>0 ){
					 u.getAccountList().get(index).setNameAccount(nameNewAccount.getText());
					 u.getAccountList().get(index).getcAccount().setExchangeRateFresh(r);
					 
					 haveNewAccount = true;
//					 dialogAccountStage.close();
//					 refreshAll();
				 }
				 else{
					 currencyRate.setText(currencyRate.getText() + " is not right number");

				 }
			 }catch(NumberFormatException e){
				 initialAmount.setText(initialAmount.getText() + " is not number");
			 }
			 
		 }catch(NumberFormatException e){
			 currencyRate.setText(currencyRate.getText() + " is not number");
		 }
		 
	 }

	public  Button getSave() {
		return save;
	}


	public  Button getClose() {
		return close;
	}

	/***
	 * ������ ������ � �����
	 * @return
	 */
	public  User getU() {
		return u;
	}

	public  void setU(User u) {
		this.u = u;
	}

	public  Account getSetAccount() {
		return setAccount;
	}

	public  void setSetAccount(Account setAccount) {
		this.setAccount = setAccount;
	}

	public  boolean isHaveNewAccount() {
		return haveNewAccount;
	}

	public void setHaveNewAccount(boolean t) {
		this.haveNewAccount = t;
	}
	
	
	 	 
	
}
