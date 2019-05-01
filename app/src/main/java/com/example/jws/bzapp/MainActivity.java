package com.example.jws.bzapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private long lastTimeBackPressed; //뒤로가기 버튼이 클릭된 시간

    ImageButton btnNotice;
    ImageButton btnQuestion;
    ImageButton btnAnal;
    ImageButton btnSurvey;
    ImageButton btnRec;
    ImageButton btnPren;

    TextView textid;
    ImageButton btnMypage;
    ImageButton btnLogout;
    ImageButton btnManager;
    ImageButton btnShare;

    Boolean logincheck, surveycheck;
    String loginID;
    FlipAdapter flipadapter;
    AutoScrollViewPager autoViewPager;
    TextView viewtext;

    String Type;
    String Sales;
    String Location;
    String mJsonString;

String url;
    private static final String TAG = "Login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //오토뷰페이저

        final ArrayList<Integer> data = new ArrayList<>(); //이미지 url를 저장하는 arraylist
        viewtext = (TextView)findViewById(R.id.viewtext);
        autoViewPager = (AutoScrollViewPager)findViewById(R.id.autoViewPager);
        data.add(R.drawable.sosang1);
        data.add(R.drawable.chang1);
        data.add(R.drawable.kpren1);
        flipadapter = new FlipAdapter(this,data);
        autoViewPager.setAdapter(flipadapter); //Auto Viewpager에 Adapter 장착
        autoViewPager.setInterval(2000); // 페이지 넘어갈 시간 간격 설정
        autoViewPager.startAutoScroll(); //Auto Scroll 시작
        autoViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                if(position<data.size()){
                    autoViewPager.setCurrentItem(position+data.size(),false);
                }
                else if(position>=data.size()*2){
                    autoViewPager.setCurrentItem(position-data.size(),false);
                }
                position=position%data.size();
                if (position == 0){  // 첫 페이지
                    viewtext.setText("1/3");

                } else if (position == 1){   //두번째 페이지
                    viewtext.setText("2/3");

                }else if(position==2){
                    viewtext.setText("3/3");
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        btnNotice = (ImageButton) findViewById(R.id.btnNotice);
        btnQuestion = (ImageButton) findViewById(R.id.btnQuestion);
        btnAnal = (ImageButton) findViewById(R.id.btnAnal);
        btnSurvey = (ImageButton) findViewById(R.id.btnSurvey);
        btnRec = (ImageButton) findViewById(R.id.btnRec);
        btnPren = (ImageButton) findViewById(R.id.btnPren);


        //네비게이션 메뉴설정
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View nav_header_view = navigationView.getHeaderView(0);

        btnLogout = (ImageButton) nav_header_view.findViewById(R.id.btnLogout);
        btnMypage = (ImageButton) nav_header_view.findViewById(R.id.btnMypage);
        btnManager = (ImageButton) nav_header_view.findViewById(R.id.nav_manage);
        btnShare = (ImageButton) nav_header_view.findViewById(R.id.nav_share);

        textid = (TextView)nav_header_view.findViewById(R.id.loginid);

        //리사이클뷰에 공지사항 데이터 추가하는 클래스 선언후 실행
        HotList hotList = new HotList();
        hotList.execute();

        //로그인 체크와 아이디 값 가져옥;
        SharedPreferences test = getSharedPreferences("check", Activity.MODE_PRIVATE);
        logincheck = test.getBoolean("check", false);
        surveycheck = test.getBoolean("surveycheck", false);
        loginID = test.getString("id",null);

        //로그인이 되어있을때 실핼될 코드
        if (logincheck) {
            textid.setText(loginID+" 님");
            btnLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LoginCheck loginCheck = new LoginCheck(MainActivity.this);
                    loginCheck.Logout();
                    Intent intent = getIntent();
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
            });
            btnMypage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, Mypage.class);
                    startActivity(intent);
                }
            });
            btnNotice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, NoticeActivity.class);
                    startActivity(intent);
                }
            });

            btnQuestion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, Quetion_rActivity.class);
                    startActivity(intent);
                }
            });

            btnAnal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, MapActivity.class);
                    startActivity(intent);
                }
            });

            btnSurvey.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (surveycheck) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this
                        );
                        alert.setTitle("설문조사");
                        alert.setMessage("설문조사를 다시 하시겠습니까?").setCancelable(false)
                                .setPositiveButton("예", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(MainActivity.this, SurveyActivity.class);
                                        intent.putExtra("ID", loginID);
                                        startActivity(intent);
                                    }
                                }).setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(MainActivity.this, RecommendActivity.class);
                                intent.putExtra("ID", loginID);
                                startActivity(intent);
                            }
                        });

                        AlertDialog alertDialog = alert.create();
                        alertDialog.show();
                    }
                    else {
                        Intent intent = new Intent(MainActivity.this, SurveyActivity.class);
                        intent.putExtra("ID", loginID);
                        startActivity(intent);
                    }
                }
            });

            btnRec.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getsurvey task = new getsurvey();
                    task.execute(loginID);
                }
            });
            btnPren.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, FranchiseActivity1.class);
                    startActivity(intent);
                }
            });

            Menu menu = navigationView.getMenu();
            MenuItem item_Login = menu.findItem(R.id.btnLog);
            item_Login.setVisible(false);//false가 안보임
            MenuItem item_join = menu.findItem(R.id.btnJoin);
            item_join.setVisible(false);
            //getAppKeyHash();
        }
        //로그인이 되어있지 않을때 실핼될 코드
        else {
            btnLogout.setVisibility(View.INVISIBLE);
            btnMypage.setVisibility(View.INVISIBLE);
            Menu menu = navigationView.getMenu();
            MenuItem item_Manage = menu.findItem(R.id.nav_manage);
            item_Manage.setVisible(false);
            btnNotice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, NoticeActivity.class);
                    startActivity(intent);
                }
            });

            btnQuestion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, Quetion_rActivity.class);
                    startActivity(intent);
                }
            });

            btnAnal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, MapActivity.class);
                    startActivity(intent);
                }
            });

            btnSurvey.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this
                    );
                    alert.setTitle("설문조사");
                    alert.setMessage("로그인이 필요한 서비스입니다. 로그인하시겠습니까").setCancelable(false)
                            .setPositiveButton("예", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                }
                            }).setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });

                    AlertDialog alertDialog = alert.create();
                    alertDialog.show();
                }
            });

            btnRec.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this
                    );
                    alert.setTitle("상권 분석");
                    alert.setMessage("로그인이 필요한 서비스입니다. 로그인하시겠습니까").setCancelable(false)
                            .setPositiveButton("예", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                }
                            }).setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });

                    AlertDialog alertDialog = alert.create();
                    alertDialog.show();
                }
            });

            btnPren.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, FranchiseActivity1.class);
                    startActivity(intent);
                }
            });

        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View nav_header_view = navigationView.getHeaderView(0);
        //로그인 체크와 아이디 값 가져옥;
        SharedPreferences test = getSharedPreferences("check", Activity.MODE_PRIVATE);
        logincheck = test.getBoolean("check", false);
        surveycheck = test.getBoolean("surveycheck", false);
        loginID = test.getString("id",null);

        //로그인이 되어있을때 실핼될 코드
        if (logincheck) {
            btnLogout.setVisibility(View.VISIBLE);
            btnMypage.setVisibility(View.VISIBLE);
            Menu menu = navigationView.getMenu();
            MenuItem item_Manage = menu.findItem(R.id.nav_manage);
            item_Manage.setVisible(true);
            textid.setText(loginID+" 님");
            btnLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LoginCheck loginCheck = new LoginCheck(MainActivity.this);
                    loginCheck.Logout();
                    Intent intent = getIntent();
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
            });
            btnMypage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, Mypage.class);
                    startActivity(intent);
                }
            });
            btnNotice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, NoticeActivity.class);
                    startActivity(intent);
                }
            });

            btnQuestion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, Quetion_rActivity.class);
                    startActivity(intent);
                }
            });

            btnAnal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, MapActivity.class);
                    startActivity(intent);
                }
            });

            btnSurvey.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (surveycheck) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                        alert.setTitle("설문조사");
                        alert.setMessage("설문조사를 다시 하시겠습니까?").setCancelable(false)
                                .setPositiveButton("예", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(MainActivity.this, SurveyActivity.class);
                                        intent.putExtra("ID", loginID);
                                        startActivity(intent);
                                    }
                                }).setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                getsurvey task = new getsurvey();
                                task.execute(loginID);
                            }
                        });

                        AlertDialog alertDialog = alert.create();
                        alertDialog.show();

                    }
                    else {
                        Intent intent = new Intent(MainActivity.this, SurveyActivity.class);
                        intent.putExtra("ID", loginID);
                        startActivity(intent);
                    }
                }
            });


            btnRec.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getsurvey task = new getsurvey();
                    task.execute(loginID);
                }
            });


            btnPren.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, FranchiseActivity1.class);
                    startActivity(intent);
                }
            });

            MenuItem item_Login = menu.findItem(R.id.btnLog);
            item_Login.setVisible(false);//false가 안보임
            MenuItem item_join = menu.findItem(R.id.btnJoin);
            item_join.setVisible(false);
            //getAppKeyHash();
        }
        //로그인이 되어있지 않을때 실핼될 코드
        else {
            btnLogout.setVisibility(View.INVISIBLE);
            btnMypage.setVisibility(View.INVISIBLE);
            Menu menu = navigationView.getMenu();
            MenuItem item_Manage = menu.findItem(R.id.nav_manage);
            item_Manage.setVisible(false);
            MenuItem item_Login = menu.findItem(R.id.btnLog);
            item_Login.setVisible(true);//false가 안보임
            MenuItem item_join = menu.findItem(R.id.btnJoin);
            item_join.setVisible(true);
            btnNotice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, NoticeActivity.class);
                    startActivity(intent);
                }
            });

            btnQuestion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, Quetion_rActivity.class);
                    startActivity(intent);
                }
            });

            btnAnal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, MapActivity.class);
                    startActivity(intent);
                }
            });

            btnSurvey.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                    alert.setTitle("설문조사");
                    alert.setMessage("로그인이 필요한 서비스 입니다. 로그인 하시겠습니까?").setCancelable(false)
                            .setPositiveButton("예", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                }
                            }).setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });

                    AlertDialog alertDialog = alert.create();
                    alertDialog.show();
                }
            });

            btnRec.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this
                    );
                    alert.setTitle("상권 분석");
                    alert.setMessage("로그인이 필요한 서비스입니다. 로그인하시겠습니까").setCancelable(false)
                            .setPositiveButton("예", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                }
                            }).setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });

                    AlertDialog alertDialog = alert.create();
                    alertDialog.show();
                }
            });

            btnPren.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, FranchiseActivity1.class);
                    startActivity(intent);
                }
            });

        }
    }

    class HotList extends AsyncTask<Void, Void, String> {


        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                //서버에 있는 php 실행
                URL url = new URL("http://qwerr784.cafe24.com/HList.php");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while ((temp = bufferedReader.readLine()) != null) {
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                //결과 값을 리턴
                return stringBuilder.toString().trim();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        //결과값 출력 메소드
        public void show(String s) {


            ArrayList<HotInfo> arrayList = new ArrayList<>();

            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.HRV);
            RecyclerView.LayoutManager manager = new LinearLayoutManager(MainActivity.this);
            recyclerView.setLayoutManager(manager);

            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count = 0;
                String imageurl, title, date, url;
                while (count < jsonArray.length()) {

                    JSONObject object = jsonArray.getJSONObject(count);
                    imageurl = object.getString("ImageURL");
                    title = object.getString("Title");
                    date = object.getString("Date");
                    url = object.getString("URL");
                    HotInfo hotInfo = new HotInfo(imageurl, title, date, url);
                    arrayList.add(hotInfo);

                    count++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            HotAdapter hotAdapter = new HotAdapter(getApplicationContext(), arrayList);
            recyclerView.setAdapter(hotAdapter);
        }

        @Override
        protected void onPostExecute(String s) {
            show(s);
        }


    }

    /*private void getAppKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                Log.d("Hash key", something);
            }
        } catch (Exception e) {
// TODO Auto-generated catch block
            Log.e("name not found", e.toString());
        }
    } */


    @Override
    public void onBackPressed() {
        //2초 이내에 뒤로가기 버튼을 재 클릭 시 앱 종료
        if (System.currentTimeMillis() - lastTimeBackPressed < 2000)
        {
            finish();
            return;
        }
        //'뒤로' 버튼 한번 클릭 시 메시지
        Toast.makeText(this, "'뒤로' 버튼을 한번 더 누르시면 앱이 종료됩니다.", Toast.LENGTH_SHORT).show();
        //lastTimeBackPressed에 '뒤로'버튼이 눌린 시간을 기록
        lastTimeBackPressed = System.currentTimeMillis();

    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);


        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.btnHome) {
            drawer.closeDrawer(GravityCompat.START);

        } else if (id == R.id.btnLog) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);

        } else if (id == R.id.btnJoin) {
            Intent intent = new Intent(MainActivity.this, JoinActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_manage) {
            Intent intent = new Intent(MainActivity.this, BookmarkActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_share) {
            Intent intent = new Intent(MainActivity.this, HelpActivity.class);
            startActivity(intent);
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }







    private class getsurvey extends AsyncTask<String, Void, String>{

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(MainActivity.this,
                    "성격 급하시네 시발라꺼", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            Log.d(TAG, "response - " + result);
            if (result == null){
               Toast.makeText(getApplicationContext(),"오류",Toast.LENGTH_SHORT).show();
            }
            else {
                mJsonString = result;
                showResult();
            }
        }


        @Override
        protected String doInBackground(String... params) {

            String ID = params[0];

            String serverURL = "http://qwerr784.cafe24.com/findsurvey.php";
            String postParameters = "ID=" + ID;

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

                Log.d(TAG, "InsertData: Error ", e);
                errorString = e.toString();

                return null;
            }

        }
    }


    private void showResult(){
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray("response");

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                 Location = item.getString("location");
                 Type = item.getString("type");
                 Sales = item.getString("sales");
                Intent intent = new Intent(MainActivity.this, RecommendActivity.class);
                intent.putExtra("location",Location);
                intent.putExtra("sales",Sales);
                intent.putExtra("type",Type);
                startActivity(intent);

            }
        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }

}
