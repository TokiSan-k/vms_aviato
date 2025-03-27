package com.aviato.db.dao;

import com.aviato.Types.Customer;
import com.aviato.db.HibernateUtil;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.procedure.ProcedureCall;

import javax.persistence.ParameterMode;
import java.util.List;

public class Customer_dao
{
    public static void InsertCustomer(Customer customer) throws Exception
    {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            ProcedureCall procedureCall = session.getNamedProcedureCall("InsertCustomer");
            procedureCall.setParameter("p_cust_name", customer.getName());
            procedureCall.setParameter("p_phone", customer.getPhone());
            procedureCall.setParameter("p_email", customer.getEmailId());
            procedureCall.setParameter("p_address", customer.getAddress());

            procedureCall.execute();
            Long generatedId = (Long) procedureCall.getOutputParameterValue("p_cust_id");
            customer.setCustId(generatedId);

            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw ex;
        }
    }

    public static Customer GetCustomer(Long custId) throws Exception
    {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            ProcedureCall procedureCall = session.getNamedProcedureCall("GetCustomer");
            procedureCall.setParameter("p_cust_id", custId);

            List<Customer> results = procedureCall.getResultList();
            if (results == null || results.isEmpty()) {
                throw new Exception("No customer with ID: " + custId);
            }
            return results.get(0);
        } catch (Exception ex) {
            throw ex;
        }
    }

    public static void UpdateCustomer(Customer customer)
    {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            ProcedureCall procedureCall = session.getNamedProcedureCall("UpdateCustomer");
            procedureCall.setParameter("p_cust_id", customer.getCustId());
            procedureCall.setParameter("p_cust_name", customer.getName());
            procedureCall.setParameter("p_phone", customer.getPhone());
            procedureCall.setParameter("p_email", customer.getEmailId());
            procedureCall.setParameter("p_address", customer.getAddress());

            procedureCall.execute();
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw ex;
        }
    }

    public static void DeleteCustomer(Long custId) throws Exception {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            ProcedureCall procedureCall = session.getNamedProcedureCall("DeleteCustomer");
            procedureCall.setParameter("p_cust_id", custId);

            procedureCall.execute();
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw ex;
        }
    }

    public static void GetAllCustomers(ObservableList<Customer> customers) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            customers.clear();

            ProcedureCall procedureCall = session.getNamedProcedureCall("GetAllCustomers");
            procedureCall.setParameter("p_cust_id", null);

            List<Customer> resultList = procedureCall.getResultList();
            if (resultList == null || resultList.isEmpty()) {
                throw new Exception("No customers in the database");
            }
            customers.addAll(resultList);
        } catch (Exception ex) {
            throw ex;
        }
    }
}