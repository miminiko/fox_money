/**
 * 
 */
package fox_money.ui;

import java.time.LocalDate;

import fox_money.Account;
import fox_money.Category;
import fox_money.Operation;
import fox_money.Status;
import fox_money.SumOperationTableCell;
import fox_money.TypOperation;
import fox_money.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author endem
 *
 */
public class DialogStatistic extends Stage {

	private User user;
	private  Image foxikIcon = new Image("im_fox.jpg");

	private LangKey keyW;
	@SuppressWarnings("unused")
	private ChoiceBox<String> choice;
	@SuppressWarnings("unused")
	private  DatePicker dataBeginPicker ;
	@SuppressWarnings("unused")
	private DatePicker dateEndPicker;
	@SuppressWarnings("unused")
	private Button show;
	CategoryAxis xAxis = new CategoryAxis();
	NumberAxis yAxis = new NumberAxis();
	
	BarChart<String,Number> bc = 
            new BarChart<>(xAxis,yAxis);
	

	private TableView<Operation> table;

	
	public DialogStatistic(User user){
		this.user = user;
		this.keyW = user.getKeyUser();
		 init();
		 this.setScene( new Scene(getMyScene(), 400, 600));
	}
	
	private void init(){
		this.setTitle(keyW.getString("dialogStatistic"));
		this.getIcons().add(foxikIcon);
		 table = new TableView<Operation>(getItems());
		
		show = new Button(keyW.getString("buttonShow"));
		choice = new ChoiceBox<String>();
		dataBeginPicker = new DatePicker();
		dateEndPicker = new DatePicker(LocalDate.now());
	}
	
	private ObservableList<Operation> getItems(){
		ObservableList<Operation> list =  FXCollections.observableArrayList();
		for(Category c : user.getCategoryList()){
			double summ = 0;
			for( Account a : user.getAccountList()){
				for(Operation o : a.getOperList()){
					if(o.getIsIt().equals(Status.ACTUALY)){
						System.out.println(o.getCategory() + "   " + c.getCategoryName());

						System.out.println(o.getCategory().equals(c.getCategoryName()));
						if(o.getCategory().equals(c.getCategoryName()) && o.getTyp().equals(c.getTyp().getName())){
							if(c.getTyp().equals(TypOperation.PLUS)) summ += o.getMainSum();
							else{
								summ -= o.getMainSum();
							}
							
						}
					}
				}
			}
			list.add(new Operation(summ, user.getMainCurrency().getName(), c.getCategoryName(), c.getTyp()));
		}
		
		return list;
	}
	
	private Parent getMyScene(){
		BorderPane br = new BorderPane();
		br.setCenter(getTable());
		br.setTop(getControlPanel());
		return br;
	}
	
	@SuppressWarnings({ "unchecked" })
	private Node getTable(){
		TableColumn<Operation, String> nameCategory = new TableColumn<Operation, String>(keyW.getString("tableCategory"));
		nameCategory.setCellValueFactory(new PropertyValueFactory<Operation, String>("Category"));

		TableColumn<Operation, Double> summ = new TableColumn<Operation, Double>(keyW.getString("tableSum"));
		summ.setCellValueFactory(new PropertyValueFactory<Operation, Double>("MainSum"));
		summ.setCellFactory((param -> new SumOperationTableCell()));

		table.getColumns().addAll(nameCategory, summ);
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		return table;

	}
	
	private Node getControlPanel(){
		GridPane gP = new GridPane();
		
		return gP;
	}
	
	@SuppressWarnings({ "unchecked", "unused" })
	private Node getVizialization(){
		VBox vB = new VBox();
		 bc.getData().add(getOperationData());
		vB.getChildren().add(bc);
		return vB;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public XYChart.Series getOperationData(){
//		int month = dateEndPicker.getValue().getMonthValue() - dataBeginPicker.getValue().getMonthValue();
//		XYChart.Series [] series = new XYChart.Series [month];
		XYChart.Series series = new XYChart.Series();

		for(Category c : user.getCategoryList()){
			if(c.getTyp().equals(TypOperation.MINUS)){
				double sum = 0;
				for(Account a : user.getAccountList()){
					for(Operation o : a.getOperList() ){
						if(o.getIsIt().equals(Status.ACTUALY)){
							if(o.getCategory().equals(c.getCategoryName())){
								sum -= o.getMainSum();
							}
						}
						
					}
				}
				series.getData().add( new XYChart.Data(c.getCategoryName(), sum));
			}
			
		}
		
		return series;
	}
	
	@SuppressWarnings({ "rawtypes", "unused" })
	public void getMovingData(){
		NumberAxis xAxis = new NumberAxis();
		NumberAxis yAxis = new NumberAxis();

		LineChart<Number,Number> lineChart = 
	            new LineChart<Number,Number>(xAxis,yAxis);
		
		XYChart.Series series = new XYChart.Series();
		for(Account a : user.getAccountList()){
			for(Operation o : a.getOperList() ){
				double summ = 0;
			}
		}
	}
}
