package com.app.library;

import com.app.library.controllers.UserController;
import com.app.library.enums.GenderEnum;
import com.app.library.models.User;
import com.app.library.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Date;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
class LibraryApplicationTests {
    @Autowired
    private UserController userController;

    private MockMvc mvc;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext){
        this.mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();
    }
    @Test
    void contextLoads() throws Exception {
        assertThat(userController).isNotNull();
    }

    @Test
    public void givenUser_whenSave_thenGetOk() {
        User user = new User("john", "taramas", new Date(2023, 12, 12), new Date(2023, 12, 31), GenderEnum.MALE);
        User user2 = userRepository.saveAndFlush(user);
        assertEquals("john", user2.getName());
    }

    @Test
    public void canRetrieveAllUsersWithBook_thenStatus200() throws Exception {

        // when
        MockHttpServletResponse response = mvc.perform(get("/user/getAllUsersWithBook").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains("James");
    }
}
