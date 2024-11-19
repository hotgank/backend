package org.example.backend.service.doctor;

import java.util.List;
import org.example.backend.entity.doctor.Doctor;
import org.example.backend.entity.doctor.DoctorUserRelation;
import org.example.backend.entity.others.DoctorWithStatus;
import org.example.backend.entity.user.User;

public interface DoctorUserRelationService {

  List<User> selectMyPatients(String doctorId, String relationStatus);

  List<User> selectRecentPatients(String doctorId, String relationStatus);

  List<Doctor> selectMyDoctors(String userId);

  List<DoctorWithStatus> selectPendingDoctors(String userId);

  int createDoctorUserRelation(DoctorUserRelation relation);

  boolean updateDoctorUserRelation(DoctorUserRelation relation);

  boolean deleteDoctorUserRelation(DoctorUserRelation relation);

  List<DoctorUserRelation> selectPendingPatients(String doctorId, String relationStatus);

  DoctorUserRelation selectDoctorUserRelationByIDs(String doctorId, String userId);



    /**
     * 根据医生 ID 获取医生-用户关系
     * @param doctorId 医生的唯一标识
     * @return 医生-用户关系的列表
     */
    public List<DoctorUserRelation> getRelationsByDoctorId(String doctorId);

    /**
     * 根据用户 ID 获取用户-医生关系
     * @param userId 用户的唯一标识
     * @return 用户-医生关系的列表
     */
    public List<DoctorUserRelation> getRelationsByUserId(String userId);
}
