package org.o6u.studentapp.customViews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

@SuppressLint("AppCompatCustomView")
public class CustomButton extends Button {

    private final String FONT_NAME = "jf_flat_regular.ttf";

    public CustomButton(Context context) {
        super(context);
        init();
    }
    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public CustomButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    public void init(){

        Typeface font = Typeface.createFromAsset(getContext().getAssets(), FONT_NAME);
        setTypeface(font);

    }
}
