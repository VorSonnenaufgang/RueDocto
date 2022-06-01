package ink.vor.ruedocto.hosp.repository;

import ink.vor.ruedocto.model.hosp.Hospital;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author muquanrui
 * @date 2022/4/27 08:42
 */

@Repository
public interface HospitalRepository extends MongoRepository<Hospital, String> {
    // 判断是否存在记录
    Hospital getHospitalByHoscode(String hoscode);
}
