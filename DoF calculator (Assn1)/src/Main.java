/**
 * The main class.
 * Launch application.
 */

import model.LensManager;
import textui.CameraTextUI;

public class Main {
    public static void main(String args[]) {
        LensManager manager = new LensManager();
        CameraTextUI ui = new CameraTextUI(manager);
        ui.show();
    }
}



