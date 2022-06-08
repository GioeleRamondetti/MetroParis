/**
 * Sample Skeleton for 'Metro.fxml' Controller Class
 */

package it.polito.tdp.metroparis;

import java.net.URL;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.metroparis.model.Fermata;
import it.polito.tdp.metroparis.model.Model;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;

public class MetroController {
	private Model model;
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;
    @FXML // fx:id="tablepercorso"
    private URL location;
    @FXML // fx:id="tablefermata"
    private TableView<Fermata> tablefermata; // Value injected by FXMLLoader
    @FXML // fx:id="colfermata"
    private TableColumn<Fermata, String> colfermata; // Value injected by FXMLLoader
    @FXML // fx:id="boxarrivo"
    private ComboBox<Fermata> boxarrivo; // Value injected by FXMLLoader

    @FXML // fx:id="boxpartenza"
    private ComboBox<Fermata> boxpartenza; // Value injected by FXMLLoader

    @FXML // fx:id="handlecerca"
    private Button handlecerca; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void cerca(ActionEvent event) {
    	txtResult.clear();
    	Fermata partenza=boxpartenza.getValue();
    	Fermata arrivo=boxarrivo.getValue();
    	if(partenza!=null && arrivo!=null && !partenza.equals(arrivo)) {
    		List<Fermata> percorso= model.calcolaPercorso(partenza, arrivo);
    		tablefermata.setItems(FXCollections.observableArrayList(percorso));
    		txtResult.setText("percorso trovato con "+percorso.size()+" stazioni");
    		// non accetta list ma solo observable list
    	}else {
    		txtResult.setText("selezionare partenza e arrivo diverse e non nulle");
    	}
    }

    @FXML
    void partenca(ActionEvent event) {
    	/*
    	txtResult.clear();
    	Fermata partenza=boxpartenza.getValue();
    	Fermata arrivo=boxarrivo.getValue();
    	if(partenza!=null && arrivo!=null && !partenza.equals(arrivo)) {
    		List<Fermata> percorso= model.calcolaPercorso(partenza, arrivo);
    		txtResult.setText(percorso.toString());
    	}else {
    		txtResult.setText("selezionare partenza e arrivo diverse e non nulle");
    	}
    	*/
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxarrivo != null : "fx:id=\"boxarrivo\" was not injected: check your FXML file 'Metro.fxml'.";
        assert boxpartenza != null : "fx:id=\"boxpartenza\" was not injected: check your FXML file 'Metro.fxml'.";
        assert handlecerca != null : "fx:id=\"handlecerca\" was not injected: check your FXML file 'Metro.fxml'.";
        assert colfermata != null : "fx:id=\"colfermata\" was not injected: check your FXML file 'Metro.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Metro.fxml'.";
        colfermata.setCellValueFactory(new PropertyValueFactory<Fermata,String>("nome"));// devo farlo per ogni colonna
        // sempre qui
    }
    public void setModel(Model model) {
    	this.model=model;
    	List<Fermata> fermate=this.model.getFermata();
    	boxpartenza.getItems().addAll(fermate);
    	boxarrivo.getItems().addAll(fermate);
    }

}

