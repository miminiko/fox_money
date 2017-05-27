package fox_money.ui;

import fox_money.Budget;
import javafx.geometry.Pos;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableCell;


public class ProgressTableCell extends TableCell <Budget, Double> {
	
	protected void updateItem(Double item, boolean empty) {
		super.updateItem(item, empty);
		 if (!empty ) {
			 System.out.println(item);
			 if( item != null){
				 
				 ProgressBar bar = new ProgressBar();
				 bar.setProgress(item);
				 setAlignment(Pos.CENTER);
				 setGraphic(bar);
//				 
//				 if(item.equals(Status.ACTUALY)){
////					 setText("+");
//					 setTextFill(Color.FORESTGREEN);
//					 Image img = new Image("itWasDone.png");
//					 ImageView imageView = new ImageView(img);
//					 imageView.setFitHeight(13);
//					 imageView.setFitWidth(13);
//					
//				 }
//				 else if(item.equals(Status.PLAN)){
////					 setText("-");
//					 setTextFill(Color.ORANGERED);
//					 Image img = new Image("forPlan.png");
//					 ImageView imageView = new ImageView(img);
//					 imageView.setFitHeight(15);
//					 imageView.setFitWidth(15);
//					 setAlignment(Pos.CENTER);
//
//					 setGraphic(imageView);
//				 }			 
			 }
			 
			 
		 }
		 
		 else if (item == null){
//			 setText("null");
			 }
		 }
	

}
