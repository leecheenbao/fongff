package fongff.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import fongff.model.SysFunc;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * A DTO for the {@link SysFunc} entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysFuncDto implements Serializable {
    private Integer indexR;
    private String module;
    private String category;
    private String image;
    private String title;
    private String content;
    private String auth;
    private String url;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date postDate;
    private Integer states = 0;
}