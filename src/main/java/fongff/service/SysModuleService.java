package fongff.service;

import fongff.model.SysModule;

import java.util.List;

public interface SysModuleService {

	List<SysModule> findAll();

	SysModule findOne(String id);

	void save(SysModule sysModule);

	void delete(String id);
}
