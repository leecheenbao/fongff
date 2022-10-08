package fongff.service;

import fongff.model.SysFuncId;
import fongff.model.SysFunc;

import java.util.List;

public interface SysFuncService {

    List<SysFunc> findAll();

    List<SysFunc> findByModule(String module);

    SysFunc findOne(SysFuncId sysFuncId);

    void save(SysFunc sysFunc);

    void delete(SysFunc sysFunc);

}
