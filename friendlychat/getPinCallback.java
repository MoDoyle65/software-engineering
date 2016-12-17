package com.google.firebase.codelab.friendlychat;

import com.google.firebase.database.DatabaseError;

import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by johnny on 13/12/2016.
 */

public class getPinCallback implements PinCallback {

    private Map<String, PinData> PinInfo = new HashMap<String, PinData>();


    public void onPinResult(String key, PinData pindata) {
        System.out.println(pindata.getAddress());
        System.out.println(key);
        this.PinInfo.put(key, pindata);
        System.out.println(PinInfo.values());
        this.getPinList();
    }

    public void onCancelled(DatabaseError databaseError){
        System.out.println("Error encountered");
    };

    public void getPinList() {
        Set set = PinInfo.entrySet();
        Iterator iterator = set.iterator();
        while(iterator.hasNext()) {
            Map.Entry mentry = (Map.Entry)iterator.next();
            System.out.print("key is: "+ mentry.getKey() + " & Value is: ");
            PinData pp = (PinData) mentry.getValue();
            System.out.println(pp.getReview());
        }
    }
}
