/**
 * 
 */
package fox_money;

import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

/**
 * @author endem
 *
 */
public class TypOperationTableCell extends TableCell <Operation, String>{
	
//	@Override
	protected void updateItem(String item, boolean empty) {
		super.updateItem(item, empty);
		 if (!empty ) {
			 System.out.println(item);
			 if( item != null){
				 if(item.equals("plus")){
					 setText("+");
					 setTextFill(Color.FORESTGREEN);
					 Image img = new Image("plus.png");
					 ImageView imageView = new ImageView(img);
					 imageView.setFitHeight(13);
					 imageView.setFitWidth(13);

					 setGraphic(imageView);
				 }
				 else if(item.equals("minus")){
					 setText("-");
					 setTextFill(Color.ORANGERED);
					 Image img = new Image("minus.png");
					 ImageView imageView = new ImageView(img);
					 imageView.setFitHeight(15);
					 imageView.setFitWidth(15);

					 setGraphic(imageView);
				 }			 
			 }
			 
			 
		 }
		 
		 else if (item == null){
//			 setText("null");
			 }
		 }
	
}
