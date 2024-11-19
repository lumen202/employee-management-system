package dev.lumen.app;

import dev.lumen.App;

import dev.lumen.models.Department;
import dev.lumen.models.Employee;
import dev.lumen.models.Job;
import dev.sol.core.application.FXController;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class RootController extends FXController {

    @FXML
    private TableView<Employee> employeeTable;
    @FXML
    private TableColumn<Employee, String> empIdColumn;
    @FXML
    private TableColumn<Employee, String> empNameColumn;
    @FXML
    private TableColumn<Employee, Job> empJobColumn;
    @FXML
    private TableColumn<Employee, Employee> empManagerColumn;
    @FXML
    private TableColumn<Employee, Department> departmentColumn;
    @FXML
    private ComboBox<Department> departmentDropDown;

    private ObservableList<Department> department_masterList;
    private ObservableList<Employee> employee_masterList;

    @Override
    protected void load_fields() {
        department_masterList = App.COLLECTIONS_REGISTRY.getList("DEPARTMENT");
        employee_masterList = App.COLLECTIONS_REGISTRY.getList("EMPLOYEE");

        departmentDropDown.setButtonCell(new Department.LIST_CELL());
        departmentDropDown.setCellFactory(cell -> new Department.LIST_CELL());
        departmentDropDown.setItems(department_masterList);

        empIdColumn.setCellValueFactory(cell -> cell.getValue().emp_IdProperty());
        empNameColumn.setCellValueFactory(cell -> cell.getValue().nameProperty());
        empJobColumn.setCellValueFactory(cell -> cell.getValue().jobProperty());
        empManagerColumn.setCellFactory(cell -> {
            TableCell<Employee, Employee> tableCell = new TableCell<>() {
                @Override
                protected void updateItem(Employee item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                        setGraphic(null);
                        return;
                    }
                    setGraphic(new Label(item.getName()));
                }
            };
            return tableCell;
        });
        empManagerColumn.setCellValueFactory(cell -> cell.getValue().managerProperty());

        departmentColumn.setCellFactory(cell -> new Employee.DEPARTMENT_TABLECELL());
        departmentColumn.setCellValueFactory(cell -> cell.getValue().departmentProperty());

        employeeTable.setItems(employee_masterList);

    };

    @Override
    protected void load_bindings() {

    }

    @Override
    protected void load_listeners() {
        departmentDropDown.getSelectionModel().selectFirst();
    }

}
