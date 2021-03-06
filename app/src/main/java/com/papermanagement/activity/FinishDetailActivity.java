package com.papermanagement.activity;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;

import com.papermanagement.R;
import com.papermanagement.adapter.GridViewAdapter;
import com.papermanagement.bean.FinishTimeBean;
import com.papermanagement.bean.OrderDataBen;

public class FinishDetailActivity extends BaseActivity {

    private GridView gridViewFinishDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_detail);
        FinishTimeBean finishTimeBean = (FinishTimeBean) getIntent().getSerializableExtra("data");
        TextView tvFinishTitle = (TextView) findViewById(R.id.tv_finish_detail);
        tvFinishTitle.setText("完工资料详情(" + finishTimeBean.getKhjc() + ")");
        gridViewFinishDetail = (GridView) findViewById(R.id.grid_view_finish_detail);
        GridViewAdapter adapter = new GridViewAdapter(this, R.layout.grid_view_cell);
        gridViewFinishDetail.setAdapter(adapter);
        adapter.setData(finishTimeBean.toList());
    }
}
