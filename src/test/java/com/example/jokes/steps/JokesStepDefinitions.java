package com.example.jokes.steps;

import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.AssertionsForClassTypes.not;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration
public class JokesStepDefinitions {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;
    private ResultActions successResultActions;
    private ResultActions failureResultActions;
    private ResultActions rateLimitorResultActions;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @When("I make a successful GET request to {string}")
    public void setSuccessResultActions(String url) throws Exception {
        successResultActions = mockMvc.perform(MockMvcRequestBuilders.get(url));
    }

    @Then("the response status code should be {int}")
    public void theResponseStatusCodeShouldBe(int code) throws Exception {
        successResultActions.andExpect(status().is(code));
    }

    @Then("the response body should be {string}")
    public void theResponseBodyShouldBe(String type) throws Exception {
        successResultActions.andExpect(content().contentType(type));
    }

    @And("the response body should contain a joke")
    public void theResponseBodyShouldContainAJoke() throws Exception {
        successResultActions.andExpect(jsonPath("$.content").isNotEmpty());
    }

    @When("I make a failure GET request to {string}")
    public void setFailureResultActions(String url) throws Exception {
        failureResultActions = mockMvc.perform(MockMvcRequestBuilders.get(url));
    }

    @Then("the failure response body should be {string}")
    public void theResponseBodyShouldContainContentType(String type) throws Exception {
        failureResultActions.andExpect(content().contentType(type));
    }

    @Then("the failure response status code should be {int}")
    public void theFailureResponseBodyShouldReturnCode(int code) throws Exception {
        failureResultActions.andExpect(status().is(code));
    }

    @And("the response body should contain an error")
    public void theResponseBodyShouldContainAnError() throws Exception {
        failureResultActions.andExpect(jsonPath("$.message").isNotEmpty());
    }

    @When("I make a rate limiter GET request to {string}")
    public void setRateLimiterResultActions(String url) throws Exception {
        rateLimitorResultActions = mockMvc.perform(MockMvcRequestBuilders.get(url));
    }

    @When("I make another rate limiter GET request to {string}")
    public void updateRateLimiterResultActions(String url) throws Exception {
        rateLimitorResultActions = mockMvc.perform(MockMvcRequestBuilders.get(url));
    }

    @Then("the rate limiter response status code should be {int}")
    public void theRateLimiterResponseBodyShouldReturnCode(int code) throws Exception {
        rateLimitorResultActions.andExpect(status().is(code));
    }
}
