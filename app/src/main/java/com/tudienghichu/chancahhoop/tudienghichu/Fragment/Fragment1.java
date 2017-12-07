package com.tudienghichu.chancahhoop.tudienghichu.Fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.tudienghichu.chancahhoop.tudienghichu.Custom.CustomGridViewAdapter;
import com.tudienghichu.chancahhoop.tudienghichu.QuanLy.QuanLy;
import com.tudienghichu.chancahhoop.tudienghichu.QuanLy.QuanLyDatabase;
import com.tudienghichu.chancahhoop.tudienghichu.R;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import id.zelory.compressor.Compressor;

import static android.app.Activity.RESULT_OK;

/**
 * Created by hoadt on 15/10/2017.
 */

public class Fragment1  extends Fragment {
    Button btnChiTiet;
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    private static final int REQUEST_RECORD = 999;
    private static final int REQUEST_WRITE_PERMISSION = 786;
    public static final String ARG_PAGE = "ARG_PAGE";
    private MediaRecorder myRecorder;
    private MediaPlayer myPlayer;
    public static int flag, flag2 = 0;

    private String outputFile = "";
    private String outputFile2 = "";
    public static ImageView imageView;
    static QuanLyDatabase quanLyDatabase;
    QuanLy quanLy;
    public static Button btnNhanDienTiengAnh, btnNhanDienTiengViet, btnTiengViet, btnLen, btnSuong, btnTiengAnh, btnThem, btnSua, btnXoa, btnPhatAmTiengViet, btnPhatAmTiengAnh;
    public static EditText edTiengViet, edTiengAnh, edGhiChu;
    Bitmap bitmap;
    int RESULT_LOAD_IMG = 9;
    int CAMERA_REQUEST = 999;


    int i, j = 0;

    public static Fragment1 newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        Fragment1 fragment = new Fragment1();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.layout_fragment1, container, false);

        initPermission();
        // khai bao database;
        quanLyDatabase = new QuanLyDatabase(getActivity());
        quanLyDatabase.getAll();

        // khoi tao view
        imageView = (ImageView) view.findViewById(R.id.imageView);
        btnTiengViet = (Button) view.findViewById(R.id.btnTiengViet);
        btnTiengAnh = (Button) view.findViewById(R.id.btnTiengAnh);
        btnPhatAmTiengViet = (Button) view.findViewById(R.id.btnPhatAmTiengViet);
        btnPhatAmTiengAnh = (Button) view.findViewById(R.id.btnPhatAmTiengAnh);
        btnLen = (Button) view.findViewById(R.id.btnLen);
        btnSuong = (Button) view.findViewById(R.id.btnSuong);
        btnThem = (Button) view.findViewById(R.id.btnThem);
        btnSua = (Button) view.findViewById(R.id.btnSua);
        btnXoa = (Button) view.findViewById(R.id.btnXoa);
        btnChiTiet = (Button) view.findViewById(R.id.btnChiTiet);
        edTiengViet = (EditText) view.findViewById(R.id.edTiengViet);
        edTiengAnh = (EditText) view.findViewById(R.id.edTiengAnh);
        edGhiChu = (EditText) view.findViewById(R.id.edGhiChu);
        btnNhanDienTiengAnh = (Button) view.findViewById(R.id.btnNhanDienTiengAnh);
        btnNhanDienTiengViet = (Button) view.findViewById(R.id.btnNhanDienTiengViet);

        // xu ly su kien
        imageView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

//                Toast.makeText(getActivity(),"imgview",Toast.LENGTH_SHORT).show();

                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.custom_dialog);

                ImageView imgGallery = (ImageView) dialog.findViewById(R.id.imgGallery);
                ImageView imgCamera = (ImageView) dialog.findViewById(R.id.imgCamera);
                imgGallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            if (ActivityCompat.checkSelfPermission(getActivity(),Manifest.permission.READ_EXTERNAL_STORAGE)
                                    != PackageManager.PERMISSION_GRANTED) {
                                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                        REQUEST_WRITE_PERMISSION);
                            }
                        }catch (NoSuchMethodError e){
                            Log.d("////",""+e);
                        }
                        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                        photoPickerIntent.setType("image/*");
                        startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
                        dialog.cancel();

                    }
                });
                imgCamera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                                    != PackageManager.PERMISSION_GRANTED) {
                                requestPermissions(new String[]{Manifest.permission.CAMERA},
                                        MY_CAMERA_REQUEST_CODE);
                            }} catch (NoSuchMethodError e) {
                            Log.d("////", "" + e);
                        }
                        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, CAMERA_REQUEST);
                        dialog.cancel();
                    }
                });
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());


                dialog.getWindow().setAttributes(lp);
                dialog.show();
            }
        });
        btnTiengViet.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                i++;

                if (i == 1) {
                    outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/amthanhtiengviet.3gpp";
                    khoitaoamthanh(outputFile);
                    start(view);
                    btnTiengViet.setBackgroundResource(R.drawable.stop);
                } else {
                    btnTiengViet.setBackgroundResource(R.drawable.phatam);
                    stop(view);
                    i = 0;
                }
            }
        });
        btnPhatAmTiengViet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edTiengViet.getText().toString().equals("")) {
                    play(view, outputFile);
                } else {
                    flag2 = 1;
                    quanLyDatabase.TimAmThanhTV(edTiengViet.getText().toString(), "", "");
                }
            }
        });
        btnTiengAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                j++;

                if (j == 1) {
                    outputFile2 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/amthanhtienganh.3gpp";
                    khoitaoamthanh(outputFile2);
                    start(view);
                    btnTiengAnh.setBackgroundResource(R.drawable.stop);
                } else {

                    btnTiengAnh.setBackgroundResource(R.drawable.phatam);
                    stop(view);
                    j = 0;
                }
            }
        });
        btnPhatAmTiengAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edTiengAnh.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Ô Tiếng Anh trống", Toast.LENGTH_SHORT).show();
                    play(view, outputFile2);
                } else {
                    flag2 = 2;
                    quanLyDatabase.TimAmThanhTV("", edTiengAnh.getText().toString(), "");
                }
            }
        });
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 1;

                setSQLite();
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon);
//                imageView.setImageBitmap(bitmap);
//                stop(view);
//                stopPlay(view);
//                outputFile="" ;
//                outputFile2="";
//                btnTiengAnh.setBackgroundResource(R.drawable.phatam);
//                btnTiengViet.setBackgroundResource(R.drawable.phatam);


            }
        });
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                outputFile = "";
                outputFile2 = "";
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon);
                imageView.setImageBitmap(bitmap);
                edTiengViet.setText("");
                edTiengAnh.setText("");
                edGhiChu.setText("");

            }
        });
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 2;
                setSQLite();


//                stop(view);
//                stopPlay(view);
//                outputFile="" ;
//                outputFile2="";
//                btnTiengAnh.setBackgroundResource(R.drawable.phatam);
//                btnTiengViet.setBackgroundResource(R.drawable.phatam);
            }
        });
        btnLen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quanLyDatabase.TimAmThanhTV("", edTiengAnh.getText().toString(), "");
            }
        });
        btnSuong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                quanLyDatabase.TimAmThanhTV(edTiengViet.getText().toString(), "", "");
            }
        });

        btnChiTiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                builder1.setMessage(edGhiChu.getText().toString());
                builder1.setCancelable(true);
                builder1.setPositiveButton(
                        "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });


                AlertDialog alert11 = builder1.create();
                alert11.show();
            }

        });
        btnNhanDienTiengAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(intent, 10);
                } else {
                    Toast.makeText(getActivity(), "Thiết bị của bạn không đươc hỗ trợ!", Toast.LENGTH_SHORT).show();
                }

            }

        });
        btnNhanDienTiengViet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(intent, 11);
                } else {
                    Toast.makeText(getActivity(), "Thiết bị của bạn không đươc hỗ trợ!", Toast.LENGTH_SHORT).show();
                }

            }

        });
        return view;
    }


    private void setSQLite() {
        if (edTiengViet.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Ô Tiếng Việt còn trống!", Toast.LENGTH_SHORT).show();
        } else if (edTiengAnh.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Ô Tiếng Anh còn trống!", Toast.LENGTH_SHORT).show();
        } else {
            quanLy = new QuanLy();
//            quanLy.setMaTV(String.valueOf(QuanLyDatabase.id));
            quanLy.setMaTV(edTiengViet.getText().toString() + edTiengAnh.getText().toString());
            quanLy.setHinhAnh(getBytes(bitmap));
            quanLy.setTiengViet(edTiengViet.getText().toString());
            quanLy.setAmThanhTiengViet(FileLocal_To_Byte(outputFile));
            quanLy.setTiengAnh(edTiengAnh.getText().toString());
            quanLy.setAmThanhTiengAnh(FileLocal_To_Byte(outputFile2));
            quanLy.setGhiChu(edGhiChu.getText().toString());

            if (flag == 2) {
                if (Fragment2.vitri == -1) {
                    Toast.makeText(getActivity(), "Bạn cần chọn từ vựng", Toast.LENGTH_SHORT).show();
                } else {
                    quanLyDatabase.suaSP(Fragment2.qlArr.get(Fragment2.vitri).getMaTV(), quanLy);
                    for (int k = 0; k < CustomGridViewAdapter.listarr.size(); k++) {
                        if (CustomGridViewAdapter.listarr.get(k).getMaTV().equals(Fragment2.qlArr.get(Fragment2.vitri).getMaTV())) {
                            CustomGridViewAdapter.listarr.set(k, quanLy);
                        }
                    }
                    Fragment2.qlArr.set(Fragment2.vitri, quanLy);
                    QuanLyDatabase.mangGhiAm.set(Fragment2.vitri, FileLocal_To_Byte(outputFile));
                    QuanLyDatabase.mangGhiAm2.set(Fragment2.vitri, FileLocal_To_Byte(outputFile2));
                    Fragment2.customGridAdapter.notifyDataSetChanged();
                    Toast.makeText(getActivity(), "Đã sửa từ vựng!", Toast.LENGTH_SHORT).show();
                    Fragment2.vitri = -1;

//                    return;
                }


            }
            if (flag == 1) {
                if (quanLyDatabase.sosanhTV(edTiengViet.getText().toString() + edTiengAnh.getText().toString())) {
                    Toast.makeText(getActivity(), "Từ " + edTiengViet.getText().toString() + " --> " + edTiengAnh.getText().toString() + " đã có trong danh sách", Toast.LENGTH_SHORT).show();
                } else {

                    quanLyDatabase.themSanPham(quanLy);
                    Fragment2.qlArr.add(quanLy);
                    CustomGridViewAdapter.listarr.add(quanLy);
                    QuanLyDatabase.mangGhiAm.add(FileLocal_To_Byte(outputFile));
                    QuanLyDatabase.mangGhiAm2.add(FileLocal_To_Byte(outputFile2));
                    Toast.makeText(getActivity(), "Đã thêm từ vựng!", Toast.LENGTH_SHORT).show();
                    Fragment2.customGridAdapter.notifyDataSetChanged();

                }

            }


        }
    }

    public void start(View view) {
        try {
            myRecorder.prepare();
            myRecorder.start();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Toast.makeText(getActivity(), "Đang ghi âm!", Toast.LENGTH_SHORT).show();
    }

    public void stop(View view) {
        try {
            myRecorder.stop();
            myRecorder.release();
            myRecorder = null;
            Toast.makeText(getActivity(), "Ngừng ghi âm", Toast.LENGTH_SHORT).show();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public void stopPlay(View view) {
        try {
            if (myPlayer != null) {
                myPlayer.stop();
                myPlayer.release();
                myPlayer = null;

                Toast.makeText(getActivity(), "Stop playing the recording...", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play(View view, String outPut) {
        try {
            myPlayer = new MediaPlayer();
            myPlayer.setDataSource(outPut);
            myPlayer.prepare();
            myPlayer.start();

            Toast.makeText(getActivity(), "Phát ghi âm...", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void khoitaoamthanh(String outPut) {
        myRecorder = new MediaRecorder();
        myRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        myRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        myRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        myRecorder.setOutputFile(outPut);
    }

    @Override
    // tối ưu hóa hình ảnh
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        //chèn hình chụp vào imageview
        if (reqCode == RESULT_LOAD_IMG && resultCode == RESULT_OK) {
//            Bitmap photo = (Bitmap) data.getExtras().get("data");

            final Uri imageUri = data.getData();

            try {
                bitmap = new Compressor(getActivity()).compressToBitmap(new File(imageUri.getPath().substring(6)));
                imageView = (ImageView) getView().findViewById(R.id.imageView);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        //chèn hình chụp vào imageview
        if (reqCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            try {
                bitmap = (Bitmap) data.getExtras().get("data");
                imageView.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Đã xảy ra sự cố!", Toast.LENGTH_LONG).show();
            }

        } else {

        }
        switch (reqCode) {
            case 10:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    edTiengAnh.setText(result.get(0));
                }
                break;
            case 11:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    edTiengViet.setText(result.get(0));
                }
                break;
        }
    }

    //convert byte âm thanh
    public byte[] FileLocal_To_Byte(String path) {
        File file = new File(path);
        int size = (int) file.length();
        byte[] bytes = new byte[size];
        try {
            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
            buf.read(bytes, 0, bytes.length);
            buf.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return bytes;
    }

    // tối ưu hóa hình ảnh
    public byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        if (bitmap != null)
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    // convert from byte array to bitmap
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    public void initPermission(){

        try {
//    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
//            != PackageManager.PERMISSION_GRANTED) {
//        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
//                REQUEST_WRITE_PERMISSION);
//    }
//    if (ActivityCompat.checkSelfPermission(getActivity(),Manifest.permission.CAMERA)
//            != PackageManager.PERMISSION_GRANTED) {
//        requestPermissions(new String[]{Manifest.permission.CAMERA},
//                MY_CAMERA_REQUEST_CODE);
//    }

            if (ActivityCompat.checkSelfPermission(getActivity(),Manifest.permission.RECORD_AUDIO)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO},
                        REQUEST_RECORD);
            }
        }catch (NoSuchMethodError e){
            Log.d("////",""+e);
        }}
}