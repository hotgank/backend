package org.example.backend.service.admin;

public interface AdminManageService {
    String generateRegisterCode(String email);

    String isEmailExist(String email);

    boolean validateRegisterCode(String email, String registerCode);
}
