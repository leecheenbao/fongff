package fongff.serviceImpl;

import fongff.model.SysModule;
import fongff.repository.SysModuleRepository;
import fongff.service.SysModuleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SysModuleServiceImpl implements SysModuleService {
	private static final Logger logger = LoggerFactory.getLogger(SysModuleServiceImpl.class);

	@Autowired
	private SysModuleRepository sysModuleRepository;

	@Override
	public List<SysModule> findAll() {
		return sysModuleRepository.findAll();
	}

	@Override
	public SysModule findOne(String id) {
		SysModule sysModule = new SysModule();
		Optional<SysModule> optional = sysModuleRepository.findById(id);
		if (optional.isPresent()) {
			sysModule = optional.get();
		}
		return sysModule;
	}

	@Override
	public void save(SysModule sysModule) {
		sysModuleRepository.save(sysModule);
	}

	@Override
	public void delete(String id) {
		SysModule sysModule = new SysModule();
		sysModule = sysModuleRepository.getById(id);
		sysModuleRepository.delete(sysModule);
	}
}
