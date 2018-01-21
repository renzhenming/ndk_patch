package com.example.ndk_patch;

import java.io.File;

import com.example.ndk_patch.utils.ApkUtils;
import com.example.ndk_patch.utils.BsPatch;
import com.example.ndk_patch.utils.Constants;
import com.example.ndk_patch.utils.DownloadUtils;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				new ApkUpdateTask().execute();
				
			}
		}, 2000);
		
	}

	class ApkUpdateTask extends AsyncTask<Void, Void, Boolean>{

		@Override
		protected Boolean doInBackground(Void... params) {
			try {
				System.out.println("开始合并");
				File patchFile = new File(Constants.PATCH_FILE_PATH);
				if (patchFile.exists()) {
					//获取当前应用的apk文件/data/app/app
					String oldfile = ApkUtils.getSourceApkPath(MainActivity.this, getPackageName());
					//2.合并得到最新版本的APK文件
					String newfile = Constants.NEW_APK_PATH;
					String patchfile = patchFile.getAbsolutePath();
					BsPatch.patch(oldfile, newfile, patchfile);
				}else{
					return false;
				}				
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			
			return true;
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			//3.安装
			if(result){
				System.out.println("合并成功");
				Toast.makeText(MainActivity.this, "合并成功，您正在进行无流量更新", Toast.LENGTH_SHORT).show();
				ApkUtils.installApk(MainActivity.this, Constants.NEW_APK_PATH);
			}
		}
		
	}
}
