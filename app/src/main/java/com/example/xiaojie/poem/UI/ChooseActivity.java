package com.example.xiaojie.poem.UI;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.xiaojie.poem.R;
import com.example.xiaojie.poem.databinding.ActivityChooseBinding;
import com.example.xiaojie.poem.Utils.Util;

/**
 * Created by xiaojie on 2017/7/11.
 */

public class ChooseActivity extends AppCompatActivity{

    private ActivityChooseBinding chooseBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chooseBinding = DataBindingUtil.setContentView(this, R.layout.activity_choose);
        chooseBinding.setPresenter(new presenter());
    }

    public class presenter{

        public void sinaOnclick(View v){
            Util.skipActivity(ChooseActivity.this, MainActivity.class);
        }
        public void ibmOnclick(View v){
            Util.skipActivity(ChooseActivity.this, IBMActivity.class);
        }

    }
}
