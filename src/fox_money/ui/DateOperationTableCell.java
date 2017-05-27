/**
 * 
 */
package fox_money.ui;

import java.time.LocalDate;

import fox_money.Operation;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * @author endem
 *
 */
public class DateOperationTableCell extends TableCell <Operation, LocalDate> {


	
	protected void updateItem(LocalDate item, boolean empty) {
	super.updateItem(item, empty);
	
	
	setFont(new Font("Arial", 10));
	 if (!empty ) {
//		 System.out.println(item);
		 if(item != null){
			 setText(item.toString());
			 setAlignment(Pos.BASELINE_RIGHT);
//             setTextFill(Color.SLATEGRAY);
		 } else {
			 setText("no date");
	         setTextFill(Color.LIMEGREEN);

		 }
		 
         }
	
	}
}
