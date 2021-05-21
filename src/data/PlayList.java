/**
 * This class have all the atributtes to create a playlist object
 * @param title a string with the title of the playlist
 * @param publicList an arraylist of songs with songs
 * @param privateList an arraylist of playlist of songs
 */
package data;

import java.util.ArrayList;

public class PlayList {

    private String title;
    private ArrayList<Songs> publicList;
    private ArrayList<PrivateSongs> privateList;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Songs> getPublicList() {
        return publicList;
    }

    public void setPublicList(ArrayList<Songs> publicList) {
        this.publicList = publicList;
    }

    public PlayList(ArrayList<Songs> playListOfSongs, String title) {
        this.publicList = playListOfSongs;
        this.title = title;
    }

    public ArrayList<PrivateSongs> getPrivateList() {
        return privateList;
    }

    public void setPrivateList(ArrayList<PrivateSongs> privateList) {
        this.privateList = privateList;
    }

    public PlayList() {
    }

    @Override
    public String toString() {
        return title;
    }
}
