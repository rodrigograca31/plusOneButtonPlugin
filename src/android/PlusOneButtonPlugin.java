/*
Licensed to the Apache Software Foundation (ASF) under one
or more contributor license agreements.  See the NOTICE file
distributed with this work for additional information
regarding copyright ownership.  The ASF licenses this file
to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance
with the License.  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied.  See the License for the
specific language governing permissions and limitations
under the License.
*/

package com.jcesarmobile.plusonebutton;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.widget.RelativeLayout;


import com.google.android.gms.plus.PlusOneButton;


/**
* This class exposes methods in Cordova that can be called from JavaScript.
*/
public class PlusOneButtonPlugin extends CordovaPlugin {



    private PlusOneButton mPlusOneButton;
    private String URL;
    private long x;
    private long y;


    /**
     * Executes the request and returns PluginResult.
     *
     * @param action            The action to execute.
     * @param args              JSONArry of arguments for the plugin.
     * @param callbackContext   The callback context from which we were invoked.
     */
    @SuppressLint("NewApi")
    public boolean execute(String action, final JSONArray args, final CallbackContext callbackContext) throws JSONException {
        if (action.equals("show")) {
            if (args.optJSONObject(0) != null) {
                JSONObject obj = args.getJSONObject(0);
                URL = obj.optString("url", null);
                if (URL==null) {
                    callbackContext.error("you have to provide an url");
                }
                x = obj.optJSONObject("position").optLong("x",0);
                y = obj.optJSONObject("position").optLong("y",0);
            } else if (args.optString(0)!=null) {
                URL = args.optString(0);
            } else {
                callbackContext.error("you have to provide an url");
            }
            mPlusOneButton =  new PlusOneButton(cordova.getActivity());
            mPlusOneButton.initialize(URL, null);

            cordova.getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    cordova.getActivity().addContentView(mPlusOneButton,new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT));
                    mPlusOneButton.setX(x);
                    mPlusOneButton.setY(y);
                }
            });

        } else {
            return false;
        }
        return true;
    }

    @Override
    public void onResume(boolean multitasking) {
        super.onResume(multitasking);
        mPlusOneButton.initialize(URL, null);
    }

}
