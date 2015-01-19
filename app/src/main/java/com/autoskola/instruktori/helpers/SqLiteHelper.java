package com.autoskola.instruktori.helpers;

import android.content.Context;

import com.turbomanage.storm.DatabaseHelper;
import com.turbomanage.storm.api.Database;
import com.turbomanage.storm.api.DatabaseFactory;

@Database(name = "gpsdb", version = 1)
public class SqLiteHelper extends DatabaseHelper {

	public SqLiteHelper(Context ctx, DatabaseFactory dbFactory) {
		super(ctx, dbFactory);
		// TODO Auto-generated constructor stub
	}

	@Override
	public UpgradeStrategy getUpgradeStrategy() {
		// TODO Auto-generated method stub
		return UpgradeStrategy.DROP_CREATE;
	}

}
