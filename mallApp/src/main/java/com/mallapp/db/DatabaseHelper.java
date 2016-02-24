package com.mallapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.chatdbserver.xmpp.model.OpenChats;
import com.chatdbserver.xmpp.model.PhoneBookContacts;
import com.chatdbserver.xmpp.model.SingleChat;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.mallapp.Model.MallActivitiesModel;
import com.mallapp.Model.RestaurantModel;
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
    private static final int DATABASE_VERSION = 5;

    private Dao<ShopsModel, Integer> shopsDao;
    private Dao<RestaurantModel, Integer> restaurantsDao;
    private Dao<MallActivitiesModel, Integer> mallActivitiesModelIntegerDao;
    private Dao<ShopDetailModel, Integer> shopDetailModelIntegerDao;

    private Dao<OpenChats, String> openchatsDao;
    private Dao<SingleChat, Integer> singblechatDao;
    private Dao<PhoneBookContacts, String> appcontactsDao;

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
            TableUtils.createTableIfNotExists(connectionSource, RestaurantModel.class);

            TableUtils.createTable(connectionSource, SingleChat.class);
            TableUtils.createTable(connectionSource, OpenChats.class);
            TableUtils.createTable(connectionSource, PhoneBookContacts.class);

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
            TableUtils.dropTable(connectionSource, RestaurantModel.class, true);

            TableUtils.dropTable(connectionSource, SingleChat.class, true);
            TableUtils.dropTable(connectionSource, OpenChats.class, true);

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
    public Dao<RestaurantModel, Integer> getRestaurantsDao() throws SQLException {
        if (restaurantsDao == null) {
            restaurantsDao = getDao(RestaurantModel.class);
        }
        return restaurantsDao;
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

    public Dao<SingleChat, Integer> getSinglechatDao() throws SQLException {
        if (this.singblechatDao == null) {
            this.singblechatDao = getDao(SingleChat.class);
        }
        return this.singblechatDao;
    }

    public Dao<OpenChats, String> getOpenchatsDao() throws SQLException {
        if (this.openchatsDao == null) {
            this.openchatsDao = getDao(OpenChats.class);
        }
        return this.openchatsDao;
    }

    public Dao<PhoneBookContacts, String> getContactsDao() throws SQLException {
        if (this.appcontactsDao == null) {
            this.appcontactsDao = getDao(PhoneBookContacts.class);
        }
        return this.appcontactsDao;
    }

    public void close() {
        super.close();
        this.singblechatDao = null;
        this.openchatsDao = null;
        this.appcontactsDao = null;
    }

}
