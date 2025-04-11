package com.aviato.db.dao;

import com.aviato.Types.InventoryAlert;
import com.aviato.Types.Item;
import com.aviato.db.HibernateUtil;
import javafx.concurrent.Task;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.procedure.ParameterRegistration;
import org.hibernate.procedure.ProcedureCall;

import java.util.List;

public class Inventory_dao {

    public static Task<Void> insertItemTask(Item item) {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Transaction transaction = null;
                try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                    transaction = session.beginTransaction();

                    ProcedureCall procedureCall = session.getNamedProcedureCall("InsertItem");
                    procedureCall.setParameter("p_item_name", item.getItemName());
                    procedureCall.setParameter("p_quantity", item.getQuantity());
                    procedureCall.setParameter("p_price_per_unit", item.getPricePerUnit());

                    procedureCall.execute();
                    Long generatedId = (Long) procedureCall.getOutputParameterValue("p_item_id_out");
                    item.setItemId(generatedId);

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

    public static Task<Item> getItemTask(Long itemId) {
        return new Task<Item>() {
            @Override
            protected Item call() throws Exception {
                try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                    ProcedureCall procedureCall = session.getNamedProcedureCall("GetItem");
                    procedureCall.setParameter("p_item_id", itemId);

                    List<Item> results = procedureCall.getResultList();
                    if (results == null || results.isEmpty()) {
                        throw new Exception("No item with ID: " + itemId);
                    }
                    return results.get(0);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    throw ex;
                }
            }
        };
    }

    public static Task<Void> updateItemTask(Item item) {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Transaction transaction = null;
                try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                    transaction = session.beginTransaction();

                    ProcedureCall procedureCall = session.getNamedProcedureCall("UpdateItem");
                    procedureCall.setParameter("p_item_id", item.getItemId());
                    procedureCall.setParameter("p_quantity", item.getQuantity());
                    procedureCall.setParameter("p_price_per_unit", item.getPricePerUnit());

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

    public static Task<Void> deleteItemTask(Long itemId) {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Transaction transaction = null;
                try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                    transaction = session.beginTransaction();

                    ProcedureCall procedureCall = session.getNamedProcedureCall("DeleteItem");
                    procedureCall.setParameter("p_item_id", itemId);

                    procedureCall.execute();
                    Long deletedId = (Long) procedureCall.getOutputParameterValue("p_deleted_item_id");
                    if (deletedId == null || !deletedId.equals(itemId)) {
                        throw new Exception("Failed to delete item with ID: " + itemId);
                    }

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

    public static Task<List<Item>> getAllItemsTask() {
        return new Task<List<Item>>() {
            @Override
            protected List<Item> call() throws Exception {
                try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                    ProcedureCall procedureCall = session.getNamedProcedureCall("GetAllItems");
                    ParameterRegistration<Long> param = procedureCall.getParameterRegistration("p_item_id");
                    param.enablePassingNulls(true);
                    procedureCall.setParameter("p_item_id", null);

                    List<Item> resultList = procedureCall.getResultList();
                    if (resultList == null || resultList.isEmpty()) {
                        throw new Exception("No items in the database");
                    }
                    return resultList;
                } catch (Exception ex) {
                    ex.printStackTrace();
                    throw ex;
                }
            }
        };
    }

    public static Task<List<InventoryAlert>> getInventoryAlertsTask() {
        return new Task<List<InventoryAlert>>() {
            @Override
            protected List<InventoryAlert> call() throws Exception {
                try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                    ProcedureCall procedureCall = session.getNamedProcedureCall("GetInventoryAlerts");

                    List<InventoryAlert> resultList = procedureCall.getResultList();
                    if (resultList == null || resultList.isEmpty()) {
                        throw new Exception("No inventory alerts found (all items above threshold)");
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