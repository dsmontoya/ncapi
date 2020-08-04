package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@GetMapping("/agreements/{name}")
	Agreement getAgreement(@PathVariable String name) {
		Agreement agreement = new Agreement(name);
		return agreement;
	}
	
	@PostMapping("/agreements")
  Agreement newAgreement(@RequestBody Agreement newAgreement) {
		newAgreement.save();
    return newAgreement;
	}
}
