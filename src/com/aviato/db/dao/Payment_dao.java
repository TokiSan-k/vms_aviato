package com.aviato.db.dao;

import com.aviato.Types.Payment;
import com.aviato.db.HibernateUtil;
import javafx.concurrent.Task;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.procedure.ParameterRegistration;
import org.hibernate.procedure.ProcedureCall;

import java.util.Date;
import java.util.List;

public class Payment_dao {

    public static Task<Void> recordPaymentTask(Payment payment) {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Transaction transaction = null;
                try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                    transaction = session.beginTransaction();

                    ProcedureCall procedureCall = session.getNamedProcedureCall("RecordPayment");
                    procedureCall.setParameter("p_invoice_id", payment.getInvoiceId());
                    procedureCall.setParameter("p_amount_paid", payment.getAmountPaid());
                    procedureCall.setParameter("p_payment_method", payment.getPaymentMethod());
                    procedureCall.setParameter("p_status", payment.getStatus());

                    procedureCall.execute();
                    Long generatedId = (Long) procedureCall.getOutputParameterValue("p_payment_id");
                    payment.setPaymentId(generatedId);

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

    public static Task<Payment> getPaymentByIdTask(Long paymentId) {
        return new Task<Payment>() {
            @Override
            protected Payment call() throws Exception {
                try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                    ProcedureCall procedureCall = session.getNamedProcedureCall("GetPaymentById");
                    procedureCall.setParameter("p_payment_id", paymentId);

                    List<Payment> results = procedureCall.getResultList();
                    if (results == null || results.isEmpty()) {
                        throw new Exception("No payment with ID: " + paymentId);
                    }
                    return results.get(0);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    throw ex;
                }
            }
        };
    }

    public static Task<Void> updatePaymentStatusTask(Long paymentId, String newStatus) {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Transaction transaction = null;
                try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                    transaction = session.beginTransaction();

                    ProcedureCall procedureCall = session.getNamedProcedureCall("UpdatePaymentStatus");
                    procedureCall.setParameter("p_payment_id", paymentId);
                    procedureCall.setParameter("p_new_status", newStatus);

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

    public static Task<List<Payment>> getAllPaymentsTask() {
        return new Task<List<Payment>>() {
            @Override
            protected List<Payment> call() throws Exception {
                try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                    ProcedureCall procedureCall = session.getNamedProcedureCall("getAllPayment");

                    // No IN parameters to set, just execute the procedure
                    List<Payment> resultList = procedureCall.getResultList();
                    if (resultList == null || resultList.isEmpty()) {
                        throw new Exception("No payments in the database");
                    }
                    return resultList;
                } catch (Exception ex) {
                    ex.printStackTrace();
                    throw ex;
                }
            }
        };
    }
}