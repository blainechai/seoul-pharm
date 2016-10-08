package com.example.hyunwoo.samplenavermap.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import com.example.hyunwoo.samplenavermap.model.PharmItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hyunwoo on 2016. 10. 1..
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "pharmDB";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE "+PharmDB.PharmTable.TABLE_NAME + "(" +
                PharmDB.PharmTable.COLUMN_MAIN_KEY + " TEXT NOT NULL, " +
                PharmDB.PharmTable.COLUMN_NAME_KOR + " TEXT NOT NULL, " +
                PharmDB.PharmTable.COLUMN_ADD_KOR_ROAD + " TEXT, " +
                PharmDB.PharmTable.COLUMN_H_KOR_CITY + " TEXT, " +
                PharmDB.PharmTable.COLUMN_H_KOR_GU + " TEXT, " +
                PharmDB.PharmTable.COLUMN_H_KOR_DONG + " TEXT, " +
                PharmDB.PharmTable.COLUMN_TEL + " TEXT, " +
                PharmDB.PharmTable.COLUMN_AVAIL_LAN + " TEXT ); ";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // /?
    }

/*
    public void addPharmItem (PharmItem item) {
        // 1. get reference to writable DB
        SQLiteDatabase db = getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.clear();
        values.put(PharmDB.PharmTable.COLUMN_MAIN_KEY , item.getMainKey());
        values.put(PharmDB.PharmTable.COLUMN_NAME_KOR , item.getNameKor());
        values.put(PharmDB.PharmTable.COLUMN_ADD_KOR_ROAD , item.getAddKorRoad());
        values.put(PharmDB.PharmTable.COLUMN_H_KOR_CITY , item.gethKorCity());
        values.put(PharmDB.PharmTable.COLUMN_H_KOR_GU , item.gethKorGu());
        values.put(PharmDB.PharmTable.COLUMN_H_KOR_DONG , item.gethKorDong());
        values.put(PharmDB.PharmTable.COLUMN_TEL , item.getTel());
        values.put(PharmDB.PharmTable.COLUMN_AVAIL_LAN , item.getAvailLan());

        // 3. insert
        db.insert(PharmDB.PharmTable.TABLE_NAME, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    public List<PharmItem> getAddressList(String keyword) {
        List<PharmItem> list = new ArrayList<PharmItem>();
        Cursor c = getAddressCursor(keyword);
        while(c.moveToNext()) {
            PharmItem item = new PharmItem();
            item.setMainKey(c.getString(c.getColumnIndex(PharmDB.PharmTable.COLUMN_MAIN_KEY)));
            item.setNameKor(c.getString(c.getColumnIndex(PharmDB.PharmTable.COLUMN_NAME_KOR)));
            item.setAddKorRoad(c.getString(c.getColumnIndex(PharmDB.PharmTable.COLUMN_ADD_KOR_ROAD)));
            item.sethKorCity(c.getString(c.getColumnIndex(PharmDB.PharmTable.COLUMN_H_KOR_CITY)));
            item.sethKorGu(c.getString(c.getColumnIndex(PharmDB.PharmTable.COLUMN_H_KOR_GU)));
            item.sethKorDong(c.getString(c.getColumnIndex(PharmDB.PharmTable.COLUMN_H_KOR_DONG)));
            item.setTel(c.getString(c.getColumnIndex(PharmDB.PharmTable.COLUMN_TEL)));
            item.setAvailLan(c.getString(c.getColumnIndex(PharmDB.PharmTable.COLUMN_AVAIL_LAN)));
            list.add(item);
        }
        return list;
    }

    public Cursor getAddressCursor(String keyword) {
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {PharmDB.PharmTable.COLUMN_MAIN_KEY,
                PharmDB.PharmTable.COLUMN_NAME_KOR,
                PharmDB.PharmTable.COLUMN_ADD_KOR_ROAD,
                PharmDB.PharmTable.COLUMN_H_KOR_CITY,
                PharmDB.PharmTable.COLUMN_H_KOR_GU,
                PharmDB.PharmTable.COLUMN_H_KOR_DONG,
                PharmDB.PharmTable.COLUMN_TEL,
                PharmDB.PharmTable.COLUMN_AVAIL_LAN };

        String selection = null;
        String[] args = null;
        if (!TextUtils.isEmpty(keyword)) {
            selection = "name LIKE ? OR address LIKE ? OR phone LIKE ? OR office LIKE ?";
            args = new String[] {"%" + keyword + "%","%" + keyword + "%","%" + keyword + "%","%" + keyword + "%"};
        }
        String orderBy = " name COLLATE LOCALIZED ASC";
        Cursor c = db.query("addressTable", columns, selection, args, null, null, orderBy);
        return c;
    }
*/

/*
    public void update(AddressItem item) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.clear();
        values.put(AddressDB.AddressTable.COLUMN_NAME,item.name);
        values.put(AddressDB.AddressTable.COLUMN_ADDRESS, item.address);
        values.put(AddressDB.AddressTable.COLUMN_PHONE, item.phone);
        values.put(AddressDB.AddressTable.COLUMN_OFFICE, item.office);

        String selection = AddressDB.AddressTable._ID + " = ?";
        String[] args = new String[] {"" + item._id};
        db.update(AddressDB.AddressTable.TABLE_NAME,values, selection, args);

    }

*/
}
