package com.tudienghichu.chancahhoop.tudienghichu.QuanLy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.util.Log;

import com.tudienghichu.chancahhoop.tudienghichu.Fragment.Fragment1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by hoadt on 15/10/2017.
 */

public class QuanLyDatabase extends SQLiteOpenHelper {
//    private byte[] hinhAnh;
//    private byte[] amThanhTiengViet;
//    private byte[] amThanhTiengAnh;
//    private String tiengAnh;
//    private String tiengViet;
//    private String ghiChu;

    private static  String TAG = "SQLite";
    private static int DATABASE_VERSION = 1;
    private static  String DATABASE_NAME = "quanly";
    private static  String TABLE_NAME = "quanlytuvung";

    private static String ID="id";
    private static String HINH_ANH="hinh_anh";
    private static String AT_TVIET="at_tviet";
    private static String AT_TANH="at_tanh";
    private static String TIENG_VIET="tieng_viet";
    private static String TIENG_ANH="tieng_anh";
    private static String GHI_CHU="ghi_chu";
    public static ArrayList<byte[]>mangGhiAm;
    public static ArrayList<byte[]>mangGhiAm2;
    Context mContext;
    public static int id;
    SQLiteDatabase db;
    public QuanLyDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mangGhiAm=new ArrayList<byte[]>();
        mangGhiAm2=new ArrayList<byte[]>();
       mContext=context;
        Log.d("//////","created");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String script = "CREATE TABLE " + TABLE_NAME + "("
                + ID + " TEXT,"
                + HINH_ANH + " BLOB,"
                + AT_TVIET + " BLOB,"
                + AT_TANH + " BLOB,"
                + TIENG_VIET + " TEXT,"
                + TIENG_ANH + " TEXT,"
                + GHI_CHU + " TEXT "+")";
        db.execSQL(script);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public void themSanPham(QuanLy quanLy) {
        Log.i(TAG, "MyDatabaseHelper.addNote ... ");

      db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ID,quanLy.getMaTV());
        values.put(HINH_ANH,quanLy.getHinhAnh());
        values.put(AT_TVIET,quanLy.getAmThanhTiengViet());
        values.put(AT_TANH, quanLy.getAmThanhTiengAnh());
        values.put(TIENG_VIET, quanLy.getTiengViet());
        values.put(TIENG_ANH, quanLy.getTiengAnh());
        values.put(GHI_CHU, quanLy.getGhiChu());
        db.insert(TABLE_NAME, null, values);
        db.close();

    }
    public  ArrayList<QuanLy>getAll() {
        ArrayList<QuanLy>qllist=new ArrayList<>();
        String query="SELECT * FROM "+ TABLE_NAME;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(query,null);
        cursor.moveToFirst();
        while (cursor.isAfterLast()==false){
            QuanLy quanLy=new QuanLy(
                    cursor.getString(0),
                    cursor.getBlob(1),
                    cursor.getBlob(2),
                    cursor.getBlob(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6)
                    );
            qllist.add(quanLy);
            mangGhiAm.add(cursor.getBlob(2));
            mangGhiAm2.add(cursor.getBlob(3));
            cursor.moveToNext();

        }
        return qllist;


    }
    public boolean sosanhTV(String maTV){
        String countQuery = "SELECT "+ID+"  FROM " + TABLE_NAME+" where "+ID+" = "+"'"+maTV+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        if(cursor.getCount()>0){
            return true;
        }else {
            return false;
        }
    }
//    public boolean sosanhTA(String tiengAnh){
//        String countQuery = "SELECT "+TIENG_ANH+"  FROM " + TABLE_NAME+" where "+TIENG_ANH+" = "+"'"+tiengAnh+"'";
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(countQuery, null);
//        if(cursor.getCount()>0){
//
//            return true;
//        }else {
//            return false;
//        }
//    }
    public void xoaSP(String maSP){

        db=this.getWritableDatabase();
      int i=  db.delete(TABLE_NAME,ID+" =? ",new String[]{String.valueOf(maSP)});
        db.close();
    }
    public void suaSP(String id,QuanLy quanLy){
        db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
//        Toast.makeText(mContext,quanLy.getMaTV(),Toast.LENGTH_SHORT).show();
                    values.put(ID,quanLy.getMaTV());
                    values.put(HINH_ANH,quanLy.getHinhAnh());
                    values.put(AT_TVIET,quanLy.getAmThanhTiengViet());
                    values.put(AT_TANH, quanLy.getAmThanhTiengAnh());
                    values.put(TIENG_VIET, quanLy.getTiengViet());
                    values.put(TIENG_ANH, quanLy.getTiengAnh());
                    values.put(GHI_CHU, quanLy.getGhiChu());
                    db.update(TABLE_NAME,values,ID+" =? ",new String[]{String.valueOf(id)});
        db.close();
    }
    public void deleteAll(){
        db=this.getWritableDatabase(); // helper is object extends SQLiteOpenHelper
        db.delete(QuanLyDatabase.TABLE_NAME, null, null);

    }
    public void TimAmThanhTV(String tiengViet,String tiengAnh,String maTV){
        db=this.getReadableDatabase();
        String query="select * from "+TABLE_NAME;
        Cursor cursor=db.rawQuery(query,null);
        String tiengViet2,tiengAnh2,maTV2;
        if(cursor.moveToFirst()){
            do{
                maTV2=cursor.getString(0);
                tiengViet2=cursor.getString(4);
                tiengAnh2=cursor.getString(5);
                if(maTV2.equals(maTV)){

                    Fragment1.imageView.setImageBitmap(getImage(cursor.getBlob(1)));
                    Fragment1.edTiengViet.setText(cursor.getString(4));
                    Fragment1.edTiengAnh.setText(cursor.getString(5));
                    Fragment1.edGhiChu.setText(cursor.getString(6));
                }
                if(tiengViet2.equals(tiengViet)){
                    if(Fragment1.flag2==1){
                        playMp3FromByte(cursor.getBlob(2));
                        Fragment1.flag2=0;
                    }
                        Fragment1.imageView.setImageBitmap(getImage(cursor.getBlob(1)));
                        Fragment1.edTiengViet.setText(cursor.getString(4));
                        Fragment1.edTiengAnh.setText(cursor.getString(5));
                        Fragment1.edGhiChu.setText(cursor.getString(6));
                }
                if(tiengAnh2.equals(tiengAnh)){
                    if(Fragment1.flag2==2){
                        playMp3FromByte(cursor.getBlob(3));
                        Fragment1.flag2=0;
                    }
                        Fragment1.imageView.setImageBitmap(getImage(cursor.getBlob(1)));
                        Fragment1.edTiengViet.setText(cursor.getString(4));
                        Fragment1.edTiengAnh.setText(cursor.getString(5));
                        Fragment1.edGhiChu.setText(cursor.getString(6));
                }
            }while (cursor.moveToNext());
        }
        db.close();

    }
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
    private void playMp3FromByte(byte[] mp3SoundByteArray) {
        try {

            File tempMp3 = File.createTempFile("kurchina", "mp3", mContext.getCacheDir());
            tempMp3.deleteOnExit();
            FileOutputStream fos = new FileOutputStream(tempMp3);
            fos.write(mp3SoundByteArray);
            fos.close();

            MediaPlayer mediaPlayer = new MediaPlayer();

            FileInputStream fis = new FileInputStream(tempMp3);
            mediaPlayer.setDataSource(fis.getFD());

            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException ex) {
            String s = ex.toString();
            ex.printStackTrace();
        }
    }
}
