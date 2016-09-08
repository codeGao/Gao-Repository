package com.xfxb.utils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;

import com.xfxb.bean.NewsBean;
import com.xfxb.dao.NewsDaoUtils;

public class NewsUtils {

	//访问接口的路径
	public static String newsPath_url = "http://192.168.30.1:8080/itheima74/servlet/GetNewsServlet";
	//封装新闻的假数据到list集合中
	public static ArrayList<NewsBean> getAllNewsForNetWork(Context context){
		ArrayList<NewsBean> arraylist = new ArrayList<NewsBean>();
		
		try {
			// 1、请求服务器获取新闻数据
			URL url = new URL(newsPath_url);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setConnectTimeout(2000);
			int code = connection.getResponseCode();
			if(code == 200){
				//获取请求的流数据，并转换成字符串
				InputStream inputStream = connection.getInputStream();
				String result = StreamUtils.streamToString(inputStream);
				//解析获取的新闻数据到list集合中
				JSONObject root_json = new JSONObject(result);
				//获取root_json中的newss作为jsonarray对象
				JSONArray jsonArray = root_json.getJSONArray("newss");
				//循环遍历jsonArray中的获取每一天数据
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject news_json = jsonArray.getJSONObject(i);
					
					NewsBean newsBean = new NewsBean();
					
					newsBean. id = news_json.getInt("id");
					newsBean. comment = news_json.getInt("comment");//评论数
					newsBean. type = news_json.getInt("type");//新闻的类型，0 ：头条 1 ：娱乐 2.体育
					newsBean. time = news_json.getString("time");
					newsBean. des = news_json.getString("des");
					newsBean. title = news_json.getString("title");
					newsBean. news_url = news_json.getString("news_url");
					newsBean. icon_url = news_json.getString("icon_url");
					
					arraylist.add(newsBean);
				}
				
				//清除数据库中的就数据，将新的数据缓存到数据库中
				new NewsDaoUtils(context).delete();
				//
				new NewsDaoUtils(context).saveNews(arraylist);
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return arraylist;
	}
	
	//从数据库中获取上次缓存的新闻数据做listView展示
	public static ArrayList<NewsBean> getAllNewsForDatabase(Context context){
		return new 	NewsDaoUtils(context).getNews();
	}
}
