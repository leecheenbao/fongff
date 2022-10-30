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
@Table(name = "sys_func", schema = "fongff")
public class SysFunc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "indexR",nullable = false)
    private Integer indexR;

    @Column(name = "module", nullable = false)
    private String module;

    @Column(name = "category", nullable = false)
    private String category;

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

    @Column(name = "remark", nullable = false)
    private String remark;

    @Column(name = "orderId",nullable = false)
    private Integer orderId;

    @ApiModelProperty("更新時間-系統自增")
    @Column(name = "postdate", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @CreationTimestamp
    private Date postDate;

    @Column(name = "states")
    private Integer states = 0;

}
