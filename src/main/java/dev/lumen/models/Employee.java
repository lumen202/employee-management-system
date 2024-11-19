package dev.lumen.models;

import java.time.LocalDate;

import dev.sol.core.application.FXModel;
import dev.sol.core.properties.beans.FXDoubleProperty;
import dev.sol.core.properties.beans.FXObjectProperty;
import dev.sol.core.properties.beans.FXStringProperty;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;

public class Employee extends FXModel {

        public static class DEPARTMENT_TABLECELL extends TableCell<Employee, Department> {
        @Override
        protected void updateItem(Department item, boolean empty) {
            super.updateItem(item, empty);

            if (item == null || empty) {
                setText(null);
                setGraphic(null);
                return;
            }
            setGraphic(new Label(item.getName()));
        }

    }


    private FXStringProperty emp_id;
    private FXStringProperty name;
    private FXObjectProperty<Job> job;
    private FXObjectProperty<Employee> manager;
    private FXObjectProperty<LocalDate> hire_date;
    private FXDoubleProperty salary;
    private FXDoubleProperty commission;
    private FXObjectProperty<Department> department;

    public Employee(String emp_id){
        this(emp_id,null,null,null,null,0,0,null);
    }

    public Employee(String emp_id, String name, Job job, Employee manager, LocalDate hire_date, double salary,
            double commission,
            Department department) {

        this.emp_id = new FXStringProperty(emp_id);
        this.name = new FXStringProperty(name);
        this.job = new FXObjectProperty<>(job);
        this.manager = new FXObjectProperty<>(manager);
        this.hire_date = new FXObjectProperty<>(hire_date);
        this.salary = new FXDoubleProperty(salary);
        this.commission = new FXDoubleProperty(commission);
        this.department = new FXObjectProperty<>(department);

        track_properties(this.emp_id, this.name, this.job, this.manager, this.hire_date, this.salary, this.commission,
                this.department);

    }

    public FXStringProperty emp_IdProperty() {
        return this.emp_id;
    }

    public String getEmp_id() {
        return emp_IdProperty().get();
    }

    public void setEmp_id(String emp_id) {
        emp_IdProperty().set(emp_id);
    }

    public FXStringProperty nameProperty() {
        return this.name;
    }

    public String getName() {
        return nameProperty().get();
    }

    public void setName(String name) {
        nameProperty().set(name);
    }

    public FXObjectProperty<Job> jobProperty() {
        return this.job;
    }

    public Job getJob() {
        return jobProperty().get();
    }

    public void setJob(Job job) {
        jobProperty().set(job);
    }

    public FXObjectProperty<Employee> managerProperty() {
        return this.manager;
    }

    public Employee getManager() {
        return managerProperty().get();
    }

    public void setManager(Employee manager) {
        managerProperty().set(manager);
    }

    public FXObjectProperty<LocalDate> hire_dateProperty() {
        return this.hire_date;
    }

    public LocalDate getHireDate() {
        return hire_dateProperty().get();
    }

    public void setHire_date(LocalDate hire_date) {
        hire_dateProperty().set(hire_date);
    }

    public FXDoubleProperty salaryProperty() {
        return this.salary;
    }

    public double getSalary() {
        return salaryProperty().get();
    }

    public void setSalary(double salary) {
        salaryProperty().set(salary);
    }

    public FXDoubleProperty commissionProperty() {
        return this.commission;
    }

    public double getCommision() {
        return commissionProperty().get();
    }

    public void setCommision(double commision) {
        commissionProperty().set(commision);
    }

    public FXObjectProperty<Department> departmentProperty() {
        return this.department;
    }

    public Department getDepartment() {
        return departmentProperty().get();
    }

    public void setDepartment(Department department) {
        departmentProperty().set(department);
    }

    @Override
    public FXModel clone() {
        Employee employee = new Employee(getEmp_id(), getName(), getJob(),getManager(), getHireDate(), getSalary(), getCommision(),
                getDepartment());
        if (getManager() != null)
            employee.setManager(getManager());
        return employee;
    }

    @Override
    public void copy(FXModel arg0) {
        Employee c = (Employee) arg0;

        setEmp_id(c.getEmp_id());
        setName(c.getName());
        setJob(c.getJob());
        setManager(c.getManager());
        setHire_date(c.getHireDate());
        setSalary(c.getSalary());
        setCommision(c.getCommision());
    }

}
