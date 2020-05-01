package com.example.internship;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class pdfView extends AppCompatActivity {

    private Bundle extras;
    PDFView pdfView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_view);


        pdfView=(PDFView) findViewById(R.id.pdfview);
        extras=getIntent().getExtras();
        //Link to the server I have made
        String url="http://192.168.0.105/internship/";
        //Adding the exact file location to the url
        if(extras!=null)
        {
            url=url+extras.get("name")+".pdf";
        }

        new RetrievePDFstream().execute(url);


    }


    class RetrievePDFstream extends AsyncTask<String,Void, InputStream>
    {

        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream=null;
           try{
               URL url=new URL(strings[0]);
               HttpURLConnection urlConnection=(HttpURLConnection)url.openConnection();
               if(urlConnection.getResponseCode()==200)
               {
                   inputStream=new BufferedInputStream(urlConnection.getInputStream());

               }

           } catch (IOException e)
           {
               return null;
           }
            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream)
        {
            pdfView.fromStream(inputStream).load();
        }

    }
}
