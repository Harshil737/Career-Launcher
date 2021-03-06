package com.example.careerlauncher;

import java.util.ArrayList;

import com.squareup.picasso.Picasso;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class QuizAdapter extends ArrayAdapter<QuizClass> {

	private Context context;

	public QuizAdapter(Context context, ArrayList<QuizClass> quizList) {
		super(context, 0, quizList);
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		QuizClass quiz = getItem(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(
					R.layout.item_quiz, parent, false);
		}

		TextView tv_name = (TextView) convertView
				.findViewById(R.id.item_quiz_tv_name);
		ImageView iv_poster = (ImageView) convertView
				.findViewById(R.id.item_quiz_iv_poster);

		tv_name.setText(quiz.getName());
		String path = context.getResources().getString(R.string.SERVER_URL)
				+ quiz.getImg();
		Picasso.with(context).load(path).error(R.drawable.bg).into(iv_poster);
		iv_poster.getLayoutParams().width = MeasureUtil.getSizeOfItem(context);
		iv_poster.getLayoutParams().height = MeasureUtil.getSizeOfItem(context);
		return convertView;
	}
}