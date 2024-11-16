package org.example.backend;

import org.example.backend.service.doctor.DoctorService;
import org.example.backend.service.serviceImpl.doctor.DoctorServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class Base64Test {

  private DoctorService doctorService = new DoctorServiceImpl();
  @Test
    public void Test() {
        String doctorId = "D-d45b4b41-6465-437c-a8e6-8676f9e9a7d1";

      System.out.println(doctorService.getAvatarBase64(doctorId));
    }

}
