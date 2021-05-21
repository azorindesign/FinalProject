/**
 * Private songs is a class to have all the information about song object(title, author and duration).
 *  * It only have the constructors, getters, setters of all the variables and
 *  * a tostring method
 */
package data;
import javafx.scene.media.Media;

public class Songs {
    protected String title;
    protected String author;
    protected double duration;

    public Songs(String author, String title, double duration) {
        this.title = title;
        this.author = author;
        this.duration = duration;
    }

    public Songs() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return title + " | " + author + " | " + duration;
    }
}
