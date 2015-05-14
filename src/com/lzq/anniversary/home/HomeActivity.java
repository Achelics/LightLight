package com.lzq.anniversary.home;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import com.lzq.anniversary.util.MyToast;
import com.lzq.soeasy.R;

public class HomeActivity extends Activity implements OnClickListener{
	
	private ImageView dataUpdate; //���ݸ���
	private ImageView information;//��Ϣ����
	private ImageView brandView;//Ʒ��Ԥ��
	private ImageView direct_shot;//ֱ������
	private ImageView runImage;
	TranslateAnimation left, right;
	Animation up, down;
	/*ʵ�ִ�������*/
	private static final int REQUEST_TIMEOUT = 5*1000;//��������ʱ10����    
	private static final int SO_TIMEOUT = 10*1000;  //���õȴ����ݳ�ʱʱ��10����    
	private String responseMsg = "";  //���ض�Ӧ������
	private String lan;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//��ʼ���ĸ�ƥ�䰴ť
		initView();
	}
	
	private void initView(){
		dataUpdate=(ImageView)findViewById(R.id.data_update);
		information=(ImageView)findViewById(R.id.id_information);
		brandView=(ImageView)findViewById(R.id.brand_view);
		direct_shot=(ImageView)findViewById(R.id.direct_shot);
		
		dataUpdate.setOnClickListener(this);
		information.setOnClickListener(this);
		brandView.setOnClickListener(this);
		direct_shot.setOnClickListener(this);
		runImage = (ImageView) findViewById(R.id.run_image);
		runAnimation();
	}
	@SuppressLint("ShowToast") @Override
	public void onClick(View v) {
		if(v == dataUpdate){
			if(MyToast.isNetworkConnected(HomeActivity.this)){
				Thread loginThread = new Thread(new LoginThread());  
				loginThread.start(); 
			}else{
				Toast.makeText(getApplicationContext(), "δ��������", 2000).show();
			}
			
		}else if(v == information){
			Toast.makeText(getApplicationContext(), "�ף������Ե�Ŷ", 2000).show();
		}else if(v == brandView){
			Toast.makeText(getApplicationContext(), "�ף������Ե�Ŷ", 2000).show();
		}else if(v == direct_shot){
			Toast.makeText(getApplicationContext(), "�ף������Ե�Ŷ", 2000).show();
		}
	}
	
	 //LoginThread�߳���  
	  class LoginThread implements Runnable  {

		@Override
		public void run() {
			 lan = "1";
			 boolean loginValidate = loginServer(lan);
			 Message msg = handler.obtainMessage();  
			 if(loginValidate){ 
				 if(responseMsg.equals("1")){  
					 msg.what = 0;  
					 handler.sendMessage(msg);
				 }
			 }
		}
		  
	  }
	  
	  	/*Handler*/  
		Handler handler = new Handler(){  
				@SuppressLint("ShowToast") public void handleMessage(Message msg){  
					switch(msg.what)  {  
					case 0:  
						Toast.makeText(getApplicationContext(), "�������ѵ���", 2000).show(); 
						break;
					default:
						break;
					}
				}
		};
		
		//��ʼ��HttpClient�������ó�ʱ  
		public HttpClient getHttpClient(){  
			BasicHttpParams httpParams = new BasicHttpParams();  
			HttpConnectionParams.setConnectionTimeout(httpParams, REQUEST_TIMEOUT);  
			HttpConnectionParams.setSoTimeout(httpParams, SO_TIMEOUT);  
			HttpClient client = new DefaultHttpClient(httpParams);  
			return client;  
		}  
		
		//�ύpsot����
		@SuppressWarnings("unchecked")
		private boolean loginServer(String lan) {  
			boolean loginValidate = false;
			//ʹ��HTTP�ͻ���ʵ��  
			String urlStr = "http://202.207.240.147:8089/light_heart/get_lan.php";  
			HttpPost request = new HttpPost(urlStr);  
			//������ݲ�����Ļ������ԶԴ��ݵĲ������з�װ  
			@SuppressWarnings("rawtypes")
			List params = new ArrayList();  
			//����û���������  
			params.add(new BasicNameValuePair("lan",lan));  
			try  {  
				//�������������  
				request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));  
				HttpClient client = getHttpClient();  
				//ִ�����󷵻���Ӧ  
				HttpResponse response = client.execute(request);  
				//�ж��Ƿ�����ɹ�  
				if(response.getStatusLine().getStatusCode()==200) {  
					loginValidate = true;  
					/*�����Ӧ��Ϣ */ 
					responseMsg = EntityUtils.toString(response.getEntity());  
					/*��½�ɹ�*/
					if (loginValidate) {
						/*�ͷ�����*/
						client.getConnectionManager().closeExpiredConnections();
						client.getConnectionManager().shutdown();
					} 
				}
			}catch(Exception e) {  
				e.printStackTrace();  
			}  
			return loginValidate;  
		} 
	
	
	public void runAnimation() {
		down = AnimationUtils.loadAnimation(HomeActivity.this,
				R.anim.bg_del_down);
		up = AnimationUtils.loadAnimation(HomeActivity.this,
				R.anim.bg_del_up);
		down.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation arg0) {
			}
		});

		right = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0f,
				Animation.RELATIVE_TO_PARENT, -1f,
				Animation.RELATIVE_TO_PARENT, 0f, Animation.RELATIVE_TO_PARENT,
				0f);
		left = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, -1f,
				Animation.RELATIVE_TO_PARENT, 0f, Animation.RELATIVE_TO_PARENT,
				0f, Animation.RELATIVE_TO_PARENT, 0f);
		right.setDuration(25000);
		left.setDuration(25000);
		right.setFillAfter(true);
		left.setFillAfter(true);

		right.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				runImage.startAnimation(left);
			}
		});
		left.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				runImage.startAnimation(right);
			}
		});
		runImage.startAnimation(right);
	}
}
