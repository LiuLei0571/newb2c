package com.example.b2c.net.util;

import android.app.Activity;
import android.widget.Toast;


public class UIUtils {
	
	public  static void showToast(final Activity context,final String  message){
        if("main".equals(Thread.currentThread().getName())){  
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();  
        }else{  
            context.runOnUiThread(new Runnable() {  
                @Override  
                public void run() {  
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();  
                }  
            });  
        }  
		   

	}

}
