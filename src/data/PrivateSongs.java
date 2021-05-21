/**
 * Private songs is a son of songs to have more detailed information that the user
 * * have to give, the description. And it save the user that save this song too
 * * It only have the constructors, getters, setters of all the variables and
 * * a tostring method
 */

package data;

public class PrivateSongs extends Songs {
    private String username;
    private String description;

    public PrivateSongs( String author, String title, double duration, String username,
                         String description) {
        super(author, title, duration);
        this.username = username;
        this.description = description;
    }

    public PrivateSongs(String title, String author, double duration) {
        super(title, author, duration);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return super.toString() + "|" + username + " | " + description;
    }
}
