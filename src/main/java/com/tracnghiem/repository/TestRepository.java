package com.tracnghiem.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TestRepository {

    @Autowired
    private SessionFactory sessionFactory;

    public boolean testConnection() {

        try {

            Session session = sessionFactory.getCurrentSession();

            session.createNativeQuery("SELECT 1")
                    .getSingleResult();

            return true;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }
}