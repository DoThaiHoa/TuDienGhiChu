package com.tudienghichu.chancahhoop.tudienghichu.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.tudienghichu.chancahhoop.tudienghichu.Custom.CustomGridViewAdapter;
import com.tudienghichu.chancahhoop.tudienghichu.MainActivity;
import com.tudienghichu.chancahhoop.tudienghichu.QuanLy.QuanLy;
import com.tudienghichu.chancahhoop.tudienghichu.QuanLy.QuanLyDatabase;
import com.tudienghichu.chancahhoop.tudienghichu.R;
import com.tudienghichu.chancahhoop.tudienghichu.SwipeDismissListViewTouchListener;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by hoadt on 15/10/2017.
 */

public class Fragment2  extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    ListView gridView;
    Button btnDelete,btnDeleteAll;
    EditText edSearch;
    public static ArrayList<QuanLy>qlArr;
    QuanLy quanLy;
    QuanLyDatabase quanLyDatabase;
    public static int i=-1;
    public static int vitri=-1;
    static CustomGridViewAdapter customGridAdapter;
    ViewPager mViewPager;


    public static Fragment2 newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        Fragment2 fragment = new Fragment2();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment2, container, false);
        quanLy=new QuanLy();
        qlArr=new ArrayList<QuanLy>();
        quanLyDatabase=new QuanLyDatabase(getActivity());
        mViewPager=(ViewPager)view.findViewById(R.id.viewpager) ;
//        btnDelete=(Button)view.findViewById(R.id.btnDelete);
        btnDeleteAll=(Button)view.findViewById(R.id.btnDeleteAll) ;
        qlArr=quanLyDatabase.getAll();
        edSearch=(EditText)view.findViewById(R.id.edSearch);

        btnDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                builder1.setMessage("Bạn có muốn xóa hết từ vựng");
                builder1.setCancelable(true);
                builder1.setPositiveButton(
                        "Có",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                               qlArr.clear();
                                customGridAdapter.notifyDataSetChanged();
                                quanLyDatabase.deleteAll();
                                CustomGridViewAdapter.listarr.clear();
                            }
                        });

                builder1.setNegativeButton(
                        "Không",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });
//        btnDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                for(int b=0;b<CustomGridViewAdapter.listarr.size();b++){
//                for(int a=0;a<qlArr.size();a++){
//                    for(int c=0;c<CustomGridViewAdapter.arrStr.size();c++){
//                        if(CustomGridViewAdapter.arrStr.get(c).toString().equals(qlArr.get(a).getMaTV().toString())&&CustomGridViewAdapter.arrStr.get(c).toString().equals(CustomGridViewAdapter.listarr.get(b).getMaTV().toString())){
//                            quanLyDatabase.xoaSP(qlArr.get(a).getMaTV());
//                            qlArr.remove(a);
//                            CustomGridViewAdapter.listarr.remove(b);
//                            QuanLyDatabase.mangGhiAm.remove(a);
//                            QuanLyDatabase.mangGhiAm2.remove(a);
//                            customGridAdapter.notifyDataSetChanged();
//                        }
//                    }
//                }
//                }
//
//                customGridAdapter.notifyDataSetChanged();
//
//            }
//        });



        quanLy.setTiengViet(quanLy.getTiengViet());
        quanLy.setTiengAnh(quanLy.getTiengAnh());

        gridView = (ListView)view.findViewById(R.id.gridView);
        customGridAdapter = new CustomGridViewAdapter(
                getActivity(),
                R.layout.custom_gridview_adapter,
                qlArr
        );

        gridView.setAdapter(customGridAdapter);
        SwipeDismissListViewTouchListener touchListener =
                new SwipeDismissListViewTouchListener(
                        gridView,
                        new SwipeDismissListViewTouchListener.DismissCallbacks() {
                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }

                            @Override
                            public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    quanLyDatabase.xoaSP(qlArr.get(position).getMaTV());
//                                    CustomGridViewAdapter.listarr.remove(b);
                                    for(int k=0;k<CustomGridViewAdapter.listarr.size();k++){
                                        if(CustomGridViewAdapter.listarr.get(k).getMaTV().equals(Fragment2.qlArr.get(position).getMaTV())){
                                            CustomGridViewAdapter.listarr.remove(k);
                                        }
                                    }
                                    QuanLyDatabase.mangGhiAm.remove(position);
                                    QuanLyDatabase.mangGhiAm2.remove(position);
                                    qlArr.remove(position);
                                    customGridAdapter.notifyDataSetChanged();

                                }


                            }
                        });
        gridView.setOnTouchListener(touchListener);
        edSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                String text = edSearch.getText().toString().toLowerCase(Locale.getDefault());
                customGridAdapter.filter(text);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                // TODO Auto-generated method stub
            }
        });


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                i=position;
                MainActivity.viewPager.setCurrentItem(0);
                vitri=position;
                quanLyDatabase.TimAmThanhTV("","",qlArr.get(position).getMaTV());


            }
        });
        return view;
    }


}