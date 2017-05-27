/**
 * 
 */
package fox_money.ui;


import java.io.File;
import java.text.DecimalFormat;
import java.time.LocalDate;

import fox_money.Account;
import fox_money.AccountListCell;
import fox_money.Currency;
import fox_money.Operation;
import fox_money.Saver;
import fox_money.Status;
import fox_money.StatusOperationTableCell;
import fox_money.SumOperationTableCell;
import fox_money.TypOperation;
import fox_money.User;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
//import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @author endem
 *
 */
public class MainAp extends Application {

	private static LangKey keyW;
	
	private static Stage s;
	private static Image foxikIcon = new Image("im_fox.jpg");
	private static Image imgDelete = new Image("deleting.png");
	
	private static DecimalFormat df = new DecimalFormat("##,###,###,###,##0.00");
	private static boolean needNewUser = false;
	private static 	GridPane gP = new GridPane();

	
//	private static ArrayList <String> cur  = new  ArrayList <String>();
	private static User user;
	ChoiceBox<Currency> cB = new ChoiceBox<Currency>();
	private static Text faktE =  new Text();
	private static Text faktI =  new Text();
	private static Text planE =  new Text();
	private static Text planI =  new Text();
	private static Text inTotalE = new Text();
	private static Text inTotalI = new Text();
	private static Text inTotal = new Text();
	private static PieChart pie;

	private static Text hi;
	private static Text nUser;
	private static Text bUser;
	private static Text actualText;
	private static Text planText;
	private static Text expensesText;
	private static Text incomeText;
	private static Text totalSumm;
	
	private static Menu fM, setting;
	private static MenuItem newI, setI, saveData, oI, addCurrency, addCategory, language;

	private static Tooltip tooltipAdd, tooltipDelete, tooltipCleanSelectAccount, tooltipAddO, tooltipEditO, tooltipTr, tooltipRemove;

	private static Text sumSelectedOperationText;
	public static TextField sumSelectedOperation = new TextField();

	
	public static TextField balanceUserText = new TextField();
	public static ListView<Account> list = new ListView<Account>();
	
	public static TableView<Operation> tableOperation;
	public static TableColumn<Operation, Status> statusO;
	public static TableColumn<Operation, String> typO;
	public static TableColumn<Operation, LocalDate> data ;
	public static TableColumn<Operation, Double> sumMoney;
	public static TableColumn<Operation, String> categoryOperation;
	public static TableColumn<Operation, String> subCategoryOperation;
	public static TableColumn<Operation, String> contrAgentOperation;
	public static TableColumn<Operation, String> note;
	
	//////////////////////

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Saver saver = new Saver("dataForTextingFoxMoney.txt");
		user = saver.readData();

		if(user != null){
			
//			cur = user.getCurrencyStringList();
		}
		else{
			System.out.println("Юзер не найден");
			needNewUser = true;
			user = new User("default", "USD");
		}

		launch(args);

	}
	

	@Override
	public void start(Stage stage) throws Exception {
		
		if(needNewUser == true) newUserCreate();
		System.out.println(user.getNameUser());
		s= stage;
		s.setTitle("FOX_MONEY");
		s.getIcons().add(foxikIcon);
		s.setScene(createScene());
		
		s.setOnCloseRequest(event -> saveDataUser()); 

		s.show();
	}
	
	@Override
	public void init() throws Exception{
//		System.out.println(Locale.getDefault());
//		Locale.setDefault(Locale.US);
		
//		keyW = new LangKey(Locale.getDefault());
		System.out.println("Информация о локальности");
		System.out.println(user.getKeyUser().getLocale());
		keyW = user.getKeyUser();
		tableOperation = new TableView<Operation>();
		statusO = new TableColumn<Operation, Status>(keyW.getString("tableStatus"));
		typO = new TableColumn<Operation, String>(keyW.getString("tableStatus"));
		data = new TableColumn<Operation, LocalDate>(keyW.getString("tableDate"));
		sumMoney = new TableColumn<Operation, Double>(keyW.getString("tableSum"));
		categoryOperation = new TableColumn<Operation, String>(keyW.getString("tableCategory"));
		subCategoryOperation = new TableColumn<Operation, String>(keyW.getString("tableSubCategoty"));
		contrAgentOperation = new TableColumn<Operation, String>(keyW.getString("tableContragent"));
		note = new TableColumn<Operation, String>(keyW.getString("tableNote"));
		sumSelectedOperationText = new Text("    "+keyW.getString("tableForSelectSum")+":  ");
		
	}
	
	public Scene createScene(){
		Scene s = new Scene(createPanels(), 800, 500);
		return s;
	}

	public Parent createPanels(){
		BorderPane r = new BorderPane();
		VBox v = new VBox();
		v.getChildren().addAll(topPanel(),downPanel());
		r.setTop(doMenuBar());
		r.setCenter(v);
		return r;
	}
	
	public Node doMenuBar(){
		MenuBar m = new MenuBar();
		
		fM = new Menu(keyW.getString("menuFile"));
		newI = new MenuItem(keyW.getString("menuNewUser"));
		
		newI.setOnAction(event -> newUserCreate());
		setI = new MenuItem(keyW.getString("menuSetUserDetails"));
		setI.setOnAction(e -> setUserDetails());
		
		oI = new MenuItem(keyW.getString("menuOpenFile"));
		oI.setOnAction(e ->{
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Open Resource File");
			File file = fileChooser.showOpenDialog(s);
            if (file != null) {
            	Saver sav = new Saver(file.getName());
            	sav.readData();
            }

		});
		
		saveData = new MenuItem(keyW.getString("menuSave"));
		saveData.setOnAction(evetn -> saveDataUser());
		
		fM.getItems().addAll(newI,setI, saveData, oI);
		
		setting = new Menu(keyW.getString("menuSetting"));
		
		ImageView imgCur = new ImageView(new Image("currency.png"));
		ImageView imgCat = new ImageView(new Image("category.png"));
		
		addCategory = new MenuItem(keyW.getString("menuCategory"));
		imgCat.setFitHeight(15);
		imgCat.setFitWidth(15);
		addCategory.setGraphic(imgCat);
		addCategory.setOnAction(event -> addingCategoryWindows());
		
		addCurrency = new MenuItem(keyW.getString("menuCurrency"));
		imgCur.setFitHeight(15);
		imgCur.setFitWidth(15);
		addCurrency.setGraphic(imgCur);
		addCurrency.setOnAction(e -> addingCurrencyWindows());
		
		language = new MenuItem(keyW.getString("menuLanguage"));
		ImageView imgEng = new ImageView(new Image("eng.jpg"));
		language.setGraphic(imgEng);
		language.setOnAction(e -> setLanguage());
		
		setting.getItems().addAll(addCategory, addCurrency, language);
						
		m.getMenus().addAll(fM, setting);
		return m;
		
	}
	
	public Node topPanel(){
		HBox hB = new HBox();
		
		gP.setHgap(10);
		gP.setVgap(10);
		
		HBox miniHb = new HBox();
		
		hi = new Text( keyW.getString("mainHi")+ ", ");
		hi.setFont(Font.font(20));
		
		nUser = new Text(user.getNameUser());
		nUser.setFont(Font.font(20));
		nUser.setFill(Color.PURPLE);

		miniHb.getChildren().addAll(hi, nUser);
		gP.add(miniHb, 0, 0, 2, 1);

		///////////////////////////////////////
		bUser = new Text(keyW.getString("mainBalance")+": ");
		bUser.setFont(Font.font(20));
		gP.add(bUser, 0, 1);


		balanceUserText.setText(df.format(user.getUserBALANCE()));
		balanceUserText.setEditable(false);
		gP.add(balanceUserText, 1, 1, 2, 1);

		cB = new ChoiceBox<Currency>(
				FXCollections.observableArrayList(user.getCurrencyList()));
		cB.setValue(user.getMainCurrency());
		cB.setOnAction(e ->{
			balanceUserText.setText(df.format(user.getUserBALANCE()/cB.getValue().getRate()));
		});
		gP.add(cB, 3, 1);
		
		////////////////////////////////////
		actualText = new Text(keyW.getString("mainActual"));
		gP.add(actualText, 1, 3);
		
		planText = new Text(keyW.getString("mainPlanning"));
		gP.add(planText, 2, 3);
		
		expensesText = new Text(keyW.getString("mainExpenses"));
		gP.add(expensesText, 0, 4);
		
		faktE.setText(df.format((user.getCreditUser())));
		gP.add(faktE, 1, 4);
		
		faktI.setText(df.format((user.getDebitUser())));
		gP.add(faktI, 1, 5);
		
		incomeText = new Text(keyW.getString("mainIncome"));
		gP.add(incomeText, 0, 5);
		
		planE = new Text(df.format((user.getPlaningCreditUser())));
		gP.add(planE, 2, 4);
		
		planI = new Text(df.format((user.getPlaningDebitUser())));
		gP.add(planI, 2, 5);
		
		totalSumm = new Text(keyW.getString("mainInTotal"));
		gP.add(totalSumm, 3, 3);
		
		inTotalE = new Text(df.format(user.getPlaningCreditUser() + user.getCreditUser()));
		gP.add(inTotalE, 3, 4);

		inTotalI = new Text(df.format(user.getPlaningDebitUser() + user.getDebitUser()));
		gP.add(inTotalI, 3, 5);

		inTotal = new Text(df.format(user.getPlaningDebitUser() + user.getDebitUser() - 
				(user.getPlaningCreditUser() + user.getCreditUser())));

		inTotal.setFill(Color.PURPLE);
		gP.add(inTotal, 3, 6);
		
		//////------------------pie---------
		pie = new PieChart(getPieDataByUserAccount());
//		pie.setTitle("Expenses");
		
		pie.setLegendSide(Side.RIGHT);
		pie.setClockwise(false);
		pie.setLabelsVisible(false);

		
		Label caption = new Label("");
		caption.setTextFill(Color.DARKORANGE);
		caption.setStyle("-fx-font: 24 arial;");
		
		for (PieChart.Data data : pie.getData()) {
		    data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
		    		
		        new EventHandler<MouseEvent>() {
		    	
		            @Override public void handle(MouseEvent e) {
//		            	System.out.println("TUT");
		                caption.setTranslateX(e.getSceneX());
		                caption.setTranslateY(e.getSceneY());
		                caption.setText(String.valueOf(data.getPieValue()) + "%");
		             }
		        });
		}

		pie.setPadding(new Insets(10,10,10,10));
//		pie.setMaxHeight(150);
//		pie.setMaxWidth(350);'
		
		VBox vB = new VBox();
		
		ImageView budgetView = new ImageView(new Image("budget.png"));
		ImageView statisticView = new ImageView(new Image("statistic.png"));
		ImageView imgCur = new ImageView(new Image("currency.png"));
		ImageView imgCat = new ImageView(new Image("category.png"));
		
		Button budgetButton = new Button();
		budgetButton.setTooltip(new Tooltip(keyW.getString("dialogForBudget")));
		budgetView.setFitHeight(20);
		budgetView.setFitWidth(20);
		budgetButton.setGraphic(budgetView);
		budgetButton.setOnAction(e -> getBudgetDialog());
		budgetButton.setPrefSize(30, 30);
		
		Button statisticButton = new Button();
		statisticButton.setTooltip(new Tooltip(keyW.getString("dialogStatistic")));
		statisticView.setFitHeight(20);
		statisticView.setFitWidth(20);
		statisticButton.setGraphic(statisticView);
		statisticButton.setOnAction(e -> getStatisticDialog());
		statisticButton.setPrefSize(30, 30);
		
		Button categoryButton = new Button();
		categoryButton.setTooltip(new Tooltip(keyW.getString("menuCategory")));
		imgCat.setFitHeight(20);
		imgCat.setFitWidth(20);
		categoryButton.setGraphic(imgCat);
		categoryButton.setOnAction(e -> addingCategoryWindows());
		categoryButton.setPrefSize(35, 30);

		Button currencyButton = new Button();
		currencyButton.setTooltip(new Tooltip(keyW.getString("menuCurrency")));
		imgCur.setFitHeight(20);
		imgCur.setFitWidth(20);
		currencyButton.setGraphic(imgCur);
		currencyButton.setOnAction(e -> addingCurrencyWindows());
		currencyButton.setPrefSize(35, 30);
		
		vB.setSpacing(10);		
		vB.getChildren().addAll(budgetButton, statisticButton, categoryButton, currencyButton);
		
		hB.getChildren().addAll(gP , pie, vB);
		hB.setSpacing(5);
		hB.setPadding(new Insets(25,35,35,35));
//		hB.setAlignment(Pos.TOP_LEFT);
		hB.setMaxHeight(300);
		return hB;
	}
	
	public static ObservableList<Data> getPieDataByUserAccount(){
		ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList();
		for(int i = 0; i< user.getAccountList().size(); i++){
			pieChartData.add(
					new PieChart.Data(user.getAccountList().get(i).getNameAccount(),
							user.getAccountList().get(i).getBalanceMainCurrencyAccount()));
		}
		
		return pieChartData;
	}
	
	public ObservableList<Data> getPieDataByUserOperation(){
		ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList();
		for(int i = 0; i< user.getAccountList().size(); i++){
			for(int j = 0; j<user.getAccountList().get(i).getOperList().size(); j++){
				if(user.getAccountList().get(i).getOperList().get(j).getTypOperation().equals(TypOperation.MINUS)){
					pieChartData.add(
							new PieChart.Data(user.getAccountList().get(i).getOperList().get(j).getCategory(),
									(user.getAccountList().get(i).getOperList().get(j).getMainSum() * user.getAccountList().get(i).getcAccount().getRate())
									));
				}
			}
		}
		return pieChartData;
	}
	
	public Node downPanel(){
		SplitPane sP = new SplitPane();
		sP.setOrientation(Orientation.HORIZONTAL);
		sP.getItems().addAll( doAccountTable(), doOperationTable());
		return sP;
	}
	
	
	
	public Node doAccountTable(){
		VBox vB = new VBox();
		
		ToolBar tB = new ToolBar();
		Button newAccoutnB = new Button();
		tooltipAdd = new Tooltip(keyW.getString("toolAccountAdd"));
		newAccoutnB.setTooltip(tooltipAdd);
		Image imgAdding = new Image("adding.png");
		 ImageView imageView = new ImageView(imgAdding);
		 imageView.setFitHeight(20);
		 imageView.setFitWidth(20);		
		newAccoutnB.setGraphic(imageView);
		newAccoutnB.setOnAction(event -> dialogNewAccount());
		
		Button deleteAccountB = new Button();
		tooltipDelete = new Tooltip(keyW.getString("toolAccountRemove"));
		deleteAccountB.setTooltip(tooltipDelete);
		ImageView iDelete = new ImageView(imgDelete);
		iDelete.setFitHeight(20);
		iDelete.setFitWidth(20);
		deleteAccountB.setGraphic(iDelete);
		deleteAccountB.setOnAction(event -> deleteAccount());
		
		Button cleanSelectAccount = new Button();
		tooltipCleanSelectAccount = new Tooltip(keyW.getString("toolAccountClear"));
		cleanSelectAccount.setTooltip(tooltipCleanSelectAccount);
		ImageView cleanSelect = new ImageView(new Image("clean.png"));
		cleanSelect.setFitHeight(20);
		cleanSelect.setFitWidth(20);
		cleanSelectAccount.setGraphic(cleanSelect);
		cleanSelectAccount.setOnAction(event -> {
			list.getSelectionModel().clearSelection();
			inTotalE.setText(df.format(user.getPlaningCreditUser() + user.getCreditUser()));
			inTotalI.setText(df.format(user.getPlaningDebitUser() + user.getDebitUser()));
			faktE.setText(df.format((user.getCreditUser())));
			faktI.setText(df.format((user.getDebitUser())));
			inTotal.setText(df.format(user.getPlaningDebitUser() + user.getDebitUser() - 
					(user.getPlaningCreditUser() + user.getCreditUser())));
		});
		
		
		tB.getItems().addAll(newAccoutnB, deleteAccountB, cleanSelectAccount);
		tB.setPadding(new Insets(3));
		
		
		list.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		list.setCellFactory((param -> new AccountListCell()));
		list.setItems(getInitData());

		list.setOnMouseClicked(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent mouseEvent) {
		        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
		            if(mouseEvent.getClickCount() == 1){
		            	showAccountOperation(list);
		            }
		            else if(mouseEvent.getClickCount() == 2){
		            	dialogSetAccount();

		            }
		        }
		    }
		});
		
		vB.getChildren().addAll(tB, list);
		vB.setMaxWidth(250);
		return vB;
	}
	

	
	
	@SuppressWarnings("unchecked")
	public Node doOperationTable(){
		VBox vB = new VBox();
		
		ToolBar tB = new ToolBar();
		Button newOperB = new Button();//"Add operation"
		ImageView addImg = new ImageView("plusOp.png");
		addImg.setFitHeight(20);
		addImg.setFitWidth(20);
		newOperB.setGraphic(addImg);
		tooltipAddO = new Tooltip(keyW.getString("toolOperAdd"));///
		newOperB.setTooltip(tooltipAddO);
		
		newOperB.setOnAction(event -> addNewOperation());
		
		Button editOperB = new Button();//"Edit"
		editOperB.setOnAction(e -> setOneOperation());
		ImageView editImg = new ImageView("editing.png");
		editImg.setFitHeight(20);
		editImg.setFitWidth(20);
		editOperB.setGraphic(editImg);
		tooltipEditO = new Tooltip(keyW.getString("toolOperEdit"));
		editOperB.setTooltip(tooltipEditO);
		
		Button tranfOperB = new Button();//"Tranfer money"
		Image tranImg = new Image("transferImage.png");
		ImageView tI = new ImageView(tranImg);
		tI.setFitHeight(20);
		tI.setFitWidth(20);
		tranfOperB.setGraphic(tI);
		tooltipTr = new Tooltip(keyW.getString("toolOperTransfer"));
		tranfOperB.setTooltip(tooltipTr);
		tranfOperB.setOnAction(event -> transfer());
		
		Button deleteOperB = new Button();//"Delete"
		Image removeImg = new Image("remove.png");
		ImageView removeI = new ImageView(removeImg);
		removeI.setFitHeight(20);
		removeI.setFitWidth(20);
		deleteOperB.setGraphic(removeI);
		tooltipRemove = new Tooltip(keyW.getString("toolOperRemove"));
		deleteOperB.setTooltip(tooltipRemove);
		
		deleteOperB.setOnAction(event -> deleteOperation());
		
		sumSelectedOperation.setEditable(false);
		sumSelectedOperation.setAlignment(Pos.TOP_RIGHT);
		
		tB.getItems().addAll(newOperB, editOperB, tranfOperB, deleteOperB, sumSelectedOperationText, sumSelectedOperation);
		tB.setPadding(new Insets(3));
		
		statusO.setCellValueFactory(new PropertyValueFactory<Operation, Status>("IsIt"));
		statusO.setMinWidth(45);
		statusO.setMaxWidth(55);
		statusO.setCellFactory(param -> new StatusOperationTableCell());
		
		
		data.setCellValueFactory(new PropertyValueFactory<Operation, LocalDate>("Date"));
		data.setCellFactory(param -> new DateOperationTableCell());
		
		sumMoney.setCellValueFactory(new PropertyValueFactory<Operation, Double>("MainSum"));
		sumMoney.setCellFactory((param -> new SumOperationTableCell()));
		
		categoryOperation.setCellValueFactory(new PropertyValueFactory<Operation, String>("Category"));
		subCategoryOperation.setCellValueFactory(new PropertyValueFactory<Operation, String>("Subcategory"));
		
		contrAgentOperation.setCellValueFactory(new PropertyValueFactory<Operation, String>("Contragent"));
		note.setCellValueFactory(new PropertyValueFactory<Operation, String>("Note"));
		
        tableOperation.getColumns().addAll(
        		statusO, 
//        		typO, 
        		data, sumMoney, categoryOperation, subCategoryOperation, contrAgentOperation, note);
        
        tableOperation.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableOperation.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
       
        
        tableOperation.setOnMouseClicked(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent mouseEvent) {
		        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
//		            if(mouseEvent.getClickCount() == 1){
//		            	showAccountOperation(list);
//		            }
		            if(mouseEvent.getClickCount() == 2){
		            	setOneOperation();;

		            }
		            double sum = 0;
		            for(Operation o : tableOperation.getSelectionModel().getSelectedItems()){
		            	sum += o.getMainSum();
		            }
		            sumSelectedOperation.setText(df.format(sum));
		        }
		    }
		});
        tableOperation.setMinHeight(50);
        
        vB.getChildren().addAll(tB, tableOperation);
        vB.setMinHeight(200);
		return vB;

	}
	
	
	///////////////////////////////////////////////////////////////
	private static ObservableList<Account> getInitData() {
		ObservableList<Account> dataForListAccount = FXCollections.observableArrayList(user.getAccountList());
		return dataForListAccount;
	}
	
	public void saveDataUser(){
		Saver sav = new Saver("dataForTextingFoxMoney.txt");
//		System.out.println(user.forSave());
		sav.getFileName();
		sav.saveData(user);
		System.out.println("Save");
	}
	
	 public void showAccountOperation(ListView< Account>list){
			ObservableList<Operation> dataOperation = FXCollections.observableArrayList(list.getSelectionModel().getSelectedItem().getOperList());
			tableOperation.refresh(); 
			tableOperation.setItems(dataOperation);
			 faktE.setText(df.format(list.getSelectionModel().getSelectedItem().getCreditAccount()));
			 faktI.setText(df.format(list.getSelectionModel().getSelectedItem().getDebitAccount()));
			 planE.setText(df.format((list.getSelectionModel().getSelectedItem().getPlaninCreditAccount())));
			 planI.setText(df.format((list.getSelectionModel().getSelectedItem().getPlaninDebitAccount())));
				inTotalE.setText(df.format(list.getSelectionModel().getSelectedItem().getPlaninCreditAccount() + list.getSelectionModel().getSelectedItem().getCreditAccount()));
				inTotalI.setText(df.format(list.getSelectionModel().getSelectedItem().getPlaninDebitAccount() + list.getSelectionModel().getSelectedItem().getDebitAccount()));
				inTotal.setText(df.format(list.getSelectionModel().getSelectedItem().getPlaninDebitAccount() + list.getSelectionModel().getSelectedItem().getDebitAccount()-
						(list.getSelectionModel().getSelectedItem().getPlaninCreditAccount() + list.getSelectionModel().getSelectedItem().getCreditAccount())
						));
				sumSelectedOperation.setText(" ");
				
	 }

	 
	 /////////////////////////////////////new user ////////////////////////////////////////////////////////
	 public static void newUserCreate(){
			DialogNewUser newUser = new DialogNewUser();
			newUser.initModality(Modality.APPLICATION_MODAL);

			newUser.getSaveButton().setOnAction(e -> {
				newUser.addNewUser();
				if(newUser.isHaveNewUser()){
					user = newUser.getU();
					System.out.println("new user is");
					System.out.println(newUser.getU().toString());
				}
				newUser.close();
			});
			newUser.showAndWait();
			
		}
	 
	 public void setUserDetails(){
		 DialogNewUser newUser = new DialogNewUser(user);
			newUser.initModality(Modality.APPLICATION_MODAL);

			newUser.getSaveButton().setOnAction(e -> {
				newUser.addNewUser();
				if(newUser.isHaveNewUser()){
					user.setNameUser(newUser.getU().getNameUser()); 
					user.setMainCurrency(newUser.getU().getMainCurrency());
					nUser.setText(user.getNameUser());
					refreshCurrency();
					System.out.println("new user detials is");
					System.out.println(newUser.getU().toString());
				}
				newUser.close();
			});
			newUser.showAndWait();
	 }
	 
	 //////////////////////////////////____account setting____///////////////////////////////////////
	 public void dialogNewAccount(){
		 
		 AccountDialog dialogAccountStage = new AccountDialog(user);
			dialogAccountStage.initModality(Modality.APPLICATION_MODAL);

		 dialogAccountStage.getClose().setOnAction(e -> dialogAccountStage.close());
		 dialogAccountStage.getSave().setOnAction(e -> {
			 dialogAccountStage.addNewAccount();
			 if(dialogAccountStage.isHaveNewAccount()){
				 user = dialogAccountStage.getU();
				 refreshAll();
				 dialogAccountStage.setHaveNewAccount(false);
				 dialogAccountStage.close();
			 }
			 
		 });
		 dialogAccountStage.showAndWait();
	 }

	 
	 public void dialogSetAccount(){
		 AccountDialog dialogAccountStage = new AccountDialog(user, list.getSelectionModel().getSelectedItem());
		dialogAccountStage.initModality(Modality.APPLICATION_MODAL);
		
		 dialogAccountStage.getClose().setOnAction(e -> dialogAccountStage.close());
		 dialogAccountStage.getSave().setOnAction(e -> {
			 dialogAccountStage.setAccount();
			 if(dialogAccountStage.isHaveNewAccount()){
				 user = dialogAccountStage.getU();
				 refreshAll();
				 dialogAccountStage.setHaveNewAccount(false);
				 dialogAccountStage.close();
			 }
			 
		 });
		 dialogAccountStage.showAndWait();
		 
	 }
	 
	 /////////////////////////////////////////////////////////////
	 public void deleteAccount(){
		 Account deletAccount = list.getSelectionModel().getSelectedItem();
		 if(deletAccount == null ){
			 Alert alert = new Alert(AlertType.WARNING);
				alert.setContentText(keyW.getString("erorSelect"));
				alert.setTitle(keyW.getString("erorSelect"));
				alert.setHeaderText(keyW.getString("erorSelect"));
				alert.showAndWait();
		 }
		 else{
			 Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle(keyW.getString("delete"));
				alert.setHeaderText(keyW.getString("deleteQuestion"));
				alert.setGraphic(new Label(deletAccount.toString()));
				alert.showAndWait()
				.filter(response -> response == ButtonType.OK)
				.ifPresent(response -> {
					list.getItems().removeAll(deletAccount);
					user.deleteAccount((Account)deletAccount);
					list.getSelectionModel().clearSelection();
					refreshAll();
//					list.getSelectionModel().clearSelection();
			 });
			
		 }
	 }
	 
	 
	 ///////////////////////////____operation_setting__//////////////////
	 @SuppressWarnings("static-access")
	public static void addNewOperation(){

		 DialogOperation dialogOperationStage = new DialogOperation(user, list.getSelectionModel().getSelectedItem());
		 dialogOperationStage.initModality(Modality.WINDOW_MODAL);
		 dialogOperationStage.getClose().setOnAction(e-> dialogOperationStage.close());
		 dialogOperationStage.getSave().setOnAction(e -> {
			 dialogOperationStage.addNewOperationToAccount();
			 if(dialogOperationStage.isHaveNewAccount()){
				 user= dialogOperationStage.getUser();
				 refreshAll();
				 dialogOperationStage.setHaveNewAccount(false);
				 dialogOperationStage.close();
			 }
		 });
			dialogOperationStage.showAndWait();
	 }
	 
	 @SuppressWarnings("static-access")
	public static void setOneOperation(){

		 if(list.getSelectionModel().getSelectedItem() != null && tableOperation.getSelectionModel().getSelectedItem() != null){
			 DialogOperation setOperationD = new DialogOperation(
					 user, 
					 tableOperation.getSelectionModel().getSelectedItem(), 
					 list.getSelectionModel().getSelectedItem());
			 setOperationD.initModality(Modality.WINDOW_MODAL);
			 setOperationD.getClose().setOnAction(e-> setOperationD.close());
			 setOperationD.getSave().setOnAction(e -> {
				 setOperationD.setOneOperationInAccount();;
				 if(setOperationD.isHaveNewAccount()){
					 user= setOperationD.getUser();
					 refreshAll();
					 setOperationD.setHaveNewAccount(false);
					 setOperationD.close();
				 }
				 
				 
			 });
			 setOperationD.show();
		 }
		 else{
			 Alert alert = new Alert(AlertType.WARNING);
				alert.setContentText(keyW.getString("erorTitle"));
				alert.setTitle(keyW.getString("erorSelect"));
				alert.setHeaderText(keyW.getString("erorSelect"));
				alert.showAndWait();
		 }

		 
	 }

	 
	 private static void deleteOperation(){
		 if(list.getSelectionModel().getSelectedItem() != null && tableOperation.getSelectionModel().getSelectedItem() != null){
			 
			 Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle(keyW.getString("delete"));
				alert.setHeaderText(keyW.getString("deleteQuestion"));
				alert.setGraphic(new TextArea(tableOperation.getSelectionModel().getSelectedItem().toString()));
				alert.showAndWait()
				.filter(response -> response == ButtonType.OK)
				.ifPresent(response -> {
					 user.deleteOperation(list.getSelectionModel().getSelectedItem(), tableOperation.getSelectionModel().getSelectedItem());
					tableOperation.getItems().removeAll(tableOperation.getSelectionModel().getSelectedItem());
					tableOperation.getSelectionModel().clearSelection();
					 refreshAll();

			 });
				
		 }
		 else{
			 Alert alert = new Alert(AlertType.WARNING);
				alert.setContentText(keyW.getString("erorTitle"));
				alert.setTitle(keyW.getString("erorTitle"));
				alert.setHeaderText(keyW.getString("erorSelect"));
				alert.showAndWait();
		 }
	 }
	 
	 
	 //////////////////////____transfer______/////////////////////////////////

	 public static void transfer(){
		 DialogTransfer dialogTranser = new DialogTransfer(user);
		 dialogTranser.initModality(Modality.WINDOW_MODAL);
		 dialogTranser.getClose().setOnAction(e -> dialogTranser.close());
		 dialogTranser.getSave().setOnAction(e -> {
//			 System.out.println("tut");
			 dialogTranser.addTranferOperation();
			 if(dialogTranser.isHaveNewAccount()){
				 user = dialogTranser.getUser();
				 refreshAll();
				 dialogTranser.setHaveNewAccount(false);
				 dialogTranser.close();
			 }
		 });
		 
		 dialogTranser.showAndWait();
	 }
	
	 

	 
	 
	 public static void addingCategoryWindows(){
		 
		 DialogForCategory addingCategory = new DialogForCategory(user);
		 addingCategory.initModality(Modality.APPLICATION_MODAL);
		 addingCategory.getSaveCategory().setOnAction(e -> {
			 user.setCategoryList(addingCategory.getCategory());
			 addingCategory.close();
		 });
		 addingCategory.showAndWait();
	 }
	 
	public void addingCurrencyWindows(){
		 DialogCurrency dialogCurrency = new DialogCurrency(user);
		 dialogCurrency.initModality(Modality.APPLICATION_MODAL);
		 dialogCurrency.getClose().setOnAction(e -> dialogCurrency.close());
		 dialogCurrency.getSave().setOnAction(e -> {
			 user.setCurrencyList(dialogCurrency.getNewCurrencyList());
			 refreshCurrency();
			 dialogCurrency.close();
		 });
		 dialogCurrency.showAndWait();
	 }
	 
	public void refreshCurrency(){
		 cB = new ChoiceBox<Currency>(
					FXCollections.observableArrayList(user.getCurrencyList()));
			cB.setValue(user.getMainCurrency());
			cB.setOnAction(e ->{
				balanceUserText.setText(df.format(user.getUserBALANCE()/cB.getValue().getRate()));
			});
			gP.add(cB, 3, 1);
	 }
	 
	 public void getBudgetDialog(){
		 DialogForBudget dialogBudget = new DialogForBudget(user);
		 dialogBudget.initModality(Modality.APPLICATION_MODAL);
		 dialogBudget.getSave().setOnAction(e ->{
			 user.setBudgetList(dialogBudget.getUser().getBudgetList());
			 dialogBudget.close();
		 });
		 dialogBudget.showAndWait();
	 }
	 
	 public void  getStatisticDialog(){
		 DialogStatistic dialogStatistic = new DialogStatistic(user);
		 dialogStatistic.initModality(Modality.APPLICATION_MODAL);
		 dialogStatistic.showAndWait();
	 }
	 
	 public void setLanguage(){
		 DialogSetLanguage languageDialog = new DialogSetLanguage(user);
		 languageDialog.initModality(Modality.APPLICATION_MODAL);
		 languageDialog.getClose().setOnAction(e -> languageDialog.close());
		 languageDialog.getSave().setOnAction(e -> {
			 keyW = new LangKey(languageDialog.getKeyW().getLocale());
			 actualLanguage();
			 user.setKeyUser(keyW.getLocale());
			 languageDialog.close();
		 });
		 		 languageDialog.showAndWait();
	 }
	 
	 public static void actualLanguage(){
		 System.out.println(user.getKeyUser().getLocale());
		 System.out.println( keyW.getString("mainHi"));

		 hi.setText( keyW.getString("mainHi")+ ", ");
		 bUser.setText(keyW.getString("mainBalance")+": ");
		 actualText.setText(keyW.getString("mainActual"));
		 planText.setText(keyW.getString("mainPlanning"));
		 expensesText.setText(keyW.getString("mainExpenses"));
		 incomeText.setText(keyW.getString("mainIncome"));
		 totalSumm.setText(keyW.getString("mainInTotal"));
		 
		sumSelectedOperationText.setText("    "+keyW.getString("tableForSelectSum")+":  ");

		 statusO.setText(keyW.getString("tableStatus"));
		 typO.setText(keyW.getString("tableStatus"));
		 data.setText(keyW.getString("tableDate"));
		 sumMoney.setText(keyW.getString("tableSum"));
		 categoryOperation.setText(keyW.getString("tableCategory"));
		 subCategoryOperation.setText(keyW.getString("tableSubCategoty"));
		 contrAgentOperation.setText(keyW.getString("tableContragent"));
		 note.setText(keyW.getString("tableNote"));
		 
		 fM.setText(keyW.getString("menuFile"));
		 newI.setText(keyW.getString("menuNewUser"));
		 setI.setText(keyW.getString("menuSetUserDetails"));
		 oI.setText(keyW.getString("menuOpenFile"));
		 saveData.setText(keyW.getString("menuSave"));
		 setting.setText(keyW.getString("menuSetting"));
		 addCategory.setText(keyW.getString("menuCategory"));
		 addCurrency.setText(keyW.getString("menuCurrency"));
		 language.setText(keyW.getString("menuLanguage"));
		 
		 tooltipAdd.setText(keyW.getString("toolAccountAdd"));
		 tooltipDelete.setText(keyW.getString("toolAccountRemove"));
		 tooltipCleanSelectAccount.setText(keyW.getString("toolAccountClear"));
		 tooltipAddO.setText(keyW.getString("toolOperAdd"));
		 tooltipEditO.setText(keyW.getString("toolOperEdit"));
		 tooltipTr.setText(keyW.getString("toolOperTransfer"));
		 tooltipRemove.setText(keyW.getString("toolOperRemove"));
	 }
	 
	 
	 public static void refreshAll(){
		 balanceUserText.setText(df.format(user.getUserBALANCE()));
		 faktE.setText(df.format((user.getCreditUser())));
		 faktI.setText(df.format((user.getCreditUser())));
		 planE.setText(df.format((user.getPlaningCreditUser())));
		 planI.setText(df.format((user.getPlaningDebitUser())));
		 if(list.getSelectionModel().getSelectedItem()!= null){
			 inTotalE.setText(df.format(list.getSelectionModel().getSelectedItem().getPlaninCreditAccount() + list.getSelectionModel().getSelectedItem().getCreditAccount()));
				inTotalI.setText(df.format(list.getSelectionModel().getSelectedItem().getPlaninDebitAccount() + list.getSelectionModel().getSelectedItem().getDebitAccount()));
				inTotal.setText(df.format(list.getSelectionModel().getSelectedItem().getPlaninDebitAccount() + list.getSelectionModel().getSelectedItem().getDebitAccount()-
						(list.getSelectionModel().getSelectedItem().getPlaninCreditAccount() + list.getSelectionModel().getSelectedItem().getCreditAccount())
						));
		 }
			
		 list.refresh();
		 list.setItems(getInitData());
		 tableOperation.refresh();

			 if(list.getSelectionModel().getSelectedItem() != null){
					tableOperation.setItems(FXCollections.observableArrayList(list.getSelectionModel().getSelectedItem().getOperList()));

			 }
		 pie.setData(getPieDataByUserAccount());		 
	 }
	 
	 
	
}
