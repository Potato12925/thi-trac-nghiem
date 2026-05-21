package com.tracnghiem.service;

import java.util.List;

import com.tracnghiem.dto.ExamRegistrationDTO;
import com.tracnghiem.entity.GiaoVienDangKy;

public interface ExamRegistrationService {
    
    /**
     * Registers a new exam.
     * @param dto the registration data
     * @param userMaGv the logged in user's ID
     * @param role the logged in user's role (PGV or GIAOVIEN)
     * @throws Exception if validation or business logic fails
     */
    void registerExam(ExamRegistrationDTO dto, String userMaGv, String role) throws Exception;
    
    /**
     * Deletes an existing exam registration.
     * @param maLop class code
     * @param maMh subject code
     * @param lan exam try number
     * @param userMaGv the logged in user's ID
     * @param role the logged in user's role
     * @throws Exception if deletion is not allowed
     */
    void deleteExam(String maLop, String maMh, Short lan, String userMaGv, String role) throws Exception;
    
    /**
     * Retrieves the list of exam registrations depending on user role.
     * @param userMaGv the logged in user's ID
     * @param role the logged in user's role
     * @return List of GiaoVienDangKy
     */
    List<GiaoVienDangKy> getRegistrations(String userMaGv, String role);
}
