/**
 * 2play is a java app to manage, save and reproduce songs. The user must have an account to access the app, if he doesn't
 * have one the user can create an account signing up chosen an available username and a password. Already in
 * the app the user can create a playlist and manage it by uploading songs, adding songs, showing all the songs and playing it,
 * selecting it from the list viewers or searching it by name.When a playlist is playing, the user can next and back the
 * songs or choose another playlist to listen.
 * @author Andrea Pérez Azorín
 * @contact azorin.andrea.design@gmail.com
 * @version 1.2
 * @see https://github.com/azorindesign/2play
 * @since 21/05/2021
 */

package play;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    //start the fxml to start the application, setting the title "2play" and given the frontend.fxml path
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/frontend/frontend.fxml"));
        primaryStage.setTitle("2PLAY");
        primaryStage.setScene(new Scene(root, 768, 598));
        primaryStage.show();
    }
}