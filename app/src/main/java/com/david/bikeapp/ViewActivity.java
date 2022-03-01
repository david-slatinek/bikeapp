package com.david.bikeapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class ViewActivity extends AppCompatActivity {
    private MyApplication cyclist;
    private RecyclerView recyclerView;
    CyclistAdapter adapter;

    public static final String ID = "ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        cyclist = (MyApplication) getApplication();
        recyclerView = findViewById(R.id.recyclerView);
        initAdapter();
    }

    public void displayToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    private void initAdapter() {
        adapter = new CyclistAdapter(cyclist, new CyclistAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                Intent i = new Intent(getBaseContext(), InsertActivity.class).putExtra(InsertActivity.FORM_MODE_ID, InsertActivity.FORM_MODE_UPDATE).putExtra(ID, position);
                startActivityForResult(i, InsertActivity.ACTIVITY_ID);
            }

            @Override
            public void onItemLongClick(View itemView, int position) {
                DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
                    if (which == DialogInterface.BUTTON_POSITIVE) {
                        cyclist.getCyclist().removeAt(position);
                        cyclist.saveToFile();
                        adapter.notifyDataSetChanged();
                        displayToast("Tour deleted!");
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(ViewActivity.this);
                builder.setMessage(R.string.delete_conf).setPositiveButton(R.string.yes, dialogClickListener).setNegativeButton(R.string.no, dialogClickListener).show();
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == InsertActivity.ACTIVITY_ID) {
            if (resultCode == RESULT_OK) {
                cyclist.saveToFile();
                adapter.notifyDataSetChanged();
                displayToast(getString(R.string.tour_updated) + "!");
            }
        }
    }

    public void onClickBack(View view) {
        super.onBackPressed();
    }
}