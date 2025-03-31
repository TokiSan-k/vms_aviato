package com.aviato.db.dao;

import com.aviato.Types.Appointment;
import com.aviato.db.HibernateUtil;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.procedure.ProcedureCall;

import java.util.List;

public class Appointment_dao {

    public static void ScheduleAppointment(Appointment appointment) throws Exception {
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
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw ex;
        }
    }

    public static Appointment GetAppointment(Long appId) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            ProcedureCall procedureCall = session.getNamedProcedureCall("GetAppointment");
            procedureCall.setParameter("p_app_id", appId);

            List<Appointment> results = procedureCall.getResultList();
            if (results == null || results.isEmpty()) {
                throw new Exception("No appointment with ID: " + appId);
            }
            return results.get(0);
        } catch (Exception ex) {
            throw ex;
        }
    }

    public static void UpdateAppointmentStatus(Long appId, String status) throws Exception {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            ProcedureCall procedureCall = session.getNamedProcedureCall("UpdateAppointmentStatus");
            procedureCall.setParameter("p_app_id", appId);
            procedureCall.setParameter("p_status", status);

            procedureCall.execute();
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw ex;
        }
    }

    public static void DeleteAppointment(Long appId) throws Exception {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            ProcedureCall procedureCall = session.getNamedProcedureCall("DeleteAppointment");
            procedureCall.setParameter("p_app_id", appId);

            procedureCall.execute();
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw ex;
        }
    }

    public static void GetAllAppointments(ObservableList<Appointment> appointments) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            appointments.clear();

            ProcedureCall procedureCall = session.getNamedProcedureCall("GetAllAppointments");
            procedureCall.setParameter("p_app_id", null);

            List<Appointment> resultList = procedureCall.getResultList();
            if (resultList == null || resultList.isEmpty()) {
                throw new Exception("No appointments in the database");
            }
            appointments.addAll(resultList);
        } catch (Exception ex) {
            throw ex;
        }
    }
}