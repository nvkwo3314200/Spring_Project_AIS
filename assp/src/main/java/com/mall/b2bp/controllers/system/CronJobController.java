package com.mall.b2bp.controllers.system;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

//import javax.annotation.Resource;


import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.b2bp.controllers.BaseConroller;
import com.mall.b2bp.enums.CronJob;
import com.mall.b2bp.exception.SystemException;
import com.mall.b2bp.utils.ConstantUtil;
import com.mall.b2bp.vos.ResponseData;
import com.mall.b2bp.vos.system.CronJobVo;

@Controller("CronJobController")
@RequestMapping(value = "/cronJob")
public class CronJobController extends BaseConroller {
    private static final Logger LOG = LoggerFactory.getLogger(CronJobController.class);
    
	//@Resource(name = "trigger")
	private Scheduler scheduler;
    
	@Secured({"ROLE_SYSTEMADMIN"})
    @RequestMapping(value = "/getCronJobList", method = {RequestMethod.GET},
            produces = {"application/xml", "application/json"})
    @ResponseBody
    public List<CronJobVo> getCronJobList() throws SystemException {
	    GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
	    Set<JobKey> jobKeys = null;
		try {
			jobKeys = scheduler.getJobKeys(matcher);
		} catch (SchedulerException e) {
			throw new SystemException("Get cron job list error", e);
		}

		List<CronJobVo> cronJobList = new ArrayList();
	    for (JobKey jobKey : jobKeys) {
	    	LOG.info(jobKey.getName());
	    	CronJobVo cronJobVo = new CronJobVo();
	    	cronJobVo.setJobId(jobKey.getName());
	    	cronJobVo.setJobName(CronJob.getJobName(cronJobVo.getJobId()));
	    	cronJobList.add(cronJobVo);
	    }
//	    Collections.sort(cronJobList, new Comparator<CronJobVo>() {
//	    	@Override
//	        public int compare(CronJobVo cronJob1, CronJobVo cronJob2) {
//	            return cronJob1.getJobName().compareTo(cronJob2.getJobName());
//	        }
//		});
    	return cronJobList;
    }
	
	@Secured({"ROLE_SYSTEMADMIN"})
	@RequestMapping(value="/runJob", method = {RequestMethod.POST}, produces={"application/xml", "application/json"})
	@ResponseBody
	public ResponseData<CronJobVo> runJob(@RequestParam(value = "cronJobId", required = true) final String cronJobId)throws SystemException{
		LOG.info(cronJobId);
		ResponseData<CronJobVo> responseData=(ResponseData<CronJobVo>) responseDataService.getReturnData(CronJobVo.class);
		JobKey jobKey = JobKey.jobKey(cronJobId, JobKey.DEFAULT_GROUP);
		try {
			scheduler.triggerJob(jobKey);
		} catch (SchedulerException e) {
			throw new SystemException("Run cron job error", e);
		}
		responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
		responseData.add("cron_job_run_success");
		String[] param = {cronJobId};
		responseData.putMessagesParamArray("cron_job_run_success", param);
		return responseData;
		
	}

}
