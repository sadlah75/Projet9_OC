package com.openclassrooms.realestatemanager.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.ContentValues;
import android.content.Context;
import android.support.annotation.NonNull;

import com.openclassrooms.realestatemanager.database.converter.CTypeConverter;
import com.openclassrooms.realestatemanager.database.converter.DateConverter;
import com.openclassrooms.realestatemanager.database.dao.AddressDAO;
import com.openclassrooms.realestatemanager.database.dao.PhotoDAO;
import com.openclassrooms.realestatemanager.database.dao.PropertyDAO;
import com.openclassrooms.realestatemanager.database.dao.UserDAO;
import com.openclassrooms.realestatemanager.model.Address;
import com.openclassrooms.realestatemanager.model.Photo;
import com.openclassrooms.realestatemanager.model.Property;
import com.openclassrooms.realestatemanager.model.User;

import java.util.concurrent.Executors;

@Database(entities = {User.class, Property.class, Address.class, Photo.class},
        version = 1,exportSchema = false)
@TypeConverters({DateConverter.class, CTypeConverter.class})
public abstract class RealEstateManagerDatabase extends RoomDatabase {


    // Dao
    public abstract PhotoDAO photoDAO();
    public abstract AddressDAO addressDAO();
    public  abstract PropertyDAO propertyDAO();
    public abstract UserDAO userDAO();


    // Singleton
    private static volatile RealEstateManagerDatabase INSTANCE;

    // Instance
    public static RealEstateManagerDatabase getInstance(Context context) {
        if (INSTANCE ==  null) {
            synchronized (RealEstateManagerDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RealEstateManagerDatabase.class,"RealEstateDatabase.db")
                            .addCallback(prepopulateDatabase())
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static Callback prepopulateDatabase() {
        return new Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                Executors.newSingleThreadExecutor().execute(() -> {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("username","Sadek LAHMADI");
                    contentValues.put("id",1);
                    contentValues.put("urlPhoto","https://lh3.googleusercontent.com/ogw/AOh-ky0jgig0y55bZCTcxZD86g0daPFQaQ90Jmix86pSkg=s32-c-mo");
                    db.insert("User", OnConflictStrategy.IGNORE,contentValues);
                });
            }
        };
    }
}
