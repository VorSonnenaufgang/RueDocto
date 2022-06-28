package ink.vor.ruedocto.hosp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import ink.vor.ruedocto.model.hosp.HospitalSet;
import ink.vor.ruedocto.vo.order.SignInfoVo;

/**
 * @author muquanrui
 * @date 2022/4/15 11:51
 */
public interface HospitalSetService extends IService<HospitalSet> {
    String getSignKey(String hoscode);

    //获取医院签名信息
    SignInfoVo getSignInfoVo(String hoscode);
}
