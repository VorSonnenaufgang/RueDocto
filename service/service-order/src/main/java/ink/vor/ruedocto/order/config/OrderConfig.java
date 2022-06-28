package ink.vor.ruedocto.order.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author muquanrui
 * @date 2022/6/24 11:27
 */

@Configuration
@MapperScan("ink.vor.ruedocto.order.mapper")
public class OrderConfig {
}
