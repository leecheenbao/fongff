package fongff.serviceImpl;

import fongff.model.SysLog;
import fongff.repository.SysLogRepository;
import fongff.service.SysLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class SysLogServiceImpl implements SysLogService {
	private static final Logger logger = LoggerFactory.getLogger(SysLogServiceImpl.class);

	@Autowired
	private SysLogRepository sysLogRepository;


	@Override
	public List<SysLog> findAll() {
		return sysLogRepository.findAll();
	}


	@Override
	public void save(SysLog sysModule) {
		sysLogRepository.save(sysModule);
	}

	@Override
	public void delete(String id) {
		SysLog sysLog = new SysLog();
		sysLog = sysLogRepository.getById(id);
		sysLogRepository.delete(sysLog);
	}

	@Override
	public List<SysLog> getDailyCount() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String start = new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(new Date());
		String end = new SimpleDateFormat("yyyy-MM-dd 23:59:59").format(new Date());
		return sysLogRepository.getDailyCount(start ,end);
	}

	@Override
	public List<SysLog> getTotalCount() {
		return sysLogRepository.getTotalCount();
	}

	@Override
	public SysLog findByIp(String ip){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String start = new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(new Date());
		String end = new SimpleDateFormat("yyyy-MM-dd 23:59:59").format(new Date());
		return sysLogRepository.findByIp(ip,start,end);
	}
}
