/**
 * This function control all the user functions. It have a constructor of user object
 * * (only can be one and Admin is default) But here is other function like check if the
 * * password is correct, if the name of the user is available and here
 * * can create an usser too (if the username is unavailable and the user click ok)
 */
package data;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;

public class User {

    /**
     * All the functions an constructor to create and make a user
     * @param username with a string of a unic name of an user
     * @param password with a string with a password for the user, it can't be null
     * @param available a boolean to know if the username is available
     * @param dirList an arrayList of strings with a list of the content of a dir, in this case user names
     * @param dir a path used to save the dir path
     */
    private static String username;
    private String password;
    private boolean available;
    private ArrayList<String> dirList;
    private Path dir;
    //initialize the constructor with "Admin" user like default
    public User() {
        this.username = "Admin";
        this.password = "1234";
    }

    public User(String username, String password, ArrayList<String> dirList) {
        this.username = username;
        this.password = password;
        this.dirList = dirList;
    }

    /**
     * Create an ArrayList with all User > directories names to check if the user exists
     */
    public boolean usernameAvailable(String username) {
        dir = Path.of("app/users");
        dirList = new ArrayList<>();

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path d: stream) {
                dirList.add(d.toString().toLowerCase());
            }
            if (dirList.contains("app\\users\\" + username)) available = true;
            else available = false;

        } catch (IOException | DirectoryIteratorException e) {
            System.err.println(e);
        }
        return available;
    }

    /**
     * THis function chek if the password introduced by the user is correct
     * @param username The username
     * @param password the password introduced by the username
     * @return a boolean false if the password is incorrect and true if it is correct
     */
    public void checkPassword(String username, String password) {
        String line;
        String[] content;
        BufferedReader file = null;

        if(usernameAvailable(username)) {
            //Check password
            System.out.println("username available");
            try  {
                file = new BufferedReader(new FileReader("app/users/" + username + "/" + "userInfo.txt"));
                //If the file exists, read the document to check password
                while((line = file.readLine()) != null) {
                    content = line.split(";");

                    if(content[1].equals(password)) {
                        setUsername(username);
                        setPassword(password);
                    }
                    else {
                        Messages.errorMessage("The password is incorrect");
                    }
                }
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
        }
        else {
            if(Messages.confirm("User doesn't exists", "The user you write doesn't exists, do you want to create this user with this password?")) {
                createUser(username, password);
                setUsername(username);
                setPassword(password);
            }
        }
    }

    /**
     * Create a directory with userinfo, userLists directory and privateSounds directory
     */
    public void createUser(String username, String password) {

        if(!usernameAvailable(username)) {
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
                Files.createDirectories(Paths.get("app/users/" + username));
                Files.createDirectories(Paths.get("app/users/" + username + "/privateSongs"));
                Files.createDirectories(Paths.get("app/users/" + username + "/userLists"));
                //create files
                Files.createFile((new File("app/users/" + username + "/privateSongs/privateSongsInfo.txt")).toPath());
                Files.createFile((new File("app/users/" + username + "/userLists/userLists.txt")).toPath());
                try (PrintWriter pw = new PrintWriter("app/users/" + username + "/" + "userInfo.txt")) {
                    pw.println(username + ";" + password);
                } catch (FileNotFoundException e) { e.printStackTrace(); }
                try (PrintWriter pd = new PrintWriter(new FileWriter("app/users/" + username + "/userLists/userLists.txt"))) {
                    pd.println("Public songs");
                    pd.println("Private songs");
                } catch (FileNotFoundException e) { e.printStackTrace(); }
            } catch (IOException | DirectoryIteratorException e) {
                Messages.exceptionMessage("Error: we can't create you user", e);
            }
        }
        else Messages.errorMessage("Error: The username already exists");
    }

    public static String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
