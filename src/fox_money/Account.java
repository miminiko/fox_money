/**
 * 
 */
package fox_money;

import java.text.DecimalFormat;
import java.util.ArrayList;


/**
 * @author endem
 *
 */
public class Account implements ISavable {
	private String nameAccount;
	private double balanceAccount;
	@SuppressWarnings("unused")
	private double balanceMainCurrencyAccount;
	private final DecimalFormat DF = new DecimalFormat("####0.00");
	private Currency cAccount;
	////
	private double creditAccount;
	private double debitAccount;
	
	private ArrayList<Operation> operList;
	
	
	
	public Account(String nameAccount, String cNameAccount){
		this(nameAccount, cNameAccount, 1);
	}
	
	public Account(String nameAccount, String cNameAccount, double rateC){
		this.nameAccount =  nameAccount;
		this.cAccount = new Currency (cNameAccount, rateC);
		this.creditAccount = 0;
		this.debitAccount = 0;
		this.balanceAccount = 0;
		this.balanceMainCurrencyAccount = 0;
		this.operList = new ArrayList<Operation>();
	}
	
	public void addOperation(Operation o ){
		operList.add(o);
		 getBalanceAccount();
	}
	
	public void deleteOperation(Operation o ){
		operList.remove(o);
		getBalanceAccount();
	}

	public double getBalanceMainCurrencyAccount() {
		return balanceMainCurrencyAccount = getBalanceAccount()*this.getcAccount().getRate();
	}
	
	public double getBalanceMainCurrencyAccount(double lastRate) {
		return balanceMainCurrencyAccount = getBalanceAccount()*lastRate;
	}

	public void setBalanceMainCurrencyAccount(double balanceMainCurrencyAccount) {
		this.balanceMainCurrencyAccount = balanceMainCurrencyAccount;
	}
	
	public String getNameAccount() {
		return nameAccount;
	}

	public void setNameAccount(String nameAccount) {
		this.nameAccount = nameAccount;
	}

	public double getBalanceAccount() {
		balanceAccount = 0;
		for(Operation o : operList){
			if(o.getIsIt().equals(Status.ACTUALY)){
				if(o.getTypOperation().equals(TypOperation.PLUS)){
					balanceAccount += o.getSum();
				}
				else if(o.getTypOperation().equals(TypOperation.MINUS)){
					balanceAccount -= o.getSum();
				}
			}
		}
		return balanceAccount;
	}
	
	public double getPlaningBalanceAccount() {
		balanceAccount = 0;
		for(Operation o : operList){
//			if(o.getIsIt().equals(Status.PLAN)){
				if(o.getTypOperation().equals(TypOperation.PLUS)){
					balanceAccount += o.getSum();
				}
				else if(o.getTypOperation().equals(TypOperation.MINUS)){
					balanceAccount -= o.getSum();
				}
//			}
		}
		return balanceAccount;
	}
	

	public void setBalanceAccount(double balanceAccount) {
		this.balanceAccount = balanceAccount;
	}

	public Currency getcAccount() {
		return cAccount;
	}

	public void setcAccount(Currency cAccount) {
		this.cAccount = cAccount;
	}

	public double getCreditAccount() {
		creditAccount = 0;
		for(Operation o : operList){
			if(o.getIsIt().equals(Status.ACTUALY)){
				if(o.getTypOperation().equals(TypOperation.MINUS)){
					creditAccount += o.getSum();
				}
			}
		}
		return creditAccount;
	}
	
	public double getPlaninCreditAccount() {
		creditAccount = 0;
		for(Operation o : operList){
			if(o.getIsIt().equals(Status.PLAN)){
				if(o.getTypOperation().equals(TypOperation.MINUS)){
					creditAccount += o.getSum();
				}
			}
		}
		return creditAccount;
	}
	
	public double getPlaninDebitAccount() {
		creditAccount = 0;
		for(Operation o : operList){
			if(o.getIsIt().equals(Status.PLAN)){
				if(o.getTypOperation().equals(TypOperation.PLUS)){
					creditAccount += o.getSum();
				}
			}
		}
		return creditAccount;
	}

	public void setCreditAccount(double creditAccount) {
		this.creditAccount = creditAccount;
	}

	public double getDebitAccount() {
		debitAccount = 0;
		for(Operation o : operList){
			if(o.getIsIt().equals(Status.ACTUALY)){
				if(o.getTypOperation().equals(TypOperation.PLUS)){
					debitAccount += o.getSum();
				}
			}
			
		}
		return debitAccount;
	}
	
	public double getPlaningDebitAccout(){
		debitAccount = 0;
		for(Operation o : operList){
			if(o.getIsIt().equals(Status.ACTUALY)){
				if(o.getTypOperation().equals(TypOperation.PLUS)){
					debitAccount += o.getSum();
				}
			}
		}
		return debitAccount;
	}

	public void setDebitAccount(double debitAccount) {
		this.debitAccount = debitAccount;
	}

	public ArrayList<Operation> getOperList() {
		return operList;
	}

	public void setOperList(ArrayList<Operation> operList) {
		this.operList = operList;
	}
	
	


	
	

	@Override
	public String toString() {
		return "" + nameAccount + ": " + DF.format(balanceAccount) + " (" + cAccount.getName()
				+ ")";
	}
	
	public String forSave(){
		String a =  nameAccount + "|" + cAccount.getName() + "|"+ cAccount.getRate() + "|" + operList.size() + "";
		for(int i = 0; i<operList.size(); i++){
			a += "\r\n"+ operList.get(i).forSave();
		}
		return a;
	}
	
}
