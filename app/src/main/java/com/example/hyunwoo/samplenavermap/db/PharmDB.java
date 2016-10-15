package com.example.hyunwoo.samplenavermap.db;

import android.provider.BaseColumns;

/**
 * Created by Hyunwoo on 2016. 10. 1..
 */
public class PharmDB {

    /**
     * 쿼리문을 날릴 때
     * Query의 key를 String으로 넣는 과정에서 오타를 낼 수 있고, 이 오타는 찾아내기 매우 어려움.
     * 다음과 같은 interface를 만들어서 KEY 들을 상수로 정의.
     */

    public interface PharmTable extends BaseColumns {
        public static final String TABLE_NAME = "PHARM_TABLE";
        public static final String COLUMN_MAIN_KEY = "MAIN_KEY";
        public static final String COLUMN_NAME_KOR = "NAME_KOR";
        public static final String COLUMN_ADD_KOR_ROAD = "ADD_KOR_ROAD";
        public static final String COLUMN_H_KOR_CITY = "H_KOR_CITY";
        public static final String COLUMN_H_KOR_GU = "H_KOR_GU";
        public static final String COLUMN_H_KOR_DONG = "H_KOR_DONG";
        public static final String COLUMN_TEL = "TEL";
        public static final String COLUMN_AVAIL_LAN = "AVAIL_LAN";

        // 추가 -----
        // ---------

    }

}
