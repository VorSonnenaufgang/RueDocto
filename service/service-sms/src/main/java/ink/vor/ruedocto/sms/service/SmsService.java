package ink.vor.ruedocto.sms.service;

import ink.vor.ruedocto.vo.msm.MsmVo;

/**
 * @author muquanrui
 * @date 2022/6/21 11:19
 */
public interface SmsService {
    //发送手机验证码
    boolean send(String phone, String code);

    // MQ发送短信
    boolean send(MsmVo msmVo);
}
