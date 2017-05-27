/**
 * 
 */
package fox_money;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * @author endem
 *
 */
public class AccountListCell extends ListCell<Account> {

////	@Override
//    public void updateItem(Account item, boolean empty) {
//        super.updateItem(item, empty);
//        if (empty) {
//            setGraphic(null);
//            setText(null);
//        } else {
////            Image fxImage = getFileIcon(item);
////            ImageView imageView = new ImageView(fxImage);
////            setGraphic(imageView);
//            setText(item.toString());
//        }
        
        protected void updateItem(Account item, boolean bln) {
            super.updateItem(item, bln);
            if (!bln) {
            	if (item != null) {
                	Text name = new Text(item.getNameAccount());
                	name.setFont(new Font(14));
                	name.setTextAlignment(TextAlignment.JUSTIFY);
                	name.setFill(Color.PURPLE);
                	NumberFormat  df = new DecimalFormat("##,###,###,###,##0.00");
                	Text money = new Text(df.format(item.getBalanceAccount()) + " " + item.getcAccount().getName());
                	money.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
                	money.setFill(Color.GREENYELLOW);
                	VBox vBox = new VBox(name, money);
                    HBox hBox = new HBox(new Label("[Graphic]"), vBox);
                    hBox.setSpacing(10);
                    setGraphic(hBox);
                }
            }
            
        }


}
	

