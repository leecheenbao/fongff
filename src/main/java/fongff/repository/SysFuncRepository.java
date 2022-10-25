package fongff.repository;

import fongff.model.SysFunc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SysFuncRepository extends JpaRepository<SysFunc, String> {

    SysFunc findByIndexR(Integer indexR);

    @Query(value = "select * from sys_func sf where module =:module and states = 0", nativeQuery = true)
    List<SysFunc> findByModuleAndStates(@Param("module") String module);

    List<SysFunc> findByModule(String module);

    List<SysFunc> findByModuleAndCategory(String module,String category);

    @Query(value = "SELECT indexR FROM sys_func sf order by indexR DESC LIMIT 1;", nativeQuery = true)
    Integer getLastIndexR();
}
