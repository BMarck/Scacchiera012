package it.polito.tdp.scacchiera;
	
import it.polito.tdp.scacchiera.model.Ricerca;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Scacchiera012.fxml")) ;
			BorderPane root = (BorderPane)loader.load();
			
			ScacchieraController controller = loader.getController() ;
			Ricerca model = new Ricerca() ;
			controller.setModel(model) ;
			
			Scene scene = new Scene(root);			
			
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
