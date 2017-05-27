/**
 * 
 */
package fox_money.ui;


import java.util.ArrayList;

import fox_money.Currency;
import fox_money.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;

/**
 * @author endem
 *
 */
public class DialogCurrency extends Stage {

	private User u;
	private LangKey keyW;

	private  Image foxikIcon = new Image("im_fox.jpg");
	public  Image imgDelete = new Image("deleting.png");
	private  ImageView iDelete = new ImageView(imgDelete);

	private  Button save;
	private  Button close;
	
	private  Button add = new Button();
	private  Button remove = new Button();

	
	@SuppressWarnings("unused")
	private ObservableList<Currency> dataForListSubCategory;

	
	private TableView<Currency> table =  new TableView<Currency>();
	private TableColumn<Currency, String> name;
	private TableColumn<Currency, String> symbol;
	private TableColumn<Currency, Double> rate;
	
	private Label nameL;
	private Label symbolL;
	private Label rateL;

	
	private TextField nameField = new TextField();
	private TextField symbolField = new TextField();
	private TextField rateField = new TextField();

	

	@SuppressWarnings("unchecked")
	public DialogCurrency(User u){
		this.u = u;
		keyW = u.getKeyUser();
		initDetails();
		iDelete.setFitHeight(20);
		iDelete.setFitWidth(20);
		remove.setGraphic(iDelete);
		
		ImageView iAdding = new ImageView(new Image("adding.png"));
		iAdding.setFitHeight(20);
		iAdding.setFitWidth(20);
		add.setGraphic(iAdding);
		
		name.setCellValueFactory(new PropertyValueFactory<Currency, String>("Name"));
		name.setCellFactory(TextFieldTableCell.forTableColumn());
		name.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Currency, String>>() {

			@Override
			public void handle(CellEditEvent<Currency, String> event) {
				event.getTableView().getItems().get(event.getTablePosition().getRow()).setName(event.getNewValue());
			}
		});
		
		
		symbol.setCellValueFactory(new PropertyValueFactory<Currency, String>("Symbol"));
		symbol.setMaxWidth(70);
		symbol.setMinWidth(50);
		symbol.setCellFactory(TextFieldTableCell.<Currency>forTableColumn());
		symbol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Currency, String>>() {
			@Override
			public void handle(CellEditEvent<Currency, String> event) {
				event.getTableView().getItems().get(event.getTablePosition().getRow()).setSymbol(event.getNewValue());;
			}
		});
		
		symbolL.setMaxWidth(50);
		symbolField.setMaxWidth(50);
		symbolField.setMinWidth(50);
		rate.setCellValueFactory(new PropertyValueFactory<Currency, Double>("Rate"));
		rate.setCellFactory(TextFieldTableCell.<Currency, Double>forTableColumn(new DoubleStringConverter()));
		rate.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Currency, Double>>() {

			@Override
			public void handle(CellEditEvent<Currency, Double> event) {
				event.getTableView().getItems().get(event.getTablePosition().getRow()).setRate(event.getNewValue());;
			}
		});

		rate.setEditable(true);
		
		

		table.setEditable(true);
		table.setItems(getElements());
		table.getColumns().addAll(name, symbol, rate);
		
		table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		
		this.getIcons().add(foxikIcon);
		
		this.setScene(new Scene(getPanel(), 250, 300));

	}
	
	public void initDetails(){
		this.setTitle(keyW.getString("dialogCurrency"));
		close = new Button(keyW.getString("buttonClose"));
		save = new Button(keyW.getString("buttonSave"));
		name = new TableColumn<Currency, String>(keyW.getString("currencyName"));
		symbol = new TableColumn<Currency, String>(keyW.getString("currencySymbol"));
		rate = new TableColumn<Currency, Double>(keyW.getString("currencyRate"));
		nameL = new Label(keyW.getString("currencyName")+": ");
		symbolL = new Label(keyW.getString("currencySymbol")+": ");
		rateL = new Label(keyW.getString("currencyRate")+": ");
	}
	
	private ObservableList<Currency> getElements(){
		return dataForListSubCategory = FXCollections.observableArrayList(u.getCurrencyList());
	}
	
	private Parent getPanel(){
		BorderPane b = new BorderPane();
		b.setCenter(getControlPanel());
		b.setBottom(getButtonPanel());
//		b.setAlignment(getButtonPanel(), Pos.BOTTOM_LEFT);
		return b;
	}
	
	private Node getButtonPanel(){
		HBox hB = new HBox();
		hB.getChildren().addAll(save, close);
		hB.setAlignment(Pos.TOP_RIGHT);
		
		hB.setPadding(new Insets(5));
		return hB;
	}
	
	private Node getControlPanel(){
		VBox vB = new VBox();
		vB.getChildren().add(table);
		
		GridPane g = new GridPane();
		g.add(nameL, 0, 0);
		g.add(nameField, 0, 1);
		
		g.add(symbolL, 1, 0);
		g.add(symbolField, 1, 1);
		
		g.add(rateL, 2, 0);
		g.add(rateField, 2, 1);
		
		g.add(remove, 3, 0);
		g.add(add, 3, 1);
		
		add.setOnAction(e ->addNewCurrency());
		remove.setOnAction(e -> deleteCurrency());
		
		g.setHgap(3);
		g.setVgap(3);
		 
		vB.getChildren().add(g);
		vB.setPadding(new Insets(5,5,5,5));
		return vB;
	}
	
	private void deleteCurrency(){
		Currency selectC = table.getSelectionModel().getSelectedItem();
		 if(selectC != null){
			 if(!u.getMainCurrency().equals(selectC)){
				 u.getCurrencyList().remove(selectC);
					table.refresh();
					table.setItems(getElements());
			 }else{
				 Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle(keyW.getString("delete"));
					alert.setHeaderText(keyW.getString("deleteMainCurrency"));
					alert.show();
			 }
			 
		 }
		 else{
			 Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle(keyW.getString("erorSelect"));
				alert.setHeaderText(keyW.getString("erorSelect"));
				alert.setContentText(keyW.getString("erorSelect"));
				alert.show();
		 }

		 
	}
	
	private void addNewCurrency(){
		String error = "";
		if(nameField.getText()!= null && nameField.getText().length() != 0){

			try{
				double r = Double.parseDouble(rateField.getText().replace(',','.'));

				if(r>0){
					u.addCurrencyUser(new Currency(nameField.getText(), symbolField.getText(), r));
					nameField.setText("");
					symbolField.setText("");
					rateField.setText("");
					table.refresh();
					table.setItems(getElements());
				}
				else{
					error += keyW.getString("erorNumber") + "/r/n";
				}
			}catch(NumberFormatException e){
				error += rateField.getText() + keyW.getString("erorNumber") +"/r/n";
				System.out.println(rateField.getText() + keyW.getString("erorNumber") + " /r/n");

			}
		}
		else{
			Alert a = new Alert(AlertType.WARNING);
			a.setTitle(keyW.getString("erorTitle"));
			a.setContentText(error);
			a.show();
		}
	}

	public User getU() {
		return u;
	}

	public void setU(User u) {
		this.u = u;
	}

	public Button getSave() {
		return save;
	}

	public Button getClose() {
		return close;
	}

	public TableView<Currency> getTable() {
		return table;
	}

	public void setTable(TableView<Currency> table) {
		this.table = table;
	}
	
	public ArrayList <Currency> getNewCurrencyList(){
		ArrayList <Currency> c =  new ArrayList <Currency>();
		for(int i = 0; i< getTable().getItems().size(); i++){
			c.add(getTable().getItems().get(i));
		}
		return c;
	}

	
}
