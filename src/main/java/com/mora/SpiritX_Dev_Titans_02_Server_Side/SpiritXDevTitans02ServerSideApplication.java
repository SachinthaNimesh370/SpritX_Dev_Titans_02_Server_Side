package com.mora.SpiritX_Dev_Titans_02_Server_Side;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SpiritXDevTitans02ServerSideApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpiritXDevTitans02ServerSideApplication.class, args);
	}


}
