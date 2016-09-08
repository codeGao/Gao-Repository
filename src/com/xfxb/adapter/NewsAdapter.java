package com.xfxb.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.newschecknet.R;
import com.xfxb.bean.NewsBean;
import com.xfxb.view.MyImageView;

public class NewsAdapter extends BaseAdapter {

	private ArrayList<NewsBean> list;
	private Context mContext;

	// 通过构造方法接收要显示的新闻数据集合
	public NewsAdapter(Context context, ArrayList<NewsBean> list) {
		this.list = list;
		this.mContext = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		if (convertView != null) {
			view = convertView;
		} else {
			// 通过context获取系统服务得到一个LayoutInfalter,通过LayoutInflater将一个布局转换成view对象
			view = View.inflate(mContext, R.layout.item_news_layout, null);
		}
		// 获取view上的子控件对象
		MyImageView item_img_icon = (MyImageView) view.findViewById(R.id.item_img_icon);
		TextView item_tv_des = (TextView) view.findViewById(R.id.item_tv_des);
		TextView item_tv_title = (TextView) view.findViewById(R.id.item_tv_title);
		TextView item_tv_comment = (TextView) view.findViewById(R.id.item_tv_comment);
		TextView item_tv_type = (TextView) view.findViewById(R.id.item_tv_type);
		// 获取postion位置条目对应的list集合中的新闻数据，Bean对象
		NewsBean newsBean = list.get(position);
		// 将数据设置给这些子控件做显示
		item_img_icon.setImageUrl(newsBean.icon_url);
		item_tv_title.setText(newsBean.title);
		item_tv_des.setText(newsBean.des);
		item_tv_comment.setText("评论：" + newsBean.comment);
		// 0 ：头条 1 ：娱乐 2.体育
		switch (newsBean.type) {
		case 0:
			item_tv_type.setText("头条");
			break;
		case 1:
			item_tv_type.setText("娱乐 ");
			break;
		case 2:
			item_tv_type.setText("体育");
			break;
		default:
			break;
		}
		return view;
	}

}
