package com.aviato.db.dao;

import com.aviato.Types.Customer;
import com.aviato.db.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.procedure.ProcedureCall;

import java.util.List;

public class Customer_dao
{
    public static void InsertCustomer(Customer customer) throws Exception
    {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            ProcedureCall procedureCall = (ProcedureCall)session.createNamedStoredProcedureQuery("InsertCustomer");
            procedureCall.setParameter("p_cust_name", customer.getName());
            procedureCall.setParameter("p_phone", customer.getPhone());
            procedureCall.setParameter("p_email", customer.getEmailId());
            procedureCall.setParameter("p_address", customer.getAddress());
            procedureCall.execute();

            transaction.commit();
        } catch (Exception ex) {
            throw ex;
        }
    }

    public static Customer GetCustomer(Long custId) throws Exception
    {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            ProcedureCall procedureCall = (ProcedureCall)session.createNamedStoredProcedureQuery("GetCustomer");
            procedureCall.setParameter("p_cust_id", custId);
            List<Customer> results = procedureCall.getResultList();
            if(results.isEmpty())
                throw new Exception("No customer with ID: "+custId);

            return results.get(0);
        } catch (Exception ex) {
            throw ex;
        }
    }
}