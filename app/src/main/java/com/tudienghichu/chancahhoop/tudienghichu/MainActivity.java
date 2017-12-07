package com.tudienghichu.chancahhoop.tudienghichu;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.tudienghichu.chancahhoop.tudienghichu.Fragment.SampleFragmentPagerAdapter;

public class MainActivity extends AppCompatActivity {
   public static ViewPager viewPager;
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    private static final int REQUEST_WRITE_PERMISSION = 786;
    private static final int REQUEST_RECORD = 999;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        initPermission();
        // Get the ViewPager and set it's PagerAdapter so that it can display items
         viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new SampleFragmentPagerAdapter(getSupportFragmentManager(),
                MainActivity.this));


        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

    }
   @RequiresApi(api = Build.VERSION_CODES.M)
   public void initPermission(){

try {
//    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
//            != PackageManager.PERMISSION_GRANTED) {
//        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
//                REQUEST_WRITE_PERMISSION);
//    }
//    if (checkSelfPermission(Manifest.permission.CAMERA)
//            != PackageManager.PERMISSION_GRANTED) {
//        requestPermissions(new String[]{Manifest.permission.CAMERA},
//                MY_CAMERA_REQUEST_CODE);
//    }

//    if (checkSelfPermission(Manifest.permission.RECORD_AUDIO)
//            != PackageManager.PERMISSION_GRANTED) {
//        requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO},
//                REQUEST_RECORD);
//    }
}catch (NoSuchMethodError e){
    Log.d("////",""+e);
}


//       if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//           if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.CAMERA)) {
//           } else {
//               ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
//           }
//        }
//       if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//           if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
//           } else {
//               ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
//           }
//       }
//       if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
//           if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
//                   Manifest.permission.RECORD_AUDIO)) {
//           } else {
//               ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.RECORD_AUDIO}, REQUEST_RECORD);
//           }
//       }

}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_favorite:
                Intent intent=new Intent(MainActivity.this,HuongDan.class);
                startActivity(intent);
                return true;



            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

}