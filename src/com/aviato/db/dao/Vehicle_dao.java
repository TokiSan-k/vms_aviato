package com.aviato.db.dao;

import com.aviato.Types.Vehicle;
import com.aviato.db.HibernateUtil;

import javafx.concurrent.Task;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.procedure.ParameterRegistration;
import org.hibernate.procedure.ProcedureCall;

import java.util.List;

public class Vehicle_dao {
    public static Task<Void> insertVehicleTask(Vehicle vehicle) {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
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

    public static Task<Vehicle> getVehicleTask(Long vehicleId) {
        return new Task<Vehicle>() {
            @Override
            protected Vehicle call() throws Exception {
                try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                    ProcedureCall procedureCall = session.getNamedProcedureCall("GetVehicle");
                    procedureCall.setParameter("p_vehicle_id", vehicleId);

                    List<Vehicle> results = procedureCall.getResultList();
                    if (results == null || results.isEmpty()) {
                        throw new Exception("No vehicle with ID: " + vehicleId);
                    }
                    return results.get(0);
                }
            }
        };
    }

    public static Task<Void> updateVehicleTask(Vehicle vehicle) {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
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

    public static Task<Void> deleteVehicleTask(Long vehicleId) {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Transaction transaction = null;
                try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                    transaction = session.beginTransaction();
                    ProcedureCall procedureCall = session.getNamedProcedureCall("DeleteVehicle");
                    procedureCall.setParameter("p_vehicle_id", vehicleId);

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

    public static Task<List<Vehicle>> getAllVehiclesTask() {
        return new Task<List<Vehicle>>() {
            @Override
            protected List<Vehicle> call() throws Exception {
                try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                    ProcedureCall procedureCall = session.getNamedProcedureCall("GetAllVehicles");
                    ParameterRegistration<Long> param = procedureCall.getParameterRegistration("p_vehicle_id");
                    param.enablePassingNulls(true);
                    procedureCall.setParameter("p_vehicle_id", null);

                    List<Vehicle> resultList = procedureCall.getResultList();
                    if (resultList == null || resultList.isEmpty()) {
                        throw new Exception("No vehicles in the database");
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