package dev.lumen;

import dev.lumen.app.RootLoader;
import dev.lumen.data.DepartmentDAO;
import dev.lumen.data.EmployeeDAO;
import dev.sol.core.application.FXApplication;
import dev.sol.core.application.loader.FXLoaderFactory;
import dev.sol.core.registry.FXCollectionsRegister;
import dev.sol.core.registry.FXControllerRegister;
import dev.sol.core.registry.FXNodeRegister;
import dev.sol.core.scene.FXSkin;
import dev.sol.core.thread.FXThreadService;
import dev.sol.db.DBService;
import javafx.collections.FXCollections;

public class App extends FXApplication {

    public static final FXControllerRegister CONTROLLER_REGISTRY = FXControllerRegister.INSTANCE;
    public static final FXCollectionsRegister COLLECTIONS_REGISTRY = FXCollectionsRegister.INSTANCE;
    public static final FXNodeRegister NODE_REGISTER = FXNodeRegister.INSTANCE;

    public static final FXThreadService THREAD = FXThreadService.INSTANCE;

    public static final DBService DB_EMPLOYEE = DBService.INSTANCE.initialize("jdbc:mysql://localhost/employee?user=root&password=");

    @Override
    public void initialize() throws Exception {

        setTitle("EmployeeFX JDBC");
        setSkin(FXSkin.PRIMER_LIGHT);
        applicationStage.setResizable(false);

        initialize_dataset();
        initialize_application();
    }

    public void initialize_dataset(){

        COLLECTIONS_REGISTRY.register("DEPARTMENT", FXCollections.observableArrayList(DepartmentDAO.getDepartmentList()));
        COLLECTIONS_REGISTRY.register("EMPLOYEE", FXCollections.observableArrayList(EmployeeDAO.getEmployeeList()));
    }

    private void initialize_application() {
        RootLoader rootLoader = (RootLoader) FXLoaderFactory
                .createInstance(RootLoader.class, App.class.getResource("/dev/lumen/app/ROOT.fxml"))
                .addParameter("scene", applicationScene).initialize();
        rootLoader.load();
    }

}