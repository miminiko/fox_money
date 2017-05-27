/**
 * 
 */
package fox_money;

import java.util.ArrayList;

/**
 * @author endem
 *
 */
public class Category implements ISavable{
	private String categoryName;
	private TypOperation typ;
	private ArrayList <String> nameSubCategory;
	
	public Category (TypOperation typ, String categoryName){
		this.typ = typ;
		this.categoryName = categoryName;
		this.nameSubCategory = new ArrayList <String>();
	}
	
	public void addSubCategory(String name){
		nameSubCategory.add(name);
	}

	
	//////////////////////////////////////////////////////
	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public TypOperation getTyp() {
		return typ;
	}

	public void setTyp(TypOperation typ) {
		this.typ = typ;
	}

	public ArrayList<String> getNameSubCategory() {
		return nameSubCategory;
	}
	
	
	/***
	 * 
	 * @param sLast
	 * @param sNew
	 */
	public void setNameSubCategory(String sLast, String sNew) {
		for(String s : nameSubCategory){
			if(s.equals(sLast)) s = sNew;
		}
	}

	
	
	@Override
	public String toString() {
		return "Category [categoryName=" + categoryName + ", typ=" + typ + ", nameSubCategory=" + nameSubCategory + "]";
	}

	@Override
	public String forSave() {
		String a = categoryName + "|" + typ.getName() + "|" + nameSubCategory.size() + "\r\n";
		for(int i = 0; i<nameSubCategory.size(); i++){
			a += nameSubCategory.get(i) + "\r\n";
		}
		return a;
	}
	
	
	
}
