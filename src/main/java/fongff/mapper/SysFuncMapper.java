package fongff.mapper;

import fongff.model.SysFunc;
import fongff.dto.SysFuncDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface SysFuncMapper {
    SysFuncMapper INSTANCE = Mappers.getMapper(SysFuncMapper.class);

    SysFunc sysFuncDtoToSysFunc(SysFuncDto sysFuncDto);

    SysFuncDto sysFuncToSysFuncDto(SysFunc sysFunc);

}
