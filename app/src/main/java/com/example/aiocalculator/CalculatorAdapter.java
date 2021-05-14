package com.example.aiocalculator;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CalculatorAdapter extends RecyclerView.Adapter<CalculatorAdapter.ViewHolder> {

    ArrayList<Calculator> calcArrayList = new ArrayList<>();
    Context context;

    public CalculatorAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.calc_card_layout,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.iVCalcLogo.setImageResource(calcArrayList.get(position).getIcon());
        holder.tVCalcName.setText(calcArrayList.get(position).getTitle());
        holder.cVCalcList.setOnClickListener(v -> {
            switch (position){
                case 0:{
                    Intent intent = new Intent(context, SimpleCalculatorActivity.class);
                    context.startActivity(intent);
                    break;
                } case 1:{
                    Intent intent = new Intent(context, AgeCalActivity.class);
                    context.startActivity(intent);
                    break;
                } case 3:{
                    Intent intent = new Intent(context, TempConActivity.class);
                    context.startActivity(intent);
                    break;
                } case 4:{
                    Intent intent = new Intent(context, DataConvertorActivity.class);
                    context.startActivity(intent);
                    break;
                } default:{
                    Toast.makeText(context, "Under Construction", Toast.LENGTH_SHORT).show();
                }

            }

        });
    }

    @Override
    public int getItemCount() {
        return calcArrayList.size();
    }

    public void setCalcArrayList(ArrayList<Calculator> calcArrayList) {
        this.calcArrayList = calcArrayList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tVCalcName;
        ImageView iVCalcLogo;
        CardView cVCalcList;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tVCalcName = itemView.findViewById(R.id.tVCalcName);
            iVCalcLogo = itemView.findViewById(R.id.iVCalcLogo);
            cVCalcList = itemView.findViewById((R.id.cVCalcList));
        }
    }
}
