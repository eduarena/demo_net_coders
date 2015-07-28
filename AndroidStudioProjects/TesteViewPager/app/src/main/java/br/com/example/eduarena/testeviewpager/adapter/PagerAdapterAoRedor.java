package br.com.example.eduarena.testeviewpager.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import br.com.example.eduarena.testeviewpager.R;

/**
 * Created by edu.arena on 20/07/2015.
 */
public class PagerAdapterAoRedor extends PagerAdapter {

    Context context;
    ArrayList<Integer> listEstab;

    public PagerAdapterAoRedor(){}

    public PagerAdapterAoRedor(Context context, ArrayList<Integer> listEstab){
        this.context = context;
        this.listEstab = listEstab;
    }

    @Override
    public int getCount() {
        return listEstab.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //return super.instantiateItem(container, position);

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.activity_ao_redor_detalhes, null);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogAoRedorDetail = new  AlertDialog.Builder(context);
                alertDialogAoRedorDetail.setTitle("VIEWPAGER");
                alertDialogAoRedorDetail.setMessage("Você clicou no Viewpager");
                alertDialogAoRedorDetail.show();
            }
        });

        ((ViewPager) container).addView(view,0);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object view) {
        container.removeView((View) view);
    }
}
