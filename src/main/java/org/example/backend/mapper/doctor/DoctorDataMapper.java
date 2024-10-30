package org.example.backend.mapper.doctor;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.backend.entity.doctor.DoctorData;

@Mapper
public interface DoctorDataMapper extends BaseMapper<DoctorData> {
}