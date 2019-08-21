package com.example.hyunm.sittingcafe;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class CafeDataActivity extends AppCompatActivity {

    private static String IP_ADDRESS = "sittingcafe.com";
    private static String TAG = "sittingcafe";

    private TextView mTextViewSchool;
    private ArrayList<CafeData> mArrayList;
    private CafeAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private String mJsonString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafe_data);

        mTextViewSchool = (TextView)findViewById(R.id.textView_school_name);
        mRecyclerView = (RecyclerView) findViewById(R.id.listView_main_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        Button button_refresh = (Button) findViewById(R.id.button_refresh);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        if(b != null) {
            String school = (String) b.get("school");
            mTextViewSchool.setText(school);

            if(mTextViewSchool.getText().equals("성신여자대학교")) {

                GetData task = new GetData();
                task.execute( "http://sittingcafe.com/android.php", "");

                button_refresh.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        mArrayList.clear();
                        mAdapter.notifyDataSetChanged();

                        GetData task = new GetData();
                        task.execute( "http://sittingcafe.com/android.php", "");
                    }
                });

            } else if(mTextViewSchool.getText().equals("고려대학교")) {
                GetData task = new GetData();
                task.execute( "http://sittingcafe.com/android2.php", "");

                button_refresh.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        mArrayList.clear();
                        mAdapter.notifyDataSetChanged();

                        GetData task = new GetData();
                        task.execute( "http://sittingcafe.com/android2.php", "");
                    }
                });
            }
        }

        mArrayList = new ArrayList<>();

        mAdapter = new CafeAdapter(this, mArrayList);
        mRecyclerView.setAdapter(mAdapter);

        Button button_back = (Button) findViewById(R.id.button_back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(CafeDataActivity.this, LocationActivity.class);
                startActivity(back);
                finish();
            }
        });
    }

    private class GetData extends AsyncTask<String, Void, String>{

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(CafeDataActivity.this,
                    "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            Log.d(TAG, "response - " + result);

            if (result == null){

            }
            else {
                mJsonString = result;
                showResult();
            }
        }

        @Override
        protected String doInBackground(String... params) {

            String serverURL = params[0];
            String postParameters = "totalPeople=" + params[1];

            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }

                bufferedReader.close();

                return sb.toString().trim();

            } catch (Exception e) {

                Log.d(TAG, "GetData : Error ", e);
                errorString = e.toString();

                return null;
            }

        }
    }

    private void showResult(){

        String TAG_JSON="threepig";
        String TAG_NAME = "cafeName";
        String TAG_PER = "perName";
        String TAG_PEOPLE = "totalPeople";
        String TAG_TABLE = "numTable";
        String TAG_CHAIR = "numChair";


        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                String name = item.getString(TAG_NAME);
                String per = item.getString(TAG_PER);
                String people = item.getString(TAG_PEOPLE);
                String table = item.getString(TAG_TABLE);
                String chair = item.getString(TAG_CHAIR);

                CafeData cafeData = new CafeData();

                cafeData.setCafe_name(name);
                cafeData.setCafe_per(per);
                cafeData.setCafe_people(people);
                cafeData.setCafe_table(table);
                cafeData.setCafe_chair(chair);

                mArrayList.add(cafeData);
                mAdapter.notifyDataSetChanged();
            }
        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }
    }
}