package com.kisan.util;

import android.content.res.Resources;

import androidx.annotation.NonNull;

import com.kisan.R;
import com.kisan.db.entity.Contact;
import com.kisan.repositories.ContactRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AppUtils {

    public static final String KEY_DB_CREATED = "db_created";


    public static boolean addMockData(@NonNull ContactRepository repository, List<Contact> data) {
        if (data != null && data.size() > 0) {
            repository.saveAll(data);
            return true;
        }
        return false;
    }

    public static String readMockDataFromRaw(@NonNull Resources resources) {
        InputStream is = resources.openRawResource(R.raw.contact_list);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
            return writer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static Date toDate(String data, String pattern) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.US);
        return format.parse(data);
    }
}
