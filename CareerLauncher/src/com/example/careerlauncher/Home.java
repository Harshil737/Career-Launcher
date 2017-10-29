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

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

public class Home extends Fragment implements OnItemClickListener {

	private View fragView;
	private GridView gridView;
	private ArrayList<QuizClass> quizList;
	private QuizAdapter quizAdapter;
	private String finalURL = "";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		fragView = inflater.inflate(R.layout.fragment_home, container, false);
		initView();
		return fragView;
	}

	void initView() {
		gridView = (GridView) fragView.findViewById(R.id.home_gridView);

		finalURL = getResources().getString(R.string.SERVER_URL)
				+ "Question.php?action=getAllQuize";
		new QuizTask().execute();
	}

	private void updateUI(ArrayList<QuizClass> quiz) {
		Toast.makeText(getActivity(), "Success", Toast.LENGTH_LONG).show();
		quizList = new ArrayList<QuizClass>();
		quizList = quiz;
		quizAdapter = new QuizAdapter(getActivity(), quiz);
		gridView.setAdapter(quizAdapter);

		gridView.setOnItemClickListener(this);
	}

	public class QuizTask extends AsyncTask<URL, Object, ArrayList<QuizClass>> {

		@Override
		protected ArrayList<QuizClass> doInBackground(URL... params) {
			// TODO Auto-generated method stub
			HttpClient client = new DefaultHttpClient();
			HttpGet get = new HttpGet(finalURL);
			StringBuffer buffer = new StringBuffer();

			HttpResponse response;
			try {
				response = client.execute(get);
				BufferedReader bufferedReader = new BufferedReader(
						new InputStreamReader(response.getEntity().getContent()));

				String line = "";
				while ((line = bufferedReader.readLine()) != null) {
					buffer.append(line);
				}
			} catch (ClientProtocolException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			quizList = new ArrayList<QuizClass>();
			quizList = extractFeatureFromJson(buffer.toString());
			return quizList;
		}

		private ArrayList<QuizClass> extractFeatureFromJson(String userJSON) {
			if (TextUtils.isEmpty(userJSON))
				return null;

			try {
				JSONObject baseJsonResponse = new JSONObject(userJSON);
				ArrayList<QuizClass> list = new ArrayList<QuizClass>();
				int success = baseJsonResponse.getInt("Status");
				if (success == 1) {
					JSONArray jsonArray = baseJsonResponse
							.getJSONArray("Records");
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jsonObject = jsonArray.getJSONObject(i);
						int id = jsonObject.getInt("quiz_id");
						String name = jsonObject.getString("quiz_name");
						String img = jsonObject.getString("img");

						QuizClass quizClass = new QuizClass(id, name, img);
						list.add(quizClass);
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
		protected void onPostExecute(ArrayList<QuizClass> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result != null) {
				updateUI(result);
			} else {
				Toast.makeText(getActivity(), "Failure", Toast.LENGTH_LONG)
						.show();
			}
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Toast.makeText(getActivity(), quizList.get(arg2).getId() + "",
				Toast.LENGTH_LONG).show();
	}
}
