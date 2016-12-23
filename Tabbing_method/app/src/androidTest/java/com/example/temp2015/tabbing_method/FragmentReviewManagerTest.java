package com.example.temp2015.tabbing_method;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by temp2015 on 23-Dec-16.
 */



public class FragmentReviewManagerTest {

    private String review;
    private int maxSize =40;
    private boolean check;
    private boolean set;
    @Before
    public void setUp() throws Exception {
        review ="the cat went meow ";
        set =true;
    }

    @Test
    public void checkReviewUnderLimitTest0() throws Exception {
        FragmentReviewManager test =new FragmentReviewManager();
        System.out.println(review.length());
        check = test.checkReviewUnderLimit(review , maxSize);
        assertEquals("the review is under 40 characters this is acceptable", check, set);
    }

    @Test
    public void checkReviewUnderLimitTest1() throws Exception {
        review= " the dog chased the cat up a tree  and barked  a lot.......................";
        System.out.println(review.length());
        set = false;
        FragmentReviewManager test =new FragmentReviewManager();
        check = test.checkReviewUnderLimit(review , maxSize);
        assertEquals("charcter count is above 40 not allowed", check, set);
    }

    @Test
    public void checkReviewUnderLimitTest2() throws Exception {
        review= "Idont know what to type here what to say";
        System.out.println(review.length());
        set = true;
        FragmentReviewManager test =new FragmentReviewManager();
        check = test.checkReviewUnderLimit(review , maxSize);
        assertEquals("charcter count is above 40 not allowed", check, set);
    }

    @Test
    public void checkReviewUnderLimitTest3() throws Exception {
        review= "Idontknow what to type here what to say";
        System.out.println(review.length());
        set = true;
        FragmentReviewManager test =new FragmentReviewManager();
        check = test.checkReviewUnderLimit(review , maxSize);
        assertEquals("charcter count is above 40 not allowed", check, set);
    }

    @Test
    public void checkReviewUnderLimitTest4() throws Exception {
        review= "I dont know what to type here what to say";
        System.out.println(review.length());
        set= false;
        FragmentReviewManager test =new FragmentReviewManager();
        check = test.checkReviewUnderLimit(review , maxSize);
        assertEquals("charcter count is above 40 not allowed", check, set);
    }

    @Test
    public void checkReviewUnderLimitTest5() throws Exception {
        review= "I dont know what to type here what to say I dont know what to type here what to say I dont know what to type here what to say";
        System.out.println(review.length());
        set = false;
        FragmentReviewManager test =new FragmentReviewManager();
        check = test.checkReviewUnderLimit(review , maxSize);
        assertEquals("charcter count isalot over 40 not allowed", check, set);
    }


}