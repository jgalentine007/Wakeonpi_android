package jgalentine007.wakeonpi;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

class MyTwitter {
    private String consumerKey, consumerSecret, token, tokenSecret;
    private TwitterFactory factory;
    private Twitter twitter;
    private AccessToken accessToken;
    private long myId;
    private Context context;

    public MyTwitter(Context context, String consumerKey, String consumerSecret, String token, String tokenSecret){
        this.context = context;
        this.consumerKey = consumerKey;
        this.consumerSecret = consumerSecret;
        this.token = token;
        this.tokenSecret = tokenSecret;

        factory = new TwitterFactory();
        twitter = factory.getInstance();
        twitter.setOAuthConsumer(this.consumerKey, this.consumerSecret);
        accessToken = new AccessToken(this.token, this.tokenSecret);
        twitter.setOAuthAccessToken(accessToken);
    }

    public void init(){
        new InitTask().execute();
    }

    public void sendMessage(String message){
        new SendTask().execute(message);
    }

    // For sending direct messages using twitter4j
    private class SendTask extends AsyncTask<String,Void,Boolean>{
        protected Boolean doInBackground(String... message){
            try {
                twitter.sendDirectMessage(myId, message[0]);
            }
            catch (TwitterException te){
                return false;
            }

            return true;
        }

        protected void onPostExecute(Boolean result){

            if (result == true)
                Toast.makeText(context, "Trying to wakeup.", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(context, "Twitter error, check settings?", Toast.LENGTH_SHORT).show();
        }
    }

    // Fetch our user ID so we can send direct messages to ourself
    private class InitTask extends AsyncTask<Void,Void,Boolean> {

        protected Boolean doInBackground(Void... params){
            try {
                myId = twitter.getId();
            }
            catch (TwitterException te){
                return false;
            }

            return true;
        }
    }
}


