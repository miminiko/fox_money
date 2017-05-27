/**
 * 
 */
package fox_money;

import java.util.ArrayList;

/**
 * @author endem
 *
 */
public class LineCheque implements ISavable {
	
	private String category;
	private String subcategory;
	private double sum;
	private String note;
	
	public LineCheque(double sum, String category, String subcategory, String note){
		this.sum = sum;
		this.category = category;
		this.subcategory = subcategory;
		this.note = note;
		
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSubcategory() {
		return subcategory;
	}

	public void setSubcategory(String subcategory) {
		this.subcategory = subcategory;
	}

	public double getSum() {
		return sum;
	}

	public void setSum(double sum) {
		this.sum = sum;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Override
	public String forSave() {
		return sum + "|" + category + "|" + subcategory + "|" + note + "|";
	}
	
	
	
}
