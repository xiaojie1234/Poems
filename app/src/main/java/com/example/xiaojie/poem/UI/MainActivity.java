package com.example.xiaojie.poem.UI;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.xiaojie.poem.R;
import com.example.xiaojie.poem.databinding.ActivityMainBinding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mainBinding;
    private List<String> list1 = new ArrayList<>();;
    private List<String> list2 = new ArrayList<>();;
    private List<String> ts = new ArrayList<>();;
    private List<String> sc = new ArrayList<>();;
    private ArrayAdapter<String> adapter1;
    private ArrayAdapter<String> adapter2;
    private String type;
    private String sontype;
    private String kw;
    private OkHttpClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

//        String re = "<p>&nbsp;<\\/p><p><b>\\u300a\\u8def\\u300b<\\/b><\\/p><p><font style=\\\"font-size:11pt;\\\">\\u8c01\\u8bf4\\u6dee\\u7538\\u6e5b\\u6e05\\u9704\\uff0c<\\/font><\\/p><p><font style=\\\"font-size:11pt;\\\">\\u4e1c\\u6cfb\\u6eaa\\u659c\\u4e0d\\u590d\\u5f97\\u3002<\\/font><\\/p><p><font style=\\\"font-size:11pt;\\\">\\u5c71\\u811a\\u671b\\u776b\\u8bdb\\u864f\\u8840\\uff0c<\\/font><\\/p><p><font style=\\\"font-size:11pt;\\\">\\u7389\\u6eaa\\u70df\\u5bfa\\u88ab\\u6c99\\u9601\\u3002<\\/font><\\/p><p>&nbsp;<\\/p><hr \\/>";
//        Pattern p = Pattern.compile(".*?<b>(.*?)<.*?<p>(.*?)<");
//        Matcher m = p.matcher(re);
//        while(m.find()){
//            System.out.println(m.group(1));
//        }
        intiData();
        initView();
        initListener();

    }

    private void initListener() {

        mainBinding.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                kw = mainBinding.edt.getText().toString();
                if(kw == "" || kw == null){
                    Toast.makeText(MainActivity.this, "请输入一个关键字~.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(kw.length() != 1){
                    Toast.makeText(MainActivity.this, "请输入一个关键字~.", Toast.LENGTH_SHORT).show();
                    return;
                }
                String url = "http://tssc.sinaapp.com/index.php/Index/ajax/?type="+type+"&sontype="+sontype+"&kw="+kw+"&C1=ON";
                Request request = new Request.Builder()
                        .url(url)
                        .build();
                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
//                        mainBinding.tv.setText();
//                        System.out.println(Html.fromHtml(response.body().string().toString()));
                        String str = response.body().string().toString();
                        Pattern p = Pattern.compile("<(.*)>");
                        Matcher m = p.matcher(str);
                        m.find();
                        String s = m.group();
                        s = decode(s);
                        System.out.println(s);
                        final String finalS = s;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mainBinding.tv.setText(Html.fromHtml(finalS));
                            }
                        });

                    }
                });
            }
        });


        mainBinding.spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    type = "ts";
                    adapter2.clear();
                    adapter2.addAll(ts);
                    adapter2.notifyDataSetChanged();
                }else{
                    type="sc";
                    sontype="Hxs";
                    adapter2.clear();
                    adapter2.addAll(sc);
                    adapter2.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mainBinding.spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:
                        if(type == "ts"){
                            sontype="5yls";
                        }else{
                            sontype="Hxs";
                        }
                        break;
                    case 1:
                        if(type == "ts"){
                            sontype="5yjj";
                        }else{
                            sontype="Zgt";
                        }
                        break;
                    case 2:
                        if(type == "ts"){
                            sontype="7yls";
                        }else{
                            sontype="Qpl";
                        }
                         break;
                    case 3:
                        if(type == "ts"){
                            sontype="7yjj";
                        }else{
                            sontype="Yja";
                        }
                        break;
                    case 4:
                        sontype="Psm";
                        break;
                    case 5:
                        sontype="Ljx";
                        break;
                    case 6:
                        sontype="Dlh";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void intiData() {
        client = new OkHttpClient();
        type="ts";
        sontype="5yls";

        list1.add("唐诗");
        list1.add("宋词");
        ts.add("五言律诗");
        ts.add("五言绝句");
        ts.add("七言律诗");
        ts.add("七言绝句");
        sc.add("浣溪沙");
        sc.add("鹧鸪天");
        sc.add("清平乐");
        sc.add("渔家傲");
        sc.add("菩萨蛮");
        sc.add("临江仙");
        sc.add("蝶恋花");
    }

    private void initView() {
        
        adapter1 = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, list1);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mainBinding.spinner1.setAdapter(adapter1);
        adapter2 = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,list2);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mainBinding.spinner2.setAdapter(adapter2);
    }

    public String decode(String string){

        if(string == null){
            return null;
        }
        StringBuffer sb = new StringBuffer();
        int len = string.length();
        for(int i=0;i<len;i++){
            if(string.charAt(i) == '\\'){
                if((i<len-5) && ((string.charAt(i+1) == 'u') || string.charAt(i+1) == 'U')){
                    sb.append((char) Integer.parseInt(string.substring(i+2,i+6),16));
                    i+=5;
                }else {
                    sb.append(string.charAt(i));
                }
            }else {
                sb.append(string.charAt(i));
            }
        }
        return sb.toString().trim();
    }


}
