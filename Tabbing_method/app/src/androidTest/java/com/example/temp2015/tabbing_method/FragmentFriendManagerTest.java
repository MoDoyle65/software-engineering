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
    public void isEmailValidTestValidEmail() throws Exception {
        FragmentFriendManager emailCheck = new FragmentFriendManager();
        email="UCD.student@ucdconnect.ie";
        emailValid = emailCheck.isEmailValid(email);
        valid = true;
        assertEquals("Email is valid genuine email entered", emailValid, valid);
    }

    @Test
    public void isEmailValidTestEmailMissingTopLevelDomainName() throws Exception {
        FragmentFriendManager emailCheck = new FragmentFriendManager();
        email="UCD.student@ucdconnect";
        emailValid = emailCheck.isEmailValid(email);
        valid = true;
        assertEquals("Email is valid but email is missing the top-level domain name", emailValid, valid);
    }

    @Test
    public void isEmailValidTestEMailMissingAtSymbol() throws Exception {
        FragmentFriendManager emailCheck = new FragmentFriendManager();
        email="UCD.studentucdconnect.ie";
        emailValid = emailCheck.isEmailValid(email);
        valid = false;
        assertEquals("Email is invalid missing @ sign", emailValid, valid);
    }

    @Test
    public void isEmailValidTestEmailWithDotAtTheEnd() throws Exception {
        FragmentFriendManager emailCheck = new FragmentFriendManager();
        email="UCD.student@ucdconnect.ie.";
        emailValid = emailCheck.isEmailValid(email);
        valid = false;
        assertEquals("Email is invalid last character is a '.' ", emailValid, valid);
    }

    @Test
    public void isEmailValidTestEmailMissingAtSybolAndDotAtTheEnd() throws Exception {
        FragmentFriendManager emailCheck = new FragmentFriendManager();
        email="UCD.studentucdconnect.ie.";
        emailValid = emailCheck.isEmailValid(email);
        valid = false;
        assertEquals("Email is invalid missing @ and  last character is a '.' ", emailValid, valid);
    }

    // we do not need to check if  users trys to add a null friend as the button cannot be pressed untill a string of atleast one character has been entered
}