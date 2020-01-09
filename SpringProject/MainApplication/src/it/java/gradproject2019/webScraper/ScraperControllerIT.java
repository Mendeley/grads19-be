package gradproject2019.webScraper;

import gradproject2019.conferences.data.ConferenceResponseDto;
import gradproject2019.utils.TestUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.net.URISyntaxException;

import static org.springframework.http.HttpMethod.POST;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ScraperControllerIT extends TestUtils {

    @LocalServerPort
    int testServerPort;

    private String baseUri;

    @Before
    public void setUp() {
        universalSetUp();
        baseUri = "http://localhost:" + testServerPort + "/add";
    }

    @After
    public void tearDown() {
        clearRepositories();
    }

    @Test
    public void shouldReturn200WhenURLIsSubmitted() throws URISyntaxException {
        clearRepositories();;
        URI uri = new URI(baseUri);
        String conferenceURL = "https://www.eventbrite.co.uk/e/ucl-ai-in-medicine-conference-2020-tickets-85348905875?aff=ebdssbdestsearch";

        ResponseEntity<ConferenceResponseDto> response = getConferenceURL(uri, conferenceURL);

        Assert.assertEquals(200, response.getStatusCodeValue());
    }

    private ResponseEntity<ConferenceResponseDto> getConferenceURL(URI uri, String conferenceURL) {
        return restTemplate.exchange(uri, POST, new HttpEntity<>(conferenceURL, passingHeaders), new ParameterizedTypeReference<ConferenceResponseDto>() {

        });
    }

}