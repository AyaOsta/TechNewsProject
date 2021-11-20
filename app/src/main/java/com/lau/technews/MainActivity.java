package com.lau.technews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase db;
    String articleID = "";
    String title, url;

    public static final String EXTRA_MESSAGE = "URL OF ARTICLE";
    public static final String EXTRA_MESSAGE1 = "TITLE OF ARTICLE";

    public class DownloadTask extends AsyncTask<String, Void, String>{

        protected String doInBackground(String... urls){
            String result = "";
            String resultofsecondapi = "";
            String urlofId;
            HttpURLConnection con, con1, con2;
            URL url1, url2, url3;
            InputStream in,in1, in2;
            InputStreamReader reader, reader1, reader2;

            JSONObject json;

            try{
//                url1 = new URL(urls[0]);
//
//                con = (HttpURLConnection) url1.openConnection();
//                con.connect();
//
//                in = con.getInputStream();
//                reader = new InputStreamReader(in);
//                int data = reader.read();
//
//                while (data != -1){
//                    char current =(char) data;
//                    result += current;
//                    data = reader.read();
//                }
//
//                JSONArray arr = new JSONArray(result);
//                int size = 20;
//                for(int i=0; i<size; i++){
//                    resultofsecondapi = "";
//
//                    articleID = arr.get(i).toString();
//
//                    urlofId = "https://hacker-news.firebaseio.com/v0/item/" + articleID + ".json?print=pretty";
//
//
//                    url2 = new URL(urlofId);
//                    con1 = (HttpURLConnection) url2.openConnection();
//                    con1.connect();
//
//                    in1 = con1.getInputStream();
//                    reader1 = new InputStreamReader(in1);
//
//                    int data1 = reader1.read();
//                    while (data1 != -1){
//                        char current =(char) data1;
//                        resultofsecondapi += current;
//
//                        data1 = reader1.read();
//                    }
//
//                    json = new JSONObject(resultofsecondapi);
//
//                    if (json.has("url") && json.has("title")) {
//                        title = json.getString("title");
//
//                        url = json.getString("url");
//
//
//                        if (title.contains("'")) {
//                            title = String.format(title.replace("'", "‘"));
//
//                        }
//
//                        try {
//                            db.execSQL("INSERT INTO articles(article_id,article_title,article_content) " + "VALUES (" + Integer.parseInt(articleID) + ", '" + title + "', '" + url + "' );");
//                        } catch (Exception e){
//                            e.printStackTrace();
//                        }
//                    }
//                }



                return result;

            }catch(Exception e){
                e.printStackTrace();
                return null;
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView list = (ListView) findViewById(R.id.list);

        final ArrayList <String> array = new ArrayList<>();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, array);
        list.setAdapter(adapter);

        String urlofIds = "https://hacker-news.firebaseio.com/v0/topstories.json";

        Intent intent = new Intent(this, ArticleView.class);
        try {
            db = this.openOrCreateDatabase("technewsdb", MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS articles(article_id VARCHAR, article_title VARCHAR, article_content VARCHAR);");
//            db.execSQL("DELETE from articles;");
//            db.execSQL("vacuum");
            DownloadTask task = new DownloadTask();
            task.execute(urlofIds);

            Cursor c = db.rawQuery("Select * from articles", null);

//            int fullarticleid = c.getColumnIndex("article_id");
            int fullarticletitle = c.getColumnIndex("article_title");
//            int fullarticlecontent = c.getColumnIndex("article_content");

            c.moveToFirst();

            while (c != null) {
                array.add(c.getString(fullarticletitle));
                c.moveToNext();
            }



        } catch (Exception e){
            e.printStackTrace();
        }
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor cursor = db.rawQuery("SELECT article_content From articles WHERE article_title = '" + array.get(i)+"'", null);
                cursor.moveToFirst();

                int temp = cursor.getColumnIndex("article_content");
                Log.i("li",cursor.getString(temp));

                intent.putExtra(EXTRA_MESSAGE,cursor.getString(temp));
                intent.putExtra(EXTRA_MESSAGE1, array.get(i));
                startActivity(intent);
            }
        });
    }
}