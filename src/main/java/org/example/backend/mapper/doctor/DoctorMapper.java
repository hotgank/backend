package org.example.backend.mapper.doctor;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.backend.entity.doctor.Doctor;

@Mapper
public interface DoctorMapper extends BaseMapper<Doctor> {
}