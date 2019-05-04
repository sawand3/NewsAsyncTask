package com.example.newsasynctask;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Bean> newsDatas=new ArrayList<>();
    RecyclerView recyclerView;
    MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(MainActivity.this);

        recyclerView.setLayoutManager(layoutManager);


        new News().execute();

    }

    public class News extends AsyncTask<String,String,String>{

        HttpURLConnection httpURLConnection;
        String json="";
        JSONObject jsonObject;
        StringBuilder stringBuilder=new StringBuilder("");

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url=new URL("https://newsapi.org/v2/top-headlines?country=in&apiKey=b4f0614a42bc491498fa9fc73943a173");
                httpURLConnection= (HttpURLConnection) url.openConnection();
                InputStream inputStream=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                String data;
                while ((data=bufferedReader.readLine())!=null){
                    stringBuilder.append(data);

                }

            }
            catch (Exception e){
                System.out.println(e);
            }

            json=stringBuilder.toString();

            return json;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try{
                jsonObject=new JSONObject(json);
                JSONArray jsonArray=jsonObject.getJSONArray("articles");
                for (int i=0; i<jsonArray.length(); i++){
                    Bean newData=new Bean();
                    newData.setNewsTitle(jsonArray.getJSONObject(i).getString("title"));
                    JSONObject jsonObject1=jsonArray.getJSONObject(i).getJSONObject("source");

                    newData.setNewsName(jsonObject1.getString("name"));
                    newData.setNewsAuthor(jsonArray.getJSONObject(i).getString("author"));
                    newData.setNewsImage(jsonArray.getJSONObject(i).getString("urlToImage"));
                    newsDatas.add(newData);
                    Log.d("author",newData.getNewsAuthor());
                    Log.d("name",newData.getNewsName());
                    Log.d("title",newData.getNewsTitle());
                    Log.d("img",newData.getNewsImage());
                }
                for (int i=0;i<newsDatas.size();i++){
                    Log.d("Values",newsDatas.get(i).getNewsTitle());
                }


            }
            catch (Exception e){
                System.out.println(e);
            }

            myAdapter=new MyAdapter(newsDatas,getApplicationContext());
            recyclerView.setAdapter(myAdapter);

        }
    }
}
