package com.gradproject2019;

import com.gradproject2019.conferences.persistance.Conference;
import com.gradproject2019.conferences.repository.ConferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

@SpringBootApplication
public class Application implements CommandLineRunner {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private ConferenceRepository conferenceRepository;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Transactional(readOnly = true)
	@Override
	public void run(String... args) throws Exception {

		System.out.println("DATASOURCE = " + dataSource);

		System.out.println("\n1.findAll()...");

		conferenceRepository.findAll().forEach(conference -> System.out.println(conference.getId()));

		System.out.println("Done!");

	}

}