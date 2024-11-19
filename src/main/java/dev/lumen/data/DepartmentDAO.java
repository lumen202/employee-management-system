package dev.lumen.data;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.sql.rowset.CachedRowSet;

import dev.lumen.App;
import dev.lumen.models.Department;
import dev.sol.db.DBService;

public class DepartmentDAO {
    public static final String TABLE = "department";
    public static final DBService DB = App.DB_EMPLOYEE;

    private static Department data(CachedRowSet crs) {
        try {
            String dep_id = crs.getString("dep_id");
            String dep_name = crs.getString("dep_name");
            String dep_location = crs.getString("dep_location");

            return new Department(dep_id, dep_name, dep_location);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Department> getDepartmentList() {
        CachedRowSet crs = App.DB_EMPLOYEE.select_all(TABLE);
        List<Department> list = new LinkedList<>();

        try {
            while (crs.next()) {
                Department department = data(crs);
                if (department != null)
                    list.add(department);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list; 

    }

}