/**
 * 
 */
package fox_money.ui;

import java.util.Locale;

import fox_money.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author endem
 *
 */
public class DialogSetLanguage extends Stage{
	private  Image foxikIcon = new Image("im_fox.jpg");

	private User u;
	private LangKey keyW;
	private  Button save;
	private  Button close;
	private Button engButton;
	private Button ruButton;
	private Button czButton;
	
	public DialogSetLanguage(User u){
		keyW = u.getKeyUser();
		initDetails();
		this.u = u;
		this.getIcons().add(foxikIcon);
		this.setTitle("Set language");
		this.setScene(new Scene(getMyScene(),250, 150));
	}
	
	public void initDetails(){
		close = new Button(keyW.getString("buttonClose"));
		save = new Button(keyW.getString("buttonSave"));
		

	}
	
	public Parent getMyScene(){
		
		VBox vB = new VBox();
		engButton = new Button("English");
		engButton.setPrefWidth(150);
		ImageView imgEng = new ImageView(new Image("eng.jpg"));
		engButton.setGraphic(imgEng);
		engButton.setOnAction(e -> changeLanguageToEng());
		
		ruButton = new Button("Русский");
		ruButton.setPrefWidth(150);
		ImageView imgRu = new ImageView(new Image("ru.png"));
		ruButton.setGraphic(imgRu);
		ruButton.setOnAction(e -> changeLanguageToRu());
		
		czButton = new Button("Česky");
		czButton.setPrefWidth(150);
		ImageView imgCz = new ImageView(new Image("cz.jpg"));
		czButton.setGraphic(imgCz);
		czButton.setOnAction(e -> changeLanguageToCz());
		HBox hB = new HBox();
		
		hB.getChildren().addAll(save, close);
		hB.setAlignment(Pos.CENTER);
		hB.setSpacing(10);
		vB.getChildren().addAll(engButton, ruButton,czButton, hB);

		vB.getChildren().forEach(n -> FlowPane.setMargin(n, new Insets(10,10,10,10)));
		vB.setPadding(new javafx.geometry.Insets(5, 5, 5, 5));

		vB.setAlignment(Pos.CENTER);
		vB.setSpacing(5);
		return vB;
	}
	
	private void changeLanguageToRu(){
		
		Locale.setDefault(new Locale("ru", "RU"));
		keyW = new LangKey(Locale.getDefault());
		actualizeLocale();
	}
	
	private void changeLanguageToEng(){
		keyW = new LangKey(Locale.US);
		actualizeLocale();
	}
	
	private void changeLanguageToCz(){
		keyW = new LangKey(new Locale("cs", "CZ"));
		actualizeLocale();
	}
	
	private void actualizeLocale(){
		close.setText(keyW.getString("buttonClose"));
		save.setText(keyW.getString("buttonSave"));
	}

	public User getU() {
		return u;
	}

	public void setU(User u) {
		this.u = u;
	}

	public LangKey getKeyW() {
		return keyW;
	}

	public void setKeyW(LangKey keyW) {
		this.keyW = keyW;
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
