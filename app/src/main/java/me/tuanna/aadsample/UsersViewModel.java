package me.tuanna.aadsample;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

public class UsersViewModel extends AndroidViewModel {

    private LiveData<PagedList<User>> usersLiveData;

    public UsersViewModel(@NonNull Application application) {
        super(application);

        Context context = application.getApplicationContext();
        DataSource.Factory<Integer, User> factory =
                AppDatabase.getInstance(context).userDao().getAllPaged();

        LivePagedListBuilder<Integer, User> listBuilder = new LivePagedListBuilder<>(factory, 20);
        usersLiveData = listBuilder.build();
    }

    public LiveData<PagedList<User>> getUsersLiveData() {
        return usersLiveData;
    }
}
