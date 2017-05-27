/**
 * 
 */
package fox_money;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;


/**
 * @author endem
 *
 */
public class Saver {
	
	private String fileName;
	
	public Saver(String f){
		this.fileName = f;
	}
	
	public void saveData(User u){
			try{
				System.out.println("Begin save data user: " + u.getNameUser() );
				
//				BufferedWriter bw = new BufferedWriter(new FileWriter(this.fileName));
				FileWriter f = new FileWriter(new File(this.fileName));
//				bw.write(u.forSave());
				f.write(u.forSave());
//				bw.close();
				f.close();
				System.out.println("Save data user: " + u.getNameUser() );
			}catch(IOException e){
				System.out.println("I did`n save!!!!");
				e.printStackTrace();
			}
		
	}
	
	public User readData(){
		if(new File(this.fileName).exists()){
			try{
				String cvsSplitBy = "\\|";

				Scanner sc = new Scanner(new FileReader(this.fileName));
				sc.useLocale(Locale.US);
				
				String line =sc.nextLine();
				String [] dataUser =line.split(cvsSplitBy);
				System.out.println(Arrays.toString(dataUser));
				User u = new User(dataUser[0], dataUser[1] );
				u.getMainCurrency().setItMainCurrency(true);
				int howManyAccount = Integer.parseInt(dataUser[2]);
				int howManyCurrency = Integer.parseInt(dataUser[3]);
				int howManyCategory = Integer.parseInt(dataUser[4]);
				int howManyContragent = Integer.parseInt(dataUser[5]);
				Locale l = new Locale(dataUser[6], dataUser[7]);
				int howManyBudget = Integer.parseInt(dataUser[8]);
				
				for(int i = 0; i<howManyAccount; i++){
					line =sc.nextLine();
					dataUser =line.split(cvsSplitBy);
					
					Account a = new Account(dataUser[0], dataUser[1]);
					double r = Double.parseDouble(dataUser[2]);

					a.getcAccount().setRate(r);
					int howManyOperation = Integer.parseInt(dataUser[3]);
					for(int j = 0; j<howManyOperation; j++){
						line =sc.nextLine();
						dataUser =line.split(cvsSplitBy);
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
						LocalDate d = LocalDate.parse(dataUser[0], formatter);
						double sum = Double.parseDouble(dataUser[1]);
						String nameCurOp = dataUser[2];
						double rate = Double.parseDouble(dataUser[3]);
						String nameTypOp = dataUser[4];
						TypOperation typ = TypOperation.getTyp(nameTypOp);
						
						String category = dataUser[5];
						String subcategory = dataUser[6];
						String contragent = dataUser[7];
						String note =  dataUser[8];
						
						String status = dataUser[9];
						Status s = Status.getStatus(status);
						Operation o = new Operation(d, sum, nameCurOp, rate, 
								typ, category, subcategory, contragent, note, s);
						a.addOperation(o);
					}
					
					u.addAccount(a);
					 
				}
				for(int i = 0; i< howManyCurrency; i++){
					
					if(i>0){
						line =sc.nextLine();
						dataUser =line.split(cvsSplitBy);
						u.addCurrencyUser(new Currency(dataUser[0], dataUser[1], Double.parseDouble(dataUser[2])));
					}
				}
				for(int i = 0; i<howManyCategory;i++){
//					while(sc.hasNextLine()){
						line =sc.nextLine();
						dataUser =line.split(cvsSplitBy);
						String nameCategory = dataUser[0];
						String nameTypOp = dataUser[1];
						TypOperation typ = TypOperation.getTyp(nameTypOp);
						Category c = new Category(typ, nameCategory);
//						System.out.println(c.toString());
						int howManySubCategory = Integer.parseInt(dataUser[2]);
						for(int j = 0; j<howManySubCategory; j++){
							
							c.addSubCategory(line =sc.nextLine());
							System.out.println(c.toString());
						}
						u.addCategoryUser(c);
//					}
					
				}
				for(int i = 0; i< howManyContragent; i++){
					line =sc.nextLine();
					System.out.println(line);
					dataUser =line.split(cvsSplitBy);
					if(dataUser.length >0){
						if(dataUser[0] != null && dataUser[0].length() >0) {
							u.addContragetnToList(dataUser[0]);
						}
					}
					
					
//					u.addCurrencyUser(new Currency(dataUser[0], dataUser[1], Double.parseDouble(dataUser[2])));
				}
				for(int i = 0; i<howManyBudget; i++){
					line =sc.nextLine();
					dataUser =line.split(cvsSplitBy);
					double sum = Double.parseDouble(dataUser[0]);
					String cur = dataUser[1];
					String categoryName = dataUser[2];
					String subCategoryName = dataUser[3];
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					LocalDate d1 = LocalDate.parse(dataUser[4], formatter);
					LocalDate d2 = LocalDate.parse(dataUser[5], formatter);

					u.addNewBudget(new Budget (sum, u.getCurrencyByName(cur) , categoryName, subCategoryName, d1, d2));
					
				}
				sc.close();
				
				u.setKeyUser(l);;
				System.out.println(u.getKeyUser().getLocale());

				System.out.println(u.getNameUser() + " was readed");
				return u;
				
			}catch(IOException e){
				
			}
		}
		
		return null;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	
	
}
