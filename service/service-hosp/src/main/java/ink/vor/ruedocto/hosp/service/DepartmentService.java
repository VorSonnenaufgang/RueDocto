package ink.vor.ruedocto.hosp.service;

import ink.vor.ruedocto.model.hosp.Department;
import ink.vor.ruedocto.vo.hosp.DepartmentQueryVo;
import org.springframework.data.domain.Page;

import java.util.Map;

/**
 * @author muquanrui
 * @date 2022/5/16 20:09
 */
public interface DepartmentService {
    void save(Map<String, Object> paramMap);
    Page<Department> findPageDepartment(int page, int limit, DepartmentQueryVo departmentQueryVo);
    void removeDepartment(String hoscode, String depcode);
}
