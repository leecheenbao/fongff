package fongff.serviceImpl;

import fongff.model.SysFunc;
import fongff.model.SysFuncId;
import fongff.repository.SysFuncRepository;
import fongff.service.SysFuncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SysFuncServiceImpl implements SysFuncService {
    private static final Logger logger = LoggerFactory.getLogger(SysFuncServiceImpl.class);

    @Autowired
    private SysFuncRepository sysFuncRepository;

    @Override
    public void save(SysFunc sysFunc) {
        sysFuncRepository.save(sysFunc);
    }

    @Override
    public void delete(SysFunc sysFunc) {
        sysFuncRepository.delete(sysFunc);
    }

    @Override
    public SysFunc findOne(SysFuncId sysFuncId) {
        SysFunc sysFunc = new SysFunc();
        sysFunc.setSysFuncId(sysFuncId);
        Example example = Example.of(sysFunc);
        Optional<SysFunc> optional = sysFuncRepository.findOne(example);
        if (optional.isPresent()) {
            sysFunc = optional.get();
        } else {
            sysFunc = null;
        }
        return sysFunc;
    }

    @Override
    public List<SysFunc> findAll() {
        return sysFuncRepository.findAll();
    }

    @Override
    public List<SysFunc> findByModule(String module) {
        List<SysFunc> sysFuncs =  sysFuncRepository.findByModule(module);
        return sysFuncs;
    }


    public static void main(String[] args) {
        String str = "123456789";
        System.out.println(str.substring(0, 1));
        System.out.println(str.substring(1, 3));
        System.out.println(str.substring(3, 5));
    }
}
