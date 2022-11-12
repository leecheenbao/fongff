package fongff.service;

import fongff.model.SysLog;

import java.util.List;

public interface SysLogService {

	List<SysLog> findAll();

	SysLog findByIp(String ip);

	void save(SysLog sysModule);

	void delete(String id);

	List<SysLog> getDailyCount();

	List<SysLog> getTotalCount();
}
