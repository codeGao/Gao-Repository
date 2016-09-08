package com.xfxb.view;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.ImageView;

public class MyImageView extends ImageView{

	public MyImageView(Context context){
		super(context);
	}
	public MyImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	public MyImageView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}
	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			Bitmap bitmap = (Bitmap) msg.obj;
			MyImageView.this.setImageBitmap(bitmap);
		};
	};
	public void setImageUrl(final String url_str){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					//获取url对应的图片资源 bitmap
					URL url = new URL(url_str);
					HttpURLConnection connection = (HttpURLConnection) url.openConnection();
					connection.setRequestMethod("GET");
					connection.setConnectTimeout(2000);
					int code = connection.getResponseCode();
					if(code == 200){
						InputStream inputStream = connection.getInputStream();
						Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
						Message msg = new Message();
					    msg.obj = bitmap; 
					    handler.sendMessage(msg);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
}
