package com.ricardohg.ejercicio3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProductDetails extends AppCompatActivity implements Response.ErrorListener, Response.Listener<JSONObject>{

    // UI elements:
    ProgressBar loadPB;
    ImageView productIV;
    TextView nameTV;
    TextView descriptionTV;

    LinearLayout contentLL;

    // Connection variables:
    String url;
    RequestQueue queue;
    JsonObjectRequest request;

    Product productData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        // Getting product data:
        productData = (Product) getIntent().getSerializableExtra("productData");

        // UI search:
        // ImageView:
        productIV = findViewById(R.id.productIV);

        // TextView:
        nameTV = findViewById(R.id.nameTV);
        descriptionTV = findViewById(R.id.descriptionTV);

        // Progress:
        loadPB = findViewById(R.id.loadPB);

        // Linear Layout:
        contentLL = findViewById(R.id.contentLL);
        contentLL.setVisibility(View.GONE);

        // Connection request:
        queue = Volley.newRequestQueue(this);
        url = getString(R.string.url_data_source) + getString(R.string.url_product_details) + productData.getId();
        request = new JsonObjectRequest(Request.Method.GET, url, null,this, this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 30,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        loadPB.setVisibility(View.GONE);
        Log.d("Message: ", error.getMessage());
        finish();
    }

    @Override
    public void onResponse(JSONObject response) {
        loadPB.setVisibility(View.GONE);
        contentLL.setVisibility(View.VISIBLE);
        Log.d("Message: ", response.toString());

        try{
            // Product data:
            String productName = response.getString(getString(R.string.name_detail_json));
            String productImageURL = response.getString(getString(R.string.image_detail_json));
            String productDescription = response.getString(getString(R.string.desc_detail_json));

            // Load image
            Picasso.with(this)
                    .load(productImageURL)
                    .into(productIV);

            // Set UI elements
            nameTV.setText(productName);
            descriptionTV.setText(productDescription.replace("\\", ""));

        }catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
