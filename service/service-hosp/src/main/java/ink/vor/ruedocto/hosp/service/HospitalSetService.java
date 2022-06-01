package ink.vor.ruedocto.hosp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import ink.vor.ruedocto.model.hosp.HospitalSet;

/**
 * @author muquanrui
 * @date 2022/4/15 11:51
 */
public interface HospitalSetService extends IService<HospitalSet> {
    String getSignKey(String hoscode);
}
