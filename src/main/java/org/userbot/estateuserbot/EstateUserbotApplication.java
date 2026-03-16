package org.userbot.estateuserbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.userbot.estateuserbot.config.TelegramProperties;

@SpringBootApplication
@EnableConfigurationProperties(TelegramProperties.class)
public class EstateUserbotApplication {

	public static void main(String[] args) {
		SpringApplication.run(EstateUserbotApplication.class, args);
	}

}
