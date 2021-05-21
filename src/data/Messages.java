/**
 * This class are created only to have static functions to show the user error messages
 * * and confirm messages, that can be called in all other classes and allows not repeated code
 */

package data;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class Messages {
    private static Alert dialog;

    /**
     * Error message that show in content the error message given by java
     * @param error string with the text to put
     * @param exception the exception to get message
     */
    public static void exceptionMessage(String error, Exception exception) {
        dialog = new Alert(Alert.AlertType.ERROR);
        dialog.setTitle("Error");
        dialog.setHeaderText(error);
        dialog.setContentText("Error: " + exception.getMessage());
        dialog.showAndWait();
    }

    /**
     * a simple error message, the program has to give the text to output
     * @param error string with the text to put
     */
    public static void errorMessage(String error) {
        dialog = new Alert(Alert.AlertType.ERROR);
        dialog.setTitle("Error");
        dialog.setHeaderText(error);
        dialog.showAndWait();
    }
    /**
     * A simple confirm message, the program has to give the text to output
     * @param title The title to put at confirm message
     * @param text The text to put on content of the confirm message
     * @return a boolean to check if the user select OK or cancel confirm message
     */
    public static boolean confirm(String title,String text) {
        boolean check = false;
        dialog = new Alert((Alert.AlertType.CONFIRMATION));
        dialog.setTitle(title);
        dialog.setContentText(text);
        dialog.showAndWait();
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.get() == ButtonType.OK) {
            check = true;
        }
        else  { check = false;}
        return check;
    }


}
