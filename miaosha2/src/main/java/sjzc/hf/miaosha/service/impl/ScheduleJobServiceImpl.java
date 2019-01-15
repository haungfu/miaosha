package sjzc.hf.miaosha.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sjzc.hf.miaosha.dao.QuartzJobDOMapper;
import sjzc.hf.miaosha.dataobject.ScheduleJobEntity;
import sjzc.hf.miaosha.service.ScheduleJobService;
import sjzc.hf.miaosha.utils.ScheduleUtils;

@Service
public class ScheduleJobServiceImpl implements ScheduleJobService {
	private static Logger logger = LoggerFactory.getLogger(ScheduleJobServiceImpl.class);
	@Autowired
    private Scheduler scheduler;
	@Autowired
	private QuartzJobDOMapper quartzJobDOMapper;

	/**
	 * 项目启动时，初始化定时器
	 */
	@PostConstruct
	public void init(){
		logger.info("PostConstruct");
		List<ScheduleJobEntity> scheduleJobList = quartzJobDOMapper.queryList(new HashMap<String, Object>());
		for(ScheduleJobEntity scheduleJob : scheduleJobList){
			CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, scheduleJob.getJobId());
            //如果不存在，则创建
            if(cronTrigger == null) {
                ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
            }else {
                ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
            }
		}
	}

	@Override
	public ScheduleJobEntity queryObject(Long jobId) {
		return quartzJobDOMapper.selectByPrimaryKey(jobId);
	}

	@Override
	public List<ScheduleJobEntity> queryList(Map<String, Object> map) {
		return quartzJobDOMapper.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return quartzJobDOMapper.queryTotal(map);
	}

	@Override
	@Transactional
	public void save(ScheduleJobEntity scheduleJob) {
		scheduleJob.setCreateTime(new Date());
		scheduleJob.setStatus(0);
		quartzJobDOMapper.insertSelective(scheduleJob);
        ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
    }

	@Override
	@Transactional
	public void update(ScheduleJobEntity scheduleJob) {
        ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);

        quartzJobDOMapper.updateByPrimaryKeySelective(scheduleJob);
    }

	@Override
	@Transactional
    public void deleteBatch(Long[] jobIds) {
    	for(Long jobId : jobIds){
    		ScheduleUtils.deleteScheduleJob(scheduler, jobId);
    	}

    	//删除数据
    	quartzJobDOMapper.deleteByPrimaryKeys(jobIds);
	}

	@Override
    public int updateBatch(Long[] jobIds, int status){
    	Map<String, Object> map = new HashMap<>();
    	map.put("list", jobIds);
    	map.put("status", status);
    	return quartzJobDOMapper.updateBatch(map);
    }

	@Override
	@Transactional
    public void run(Long[] jobIds) {
    	for(Long jobId : jobIds){
    		ScheduleUtils.run(scheduler, queryObject(jobId));
    	}
    }

	@Override
	@Transactional
    public void pause(Long[] jobIds) {
        for(Long jobId : jobIds){
    		ScheduleUtils.pauseJob(scheduler, jobId);
    	}

    	updateBatch(jobIds,1);
    }

	@Override
	@Transactional
    public void resume(Long[] jobIds) {
    	for(Long jobId : jobIds){
    		ScheduleUtils.resumeJob(scheduler, jobId);
    	}

    	updateBatch(jobIds, 0);
    }

}
