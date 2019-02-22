package com.example.truestory;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.Response;
import com.android.volley.Request;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

public class WebServiceHandler extends AppCompatActivity {



    /** We will be using Volley to help us get webservice data.
     * https://www.londonappdeveloper.com/consuming-a-json-rest-api-in-android/
     *
     * */
    String baseURL = "https://www.reddit.com/r/todayilearned/new.json?limit=25";

    RequestQueue mRequestQueue = Volley.newRequestQueue(this);


}
