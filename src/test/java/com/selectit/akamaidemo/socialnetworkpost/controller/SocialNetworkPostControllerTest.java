package com.selectit.akamaidemo.socialnetworkpost.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.selectit.akamaidemo.socialnetworkpost.model.SocialNetworkPost;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@SqlGroup({@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:test/snp_test.sql")})
class SocialNetworkPostControllerTest {
  public static final int ID_ONE = 1;
  public static final int ID_FIVE = 5;
  public static final int FAKE_ID = 44;
  @Autowired
  private WebApplicationContext wac;
  @Autowired
  private ObjectMapper objectMapper;
  private MockMvc mockMvc;

  @BeforeAll
  public void setupBeforeAll() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
  }

  @Test
  public void whenSendingGet_thenResultIsOKAndCountIsIncremented() throws Exception {

    this.mockMvc
        .perform(get("/api/socialnetwork/posts/{id}", ID_ONE)
            .accept(MediaType.APPLICATION_JSON))
        .andDo(print()).andExpect(status().isOk())
        .andExpect(content().json("{'id':1,'postDate':'2023-01-22T22:54:25.459852','author':'author1', 'content': 'content1', 'viewCount': 23}"));
  }

  @Test
  public void whenSendingGetWithFalseId_thenStatusIs404() throws Exception {

    this.mockMvc
        .perform(get("/api/socialnetwork/posts/{id}", FAKE_ID)
            .accept(MediaType.APPLICATION_JSON))
        .andDo(print()).andExpect(status().isNotFound())
        .andExpect(jsonPath("$.message", is("POST_NOT_FOUND")));
  }

  @Test
  public void whenGettingTop10_thenOrderIsCorrect() throws Exception {

    this.mockMvc
        .perform(get("/api/socialnetwork/posts/top10")
            .accept(MediaType.APPLICATION_JSON))
        .andDo(print()).andExpect(status().isOk())
        .andExpect(jsonPath("$[0].author", is("author5")))
        .andExpect(jsonPath("$[1].author", is("author1")))
        .andExpect(jsonPath("$[2].author", is("author12")))
        .andExpect(jsonPath("$[3].id", is(2)))
        .andExpect(jsonPath("$[4].id", is(3)))
        .andExpect(jsonPath("$[5].id", is(4)))
        .andExpect(jsonPath("$[6].id", is(6)))
        .andExpect(jsonPath("$[7].id", is(8)))
        .andExpect(jsonPath("$[8].id", is(7)))
        .andExpect(jsonPath("$[9].id", is(9)));
  }

  @Test
  public void whenEditing_thenResultIsOKAndResultIsReturned() throws Exception {
    final SocialNetworkPost snp = SocialNetworkPost.builder()
        .id(1L)
        .postDate(LocalDateTime.parse("2023-01-22T20:54:25.459852"))
        .author("author1")
        .content("content1.1")
        .viewCount(22L).build();
    this.mockMvc
        .perform(put("/api/socialnetwork/posts/1")
            .content(objectMapper.writeValueAsString(snp))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andDo(print()).andExpect(status().isOk())
        .andExpect(content().json("{'id':1,'postDate':'2023-01-22T22:54:25.459852','author':'author1', 'content': 'content1.1', 'viewCount': 22}"));
  }

  @Test
  public void whenEditingButResourceDoesNotExist_thenResultIsCreatedAndReturned() throws Exception {
    final SocialNetworkPost snp = SocialNetworkPost.builder()
        .id(111L)
        .author("author13")
        .content("content1.2").build();
    this.mockMvc
        .perform(put("/api/socialnetwork/posts/{id}", 111)
            .content(objectMapper.writeValueAsString(snp))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andDo(print()).andExpect(status().isCreated())
        .andExpect(content().json("{'id':13,'author':'author13', 'content': 'content1.2', 'viewCount': 1}"));
  }
}