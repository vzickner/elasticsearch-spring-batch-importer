package com.example;

import com.example.domain.Employee;
import com.example.domain.Gender;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Valentin Zickner
 */
@Component
@JobScope
public class EmployeesItemReader extends JdbcCursorItemReader<Employee> {

    @Autowired
    public EmployeesItemReader(DataSource dataSource) {
        this.setSql("select * from employees");
        this.setRowMapper(new EmployeeRowMapper());
        this.setDataSource(dataSource);
    }

    private static class EmployeeRowMapper implements RowMapper<Employee> {
        @Override
        public Employee mapRow(ResultSet resultSet, int i) throws SQLException {
            Employee employee = new Employee();
            employee.setEmpNo(resultSet.getInt("emp_no"));
            employee.setBirthDate(resultSet.getDate("birth_date"));
            employee.setFirstName(resultSet.getString("first_name"));
            employee.setLastName(resultSet.getString("last_name"));
            employee.setGender(Gender.valueOf(resultSet.getString("gender")));
            employee.setHireDate(resultSet.getDate("hire_date"));
            return employee;
        }
    }
}
