package ink.vor.ruedocto.user.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author muquanrui
 * @date 2022/6/20 19:00
 */

@Configuration
@MapperScan("ink.vor.ruedocto.user.mapper")
public class UserConfig {
}
