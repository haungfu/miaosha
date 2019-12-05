package sjzc.hf.miaosha.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sjzc.hf.miaosha.dataobject.ScheduleJobEntity;
import sjzc.hf.miaosha.response.R;
import sjzc.hf.miaosha.service.ScheduleJobService;
import sjzc.hf.miaosha.validator.ValidatorImpl;

/**
 * 定时任务
 *
 * @author asiainfo
 *
 * @date 2016年11月28日 下午2:16:40
 */
@RestController
@RequestMapping("/sys/schedule")
public class ScheduleJobController {
	@Autowired
	private ScheduleJobService scheduleJobService;

	/**
	 * 定时任务列表
	 */
//	@RequestMapping("/list")
//	@RequiresPermissions("sys:schedule:list")
//	public R list(@RequestParam Map<String, Object> params, HttpServletRequest request){
//		//查询列表数据
//		Query query = new Query(params);
//		Page<Object> page = PageHelper.startPage(request);
//		List<ScheduleJobEntity> jobList = scheduleJobService.queryList(query);
//		PageUtils pageUtil = new PageUtils(jobList, page);
//		return R.ok().put("page", pageUtil);
//	}

	/**
	 * 定时任务信息
	 */
	@RequestMapping("/info/{jobId}")
	@RequiresPermissions("sys:schedule:info")
	public R info(@PathVariable("jobId") Long jobId){
		ScheduleJobEntity schedule = scheduleJobService.queryObject(jobId);

		return R.ok().put("schedule", schedule);
	}

	/**
	 * 保存定时任务
	 * @throws Exception 
	 */
//	@SysLog("保存定时任务")
	@RequestMapping("/save")
	@RequiresPermissions("sys:schedule:save")
	public R save(@RequestBody ScheduleJobEntity scheduleJob) throws Exception{
		ValidatorImpl.validata(scheduleJob);

		scheduleJobService.save(scheduleJob);

		return R.ok();
	}

	/**
	 * 修改定时任务
	 * @throws Exception 
	 */
//	@SysLog("修改定时任务")
	@RequestMapping("/update")
	@RequiresPermissions("sys:schedule:update")
	public R update(@RequestBody ScheduleJobEntity scheduleJob) throws Exception{
		ValidatorImpl.validata(scheduleJob);

		scheduleJobService.update(scheduleJob);

		return R.ok();
	}

	/**
	 * 删除定时任务
	 */
//	@SysLog("删除定时任务")
	@RequestMapping("/delete")
	@RequiresPermissions("sys:schedule:delete")
	public R delete(@RequestBody Long[] jobIds){
		scheduleJobService.deleteBatch(jobIds);

		return R.ok();
	}

	/**
	 * 立即执行任务
	 */
//	@SysLog("立即执行任务")
	@RequestMapping("/run")
	@RequiresPermissions("sys:schedule:run")
	public R run(@RequestBody Long[] jobIds){
		scheduleJobService.run(jobIds);

		return R.ok();
	}

	/**
	 * 暂停定时任务
	 */
//	@SysLog("暂停定时任务")
	@RequestMapping("/pause")
	@RequiresPermissions("sys:schedule:pause")
	public R pause(@RequestBody Long[] jobIds){
		scheduleJobService.pause(jobIds);

		return R.ok();
	}

	/**
	 * 恢复定时任务
	 */
//	@SysLog("恢复定时任务")
	@RequestMapping("/resume")
	@RequiresPermissions("sys:schedule:resume")
	public R resume(@RequestBody Long[] jobIds){
		scheduleJobService.resume(jobIds);

		return R.ok();
	}

}
