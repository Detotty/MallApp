package com.mallapp.socialsharing;



import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.mallapp.View.R;

public class Twitter_Integration {
	
	/*private static String TWITTER_CONSUMER_KEY 		= "P3YMqVUXjl8HgyNnl9IlJmCT6";
    private static String TWITTER_CONSUMER_SECRET 	= "ltshwIbfrsNIvnn461nAMCzxEVhXsDNcprvtr6vsKXytmWJgQS";
    // Shared Preferences
 	private static SharedPreferences mSharedPreferences;
 	
    Context context;
    Activity activity;
    Twitter twitter;
    RequestToken requestToken = null;
    AccessToken accessToken;
    String oauth_url,oauth_verifier,profile_url;
    Dialog auth_dialog;
    WebView web;
    //SharedPreferences pref;
    ProgressDialog progress;
    
    
    
	public Twitter_Integration(Context context, Activity activity) {
		super();
		this.context= context;
		this.activity= activity;
		mSharedPreferences = this.activity.getPreferences(0);
		twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer(TWITTER_CONSUMER_KEY, TWITTER_CONSUMER_SECRET);
	}
	
	public void post_twitter() {
		new TokenGet().execute();
	}
	
	@SuppressLint("SetJavaScriptEnabled") 
	private class TokenGet extends AsyncTask<String, String, String> {
		@Override
		protected String doInBackground(String... args) {
			try {
				requestToken = twitter.getOAuthRequestToken();
				oauth_url = requestToken.getAuthorizationURL();
			} catch (TwitterException e) {
				e.printStackTrace();
			}
			return oauth_url;
		}
		
		@Override
		protected void onPostExecute(String oauth_url) {
			if(oauth_url != null){
				
				Log.e("URL", oauth_url);
				auth_dialog = new Dialog(activity);
				auth_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); 
				auth_dialog.setContentView(R.layout.auth_dialog);
				
				web = (WebView)auth_dialog.findViewById(R.id.webv);
				web.getSettings().setJavaScriptEnabled(true);
				web.loadUrl(oauth_url);
				
				web.setWebViewClient(new WebViewClient() {
					boolean authComplete = false;
					@Override
					public void onPageStarted(WebView view, String url, Bitmap favicon){
						super.onPageStarted(view, url, favicon);
					}	
					
					@Override
					public void onPageFinished(WebView view, String url) {
						super.onPageFinished(view, url);
						if (url.contains("oauth_verifier") && authComplete == false){
							authComplete = true;
							Log.e("Url",url);
							Uri uri = Uri.parse(url);
							oauth_verifier = uri.getQueryParameter("oauth_verifier");
							auth_dialog.dismiss();
							new AccessTokenGet().execute();
							
						}else if(url.contains("denied")){
							auth_dialog.dismiss();
							Toast.makeText(context, "Sorry !, Permission Denied", Toast.LENGTH_SHORT).show();
						}
					}
				});
				auth_dialog.show();
				auth_dialog.setCancelable(true);
			}else{
	        	Toast.makeText(context, "Sorry !, Network Error or Invalid Credentials", Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	private class AccessTokenGet extends AsyncTask<String, String, Boolean> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progress = new ProgressDialog(activity);
			progress.setMessage("Fetching Data ...");
			progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progress.setIndeterminate(true);
			progress.show();
            
		}
		@Override
		protected Boolean doInBackground(String... args) {
			try {
				accessToken = twitter.getOAuthAccessToken(requestToken, oauth_verifier);
				User user = twitter.showUser(accessToken.getUserId());
				profile_url = user.getOriginalProfileImageURL();
				
				SharedPreferences.Editor edit = mSharedPreferences.edit();
				edit.putString("ACCESS_TOKEN", accessToken.getToken());
				edit.putString("ACCESS_TOKEN_SECRET", accessToken.getTokenSecret());
				edit.putString("NAME", user.getName());
				edit.putString("IMAGE_URL", user.getOriginalProfileImageURL());
				edit.commit();  
			
			} catch (TwitterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		}
		@Override
		protected void onPostExecute(Boolean response) {
			if(response){
				progress.hide();
				open_postDailog();
			}
	    }
	}
	
	Dialog tDialog;
	EditText tweet_text;
	String tweetText;
	
	public void open_postDailog() {

		// TODO Auto-generated method stub
		tDialog = new Dialog(activity);
		tDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		tDialog.setContentView(R.layout.tweet_dialog);
		tweet_text = (EditText)tDialog.findViewById(R.id.tweet_text);
		
		ImageButton post_tweet = (ImageButton)tDialog.findViewById(R.id.post_tweet);
		
		post_tweet.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new PostTweet().execute();	
			}
		});
		
		tDialog.show();
	}
	
	
	private class PostTweet extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progress = new ProgressDialog(activity);
			progress.setMessage("Posting tweet ...");
			progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progress.setIndeterminate(true);
			tweetText = tweet_text.getText().toString();
			progress.show();
			
		}
		protected String doInBackground(String... args) {

			ConfigurationBuilder builder = new ConfigurationBuilder();
			builder.setOAuthConsumerKey(TWITTER_CONSUMER_KEY);
			builder.setOAuthConsumerSecret(TWITTER_CONSUMER_SECRET);
			
			AccessToken accessToken = new AccessToken(mSharedPreferences.getString("ACCESS_TOKEN", ""),
					mSharedPreferences.getString("ACCESS_TOKEN_SECRET", ""));
			
			Twitter twitter = new TwitterFactory(builder.build()).getInstance(accessToken);
			
			try {
				twitter4j.Status response = twitter.updateStatus(tweetText +" @Endorsement ");
				return response.toString();
			} catch (TwitterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		protected void onPostExecute(String res) {
			if(res != null){
				progress.dismiss();
				Toast.makeText(context, "Tweet Sucessfully Posted", Toast.LENGTH_SHORT).show();
				tDialog.dismiss();
			}else{
				progress.dismiss();
				Toast.makeText(context, "Error while tweeting !", Toast.LENGTH_SHORT).show();
				tDialog.dismiss();
			}
		}
	}
	*/
}
