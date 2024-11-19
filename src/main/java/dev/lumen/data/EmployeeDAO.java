package dev.lumen.data;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import javax.sql.rowset.CachedRowSet;

import dev.lumen.App;
import dev.lumen.models.Department;
import dev.lumen.models.Employee;
import dev.lumen.models.Job;
import dev.sol.db.DBService;
import dev.sol.util.CoreDateUtils;
import javafx.collections.ObservableList;

public class EmployeeDAO {
    public static final String TABLE = "employees";
    public static final DBService DB = App.DB_EMPLOYEE;

    private static final ObservableList<Department> departmentlist = App.COLLECTIONS_REGISTRY.getList("DEPARTMENT");

    public static Employee data(CachedRowSet crs) {
        try {
            String id = crs.getString("emp_id");
            String name = crs.getString("emp_name");
            Job job = Job.valueOf(crs.getString("job_name").toUpperCase().trim());
            Employee manager = new Employee(crs.getString("manager_id"));
            LocalDate hireDate = CoreDateUtils.parse(
                    crs.getString("hire_date"), "yyyy-MM-dd");
            double salary = crs.getDouble("salary");
            double commission = crs.getDouble("commission");
            Department department = departmentlist.stream()
                    .filter(dept -> {
                        try {
                            return dept.getDepID().equals(crs.getString("dep_id"));
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        return false;
                    }).findFirst().get();

            return new Employee(id, name, job, manager, hireDate, salary, commission, department);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Employee> getEmployeeList() {
        CachedRowSet crs = DB.select_all(TABLE);
        List<Employee> list = new LinkedList<>();

        try {
            while (crs.next()) {
                Employee employee = data(crs);
                if (employee != null)
                    list.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        list.forEach(employee -> {
            String manager_id = employee.getManager().getEmp_id();
            if (!manager_id.isEmpty()) {
                employee.setManager(
                        list.stream()
                                .filter(e -> e.getEmp_id().equals(manager_id))
                                .findFirst().get());
                employee.rebaseline();
            }
        });

        return list;
    }
}