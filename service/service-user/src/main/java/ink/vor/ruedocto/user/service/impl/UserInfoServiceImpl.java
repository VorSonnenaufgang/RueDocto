package ink.vor.ruedocto.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ink.vor.ruedocto.model.user.UserInfo;
import ink.vor.ruedocto.user.mapper.UserInfoMapper;
import ink.vor.ruedocto.user.service.UserInfoService;
import org.springframework.stereotype.Service;

/**
 * @author muquanrui
 * @date 2022/6/20 18:59
 */
@Service
public class UserInfoServiceImpl extends
        ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {
}
