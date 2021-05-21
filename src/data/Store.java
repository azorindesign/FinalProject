/**
 * This class allow the program to store the information about lists and songs. It have
 * * static functions but is only relationship to Player
 */

package data;

import javafx.scene.control.Alert;

import java.io.*;
import java.nio.file.*;

public class Store {

    /**
     * To create a list create first the file with the list name and then print on the userLists.txt
     * the name of the new list added
     * @param listTitle a string with the name of the new list added
     */
    public static void createList(String listTitle) {
        //Create a file with the name of the list to save the songs here
        Path dir=Path.of("app/users/" + User.getUsername() + "/userLists/");
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            Files.createFile((new File("app/users/" + User.getUsername() + "/userLists/" + listTitle + ".txt")).toPath());
        } catch (IOException | DirectoryIteratorException e) {
            Messages.exceptionMessage("Error: This song already exists", e);
        }
        //write the name of the list on general file that saves all the lists
        try (PrintWriter pw = new PrintWriter(new FileWriter("app/users/" + User.getUsername() + "/userLists/userLists.txt", true))) {
            pw.println(listTitle);
        } catch (FileNotFoundException e) { Messages.exceptionMessage("Error: file not found", e); }
        catch(IOException e) {Messages.exceptionMessage("Error: This list already exists", e); }
    }

    /**
     * To save a song first copy the song from the path provided by the user to the privateSongs div and
     * then save his title, duration... and all privateSong information at privateSongsInfo.txt
     */
    public static void addSong(String path, PrivateSongs song) throws IOException {
        File source = new File(path);
        File dest = new File("app/users/" + User.getUsername() + "/privateSongs/" + song.getTitle() + ".mp3");
        boolean notFound = false;
        //Copy the song
        try {
            Files.copy(source.toPath(), dest.toPath());
        } catch(NoSuchFileException e) { Messages.exceptionMessage("Error: song not found", e); notFound=true;}
        catch(IOException e) {Messages.exceptionMessage("Error: This list already exists", e);notFound=true;}

        //Add this song to the privateSongsInfo.txt
        if(!notFound) {
            try (PrintWriter pw = new PrintWriter(new FileWriter("app/users/" + User.getUsername() + "/privateSongs/privateSongsInfo.txt", true))) {
                pw.println(song.getAuthor()+ ";" + song.getTitle() + ";" + song.getDuration() + ";" + User.getUsername() + ";" + song.getDescription());
            } catch (FileNotFoundException e) { Messages.exceptionMessage("Error: file not found", e); }
            catch(IOException e) {Messages.exceptionMessage("Error: This song already exists", e);}
        }
    }

    /**
     * To add a song to a list only have to add all the information of this song (filtered at the previous
     *     // call function in player) in the corresponding playlist.txt file
     */
    public static void addSongToList(String list, Songs s) {
        File file = new File("app/users/" + User.getUsername() + "/userLists/" + list + ".txt");
        if(file.exists()) {
            try (PrintWriter pw = new PrintWriter(new FileWriter("app/users/" + User.getUsername() + "/userLists/" + list + ".txt", true))) {
                pw.println(s.getAuthor() + ";" + s.getTitle() + ";" + s.getDuration() + ";" + User.getUsername() + ";" + " ");
            } catch (FileNotFoundException e) {
                Messages.exceptionMessage("Error: file not found", e);
            } catch (IOException e) {
                Messages.exceptionMessage("Error: This list already exists", e);
            }
        } else Messages.errorMessage("List not found");

    }
}
