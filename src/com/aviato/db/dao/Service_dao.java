package com.aviato.db.dao;

import com.aviato.Types.Service;
import com.aviato.Types.ServiceItem;
import com.aviato.db.HibernateUtil;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.procedure.ProcedureCall;

import java.util.List;

public class Service_dao {

    public static void InsertService(Service service) throws Exception {
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
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw ex;
        }
    }

    public static Service GetService(Long serviceId) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            ProcedureCall procedureCall = session.getNamedProcedureCall("GetService");
            procedureCall.setParameter("p_service_id", serviceId);

            List<Service> results = procedureCall.getResultList();
            if (results == null || results.isEmpty()) {
                throw new Exception("No service with ID: " + serviceId);
            }
            return results.get(0);
        } catch (Exception ex) {
            throw ex;
        }
    }

    public static void UpdateService(Service service) throws Exception {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            ProcedureCall procedureCall = session.getNamedProcedureCall("UpdateService");
            procedureCall.setParameter("p_service_id", service.getServiceId());
            procedureCall.setParameter("p_status", service.getStatus());
            procedureCall.setParameter("p_cost", service.getCost());

            procedureCall.execute();
            Long updatedId = (Long) procedureCall.getOutputParameterValue("p_service_id_out");
            if (updatedId == null || !updatedId.equals(service.getServiceId())) {
                throw new Exception("Failed to update service with ID: " + service.getServiceId());
            }

            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw ex;
        }
    }

    public static void DeleteService(Long serviceId) throws Exception {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            ProcedureCall procedureCall = session.getNamedProcedureCall("DeleteService");
            procedureCall.setParameter("p_service_id", serviceId);

            procedureCall.execute();
            Long deletedId = (Long) procedureCall.getOutputParameterValue("p_service_id_out");
            if (deletedId == null || !deletedId.equals(serviceId)) {
                throw new Exception("Failed to delete service with ID: " + serviceId);
            }

            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw ex;
        }
    }

    public static void GetAllServices(ObservableList<Service> services) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            services.clear();

            ProcedureCall procedureCall = session.getNamedProcedureCall("GetAllServices");
            procedureCall.setParameter("p_service_id", null);

            List<Service> resultList = procedureCall.getResultList();
            if (resultList == null || resultList.isEmpty()) {
                throw new Exception("No services in the database");
            }
            services.addAll(resultList);
        } catch (Exception ex) {
            throw ex;
        }
    }

    public static void UseInventory(Long serviceId, Long itemId, Integer quantityUsed) throws Exception {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            ProcedureCall procedureCall = session.getNamedProcedureCall("UseInventory");
            procedureCall.setParameter("p_service_id", serviceId);
            procedureCall.setParameter("p_item_id", itemId);
            procedureCall.setParameter("p_quantity_used", quantityUsed);

            procedureCall.execute();
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw ex;
        }
    }

    public static void GetServiceInventory(Long serviceId, ObservableList<ServiceItem> items) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            items.clear();

            ProcedureCall procedureCall = session.getNamedProcedureCall("GetServiceInventory");
            procedureCall.setParameter("p_service_id", serviceId);

            List<ServiceItem> resultList = procedureCall.getResultList();
            if (resultList == null || resultList.isEmpty()) {
                throw new Exception("No inventory items found for service ID: " + serviceId);
            }
            items.addAll(resultList);
        } catch (Exception ex) {
            throw ex;
        }
    }
}