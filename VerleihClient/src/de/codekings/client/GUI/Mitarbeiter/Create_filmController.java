/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codekings.client.GUI.Mitarbeiter;

import de.codekings.client.Controls.Control;
import de.codekings.client.Controls.DataManager;
import de.codekings.client.connection.ClientThread;
import de.codekings.client.connection.MessageReturn;
import de.codekings.client.datacontent.Film_Client;
import de.codekings.common.Connection.Message;
import de.codekings.common.config.ConfigManager;
import de.codekings.common.datacontents.Cover;
import de.codekings.common.datacontents.Film;
import de.codekings.common.datacontents.Genre;
import de.codekings.common.datacontents.Sendable;
import de.codekings.common.json.JSON_Parser;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import se.mbaeumer.fxmessagebox.MessageBox;
import se.mbaeumer.fxmessagebox.MessageBoxResult;
import se.mbaeumer.fxmessagebox.MessageBoxType;

/**
 * FXML Controller class
 *
 * @author Simon
 */
public class Create_filmController implements Initializable, MessageReturn {

    @FXML
    private AnchorPane create_anchor;
    @FXML
    private TextField txf_titel;
    @FXML
    private TextArea txf_beschreibung;
    @FXML
    private RadioButton rbtn_fsk0;
    @FXML
    private ToggleGroup fskbuttons;
    @FXML
    private RadioButton rbtn_fsk6;
    @FXML
    private RadioButton rbtn_fsk12;
    @FXML
    private RadioButton rbtn_fsk16;
    @FXML
    private RadioButton rbtn_fsk18;
    @FXML
    private TextField txf_trailer;
    @FXML
    private TextField txf_schauspieler;
    @FXML
    private TextField txf_regisseur;
    @FXML
    private TextField txf_subtitel;
    @FXML
    private ComboBox<String> cb_genre;
    @FXML
    private ListView<String> li_genre_added;
    @FXML
    private Button btn_genre_add;
    @FXML
    private Button btn_genre_remove;
    @FXML
    private ImageView img_cover;
    @FXML
    private TableView<Film_Client> tbvw_filme;
    @FXML
    private Button btn_create;
    @FXML
    private Button btn_reset;
    @FXML
    private Button btn_remove;
    @FXML
    private ComboBox<Integer> cb_rating;

    private ObservableList<Film_Client> filmdata;

    private int selectedFilmID = -1;
    @FXML
    private Button btn_update;
    @FXML
    private TextField txf_laufzeit;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ladeTableView();
        ladeGenreList();
        ladeRatingView();

        btn_create.setOnMouseClicked((MouseEvent event) -> {
            Film film = generateFilm();
            //Cover_Client cc = new Cover_Client(film.getFILMID(), img_cover.getImage());
            //Cover c = new Cover(film.getFILMID(), cc.getCover());
            
            Cover c = new Cover(film.getFILMID(), JSON_Parser.encodeToString(SwingFXUtils.fromFXImage(img_cover.getImage(), null), "JPG"));
            
            Message m = new Message("addFilm");
            m.addSendable(film);
            m.addSendable(c);
            m.addAdditionalParameter("email", Control.getControl().getSession().getEmail());
            
            ConfigManager cfgManager = Control.getControl().getCfgManager();
            String host = cfgManager.getConfigs().getProperty("ip");
            int port = Integer.parseInt(cfgManager.getConfigs().getProperty("port"));

            ClientThread ct = new ClientThread(this, host, port);
            //ct.requestToServer(m);
            
            MessageBox mb = new MessageBox("Film hinzugefügt", MessageBoxType.OK_ONLY);
            mb.setTitle("Information");
            mb.showAndWait();
            
        });

        btn_reset.setOnMouseClicked((MouseEvent event) -> {
            resetValues();
        });

        btn_genre_add.setOnMouseClicked((MouseEvent event) -> {
            ArrayList<Genre> genres = (ArrayList<Genre>) Control.getControl().getDataManager().getGenres().clone();
            String genreinput = cb_genre.getValue();
            boolean vorhanden = false;
            for (Genre genre : genres) {
                if (genre.getGenrebezeichnung().equals(genreinput)) {
                    vorhanden = true;
                    break;
                }
            }
            if (!vorhanden) {
                //Genre dem Server hinzufügen!!
                ConfigManager cfgManager = Control.getControl().getCfgManager();
                String host = cfgManager.getConfigs().getProperty("ip");
                int port = Integer.parseInt(cfgManager.getConfigs().getProperty("port"));

                ClientThread ct = new ClientThread(this, host, port);

                Message m = new Message("addGenre");
                m.addAdditionalParameter("bez", genreinput);

                ct.requestToServer(m);
                //Genre dem Server hinzugefügt!!

                cb_genre.getItems().add(genreinput);
            }
            li_genre_added.getItems().add(genreinput);
            cb_genre.getItems().remove(genreinput);

        });

        btn_genre_remove.setOnMouseClicked((MouseEvent event) -> {
            int index = li_genre_added.getSelectionModel().getSelectedIndex();
            cb_genre.getItems().add(li_genre_added.getItems().get(index));
            Collections.sort(cb_genre.getItems());
            li_genre_added.getItems().remove(index);
        });

        tbvw_filme.setOnMouseClicked((MouseEvent event) -> {
            int klickcount = event.getClickCount();

            if (klickcount == 2) {
                resetValues();
                Film_Client fc = tbvw_filme.getSelectionModel().getSelectedItem();
                selectedFilmID = fc.getFILMID();
                txf_beschreibung.setText(fc.getS_description());
                txf_regisseur.setText(fc.getS_regie());
                txf_subtitel.setText(fc.getS_subtitel());
                txf_titel.setText(fc.getS_titel());
                txf_trailer.setText(fc.getS_trailer());
                txf_laufzeit.setText(String.valueOf(fc.getI_duration()));
                cb_rating.setValue(fc.getI_rating());
                for (Genre g : fc.getGenres()) {
                    cb_genre.getItems().remove(g.getGenrebezeichnung());
                    li_genre_added.getItems().add(g.getGenrebezeichnung());
                }
                String ausgabe = "";
                for (int i = 0; i < fc.getActors().size(); i++) {
                    if (i < fc.getActors().size() - 1) {
                        ausgabe = ausgabe + fc.getActor(i) + "; ";
                    } else {
                        ausgabe = ausgabe + fc.getActor(i);
                    }
                }
                txf_schauspieler.setText(ausgabe);
                img_cover.setImage(fc.getCover().gibCoverImage());
                switch (fc.getI_fsk()) {
                    case 0:
                        fskbuttons.selectToggle(rbtn_fsk0);
                        break;
                    case 6:
                        fskbuttons.selectToggle(rbtn_fsk6);
                        break;
                    case 12:
                        fskbuttons.selectToggle(rbtn_fsk12);
                        break;
                    case 16:
                        fskbuttons.selectToggle(rbtn_fsk16);
                        break;
                    case 18:
                        fskbuttons.selectToggle(rbtn_fsk18);
                        break;
                    default:
                        fskbuttons.selectToggle(rbtn_fsk0);
                }
            }
        });

        img_cover.setOnMouseClicked((MouseEvent event) -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Wählen Sie ein Cover");
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JPG", "*.jpg"));
            File file = fileChooser.showOpenDialog(new Stage());
            if (file != null) {
                long size = file.length();
                if (size <= 122880) {
                    try {
                        BufferedImage img = ImageIO.read(file);
                        Cover c = new Cover(selectedFilmID, JSON_Parser.encodeToString(img, "JPG"));
                        img_cover.setImage(c.gibCoverImage());
                    } catch (IOException ex) {
                        MessageBox mb = new MessageBox("Fehler beim Laden des Bildes", MessageBoxType.OK_ONLY);
                        mb.setAlwaysOnTop(true);
                        mb.setTitle("Fehler!");
                        mb.setResizable(false);
                        mb.showAndWait();
                    }
                } else {
                    MessageBox mb = new MessageBox("Das Bild darf höchstens 120kb groß sein.", MessageBoxType.OK_ONLY);
                    mb.setAlwaysOnTop(true);
                    mb.setTitle("Fehler!");
                    mb.setResizable(false);
                    mb.showAndWait();
                }
            }
        });

        btn_remove.setOnMouseClicked((MouseEvent event) -> {
            if (tbvw_filme.getSelectionModel().getSelectedIndices().size() > 0) {
                int selectedIndex = tbvw_filme.getSelectionModel().getSelectedIndex();
                MessageBox mb = new MessageBox("Wenn Sie den Film löschen, kann auf\ndiesen nicht mehr zugegriffen werden.\n"
                        + "Das Cover wird vom Server gelöscht.\n\nMöchten Sie fortfahren?", MessageBoxType.YES_NO);
                mb.setAlwaysOnTop(true);
                mb.setTitle("Achtung!");
                mb.setResizable(false);
                mb.setWidth(225.0);
                mb.setHeight(175.0);
                mb.showAndWait();
                if (mb.getMessageBoxResult() == MessageBoxResult.YES) {
                    Message m = new Message("removeFilm");
                    m.addAdditionalParameter("FILM_ID", String.valueOf(tbvw_filme.getItems().get(selectedIndex).getFILMID()));

                    ConfigManager cfgManager = Control.getControl().getCfgManager();
                    String host = cfgManager.getConfigs().getProperty("ip");
                    int port = Integer.parseInt(cfgManager.getConfigs().getProperty("port"));

                    ClientThread ct = new ClientThread(this, host, port);
                    ct.requestToServer(m);

                    DataManager dm = Control.getControl().getDataManager();
                    dm.removeFilm(tbvw_filme.getItems().get(selectedIndex).getFILMID());

                    tbvw_filme.getItems().remove(selectedIndex);
                }
                tbvw_filme.getItems().remove(selectedIndex);
            } else {
                MessageBox mb = new MessageBox("Kein Film aus der Liste zum Löschen ausgewählt", MessageBoxType.OK_ONLY);
                mb.setAlwaysOnTop(true);
                mb.setTitle("Fehler!");
                mb.setResizable(false);
                mb.showAndWait();
            }
        });

    }

    @Override
    public void returnedMessage(Message m) {
        if (m.getCommand().equalsIgnoreCase("addedGenre")) {
            for (Sendable s : m.getContent()) {
                if (s instanceof Genre) {
                    Genre g = (Genre) s;
                    Control.getControl().getDataManager().getGenres().add(g);
                }
            }
        }
        if (m.getCommand().equalsIgnoreCase("GenreExists")) {
            ArrayList<Genre> genres = (ArrayList<Genre>) Control.getControl().getDataManager().getGenres().clone();
            Genre g = new Genre();
            for (Sendable s : m.getContent()) {
                if (s instanceof Genre) {
                    g = (Genre) s;
                }
            }
            for (Genre genre : genres) {
                if (genre.getGenrebezeichnung().equalsIgnoreCase(g.getGenrebezeichnung())) {
                    if (genre.getGenre_id() != g.getGenre_id()) {
                        Control.getControl().getDataManager().updateGenres();
                    }
                    break;
                }
            }
        }
    }

    private void ladeGenreList() {
        cb_genre.getItems().clear();
        ArrayList<Genre> genres = (ArrayList<Genre>) Control.getControl().getDataManager().getGenres().clone();
        Collections.sort(genres);
        for (Genre g : genres) {
            cb_genre.getItems().add(g.getGenrebezeichnung());
        }
    }

    private void ladeTableView() {
        filmdata = FXCollections.observableArrayList();
        ArrayList<Film_Client> filme = (ArrayList<Film_Client>) Control.getControl().getDataManager().getFilme().clone();
        for (Film_Client fc : filme) {
            filmdata.add(fc);
        }

        String columnname[] = {"Titel", "Subtitel"};
        String variablename[] = {"s_titel", "s_subtitel"};

        TableColumn<Film_Client, String> titel = new TableColumn<>(columnname[0]);
        TableColumn<Film_Client, String> subtitel = new TableColumn<>(columnname[1]);
        titel.setCellValueFactory(new PropertyValueFactory<Film_Client, String>(variablename[0]));
        subtitel.setCellValueFactory(new PropertyValueFactory<Film_Client, String>(variablename[1]));

        tbvw_filme.getColumns().clear();
        tbvw_filme.getColumns().add(titel);
        tbvw_filme.getColumns().add(subtitel);
        tbvw_filme.getItems().addAll(filmdata);
    }

    private void resetValues() {
        txf_beschreibung.setText("");
        txf_regisseur.setText("");
        txf_schauspieler.setText("");
        txf_subtitel.setText("");
        txf_titel.setText("");
        txf_trailer.setText("");
        cb_genre.setValue("");
        ladeGenreList();
        li_genre_added.getItems().clear();
        selectedFilmID = -1;
        txf_laufzeit.setText("");
        cb_rating.setValue(0);
        img_cover.setImage(new Image(Create_filmController.class.getClassLoader().getResourceAsStream("de/codekings/client/GUI/Elements/NoCover.png")));
    }

    private Film generateFilm() {
        String beschreibung = txf_beschreibung.getText();
        String regie = txf_regisseur.getText();
        String actor = txf_schauspieler.getText();
        String sub = txf_subtitel.getText();
        String titel = txf_titel.getText();
        String trailer = txf_trailer.getText();
        
        Film f = new Film(selectedFilmID);
        f.setS_description(beschreibung);
        f.setS_regie(regie);
        f.setS_subtitel(sub);
        f.setS_titel(titel);
        f.setS_trailer(trailer);
        for (String a : actor.split(";")) {
            f.addActor(a.trim());
        }
        f.setI_rating(cb_rating.getSelectionModel().getSelectedItem());

        ArrayList<Genre> localgenre = (ArrayList<Genre>) Control.getControl().getDataManager().getGenres().clone();
        for (String gbez : li_genre_added.getItems()) {
            Genre addGenre = null;
            for (Genre g : localgenre) {
                if (g.getGenrebezeichnung().equals(gbez)) {
                    addGenre = g;
                    break;
                }
            }
            if (addGenre != null) {
                f.addGenre(addGenre);
            }
        }
        f.setI_duration(Integer.parseInt(txf_laufzeit.getText()));
        int fsk = fskbuttons.getToggles().indexOf(fskbuttons.getSelectedToggle());
        f.setI_fsk(fsk + 1);
        ToggleButton tb = (ToggleButton) fskbuttons.getSelectedToggle();
        f.setS_FSK(tb.getText());

        return f;
    }

    private void ladeRatingView() {
        cb_rating.getItems().clear();
        for (int i = 0; i <= 100; i++) {
            cb_rating.getItems().add(i);
        }
    }

}
