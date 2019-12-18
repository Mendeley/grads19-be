package com.gradproject2019.conferences;

import com.gradproject2019.conferences.repository.ConferenceSearchRepository;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.elasticsearch.ElasticsearchContainer;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ElasticsearchIT {

    @ClassRule
    public static ElasticsearchContainer container = new ElasticsearchContainer();
    @Autowired
    ConferenceSearchRepository repository;

    @BeforeClass
    public static void before() {
        System.setProperty("spring.data.elasticsearch.cluster-nodes", container.getContainerIpAddress() + ":" + container.getMappedPort(9300));
    }
}
