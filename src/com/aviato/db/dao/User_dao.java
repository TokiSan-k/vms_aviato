package com.aviato.db.dao;

import com.aviato.Types.User;
import com.aviato.db.HibernateUtil;
import javafx.concurrent.Task;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.procedure.ParameterRegistration;
import org.hibernate.procedure.ProcedureCall;

import java.util.List;

public class User_dao {

    public static Task<Void> insertUserTask(User user) {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Transaction transaction = null;
                try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                    transaction = session.beginTransaction();
                    ProcedureCall procedureCall = session.getNamedProcedureCall("InsertUser");
                    procedureCall.setParameter("p_role_id", user.getRoleId());
                    procedureCall.setParameter("p_username", user.getUsername());
                    procedureCall.setParameter("p_email", user.getEmail());
                    procedureCall.setParameter("p_password", user.getPassword());

                    procedureCall.execute();
                    Long generatedId = (Long) procedureCall.getOutputParameterValue("p_user_id");
                    user.setUserId(generatedId);
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

    public static Task<User> getUserTask(Long userId) {
        return new Task<User>() {
            @Override
            protected User call() throws Exception {
                try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                    ProcedureCall procedureCall = session.getNamedProcedureCall("GetUser");
                    procedureCall.setParameter("p_user_id", userId);

                    List<User> results = procedureCall.getResultList();
                    if (results == null || results.isEmpty()) {
                        throw new Exception("No user with ID: " + userId);
                    }
                    return results.get(0);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    throw ex;
                }
            }
        };
    }

    public static Task<Void> updateUserTask(User user) {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Transaction transaction = null;
                try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                    transaction = session.beginTransaction();
                    ProcedureCall procedureCall = session.getNamedProcedureCall("UpdateUser");
                    procedureCall.setParameter("p_user_id", user.getUserId());
                    procedureCall.setParameter("p_username", user.getUsername());
                    procedureCall.setParameter("p_email", user.getEmail());
                    procedureCall.setParameter("p_password", user.getPassword());
                    procedureCall.setParameter("p_role_id", user.getRoleId());

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

    public static Task<Void> deleteUserTask(Long userId) {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Transaction transaction = null;
                try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                    transaction = session.beginTransaction();
                    ProcedureCall procedureCall = session.getNamedProcedureCall("DeleteUser");
                    procedureCall.setParameter("p_user_id", userId);

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

    public static Task<List<User>> getAllUsersTask() {
        return new Task<List<User>>() {
            @Override
            protected List<User> call() throws Exception {
                try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                    ProcedureCall procedureCall = session.getNamedProcedureCall("GetAllUsers");
                    ParameterRegistration<Long> param = procedureCall.getParameterRegistration("p_user_id");
                    param.enablePassingNulls(true);
                    procedureCall.setParameter("p_user_id", null);

                    List<User> resultList = procedureCall.getResultList();
                    if (resultList == null || resultList.isEmpty()) {
                        throw new Exception("No users in the database");
                    }
                    return resultList;
                } catch (Exception ex) {
                    ex.printStackTrace();
                    throw ex;
                }
            }
        };
    }

    public static Task<String> authenticateUserTask(String email, String password) {
        return new Task<String>() {
            @Override
            protected String call() throws Exception {
                Transaction transaction = null;
                try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                    transaction = session.beginTransaction();
                    ProcedureCall procedureCall = session.getNamedProcedureCall("AuthenticateUser");
                    procedureCall.setParameter("p_email", email);
                    procedureCall.setParameter("p_password", password);

                    procedureCall.execute();
                    String roleName = (String) procedureCall.getOutputParameterValue("p_role_name");
                    transaction.commit();

                    if (roleName == null) {
                        throw new Exception("Authentication failed: Invalid email or password");
                    }
                    return roleName;
                } catch (Exception ex) {
                    if (transaction != null) {
                        transaction.rollback();
                    }
                    throw ex;
                }
            }
        };
    }
}