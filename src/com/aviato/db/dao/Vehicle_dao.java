package com.aviato.db.dao;

import com.aviato.Types.Vehicle;
import com.aviato.db.HibernateUtil;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.procedure.ProcedureCall;

import java.util.List;

public class Vehicle_dao {
    public static void InsertVehicle(Vehicle vehicle) throws Exception {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            ProcedureCall procedureCall = session.getNamedProcedureCall("InsertVehicle");
            procedureCall.setParameter("p_cust_id", vehicle.getCustomerId());
            procedureCall.setParameter("p_licence_plate", vehicle.getLicencePlate());
            procedureCall.setParameter("p_make", vehicle.getMake());
            procedureCall.setParameter("p_model", vehicle.getModel());
            procedureCall.setParameter("p_year", vehicle.getYear());

            procedureCall.execute();
            Long generatedId = (Long) procedureCall.getOutputParameterValue("p_vehicle_id");
            vehicle.setVehicleId(generatedId);

            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw ex;
        }
    }

    public static Vehicle GetVehicle(Long vehicleId) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            ProcedureCall procedureCall = session.getNamedProcedureCall("GetVehicle");
            procedureCall.setParameter("p_vehicle_id", vehicleId);

            List<Vehicle> results = procedureCall.getResultList();
            if (results == null || results.isEmpty()) {
                throw new Exception("No vehicle with ID: " + vehicleId);
            }
            return results.get(0);
        } catch (Exception ex) {
            throw ex;
        }
    }

    public static void UpdateVehicle(Vehicle vehicle) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            ProcedureCall procedureCall = session.getNamedProcedureCall("UpdateVehicle");
            procedureCall.setParameter("p_vehicle_id", vehicle.getVehicleId());
            procedureCall.setParameter("p_licence_plate", vehicle.getLicencePlate());
            procedureCall.setParameter("p_make", vehicle.getMake());
            procedureCall.setParameter("p_model", vehicle.getModel());
            procedureCall.setParameter("p_year", vehicle.getYear());

            procedureCall.execute();
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw ex;
        }
    }

    public static void DeleteVehicle(Long vehicleId) throws Exception {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            ProcedureCall procedureCall = session.getNamedProcedureCall("DeleteVehicle");
            procedureCall.setParameter("p_vehicle_id", vehicleId);

            procedureCall.execute();
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw ex;
        }
    }

    public static void GetAllVehicles(ObservableList<Vehicle> vehicles) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            vehicles.clear();

            ProcedureCall procedureCall = session.getNamedProcedureCall("GetAllVehicles");
            procedureCall.setParameter("p_vehicle_id", null);

            List<Vehicle> resultList = procedureCall.getResultList();
            if (resultList == null || resultList.isEmpty()) {
                throw new Exception("No vehicles in the database");
            }
            vehicles.addAll(resultList);
        } catch (Exception ex) {
            throw ex;
        }
    }
}