package fongff.serviceImpl;

import fongff.model.SysFunc;
import fongff.repository.SysFuncRepository;
import fongff.service.SysFuncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SysFuncServiceImpl implements SysFuncService {
    private static final Logger logger = LoggerFactory.getLogger(SysFuncServiceImpl.class);

    @Autowired
    private SysFuncRepository sysFuncRepository;

    @Override
    public void save(SysFunc sysFunc) {
        sysFuncRepository.save(sysFunc);
    }

    @Override
    public void delete(SysFunc sysFunc) {
        sysFuncRepository.delete(sysFunc);
    }

    @Override
    public Integer getLastIndexR() {
        return sysFuncRepository.getLastIndexR() + 1;
    }

    @Override
    public SysFunc findOne(Integer indexR) {
        return sysFuncRepository.findByIndexR(indexR);
    }

    @Override
    public List<SysFunc> findAll() {
        return sysFuncRepository.findAll(orderByOrderIdAndpostDate());
    }

    @Override
    public List<SysFunc> findByModuleAndStates(String module) {
        List<SysFunc> sysFuncs = sysFuncRepository.findByModuleAndStates(module, 0, orderByOrderIdAndpostDate());
        return sysFuncs;
    }

    @Override
    public List<SysFunc> findByModule(String module) {
        List<SysFunc> sysFuncs = sysFuncRepository.findByModule(module, orderByOrderIdAndpostDate());
        return sysFuncs;
    }

    @Override
    public List<SysFunc> findByModuleAndCategory(String module, String category) {
        List<SysFunc> sysFuncs = sysFuncRepository.findByModuleAndCategory(module, category, orderByOrderIdAndpostDate());
        return sysFuncs;
    }

    /* 排序條件 */
    public Sort orderByOrderIdAndpostDate() {
        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.ASC, "orderId"));
        orders.add(new Sort.Order(Sort.Direction.DESC, "postDate"));
        Sort sort = Sort.by(orders);
        return sort;
    }

}
