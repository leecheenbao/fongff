package fongff.service;

import fongff.model.SysFunc;

import java.util.List;

public interface SysFuncService {

    List<SysFunc> findAll();

    List<SysFunc> findByModuleAndStates(String module);

    List<SysFunc> findByModule(String module);

    List<SysFunc> findByModuleAndCategory(String module,String category);
    SysFunc findOne(Integer indexR);

    void save(SysFunc sysFunc);

    void delete(SysFunc sysFunc);

    Integer getLastIndexR();

}
