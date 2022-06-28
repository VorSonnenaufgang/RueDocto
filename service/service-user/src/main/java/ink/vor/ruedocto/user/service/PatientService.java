package ink.vor.ruedocto.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import ink.vor.ruedocto.model.user.Patient;

import java.util.List;

/**
 * @author muquanrui
 * @date 2022/6/23 10:20
 */
public interface PatientService extends IService<Patient> {
    //获取就诊人列表
    List<Patient> findAllUserId(Long userId);
    //根据id获取就诊人信息
    Patient getPatientId(Long id);
}