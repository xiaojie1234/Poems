package com.example.xiaojie.poem.UI;

import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xiaojie.poem.Model.PoemBean;
import com.example.xiaojie.poem.R;
import com.example.xiaojie.poem.databinding.ActivityIbmBinding;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by xiaojie on 2017/7/11.
 */

public class IBMActivity extends AppCompatActivity{

    private ActivityIbmBinding binding;
    private OkHttpClient mOkhttpClient;
    private Request mRequest;
    private List<String> poem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_ibm);
        binding.setPresenter(new presenter());

        init();

    }

    private void init() {
        mOkhttpClient = new OkHttpClient();
    }

    public void showTheDialog(final int type){
        final EditText edt = new EditText(this);
        edt.setSingleLine();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(edt);
        builder.setNegativeButton("取消",null);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getDataByOkHttp(UUID.randomUUID().toString(),type,edt.getText().toString());
            }
        });
        builder.setCancelable(false);
        builder.setTitle("请输入关键字").show();
    }

    public void showResult(String result, String title){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialog = inflater.inflate(R.layout.dialog_show, (ViewGroup) findViewById(R.id.dialog));
        TextView tv_title = (TextView) dialog.findViewById(R.id.result_title);
        TextView tv_content = (TextView) dialog.findViewById(R.id.result_content);
        tv_title.setText(title);
        tv_content.setText(result);

        builder.setView(dialog);
        builder.show();
    }

    private void getDataByOkHttp(String uuid, int type, final String key) {
        Toast.makeText(this, "正在生成，请稍等", Toast.LENGTH_SHORT).show();
        String time  = String.valueOf(System.currentTimeMillis());
        String url = "https://crl.ptopenlab.com:8800/poem/getpoems?seed="+key+"&type="+type+"&uuid="+uuid+"&poemIdx=0&_="+time;;
        mRequest = new Request.Builder()
                .url(url)
                .build();
        mOkhttpClient.newCall(mRequest).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                Gson gson = new Gson();
                PoemBean b = gson.fromJson(json,PoemBean.class);
                int ran = new Random().nextInt(b.getPoems().size());
                System.out.println(ran);
                poem = b.getPoems().get(ran);
                StringBuffer sb = new StringBuffer();
                int i=1;
                for (String s: poem) {
                    if(i%2 == 1){
                        sb.append("  "+s+"，\n");
                    }else{
                        sb.append("  "+s+"。\n");
                    }
                    i++;
                }
                final String result = sb.toString();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showResult(result,key);
                    }
                });

                System.out.println(result);
            }
        });
    }

    public class presenter{

        public void wyjjOnclick(View v){
            int type=1;
            showTheDialog(type);
        }
        public void wyctOnclick(View v){
            int type=3;
            showTheDialog(type);
        }
        public void qyjjOnclick(View v){
            int type=2;
            showTheDialog(type);
        }
        public void qyctOnclick(View v){
            int type=4;
            showTheDialog(type);
        }
    }
}
