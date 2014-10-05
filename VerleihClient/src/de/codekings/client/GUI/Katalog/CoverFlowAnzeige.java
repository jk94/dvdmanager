/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codekings.client.GUI.Katalog;

/**
 *
 * @author Jan
 */

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollBar;
import javafx.scene.effect.PerspectiveTransform;
import javafx.scene.effect.ReflectionBuilder;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * A display shelf of images using the PerspectiveTransform effect.
 *
 * @see javafx.scene.effect.PerspectiveTransform
 * @see javafx.scene.effect.Reflection
 * @see javafx.scene.control.ScrollBar
 * @see javafx.scene.input.MouseEvent
 * @see javafx.scene.input.KeyEvent
 * @resource cover1.jpg
 * @resource cover2.jpg
 * @resource cover3.jpg
 * @resource cover4.jpg
 */
public class CoverFlowAnzeige extends Application {
    //private static final double WIDTH = 695, HEIGHT = 600;
    private static final double  WIDTH = 890, HEIGHT = 768;
    private void init(Stage primaryStage) {
        Group root = new Group();
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, WIDTH,HEIGHT));
         // load images
        Image[] images = new Image[4];
        for (int i = 0; i < 4; i++) {
            images[i] = new Image( CoverFlowAnzeige.class.getResource("cover"+(i+1)+".png").toExternalForm(),false);
        }
        // create display shelf
        CoverFlow displayShelf = new CoverFlow(images);
        displayShelf.setPrefSize(WIDTH, HEIGHT);
        root.getChildren().add(displayShelf);
        
    }

    @Override public void start(Stage primaryStage) throws Exception {
        init(primaryStage);
        primaryStage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX 
     * application. main() serves only as fallback in case the 
     * application can not be launched through deployment artifacts,
     * e.g., in IDEs with limited FX support. NetBeans ignores main().
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
