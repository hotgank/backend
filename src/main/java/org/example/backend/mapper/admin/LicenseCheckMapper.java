package org.example.backend.mapper.admin;

import org.apache.ibatis.annotations.*;
import org.example.backend.dto.AdminGetDoctorLicenseDTO;
import org.example.backend.entity.admin.LicenseCheck;

import java.util.List;

@Mapper
public interface LicenseCheckMapper {
    @Select("SELECT audit_id, a_license_check.doctor_id, name, gender, position, workplace, url " +
            "FROM a_license_check, d_doctors WHERE a_license_check.doctor_id = d_doctors.doctor_id " +
            "AND a_license_check.status = '未认证' ORDER BY created_at")
    @Results({
            @Result(column = "audit_id", property = "auditId"),
            @Result(column = "doctor_id", property = "doctorId"),
            @Result(column = "name", property = "name"),
            @Result(column = "gender", property = "gender"),
            @Result(column = "position", property = "position"),
            @Result(column = "workplace", property = "workplace"),
            @Result(column = "url", property = "url")
    })
    List<AdminGetDoctorLicenseDTO> adminSelectAll();

    @Select("SELECT audit_id, a_license_check.doctor_id, name, gender, position, workplace, url " +
            "FROM a_license_check, d_doctors WHERE a_license_check.doctor_id = d_doctors.doctor_id " +
            "AND a_license_check.status = '未认证' ORDER BY created_at DESC LIMIT 5")
    @Results({
            @Result(column = "audit_id", property = "auditId"),
            @Result(column = "doctor_id", property = "doctorId"),
            @Result(column = "name", property = "name"),
            @Result(column = "gender", property = "gender"),
            @Result(column = "position", property = "position"),
            @Result(column = "workplace", property = "workplace"),
            @Result(column = "url", property = "url")
    })
    List<AdminGetDoctorLicenseDTO> adminSelectRecent();

    @Select("SELECT audit_id, doctor_id, admin_id, status, created_at, url, updated_at, comment FROM a_license_check " +
            "WHERE audit_id = #{auditId}")
    @Results({
            @Result(column = "audit_id", property = "auditId"),
            @Result(column = "doctor_id", property = "doctorId"),
            @Result(column = "admin_id", property = "adminId"),
            @Result(column = "status", property = "status"),
            @Result(column = "created_at", property = "createdAt"),
            @Result(column = "url", property = "url"),
            @Result(column = "updated_at", property = "updatedAt"),
    })
    LicenseCheck selectById(@Param("auditId") String auditId);

    @Update("UPDATE a_license_check SET doctor_id = #{doctorId}, admin_id = #{adminId}, status = #{status}, " +
            "created_at = #{createdAt}, url = #{url}, updated_at = #{updatedAt}, comment = #{comment} " +
            "WHERE audit_id = #{auditId}")
    boolean update(LicenseCheck licenseCheck);

    @Insert("INSERT INTO a_license_check (audit_id, doctor_id, admin_id, status, created_at, url, updated_at, comment) " +
            "VALUES (#{auditId}, #{doctorId}, #{adminId}, #{status}, #{createdAt}, #{url}, #{updatedAt}, #{comment})")
    boolean insert(LicenseCheck licenseCheck);

    @Select("SELECT * FROM a_license_check WHERE doctor_id = #{doctorId}")
    @Results({
            @Result(column = "audit_id", property = "auditId"),
            @Result(column = "doctor_id", property = "doctorId"),
            @Result(column = "admin_id", property = "adminId"),
            @Result(column = "status", property = "status"),
            @Result(column = "created_at", property = "createdAt"),
            @Result(column = "url", property = "url"),
            @Result(column = "updated_at", property = "updatedAt"),
    })
    List<LicenseCheck> selectByDoctorId(@Param("doctorId") String doctorId);

}
