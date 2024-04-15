package az.edu.ada.mstest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsTestApplication.class, args);
    }

}
