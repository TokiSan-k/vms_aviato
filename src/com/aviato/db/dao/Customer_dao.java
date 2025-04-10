package com.aviato.db.dao;

import com.aviato.Types.Customer;
import com.aviato.db.HibernateUtil;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.procedure.ParameterRegistration;
import org.hibernate.procedure.ProcedureCall;

import javax.persistence.ParameterMode;
import java.util.List;

public class Customer_dao {

    public static Task<Void> insertCustomerTask(Customer customer) {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
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

    public static Task<Customer> getCustomerTask(Long custId) {
        return new Task<Customer>() {
            @Override
            protected Customer call() throws Exception {
                try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                    ProcedureCall procedureCall = session.getNamedProcedureCall("GetCustomer");
                    procedureCall.setParameter("p_cust_id", custId);

                    List<Customer> results = procedureCall.getResultList();
                    if (results == null || results.isEmpty()) {
                        throw new Exception("No customer with ID: " + custId);
                    }
                    return results.get(0);
                }
            }
        };
    }

    public static Task<Void> updateCustomerTask(Customer customer) {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
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

    public static Task<Void> deleteCustomerTask(Long custId) {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Transaction transaction = null;
                try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                    transaction = session.beginTransaction();
                    ProcedureCall procedureCall = session.getNamedProcedureCall("DeleteCustomer");
                    procedureCall.setParameter("p_cust_id", custId);

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

    public static Task<List<Customer>> getAllCustomersTask() {
        return new Task<List<Customer>>() {
            @Override
            protected List<Customer> call() throws Exception {
                try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                    ProcedureCall procedureCall = session.getNamedProcedureCall("GetAllCustomers");

                    ParameterRegistration<Long> param = procedureCall.getParameterRegistration("p_cust_id");
                    param.enablePassingNulls(true);
                    procedureCall.setParameter("p_cust_id", null);

                    List<Customer> resultList = procedureCall.getResultList();
                    if (resultList == null || resultList.isEmpty()) {
                        throw new Exception("No customers in the database");
                    }
                    return resultList;
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                    throw ex;
                }
            }
        };
    }
    
    public static Task<List<Customer>> searchCustomersByPartialNameTask(String searchTerm) {
        return new Task<List<Customer>>() {
            @Override
            protected List<Customer> call() throws Exception {
                try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                    ProcedureCall procedureCall = session.getNamedProcedureCall("SearchCustomersByPartialName");
                    procedureCall.setParameter("p_partial_name", searchTerm);

                    List<Customer> resultList = procedureCall.getResultList();
                    if (resultList == null || resultList.isEmpty()) {
                        throw new Exception("No customers found matching: " + searchTerm);
                    }
                    return resultList;
                } catch (Exception ex) {
                    throw ex;
                }
            }
        };
    }
}