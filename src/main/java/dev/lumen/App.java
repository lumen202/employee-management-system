package dev.lumen;

import dev.lumen.app.RootLoader;
import dev.sol.core.app.FXApplication;
import dev.sol.core.app.view.FXLoader;
import dev.sol.core.base.scene.FXSkin;

public class App extends FXApplication {

    @Override
    public void initialize() throws Exception {

        setTitle("EmployeeFX JBC");
        setSkin(FXSkin.NORD_DARK);
        _initialize_application();
    }

    private void _initialize_application() {
        RootLoader rootLoader = (RootLoader) FXLoader
                .createInstance(RootLoader.class, App.class.getResource("/dev/lumen/app/ROOT.fxml"))
                .addParameter("scene", applicationScene).initialize();
        rootLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}