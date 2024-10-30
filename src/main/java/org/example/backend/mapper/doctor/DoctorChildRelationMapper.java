package org.example.backend.mapper.doctor;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.backend.entity.doctor.DoctorChildRelation;

@Mapper
public interface DoctorChildRelationMapper extends BaseMapper<DoctorChildRelation> {
}