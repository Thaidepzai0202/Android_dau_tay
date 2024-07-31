package com.example.jaja;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;

public class GradientTextView extends AppCompatTextView {

    private boolean hasFocus = true;

    public GradientTextView(Context context) {
        super(context);
        init();
    }

    public GradientTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GradientTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
//        setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                updateTextShader();
//            }
//        });

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        updateTextShader();
    }

    protected void setHasSelect(boolean hasSelect) {
        this.hasFocus = hasSelect;
        updateTextShader();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    private void updateTextShader() {
        if (hasFocus) {
            // Áp dụng gradient shader khi có focus
            Shader textShader = new LinearGradient(0, 0, 0, getHeight(), new int[]{getResources().getColor(R.color.primary_2), getResources().getColor(R.color.primary_3)}, null, Shader.TileMode.CLAMP);
            getPaint().setShader(textShader);
        } else {
            Shader textShader = new LinearGradient(0, 0, 0, getHeight(), new int[]{getResources().getColor(R.color.white), getResources().getColor(R.color.primary_1)}, null, Shader.TileMode.CLAMP);
            getPaint().setShader(textShader);
        }
        invalidate(); // Yêu cầu vẽ lại để áp dụng thay đổi
    }
}
