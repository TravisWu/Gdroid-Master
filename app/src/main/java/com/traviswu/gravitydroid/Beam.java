package com.traviswu.gravitydroid;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by sylve_000 on 2015-06-17.
 */
@ParseClassName("Beam")
public class Beam extends ParseObject {
    private boolean facebookOn;
    private boolean twitterOn;
    private boolean mailOn;
    private boolean callOn;
    private String senderId;

    public Beam(){
        super();
    }

    public void setBeam(boolean facebookOn, boolean twitterOn, boolean mailOn, boolean callOn, String id) {
        this.facebookOn = facebookOn;
        this.twitterOn = twitterOn;
        this.mailOn = mailOn;
        this.callOn = callOn;
        this.senderId = id;
    }
}
