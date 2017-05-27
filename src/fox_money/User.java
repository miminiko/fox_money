/**
 * 
 */
package fox_money;

import java.util.ArrayList;
import java.util.Locale;

import fox_money.ui.LangKey;

/**
 * @author endem
 *
 */
public class User implements ISavable{
	private String nameUser;
	private Currency mainCurrency;
	private final double RATE = 1;
	private  ArrayList <Currency> currencyList;
	private  ArrayList <Category> categoryList;
	private  ArrayList <String> contragetnList;
	private ArrayList<Budget> budgetList;

	
	private ArrayList <Account> accountList;
	private double creditUser;
	private double debitUser;
	private double userBALANCE;
	
	LangKey keyUser;

	
	public User(String nameUser, String nameMainCurrency){
		this.nameUser = nameUser;
		this.mainCurrency = new Currency(nameMainCurrency, RATE);
		this.accountList = new ArrayList<Account>();
		this.creditUser = 0;
		this.debitUser = 0;
		this.userBALANCE = 0;
		this.currencyList = new ArrayList <Currency>();
		this.currencyList.add(mainCurrency);
		this.categoryList = new ArrayList <Category>();
		this.contragetnList = new ArrayList <String>();
		this.budgetList = new ArrayList <Budget>();
		this.keyUser = new LangKey(Locale.US);
	}
	
	
	
	
	public void addAccount(Account a){
		accountList.add(a);
	}
	
	
	public int getIndexAccountInList(Account a){
		for(int i = 0; i< accountList.size(); i++){
			if(accountList.get(i).getNameAccount().equals(a.getNameAccount())){
				return i;
			}
		}
		return -1;
	}
	
	public void deleteAccount(Account a){
		for(int i = 0; i< accountList.size(); i++){
			if(accountList.get(i).getNameAccount().equals(a.getNameAccount())){
				accountList.remove(i);
				break;
			}
		}
		System.out.println("After delete " + accountList.size());
		System.out.println(getAccountList().size());
		System.out.println(getAccountList());

	}
	
	public void addOperation(Account a, Operation o){
		for(int i = 0; i<accountList.size(); i++){
			if(accountList.get(i).getNameAccount().equals(a.getNameAccount())){
				accountList.get(i).addOperation(o);
				
//				
			}
		}
		getUserBALANCE();
		newBalance();
	}
	
	public void deleteOperation(Account a, Operation o){
		for(int i = 0; i<accountList.size(); i++){
			if(accountList.get(i).getNameAccount().equals(a.getNameAccount())){
				accountList.get(i).getOperList().remove(o);
			}
		}
		getUserBALANCE();
		newBalance();
	}
	
	public void addTransferOperation(Account a1, Account a2, double sum, double rate, String currencyName){
		for(int i = 0; i<accountList.size(); i++){
			if(accountList.get(i).getNameAccount().equals(a1.getNameAccount())){
				System.out.println("1");
				for(int j = 0; j<accountList.size(); j++){
					if(accountList.get(j).getNameAccount().equals(a2.getNameAccount())){
						System.out.println("2");

						Operation o1 = new Operation(sum, currencyName , rate, ("Transfer to " + accountList.get(j).getNameAccount()), TypOperation.MINUS);
						Operation o2 = new Operation((sum*rate), currencyName , 1,  ("Transfer frome " + accountList.get(i).getNameAccount()), TypOperation.PLUS);
						accountList.get(i).addOperation(o1);
						accountList.get(j).addOperation(o2);
					}
				}
			}
		}
		getUserBALANCE();
		newBalance();
	}
	
	
	/***
	 * ��������� ���� �� ��� ��������� ��� ����������������
	 * (��� ���� ��������� � ��� ���������)
	 * ���� ��, �� ������� � ��� ������������
	 * ���� ���, ������� �� ��� �����
	 * @param typ
	 * @param c
	 * @param s
	 */
	public void addNewCategoryWithSubCategory(TypOperation typ, String c, String s){
		boolean itsNewCategory = true;
		for(Category cL : categoryList){
			if(cL.getCategoryName().equals(c) && cL.getTyp().equals(typ)){
				itsNewCategory = false;
				addSubCategory(cL, s);
			}
		}
		if(itsNewCategory){
			Category newCategory = new Category(typ, c);
			newCategory.addSubCategory(s);
			categoryList.add(newCategory);
		}
	}
	
	/***
	 * ���� � ������ ��������� ��� �� ���� ���� ������������ -> 
	 * ������� ������������
	 * @param c
	 * @param s
	 */
	public void addSubCategory(Category c, String s){
		if(s != "" && s != null){
			for(int i = 0; i<categoryList.size(); i++){
				if(categoryList.get(i).getCategoryName().equals(c.getCategoryName())){
					if(!(categoryList.get(i).getNameSubCategory().contains(s))){
						categoryList.get(i).addSubCategory(s);
					}
				}
			}
		}
		
		
	}
	
	public void deleteSubCategory(Category c, String s){
		for(int i = 0; i<categoryList.size(); i++){
			if(categoryList.get(i).getCategoryName().equals(c.getCategoryName())){
				categoryList.get(i).getNameSubCategory().remove(s);
			}
		}
	}
	
	public void addNewBudget(Budget b){
		budgetList.add(b);
	}
	
	public void newBalance(){
		userBALANCE = getDebitUser() - getCreditUser();
	}
	
	public void addCurrencyUser(Currency newCurrency){
		this.currencyList.add(newCurrency);
	}
	
	public void setCurrencyName(Currency c, String name){
		for(int i =0; i<currencyList.size(); i++){
			if(currencyList.get(i).equals(c)){
				currencyList.get(i).setName(name);;
			}
		}
	}
	
	public void setCurrencySymbol(Currency c, String s){
		for(int i =0; i<currencyList.size(); i++){
			if(currencyList.get(i).equals(c)){
				currencyList.get(i).setSymbol(s);
			}
		}
	}
	
	public void setCurrencyRate(Currency c, double r){
		for(int i =0; i<currencyList.size(); i++){
			if(currencyList.get(i).equals(c)){
				currencyList.get(i).setRate(r);
			}
		}
	}
	
	public double getLastRateForCurrency(String cur){
		for(int i = 0; i>currencyList.size(); i++){
			if(currencyList.get(i).getName().equals(cur)){
				System.out.println();
				return currencyList.get(i).getRate();
			}
		}
		return 0.0;
	}
	
	public Currency getCurrencyByName(String cur){
		for(int i = 0; i<currencyList.size(); i++){
			if(currencyList.get(i).getName().equals(cur)){
				return currencyList.get(i);
			}
		}
		return null;
	}
	
	public void deleteCurrencyUser(Currency newCurrency){
		currencyList.remove(newCurrency);
	}
	
	public void addCategoryUser(Category category){
		this.categoryList.add(category);
	}
	
	public ArrayList<String> getCategoryByTyp(TypOperation typ){
		ArrayList <String> typCat = new ArrayList<String>();
		for(Category c : categoryList){
			if(c.getTyp().equals(typ)){
				typCat.add(c.getCategoryName());
			}
		}
		return typCat;
	}
	
	public void deleteCategoryUser(Category category){
		//TO-DO 
	}
	
	public ArrayList <String> getCurrencyStringList(){
		ArrayList <String> c = new ArrayList<String>();
		currencyList.forEach(a -> c.add(a.getName()));
		return c;
	}

	public String getNameUser() {
		return nameUser;
	}

	public void setNameUser(String nameUser) {
		this.nameUser = nameUser;
	}

	public Currency getMainCurrency() {
		return mainCurrency;
	}

	public void setMainCurrency(Currency mainCurrency) {
		this.mainCurrency = mainCurrency;
	}

	public  ArrayList<Currency> getCurrencyList() {
		return currencyList;
	}

	public void setCurrencyList(ArrayList<Currency> currencyList) {
		this.currencyList = currencyList;
	}

	public  ArrayList<Category> getCategoryList() {
		return categoryList;
	}
	
	public  ArrayList<String> getCategoryListString() {
		ArrayList <String> category = new ArrayList <String>();
		if(getCategoryList().size()>0){
			
			getCategoryList().forEach(c -> category.add(c.getCategoryName()));
		}
		else{
			category.add("");
		}
		
		
		return category;
	}

	public  void setCategoryList(ArrayList<Category> categoryList) {
		this.categoryList = categoryList;
	}

	public ArrayList<Account> getAccountList() {
		return accountList;
	}

	public void setAccountList(ArrayList<Account> accountList) {
		this.accountList = accountList;
	}

	public double getCreditUser() {
		creditUser = 0;
		for(Account a : accountList){
			
			
			if(a.getcAccount().getName().equals(mainCurrency)){
				creditUser += a.getCreditAccount();
			}
			else{
				double rate = 1;
				for(Currency c : currencyList){
					if(a.getcAccount().equals(c)){
						rate = c.getRate();
					}
				}
				creditUser += a.getCreditAccount()*rate;
			}
		}
		return creditUser;
	}

	public void setCreditUser(double creditUser) {
		this.creditUser = creditUser;
	}
	
	public double getPlaningCreditUser(){
		double s = 0;
		for(Account a : accountList){
			s = a.getPlaninCreditAccount();
		}
		return s;
	}
	
	public double getPlaningDebitUser(){
		double s = 0;
		for(Account a : accountList){
			s = a.getPlaninDebitAccount();
		}
		return s;
	}

	public double getDebitUser() {
		debitUser = 0;
		for(Account a : accountList){
			if(a.getcAccount().getName().equals(mainCurrency)){
				debitUser += a.getDebitAccount();
			}
			else{
				double rate = 1;
				for(Currency c : currencyList){
					if(a.getcAccount().equals(c)){
						rate = c.getRate();
					}
				}
				debitUser += a.getDebitAccount()*rate;
			}
		}
		return debitUser;
	}

	public void setDebitUser(double debitUser) {
		this.debitUser = debitUser;
	}

	public double getUserBALANCE() {
		userBALANCE = 0;
		for(Account a : accountList){
			
				userBALANCE += a.getBalanceMainCurrencyAccount();
			
		}
		return userBALANCE;
	}

	public void setUserBALANCE(double userBALANCE) {
		this.userBALANCE = userBALANCE;
	}

	public double getRATE() {
		return RATE;
	}
	

	public ArrayList<String> getContragetnList() {
		return contragetnList;
	}
	
	public void addContragetnToList(String newContragent) {
		if(newContragent != null){
			if(newContragent.length() > 0){
				for(String s : contragetnList){
					if(s.equals(newContragent)) return;
				}
				System.out.println("New contragen " + newContragent);
				contragetnList.add(newContragent);
			}
		}
		
		
//		if(!(contragetnList.contains(newContragent))){
//			contragetnList.add(newContragent);
//		}
	}

	public  void setContragetnList(ArrayList<String> contragetnList) {
		this.contragetnList = contragetnList;
	}
	
	

	public ArrayList<Budget> getBudgetList() {
//		System.out.println("getBudgetList() ");

		for(int j = 0; j< budgetList.size(); j++){
//			System.out.println("1 "+ j);

			
			for(int i = 0; i< accountList.size(); i++){
//				System.out.println("2 "+ i);

				for(Operation o: accountList.get(i).getOperList()){
//					System.out.println("3 "+ o.getCategory());

					if(o.getTypOperation().equals(TypOperation.MINUS) && o.getIsIt().equals(Status.ACTUALY)){
//						System.out.println("4 "+ o.getTyp());

						if(o.getCategory().equals(budgetList.get(j).getBudgetCategory())){
							System.out.println("budgetList.get(j).getBudgetCategory()  " + budgetList.get(j).getBudgetCategory());
							
							if(budgetList.get(j).getBudgetSubCategory() != null &&
									budgetList.get(j).getBudgetSubCategory().equals(o.getSubcategory())){
								System.out.println("o.getSubcategory()  " + (o.getSubcategory()));

								System.out.println("getBeginDate().isAfter(o.getDate()  " + (budgetList.get(j).getBeginDate().isAfter(o.getDate())));
								System.out.println("getImportantDate().isBefore(o.getDate()  " + (budgetList.get(j).getImportantDate().isBefore(o.getDate())));

								if(budgetList.get(j).getBeginDate().isBefore(o.getDate()) && 
										budgetList.get(j).getImportantDate().isAfter(o.getDate())){
									System.out.println(budgetList.get(j).getRemainingSumm());
									budgetList.get(j).addMinusSumm(o.getMainSum());
									System.out.println(budgetList.get(j).getRemainingSumm());

								}
							}
							else if(budgetList.get(j).getBudgetSubCategory() == null){
								System.out.println("getBeginDate().isAfter(o.getDate()  " + (budgetList.get(j).getBeginDate().isAfter(o.getDate())));
								System.out.println("getImportantDate().isBefore(o.getDate()  " + (budgetList.get(j).getImportantDate().isBefore(o.getDate())));

								if(budgetList.get(j).getBeginDate().isBefore(o.getDate()) && 
										budgetList.get(j).getImportantDate().isAfter(o.getDate())){
									System.out.println(budgetList.get(j).getRemainingSumm());
									budgetList.get(j).addMinusSumm(o.getMainSum());
									System.out.println(budgetList.get(j).getRemainingSumm());

								}
							}
						}
					}
					
				}
			}
		}
		
		return budgetList;
	}




	public void setBudgetList(ArrayList<Budget> budgetList) {
		this.budgetList = budgetList;
	}




	public LangKey getKeyUser() {
		return keyUser;
	}




	public void setKeyUser(Locale l) {
		
		this.keyUser = new LangKey(l);
	}




	@Override
	public String toString() {
		return "User [nameUser=" + nameUser + ", mainCurrency=" + mainCurrency.getName() + ", accountList=" + accountList.size()
				+ ", userBALANCE=" + userBALANCE + "]";
	}

	@Override
	public String forSave() {
		String u = nameUser + "|" + mainCurrency.getName() + "|" + accountList.size() + "|" + currencyList.size() +  "|" + categoryList.size() + "|" + contragetnList.size() + 
				"|" + keyUser.getLocale().getLanguage() + "|" + keyUser.getLocale().getCountry()+ "|" + budgetList.size() +"\r\n";
//		u.keyUser.setLocale(Locale.US);
		for(int i = 0; i<accountList.size(); i++){
			u += accountList.get(i).forSave() + "\r\n";
		}
		for(int i = 1; i<currencyList.size(); i++){
			if(currencyList.size() > 1){
				u += currencyList.get(i).forSave() + " \r\n";
			}
		}
		for(int i = 0; i<categoryList.size(); i++){
			u += categoryList.get(i).forSave() + "";
		}
		for(int i = 0; i<contragetnList.size(); i++){
			u += contragetnList.get(i) + "|\r\n";
		}
		for(int i = 0; i<budgetList.size(); i++){
			u += budgetList.get(i).forSave() + "|\r\n";
		}

		return u;
	}
	
	
	
}
