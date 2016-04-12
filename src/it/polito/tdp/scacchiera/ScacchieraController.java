package it.polito.tdp.scacchiera;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.scacchiera.model.Ricerca;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;

public class ScacchieraController {
	
	private Ricerca model ;

    public void setModel(Ricerca model) {
		this.model = model;
	}

	@FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Spinner<Integer> spinSize;

    @FXML
    private TextArea txtResult;

    @FXML
    void doCalcola(ActionEvent event) {
    	
    	Integer size = spinSize.getValue() ;
    	
    	long start = System.nanoTime() ;
    	int soluzioni = model.contaSoluzioni(size) ;
    	long end = System.nanoTime() ;
    	
    	txtResult.appendText(String.format("Lato %d: %d soluzioni (%d ms)\n", size, soluzioni,
    			(end-start)/1000000));

    }

    @FXML
    void initialize() {
        assert spinSize != null : "fx:id=\"spinSize\" was not injected: check your FXML file 'Scacchiera012.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scacchiera012.fxml'.";
        
        spinSize.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 20));
        spinSize.getValueFactory().setValue(5);
    }
}
