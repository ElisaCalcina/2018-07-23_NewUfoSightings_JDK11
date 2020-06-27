package it.polito.tdp.newufosightings;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.newufosightings.model.Adiacenze;
import it.polito.tdp.newufosightings.model.Model;
import it.polito.tdp.newufosightings.model.State;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

//controller turno A --> switchare al branch master_turnoB per turno B

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
    private Button btnSelezionaAnno;

    @FXML
    private ComboBox<String> cmbBoxForma;

    @FXML
    private Button btnCreaGrafo;

    @FXML
    private TextField txtT1;

    @FXML
    private TextField txtAlfa;

    @FXML
    private Button btnSimula;

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	
    	Integer anno=Integer.parseInt(txtAnno.getText());
    	
    	String forma= this.cmbBoxForma.getValue();
    	if(forma==null) {
    		txtResult.appendText("devi selezionare una forma");
    		return;
    	}
    	
    	this.model.creaGrafo(anno, forma);
    	
    	txtResult.appendText("Grafo creato\n");
    	txtResult.appendText("#vertici: "+ this.model.nVertici()+"\n");
    	txtResult.appendText("#archi: "+ this.model.nArchi()+"\n");
    	
    	txtResult.appendText("Per ogni stato, peso adiacenti:\n");
    	
    	for(Adiacenze a: this.model.pesoVicini()) {
    		txtResult.appendText(a.toString()+"\n");
    	}
    	
    }

    @FXML
    void doSelezionaAnno(ActionEvent event) {
    	txtResult.clear();
    	String anno= txtAnno.getText();
    	int annoI=0;
    	
    	try {
    		annoI=Integer.parseInt(anno);
    	}catch(NumberFormatException e) {
    		txtResult.appendText("Devi inserire un anno");
    	}
    	if(annoI>=1910 && annoI<=2014) {
    		this.cmbBoxForma.getItems().addAll(this.model.getForme(annoI));
    	}else {
    		txtResult.appendText("Devi inserire un anno tra 1910 e 2014");
    	}
    	
    	
    }

    @FXML
    void doSimula(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
        assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
        assert btnSelezionaAnno != null : "fx:id=\"btnSelezionaAnno\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
        assert cmbBoxForma != null : "fx:id=\"cmbBoxForma\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
        assert txtT1 != null : "fx:id=\"txtT1\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
        assert txtAlfa != null : "fx:id=\"txtAlfa\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
		
	}
}
