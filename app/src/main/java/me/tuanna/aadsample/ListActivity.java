package me.tuanna.aadsample;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ListActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);

        setupActionBar();

        MyAdapter adapter = new MyAdapter(new UserDiffCallback());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);

        UsersViewModel viewModel = ViewModelProviders.of(this).get(UsersViewModel.class);
        viewModel.getUsersLiveData().observe(this, adapter::submitList);
    }

    private void setupActionBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.mipmap.ic_launcher_round);
    }

    @OnClick({ R.id.floatingActionButton })
    public void addNewUser() {
        Random random = new Random();
        User user = new User();
        user.name = "Android" + random.nextInt(1000);
        user.age = random.nextInt(100);

        new InsertAsyncTask(this, user).execute();
    }

    private class MyAdapter extends PagedListAdapter<User, MyViewHolder> {

        protected MyAdapter(@NonNull DiffUtil.ItemCallback<User> diffCallback) {
            super(diffCallback);
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.simple_list_item, null);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.bind(getItem(position));
        }
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView mNameText;
        private TextView mAgeText;

        private MyViewHolder(@NonNull View itemView) {
            super(itemView);

            mNameText = itemView.findViewById(R.id.nameText);
            mAgeText = itemView.findViewById(R.id.ageText);
        }

        public void bind(User user) {
            mNameText.setText("Name: " + user.name);
            mAgeText.setText("Age: " + user.age);
        }
    }

    private class UserDiffCallback extends DiffUtil.ItemCallback<User> {

        @Override
        public boolean areItemsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem.uid == newItem.uid;
        }

        @Override
        public boolean areContentsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem.uid == newItem.uid;
        }
    }

    private static class InsertAsyncTask extends AsyncTask<Void, Void, Void> {

        private WeakReference<Context> mContextReference;
        private User mUser;

        public InsertAsyncTask(Context context, User user) {
            mContextReference = new WeakReference<>(context);
            mUser = user;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Context context = mContextReference.get();
            if (context != null) {
                AppDatabase.getInstance(context).userDao().insert(mUser);
            }
            return null;
        }
    }
}
