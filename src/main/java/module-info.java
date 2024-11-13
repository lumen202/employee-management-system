module lumen.employee_management {
    
    requires javafx.controls;
    requires javafx.fxml;
    requires core.fx;
    requires javafx.graphics;

    opens dev.lumen to javafx.fxml;
    opens dev.lumen.app to java.fxml;
    

    
    exports dev.lumen;
    exports dev.lumen.app;

}
