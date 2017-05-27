/**
 * 
 */
package fox_money.ui;

import java.util.ArrayList;
import java.util.Locale;

import fox_money.User;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author endem
 *
 */
public class DialogNewUser extends Stage {
	
	private LangKey keyW;
	private  Image foxikIcon = new Image("im_fox.jpg");
	private  User u;
	private static  TextField nameField;
	private  ComboBox<String> cB = new ComboBox<String>();
	private static  ArrayList <String> currencyList = new ArrayList<String>();
	private  Label l;
	private  Label currencyL;
	private  Label nameL;
	
	private static Button saveButton = new Button("Save and use");
//	private static Button close = new Button("Close");
	private static boolean haveNewUser = false;
	
	static{
		nameField =  new TextField();
		
		currencyList.add("USD"); // $
		currencyList.add("EUR"); //  €
		currencyList.add("RUB"); // ₽
		currencyList.add("GBR"); // £
		currencyList.add("CNY"); // ¥ (元)
		currencyList.add("JPY"); // ¥ (円)
		currencyList.add("PLN"); // zł
		currencyList.add("CZK"); // Kč
	}
	
	public DialogNewUser(){
		cB = new ComboBox<String>(
				FXCollections.observableArrayList(currencyList));

		this.setTitle("Create new user");

		this.getIcons().add(foxikIcon);
		setScene( new Scene(createPanels(), 300, 350));
		
	}
	
	public DialogNewUser(User u){
		this.u = u;
		this.keyW = u.getKeyUser();
		cB = new ComboBox<String>(
				FXCollections.observableArrayList(currencyList));

		this.setTitle(keyW.getString("labelSetUser"));

		this.getIcons().add(foxikIcon);
		setScene( new Scene(createPanelsForSet(), 350, 350));
		
	}
		

	
	@SuppressWarnings("static-access")
	private Parent createPanelsForSet(){
		BorderPane b = new BorderPane();
		
		nameL = new Label(keyW.getString("labelnameUser"));
		currencyL = new Label( keyW.getString("labelcurrencyUser")+"   ");
		
		b.setCenter(doTitle());
		nameField.setMinWidth(50);
		nameField.setStyle("-fx-fill : #0277BD");
		nameField.setPromptText("Your user name");
		nameField.setText(u.getNameUser());
		cB.setValue(u.getMainCurrency().getName());
		
		HBox h = new HBox();
		
		saveButton.setStyle("-fx-background-color : #29B6F6; -fx-text-fill : #E1F5FE; -fx-font-weight : 900");

		h.getChildren().add(saveButton);
		h.setAlignment(Pos.TOP_CENTER);
		h.setMargin(saveButton, new Insets(5,5,5,5));
		b.setBottom(h);
		
		return b;
	}
	
	@SuppressWarnings("static-access")
	private Parent createPanels(){
		BorderPane b = new BorderPane();
		
		nameL = new Label("User name");
		currencyL = new Label("Base currency   ");
		nameField.setMinWidth(70);
		nameField.setStyle("-fx-fill : #0277BD");
		nameField.setPromptText("Your user name");
//		cB.setValue("Select the main currency");
		b.setCenter(doTitle());

		HBox h = new HBox();		
		saveButton.setStyle(
				"-fx-background-color : "
				+ "#29B6F6; -fx-text-fill : "
				+ "#E1F5FE; -fx-font-weight : 900");
		h.getChildren().add(saveButton);
		h.setAlignment(Pos.TOP_CENTER);
		h.setMargin(saveButton, new Insets(5,5,5,5));		
		b.setBottom(h);
		
		return b;
	}
	
	
	private Node doTitle(){
		VBox v = new VBox();
		try{
			Image img = new Image("userImage.png");
			
			ImageView i = new ImageView(img);
			i.setFitHeight(130);
			i.setFitWidth(130);
			i.setPreserveRatio(true);
			
			v.getChildren().add(i);
		}catch(IllegalArgumentException e){
			System.out.println("изображение не нашлось");
		}
		
		
		l = new Label("Create New User");
		l.setStyle(""
//				+ "-fx-text-fill   : #C06CC0;"
				+ "-fx-font-weight : bolder;"
				+ "-fx-font-size   : 26px;");
		
		v.getChildren().add(l);
		v.getChildren().add(getInfoPanel());
		v.setAlignment(Pos.CENTER);
		v.setPadding(new Insets(20,20,20,20));
		return v;
	}
	
	private  Node getInfoPanel(){
		GridPane g = new GridPane();
		

		g.add(nameL, 0, 0);
		
		
		g.add(nameField, 1, 0);
		
		g.add(currencyL, 0, 1);
		cB.setEditable(true);
		cB.setMinWidth(50);
		
		g.add(cB, 1, 1);
		g.setAlignment(Pos.TOP_CENTER);
		g.setHgap(3);
		g.setVgap(3);
		g.setPadding(new Insets(10,10,10,10));
		return g;
	}
	
	public void addNewUser(){
		if(nameField.getText() != "" && cB.getValue() != null){
			u = new User(nameField.getText(), cB.getValue());
			
				switch(cB.getValue()){
					case "USD": u.getMainCurrency().setSymbol("$");
						break;
					case "EUR": u.getMainCurrency().setSymbol("€");
						break;
					case "RUB": u.getMainCurrency().setSymbol("₽");
						break;
					case "GBR": u.getMainCurrency().setSymbol("£");
						break;
					case "CNY": u.getMainCurrency().setSymbol("¥/元");
						break;
					case "JPY": u.getMainCurrency().setSymbol("¥/円");
						break;
					case "PLN": u.getMainCurrency().setSymbol("zł");
						break;
					case "CZK": u.getMainCurrency().setSymbol("Kč");
						break;
				}
					 
			
			haveNewUser = true;
		}
	}

	public User getU() {
		keyW = new LangKey(Locale.US);
		return u;
	}

	public void setU(User u) {
		this.u = u;
	}

	
	
	
	public Button getSaveButton() {
		return saveButton;
	}

	
	public boolean isHaveNewUser() {
		return haveNewUser;
	}

	public static void setHaveNewUser(boolean haveNewUser) {
		DialogNewUser.haveNewUser = haveNewUser;
	}
	
	
}
