package fox_money.ui;

import javafx.application.Preloader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class PredLoader extends Preloader {
	private Stage stage;
	public ProgressIndicator progress;
	
	@Override
	public void start(Stage stage) throws Exception {
			this.stage = stage;
			Group root = new Group();
			root.setTranslateX(200);
			root.setTranslateY(200);
			Scene scene = new Scene(root, 600, 600, Color.CORNFLOWERBLUE);
			BorderPane pane = new BorderPane();
			pane.setPrefSize(200,200);
			
			progress=new ProgressIndicator();
			progress.setProgress(-0);
//			progress.setStyle("-fx-font:bold 20pt Arial;");
			
			pane.setCenter(progress);
			root.getChildren().addAll(pane);
			stage.setScene(scene);
			stage.show(); 
			}
		
	
		@Override
		public void handleStateChangeNotification(StateChangeNotification scn)
		{ if (scn.getType() == StateChangeNotification.Type.BEFORE_START) {
		stage.hide();
		} 
			}
		@Override
		public void handleProgressNotification(ProgressNotification pn) {
			progress.setProgress(pn.getProgress()); 
		}
		
		@Override
		public void handleApplicationNotification(Preloader.PreloaderNotification info){
			Preloader.ProgressNotification ntf=(Preloader.ProgressNotification) info;
			if (ntf.getProgress()==1.0) stage.hide();
			else progress.setProgress(-1);
		}
		
	}

