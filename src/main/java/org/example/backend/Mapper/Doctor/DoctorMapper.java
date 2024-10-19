package org.example.backend.Mapper.Doctor;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.backend.Entity.Doctor.Doctor;

@Mapper
public interface DoctorMapper extends BaseMapper<Doctor> {
}