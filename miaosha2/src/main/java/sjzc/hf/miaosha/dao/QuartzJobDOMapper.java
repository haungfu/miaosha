package sjzc.hf.miaosha.dao;

import java.util.List;
import java.util.Map;

import sjzc.hf.miaosha.dataobject.ScheduleJobEntity;

public interface QuartzJobDOMapper {
    int deleteByPrimaryKey(Long jobId);
    
    int deleteByPrimaryKeys(Long[] jobId);

    int insert(ScheduleJobEntity record);

    int insertSelective(ScheduleJobEntity record);

    ScheduleJobEntity selectByPrimaryKey(Long jobId);

    int updateByPrimaryKeySelective(ScheduleJobEntity record);

    int updateByPrimaryKey(ScheduleJobEntity record);
    
    int updateBatch(Map<String,Object> map);
    
    List<ScheduleJobEntity> queryList(Map<String, Object> map);
    
    int queryTotal(Map<String, Object> map);
}