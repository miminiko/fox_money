package fox_money;

import javafx.scene.image.Image;

public enum TypOperation {
	PLUS, MINUS;
	
	public Image getImageTyp(){
		switch (this) {
		case PLUS:
			return new Image("plus.png");
		case MINUS:
			return new Image("minus.png");
		}
		return null;
	}
	
	public String getName(){
		switch (this) {
		case PLUS:
			return "plus";
		case MINUS:
			return "minus";
		}
		return null;
	}
	
	public static TypOperation getTyp(String typ){
		switch (typ) {
		case "plus":
			return PLUS;
		case "minus":
			return MINUS;
		}
		return null;
	}
}
