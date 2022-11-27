package fongff.repository;

import fongff.model.SysLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SysLogRepository extends JpaRepository<SysLog, String> {
    @Query(value = "SELECT * FROM sys_log sl WHERE ip=:ip AND createTime BETWEEN :startDate AND :endDate ", nativeQuery = true)
    SysLog findByIp(String ip,String startDate , String endDate);

    @Query(value = "SELECT * FROM sys_log sl WHERE createTime BETWEEN :startDate AND :endDate ", nativeQuery = true)
    List<SysLog> getDailyCount(String startDate , String endDate);

    @Query(value = "SELECT * FROM sys_log", nativeQuery = true)
    List<SysLog> getTotalCount();

}
