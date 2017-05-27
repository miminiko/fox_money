/**
 * 
 */
package fox_money;

import java.time.LocalDate;

/**
 * @author endem
 *
 */


public class Budget implements ISavable {
	
	private double summBegin;
	private double summNow;

	private Currency curBudget;
	private String budgetCategory;
	private String budgetSubCategory;

	private int howLong;
	private LocalDate beginDate;
	private LocalDate importantDate;
	
	
	public Budget(double summ, Currency curBudget,
			String budgetCategory, String budgetSubCategory, 
			LocalDate begin, LocalDate importantDate){
		this.summBegin = summ;
		this.curBudget = curBudget;
		this.budgetCategory = budgetCategory;
		this.budgetSubCategory = budgetSubCategory;
		this.beginDate = begin;
		this.importantDate = importantDate;
	}
	
	
	public double getSummMonth(){
		if(getMonthToImportantDate() > 0){
			return (summBegin - summNow)/getMonthToImportantDate();
		}
		return 0;
	}
	
	public int getMonthToImportantDate(){
		if(importantDate.getYear() == LocalDate.now().getYear()){
			return importantDate.getMonthValue() - LocalDate.now().getMonthValue();
		}
		else if(importantDate.getYear() > LocalDate.now().getYear()){
			int month = (importantDate.getYear() - LocalDate.now().getYear())*12;
			if(importantDate.getMonthValue() >= LocalDate.now().getMonthValue()){
				month += importantDate.getMonthValue() - LocalDate.now().getMonthValue();
			}else{
				month -=  LocalDate.now().getMonthValue() - importantDate.getMonthValue();
			}
			return month;
		}
		
		return 0;
	}
	
	/***
	 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!
	 * @return
	 */
	public double getRemainingSumm() {
		return summNow;
	}
	
	public void addMinusSumm(double sum) {
		this.summNow -= sum;
	}

	public double getSummBegin() {
		return summBegin;
	}

	public void setSummBegin(double summBegin) {
		this.summBegin = summBegin;
	}

	public Currency getCurBudget() {
		return curBudget;
	}

	public void setCurBudget(Currency curBudget) {
		this.curBudget = curBudget;
	}


	public int getHowLong() {
		return howLong;
	}

	public void setHowLong(int howLong) {
		this.howLong = howLong;
	}
	
	public double getProcent() {
		return getRemainingSumm()/summBegin;
	}

	public LocalDate getImportantDate() {
		return importantDate;
	}

	public void setImportantDate(LocalDate importantDate) {
		this.importantDate = importantDate;
	}

	public String getBudgetCategory() {
		return budgetCategory;
	}

	public void setBudgetCategory(String budgetCategory) {
		this.budgetCategory = budgetCategory;
	}

	public String getBudgetSubCategory() {
		return budgetSubCategory;
	}

	public void setBudgetSubCategory(String budgetSubCategory) {
		this.budgetSubCategory = budgetSubCategory;
	}

	public LocalDate getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(LocalDate beginDate) {
		this.beginDate = beginDate;
	}

	@Override
	public String forSave() {
		return summBegin + "|" + curBudget.getName() + "|" +budgetCategory + "|" + budgetSubCategory + "|" 
						+ beginDate.toString() + "|" + importantDate.toString();
	}

	
	

}
