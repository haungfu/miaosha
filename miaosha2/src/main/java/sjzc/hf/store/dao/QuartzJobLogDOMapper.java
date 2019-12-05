package sjzc.hf.miaosha.dao;

import java.util.List;
import java.util.Map;

import sjzc.hf.miaosha.dataobject.ScheduleJobLogEntity;

public interface QuartzJobLogDOMapper {
    int deleteByPrimaryKey(Long logId);

    int insert(ScheduleJobLogEntity record);

    int insertSelective(ScheduleJobLogEntity record);

    ScheduleJobLogEntity selectByPrimaryKey(Long logId);

    int updateByPrimaryKeySelective(ScheduleJobLogEntity record);

    int updateByPrimaryKey(ScheduleJobLogEntity record);
    
    List<ScheduleJobLogEntity> queryList(Map<String, Object> map);
    
    int queryTotal(Map<String, Object> map);
}