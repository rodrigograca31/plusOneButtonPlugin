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
import android.view.View;
import android.widget.RelativeLayout;

import java.lang.System;

import com.google.android.gms.plus.PlusOneButton;
import android.content.Intent;
import org.apache.cordova.PluginResult;


/**
* This class exposes methods in Cordova that can be called from JavaScript.
*/
public class PlusOneButtonPlugin extends CordovaPlugin {


    private PlusOneButton mPlusOneButton = null;
    private String URL;
    private long x;
    private long y;
    private int size; //SIZE_SMALL = 0, SIZE_MEDIUM = 1, SIZE_TALL = 2, SIZE_STANDARD = 3
    private int annotation; //ANNOTATION_NONE = 0, ANNOTATION_BUBBLE = 1, ANNOTATION_INLINE = 2
    private boolean created = false;
    private CallbackContext callback = null;
    final PlusOneButtonPlugin PObtn = this;


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
                size = obj.optInt("size", 3);
                annotation = obj.optInt("annotation", 1);
            } else if (args.optString(0)!=null) {
                URL = args.optString(0);
            } else {
                callbackContext.error("you have to provide an url");
            }
            if (mPlusOneButton==null) {
                mPlusOneButton = new PlusOneButton(cordova.getActivity());
            }

            cordova.getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    mPlusOneButton.initialize(URL, null);
                    mPlusOneButton.setVisibility(View.VISIBLE);
                    if (!created) {
                        cordova.getActivity().addContentView(mPlusOneButton,new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT));
                        created = true;
                    }
                    mPlusOneButton.setX(x);
                    mPlusOneButton.setY(y);
                    mPlusOneButton.setSize(size);
                    mPlusOneButton.setAnnotation(annotation);
                }
            });

        } else if (action.equals("hide")) {
          if (mPlusOneButton!=null) {
            cordova.getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    mPlusOneButton.setVisibility(View.GONE);
                }
            });
          }
        } else if (action.equals("onClick")) {
            if (mPlusOneButton!=null) {
                callback = callbackContext;
                mPlusOneButton.setOnPlusOneClickListener(new PlusOneButton.OnPlusOneClickListener() {
                    @Override
                    public void onPlusOneClick(Intent intent) {
                        System.out.println("1 Click!");

                        PluginResult result = new PluginResult(PluginResult.Status.OK);
                        result.setKeepCallback(true);
                        callback.sendPluginResult(result);

                        cordova.startActivityForResult(PObtn, intent, 0);
                    }
                });
            }
        } else {
            return false;
        }
        return true;
    }

    @Override
    public void onResume(boolean multitasking) {
        super.onResume(multitasking);
        if (mPlusOneButton!=null) {
            mPlusOneButton.initialize(URL, null);
            mPlusOneButton.setOnPlusOneClickListener(new PlusOneButton.OnPlusOneClickListener() {
                @Override
                public void onPlusOneClick(Intent intent) {
                    System.out.println("2 Click!");

                    PluginResult result = new PluginResult(PluginResult.Status.OK);
                    result.setKeepCallback(true);
                    callback.sendPluginResult(result);

                    cordova.startActivityForResult(PObtn, intent, 0);
                }
            });
        }
    }

}
