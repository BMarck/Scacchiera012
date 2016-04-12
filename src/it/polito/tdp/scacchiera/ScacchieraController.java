package it.polito.tdp.scacchiera;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.scacchiera.model.Ricerca;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
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
    private ProgressBar pBar;
    
    @FXML
    private Label lblStatus;
    
    private class Risultato {
    	
    	public Risultato(Integer soluzioni, Long tempo) {
			super();
			this.soluzioni = soluzioni;
			this.tempo = tempo;
		}
		public Integer soluzioni ;
    	public Long tempo ;
    }

    @FXML
    void doCalcola(ActionEvent event) {
    	
    	Integer size = spinSize.getValue() ;
    	
    	Task<Risultato> task = new Task<Risultato>() {
    		
			@Override
			protected Risultato call() throws Exception {
		    	long start = System.nanoTime() ;
		    	Ricerca ricerca = new Ricerca() ;
		    	updateProgress(-1, -1);
		    	updateMessage("Avviata ricerca dimensione "+size) ;
		    	int soluzioni = ricerca.contaSoluzioni(size) ;
		    	updateProgress(1, 1);
		    	updateMessage("Terminata ricerca dimensione "+size) ;
		    	long end = System.nanoTime() ;
		    			    	
		    	return new Risultato(soluzioni, end-start);
			}
    	} ;
    	
    	task.setOnSucceeded( new EventHandler<WorkerStateEvent>() {
			
			@Override
			public void handle(WorkerStateEvent event) {
				Risultato ris = task.getValue() ;
				
				txtResult.appendText(String.format(
						"Lato %d: %d soluzioni (%d ms)\n", size, ris.soluzioni,
						ris.tempo/1000000));
				
			}
		} );
    	
    	pBar.progressProperty().bind(task.progressProperty());
    	lblStatus.textProperty().bind(task.messageProperty());
    	
    	Thread th = new Thread(task) ;
    	th.setDaemon(true);
    	th.start();
    }

    @FXML
    void initialize() {
        assert spinSize != null : "fx:id=\"spinSize\" was not injected: check your FXML file 'Scacchiera012.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scacchiera012.fxml'.";
        
        spinSize.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 20));
        spinSize.getValueFactory().setValue(5);
    }
}
