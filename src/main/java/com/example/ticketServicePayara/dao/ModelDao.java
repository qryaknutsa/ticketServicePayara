//package com.example.ticketServicePayara.dao;
//
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//@Repository
//public abstract class ModelDao<T> {
//    @Autowired
//    private SessionFactory sessionFactory;
//
//    protected Session getCurrentSession() {
//        return sessionFactory.getCurrentSession();
//    }
//
//    protected abstract Class<T> getEntityClass();
//
//    @Transactional(readOnly = true)
//    public List<T> getAll(){
//        return getCurrentSession().createQuery("from " + getEntityClass().getName(), getEntityClass()).list();
//    }
//    @Transactional(readOnly = true)
//    public T getById(int id){
//        return getCurrentSession().get(getEntityClass(), id);
//    }
//    @Transactional
//    public T save(T entity){
//        getCurrentSession().save(entity);
//        return entity;
//    }
//    @Transactional
//    public T update(int id, T entity){
//        getCurrentSession().update(entity);
//        return entity;
//    }
//    @Transactional
//    public void deleteById(int id){
//        T entity = getCurrentSession().byId(getEntityClass()).load(id);
//        getCurrentSession().delete(entity);
//    }
//    @Transactional
//    public void delete(T entity){
//        getCurrentSession().delete(entity);
//    }
//}
