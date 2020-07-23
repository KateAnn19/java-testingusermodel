package com.lambdaschool.usermodel.controllers;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import com.lambdaschool.usermodel.models.Role;
import com.lambdaschool.usermodel.models.User;
import com.lambdaschool.usermodel.models.UserRoles;
import com.lambdaschool.usermodel.models.Useremail;
import com.lambdaschool.usermodel.services.RoleService;
import com.lambdaschool.usermodel.services.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@WebMvcTest(value = UserController.class)
public class UserControllerTest
{
    @Autowired
    private MockMvc mockMvc; //allows mocking of different classes so we don't test actual
    //restaurant service. we just make use of it

    List<User> userList;

    @MockBean
    private UserService userService; //MockBean calls fake Restaurant Service

    @MockBean
    private RoleService roleService;

    @Before
    public void setUp() throws Exception
    {
        userList = new ArrayList<>();

        Role r1 = new Role("Test admin");
        r1.setRoleid(1);
        Role r2 = new Role("Test user");
        r2.setRoleid(2);
        Role r3 = new Role("Test data");
        r3.setRoleid(3);

        // admin, data, user
        User u1 = new User("Test admin",
            "password",
            "admin@lambdaschool.local");

        u1.getRoles().add(new UserRoles(u1, r1));
        u1.getRoles().add(new UserRoles(u1, r2));
        u1.getRoles().add(new UserRoles(u1, r3));
        u1.getUseremails()
            .add(new Useremail(u1,
                "admin@email.local"));
        u1.getUseremails()
            .add(new Useremail(u1,
                "admin@mymail.local"));

        //userService.save(u1);

        // data, user
        User u2 = new User("Test cinnamon",
            "1234567",
            "cinnamon@lambdaschool.local");
        u2.getRoles().add(new UserRoles(u2, r2));
        u2.getRoles().add(new UserRoles(u2, r3));
        u2.getUseremails()
            .add(new Useremail(u2,
                "cinnamon@mymail.local"));
        u2.getUseremails()
            .add(new Useremail(u2,
                "hops@mymail.local"));
        u2.getUseremails()
            .add(new Useremail(u2,
                "bunny@email.local"));
        //userService.save(u2);

        // user
        User u3 = new User("Test barnbarn",
            "ILuvM4th!",
            "barnbarn@lambdaschool.local");
        u3.getRoles().add(new UserRoles(u3, r2));
        u3.getUseremails()
            .add(new Useremail(u3,
                "barnbarn@email.local"));
        //userService.save(u3);

        User u4 = new User("Test puttat",
            "password",
            "puttat@school.lambda");
        u4.getRoles().add(new UserRoles(u4, r2));
        userService.save(u4);

        User u5 = new User("Test misskitty",
            "password",
            "misskitty@school.lambda");
        u5.getRoles().add(new UserRoles(u5, r2));
        //userService.save(u5);
    }


    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void listAllUsers()
    {
    }

    @Test
    public void getUserById()
    {
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
    public void addNewUser()
    {
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
    public void deleteUserById()
    {
    }
}