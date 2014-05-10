package com.branddesk.branddeskmobile;

import android.app.Application;

import com.parse.Parse;

public class BrandDeskMobileApplication extends Application {
	
	@Override
	public void onCreate() {
		super.onCreate();
		Parse.initialize(this, "sfSSjrN4CroONCQfy5UP8bxGaDIHhRbcOD9xFocv", "oB8tgjnRwW18k5tuMrl1aI1VwqjTp47mQX7nKOkb");

	}
}
