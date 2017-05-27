/**
 * 
 */
package fox_money.ui;

import fox_money.Category;
import fox_money.TypOperation;
import javafx.scene.control.ListCell;
import javafx.scene.paint.Color;


/**
 * @author endem
 *
 */
public class ListCategoryCell extends ListCell<Category>{
	
	public void updateItem(Category item, boolean bln) {
		 super.updateItem(item, bln);
         if (!bln) {
         	if (item != null) {
				if(item.getTyp().equals(TypOperation.PLUS)){
					setTextFill(Color.LAWNGREEN);
					setText(item.getCategoryName());
				}
				else if(item.getTyp().equals(TypOperation.MINUS)){
					setTextFill(Color.ORANGERED);
					setText(item.getCategoryName());
				}
//				setText(item.getCategoryName());
			}
		}
	}
}
