package com.example.jws.bzapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RecommendActivity extends AppCompatActivity {

    TextView text1;
    Button btn;
    ArrayList<String> list = new ArrayList<String>();;
    ArrayAdapter<String>Adapter;
    ListView listView;
    String mJsonString;
    String Location;
    String sales;
    String Fsale;
    String Lsale;
    String Category;

    private static final String TAG = "Login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);
     text1=(TextView)findViewById(R.id.test1);


        Adapter = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,list);
        listView=(ListView)findViewById(R.id.Recomlistview);
        Intent intent = getIntent();
        Location=intent.getStringExtra("location");
        sales=intent.getStringExtra("sales");
        Category=intent.getStringExtra("type");

        if(sales.equals("1")){
            Fsale="0";
            Lsale="2000";
        }else if(sales.equals("2")){
            Fsale="2000";
            Lsale="2500";
        }
        else if(sales.equals("3")){
            Fsale="2500";
            Lsale="3000";
        }
        else if(sales.equals("4")){
            Fsale="3000";
            Lsale="3500";
        }
        else if(sales.equals("5")){
            Fsale="3500";
            Lsale="4000";
        }
        else if(sales.equals("6")){
            Fsale="4000";
            Lsale="10000";
        }
        text1.setText(Location+sales+Category+Fsale+Lsale);

        getClosure();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getApplicationContext(),list.get(position),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RecommendActivity.this,Recommenddetail.class);
                intent.putExtra("Location",list.get(position));
                startActivity(intent);



            }
        });


    }



    public void getClosure() {

//        Toast.makeText(getApplicationContext(), addrnm, Toast.LENGTH_LONG).show();
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray jsonArray = jsonObject.getJSONArray("response");
                    int count = 0;
                    String location;
                    while (count < jsonArray.length()) {
                        JSONObject object = jsonArray.getJSONObject(count);
                        location = object.getString("location");
                        list.add(location);
                        listView.setAdapter(Adapter);
                        count++;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        Findrecommend findrecommend = new Findrecommend(Location,Category,Fsale,Lsale, responseListener);
        RequestQueue queue = Volley.newRequestQueue(RecommendActivity.this);
        queue.add(findrecommend);
    }


//    private class getsurvey extends AsyncTask<String, Void, String> {
//
//        ProgressDialog progressDialog;
//        String errorString = null;
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//
//            progressDialog = ProgressDialog.show(RecommendActivity.this,
//                    "성격 급하시네 시발라꺼", null, true, true);
//        }
//
//
//        @Override
//        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
//            progressDialog.dismiss();
//            Log.d(TAG, "response - " + result);
//            if (result == null){
//                Toast.makeText(getApplicationContext(),"오류",Toast.LENGTH_SHORT).show();
//            }
//            else {
//                mJsonString = result;
//                showResult();
//            }
//        }
//
//
//
//
//        @Override
//        protected String doInBackground(String... params) {
//
//            String location = params[0];
//            String  category = params[1];
//            String Fsale = params[2];
//            String Lsale = params[3];
//
//            String json;
//
//            String serverURL = "http://qwerr784.cafe24.com/surveylist.php";
//
//
//            String postParameters1 = "category=" + "retail" ;
//            String postParameters2 ="Fsale=" + "2000";
//            String postParameters3 ="Lsale=" + "3000";
//
//
//            try {
//
//                URL url = new URL(serverURL);
//                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//
//
//                httpURLConnection.setReadTimeout(5000);
//                httpURLConnection.setConnectTimeout(5000);
//                httpURLConnection.setRequestMethod("POST");
//                httpURLConnection.setDoInput(true);
//                httpURLConnection.connect();
//
//
//                OutputStream outputStream = httpURLConnection.getOutputStream();
//                outputStream.write(postParameters1.getBytes("UTF-8"));
//                outputStream.flush();
//                outputStream.close();
//
//
//                int responseStatusCode = httpURLConnection.getResponseCode();
//                Log.d(TAG, "response code - " + responseStatusCode);
//
//                InputStream inputStream;
//                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
//                    inputStream = httpURLConnection.getInputStream();
//                }
//                else{
//                    inputStream = httpURLConnection.getErrorStream();
//                }
//
//
//                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
//                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//
//                StringBuilder sb = new StringBuilder();
//                String line;
//
//                while((line = bufferedReader.readLine()) != null){
//                    sb.append(line);
//                }
//
//
//                bufferedReader.close();
//
//
//                return sb.toString().trim();
//
//
//            } catch (Exception e) {
//
//                Log.d(TAG, "InsertData: Error ", e);
//                errorString = e.toString();
//
//                return null;
//            }
//
//        }
//    }
//
//
//    private void showResult(){
//        try {
//            JSONObject jsonObject = new JSONObject(mJsonString);
//            JSONArray jsonArray = jsonObject.getJSONArray("response");
//
//            for(int i=0;i<jsonArray.length();i++){
//
//                JSONObject item = jsonArray.getJSONObject(i);
//
//                String location;
//                location = item.getString("location");
//                List.add(location);
//
//            }
//        } catch (JSONException e) {
//
//            Log.d(TAG, "showResult : ", e);
//        }
//
//    }
}
