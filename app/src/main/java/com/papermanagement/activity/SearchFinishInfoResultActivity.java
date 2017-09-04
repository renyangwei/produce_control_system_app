package com.papermanagement.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;

import com.papermanagement.R;
import com.papermanagement.adapter.FinishInfoAdapter;
import com.papermanagement.bean.FinishTimeBean;
import com.papermanagement.bean.OrderBean;

import java.util.ArrayList;

public class SearchFinishInfoResultActivity extends BaseActivity {

    private RecyclerView recyclerView;

    private FinishInfoAdapter finishInfoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_finish_info_result);
        ArrayList<OrderBean> list = (ArrayList<OrderBean>) getIntent().getSerializableExtra("data");
        OrderBean[] orderBeans = list.toArray(new OrderBean[0]);
        recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        finishInfoAdapter = new FinishInfoAdapter(this);
        finishInfoAdapter.setOntItemClickListner(itemClickListner);
        finishInfoAdapter.setItem(orderBeans);
        recyclerView.setAdapter(finishInfoAdapter);
    }

    FinishInfoAdapter.OnItemClickListner itemClickListner = new FinishInfoAdapter.OnItemClickListner() {
        @Override
        public void setOnItemClickListner(int position, FinishTimeBean finishTimeBean) {
            Intent intent = new Intent(getBaseContext(), FinishDetailActivity.class);
            finishTimeBean.setStartTime(finishInfoAdapter.getItem(position).getStartTime());
            finishTimeBean.setFinishTime(finishInfoAdapter.getItem(position).getFinishTime());
            intent.putExtra("data", finishTimeBean);
            startActivity(intent);
        }
    };



}
