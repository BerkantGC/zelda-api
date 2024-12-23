package com.gc.zelda_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@SpringBootApplication
@EnableCaching
public class ZeldaApiApplication {

	public static void main(String[] args) throws IOException {
		Path path = Paths.get(".env");
		if (path.toFile().exists()) {
			try{
				Stream<String> lines = Files.lines(path);
				lines.filter(line -> line.contains("="))
				.forEach(line -> {
					String[] parts = line.split("=", 2);
					System.setProperty(parts[0].trim(), parts[1].trim());
				});
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		SpringApplication.run(ZeldaApiApplication.class, args);
	}

}
