package org.o6u.studentapp.customViews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

@SuppressLint("AppCompatCustomView")
public class CustomEditText extends EditText {

    private final String FONT_NAME = "jf_flat_regular.ttf";

    public CustomEditText(Context context) {
        super(context);
        init();
    }
    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }
    public void init(){
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), FONT_NAME);
        setTypeface(font);
    }
}
