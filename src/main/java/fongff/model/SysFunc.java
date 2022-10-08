package fongff.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Data
@ToString
@Table(name = "sys_func", schema = "fongff")
public class SysFunc {

    @EmbeddedId
    private SysFuncId sysFuncId;

    @Column(name = "image", nullable = false)
    private String image;

    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "auth", nullable = false)
    private String auth;

    @ApiModelProperty("更新時間-系統自增")
    @Column(name = "postDate", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @CreationTimestamp
    private Date postDate;

    @Column(name = "states")
    private Integer states = 0;


}
