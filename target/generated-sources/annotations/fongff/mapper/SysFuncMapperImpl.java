package fongff.mapper;

import fongff.dto.SysFuncDto;
import fongff.model.SysFunc;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-10-23T01:12:41+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 1.8.0_221 (Oracle Corporation)"
)
@Component
public class SysFuncMapperImpl implements SysFuncMapper {

    @Override
    public SysFunc sysFuncDtoToSysFunc(SysFuncDto sysFuncDto) {
        if ( sysFuncDto == null ) {
            return null;
        }

        SysFunc sysFunc = new SysFunc();

        sysFunc.setIndexR( sysFuncDto.getIndexR() );
        sysFunc.setModule( sysFuncDto.getModule() );
        sysFunc.setCategory( sysFuncDto.getCategory() );
        sysFunc.setImage( sysFuncDto.getImage() );
        sysFunc.setUrl( sysFuncDto.getUrl() );
        sysFunc.setTitle( sysFuncDto.getTitle() );
        sysFunc.setContent( sysFuncDto.getContent() );
        sysFunc.setAuth( sysFuncDto.getAuth() );
        sysFunc.setPostDate( sysFuncDto.getPostDate() );
        sysFunc.setStates( sysFuncDto.getStates() );

        return sysFunc;
    }

    @Override
    public SysFuncDto sysFuncToSysFuncDto(SysFunc sysFunc) {
        if ( sysFunc == null ) {
            return null;
        }

        SysFuncDto sysFuncDto = new SysFuncDto();

        sysFuncDto.setIndexR( sysFunc.getIndexR() );
        sysFuncDto.setModule( sysFunc.getModule() );
        sysFuncDto.setCategory( sysFunc.getCategory() );
        sysFuncDto.setImage( sysFunc.getImage() );
        sysFuncDto.setTitle( sysFunc.getTitle() );
        sysFuncDto.setContent( sysFunc.getContent() );
        sysFuncDto.setAuth( sysFunc.getAuth() );
        sysFuncDto.setUrl( sysFunc.getUrl() );
        sysFuncDto.setPostDate( sysFunc.getPostDate() );
        sysFuncDto.setStates( sysFunc.getStates() );

        return sysFuncDto;
    }
}
