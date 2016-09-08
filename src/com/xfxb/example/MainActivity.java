package com.xfxb.example;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.newschecknet.R;
import com.xfxb.adapter.NewsAdapter;
import com.xfxb.bean.NewsBean;
import com.xfxb.utils.NewsUtils;

public class MainActivity extends Activity implements OnItemClickListener {

	private Context mContext;
	private ListView lv_news;
	
	private Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			ArrayList<NewsBean> allNews = (ArrayList<NewsBean>) msg.obj;
			if(allNews != null && allNews.size() > 0){
				//创建一个adapter设置给listView
				NewsAdapter newsAdapter = new NewsAdapter(mContext, allNews);
				lv_news.setAdapter(newsAdapter);
			}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mContext = this;
		lv_news = (ListView) findViewById(R.id.lv_news);
		//1、先去数据库中获取缓存的新闻数据展示到listView
		ArrayList<NewsBean> allnews_database = NewsUtils.getAllNewsForDatabase(mContext);
		if(allnews_database != null && allnews_database.size() >0){
			//创建一个adapter设置给listView
			NewsAdapter newsAdapter = new NewsAdapter(mContext, allnews_database);
			lv_news.setAdapter(newsAdapter);
		}
		//通过网络获取服务器上的新闻数据用list封装，获取网络数据需要在子线程中做
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				//请求网络数据
				ArrayList<NewsBean> allNews = NewsUtils.getAllNewsForNetWork(mContext);
				//通过handler将msg发送到主线程更新ui
				Message msg = new Message();
				msg.obj = allNews;
				handler.sendMessage(msg);
			}
		}).start();
		
		//设置listview的点击事件
		lv_news.setOnItemClickListener(this);
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		NewsBean bean = (NewsBean) arg0.getItemAtPosition(arg2);
		String url = bean.news_url;
		
		//跳转浏览器
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_VIEW);
		intent.setData(Uri.parse(url));
		startActivity(intent);
	}

}
