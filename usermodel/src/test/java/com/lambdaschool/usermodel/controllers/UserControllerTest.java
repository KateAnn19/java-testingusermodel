package com.lambdaschool.usermodel.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambdaschool.usermodel.UserModelApplication;
import com.lambdaschool.usermodel.models.Role;
import com.lambdaschool.usermodel.models.User;
import com.lambdaschool.usermodel.models.UserRoles;
import com.lambdaschool.usermodel.models.Useremail;
import com.lambdaschool.usermodel.services.RoleService;
import com.lambdaschool.usermodel.services.UserService;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)

@SpringBootTest(classes = UserModelApplication.class)
@WithMockUser(username = "admin", roles = {"ADMIN", "DATA"})
public class UserControllerTest
{
    @Autowired
    private WebApplicationContext webApplicationContext;


    private MockMvc mockMvc; //allows mocking of different classes so we don't test actual
    //restaurant service. we just make use of it

    @MockBean
    private UserService userService; //MockBean calls fake Restaurant Service

    @MockBean
    private RoleService roleService;


    List<User> userList = new ArrayList<>();

    @Before
    public void setUp() throws Exception
    {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .apply(SecurityMockMvcConfigurers.springSecurity())
            .build();

        Role r1 = new Role("Test admin");
        r1.setRoleid(1);

        Role r2 = new Role("Test user");
        r2.setRoleid(2);

        Role r3 = new Role("Test data");
        r3.setRoleid(3);

        // admin, data, user
        User u1 = new User("Test admin", "password", "admin@lambdaschool.local");
        u1.setUserid(11);

        u1.getRoles().add(new UserRoles(u1, r1));
        u1.getRoles().add(new UserRoles(u1, r2));
        u1.getRoles().add(new UserRoles(u1, r3));


        u1.getUseremails().add(new Useremail(u1, "admin@email.local"));
        u1.getUseremails().get(0).setUseremailid(1);

        u1.getUseremails().add(new Useremail(u1, "admin@mymail.local"));
        u1.getUseremails().get(1).setUseremailid(2);

        userList.add(u1);

        // data, user
        User u2 = new User("Test cinnamon",
            "1234567",
            "cinnamon@lambdaschool.local");
        u2.setUserid(12);

        u2.getRoles().add(new UserRoles(u2, r2));


        u2.getRoles().add(new UserRoles(u2, r3));

        u2.getUseremails().add(new Useremail(u2, "cinnamon@mymail.local"));
        u2.getUseremails().get(0).setUseremailid(3);
        u2.getUseremails()
            .add(new Useremail(u2,
                "hops@mymail.local"));
        u2.getUseremails().get(1).setUseremailid(4);
        u2.getUseremails()
            .add(new Useremail(u2,
                "bunny@email.local"));
        u2.getUseremails().get(2).setUseremailid(5);
        userList.add(u2);

        // user
        User u3 = new User("Test barnbarn",
            "ILuvM4th!",
            "barnbarn@lambdaschool.local");
        u3.setUserid(13);

        u3.getRoles().add(new UserRoles(u3, r2));
        u3.getUseremails()
            .add(new Useremail(u3,
                "barnbarn@email.local"));
        u3.getUseremails().get(0).setUseremailid(6);
        userList.add(u3);

        User u4 = new User("Test puttat",
            "password",
            "puttat@school.lambda");
        u4.setUserid(14);
        u4.getRoles().add(new UserRoles(u4, r2));
        userList.add(u4);

        User u5 = new User("Test misskitty",
            "password",
            "misskitty@school.lambda");
        u5.setUserid(15);
        u5.getRoles().add(new UserRoles(u5, r2));
        userList.add(u5);
    }


    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void listAllUsers() throws Exception
    {
        String apiUrl = "/users/users";
        Mockito.when(userService.findAll()).thenReturn(userList);

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl).accept(MediaType.APPLICATION_JSON);
        MvcResult r = mockMvc.perform(rb).andReturn();
        String tr = r.getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(userList);

        assertEquals(er, tr);
    }

    @Test
    public void getUserById() throws Exception
    {
        String apiUrl = "/users/user/12";
        Mockito.when(userService.findUserById(12)).thenReturn(userList.get(0));

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl).accept(MediaType.APPLICATION_JSON);
        MvcResult r = mockMvc.perform(rb).andReturn();
        String tr = r.getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(userList.get(0));

        assertEquals(er, tr);

    }

    @Test
    public void getUserByIdNotFound() throws Exception
    {
        String apiUrl = "/users/user/777";
        Mockito.when(userService.findUserById(777)).thenReturn(null);

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl).accept(MediaType.APPLICATION_JSON);
        MvcResult r = mockMvc.perform(rb).andReturn();
        String tr = r.getResponse().getContentAsString();

        String er = "";

        TestCase.assertEquals(er, tr);
    }

    @Test
    public void getUserByName()
    {
    }

    @Test
    public void getUserLikeName()
    {
    }

    @Test
    public void addNewUser() throws Exception
    {
        String apiUrl="/users/user";

        //build a user
        User newUser = new User("test one",
            "pass one",
            "myemail@email");

        newUser.setUserid(13);

        ObjectMapper mapper = new ObjectMapper();
        String restaurantString = mapper.writeValueAsString(newUser);

        Mockito.when(userService.save(any(User.class))).thenReturn(newUser);

        RequestBuilder rb = MockMvcRequestBuilders.post(apiUrl)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(restaurantString);
        mockMvc.perform(rb).andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void updateFullUser()
    {
    }

    @Test
    public void updateUser()
    {
    }

    @Test
    public void deleteUserById() throws Exception
    {
        String apiUrl = "/users/user/{id}";

        RequestBuilder rb = MockMvcRequestBuilders.delete(apiUrl, "13")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(rb)
            .andExpect(status().isOk())
            .andDo(MockMvcResultHandlers.print());
    }
}