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
        pinData.setReview("Great fun ,had a good time check out the cathedral");
        pinData.setCoord(newlat);
        pinData.setType("bar");
        pinData.setAddress("Germany Koln");
        pinData.setTitle("Germany trip");

    }


    @Test
    public void createMarkerLatLngTestNormalValue() throws Exception {
   ;
        getPinHeaderCallback call = new getPinHeaderCallback();
        ExpectedLatLng = call.createMarkerLatLng(pinData);
        assertEquals("Latidue and Longtitude inside boundaries", ExpectedLatLng, SetLatLng);
    }

    @Test
    public void createMarkerLatLngTestNegativeBoundary() throws Exception {
        newlat= new latlngCoord(-90,-180);
        SetLatLng = new LatLng(-90,-180);
        pinData.setCoord(newlat);
        getPinHeaderCallback call = new getPinHeaderCallback();
        ExpectedLatLng = call.createMarkerLatLng(pinData);
        assertEquals("Latidue and Longtitude at negative boundary", ExpectedLatLng, SetLatLng);
    }


    @Test
    public void createMarkerLatLngTestPosAndNegBoundary() throws Exception {
        newlat= new latlngCoord(90,-180);
        SetLatLng = new LatLng(90,-180);
        pinData.setCoord(newlat);
        getPinHeaderCallback call = new getPinHeaderCallback();
        ExpectedLatLng = call.createMarkerLatLng(pinData);
        assertEquals("Latidue and Longtitude postive and negative at boundary", ExpectedLatLng, SetLatLng);
    }

    @Test
    public void createMarkerLatLngTestNrgAndPosBoundary() throws Exception {
        newlat= new latlngCoord(-90,180);
        SetLatLng = new LatLng(-90,180);
        pinData.setCoord(newlat);
        getPinHeaderCallback call = new getPinHeaderCallback();
        ExpectedLatLng = call.createMarkerLatLng(pinData);
        assertEquals("Latidue and Longtitude negative and positive boundary", ExpectedLatLng, SetLatLng);
    }

    @Test
    public void createMarkerLatLngTestPositiveBoundary() throws Exception {
        newlat= new latlngCoord(90,180);
        SetLatLng = new LatLng(90,180);
        pinData.setCoord(newlat);
        getPinHeaderCallback call = new getPinHeaderCallback();
        ExpectedLatLng = call.createMarkerLatLng(pinData);
        assertEquals("Latidue and Longtitude positive boundary check", ExpectedLatLng, SetLatLng);
    }

    @Test
    public void createMarkerLatLngTestOutsideNegBoundary() throws Exception {
        newlat= new latlngCoord(-91,-181);
        SetLatLng = new LatLng(-91,-181);
        pinData.setCoord(newlat);
        getPinHeaderCallback call = new getPinHeaderCallback();
        ExpectedLatLng = call.createMarkerLatLng(pinData);
        assertEquals("Two values just outside negative boundary but will be handled by placepicker API", ExpectedLatLng, SetLatLng);
    }

    @Test
    public void createMarkerLatLngTestOutsidePosBoundary() throws Exception {
        newlat= new latlngCoord(91,181);
        SetLatLng = new LatLng(91,181);
        pinData.setCoord(newlat);
        getPinHeaderCallback call = new getPinHeaderCallback();
        ExpectedLatLng = call.createMarkerLatLng(pinData);
        assertEquals("Two values just outside positive boundary but will be handled by placepicker API", ExpectedLatLng, SetLatLng);
    }



    @Test
    public void createMarkerLatLngInsidePosBoundary() throws Exception {
        newlat= new latlngCoord(89,179);
        SetLatLng = new LatLng(89,179);
        pinData.setCoord(newlat);
        getPinHeaderCallback call = new getPinHeaderCallback();
        ExpectedLatLng = call.createMarkerLatLng(pinData);
        assertEquals("Two values just inside the postive boundary but will be handled by placepicker API", ExpectedLatLng, SetLatLng);
    }

    @Test
    public void createMarkerLatLngTestInsideNegBoundary() throws Exception {
        newlat= new latlngCoord(-89,-179);
        SetLatLng = new LatLng(-89,-179);
        pinData.setCoord(newlat);
        getPinHeaderCallback call = new getPinHeaderCallback();
        ExpectedLatLng = call.createMarkerLatLng(pinData);
        assertEquals("Two values jusy inside the postive boundaries but will be handled by placepicker API", ExpectedLatLng, SetLatLng);
    }


    @Test
    public void createMarkerLatLngTestAtZero() throws Exception {
        newlat= new latlngCoord(0,0);
        SetLatLng = new LatLng(0,0);
        pinData.setCoord(newlat);
        getPinHeaderCallback call = new getPinHeaderCallback();
        ExpectedLatLng = call.createMarkerLatLng(pinData);
        assertEquals("Checking middle values of latitude and longititde ", ExpectedLatLng, SetLatLng);
    }

    @Test
    public void createMarkerLatLngTestFarOutsidePosBoundary() throws Exception {
        newlat= new latlngCoord(120,240);
        SetLatLng = new LatLng(120,240);
        pinData.setCoord(newlat);
        getPinHeaderCallback call = new getPinHeaderCallback();
        ExpectedLatLng = call.createMarkerLatLng(pinData);
        assertEquals("Far outside the postive boundaries but will be handled by placepicker API", ExpectedLatLng, SetLatLng);
    }

    @Test
    public void createMarkerLatLngTestFarOutsideNegBoundary() throws Exception {
        newlat= new latlngCoord(-120,-239);
        SetLatLng = new LatLng(-120,-239);
        pinData.setCoord(newlat);
        getPinHeaderCallback call = new getPinHeaderCallback();
        ExpectedLatLng = call.createMarkerLatLng(pinData);
        assertEquals("Far outside the negative boundaries but will be handled by placepicker API", ExpectedLatLng, SetLatLng);
    }

    @Test
    public void createMarkerLatLngTestFarOutideNegAndPosBoundary() throws Exception {
        newlat= new latlngCoord(-145,270);
        SetLatLng = new LatLng(-145,270);
        pinData.setCoord(newlat);
        getPinHeaderCallback call = new getPinHeaderCallback();
        ExpectedLatLng = call.createMarkerLatLng(pinData);
        assertEquals("Far passed positive and negative boundaries", ExpectedLatLng, SetLatLng);
    }

    @Test
    public void createMarkerLatLngTestFarOutsidePosAndNegBoundary() throws Exception {
        newlat= new latlngCoord(145,-270);
        SetLatLng = new LatLng(145,-270);
        pinData.setCoord(newlat);
        getPinHeaderCallback call = new getPinHeaderCallback();
        ExpectedLatLng = call.createMarkerLatLng(pinData);
        assertEquals("Far pass inside positive and negative boundaries", ExpectedLatLng, SetLatLng);
    }

}