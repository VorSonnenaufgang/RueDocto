package ink.vor.ruedocto.hosp.service;

import ink.vor.ruedocto.model.hosp.Hospital;
import ink.vor.ruedocto.vo.hosp.HospitalQueryVo;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author muquanrui
 * @date 2022/4/27 08:43
 */

public interface HospitalService {
    void save(Map<String, Object> paramMap);
    Hospital getByHoscode(String hoscode);

    /**
     * 分页查询
     * @param page 当前页码
     * @param limit 每页记录数
     * @param hospitalQueryVo 查询条件
     * @return
     */
    Page<Hospital> selectPage(Integer page, Integer limit, HospitalQueryVo hospitalQueryVo);
}
