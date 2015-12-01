package com.mallapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.mallapp.Model.MallActivitiesModel;
import com.mallapp.Model.ShopDetailModel;
import com.mallapp.Model.Shops;
import com.mallapp.Model.ShopsModel;
import com.mallapp.View.R;

import java.sql.SQLException;

/**
 * Created by Sharjeel on 11/30/2015.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    /************************************************
     * Suggested Copy/Paste code. Everything from here to the done block.
     ************************************************/

    private static final String DATABASE_NAME = "favorites.db";
    private static final int DATABASE_VERSION = 3;

    private Dao<ShopsModel, Integer> shopsDao;
    private Dao<MallActivitiesModel, Integer> mallActivitiesModelIntegerDao;
    private Dao<ShopDetailModel, Integer> shopDetailModelIntegerDao;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
    }

    /************************************************
     * Suggested Copy/Paste Done
     ************************************************/

    @Override
    public void onCreate(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource) {
        try {

            // Create tables. This onCreate() method will be invoked only once of the application life time i.e. the first time when the application starts.
            TableUtils.createTableIfNotExists(connectionSource, ShopsModel.class);
            TableUtils.createTableIfNotExists(connectionSource, MallActivitiesModel.class);
            TableUtils.createTableIfNotExists(connectionSource, ShopDetailModel.class);

        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Unable to create datbases", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource, int oldVer, int newVer) {
        try {
            // In case of change in database of next version of application, please increase the value of DATABASE_VERSION variable, then this method will be invoked
            //automatically. Developer needs to handle the upgrade logic here, i.e. create a new table or a new column to an existing table, take the backups of the
            // existing database etc.
            TableUtils.dropTable(connectionSource, ShopsModel.class, true);
            TableUtils.dropTable(connectionSource, MallActivitiesModel.class, true);
            TableUtils.dropTable(connectionSource, ShopDetailModel.class, true);
            onCreate(sqliteDatabase, connectionSource);

        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Unable to upgrade database from version " + oldVer + " to new "
                    + newVer, e);
        }
    }

    // Create the getDao methods of all database tables to access those from android code.
    // Insert, delete, read, update everything will be happened through DAOs

    public Dao<ShopsModel, Integer> getShopsDao() throws SQLException {
        if (shopsDao == null) {
            shopsDao = getDao(ShopsModel.class);
        }
        return shopsDao;
    }
    public Dao<MallActivitiesModel, Integer> getMallActivitiesDao() throws SQLException {
        if (mallActivitiesModelIntegerDao == null) {
            mallActivitiesModelIntegerDao = getDao(MallActivitiesModel.class);
        }
        return mallActivitiesModelIntegerDao;
    }

    public Dao<ShopDetailModel, Integer> getShopDetailDao() throws SQLException {
        if (shopDetailModelIntegerDao == null) {
            shopDetailModelIntegerDao = getDao(ShopDetailModel.class);
        }
        return shopDetailModelIntegerDao;
    }

}
