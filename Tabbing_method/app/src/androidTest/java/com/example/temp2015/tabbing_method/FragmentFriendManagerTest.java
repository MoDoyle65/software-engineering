package com.example.temp2015.tabbing_method;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by temp2015 on 23-Dec-16.
 */
public class FragmentFriendManagerTest {

    private String email;
    private boolean emailValid;
    private boolean valid;


    @Test
    public void isEmailValidtest0() throws Exception {
        FragmentFriendManager emailCheck = new FragmentFriendManager();
        email="viet.nguyen@ucdconnect.ie";
        emailValid = emailCheck.isEmailValid(email);
        valid = true;
        assertEquals("email is valid genuine email entered", emailValid, valid);
    }

    @Test
    public void isEmailValidtest1() throws Exception {
        FragmentFriendManager emailCheck = new FragmentFriendManager();
        email="viet.nguyen@ucdconnect";
        emailValid = emailCheck.isEmailValid(email);
        valid = true;
        assertEquals("email is valid but email but wrong format", emailValid, valid);
    }

    @Test
    public void isEmailValidtest2() throws Exception {
        FragmentFriendManager emailCheck = new FragmentFriendManager();
        email="viet.nguyenucdconnect.ie";
        emailValid = emailCheck.isEmailValid(email);
        valid = false;
        assertEquals("email is invalid missing @ sign", emailValid, valid);
    }

    @Test
    public void isEmailValidtest3() throws Exception {
        FragmentFriendManager emailCheck = new FragmentFriendManager();
        email="viet.nguyen@ucdconnect.ie.";
        emailValid = emailCheck.isEmailValid(email);
        valid = false;
        assertEquals("email is invalid last character is a '.' ", emailValid, valid);
    }

    @Test
    public void isEmailValidtest4() throws Exception {
        FragmentFriendManager emailCheck = new FragmentFriendManager();
        email="viet.nguyenucdconnect.ie.";
        emailValid = emailCheck.isEmailValid(email);
        valid = false;
        assertEquals("email is invalid missing @ and  last character is a '.' ", emailValid, valid);
    }

}