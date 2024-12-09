package org.example.backend.mapper.others;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.example.backend.entity.others.DetectionAPI;

@Mapper
public interface DetectionAPIMapper {

  // 添加检测API
  @Insert(
      "INSERT INTO a_detection_api(api_id, api_type, number, state) VALUES(#{apiId},#{apiType},#{number},#{state})")
  int insertDetectionAPI(DetectionAPI detectionAPI);

  // 删除检测API
  @Delete("DELETE FROM a_detection_api WHERE api_id = #{apiId}")
  int deleteDetectionAPIById(int apiId);

  // 获取所有
  @Select("SELECT * FROM a_detection_api")
  @Results({
    @Result(column = "api_id", property = "apiId"),
    @Result(column = "api_type", property = "apiType"),
    @Result(column = "number", property = "number"),
    @Result(column = "state", property = "state")
  })
  List<DetectionAPI> selectAll();

  // 更新检测API
  @Update(
      "UPDATE a_detection_api SET api_type = #{apiType}, number = #{number}, state = #{state} WHERE api_id = #{apiId}")
  int updateDetectionAPI(DetectionAPI detectionAPI);

  //根据类型获取
  @Select("SELECT * FROM a_detection_api WHERE api_type = #{apiType}")
  @Results({
    @Result(column = "api_id", property = "apiId"),
    @Result(column = "api_type", property = "apiType"),
    @Result(column = "number", property = "number"),
    @Result(column = "state", property = "state")
  })
  DetectionAPI selectByType(String apiType);
}
