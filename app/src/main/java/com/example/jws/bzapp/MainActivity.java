package com.example.jws.bzapp;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;

import java.security.MessageDigest;

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

    Boolean logincheck, surveycheck;
    String loginID;


    private static final String TAG = "Login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //로그인 체크와 아이디 값 가져옥;
        SharedPreferences test = getSharedPreferences("check", Activity.MODE_PRIVATE);
        logincheck = test.getBoolean("check", false);
        surveycheck = test.getBoolean("surveycheck", false);
        loginID = test.getString("id", null);


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

        textid = (TextView) nav_header_view.findViewById(R.id.loginid);

        //로그인이 되어있을때 실핼될 코드
        if (logincheck) {
            textid.setText(loginID + " 님");
            btnLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LoginCheck loginCheck = new LoginCheck(MainActivity.this);
                    loginCheck.Logout();
                    Intent intent = getIntent();
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
                        Toast.makeText(getApplicationContext(), "상권분석", Toast.LENGTH_LONG).show();
                    } else {
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
            Menu menu = navigationView.getMenu();
            MenuItem item_Manage = menu.findItem(R.id.nav_manage);
            item_Manage.setVisible(false);
            btnLogout.setVisibility(View.INVISIBLE);
            btnMypage.setVisibility(View.INVISIBLE);
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
