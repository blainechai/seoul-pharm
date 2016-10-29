package com.daejong.seoulpharm.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.daejong.seoulpharm.model.MedicineInfo;
import com.daejong.seoulpharm.model.PharmItem;

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

    /**
     * TODO : DB COLUMN 추가 적용! (Initialize)
     */

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE " + PharmDB.PharmTable.TABLE_NAME + "(" +
                PharmDB.PharmTable.COLUMN_MAIN_KEY + " TEXT PRIMARY KEY NOT NULL, " +

                PharmDB.PharmTable.COLUMN_NAME_KOR + " TEXT NOT NULL, " +
                PharmDB.PharmTable.COLUMN_NAME_ENG + " TEXT NOT NULL, " +
                PharmDB.PharmTable.COLUMN_NAME_CHI + " TEXT NOT NULL, " +


                PharmDB.PharmTable.COLUMN_ADDRESS_KOR + " TEXT, " +
                PharmDB.PharmTable.COLUMN_ADDRESS_ENG + " TEXT, " +

                PharmDB.PharmTable.COLUMN_H_KOR_CITY + " TEXT, " +
                PharmDB.PharmTable.COLUMN_H_KOR_GU + " TEXT, " +
                PharmDB.PharmTable.COLUMN_H_KOR_DONG + " TEXT, " +

                PharmDB.PharmTable.COLUMN_TEL + " TEXT, " +

                PharmDB.PharmTable.COLUMN_AVAIL_LAN_KOR + " TEXT, " +
                PharmDB.PharmTable.COLUMN_AVAIL_LAN_ENG + " TEXT, " +
                PharmDB.PharmTable.COLUMN_AVAIL_LAN_CHI + " TEXT, " +

                PharmDB.PharmTable.COLUMN_LATITUDE + " TEXT," +
                PharmDB.PharmTable.COLUMN_LONGTITUDE + " TEXT );";
        sqLiteDatabase.execSQL(sql);

        sql = "CREATE TABLE " + PharmDB.ScrappedPharmTable.TABLE_NAME + "(" +
                PharmDB.ScrappedPharmTable.COLUMN_PHARM_KEY + " TEXT PRIMARY KEY NOT NULL );";
        sqLiteDatabase.execSQL(sql);

        sql = "CREATE TABLE " + PharmDB.ScrappedComponentTable.TABLE_NAME + "(" +
                PharmDB.ScrappedComponentTable.COLUMN_COMPONENT_KEY + " TEXT PRIMARY KEY NOT NULL," +
                PharmDB.ScrappedComponentTable.COLUMN_COMPANY_NAME + " TEXT, " +
                PharmDB.ScrappedComponentTable.COLUMN_IMAGE_URL + " TEXT, " +
                PharmDB.ScrappedComponentTable.COLUMN_MEDICINE_NAME + " TEXT );";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // /?
    }

    // INSERT DATA
    public void addPharmItem(PharmItem item) {
        // 1. get reference to writable DB
        SQLiteDatabase db = getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.clear();
        values.put(PharmDB.PharmTable.COLUMN_MAIN_KEY, item.getMainKey());

        values.put(PharmDB.PharmTable.COLUMN_NAME_KOR, item.getNameKor());
        values.put(PharmDB.PharmTable.COLUMN_NAME_ENG, item.getNameEng());
        values.put(PharmDB.PharmTable.COLUMN_NAME_CHI, item.getNameChi());

        values.put(PharmDB.PharmTable.COLUMN_ADDRESS_KOR, item.getAddressKor());
        values.put(PharmDB.PharmTable.COLUMN_ADDRESS_ENG, item.getAddressEng());

        values.put(PharmDB.PharmTable.COLUMN_H_KOR_CITY, item.gethKorCity());
        values.put(PharmDB.PharmTable.COLUMN_H_KOR_GU, item.gethKorGu());
        values.put(PharmDB.PharmTable.COLUMN_H_KOR_DONG, item.gethKorDong());
        values.put(PharmDB.PharmTable.COLUMN_TEL, item.getTel());

        values.put(PharmDB.PharmTable.COLUMN_AVAIL_LAN_KOR, item.getAvailLanKor());
        values.put(PharmDB.PharmTable.COLUMN_AVAIL_LAN_ENG, item.getAvailLanEng());
        values.put(PharmDB.PharmTable.COLUMN_AVAIL_LAN_CHI, item.getAvailLanChi());

        values.put(PharmDB.PharmTable.COLUMN_LATITUDE, item.getLatitude());
        values.put(PharmDB.PharmTable.COLUMN_LONGTITUDE, item.getLongtitude());


        // 3. insert
        db.insertWithOnConflict(PharmDB.PharmTable.TABLE_NAME, // table
                null, //nullColumnHack
                values,
                SQLiteDatabase.CONFLICT_REPLACE); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    public int getPharmRowCount() {
        String countQuery = "SELECT  * FROM " + PharmDB.PharmTable.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }

    // GET DATA LIST
    public List<PharmItem> getPharmList() {
        List<PharmItem> list = new ArrayList<PharmItem>();
        SQLiteDatabase db = this.getReadableDatabase();
        // Select All Query
        String selectQuery = "SELECT * FROM " + PharmDB.PharmTable.TABLE_NAME;

        Cursor c = db.rawQuery(selectQuery, null);
        try {
            // looping through all rows and adding to list
            if (c.moveToFirst()) {
                do {
                    PharmItem item = new PharmItem();
                    //only one column
                    item.setMainKey(c.getString(c.getColumnIndex(PharmDB.PharmTable.COLUMN_MAIN_KEY)));

                    item.setNameKor(c.getString(c.getColumnIndex(PharmDB.PharmTable.COLUMN_NAME_KOR)));
                    item.setNameEng(c.getString(c.getColumnIndex(PharmDB.PharmTable.COLUMN_NAME_ENG)));
                    item.setNameChi(c.getString(c.getColumnIndex(PharmDB.PharmTable.COLUMN_NAME_CHI)));

                    item.setAddressKor(c.getString(c.getColumnIndex(PharmDB.PharmTable.COLUMN_ADDRESS_KOR)));
                    item.setAddressEng(c.getString(c.getColumnIndex(PharmDB.PharmTable.COLUMN_ADDRESS_ENG)));

                    item.sethKorCity(c.getString(c.getColumnIndex(PharmDB.PharmTable.COLUMN_H_KOR_CITY)));
                    item.sethKorGu(c.getString(c.getColumnIndex(PharmDB.PharmTable.COLUMN_H_KOR_GU)));
                    item.sethKorDong(c.getString(c.getColumnIndex(PharmDB.PharmTable.COLUMN_H_KOR_DONG)));
                    item.setTel(c.getString(c.getColumnIndex(PharmDB.PharmTable.COLUMN_TEL)));

                    item.setAvailLanKor(c.getString(c.getColumnIndex(PharmDB.PharmTable.COLUMN_AVAIL_LAN_KOR)));
                    item.setAvailLanEng(c.getString(c.getColumnIndex(PharmDB.PharmTable.COLUMN_AVAIL_LAN_ENG)));
                    item.setAvailLanChi(c.getString(c.getColumnIndex(PharmDB.PharmTable.COLUMN_AVAIL_LAN_CHI)));

                    item.setLatitude(c.getString(c.getColumnIndex(PharmDB.PharmTable.COLUMN_LATITUDE)));
                    item.setLongtitude(c.getString(c.getColumnIndex(PharmDB.PharmTable.COLUMN_LONGTITUDE)));

                    list.add(item);
//                    Log.d(" GET PHARMS ITEM POS ","LAT : "+c.getString(c.getColumnIndex(PharmDB.PharmTable.COLUMN_LATITUDE))
//                            + "  /  LNG : "+c.getString(c.getColumnIndex(PharmDB.PharmTable.COLUMN_LONGTITUDE)));

                } while (c.moveToNext());
            }

        } finally {
            try {
                c.close();
            } catch (Exception ignore) {
            }
        }

        return list;

    }

    public PharmItem getPharmItemByName(String pharmName) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = null;
        PharmItem item = null;

        try {
            c = db.rawQuery("SELECT *" +
                    " FROM " + PharmDB.PharmTable.TABLE_NAME +
                    " WHERE " + PharmDB.PharmTable.COLUMN_NAME_KOR + "=? ", new String[]{pharmName + ""});

            if (c.getCount() > 0) {
                c.moveToFirst();
                item = new PharmItem();
                item.setMainKey(c.getString(c.getColumnIndex(PharmDB.PharmTable.COLUMN_MAIN_KEY)));

                item.setNameKor(c.getString(c.getColumnIndex(PharmDB.PharmTable.COLUMN_NAME_KOR)));
                item.setNameEng(c.getString(c.getColumnIndex(PharmDB.PharmTable.COLUMN_NAME_ENG)));
                item.setNameChi(c.getString(c.getColumnIndex(PharmDB.PharmTable.COLUMN_NAME_CHI)));

                item.setAddressKor(c.getString(c.getColumnIndex(PharmDB.PharmTable.COLUMN_ADDRESS_KOR)));
                item.setAddressEng(c.getString(c.getColumnIndex(PharmDB.PharmTable.COLUMN_ADDRESS_ENG)));

                item.sethKorCity(c.getString(c.getColumnIndex(PharmDB.PharmTable.COLUMN_H_KOR_CITY)));
                item.sethKorGu(c.getString(c.getColumnIndex(PharmDB.PharmTable.COLUMN_H_KOR_GU)));
                item.sethKorDong(c.getString(c.getColumnIndex(PharmDB.PharmTable.COLUMN_H_KOR_DONG)));
                item.setTel(c.getString(c.getColumnIndex(PharmDB.PharmTable.COLUMN_TEL)));

                item.setAvailLanKor(c.getString(c.getColumnIndex(PharmDB.PharmTable.COLUMN_AVAIL_LAN_KOR)));
                item.setAvailLanEng(c.getString(c.getColumnIndex(PharmDB.PharmTable.COLUMN_AVAIL_LAN_ENG)));
                item.setAvailLanChi(c.getString(c.getColumnIndex(PharmDB.PharmTable.COLUMN_AVAIL_LAN_CHI)));

                item.setLatitude(c.getString(c.getColumnIndex(PharmDB.PharmTable.COLUMN_LATITUDE)));
                item.setLongtitude(c.getString(c.getColumnIndex(PharmDB.PharmTable.COLUMN_LONGTITUDE)));
            }

            return item;

        } finally {
            c.close();
        }
    }

    public PharmItem getPharmItemByKey(String key) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = null;
        PharmItem item = null;

        try {
            c = db.rawQuery("SELECT *" +
                    " FROM " + PharmDB.PharmTable.TABLE_NAME +
                    " WHERE " + PharmDB.PharmTable.COLUMN_MAIN_KEY + "=? ", new String[]{key + ""});

            if (c.getCount() > 0) {
                c.moveToFirst();
                item = new PharmItem();
                item.setMainKey(c.getString(c.getColumnIndex(PharmDB.PharmTable.COLUMN_MAIN_KEY)));

                item.setNameKor(c.getString(c.getColumnIndex(PharmDB.PharmTable.COLUMN_NAME_KOR)));
                item.setNameEng(c.getString(c.getColumnIndex(PharmDB.PharmTable.COLUMN_NAME_ENG)));
                item.setNameChi(c.getString(c.getColumnIndex(PharmDB.PharmTable.COLUMN_NAME_CHI)));

                item.setAddressKor(c.getString(c.getColumnIndex(PharmDB.PharmTable.COLUMN_ADDRESS_KOR)));
                item.setAddressEng(c.getString(c.getColumnIndex(PharmDB.PharmTable.COLUMN_ADDRESS_ENG)));

                item.sethKorCity(c.getString(c.getColumnIndex(PharmDB.PharmTable.COLUMN_H_KOR_CITY)));
                item.sethKorGu(c.getString(c.getColumnIndex(PharmDB.PharmTable.COLUMN_H_KOR_GU)));
                item.sethKorDong(c.getString(c.getColumnIndex(PharmDB.PharmTable.COLUMN_H_KOR_DONG)));
                item.setTel(c.getString(c.getColumnIndex(PharmDB.PharmTable.COLUMN_TEL)));

                item.setAvailLanKor(c.getString(c.getColumnIndex(PharmDB.PharmTable.COLUMN_AVAIL_LAN_KOR)));
                item.setAvailLanEng(c.getString(c.getColumnIndex(PharmDB.PharmTable.COLUMN_AVAIL_LAN_ENG)));
                item.setAvailLanChi(c.getString(c.getColumnIndex(PharmDB.PharmTable.COLUMN_AVAIL_LAN_CHI)));

                item.setLatitude(c.getString(c.getColumnIndex(PharmDB.PharmTable.COLUMN_LATITUDE)));
                item.setLongtitude(c.getString(c.getColumnIndex(PharmDB.PharmTable.COLUMN_LONGTITUDE)));
            }

            return item;

        } finally {
            c.close();
        }
    }


    public void addPharmBookmark(String key) {
        // 1. get reference to writable DB
        SQLiteDatabase db = getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.clear();

        values.put(PharmDB.PharmTable.COLUMN_MAIN_KEY, key);


        // 3. insert
        db.insertWithOnConflict(PharmDB.ScrappedPharmTable.TABLE_NAME, // table
                null, //nullColumnHack
                values,
                SQLiteDatabase.CONFLICT_REPLACE); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    public void deletePharmScrapped(String deleteKey) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(PharmDB.ScrappedPharmTable.TABLE_NAME, PharmDB.ScrappedPharmTable.COLUMN_PHARM_KEY + " = ? ", new String[]{deleteKey + ""});
    }

    public boolean searchPharmScrapped(String searchKey) {
        SQLiteDatabase db = getWritableDatabase();

        Cursor c = db.rawQuery("SELECT " + PharmDB.ScrappedPharmTable.COLUMN_PHARM_KEY +
                " FROM " + PharmDB.ScrappedPharmTable.TABLE_NAME +
                " WHERE " + PharmDB.ScrappedPharmTable.COLUMN_PHARM_KEY + " = ? ", new String[]{searchKey + ""});

        if (c.getCount() <= 0) {
            c.close();
            return false;
        }
        c.close();
        return true;
    }


    public List<PharmItem> getScrappedPharms() {
        List<PharmItem> list = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        // Select All Query
        String selectQuery = "SELECT * FROM " + PharmDB.ScrappedPharmTable.TABLE_NAME;

        Cursor c = db.rawQuery(selectQuery, null);
        try {
            // looping through all rows and adding to list
            if (c.moveToFirst()) {
                do {

                    String key = c.getString(c.getColumnIndex(PharmDB.ScrappedPharmTable.COLUMN_PHARM_KEY));
                    PharmItem item = getPharmItemByKey(key);
                    list.add(item);

                } while (c.moveToNext());
            }

        } finally {
            try {
                c.close();
            } catch (Exception ignore) {
            }
        }


        return list;
    }


    public void addMedicineBookmark(MedicineInfo medicineInfo) {
        // 1. get reference to writable DB
        SQLiteDatabase db = getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.clear();

        values.put(PharmDB.ScrappedComponentTable.COLUMN_COMPONENT_KEY, medicineInfo.getItemSeq());
        values.put(PharmDB.ScrappedComponentTable.COLUMN_COMPANY_NAME, medicineInfo.getCompany());
        values.put(PharmDB.ScrappedComponentTable.COLUMN_MEDICINE_NAME, medicineInfo.getName());
        values.put(PharmDB.ScrappedComponentTable.COLUMN_IMAGE_URL, medicineInfo.getImageSrc());

        // 3. insert
        db.insertWithOnConflict(PharmDB.ScrappedComponentTable.TABLE_NAME, // table
                null, //nullColumnHack
                values,
                SQLiteDatabase.CONFLICT_REPLACE); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    public void deleteMedicineScrapped(String deleteKey) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(PharmDB.ScrappedComponentTable.TABLE_NAME, PharmDB.ScrappedComponentTable.COLUMN_COMPONENT_KEY + " = ? ", new String[]{deleteKey + ""});
    }

    public boolean searchMedicine(String searchKey) {
        SQLiteDatabase db = getWritableDatabase();

        Cursor c = db.rawQuery("SELECT " + PharmDB.ScrappedComponentTable.COLUMN_COMPONENT_KEY +
                " FROM " + PharmDB.ScrappedComponentTable.TABLE_NAME +
                " WHERE " + PharmDB.ScrappedComponentTable.COLUMN_COMPONENT_KEY + " = ? ", new String[]{searchKey + ""});

        if (c.getCount() <= 0) {
            c.close();
            return false;
        }
        c.close();
        return true;
    }

    public List<MedicineInfo> getScrappedMedicine() {
        List<MedicineInfo> list = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        // Select All Query
        String selectQuery = "SELECT * FROM " + PharmDB.ScrappedComponentTable.TABLE_NAME;

        Cursor c = db.rawQuery(selectQuery, null);
        try {
            // looping through all rows and adding to list
            if (c.moveToFirst()) {
                do {

                    String key = c.getString(c.getColumnIndex(PharmDB.ScrappedComponentTable.COLUMN_COMPONENT_KEY));
                    MedicineInfo item = new MedicineInfo();
                    item.setName(c.getString(c.getColumnIndex(PharmDB.ScrappedComponentTable.COLUMN_MEDICINE_NAME)));
                    item.setCompany(c.getString(c.getColumnIndex(PharmDB.ScrappedComponentTable.COLUMN_COMPANY_NAME)));
                    item.setImageSrc(c.getString(c.getColumnIndex(PharmDB.ScrappedComponentTable.COLUMN_IMAGE_URL)));
                    item.setItemSeq(c.getString(c.getColumnIndex(PharmDB.ScrappedComponentTable.COLUMN_COMPONENT_KEY)));
                    list.add(item);

                } while (c.moveToNext());
            }

        } finally {
            try {
                c.close();
            } catch (Exception ignore) {
            }
        }


        return list;
    }
}
