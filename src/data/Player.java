/**
 * This class is used to play all the songs using MediaPlayer and MediaSound.
 * * It is a bridge between the controller and store class, that is only used
 * * by this class.
 */

package data;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;

import java.io.*;
import java.util.ArrayList;


public class Player {
    private MediaPlayer mediaPlayer;
    private Media sound;
    public boolean sounding;
    private ArrayList<PlayList> publicList;
    private ArrayList<PlayList> privateList;
    private int position;

    /**
     * Initialize Player constructor initializing publicList and privateList
     * publicList = An array of playlist with only one array of Songs with all the public songs, visibles in all the acounts
     * privateLists = An array of playlists with some playlists of private songs. Default one (privatelist) but the user can add more
     * position = 0 It allows the program knows the position of the array in all moment, to allows the user next and back songs in the playlists
     */
    public Player() {
        publicList = new ArrayList<>();
        privateList = new ArrayList<>();
        position = 0;
    }
    /**
     * This function read of the generalSoundsInfo.txt and privatesongsInfo.txt and playlists.txt all the playlists
     * to save in the privateList array and creating and adding to this playlists the songs by his corresponding name, description...
     * that is shown in the listView
     * @return an Arraylist of playlists of songs that allows to complete the listviewer at controller
     */
    public ArrayList<PlayList> mapLists() {
        String privateLists = ("app/users/" + User.getUsername() + "/userLists/userLists.txt");
        BufferedReader file = null;
        String line;
        publicList.clear();

        try  {
            file = new BufferedReader(new FileReader(privateLists));
            //If the file exists, read the document and add to the arraylist
            while((line = file.readLine()) != null) {
                if(line.equals("Public songs")) {
                    publicList.add(new PlayList(mapSongs("app/generalSounds/generalSoundsInfo.txt"), line));
                    privateList = publicList;
                }
                else {
                    if(line.equals("Private songs")) {
                        privateList.add(new PlayList(mapPrivateSongs("app/users/" + User.getUsername() + "/privateSongs/privateSongsInfo.txt"), line));
                    }
                    else {
                        privateList.add(new PlayList(mapPrivateSongs("app/users/" + User.getUsername() + "/UserLists/" + line + ".txt"), line));
                    }
                }
            }
        }
        catch (FileNotFoundException a) {
            Messages.exceptionMessage("File not found", a);
        }
        catch (IOException e) {
            Messages.exceptionMessage("IOException", e);
        }
        finally {
            try {
                if (file != null)
                    file.close();
            } catch (Exception e1) {
                Messages.exceptionMessage("Exception", e1);
            }
        }
        return privateList;
    }
    /**
     * This function is a complementary of the previous function to create and add the songs
     * @param path the path of there are the songs (public list or private lists)
     * @return an arraylists of songs
     */
    public ArrayList<Songs> mapSongs(String path) {
        BufferedReader file = null;
        String line;
        String[] content;
        ArrayList<Songs> publicSongs = new ArrayList<>();

        try  {
            file = new BufferedReader(new FileReader(path));
            //If the file exists, read the document and add to the arraylist
            while((line = file.readLine()) != null) {
                content = line.split(";");
                //Add to a list to return on controller to show the media
                publicSongs.add( new Songs(content[0], content[1], Double.parseDouble(content[2])));
            }
        }
        catch (FileNotFoundException o) {
            Messages.exceptionMessage("Error: File not found ", o);
        }
        catch (IOException e) {
            Messages.exceptionMessage("Error", e);
        }
        finally {
            try {
                if (file != null)
                    file.close();
            } catch (Exception e1) {
                Messages.exceptionMessage("Error", e1);
            }
        }
        return publicSongs;
    }
    //this function is a complementary of the previous function to create and add the private songs
    public ArrayList<Songs> mapPrivateSongs(String path) {
        BufferedReader file = null;
        String line;
        String[] content;
        ArrayList<Songs> privateSongs = new ArrayList<>();

        try  {
            file = new BufferedReader(new FileReader(path));
            //If the file exists, read the document and add to the arraylist
            while((line = file.readLine()) != null) {
                content = line.split(";");
                //Add to a list to return on controller to show the media
                privateSongs.add( new PrivateSongs(content[0], content[1], Double.parseDouble(content[2]), content[3], content[4]));
            }
        }
        catch (FileNotFoundException o) {
            Messages.exceptionMessage("Error: File not found ", o);
        }
        catch (IOException e) {
            Messages.exceptionMessage("Error", e);
        }
        finally {
            try {
                if (file != null)
                    file.close();
            } catch (Exception e1) {
                Messages.exceptionMessage("Error", e1);
            }
        }
        return privateSongs;
    }

    /**
     * Allows the user play a song adding it like a sound in Media Player and controllling play/pause button
     * @param newName a string with the text of the label
     * @param oldName a string with the name of the song selected
     * @param path the path to the div that contains the song (public div or private div)
     */
    public void playSong(String newName, String oldName, String path) {
        try {
            if(oldName == newName) {
                newName = path + newName + ".mp3";
                sound = new Media(new File(newName).toURI().toString());
                mediaPlayer = new MediaPlayer(sound);
            }
            playPause();
        } catch(MediaException a) { Messages.exceptionMessage("Error: media not found", a);}

    }

    /**
     * Control if the song is paused o playing, then if the user press the button the opposite happens
     */
    public void playPause() {
        if(sounding) {
            mediaPlayer.pause();
            sounding = false;
        }
        else {
            mediaPlayer.play();
            sounding = true;
        }
    }

    /**
     *Control the position of the array to next/previous the songs, using a double foreach to appeal
     *     //the array and select the next/previous song (depending if the function pass +1 or -1)
     * @param list the name of the current playlist
     * @param label the name of the song saved at label
     * @param path the path where the songs are going to be, different if is a public playlist o private
     * @param type an integer with +1 or -1 depending if is next (+1) or previous (-1) button
     * @return the song that corresponds (next or previous at list)
     */
    public String nextPrevious(String list, String label, String path, int type) {
        Songs song = null;
        int count = 0;

        for (PlayList p: publicList) {
            if (p.getTitle().equals(list)) {
                for (Songs s: p.getPublicList()) {
                    position = count;
                    if(s.getTitle().equals(label)) {
                        if (position + type < p.getPublicList().size() && position + type >= 0) {
                            song = p.getPublicList().get(position + type);
                            playSong(song.getTitle(), song.getTitle(), path);
                        }
                    }
                    count++;
                }
            }
        }
        return song.getTitle();
    }

    /**
     * Call to Store add song to copy and save the song added by the user
     * @param path a string with the path of the div taht conatins the song
     * @param song the song that the user want to add
     * @throws IOException
     */
    public void addSong(String path, PrivateSongs song) throws IOException {
        Store.addSong(path, song);
    }
    /**
     * Call to Store add song to create the lists by the user
     * @param list String with the name of the playlist
     */
    public void createList(String list) {
        Store.createList(list);
    }

    /**
     *Call Store to add song to a list if the user select a song of the private list
     */
    public void addSongToList(String songList,String newList, String songName) {
        boolean found = false;
        if(songList.equals("Public songs")) {
            Messages.errorMessage("You only can add songs from privateList");
        }
        else {
            for (PlayList p: publicList) {
                if(p.getTitle().equals(songList)) {
                    for (Songs s: p.getPublicList()) {
                        if(s.getTitle().equals(songName)) {
                            Store.addSongToList(newList, s);
                            found = true;
                        }
                    }
                }
            }
            if(!found) Messages.errorMessage("Error: playlist don't found");
        }
    }

}
