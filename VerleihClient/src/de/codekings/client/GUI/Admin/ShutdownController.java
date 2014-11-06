/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.codekings.client.GUI.Admin;

import de.codekings.client.Controls.ContentControl;
import de.codekings.client.Controls.Control;
import de.codekings.client.Enum.ContentPageType;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Simon
 */
public class ShutdownController implements Initializable {
    @FXML
    private Pane admin_shutdown_pane;
    @FXML
    private Button admin_destruction;
    @FXML
    private Label admin_shutdown_label;
    @FXML
    private Label admin_shutdown_text;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        admin_destruction.setOnMouseClicked((MouseEvent event) -> {
            Platform.exit();
            
        });
    }    
    
}
