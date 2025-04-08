package com.aviato.db.dao;

import com.aviato.Types.Employee;
import com.aviato.db.HibernateUtil;
import javafx.concurrent.Task;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.procedure.ParameterRegistration;
import org.hibernate.procedure.ProcedureCall;

import java.sql.Date;
import java.util.List;

public class Employee_dao {
    public static Task<Void> insertEmployeeTask(Employee employee) {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Transaction transaction = null;
                try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                    transaction = session.beginTransaction();
                    ProcedureCall procedureCall = session.getNamedProcedureCall("InsertEmployee");
                    procedureCall.setParameter("p_emp_name", employee.getEmpName());
                    procedureCall.setParameter("p_position", employee.getPosition());
                    procedureCall.setParameter("p_emp_phn", employee.getPhone());
                    procedureCall.setParameter("p_email", employee.getEmail());
                    procedureCall.setParameter("p_salary", employee.getSalary());
                    procedureCall.setParameter("p_hire_date", employee.getHireDate());
                    procedureCall.setParameter("p_hours_worked", employee.getHoursWorked());

                    procedureCall.execute();
                    Long generatedId = (Long) procedureCall.getOutputParameterValue("p_emp_id");
                    employee.setEmpId(generatedId);
                    transaction.commit();
                    return null;
                } catch (Exception ex) {
                    if (transaction != null) {
                        transaction.rollback();
                    }
                    throw ex;
                }
            }
        };
    }

    public static Task<Employee> getEmployeeTask(Long empId) {
        return new Task<Employee>() {
            @Override
            protected Employee call() throws Exception {
                try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                    ProcedureCall procedureCall = session.getNamedProcedureCall("GetEmployee");
                    procedureCall.setParameter("p_emp_id", empId);

                    List<Employee> results = procedureCall.getResultList();
                    if (results == null || results.isEmpty()) {
                        throw new Exception("No employee with ID: " + empId);
                    }
                    return results.get(0);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    throw ex;
                }
            }
        };
    }

    public static Task<Void> updateEmployeeTask(Employee employee) {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Transaction transaction = null;
                try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                    transaction = session.beginTransaction();
                    ProcedureCall procedureCall = session.getNamedProcedureCall("UpdateEmployee");
                    procedureCall.setParameter("p_emp_id", employee.getEmpId());
                    procedureCall.setParameter("p_emp_name", employee.getEmpName());
                    procedureCall.setParameter("p_position", employee.getPosition());
                    procedureCall.setParameter("p_emp_phn", employee.getPhone());
                    procedureCall.setParameter("p_email", employee.getEmail());
                    procedureCall.setParameter("p_salary", employee.getSalary());
                    procedureCall.setParameter("p_hire_date", employee.getHireDate());
                    procedureCall.setParameter("p_hours_worked", employee.getHoursWorked());

                    procedureCall.execute();
                    transaction.commit();
                    return null;
                } catch (Exception ex) {
                    if (transaction != null) {
                        transaction.rollback();
                    }
                    throw ex;
                }
            }
        };
    }

    public static Task<Void> deleteEmployeeTask(Long empId) {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                    ProcedureCall procedureCall = session.getNamedProcedureCall("DeleteEmployee");
                    procedureCall.setParameter("p_emp_id", empId);

                    procedureCall.execute();
                    return null;
                } catch (Exception ex) {
                    throw ex;
                }
            }
        };
    }

    public static Task<List<Employee>> getAllEmployeesTask() {
        return new Task<List<Employee>>() {
            @Override
            protected List<Employee> call() throws Exception {
                try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                    ProcedureCall procedureCall = session.getNamedProcedureCall("GetAllEmployees");
                    ParameterRegistration<Long> param = procedureCall.getParameterRegistration("p_emp_id");
                    param.enablePassingNulls(true);
                    procedureCall.setParameter("p_emp_id", null);

                    List<Employee> resultList = procedureCall.getResultList();
                    if (resultList == null || resultList.isEmpty()) {
                        throw new Exception("No employees in the database");
                    }
                    return resultList;
                } catch (Exception ex) {
                    ex.printStackTrace();
                    throw ex;
                }
            }
        };
    }

    public static Task<List<Employee>> searchEmployeesByPartialNameTask(String partialName) {
        return new Task<List<Employee>>() {
            @Override
            protected List<Employee> call() throws Exception {
                try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                    ProcedureCall procedureCall = session.getNamedProcedureCall("SearchEmployeesByPartialName");
                    procedureCall.setParameter("p_partial_name", partialName);

                    List<Employee> resultList = procedureCall.getResultList();
                    if (resultList == null || resultList.isEmpty()) {
                        throw new Exception("No employees found matching name: " + partialName);
                    }
                    return resultList;
                } catch (Exception ex) {
                    throw ex;
                }
            }
        };
    }
}