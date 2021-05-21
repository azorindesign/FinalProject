/**
 * Controller is the class where manage the relationship between the fxml and java.
 * Here we create differents fxml objects with EventListeners and other event
 * to relate the buttons, labels... of the application
 */

package frontend;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import data.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaException;

public class Controller implements Initializable {

    @FXML
    private TextField txtList;
    @FXML
    private Button btnGoList;
    @FXML
    private ChoiceBox<String> cmbList;
    @FXML
    private TextField txtSong;
    @FXML
    private TextField txtListSongs;
    @FXML
    private Button btnGoSongs;
    @FXML
    private Button btnLogIn;
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;
    @FXML
    private TextField txtSearch;
    @FXML
    public static Button btnPlay;
    @FXML
    private ImageView imgPlay;
    @FXML
    private ImageView imgNext;
    @FXML
    private ImageView imgPrevious;
    @FXML
    private Label lblPLaying;
    @FXML
    private Button btnDelete;
    @FXML
    private Label lblUsername;
    @FXML
    private ListView<PlayList> dgvLists;
    @FXML
    private ListView<Songs> dgvSongs;
    @FXML
    private TextField txtName;
    @FXML
    private Button btnAdd;
    @FXML
    private TextField txtAuthor;
    @FXML
    private TextField txtDuration;
    @FXML
    private TextField txtDescription;
    @FXML
    private TextField txtPath;

    private ObservableList<String> list;
    Player p = new Player();
    User u = new User();

    /**
     * Call initializeAll. It starts the application
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeAll();
    }

    /**
     * This is the function that initialize all the items of the applications. every time we want to refresh the applications
     * to show changes we have to call it
     */
    public void initializeAll() {
        lblPLaying.setText("Sound playing...");
        lblUsername.setText("Login with: " + User.getUsername());
        mapLists();
        loadSongs();
        completeSongs();
    }

    /**
     * This function allows to loadSongs in the ListView of songs
     */
    public void loadSongs() {
        dgvLists.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<PlayList>()
                {
                    @Override
                    public void changed(ObservableValue<? extends PlayList> observable, PlayList oldValue, PlayList newValue) {
                        if (newValue != null)
                        {
                            mapSongs(newValue.getPublicList());
                        }
                    }
                }
        );
    }

    /**
     * This functions allows that when the user select one item of the listview the fields choosen will been initialized
     */
    public void completeSongs() {
        dgvSongs.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Songs>()
                {
                    @Override
                    public void changed(ObservableValue<? extends Songs> observable, Songs oldValue, Songs newValue) {
                        if (newValue != null)
                        {
                            txtSearch.setText(newValue.getTitle());
                            txtSong.setText(newValue.getTitle());
                        }
                    }
                }
        );
    }

    /**
     * This function allows to load all  in the ListView of songs
     * @param songs of the songs that can pbe played
     */
    public void mapSongs(ArrayList<Songs> songs) {
        ObservableList<Songs> listSongs;
        listSongs = FXCollections.observableArrayList(songs);
        dgvSongs.setItems(listSongs);
    }
    /**
     * This function allows to load all the list names of the array in the ListView of lists
     */
    public void mapLists() {
        ObservableList<PlayList> playList;
        playList = FXCollections.observableArrayList(p.mapLists());
        dgvLists.setItems(playList);
    }

    /**
     *This function allows to play/pause the song selected or searched by the field
     */
    public void play(MouseEvent mouseEvent) {
       String song = txtSearch.getText();
       PlayList playlist = dgvLists.getSelectionModel().getSelectedItem();
       int position = dgvSongs.getSelectionModel().getSelectedIndex();
       try {
           if (checkPlaylist(playlist)) {
               lblPLaying.setText(song);
               p.playSong(song, lblPLaying.getText(), "app/generalSounds/");
           } else {
               lblPLaying.setText(song);
               p.playSong(song, lblPLaying.getText(), "app/users/" + User.getUsername() + "/privateSongs/");
           }
       } catch (MediaException e) {
           Messages.exceptionMessage("Error, media not found", e);
       } catch (NullPointerException n) {
           Messages.exceptionMessage("Error, unselected song", n);
       }
    }

    /**
     *This function allows the user add private songs introducing the name, duration, author and path of the div that containing the song
     * It throws IOException because some function calls can be do error
     */
    public void addSong(ActionEvent actionEvent) throws IOException {
        try {
            String path = txtPath.getText();
            String name = txtName.getText();
            Double duration = Double.parseDouble(txtDuration.getText());
            String author = txtAuthor.getText();
            String description = txtDescription.getText();
            PrivateSongs song;
            if (path != "" && name != "" && duration != null && author != "" && description != "") {
                song = new PrivateSongs(name, author, duration, User.getUsername(), description);
                p.addSong(path + "/" + name + ".mp3", song);
            }
            else Messages.errorMessage("Error: You have to complete all the fields");
        }
        catch(NumberFormatException e2) { Messages.exceptionMessage("Error: you have to introduce a double in duration", e2); }

        initializeAll();
    }

    /**
     * This function return a boolean to verify if the playList is a public playlist or other (private lists)
     * @param playlist that allows a playlist of playlists of songs
     * @return boolean that if is true is "Public song" playlist
     */
    public boolean checkPlaylist(PlayList playlist) {
        playlist = dgvLists.getSelectionModel().getSelectedItem();
        boolean check = false;
        if((playlist.getTitle()).equals("Public songs")) check = true;
        else check = false;
        return check;
    }
    //This functions allows to add songs to a list (the song only can be from the private list)

    /**
     *
     * @param actionEvent
     */
    public void addSongToLIst(ActionEvent actionEvent) {
        String newList;
        String songName;
        String songList;
        newList = txtListSongs.getText();
        try {
            songList = dgvLists.getSelectionModel().getSelectedItem().getTitle();
            songName = txtSong.getText();
            p.addSongToList(songList, newList, songName);
            initializeAll();
        }
        catch(NullPointerException e) { Messages.exceptionMessage("Error: please select the list that song come from", e);}
    }

    /**
     * This functions allows the user to next/previous the songs of a list
     * @param type an integer that if is previous -1 and if is next is +1
     */
    public void changeSong(int type) {
        String path;
        String songName;
        PlayList list = dgvLists.getSelectionModel().getSelectedItem();

        if (checkPlaylist(list)) path = "app/generalSounds/";
        else path = "app/users/" + User.getUsername() + "/privateSongs/";
        songName = p.nextPrevious((list.getTitle()), lblPLaying.getText(), path, type);
        txtSearch.setText(songName);
        lblPLaying.setText(songName);
    }
    /**
     *This function return +1 to knows that is the next button, then the program has to charge the next song in the array
     */
    public void next(MouseEvent mouseEvent) { changeSong(+1); }

    /**
     *This function return +1 to knows that is the previous button then the program has to charge the previous song in the array
     */
    public void previous(MouseEvent mouseEvent) {
        changeSong(-1);
    }

    /**
     * this function check if the login exists, then if the password is correct, then log in. If the user
     * not exists, the user can create it clicking OK on the confirm pop-up
     */
    public void checkLogIn(ActionEvent actionEvent) {
        String username;
        String password;
        if (txtUsername.getText() != "" && txtPassword.getText() != "") {
            username = txtUsername.getText();
            password = txtPassword.getText();
            u.checkPassword(username, password);
        } else Messages.errorMessage("Error: Please logIn with your username and password");
        initializeAll();
    }

    /**
     * This function allows create differents lists
     */
    public void addList(ActionEvent actionEvent) {
        p.createList(txtList.getText());
        initializeAll();
    }

}
