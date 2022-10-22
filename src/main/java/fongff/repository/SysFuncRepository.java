package fongff.repository;

import fongff.model.SysFunc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SysFuncRepository extends JpaRepository<SysFunc, String> {

    SysFunc findByIndexR(Integer indexR);

    @Query(value = "select * from sys_func sf where module =:module", nativeQuery = true)
    List<SysFunc> findByModule(@Param("module") String module);

    List<SysFunc> findByModuleAndCategory(String module,String category);

    @Query(value = "select count(*)+1 from sys_func sf", nativeQuery = true)
    Integer getLastIndexR();
}
