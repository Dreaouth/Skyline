package com.example.liu.skyline;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by liu on 2017/4/10.
 */

public class MyCityFragment extends Fragment {
    public static List<MyCity> cityList=new ArrayList<>();
    private ListView listView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.my_city,container,false);
        MyCityAdapter adapter=new MyCityAdapter(this.getActivity(),cityList);
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
                Button button=(Button)view.findViewById(R.id.city_delete);
                if (button.getVisibility()==View.VISIBLE){
                    button.setVisibility(View.INVISIBLE);
                }
                WeatherActivity activity=(WeatherActivity)getActivity();
                activity.drawerLayout.closeDrawers();
                activity.swipeRefresh.setRefreshing(true);
                activity.requestWeather(cityList.get(position).getCountyId());
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Button button=(Button)view.findViewById(R.id.city_delete);
                if (button.getVisibility()==View.INVISIBLE){
                    button.setVisibility(View.VISIBLE);
                }
                else {
                    button.setVisibility(View.INVISIBLE);
                }
                return true;
                //关于返回值，若返回False，则是当长按时，既调用onItemLongClick，同时调用onItemLongClick后
                //还会调用onItemClick，就是说会同时调用onItemLongClick，和onItemClick，
                //若返回true，则只调用onItemLongClick
            }
        });
    }

    public class MyCityAdapter extends BaseAdapter {
        private Context context;
        private List<MyCity> myCities=null;

        public MyCityAdapter(Context context,List<MyCity> objects) {
            this.context=context;
            this.myCities=objects;
        }

        @NonNull
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            //自动生成的方法存根
            ViewHolder holder=null;
            MyCity myCity=myCities.get(position);
            if (convertView==null){
                holder=new ViewHolder();
                convertView=LayoutInflater.from(context).inflate(R.layout.my_city_item,null);
                holder.cityName1=(TextView)convertView.findViewById(R.id.city_name);
                holder.delete=(Button)convertView.findViewById(R.id.city_delete);
                convertView.setTag(holder);
            }
            else {
                holder=(ViewHolder)convertView.getTag();
            }
            holder.cityName1.setText(myCity.getCityName());
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myCities.remove(position);
                    notifyDataSetChanged();
                }
            });
            holder.delete.setVisibility(View.INVISIBLE);
            return convertView;
        }

        @Override
        public int getCount() {
            return myCities.size();
        }

        @Override
        public Object getItem(int position) {
            return myCities.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

    }
    final static class ViewHolder{
        TextView cityName1;
        Button delete;
    }
}


