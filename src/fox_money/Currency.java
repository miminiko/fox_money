/**
 * 
 */
package fox_money;

/**
 * @author endem
 *
 */
public class Currency implements ISavable{
	private String name;
	private String symbol;
	private boolean itMainCurrency;

	private double rate;
	
	public Currency (String nameCurrency, double exchangeRateFresh){
		this( nameCurrency, "",  exchangeRateFresh);
	}

	public Currency (String nameCurrency, String symbolCurrency, double exchangeRateFresh ){
		this.name = nameCurrency;
		this.rate = exchangeRateFresh;
		this.symbol = symbolCurrency;
	}
	
	
	
	///////////////////////////////////////////////////
	public String getName() {
		return name;
	}

	public void setNameCurrency(String nameCurrency) {
		this.name = nameCurrency;
	}

	public double getRate() {
		return rate;
	}

	public void setExchangeRateFresh(double exchangeRateFresh) {
		this.rate = exchangeRateFresh;
	}


	public String getSymbol() {
		return symbol;
	}


	public void setSymbolCurrency(String symbolCurrency) {
		this.symbol = symbolCurrency;
	}
	
	

	public void setName(String name) {
		this.name = name;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	@Override
	public String toString() {
		return "" + name + "";
	}

	@Override
	public String forSave() {
		
		return name + "|" + symbol + "|" + rate;
	}

	public boolean isItMainCurrency() {
		return itMainCurrency;
	}

	public void setItMainCurrency(boolean itMainCurrency) {
		this.itMainCurrency = itMainCurrency;
	}
	
	
	
}
