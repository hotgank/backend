package org.example.backend.Mapper.User;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.backend.Entity.User.ParentChildRelation;

@Mapper
public interface ParentChildRelationMapper extends BaseMapper<ParentChildRelation> {
}