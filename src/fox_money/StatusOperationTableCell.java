/**
 * 
 */
package fox_money;

import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

/**
 * @author endem
 *
 */
public class StatusOperationTableCell extends TableCell <Operation, Status> {

//	@Override
	protected void updateItem(Status item, boolean empty) {
		super.updateItem(item, empty);
		 if (!empty ) {
//			 System.out.println(item);
			 if( item != null){
				 if(item.equals(Status.ACTUALY)){
//					 setText("+");
					 setTextFill(Color.FORESTGREEN);
					 Image img = new Image("itWasDone.png");
					 ImageView imageView = new ImageView(img);
					 imageView.setFitHeight(13);
					 imageView.setFitWidth(13);
					 setAlignment(Pos.CENTER);
					 setGraphic(imageView);
				 }
				 else if(item.equals(Status.PLAN)){
//					 setText("-");
					 setTextFill(Color.ORANGERED);
					 Image img = new Image("forPlan.png");
					 ImageView imageView = new ImageView(img);
					 imageView.setFitHeight(15);
					 imageView.setFitWidth(15);
					 setAlignment(Pos.CENTER);

					 setGraphic(imageView);
				 }			 
			 }
			 
			 
		 }
		 
		 else if (item == null){
//			 setText("null");
			 }
		 }
	
	
	
}
