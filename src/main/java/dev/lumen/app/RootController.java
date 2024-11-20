package dev.lumen.app;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import atlantafx.base.theme.Styles;
import atlantafx.base.util.Animations;
import dev.lumen.App;
import dev.lumen.data.EmployeeDAO;
import dev.lumen.models.Department;
import dev.lumen.models.Employee;
import dev.lumen.models.Job;
import dev.sol.core.application.FXController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

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
    private ComboBox<Department> departmentField;
    @FXML
    private TextField nameField;
    @FXML
    private ComboBox<Employee> managerField;
    @FXML
    private ComboBox<Job> jobField;

    @FXML
    private TextField filteredEmployeeField;
    @FXML
    ComboBox<Employee> newManagerField;

    @FXML
    private void handleAddEmployee() {
        if (nameField.getText().isEmpty()) {
            nameField.pseudoClassStateChanged(Styles.STATE_DANGER, true);
            Animations.flash(nameField).playFromStart();
            return;
        }
        Collections.sort(employee_masterList, Comparator.comparing(Employee::getEmp_id));
        int id_int = Integer.parseInt(employee_masterList.getLast().getEmp_id()) + 1;
        String emp_id = Integer.toString(id_int);
        Employee employee = new Employee(emp_id, nameField.getText(), jobField.getValue(), managerField.getValue(),
                departmentField.getValue());
        EmployeeDAO.insert(employee);
        employee_masterList.add(employee);
        reset_newEmployeeField();
    }

    @FXML
    private void handleSearchEmployee() {
        if (filteredEmployeeField.getText().isEmpty()) {
            filteredEmployeeField.pseudoClassStateChanged(Styles.STATE_DANGER, true);
            Animations.flash(filteredEmployeeField).playFromStart();
            return;
        }
    }

    @FXML
    private void handleSearchAllEmployee() {
    }

    private ObservableList<Department> department_masterList;
    private ObservableList<Employee> employee_masterList;
    private FilteredList<Employee> managerList;

    private static class MANAGER_CELL extends ListCell<Employee> {

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

    }

    @Override
    protected void load_bindings() {
        employee_masterList = App.COLLECTIONS_REGISTRY.getList("EMPLOYEE");
        department_masterList = App.COLLECTIONS_REGISTRY.getList("DEPARTMENT");

        managerList = new FilteredList<>(employee_masterList, employeee -> {
            return employeee.getJob() == Job.PRESIDENT || employeee.getJob() == Job.MANAGER;
        });
        managerField.setButtonCell(new MANAGER_CELL());
        managerField.setCellFactory(cell -> new MANAGER_CELL());
        managerField.setItems(managerList);

        newManagerField.setButtonCell(new MANAGER_CELL());
        newManagerField.setCellFactory(cell -> new MANAGER_CELL());
        newManagerField.setItems(managerList);

        ObservableList<Job> joblList = FXCollections.observableArrayList(Job.values());
        if (employee_masterList.stream().anyMatch(e -> e.getJob().equals(Job.PRESIDENT))) {
            jobField.setItems(FXCollections.observableArrayList(joblList.subList(1, joblList.size())));
        } else
            jobField.setItems(joblList);

        departmentField.setButtonCell(new Department.LIST_CELL());
        departmentField.setCellFactory(cell -> new Department.LIST_CELL());
        departmentField.setItems(department_masterList);

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
    }

    @Override
    protected void load_fields() {
    }

    @Override
    protected void load_listeners() {
        reset_newEmployeeField();

        managerField.getSelectionModel().selectFirst();
        jobField.getSelectionModel().selectFirst();
        departmentField.getSelectionModel().selectFirst();
        newManagerField.getSelectionModel().selectFirst();

        nameField.textProperty().addListener((o, ov, nv) -> {
            nameField.pseudoClassStateChanged(Styles.STATE_DANGER, false);
        });
        filteredEmployeeField.textProperty().addListener((o, ov, nv) -> {
            filteredEmployeeField.pseudoClassStateChanged(Styles.STATE_DANGER, false);
        });

    }

    private void reset_newEmployeeField() {
        nameField.setText("");
        jobField.getSelectionModel().selectFirst();
        managerField.getSelectionModel().selectFirst();
        departmentField.getSelectionModel().selectFirst();

    }
}