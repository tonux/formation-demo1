package com.ca.formation.formationdemo1.controllers;

import com.ca.formation.formationdemo1.FormationDemo1Application;
import com.ca.formation.formationdemo1.models.Personne;
import com.ca.formation.formationdemo1.services.PersonneService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PersonneControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    PersonneService personneService;

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String getRootUrl(){
        return "http://localhost:" + port + "/api/v2";
    }

    @Test
    public void helloTest(){
        assertEquals(this.restTemplate.getForObject("http://localhost:" + port + "/api/v2/personnes/hello", String.class), "Bonjour tout le monde");
    }

    @Test
    public void getPersonnes() throws Exception{
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v2/personnes")
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }


    @Test
    public void getPersonnesVrai() throws Exception{
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange("http://localhost:" + port + "/api/v2/personnes",
                HttpMethod.GET, entity, String.class);

        assertNotNull(response.getBody());

    }

    @Test
    public void getPersonne_() throws Exception{
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        Personne personne = restTemplate.getForObject(getRootUrl()+"/personnes/3", Personne.class);

        assertNotNull(personne);
        assertEquals(personne.getNom(), "Abdel");

    }

    @Test
    public void ajouterPersonne_() throws Exception{
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        Personne personne = restTemplate.postForObject(getRootUrl()+"/personnes", new Personne("tonux", "samb", 40), Personne.class);

        assertNotNull(personne);
        assertEquals(personne.getNom(), "tonux");

    }

    @Test
    public void getPersonne() throws Exception{
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v2/personnes/2")
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();

        System.out.println(contentAsString);
        assertNotNull(contentAsString);


    }

}
