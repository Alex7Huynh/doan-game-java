/*
 * Hearts_ServerApp.java
 * 0812508-0812515-0812527
 */

package hearts_server;

import mypackage.Player;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application.
 */
public class Hearts_ServerApp extends SingleFrameApplication {

    /**
     * At startup create and show the main frame of the application.
     */
    @Override protected void startup() {
        show(new Hearts_ServerView(this));
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of Hearts_ServerApp
     */
    public static Hearts_ServerApp getApplication() {
        return Application.getInstance(Hearts_ServerApp.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        //launch(Hearts_ServerApp.class, args);
        int a = Player.LaySo("3_Chuon.jpg");
        int b = Player.LayDiem("3_Co.jpg");
        int c = Player.LaySo("12_Bich.jpg");
        int d = Player.LayDiem("12_Bich.jpg");
        int x = Player.SoSanhBai("3_Chuon.jpg", "3_Co.jpg", "12_Bich.jpg", "12_Bich.jpg");
        int y = Player.SoSanhBai("3_Chuon.jpg", "6_Chuon.jpg", "12_Bich.jpg", "12_Bich.jpg");
        int z = Player.SoSanhBai("3_Chuon.jpg", "3_Co.jpg", "14_Chuon.jpg", "12_Chuon.jpg");        
    }
}
