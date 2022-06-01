package ink.vor.ruedocto.hosp.repository;

import ink.vor.ruedocto.model.hosp.Department;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author muquanrui
 * @date 2022/5/16 20:09
 */

@Repository
public interface DepartmentRepository extends MongoRepository<Department, String> {
    Department getDepartmentByHoscodeAndDepcode(String hoscode, String depcode);
}
