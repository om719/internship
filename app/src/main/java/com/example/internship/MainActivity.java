package com.example.internship;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Adapter.MyAdapter;
import Model.ListItem;

import static com.android.volley.Request.*;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<ListItem> listItems;
    //json file created in server from file_structure.php
    private final static String URL="http://192.168.0.105/internship/Files.json";
    public String[] myArray=new String[100];
    public ArrayList<String> mylist = new ArrayList<String>();
    private Button search;
    private EditText query;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        search=(Button)findViewById(R.id.searchid);
        query=(EditText) findViewById(R.id.queryid);
        recyclerView=(RecyclerView) findViewById(R.id.reciclerviewid);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listItems=new ArrayList<>();

        //Reading items from json file created by file_strucutre.php which is stored in the server
        RequestQueue queue= Volley.newRequestQueue(this);
        JsonArrayRequest arrayRequest=new JsonArrayRequest(Request.Method.GET, URL,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i=0; i<response.length();i++)
                {
                    try{
                        JSONObject pdfObject=response.getJSONObject(i);
                        Log.d("Items:",pdfObject.getString("name"));
                        //Getting the names of the files and adding them to the list
                        String pdfname=pdfObject.getString("name");
                        ListItem item=new ListItem(pdfname);
                        listItems.add(item);
                        mylist.add(pdfname);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                //Adding the entire list to the adapter,check Adapter package
                adapter=new MyAdapter(MainActivity.this,listItems);
                recyclerView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                    VolleyLog.d("Error",error.getMessage());
            }
        });
        queue.add(arrayRequest);

        //Function to search in the list using regex
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sname=query.getText().toString();
                Pattern p=Pattern.compile(sname,Pattern.CASE_INSENSITIVE);
                int n=mylist.size();
                listItems.clear();
                for(int i=0;i<n;i++)
                {
                    Matcher m = p.matcher(mylist.get(i));
                    if(m.find())
                    {
                        ListItem item=new ListItem(mylist.get(i));
                        listItems.add(item);
                    }
                }
                adapter=new MyAdapter(MainActivity.this,listItems);
                recyclerView.setAdapter(adapter);
            }
        });

    }
}
