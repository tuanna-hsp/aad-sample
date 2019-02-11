package me.tuanna.aadsample;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = { User.class }, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase sInstance;

    public static AppDatabase getInstance(final Context context) {
        if (sInstance == null) {
            synchronized (AppDatabase.class) {
                sInstance = buildDataBase(context.getApplicationContext());
            }
        }

        return sInstance;
    }

    private static AppDatabase buildDataBase(final Context appContext) {
        return Room.databaseBuilder(appContext, AppDatabase.class, "app_database")
                .addCallback(new RoomDatabase.Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);

                        Log.d("AAD", "Database created.");
                    }
                })
                .build();
    }

    public abstract UserDao userDao();
}
