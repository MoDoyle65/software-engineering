package com.example.temp2015.tabbing_method;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by temp2015 on 23-Dec-16.
 */



public class FragmentReviewManagerTest {

    private String review;
    private int maxSize =40; // the maximum amount of characters a review can have
    private boolean check;
    private boolean set;

    @Before
    public void setUp() throws Exception {
        review ="the cat went meow ";
        set = true;
    }

    @Test
    public void checkReviewUnderLimitTestReviewunderCount() throws Exception {
        FragmentReviewManager test =new FragmentReviewManager();
        System.out.println(review.length());
        check = test.checkReviewUnderLimit(review , maxSize);
        assertEquals("the review is under 40 characters this is acceptable", check, set);
    }

    @Test
    public void checkReviewUnderLimitTestREviewOverCharacterCount() throws Exception {
        review= " the dog chased the cat up a tree  and barked  a lot.......................";
        System.out.println(review.length());
        set = false;
        FragmentReviewManager test =new FragmentReviewManager();
        check = test.checkReviewUnderLimit(review , maxSize);
        assertEquals("charcter count is above 40 not allowed", check, set);
    }

    @Test
    public void checkReviewUnderLimitTestCountEqualTo40() throws Exception {
        review= "Idont know what to type here what to say";
        System.out.println(review.length());
        set = true;
        FragmentReviewManager test =new FragmentReviewManager();
        check = test.checkReviewUnderLimit(review , maxSize);
        assertEquals("charcter count is eaxctly 40 allowed", check, set);
    }

    @Test
    public void checkReviewUnderLimitTestCountEqualTo39() throws Exception {
        review= "Idontknow what to type here what to say";
        System.out.println(review.length());
        set = true;
        FragmentReviewManager test =new FragmentReviewManager();
        check = test.checkReviewUnderLimit(review , maxSize);
        assertEquals("charcter count is below 40 allowed", check, set);
    }

    @Test
    public void checkReviewUnderLimitTestCountEqualTo41() throws Exception {
        review= "I dont know what to type here what to say";
        System.out.println(review.length());
        set= false;
        FragmentReviewManager test =new FragmentReviewManager();
        check = test.checkReviewUnderLimit(review , maxSize);
        assertEquals("charcter count is above 40 not allowed", check, set);
    }

    @Test
    public void checkReviewUnderLimitTestCountMuchGreaterThan40() throws Exception {
        review= "I dont know what to type here what to say I dont know what to type here what to say I dont know what to type here what to say";
        System.out.println(review.length());
        set = false;
        FragmentReviewManager test =new FragmentReviewManager();
        check = test.checkReviewUnderLimit(review , maxSize);
        assertEquals("charcter count is a lot over 40 not allowed", check, set);
    }

    // We do not need to check if the character count is zero as a review can never be submitted if it is 0 characters long

}