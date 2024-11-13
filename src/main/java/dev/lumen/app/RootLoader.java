package dev.lumen.app;


import dev.sol.core.application.loader.FXLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class RootLoader extends FXLoader{
    
    @Override
    public void load() {
        Scene scene = (Scene) params.get("scene");

        try {
            Parent root = loader.load();
            scene.setRoot(root);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
