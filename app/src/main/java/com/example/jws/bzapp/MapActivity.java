package com.example.jws.bzapp;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import uk.co.senab.photoview.PhotoViewAttacher;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback, NavigationView.OnNavigationItemSelectedListener {
    private GoogleMap mGoogleMap;
    private Spinner spinner;
    private static final String TAG = "googlemap_example";
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    // onRequestPermissionsResult에서 수신된 결과에서 ActivityCompat.requestPermissions를 사용한 퍼미션 요청을 구별하기 위해 사용됩니다.
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    boolean needRequest = false;
    // 앱을 실행하기 위해 필요한 퍼미션을 정의합니다.
    String[] REQUIRED_PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};  // 외부 저장소
    Location mCurrentLocatiion;
    LatLng currentPosition;
    Geocoder geocoder;
    double mLat = 37.56;
    double mLong = 126.97;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest locationRequest;
    private Location location;
    ImageView centerimg;
    Button btnResult;
    ImageButton btnSearch;
    EditText editSearch;
    ImageButton btnBack;
    PhotoViewAttacher photoViewAttacher;
    Button check;
    Marker mCenterMarker;
    private View mLayout;  // Snackbar 사용하기 위해서는 View가 필요합니다.
    // (참고로 Toast에서는 Context가 필요했습니다.)
    int radius = 100;

    TextView textid;
    ImageButton btnMypage;
    ImageButton btnLogout;

    Boolean logincheck;
    String loginID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        mLayout = findViewById(R.id.layout_main);
        locationRequest = new LocationRequest()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder builder =
                new LocationSettingsRequest.Builder();
        builder.addLocationRequest(locationRequest);
        geocoder = new Geocoder(this);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        btnResult = (Button) findViewById(R.id.btnResult);
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnSearch = (ImageButton) findViewById(R.id.Search);
        editSearch = (EditText) findViewById(R.id.locationSearch);
        check = (Button) findViewById(R.id.checkbtn);

        //로그인 체크와 아이디 값 가져옥;
        SharedPreferences test = getSharedPreferences("check", Activity.MODE_PRIVATE);
        logincheck = test.getBoolean("check", false);
        loginID = test.getString("id", null);

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

        if (logincheck) {
            Menu menu = navigationView.getMenu();
            MenuItem item_Login = menu.findItem(R.id.btnLog);
            item_Login.setVisible(false);//false가 안보임
            MenuItem item_join = menu.findItem(R.id.btnJoin);
            item_join.setVisible(false);
            textid.setText(loginID + " 님");
            btnLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LoginCheck loginCheck = new LoginCheck(MapActivity.this);
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
                    Toast.makeText(getApplicationContext(), "마이페이지", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            btnLogout.setVisibility(View.INVISIBLE);
            btnMypage.setVisibility(View.INVISIBLE);
        }

        //GSP환경을 체크해서 자기위치를 뛰운다.
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startLocationUpdates();

            }
        });


        btnResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AnalysisActivity.class);
                intent.putExtra("mLat", mLat);
                intent.putExtra("mLong", mLong);
                String text = spinner.getSelectedItem().toString();
                if (text.equals("100m")) {
                    intent.putExtra("a", 100);
                } else if (text.equals("200m")) {
                    intent.putExtra("a", 200);
                } else if (text.equals("500m")) {
                    intent.putExtra("a", 500);
                } else if (text.equals("1km")) {
                    intent.putExtra("a", 1000);
                }
                startActivity(intent);
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });


        String apiKey = "AIzaSyChj-A1rkGjpfxlRflVoZSB1ZqJSWIAPkE";

        if (apiKey.equals("")) {
            Toast.makeText(this, "No API key defined in gradle.properties", Toast.LENGTH_LONG).show();
            return;
        }

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = editSearch.getText().toString();
                double lat = 0.0, lon = 0.0;
                List<Address> addr = null;
                try {
                    addr = geocoder.getFromLocationName(address, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (addr != null) {
                    for (int i = 0; i < addr.size(); i++) {
                        Address latlon = addr.get(i);
                        lat = latlon.getLatitude();
                        lon = latlon.getLongitude();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "검색어를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                mLat = lat;
                mLong = lon;
                LatLng search = new LatLng(mLat, mLong);
                MarkerOptions maker = new MarkerOptions();
                maker.position(search);
                mGoogleMap.addMarker(maker);
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(search, 16));


            }
        });


        ///////////////////////////////////스피너//////////////////////////////////


        spinner = (Spinner) findViewById(R.id.spinner);

        String[] list = new String[4];
        list[0] = "100m";
        list[1] = "200m";
        list[2] = "500m";
        list[3] = "1km";
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, list);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(0xffED3050);
                String text = spinner.getSelectedItem().toString();
                if (text.equals("100m")) {
                    CameraUpdate cameraUpdate = CameraUpdateFactory.zoomTo(18);
                    mGoogleMap.moveCamera(cameraUpdate);
                    mGoogleMap.clear();
                    radius = 100;
                    onAddCircle100(radius);
                } else if (text.equals("200m")) {
                    CameraUpdate cameraUpdate = CameraUpdateFactory.zoomTo(17);
                    mGoogleMap.moveCamera(cameraUpdate);
                    mGoogleMap.clear();
                    radius = 200;
                    onAddCircle200(radius);
                } else if (text.equals("500m")) {
                    CameraUpdate cameraUpdate = CameraUpdateFactory.zoomTo(16);
                    mGoogleMap.moveCamera(cameraUpdate);
                    mGoogleMap.clear();
                    radius = 500;
                    onAddCircle500(radius);
                } else if (text.equals("1km")) {
                    CameraUpdate cameraUpdate = CameraUpdateFactory.zoomTo(15);
                    mGoogleMap.moveCamera(cameraUpdate);
                    mGoogleMap.clear();
                    radius = 1000;
                    onAddCircle1000(radius);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

//////////////////////////////////////////////create종료///////////////////////////////////////////////////////////////


    @Override
    protected void onResume() {
        super.onResume();

        //로그인 체크와 아이디 값 가져옥;
        SharedPreferences test = getSharedPreferences("check", Activity.MODE_PRIVATE);
        logincheck = test.getBoolean("check", false);
        loginID = test.getString("id", null);


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View nav_header_view = navigationView.getHeaderView(0);

        if (logincheck) {
            Menu menu = navigationView.getMenu();
            MenuItem item_Login = menu.findItem(R.id.btnLog);
            item_Login.setVisible(false);//false가 안보임
            MenuItem item_join = menu.findItem(R.id.btnJoin);
            item_join.setVisible(false);
            MenuItem item_Manage = menu.findItem(R.id.nav_manage);
            item_Manage.setVisible(true);
            textid.setText(loginID + " 님");
            btnLogout.setVisibility(View.VISIBLE);
            btnMypage.setVisibility(View.VISIBLE);
            btnLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LoginCheck loginCheck = new LoginCheck(MapActivity.this);
                    loginCheck.Logout();
                    Intent intent = getIntent();
                    startActivity(intent);
                    finish();
                }
            });
            btnMypage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MapActivity.this, Mypage.class);
                    startActivity(intent);
                }
            });
        } else {
            btnLogout.setVisibility(View.INVISIBLE);
            btnMypage.setVisibility(View.INVISIBLE);
            Menu menu = navigationView.getMenu();
            MenuItem item_Manage = menu.findItem(R.id.nav_manage);
            item_Manage.setVisible(false);
            MenuItem item_Login = menu.findItem(R.id.btnLog);
            item_Login.setVisible(true);//false가 안보임
            MenuItem item_join = menu.findItem(R.id.btnJoin);
            item_join.setVisible(true);
        }
    }

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
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.btnHome) {
            Intent intent = new Intent(MapActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        } else if (id == R.id.btnLog) {
            Intent intent = new Intent(MapActivity.this, LoginActivity.class);
            startActivity(intent);
            // Handle the camera action
        } else if (id == R.id.btnJoin) {
            Intent intent = new Intent(MapActivity.this, JoinActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_manage) {
            Intent intent = new Intent(MapActivity.this, BookmarkActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_share) {
            Intent intent = new Intent(MapActivity.this, HelpActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);
            List<Location> locationList = locationResult.getLocations();
            if (locationList.size() > 0) {
                location = locationList.get(locationList.size() - 1);
                //location = locationList.get(0);
                mLat = location.getLatitude();
                mLong = location.getLongitude();
                currentPosition
                        = new LatLng(mLat, mLong);
                String markerTitle = getCurrentAddress(currentPosition);
                //현재 위치에 마커 생성하고 이동
                setCurrentLocation(location);
                mCurrentLocatiion = location;


            }
        }

    };


    private void startLocationUpdates() {

        if (!checkLocationServicesStatus()) { //GPS 설정 상태가 x면

            Log.d(TAG, "startLocationUpdates : call showDialogForLocationServiceSetting");
            showDialogForLocationServiceSetting();
        } else {

            int hasFineLocationPermission = ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION);
            int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION);

            if (hasFineLocationPermission != PackageManager.PERMISSION_GRANTED ||
                    hasCoarseLocationPermission != PackageManager.PERMISSION_GRANTED) {
                return;
            }

            if (checkPermission())
                mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null); //자기 우치 받아오는 코드
            if (mGoogleMap != null)
                mGoogleMap.setMyLocationEnabled(true);
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mGoogleMap = googleMap;
        //런타임 퍼미션 요청 대화상자나 GPS 활성 요청 대화상자 보이기전에
        //지도의 초기위치를 서울로 이동
        setDefaultLocation();
        onAddCircle100(100);

        //런타임 퍼미션 처리
        // 1. 위치 퍼미션을 가지고 있는지 체크합니다.
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);


        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {
            // 2. 이미 퍼미션을 가지고 있다면
            // ( 안드로이드 6.0 이하 버전은 런타임 퍼미션이 필요없기 때문에 이미 허용된 걸로 인식합니다.)
            startLocationUpdates(); // 3. 위치 업데이트 시작
        } else {  //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요합니다. 2가지 경우(3-1, 4-1)가 있습니다.

            // 3-1. 사용자가 퍼미션 거부를 한 적이 있는 경우에는
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])) {
                // 3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유를 설명해줄 필요가 있습니다.
                Snackbar.make(mLayout, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.",
                        Snackbar.LENGTH_INDEFINITE).setAction("확인", new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        // 3-3. 사용자게에 퍼미션 요청을 합니다. 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                        ActivityCompat.requestPermissions(MapActivity.this, REQUIRED_PERMISSIONS,
                                PERMISSIONS_REQUEST_CODE);
                    }
                }).show();
            } else {
                // 4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 합니다.
                // 요청 결과는 onRequestPermissionResult에서 수신됩니다.A
                ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }
        }

        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
        mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(16));
        mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng latLng) {

                Log.d(TAG, "onMapClick :");
            }
        });
        mGoogleMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {   //카메라 움직일때마다 실행되는 메소드
            @Override
            public void onCameraMove() {

                //Remove previous center if it exists
                if (mCenterMarker != null) {
                    mCenterMarker.remove();  //마커가 널이아니면 전체 null 지움
                }

                CameraPosition test = mGoogleMap.getCameraPosition();
                mLat = mGoogleMap.getCameraPosition().target.latitude;
                mLong = mGoogleMap.getCameraPosition().target.longitude;
                //Assign mCenterMarker reference:

                if (radius == 100) {
                    mGoogleMap.clear();
                    onAddCircle100(radius);
                } else if (radius == 200) {
                    mGoogleMap.clear();
                    onAddCircle200(radius);
                } else if (radius == 500) {
                    mGoogleMap.clear();
                    onAddCircle500(radius);
                } else if (radius == 1000) {
                    mGoogleMap.clear();
                    onAddCircle1000(radius);
                }
            }
        });
    }


    public String getCurrentAddress(LatLng latlng) {

        //지오코더... GPS를 주소로 변환
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        List<Address> addresses;

        try {

            addresses = geocoder.getFromLocation(
                    latlng.latitude,
                    latlng.longitude,
                    1);
        } catch (IOException ioException) {
            //네트워크 문제
            Toast.makeText(this, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            return "지오코더 서비스 사용불가";
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(this, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            return "잘못된 GPS 좌표";

        }


        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(this, "주소 미발견", Toast.LENGTH_LONG).show();
            return "주소 미발견";

        } else {
            Address address = addresses.get(0);
            return address.getAddressLine(0).toString();
        }

    }


    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    public void setCurrentLocation(Location location, String markerTitle, String markerSnippet) {

        if (mCenterMarker != null) mCenterMarker.remove();

        //현재위치의 위도 경도 가져옴
        LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(currentLocation);
        markerOptions.title(markerTitle);
        markerOptions.snippet(markerSnippet);
        markerOptions.draggable(true);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        mCenterMarker = this.mGoogleMap.addMarker(markerOptions);

        this.mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));

    }


    public void setCurrentLocation(Location location) {


        LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(currentLatLng);
//        markerOptions.title(markerTitle);
//        markerOptions.snippet(markerSnippet);
        markerOptions.draggable(true);


        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(currentLatLng);
        mGoogleMap.moveCamera(cameraUpdate);

    }


    public void setDefaultLocation() {


        //디폴트 위치, Seoul
        LatLng DEFAULT_LOCATION = new LatLng(mLat, mLong);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(DEFAULT_LOCATION, 16);
        mGoogleMap.moveCamera(cameraUpdate);

    }


    //여기부터는 런타임 퍼미션 처리을 위한 메소드들
    private boolean checkPermission() {

        int hasFineLocationPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);


        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {
            return true;
        }

        return false;

    }


    /*
     * ActivityCompat.requestPermissions를 사용한 퍼미션 요청의 결과를 리턴받는 메소드입니다.
     */
    @Override
    public void onRequestPermissionsResult(int permsRequestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grandResults) {

        if (permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {

            // 요청 코드가 PERMISSIONS_REQUEST_CODE 이고, 요청한 퍼미션 개수만큼 수신되었다면

            boolean check_result = true;


            // 모든 퍼미션을 허용했는지 체크합니다.

            for (int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }


            if (check_result) {

                // 퍼미션을 허용했다면 위치 업데이트를 시작합니다.
                startLocationUpdates();
            } else {
                // 거부한 퍼미션이 있다면 앱을 사용할 수 없는 이유를 설명해주고 앱을 종료합니다.2 가지 경우가 있습니다.

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])
                        || ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[1])) {


                    // 사용자가 거부만 선택한 경우에는 앱을 다시 실행하여 허용을 선택하면 앱을 사용할 수 있습니다.
                    Snackbar.make(mLayout, "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요. ",
                            Snackbar.LENGTH_INDEFINITE).setAction("확인", new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {

                            finish();
                        }
                    }).show();

                } else {


                    // "다시 묻지 않음"을 사용자가 체크하고 거부를 선택한 경우에는 설정(앱 정보)에서 퍼미션을 허용해야 앱을 사용할 수 있습니다.
                    Snackbar.make(mLayout, "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ",
                            Snackbar.LENGTH_INDEFINITE).setAction("확인", new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {

                            finish();
                        }
                    }).show();
                }
            }

        }
    }


    //여기부터는 GPS 활성화를 위한 메소드들
    private void showDialogForLocationServiceSetting() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MapActivity.this);
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                + "위치 설정을 수정하실래요?");
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
//                if (checkPermission())
//                    mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
//                if (mGoogleMap!=null)
//                    mGoogleMap.setMyLocationEnabled(true);
                Intent callGPSSettingIntent
                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
//                if (checkPermission())
//                if (mGoogleMap!=null)
//                    mGoogleMap.setMyLocationEnabled(true);
                dialog.cancel();
            }
        });
        builder.create().show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case GPS_ENABLE_REQUEST_CODE:

                //사용자가 GPS 활성 시켰는지 검사
                if (checkLocationServicesStatus()) {
                    if (checkLocationServicesStatus()) {

                        Log.d(TAG, "onActivityResult : GPS 활성화 되있음");
                        needRequest = true;
                        return;
                    }
                }
                break;
        }
    }


    //
    public void onAddCircle100(int a) {
        LatLng position = new LatLng(mLat, mLong);

        //나의 위치 마커
        //MarkerOptions mymarker = new MarkerOptions().position(position);

        //사이클 반경 설정
        CircleOptions circle = new CircleOptions().center(position).radius(a)//반경
                .strokeWidth(3)//선 넓이
                .fillColor(0x44808080)
                .strokeColor(Color.GRAY);
        //this.mGoogleMap.addMarker(mymarker);
        this.mGoogleMap.addCircle(circle);
    }

    public void onAddCircle200(int a) {
        LatLng position = new LatLng(mLat, mLong);

        //나의 위치 마커
        //MarkerOptions mymarker = new MarkerOptions().position(position);

        //사이클 반경 설정
        CircleOptions circle = new CircleOptions().center(position).radius(a)//반경
                .strokeWidth(3)//선 넓이
                .fillColor(0x44808080)
                .strokeColor(Color.GRAY);
        //this.mGoogleMap.addMarker(mymarker);
        this.mGoogleMap.addCircle(circle);
    }

    public void onAddCircle500(int a) {
        LatLng position = new LatLng(mLat, mLong);

        //나의 위치 마커
        //MarkerOptions mymarker = new MarkerOptions().position(position);

        //사이클 반경 설정
        CircleOptions circle = new CircleOptions().center(position).radius(a)//반경
                .strokeWidth(3)//선 넓이
                .fillColor(0x44808080)
                .strokeColor(Color.GRAY);
        //this.mGoogleMap.addMarker(mymarker);
        this.mGoogleMap.addCircle(circle);
    }

    public void onAddCircle1000(int a) {
        LatLng position = new LatLng(mLat, mLong);

        //나의 위치 마커
        //MarkerOptions mymarker = new MarkerOptions().position(position);

        //사이클 반경 설정
        CircleOptions circle = new CircleOptions().center(position).radius(a)//반경
                .strokeWidth(3)//선 넓이
                .fillColor(0x44808080)
                .strokeColor(Color.GRAY);
        //this.mGoogleMap.addMarker(mymarker);
        this.mGoogleMap.addCircle(circle);
    }
}