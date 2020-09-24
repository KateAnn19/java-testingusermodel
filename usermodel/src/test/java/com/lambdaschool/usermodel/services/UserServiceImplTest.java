package com.lambdaschool.usermodel.services;

import com.lambdaschool.usermodel.UserModelApplication;
import com.lambdaschool.usermodel.models.Role;
import com.lambdaschool.usermodel.models.User;
import com.lambdaschool.usermodel.models.UserRoles;
import com.lambdaschool.usermodel.models.Useremail;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserModelApplication.class)
public class UserServiceImplTest
{
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Before
    public void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);
        List<User> myList = userService.findAll();
        //print out test data
        for(User u : myList)
        {
         System.out.println(u.getUsername());
        }
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void a_findUserById()
    {
       assertEquals("test barnbarn", userService.findUserById(11).getUsername());
    }

    @Test(expected = EntityNotFoundException.class)
    public void a_findUserByIdFails()
    {
        assertEquals("test barnbarn", userService.findUserById(888).getUsername());
    }

    @Test
    public void b_findByNameContaining()
    {
        assertEquals(1, userService.findByNameContaining("barnbarn").size());
    }

    @Test
    public void c_findAll()
    {
        assertEquals(5, userService.findAll().size());
    }

    @Test
    public void d_delete()
    {
        userService.delete(7);
        assertEquals(5, userService.findAll().size()); //running findAll again here
    }

    @Test
    public void e_findByName()
    {
        assertEquals("test barnbarn", userService.findByName("test barnbarn").getUsername());
    }

    @Test(expected = EntityNotFoundException.class)
    public void eadeletenotfound()
    {
        userService.delete(9999);
        assertEquals(4, userService.findAll().size());
    }

    @Test(expected = EntityNotFoundException.class)
    public void ebfindRestaurantByNameNotFound()
    {
        assertEquals("Apple", userService.findByName("Apple").getUsername());
    }

    @Test
    public void f_save()
    {
        User newUser = new User("test one",
            "pass one",
            "myemail@email");

        User addUser = userService.save(newUser);
        User foundUser = userService.findUserById(addUser.getUserid());
        assertNotNull(addUser);
        assertEquals(addUser.getUsername(), foundUser.getUsername());
    }

    @Test
    public void g_update()
    {
        User newUser = new User("graham",
            "treats",
            "doggy@bowwow.mail");

        User updateUser = userService.update(newUser,
            11);
        assertEquals("doggy@bowwow.mail",
            updateUser.getPrimaryemail());
    }

    @Test(expected = EntityNotFoundException.class)
    public void ga_updateFail()
    {
        User newUser = new User("graham",
            "treats",
            "doggy@bowwow.mail");

        User updateUser = userService.update(newUser,
            333);
        assertEquals("doggy@bowwow.mail",
            updateUser.getPrimaryemail());
    }

    @Test
    public void h_deleteAll()
    {
    }

}