package ink.vor.ruedocto.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author muquanrui
 * @date 2022/6/24 11:20
 */
@SpringBootApplication
@ComponentScan(basePackages = {"ink.vor"})
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"ink.vor"})
public class ServiceOrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceOrderApplication.class, args);
    }
}
