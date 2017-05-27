/**
 * 
 */
package fox_money;

import java.text.DecimalFormat;

import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.paint.Color;

/**
 * @author endem
 *
 */
public class SumOperationTableCell extends TableCell <Operation, Double> {
		
	protected void updateItem(Double item, boolean empty) {
		super.updateItem(item, empty);
		 if (!empty) {
			 if(item != null){

				 DecimalFormat df = (DecimalFormat) DecimalFormat.getInstance();
				 df.applyPattern("##,###,###,###,##0.00");
				 

				 
				 if(item >= 0){
					 
					 setText(df.format(item));
	                 setTextFill(Color.LIMEGREEN);
	    			 setAlignment(Pos.BASELINE_RIGHT);

				 }
				 else if(item <= 0){
					 setText(df.format(item));
					 setStyle(" font-style: italic;");
					 setAlignment(Pos.BASELINE_RIGHT);
	                 setTextFill(Color.MEDIUMVIOLETRED);
				 }

				

             }
		}
    }
}
