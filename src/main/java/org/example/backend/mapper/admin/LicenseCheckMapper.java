package org.example.backend.mapper.admin;

import org.apache.ibatis.annotations.*;
import org.example.backend.dto.AdminGetDoctorLicenseDTO;
import org.example.backend.entity.admin.LicenseCheck;

import java.util.List;

@Mapper
public interface LicenseCheckMapper {

  @Select(
          "SELECT Count(lc.audit_id) "
                  + "FROM a_license_check lc "
                  + "JOIN d_doctors d ON lc.doctor_id = d.doctor_id "
                  + "JOIN o_hospitals o ON d.workplace = o.hospital_name "
                  + "JOIN a_admins a ON a.admin_id = #{adminId} "
                  + "WHERE lc.status = '未认证' "
                  + "AND ("
                  + "(a.admin_type = 'second' AND a.admin_id = o.admin_id) "
                  + "OR (a.admin_type = 'first' AND d.workplace NOT IN (SELECT h.hospital_name FROM o_hospitals h WHERE h.admin_id IS NOT NULL)) "
                  + "OR (a.admin_type = 'super')"
                  + ") "
                  + "ORDER BY lc.created_at"
  )
  int selectPendingCount(@Param("adminId") String adminId);

  @Select(
          "SELECT lc.audit_id, lc.doctor_id, d.name, d.gender, d.position, d.workplace, lc.url "
                  + "FROM a_license_check lc "
                  + "JOIN d_doctors d ON lc.doctor_id = d.doctor_id "
                  + "JOIN o_hospitals o ON d.workplace = o.hospital_name "
                  + "JOIN a_admins a ON a.admin_id = #{adminId} "
                  + "WHERE lc.status = '未认证' "
                  + "AND ("
                  + "(a.admin_type = 'second' AND a.admin_id = o.admin_id) "
                  + "OR (a.admin_type = 'first' AND d.workplace NOT IN (SELECT h.hospital_name FROM o_hospitals h WHERE h.admin_id IS NOT NULL)) "
                  + "OR (a.admin_type = 'super')"
                  + ") "
                  + "ORDER BY lc.created_at"
  )
  @Results({
    @Result(column = "audit_id", property = "auditId"),
    @Result(column = "doctor_id", property = "doctorId"),
    @Result(column = "name", property = "name"),
    @Result(column = "gender", property = "gender"),
    @Result(column = "position", property = "position"),
    @Result(column = "workplace", property = "workplace"),
    @Result(column = "url", property = "url")
  })
  List<AdminGetDoctorLicenseDTO> adminSelectAll(@Param("adminId") String adminId);

  @Select(
          "SELECT lc.audit_id, lc.doctor_id, d.name, d.gender, d.position, d.workplace, lc.url "
                  + "FROM a_license_check lc "
                  + "JOIN d_doctors d ON lc.doctor_id = d.doctor_id "
                  + "JOIN o_hospitals o ON d.workplace = o.hospital_name "
                  + "JOIN a_admins a ON a.admin_id = #{adminId} "
                  + "WHERE lc.status = '未认证' "
                  + "AND ("
                  + "(a.admin_type = 'second' AND a.admin_id = o.admin_id) "
                  + "OR (a.admin_type = 'first' AND d.workplace NOT IN (SELECT h.hospital_name FROM o_hospitals h WHERE h.admin_id IS NOT NULL)) "
                  + "OR (a.admin_type = 'super')"
                  + ") "
                  + "ORDER BY lc.created_at DESC LIMIT 5")
  @Results({
    @Result(column = "audit_id", property = "auditId"),
    @Result(column = "doctor_id", property = "doctorId"),
    @Result(column = "name", property = "name"),
    @Result(column = "gender", property = "gender"),
    @Result(column = "position", property = "position"),
    @Result(column = "workplace", property = "workplace"),
    @Result(column = "url", property = "url")
  })
  List<AdminGetDoctorLicenseDTO> adminSelectRecent(@Param("adminId") String adminId);

  @Select(
      "SELECT audit_id, doctor_id, admin_id, status, created_at, url, updated_at, comment FROM a_license_check "
          + "WHERE audit_id = #{auditId}")
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

  @Update(
      "UPDATE a_license_check SET doctor_id = #{doctorId}, admin_id = #{adminId}, status = #{status}, "
          + "created_at = #{createdAt}, url = #{url}, updated_at = #{updatedAt}, comment = #{comment} "
          + "WHERE audit_id = #{auditId}")
  boolean update(LicenseCheck licenseCheck);

  @Insert(
      "INSERT INTO a_license_check (audit_id, doctor_id, admin_id, status, created_at, url, updated_at, comment) "
          + "VALUES (#{auditId}, #{doctorId}, #{adminId}, #{status}, #{createdAt}, #{url}, #{updatedAt}, #{comment})")
  boolean insert(LicenseCheck licenseCheck);

  @Select("SELECT * FROM a_license_check WHERE doctor_id = #{doctorId} ORDER BY updated_at DESC")
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

  @Delete("DELETE FROM a_license_check WHERE doctor_id = #{doctorId} AND status = '未认证'")
  boolean deletePendingByDoctorId(@Param("doctorId") String doctorId);
}
