package fongff.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@ToString
@Table(name = "sys_log", schema = "fongff")
public class SysLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "logId",nullable = false)
    private Integer logId;

    @Column(name = "ip", nullable = false)
    private String ip;

    @Column(name = "path", nullable = false)
    private String path;

    @ApiModelProperty("更新時間-系統自增")
    @Column(name = "createTime", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @CreationTimestamp
    private Date createTime;

}
