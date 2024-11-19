package dev.lumen.models;

import dev.sol.core.application.FXModel;
import dev.sol.core.properties.beans.FXStringProperty;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;

public class Department extends FXModel {
    public static class LIST_CELL extends ListCell<Department> {
        @Override
        protected void updateItem(Department department, boolean empty) {
            super.updateItem(department, empty);

            if (department == null || empty) {
                setText(null);
                setGraphic(null);
                return;
            }
            setGraphic(new Label(department.getName()));
        }
    }


    private FXStringProperty dep_id;
    private FXStringProperty name;
    private FXStringProperty location;

    public Department(String id, String name, String location) {
        this.dep_id = new FXStringProperty(id);
        this.name = new FXStringProperty(name);
        this.location = new FXStringProperty(location);

        track_properties(this.dep_id, this.name, this.location);
    }

    public FXStringProperty depIDProperty() {
        return dep_id;
    }

    public String getDepID() {
        return depIDProperty().get();
    }

    public void setDepID(String dep_id) {
        depIDProperty().set(dep_id);
    }

    public FXStringProperty nameProperty() {
        return name;
    }

    public String getName() {
        return nameProperty().get();
    }

    public void setName(String name) {
        nameProperty().set(name);
    }

    public FXStringProperty locationProperty() {
        return location;
    }

    public String getLocation() {
        return locationProperty().get();
    }

    public void setLocation(String location) {
        locationProperty().set(location);
    }

    @Override
    public FXModel clone() {
        return new Department(getDepID(), getName(), getLocation());

    }

    @Override
    public void copy(FXModel arg0) {
        Department c = (Department) arg0;

        setDepID(c.getDepID());
        setName(c.getName());
        setLocation(c.getLocation());
    }

}
