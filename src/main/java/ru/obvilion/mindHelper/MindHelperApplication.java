package ru.obvilion.mindHelper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;
import java.io.File;

@SpringBootApplication
public class MindHelperApplication {
	public static File mapsFolder = new File("C://Games/serv");

	public static void main(String[] args) {
		SpringApplication.run(MindHelperApplication.class, args);
	}

	@Bean MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		factory.setMaxFileSize(DataSize.ofMegabytes(4));
		factory.setMaxRequestSize(DataSize.ofMegabytes(4));
		return factory.createMultipartConfig();
	}
}
