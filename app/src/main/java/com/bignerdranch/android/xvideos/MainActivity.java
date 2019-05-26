package com.bignerdranch.android.xvideos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import android.util.Log;
import android.content.Intent;
import android.net.Uri;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class MainActivity extends AppCompatActivity {

    private Button mGenerateSomeVideoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mGenerateSomeVideoButton = findViewById(R.id.generate_button);
        mGenerateSomeVideoButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                try{
                    generate();
                }
                catch(Exception e){}

            }
        });


    }

    private void generate(){

        new Thread(new Runnable(){
            @Override
            public void run(){
                try{
                    Document doc = Jsoup.connect("https://xvideos.com").get();
                    List<Element> links = doc.select(".thumb");

                    List<String> xvideosLinks = new ArrayList<>();
                    int countXvideosLinks = 0;
                    Random random = new Random();


                    for(Element link : links){
                        String href = link.select("a").get(0).absUrl("href");
                        xvideosLinks.add(href);
                        countXvideosLinks++;
                        //System.out.println(href);

                    }


                    int randomIndex = random.nextInt(countXvideosLinks);

                    String l = xvideosLinks.get(randomIndex);

                    Log.d("CODE", "link " + l);

                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(l));
                    startActivity(intent);
                }
                catch(Exception e){

                }
            }
        }).start();


    }
}
