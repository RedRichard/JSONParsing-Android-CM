package com.ricardohg.ejercicio3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Response.ErrorListener, Response.Listener<JSONArray>{

    RecyclerView productListRV;
    ProgressBar connectionPB;

    ArrayList<Product> productList;

    AdapterProductItem adapter;

    // Connection variables:
    String url;
    RequestQueue queue;
    JsonArrayRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        productList = new ArrayList<Product>();

        // UI search:
        // RecyclerView:
        productListRV = findViewById(R.id.productListRV);
        productListRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        // Progress:
        connectionPB = findViewById(R.id.connectionPB);

        // Connection request:
        queue = Volley.newRequestQueue(this);
        url = getString(R.string.url_data_source) + getString(R.string.url_product_item);
        request = new JsonArrayRequest(Request.Method.GET, url, null, this, this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);


    }

    @Override
    public void onErrorResponse(VolleyError error) {
        connectionPB.setVisibility(View.GONE);
        finish();
    }

    @Override
    public void onResponse(JSONArray response) {
        connectionPB.setVisibility(View.GONE);
        //Log.d("RESPUESTA", response.toString());

        JSONObject jsonObject;

        try{
            for(int i=0; i < response.length(); i++){
                jsonObject = response.getJSONObject(i);

                String id = jsonObject.getString(getString(R.string.id_json));
                String name = jsonObject.getString(getString(R.string.name_json));
                String price = jsonObject.getString(getString(R.string.price_json));
                String provider = jsonObject.getString(getString(R.string.provider_json));
                String delivery = jsonObject.getString(getString(R.string.delivery_json));
                String imageURL = jsonObject.getString(getString(R.string.image_url_json));

                Product product = new Product(id, name, price, provider, delivery, imageURL);

                productList.add(product);

                //Log.d("Product: ", product.getName());
            }

            // Setting adapter
            adapter = new AdapterProductItem(productList);

            // Setting adapter for the Recycler View
            productListRV.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
