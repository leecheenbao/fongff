package fongff.repository;

import fongff.model.SysModule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SysModuleRepository extends JpaRepository<SysModule, String> {

    List<SysModule> findByRole(String role);
}
