package com.example.jws.bzapp;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

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

    Boolean logincheck, surveycheck;
    String loginID;
    FlipAdapter flipadapter;
    AutoScrollViewPager autoViewPager;
    TextView viewtext;



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
        data.add(R.drawable.chang1);
        data.add(R.drawable.sosang1);
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



        //로그인 체크와 아이디 값 가져옥;
        SharedPreferences test = getSharedPreferences("check", Activity.MODE_PRIVATE);
        logincheck = test.getBoolean("check", false);
        surveycheck = test.getBoolean("surveycheck", false);
        loginID = test.getString("id",null);


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

        textid = (TextView)nav_header_view.findViewById(R.id.loginid);

        //리사이클뷰에 공지사항 데이터 추가하는 클래스 선언후 실행
        HotList hotList = new HotList();
        hotList.execute();

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
                        Toast.makeText(getApplicationContext(),"상권분석",Toast.LENGTH_LONG).show();
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
                    Intent intent = new Intent(MainActivity.this, RecommendActivity.class);
                    startActivity(intent);
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
                    Intent intent = new Intent(MainActivity.this, SurveyActivity.class);
                    startActivity(intent);
                }
            });

            btnRec.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, RecommendActivity.class);
                    startActivity(intent);
                }
            });

            btnPren.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, FranchiseActivity1.class);
                    startActivity(intent);
                }
            });

            //getAppKeyHash();
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
                URL url = new URL("http://qwerr784.cafe24.com/NList.php");
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
                String image, title, date, url;
                while (count < jsonArray.length()) {

                    JSONObject object = jsonArray.getJSONObject(count);
                    image = object.getString("image");
                    title = object.getString("title");
                    date = object.getString("date");
                    url = object.getString("url");
                    HotInfo hotInfo = new HotInfo(image, title, date, url);
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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //카카오 유저 정보 받아오기
    public void requestMe() {
        //유저의 정보를 받아오는 함수

        UserManagement.requestMe(new MeResponseCallback() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                Log.e(TAG, "error message=" + errorResult);
//                super.onFailure(errorResult);
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {

                Log.d(TAG, "onSessionClosed1 =" + errorResult);
            }

            @Override
            public void onNotSignedUp() {
                //카카오톡 회원이 아닐시
                Log.d(TAG, "onNotSignedUp ");

            }

            @Override
            public void onSuccess(UserProfile result) {
                Log.e("UserProfile", result.toString());
                Log.e("UserProfile", result.getId() + "");
            }
        });
    }

}
