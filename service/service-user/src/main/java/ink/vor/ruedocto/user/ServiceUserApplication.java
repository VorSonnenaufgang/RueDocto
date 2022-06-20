package ink.vor.ruedocto.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author muquanrui
 * @date 2022/6/20 18:55
 */
@SpringBootApplication
@ComponentScan(basePackages = "ink.vor")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "ink.vor")
public class ServiceUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceUserApplication.class, args);
    }
}
