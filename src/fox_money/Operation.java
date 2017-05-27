/**
 * 
 */
package fox_money;

import java.time.LocalDate;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
/**
 * @author endem
 *
 */
public class Operation implements ISavable {

	private ObjectProperty<LocalDate> date;
	private String dateToString;
	private String typ;

	/***
	 * ����� �������� ����� ��� ������ (���� 30, ���� ��� ���� �������, � ������� � ������)
	 * ����� ��� ������ ������
	 */
	private double sum;
	/***
	 * ����� ������� ��� ����� ����� �� ������� ���� ���������, ������� � ����� ������ ��� ����������� � ����� ��������
	 */
	private double mainSum;

	private Currency cOperation;
	private TypOperation typOperation;
	private String category;
	private String subcategory;
	private String contragent;
	private String note;
	
	
	private Status isIt;
	
	
	public Operation(double sum, String nameCOperation, String nameCategory, TypOperation t ){
		this(LocalDate.now(), sum, nameCOperation, 1, t, nameCategory, "", "", "", Status.ACTUALY);
	}
	
	/***
	 * 
	 * @param sum
	 * @param nameCOperation
	 * @param rate
	 */
	public Operation(double sum, String nameCOperation, double rate, String nameCategory, TypOperation t ){
		this(LocalDate.now(), sum, nameCOperation, rate, t, nameCategory, "", "", "", Status.ACTUALY);
	}
	
	
	
	
	/***
	 * 
	 * @param date
	 * @param sum ����� ��������
	 * @param nameCOperation �������� ������ � ������� �������� ���� ���������
	 * @param rate ���� �� �������� �������� �����������
	 * @param plus 
	 * @param category 
	 * @param subcategory
	 * @param contrAgent
	 * @param note
	 * @param isIt
	 */
	public Operation(LocalDate date, 
			double sum, String nameCOperation, double rate,
			TypOperation plus, String category, String subcategory,
			String contrAgent, String note, Status isIt			){
		
		
		if(date.getYear()>0){
			this.date = new SimpleObjectProperty<LocalDate>(date);
		}else{
			this.date = new SimpleObjectProperty<LocalDate>(LocalDate.now());
		}
		
		this.cOperation = new Currency(nameCOperation, rate);
		this.sum = sum;
//		this.mainSum = sum*rate;
		
		if(plus.equals(TypOperation.PLUS)){
			this.typOperation = TypOperation.PLUS;
			this.typ = "plus";
			this.mainSum = sum*rate;
		}
		else if(plus.equals(TypOperation.MINUS)){
			this.typOperation = TypOperation.MINUS;
			this.typ = "minus";
			this.mainSum = -(sum*rate);

		}
		this.category = category;
		this.subcategory = subcategory;
		this.contragent = contrAgent;
		this.note = note;
		if(isIt.equals(Status.ACTUALY)){
			this.isIt = Status.ACTUALY;
		}
		else if(isIt.equals(Status.PLAN)){
			this.isIt = Status.PLAN;
		}
	}

	
	
	
	
	
	
	
	
	///////////////////////////////////////////////////////
	public LocalDate getDate() {
		return date.get();
	}

	public void setDate(LocalDate date) {
		this.date.set(date); 
	}

	public double getSum() {
		return sum;
	}

	public void setSum(double sum) {
		this.sum = sum;
	}

	public Currency getcOperation() {
		return cOperation;
	}

	public void setcOperation(Currency cOperation) {
		this.cOperation = cOperation;
	}

	public TypOperation getTypOperation() {
		return typOperation;
	}

	public void setTypOperation(TypOperation typOperation) {
		this.typOperation = typOperation;
	}

	public String getCategory() {
		if(category != null){
			return category;
		}
		else{
			return "";
		}
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSubcategory() {
		if(category != null){
			return subcategory;
		}
		else{
			return "";
		}
	}

	public void setSubcategory(String subcategory) {
		this.subcategory = subcategory;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Status getIsIt() {
		return isIt;
	}

	public void setIsIt(Status isIt) {
		this.isIt = isIt;
	}


	public double getMainSum() {
		return mainSum;
	}

	public void setMainSum(double mainSum) {
		this.mainSum = mainSum;
	}

	public String getContragent() {
		if(contragent != null){
			return contragent;
		}else{
			return "";
		}
		
	}

	public void setContragent(String contrAgent) {
		this.contragent = contrAgent;
	}
	
	

	public String getDateToString() {
		return dateToString;
	}

	public void setDateToString(String dateToString) {
		this.dateToString = dateToString;
	}

	public String getTyp() {
		return typ;
	}

	public void setTyp(String typToString) {
		this.typ = typToString;
	}

	@Override
	public String toString() {
		return "Operation: \r\n" + date.get().toString() + " " + getMainSum() + " (" + cOperation + "),  \r\n"
				 + category + " (" + subcategory + ")\r\n" + note;
	}

	@Override
	public String forSave() {
		
		return date.get().toString() + "|" + sum  + "|" + cOperation.getName() + "|" + cOperation.getRate() 
		+ "|" + typOperation.getName() + "|" + category + "|" + subcategory + "|" + contragent + "|" + note + "|" + isIt.getName();
	}
	
	
	
}
