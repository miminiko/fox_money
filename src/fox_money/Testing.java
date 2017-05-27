/**
 * 
 */
package fox_money;

import java.time.LocalDate;

/**
 * @author endem
 *
 */
public class Testing {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Saver s = new Saver("dataForTextingFoxMoney.txt");
		User u = s.readData();
		if(u != null){
			System.out.println(u.toString());;
			System.out.println(u.getUserBALANCE());
			System.out.println("--------------------------------------------------------------------");
			Budget b1 = new Budget(4500, u.getMainCurrency(), "Старховка", "", LocalDate.now(), LocalDate.of(2018, 1, 21));
			u.getBudgetList().add(b1);
			System.out.println(u.getBudgetList().get(0).getSummMonth());
//			Budget b2 = new Budget(2500, u.getMainCurrency(), "Виза", LocalDate.of(2018, 3, 21));
//			u.getBudgetList().add(b2);
//			System.out.println(u.getBudgetList().get(1).getSummMonth());
		}
		else{
			System.out.println("������");
		}

	}

}
