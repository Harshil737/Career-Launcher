package com.example.careerlauncher;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class TestPage extends Fragment {

	View fragView;
	private static String LOG_TAG = TestPage.class.getSimpleName();
	private QuestionClass question;
	private TextView tv_que;
	private RadioButton rb_op1, rb_op2, rb_op3, rb_op4;
	private onAnswerClickListener answerClickListener;
	private RadioGroup rbGroup;
	private Button btn_next, btn_previous;
	private int pos = 0;

	public TestPage(QuestionClass questionClass, int pos) {
		// TODO Auto-generated constructor stub
		question = questionClass;
		this.pos = pos;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		fragView = inflater.inflate(R.layout.fragment_test_page, container,
				false);
		initView();
		return fragView;
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		answerClickListener = (onAnswerClickListener) activity;
	}

	void initView() {
		tv_que = (TextView) fragView.findViewById(R.id.test_page_tv_que);
		rb_op1 = (RadioButton) fragView.findViewById(R.id.test_page_rb_op1);
		rb_op2 = (RadioButton) fragView.findViewById(R.id.test_page_rb_op2);
		rb_op3 = (RadioButton) fragView.findViewById(R.id.test_page_rb_op3);
		rb_op4 = (RadioButton) fragView.findViewById(R.id.test_page_rb_op4);
		rbGroup = (RadioGroup) fragView.findViewById(R.id.test_page_rbGroup);
		btn_next = (Button) fragView.findViewById(R.id.test_page_btn_next);
		btn_previous = (Button) fragView
				.findViewById(R.id.test_page_btn_previous);

		tv_que.setText(question.getQestion());
		rb_op1.setText(question.getOp1().toString());
		rb_op2.setText(question.getOp2().toString());
		rb_op3.setText(question.getOp3().toString());
		rb_op4.setText(question.getOp4().toString());

		if (pos == 14) {
			btn_next.setText("FINISH");
		} else {
			btn_next.setText("NEXT");
		}

		btn_previous.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				answerClickListener.onPreviousClicked();
			}
		});

		btn_next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (rbGroup.getCheckedRadioButtonId() == -1) {
					answerClickListener.onAnswerClicked("null", pos);
				} else {
					RadioButton rb = (RadioButton) fragView
							.findViewById(rbGroup.getCheckedRadioButtonId());
					answerClickListener.onAnswerClicked(rb.getText() + "", pos);
				}

				if (pos < 14) {
					answerClickListener.onNextClicked();
				} else {
					answerClickListener.onFinishClicked();
				}

			}
		});
	}

	public interface onAnswerClickListener {
		public void onAnswerClicked(String data, int pos);

		public void onNextClicked();

		public void onPreviousClicked();

		public void onFinishClicked();
	}
}
