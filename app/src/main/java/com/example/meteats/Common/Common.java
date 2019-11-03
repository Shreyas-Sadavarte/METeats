package com.example.meteats.Common;

import android.widget.EditText;

import com.example.meteats.ui.data.model.LoggedInUser;

public class Common {

    public static String currentuser;

    public Common(String currentuser){
        this.currentuser=currentuser;
    }



    public String getDisplayName1() {
        return currentuser;
    }
}
