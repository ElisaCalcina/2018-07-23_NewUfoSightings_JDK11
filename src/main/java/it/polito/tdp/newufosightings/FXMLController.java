package it.polito.tdp.newufosightings;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.newufosightings.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

//controller turno B --> switchare al branch master_turnoA per turno A

public class FXMLController {
	
	private Model model;

	@FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea txtResult;

    @FXML
    private TextField txtAnno;

    @FXML
    private TextField txtxG;

    @FXML
    private Button btnCreaGrafo;

    @FXML
    private TextField txtT1;

    @FXML
    private TextField txtT2;

    @FXML
    private Button btnSimula;

    @FXML
    void doCreaGrafo(ActionEvent event) {

    	String giorni= txtxG.getText();
    	Integer giorniI=0;
    	try {
    		giorniI=Integer.parseInt(giorni);
    	}catch(NumberFormatException e) {
    		txtResult.appendText("Devi inserire un numero");
    	}
    	
    	String anno= this.txtAnno.getText();
    	Integer annoI=0;
    	try {
    		annoI=Integer.parseInt(anno);
    	}catch(NumberFormatException e) {
    		txtResult.appendText("Devi inserire un numero");
    	}
    	
    	if(giorniI>=1 && giorniI<=180 && annoI>=1906 && annoI<=2014) {
    		this.model.creaGrafo(annoI, giorniI);
    		txtResult.appendText("Grafo creato!\n");
    		txtResult.appendText("#vertici: "+ this.model.nVertici()+"\n");
    		txtResult.appendText("#archi: "+ this.model.nArchi()+"\n");

    	}
    }

    @FXML
    void doSimula(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
        assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
        assert txtxG != null : "fx:id=\"txtxG\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
        assert txtT1 != null : "fx:id=\"txtT1\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
        assert txtT2 != null : "fx:id=\"txtT2\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
	}
}
