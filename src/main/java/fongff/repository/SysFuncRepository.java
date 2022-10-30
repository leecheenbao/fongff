package fongff.repository;

import fongff.model.SysFunc;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
import java.util.List;

public interface SysFuncRepository extends JpaRepository<SysFunc, String> {

    SysFunc findByIndexR(Integer indexR);

//    @Query(value = "select * from sys_func sf where module =:module and states = 0", nativeQuery = true)
    List<SysFunc> findByModuleAndStates( String module,Integer states, Sort sort);

    List<SysFunc> findByModule(String module, Sort sort);

    List<SysFunc> findByModuleAndCategory(String module, String category, Sort sort);

    @Query(value = "SELECT indexR FROM sys_func sf order by indexR DESC LIMIT 1", nativeQuery = true)
    Integer getLastIndexR();
}
