package org.project.Model;
//
/** This is a temp file basically explaining how this works.
 * Unsure where to put this and considering GUI refactoring is happening soon.
 * This is to establish some semblance of starting image exporting
 * 
 * Gut this, rework this should you need to, and include this into the GUI code
 * And put this in recycling bin
 *
 * Link to where I got this from:
 * https://www.tutorialspoint.com/how-to-create-save-the-image-of-a-javafx-pie-chart-without-actually-showing-the-window
 * */

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javafx.stage.Stage;

/**These need to be imported  to the GUI*/
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.WritableImage;
/**These need to be imported  to the GUI*/

public class ExportImage {
    public void start(Stage stage) throws IOException{

        /**Sets the stage being made. Parent Root, and the size of the canvas (Length, Width) */
        Scene scene = new Scene(new Group(), 595, 400);

        /**Set the name of the stage*/
        stage.setTitle("Set name of the stage here");

        /** Get the property of the scene, add() should be the canvas or whatever*/
        ((Group) scene.getRoot()).getChildren().add(null);

        /**Saving the current scene as an image.*/
        WritableImage image = scene.snapshot(null);

        /**The path for the image, where the image will go*/
        File file = new File("PATHNAME HERE");

        /**Writes to the image.
         * Write - the image (what SwingFXUtils.fromFXImage is doing), the file format, the path name
         * SwingFXUtils.fromFXImage - the snapshot being saved, a buffered object for the image which will probably not be needed*/
        ImageIO.write(SwingFXUtils.fromFXImage(image, null), "PNG", file);

        /**Confirmation text*/
        System.out.println("Image Saved");
    }

}
