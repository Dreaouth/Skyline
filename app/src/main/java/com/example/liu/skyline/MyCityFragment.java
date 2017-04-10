package com.example.liu.skyline;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liu on 2017/4/10.
 */

public class MyCityFragment extends Fragment {
    private Button button;
    private TextView cityItem;
    private List<MyCity> cityList=new ArrayList<>();
    private ListView listView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.my_city,container,false);
        MyCityAdapter adapter=new MyCityAdapter(getContext(),R.layout.my_city_item,cityList);
        listView=(ListView)view.findViewById(R.id.listView_MyCity);
        listView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                button.setVisibility(View.VISIBLE);
                return true;
            }
        });
    }

    public class MyCityAdapter extends ArrayAdapter<MyCity> {
        private int resourseId;

        public MyCityAdapter(Context context, int resource, List<MyCity> objects) {
            super(context, resource, objects);
            resourseId=resource;
        }

        @NonNull
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            MyCity myCity=getItem(position);
            View view=LayoutInflater.from(getContext()).inflate(resourseId,parent,false);
            button=(Button)view.findViewById(R.id.city_delete);
            cityItem=(TextView)view.findViewById(R.id.city_name);
            cityItem.setText(myCity.getCityName());
            button.setVisibility(View.INVISIBLE);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cityList.remove(position);
                }
            });
            return view;
        }
    }
}


