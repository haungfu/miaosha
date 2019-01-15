package sjzc.hf.miaosha.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sjzc.hf.miaosha.dao.QuartzJobLogDOMapper;
import sjzc.hf.miaosha.dataobject.ScheduleJobLogEntity;
import sjzc.hf.miaosha.service.ScheduleJobLogService;


@Service
public class ScheduleJobLogServiceImpl implements ScheduleJobLogService {
	@Autowired
	private QuartzJobLogDOMapper quartzJobLogDOMapper;
	
	@Override
	public ScheduleJobLogEntity queryObject(Long jobId) {
		return quartzJobLogDOMapper.selectByPrimaryKey(jobId);
	}

	@Override
	public List<ScheduleJobLogEntity> queryList(Map<String, Object> map) {
		return quartzJobLogDOMapper.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return quartzJobLogDOMapper.queryTotal(map);
	}

	@Override
	public void save(ScheduleJobLogEntity log) {
		quartzJobLogDOMapper.insertSelective(log);
	}

}
