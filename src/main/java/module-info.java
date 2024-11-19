module lumen.employeeJDBC {

    requires transitive javafx.controls;
    requires transitive core.fx;
    requires javafx.fxml;
    requires atlantafx.base;

    requires javafx.graphics;
    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.materialdesign2;
    requires org.kordamp.ikonli.materialdesign;


    requires transitive core.db;
    requires core.util;
    requires java.sql.rowset;


    opens dev.lumen to javafx.fxml;
    opens dev.lumen.app to javafx.fxml;

    exports dev.lumen;
    exports dev.lumen.app;

}
