package com.me.calendar;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;

public class CalendarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final ArrayList<LocalDate> days;
    public final View parentView;
    public final TextView dayOfMonth;
    public final ImageView imageView;
    private final CalendarAdapter.OnItemClickListener onItemClickListener;


    public CalendarViewHolder(@NonNull View itemView, CalendarAdapter.OnItemClickListener onItemClickListener, ArrayList<LocalDate> days) {
        super(itemView);
        this.days = days;
        this.parentView = itemView.findViewById(R.id.parentView);
        this.dayOfMonth = itemView.findViewById(R.id.cellDayText);
        this.onItemClickListener = onItemClickListener;
        itemView.setOnClickListener(this);
        imageView = itemView.findViewById(R.id.circleImage);
    }

    @Override
    public void onClick(View v) {
        onItemClickListener.onItemClick(getAdapterPosition(), days.get(getAdapterPosition()));
    }
}

class CircleDrawable extends Drawable {

    private Paint circlePaint;
    private int fillColor;
    private int strokeColor;
    private float radius;

    public CircleDrawable(int fillColor, int strokeColor, float radius) {
        this.fillColor = fillColor;
        this.strokeColor = strokeColor;
        this.radius = radius;
        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        int x = getBounds().centerX();
        int y = (int) (getBounds().centerY() * 0.65);
        //draw fill color circle
        circlePaint.setStyle(Paint.Style.FILL);
        circlePaint.setColor(fillColor);
        canvas.drawCircle(x, y, radius, circlePaint);
        // draw stroke circle
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setColor(strokeColor);
        circlePaint.setStrokeWidth(5);
        canvas.drawCircle(x, y, radius, circlePaint);
    }

    @Override
    public void setAlpha(int alpha) {
        circlePaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        circlePaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}
