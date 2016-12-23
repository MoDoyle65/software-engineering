package com.example.temp2015.tabbing_method;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by temp2015 on 23-Dec-16.
 */


public class getPinHeaderCallbackTest {

    private PinData pinData;
    private LatLng ExpectedLatLng;
    private LatLng SetLatLng;
    private latlngCoord newlat;

    @Before
    public void setUp() throws Exception {
        newlat= new latlngCoord(60,3);
        SetLatLng = new LatLng(60,3);
        pinData = new PinData();
        pinData.setReview("Great fun , had a good time check out the cathedral");
        pinData.setCoord(newlat);
        pinData.setType("bar");
        pinData.setAddress("Germany Koln");
        pinData.setTitle("Germany trip");

    }


    @Test
    public void createMarkerLatLngTest0() throws Exception {
   ;
        getPinHeaderCallback call = new getPinHeaderCallback();
        ExpectedLatLng = call.createMarkerLatLng(pinData);
        assertEquals("latidue and longtitude negative boundary", ExpectedLatLng, SetLatLng);
    }

    @Test
    public void createMarkerLatLngTest1() throws Exception {
        newlat= new latlngCoord(-90,-180);
        SetLatLng = new LatLng(-90,-180);
        pinData.setCoord(newlat);
        getPinHeaderCallback call = new getPinHeaderCallback();
        ExpectedLatLng = call.createMarkerLatLng(pinData);
        assertEquals("latidue and longtitude negative boundary", ExpectedLatLng, SetLatLng);
    }


    @Test
    public void createMarkerLatLngTest2() throws Exception {
        newlat= new latlngCoord(90,-180);
        SetLatLng = new LatLng(90,-180);
        pinData.setCoord(newlat);
        getPinHeaderCallback call = new getPinHeaderCallback();
        ExpectedLatLng = call.createMarkerLatLng(pinData);
        assertEquals("latidue and longtitude postive and negative at boundary", ExpectedLatLng, SetLatLng);
    }

    @Test
    public void createMarkerLatLngTest3() throws Exception {
        newlat= new latlngCoord(-90,180);
        SetLatLng = new LatLng(-90,180);
        pinData.setCoord(newlat);
        getPinHeaderCallback call = new getPinHeaderCallback();
        ExpectedLatLng = call.createMarkerLatLng(pinData);
        assertEquals("latidue and longtitude negative and positive boundary", ExpectedLatLng, SetLatLng);
    }

    @Test
    public void createMarkerLatLngTest4() throws Exception {
        newlat= new latlngCoord(-90,-180);
        SetLatLng = new LatLng(-90,-180);
        pinData.setCoord(newlat);
        getPinHeaderCallback call = new getPinHeaderCallback();
        ExpectedLatLng = call.createMarkerLatLng(pinData);
        assertEquals("latidue and longtitude negative boundary check", ExpectedLatLng, SetLatLng);
    }

    @Test
    public void createMarkerLatLngTest5() throws Exception {
        newlat= new latlngCoord(-91,-180);
        SetLatLng = new LatLng(-91,-180);
        pinData.setCoord(newlat);
        getPinHeaderCallback call = new getPinHeaderCallback();
        ExpectedLatLng = call.createMarkerLatLng(pinData);
        assertEquals("Expected fail one value outside negative boundary but will be handled by placepicker API", ExpectedLatLng, SetLatLng);
    }

    @Test
    public void createMarkerLatLngTest6() throws Exception {
        newlat= new latlngCoord(-90,-181);
        SetLatLng = new LatLng(-90,-181);
        pinData.setCoord(newlat);
        getPinHeaderCallback call = new getPinHeaderCallback();
        ExpectedLatLng = call.createMarkerLatLng(pinData);
        assertEquals("Expected fail one value outside negative boundary but will be handled by placepicker API", ExpectedLatLng, SetLatLng);
    }

    @Test
    public void createMarkerLatLngTest7() throws Exception {
        newlat= new latlngCoord(-91,-181);
        SetLatLng = new LatLng(-91,-181);
        pinData.setCoord(newlat);
        getPinHeaderCallback call = new getPinHeaderCallback();
        ExpectedLatLng = call.createMarkerLatLng(pinData);
        assertEquals("Expected fail outside both values negative boundary  but will be handled by placepicker API", ExpectedLatLng, SetLatLng);
    }

    @Test
    public void createMarkerLatLngTest8() throws Exception {
        newlat= new latlngCoord(91,180);
        SetLatLng = new LatLng(91,180);
        pinData.setCoord(newlat);
        getPinHeaderCallback call = new getPinHeaderCallback();
        ExpectedLatLng = call.createMarkerLatLng(pinData);
        assertEquals("Expected fail one value outside postive boundary but will be handled by placepicker API", ExpectedLatLng, SetLatLng);
    }

    @Test
    public void createMarkerLatLngTest9() throws Exception {
        newlat= new latlngCoord(90,181);
        SetLatLng = new LatLng(90,181);
        pinData.setCoord(newlat);
        getPinHeaderCallback call = new getPinHeaderCallback();
        ExpectedLatLng = call.createMarkerLatLng(pinData);
        assertEquals("Expected fail one value outside postive boundary but will be handled by placepicker API", ExpectedLatLng, SetLatLng);
    }

    @Test
    public void createMarkerLatLngTest10() throws Exception {
        newlat= new latlngCoord(91,181);
        SetLatLng = new LatLng(91,181);
        pinData.setCoord(newlat);
        getPinHeaderCallback call = new getPinHeaderCallback();
        ExpectedLatLng = call.createMarkerLatLng(pinData);
        assertEquals("Expected fail outside both postive boundaries but will be handled by placepicker API", ExpectedLatLng, SetLatLng);
    }


    @Test
    public void createMarkerLatLngTest11() throws Exception {
        newlat= new latlngCoord(0,0);
        SetLatLng = new LatLng(0,0);
        pinData.setCoord(newlat);
        getPinHeaderCallback call = new getPinHeaderCallback();
        ExpectedLatLng = call.createMarkerLatLng(pinData);
        assertEquals("checking middle values of latitude and longititde but will be handled by placepicker API", ExpectedLatLng, SetLatLng);
    }

    @Test
    public void createMarkerLatLngTest12() throws Exception {
        newlat= new latlngCoord(120,240);
        SetLatLng = new LatLng(120,240);
        pinData.setCoord(newlat);
        getPinHeaderCallback call = new getPinHeaderCallback();
        ExpectedLatLng = call.createMarkerLatLng(pinData);
        assertEquals("Expected fail far outside the postive boundaries but will be handled by placepicker API", ExpectedLatLng, SetLatLng);
    }

    @Test
    public void createMarkerLatLngTest13() throws Exception {
        newlat= new latlngCoord(-120,-239);
        SetLatLng = new LatLng(-120,-239);
        pinData.setCoord(newlat);
        getPinHeaderCallback call = new getPinHeaderCallback();
        ExpectedLatLng = call.createMarkerLatLng(pinData);
        assertEquals(" Expected fail far outside the negative boundaries but will be handled by placepicker API", ExpectedLatLng, SetLatLng);
    }

    @Test
    public void createMarkerLatLngTest14() throws Exception {
        newlat= new latlngCoord(-125,247);
        SetLatLng = new LatLng(-125,247);
        pinData.setCoord(newlat);
        getPinHeaderCallback call = new getPinHeaderCallback();
        ExpectedLatLng = call.createMarkerLatLng(pinData);
        assertEquals("Expected fail outside postive and negative boundaries but will be handled by placepicker API", ExpectedLatLng, SetLatLng);
    }

    @Test
    public void createMarkerLatLngTest15() throws Exception {
        newlat= new latlngCoord(113,-270);
        SetLatLng = new LatLng(113,-270);
        pinData.setCoord(newlat);
        getPinHeaderCallback call = new getPinHeaderCallback();
        ExpectedLatLng = call.createMarkerLatLng(pinData);
        assertEquals("Expected fail outside postive and negative boundaries but will be handled by placepicker API", ExpectedLatLng, SetLatLng);
    }

    @Test
    public void createMarkerLatLngTest16() throws Exception {
        newlat= new latlngCoord(-45,-70);
        SetLatLng = new LatLng(-45,-70);
        pinData.setCoord(newlat);
        getPinHeaderCallback call = new getPinHeaderCallback();
        ExpectedLatLng = call.createMarkerLatLng(pinData);
        assertEquals("Pass insiide postive and negative boundaries", ExpectedLatLng, SetLatLng);
    }





}