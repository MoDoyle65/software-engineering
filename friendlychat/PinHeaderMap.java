package com.google.firebase.codelab.friendlychat;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by johnny on 14/12/2016.
 */

public class PinHeaderMap {

    private Map<String, PinHeader> PinHeaderStore = new HashMap<String, PinHeader>();

    public void PinHeaderMap(){

    }

    public void addPinHeaderToMap(PinHeader ph) {
        PinHeaderStore.put(ph.getkey(), ph);
    }

    public void removePinHeaderFromMap(String key) {
        PinHeaderStore.remove(key);
    }

    public latlngCoord getPinHeaderCoord(String key) {
        return PinHeaderStore.get(key).getCoord();
    }

    public void getPinList() {
        Set set = PinHeaderStore.entrySet();
        Iterator iterator = set.iterator();
        while(iterator.hasNext()) {
            Map.Entry mentry = (Map.Entry)iterator.next();
            System.out.print("key is: "+ mentry.getKey() + " & Value is: ");
            PinHeader pp = (PinHeader) mentry.getValue();
            System.out.println(pp.getCoord().getLat());
            System.out.println(pp.getCoord().getLng());
        }
    }
}
