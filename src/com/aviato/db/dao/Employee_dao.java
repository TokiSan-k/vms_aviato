package com.aviato.db.dao;

import com.aviato.Types.Employee;
import com.aviato.db.HibernateUtil;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.procedure.ProcedureCall;

import java.sql.Date;
import java.util.List;

public class Employee_dao {
    public static void InsertEmployee(Employee employee) throws Exception {
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
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw ex;
        }
    }

    public static Employee GetEmployee(Long empId) throws Exception {
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

    public static void UpdateEmployee(Employee employee) {
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
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw ex;
        }
    }

    public static void DeleteEmployee(Long empId) throws Exception {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            ProcedureCall procedureCall = session.getNamedProcedureCall("DeleteEmployee");
            procedureCall.setParameter("p_emp_id", empId);

            procedureCall.execute();
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw ex;
        }
    }

    public static void GetAllEmployees(ObservableList<Employee> employees) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            employees.clear();

            ProcedureCall procedureCall = session.getNamedProcedureCall("GetAllEmployees");
            procedureCall.setParameter("p_emp_id", null);

            List<Employee> resultList = procedureCall.getResultList();
            if (resultList == null || resultList.isEmpty()) {
                throw new Exception("No employees in the database");
            }
            employees.addAll(resultList);
        } catch (Exception ex) {
            throw ex;
        }
    }
}