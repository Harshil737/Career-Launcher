package com.example.careerlauncher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Menu;
import android.widget.Toast;

public class TestMainPage extends FragmentActivity implements
		TestPage.onAnswerClickListener {

	private PageSliderAdapter pageSliderAdapter;
	private String finalURL = "";
	private ViewPager viewPager;
	private ArrayList<QuestionClass> queList;
	private static String LOG_TAG = TestMainPage.class.getSimpleName();
	private int answers[] = new int[15];
	private int correct, unattempted, wrong;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_main_page);

		Intent i = getIntent();
		int ID = i.getIntExtra("ID", 1);

		viewPager = (ViewPager) findViewById(R.id.test_viewPager);
		viewPager.setOffscreenPageLimit(15);
		finalURL = getResources().getString(R.string.SERVER_URL)
				+ "Question.php?action=getQuestionByQuize&qid=" + ID + "";
		new QuestionTask().execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.test_main_page, menu);
		return true;
	}

	@Override
	public void onAnswerClicked(String data, int pos) {
		// TODO Auto-generated method stub

		if (data.equals(queList.get(pos).getIs_correct())) {
			answers[pos] = 1;
			Toast.makeText(
					getApplicationContext(),
					"Correct : " + score(pos) + "\n" + "Wrong : " + wrong
							+ "\n" + "Unattempted : " + unattempted,
					Toast.LENGTH_SHORT).show();
		} else {
			answers[pos] = 0;
			Toast.makeText(
					getApplicationContext(),
					"Correct : " + score(pos) + "\n" + "Wrong : " + wrong
							+ "\n" + "Unattempted : " + unattempted,
					Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onNextClicked() {
		// TODO Auto-generated method stub
		int pos = viewPager.getCurrentItem();
		viewPager.setCurrentItem(pos + 1);
	}

	@Override
	public void onPreviousClicked() {
		// TODO Auto-generated method stub
		int pos = viewPager.getCurrentItem();
		viewPager.setCurrentItem(pos - 1);
	}

	@Override
	public void onFinishClicked() {
		// TODO Auto-generated method stub
		Intent resultIntent = new Intent(getApplicationContext(),
				TestResultPage.class);
		resultIntent.putExtra("Correct", score(14));
		resultIntent.putExtra("Wrong", score(14));
		resultIntent.putExtra("Unattempted", score(14));
		startActivity(resultIntent);
	}

	int score(int pos) {
		correct = 0;
		wrong = 0;
		unattempted = 0;
		for (int i = 0; i <= pos; i++) {
			if (answers[i] == 1) {
				correct += 1;
			} else if (answers[i] == 0) {
				wrong += 1;
			} else if (answers[i] == 2) {
				unattempted = pos - (correct + wrong);
			}
		}
		return correct;
	}

	void updateUI(ArrayList<QuestionClass> que) {
		queList = que;
		pageSliderAdapter = new PageSliderAdapter(getSupportFragmentManager(),
				queList);
		viewPager.setAdapter(pageSliderAdapter);
	}

	private class QuestionTask extends
			AsyncTask<URL, Object, ArrayList<QuestionClass>> {

		@Override
		protected ArrayList<QuestionClass> doInBackground(URL... params) {
			// TODO Auto-generated method stub
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(finalURL);
			StringBuffer stringBuffer = new StringBuffer();

			try {
				HttpResponse httpResponse = httpClient.execute(httpGet);
				BufferedReader bufferedReader = new BufferedReader(
						new InputStreamReader(httpResponse.getEntity()
								.getContent()));

				String line = "";
				while ((line = bufferedReader.readLine()) != null) {
					stringBuffer.append(line);
				}
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			ArrayList<QuestionClass> que;
			que = extractFeatureFromJson(stringBuffer.toString());
			return que;
		}

		private ArrayList<QuestionClass> extractFeatureFromJson(String queJSON) {
			if (TextUtils.isEmpty(queJSON))
				return null;
			try {
				JSONObject baseJsonResponse = new JSONObject(queJSON);
				ArrayList<QuestionClass> list = new ArrayList<QuestionClass>();
				int success = baseJsonResponse.getInt("Status");
				if (success == 1) {
					JSONArray jsonArray = baseJsonResponse
							.getJSONArray("Records");
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jsonObject = jsonArray.getJSONObject(i);
						int que_id = jsonObject.getInt("que_id");
						int fk_quiz_id = jsonObject.getInt("fk_quiz_id");
						String question = jsonObject.getString("question");
						String op1 = jsonObject.getString("op1");
						String op2 = jsonObject.getString("op2");
						String op3 = jsonObject.getString("op3");
						String op4 = jsonObject.getString("op4");
						String is_correct = jsonObject.getString("is_correct");

						QuestionClass questionClass = new QuestionClass(que_id,
								fk_quiz_id, question, op1, op2, op3, op4,
								is_correct);
						list.add(questionClass);
					}
					return list;
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(ArrayList<QuestionClass> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result != null) {
				updateUI(result);
			} else {
				Toast.makeText(getApplicationContext(), "Failure",
						Toast.LENGTH_SHORT).show();
			}
		}
	}
}
