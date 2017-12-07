package com.tudienghichu.chancahhoop.tudienghichu.Custom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.tudienghichu.chancahhoop.tudienghichu.QuanLy.QuanLy;
import com.tudienghichu.chancahhoop.tudienghichu.R;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by hoadt on 19/10/2017.
 */

public class CustomGridViewAdapter extends ArrayAdapter<QuanLy> {

public static CheckBox checkBox;
public static String check=null;
public static int gg=0;
    ArrayList<QuanLy> data;
    public static ArrayList<String> arrStr=new ArrayList<String>();
    public  static  ArrayList<QuanLy> listarr;
    public CustomGridViewAdapter(Context context, int layoutResourceId,
                                 ArrayList<QuanLy> data) {
        super(context, layoutResourceId, data);

       this.data = data;
        listarr = new ArrayList<>();
        listarr.addAll(data);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v=convertView;

        if(v==null){
            LayoutInflater vi;
            vi=LayoutInflater.from(getContext());
            v=vi.inflate(R.layout.custom_gridview_adapter,null);
        }
        final QuanLy sp=getItem(position);
        if(sp!=null){

            TextView txtTuVungTiengViet=(TextView) v.findViewById(R.id.txtTuVungTiengViet);
            txtTuVungTiengViet.setText(sp.getTiengViet());
            TextView txtTuTiengAnh=(TextView) v.findViewById(R.id.txtTuTiengAnh);
            txtTuTiengAnh.setText(sp.getTiengAnh());
//            checkBox=(CheckBox)v.findViewById(R.id._checkbox);
//
//            if(checkBox.isChecked()){
//                checkBox.setChecked(false);
//            }

//            checkBox.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(checkBox.isChecked()){
//                        check=sp.getMaTV();
//                        arrStr.add(check);
//                        Toast.makeText(getContext(),"yes",Toast.LENGTH_SHORT).show();
//                    }else {
//                        check=sp.getMaTV();
//                        arrStr.remove(check);
//                        Toast.makeText(getContext(),"not",Toast.LENGTH_SHORT).show();
//                    }
//
//
//                }
//            });
//            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    if(isChecked){
//                        check=sp.getMaTV();
//                        arrStr.add(check);
//                        Toast.makeText(getContext(),"yes",Toast.LENGTH_SHORT).show();
//
//                    }else if(!isChecked){
//                        check=sp.getMaTV();
//                        arrStr.remove(check);
//                        Toast.makeText(getContext(),"not",Toast.LENGTH_SHORT).show();
//                    }
//
//                }
//            });



        }
        return v;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        data.clear();
        if (charText.length() == 0) {
            data.addAll(listarr);
        }
        else
        {
            for (QuanLy ql : listarr)
            {
                if (ql.getMaTV().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    data.add(ql);
                }
            }
        }
        notifyDataSetChanged();
    }
}