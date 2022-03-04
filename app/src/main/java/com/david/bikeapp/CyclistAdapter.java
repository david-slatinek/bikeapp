package com.david.bikeapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.david.data.Tour;

import java.time.format.DateTimeFormatter;

public class CyclistAdapter extends RecyclerView.Adapter<CyclistAdapter.ViewHolder> {
    private final MyApplication cyclist;
    private final OnItemClickListener listener;

    public CyclistAdapter(MyApplication cyclist, OnItemClickListener listener) {
        this.cyclist = cyclist;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CyclistAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.rv_rowlayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CyclistAdapter.ViewHolder holder, int position) {
        Tour tour = cyclist.getCyclist().getTourAtPos(position);
        holder.tvStart.setText(DateTimeFormatter.ofPattern("dd. MM. yyyy HH:mm").format(tour.getStartPoint().getDateAndTime()));
        holder.tvEnd.setText(DateTimeFormatter.ofPattern("dd. MM. yyyy HH:mm").format(tour.getEndPoint().getDateAndTime()));
        holder.tvRowLength.setText(tour.getLength() + " km");
        holder.tvRowDesc.setText(tour.getDescription());
    }

    @Override
    public int getItemCount() {
        return cyclist.getCyclist() == null ? 0 : cyclist.getCyclist().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvStart, tvEnd, tvRowLength, tvRowDesc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvStart = itemView.findViewById(R.id.tvStart);
            tvEnd = itemView.findViewById(R.id.tvEnd);
            tvRowLength = itemView.findViewById(R.id.tvRowLength);
            tvRowDesc = itemView.findViewById(R.id.tvRowDesc);

            itemView.setOnLongClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION)
                        listener.onItemLongClick(itemView, position);
                }
                return false;
            });

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION)
                        listener.onItemClick(itemView, position);
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);

        void onItemLongClick(View itemView, int position);
    }
}
