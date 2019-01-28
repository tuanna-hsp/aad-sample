package me.tuanna.aadsample;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ListActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new MyAdapter());
    }

    private class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        private String[] dummyNames = { "Bob", "Tom", "Annie", "John", "Marry" };

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.simple_list_item, null);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.setName(dummyNames[position]);
            holder.setAge(((int) (Math.random() * 100)) + " years old.");
        }

        @Override
        public int getItemCount() {
            return dummyNames.length;
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

        void setName(String name) {
            mNameText.setText(name);
        }

        void setAge(String age) {
            mAgeText.setText(age);
        }
    }
}
