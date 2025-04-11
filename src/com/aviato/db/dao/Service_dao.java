package com.aviato.db.dao;

import com.aviato.Types.Service;
import com.aviato.Types.ServiceItem;
import com.aviato.db.HibernateUtil;
import javafx.concurrent.Task;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.procedure.ParameterRegistration;
import org.hibernate.procedure.ProcedureCall;

import java.util.List;

public class Service_dao {

    public static Task<Void> insertServiceTask(Service service) {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Transaction transaction = null;
                try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                    transaction = session.beginTransaction();
                    ProcedureCall procedureCall = session.getNamedProcedureCall("InsertService");
                    procedureCall.setParameter("p_service_type", service.getServiceType());
                    procedureCall.setParameter("p_service_date", service.getServiceDate());
                    procedureCall.setParameter("p_status", service.getStatus());
                    procedureCall.setParameter("p_cost", service.getCost());

                    procedureCall.execute();
                    Long generatedId = (Long) procedureCall.getOutputParameterValue("p_service_id");
                    service.setServiceId(generatedId);
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

    public static Task<Service> getServiceTask(Long serviceId) {
        return new Task<Service>() {
            @Override
            protected Service call() throws Exception {
                try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                    ProcedureCall procedureCall = session.getNamedProcedureCall("GetService");
                    procedureCall.setParameter("p_service_id", serviceId);

                    List<Service> results = procedureCall.getResultList();
                    if (results == null || results.isEmpty()) {
                        throw new Exception("No service with ID: " + serviceId);
                    }
                    return results.get(0);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    throw ex;
                }
            }
        };
    }

    public static Task<Void> updateServiceTask(Service service) {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Transaction transaction = null;
                try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                    transaction = session.beginTransaction();
                    ProcedureCall procedureCall = session.getNamedProcedureCall("UpdateService");
                    procedureCall.setParameter("p_service_id", service.getServiceId());
                    procedureCall.setParameter("p_status", service.getStatus());
                    procedureCall.setParameter("p_cost", service.getCost());

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

    public static Task<Void> deleteServiceTask(Long serviceId) {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Transaction transaction = null;
                try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                    transaction = session.beginTransaction();
                    ProcedureCall procedureCall = session.getNamedProcedureCall("DeleteService");
                    procedureCall.setParameter("p_service_id", serviceId);

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

    public static Task<List<Service>> getAllServicesTask() {
        return new Task<List<Service>>() {
            @Override
            protected List<Service> call() throws Exception {
                try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                    ProcedureCall procedureCall = session.getNamedProcedureCall("GetAllServices");
                    ParameterRegistration<Long> param = procedureCall.getParameterRegistration("p_service_id");
                    param.enablePassingNulls(true);
                    procedureCall.setParameter("p_service_id", null);

                    List<Service> resultList = procedureCall.getResultList();
                    if (resultList == null || resultList.isEmpty()) {
                        throw new Exception("No services in the database");
                    }
                    return resultList;
                } catch (Exception ex) {
                    ex.printStackTrace();
                    throw ex;
                }
            }
        };
    }

    public static Task<Void> useInventoryTask(Long serviceId, Long itemId, Integer quantityUsed) {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Transaction transaction = null;
                try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                    transaction = session.beginTransaction();
                    ProcedureCall procedureCall = session.getNamedProcedureCall("UseInventory");
                    procedureCall.setParameter("p_service_id", serviceId);
                    procedureCall.setParameter("p_item_id", itemId);
                    procedureCall.setParameter("p_quantity_used", quantityUsed);

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

    public static Task<List<ServiceItem>> getServiceInventoryTask(Long serviceId) {
        return new Task<List<ServiceItem>>() {
            @Override
            protected List<ServiceItem> call() throws Exception {
                try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                    ProcedureCall procedureCall = session.getNamedProcedureCall("GetServiceInventory");
                    procedureCall.setParameter("p_item_id", serviceId); // Note: This should be adjusted based on actual logic

                    List<ServiceItem> resultList = procedureCall.getResultList();
                    if (resultList == null || resultList.isEmpty()) {
                        throw new Exception("No inventory items found for service ID: " + serviceId);
                    }
                    return resultList;
                } catch (Exception ex) {
                    ex.printStackTrace();
                    throw ex;
                }
            }
        };
    }

    public static Task<List<ServiceItem>> getAllServiceInventoryTask() {
        return new Task<List<ServiceItem>>() {
            @Override
            protected List<ServiceItem> call() throws Exception {
                try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                    ProcedureCall procedureCall = session.getNamedProcedureCall("GetAllServiceInventory");
                    ParameterRegistration<Long> param = procedureCall.getParameterRegistration("p_item_id");
                    param.enablePassingNulls(true);
                    procedureCall.setParameter("p_item_id", null);

                    List<ServiceItem> resultList = procedureCall.getResultList();
                    if (resultList == null || resultList.isEmpty()) {
                        throw new Exception("No inventory items found");
                    }
                    return resultList;
                } catch (Exception ex) {
                    ex.printStackTrace();
                    throw ex;
                }
            }
        };
    }

    public static Task<List<Service>> searchServiceByTypeTask(String typeName) {
        return new Task<List<Service>>() {
            @Override
            protected List<Service> call() throws Exception {
                try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                    ProcedureCall procedureCall = session.getNamedProcedureCall("SearchServiceByType");
                    procedureCall.setParameter("p_service_type", typeName);

                    List<Service> resultList = procedureCall.getResultList();
                    if (resultList == null || resultList.isEmpty()) {
                        throw new Exception("No services found for type: " + typeName);
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