package com.tracnghiem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tracnghiem.repository.TestRepository;

@Service
public class TestService {

    @Autowired
    private TestRepository testRepository;

    @Transactional
    public boolean testConnection() {

        return testRepository.testConnection();
    }
}