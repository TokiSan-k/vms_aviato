package com.aviato.db.dao;

import com.aviato.Types.Appointment;
import com.aviato.Types.Invoice;
import com.aviato.Types.InvoiceInfo;
import com.aviato.db.HibernateUtil;
import javafx.concurrent.Task;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.procedure.ParameterRegistration;
import org.hibernate.procedure.ProcedureCall;


import java.util.List;

public class Appointment_dao {

    public static Task<Void> scheduleAppointmentTask(Appointment appointment) {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Transaction transaction = null;
                try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                    transaction = session.beginTransaction();

                    ProcedureCall procedureCall = session.getNamedProcedureCall("ScheduleAppointment");
                    procedureCall.setParameter("p_cust_id", appointment.getCustId());
                    procedureCall.setParameter("p_vehicle_id", appointment.getVehicleId());
                    procedureCall.setParameter("p_app_date", appointment.getAppDate());
                    procedureCall.setParameter("p_app_time", appointment.getAppTime());
                    procedureCall.setParameter("p_status", appointment.getStatus());
                    procedureCall.setParameter("p_service_id", appointment.getServiceId());
                    procedureCall.setParameter("p_emp_id", appointment.getEmpId());

                    procedureCall.execute();
                    Long generatedId = (Long) procedureCall.getOutputParameterValue("p_app_id");
                    appointment.setAppId(generatedId);

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

    public static Task<Appointment> getAppointmentTask(Long appId) {
        return new Task<Appointment>() {
            @Override
            protected Appointment call() throws Exception {
                try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                    ProcedureCall procedureCall = session.getNamedProcedureCall("GetAppointment");
                    procedureCall.setParameter("p_app_id", appId);

                    List<Appointment> results = procedureCall.getResultList();
                    if (results == null || results.isEmpty()) {
                        throw new Exception("No appointment with ID: " + appId);
                    }
                    return results.get(0);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    throw ex;
                }
            }
        };
    }

    public static Task<Void> updateAppointmentStatusTask(Long appId, String status) {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Transaction transaction = null;
                try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                    transaction = session.beginTransaction();

                    ProcedureCall procedureCall = session.getNamedProcedureCall("UpdateAppointmentStatus");
                    procedureCall.setParameter("p_app_id", appId);
                    procedureCall.setParameter("p_status", status);

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

    public static Task<Void> deleteAppointmentTask(Long appId) {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Transaction transaction = null;
                try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                    transaction = session.beginTransaction();

                    ProcedureCall procedureCall = session.getNamedProcedureCall("DeleteAppointment");
                    procedureCall.setParameter("p_app_id", appId);

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

    public static Task<List<Appointment>> getAllAppointmentsTask() {
        return new Task<List<Appointment>>() {
            @Override
            protected List<Appointment> call() throws Exception {
                try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                    ProcedureCall procedureCall = session.getNamedProcedureCall("GetAllAppointments");
                    ParameterRegistration<Long> param = procedureCall.getParameterRegistration("p_app_id");
                    param.enablePassingNulls(true);
                    procedureCall.setParameter("p_app_id", null);

                    List<Appointment> resultList = procedureCall.getResultList();
                    if (resultList == null || resultList.isEmpty()) {
                        throw new Exception("No appointments in the database");
                    }
                    return resultList;
                } catch (Exception ex) {
                    ex.printStackTrace();
                    throw ex;
                }
            }
        };
    }


    public static Task<InvoiceInfo> generateInvoiceTask(Long appId, String description, Double totalAmount) {
        return new Task<InvoiceInfo>() {
            @Override
            protected InvoiceInfo call() throws Exception {
                try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                    ProcedureCall call = session.getNamedProcedureCall("GenerateInvoice");

                    call.setParameter("p_app_id", appId);
                    call.setParameter("p_description", description);
                    call.setParameter("p_total_amount", totalAmount);

                    call.execute();

                    return new InvoiceInfo(
                            (Long) call.getOutputParameterValue("p_invoice_id"),
                            (java.sql.Date) call.getOutputParameterValue("p_invoice_date"),
                            (Double) call.getOutputParameterValue("p_total_amount_out"),
                            (String) call.getOutputParameterValue("p_cust_name"),
                            (String) call.getOutputParameterValue("p_address"),
                            (String) call.getOutputParameterValue("p_email"),
                            (String) call.getOutputParameterValue("p_contact"),
                            (String) call.getOutputParameterValue("p_licence_plate"),
                            (String) call.getOutputParameterValue("p_description_out")
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                    throw e;
                }
            }
        };
    }

    public static Task<List<Appointment>> searchAllCustIdAppointmentsTask(Long custID) {
        return new Task<List<Appointment>>() {
            @Override
            protected List<Appointment> call() throws Exception {
                try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                    ProcedureCall procedureCall = session.getNamedProcedureCall("searchAppointmentsByCustomerId");
                    procedureCall.setParameter("p_cust_id", custID);

                    List<Appointment> resultList = procedureCall.getResultList();
                    if (resultList == null || resultList.isEmpty()) {
                        throw new Exception("No appointments for ID: "+custID);
                    }
                    return resultList;
                } catch (Exception ex) {
                    ex.printStackTrace();
                    throw ex;
                }
            }
        };
    }

    public static Task<List<Appointment>> searchAllVehcleIdAppointmentsTask(Long vehicleID) {
        return new Task<List<Appointment>>() {
            @Override
            protected List<Appointment> call() throws Exception {
                try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                    ProcedureCall procedureCall = session.getNamedProcedureCall("searchAppointmentsByVehicleId");
                    procedureCall.setParameter("p_vehicle_id", vehicleID);

                    List<Appointment> resultList = procedureCall.getResultList();
                    if (resultList == null || resultList.isEmpty()) {
                        throw new Exception("No appointments for ID: "+vehicleID);
                    }
                    return resultList;
                } catch (Exception ex) {
                    ex.printStackTrace();
                    throw ex;
                }
            }
        };
    }

    public static Task<List<Invoice>> getAllInvoicesTask() {
        return new Task<List<Invoice>>() {
            @Override
            protected List<Invoice> call() throws Exception {
                try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                    ProcedureCall procedureCall = session.getNamedProcedureCall("GetAllInvoices");

                    List<Invoice> resultList = procedureCall.getResultList();
                    if (resultList == null || resultList.isEmpty()) {
                        throw new Exception("No invoices found in the database");
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