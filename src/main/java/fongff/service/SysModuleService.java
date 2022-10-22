package fongff.service;

import fongff.model.SysModule;

import java.util.List;

public interface SysModuleService {

	List<SysModule> findAll();

	List<SysModule> findByNormal(String role);

	SysModule findOne(String id);

	void save(SysModule sysModule);

	void delete(String id);
}
