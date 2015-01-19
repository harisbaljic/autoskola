package com.autoskola.instruktori.helpers;

import android.content.Context;

import com.turbomanage.storm.DatabaseHelper;
import com.turbomanage.storm.TableHelper;
import com.turbomanage.storm.api.DatabaseFactory;
import com.autoskola.instruktori.helpers.SqLiteHelper;

public class GpsdbFactory implements DatabaseFactory {

	private static final String DB_NAME = "gpsdb";
	private static final int DB_VERSION = 1;
	private static final TableHelper[] TABLE_HELPERS = new TableHelper[] {
	/*	new gps.model.sqlite.dao.GpsAktivnostPracenjeTable(),
		new gps.model.sqlite.dao.GpsZaposlenikTable(),
		new gps.model.sqlite.dao.GpsAktivnostTable(),
		new gps.model.sqlite.dao.PosiljkaZaPracenjeTable(),
		new gps.model.sqlite.dao.GpsPodaciZaPosiljkuTable() */
	};
	private static DatabaseHelper mInstance;

	/**
	 * Provides a singleton instance of the DatabaseHelper per application
	 * to prevent threading issues. See
	 * https://github.com/commonsguy/cwac-loaderex#notes-on-threading
	 *
	 * @param ctx Application context
	 * @return {@link android.database.sqlite.SQLiteOpenHelper} instance
	 */


    public static DatabaseHelper getDatabaseHelper(Context ctx) {
		if (mInstance==null) {
			// in case this is called from an AsyncTask or other thread
		    synchronized(GpsdbFactory.class) {
		    		if (mInstance == null)
					mInstance = new SqLiteHelper(
                            ctx.getApplicationContext(),
                            new GpsdbFactory());
			}
		}
		return mInstance;
	}

	public String getName() {
		return DB_NAME;
	}

	public int getVersion() {
		return DB_VERSION;
	}

	public TableHelper[] getTableHelpers() {
		return TABLE_HELPERS;
	}

	private GpsdbFactory() {
		// non-instantiable
	}

}
