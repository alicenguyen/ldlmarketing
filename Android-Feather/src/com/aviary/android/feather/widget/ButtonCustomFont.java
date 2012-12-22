package com.aviary.android.feather.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;
import com.aviary.android.feather.R;
import com.aviary.android.feather.utils.TypefaceUtils;

public class ButtonCustomFont extends Button {

	@SuppressWarnings("unused")
	private final String LOG_TAG = "ButtonCustomFont";

	public ButtonCustomFont( Context context ) {
		super( context );
	}

	public ButtonCustomFont( Context context, AttributeSet attrs ) {
		super( context, attrs );
		setCustomFont( context, attrs );
	}

	public ButtonCustomFont( Context context, AttributeSet attrs, int defStyle ) {
		super( context, attrs, defStyle );
		setCustomFont( context, attrs );
	}

	private void setCustomFont( Context ctx, AttributeSet attrs ) {

		TypedArray array = ctx.obtainStyledAttributes( attrs, R.styleable.TextViewCustomFont );
		String font = array.getString( R.styleable.TextViewCustomFont_font );
		setCustomFont( font );
		array.recycle();
	}

	protected void setCustomFont( String fontname ) { 
		if ( null != fontname ) {
			try {
//				Typeface font = TypefaceUtils.createFromAsset( getContext().getAssets(), fontname );
				Typeface font = Typeface.createFromAsset(getContext().getAssets(), "Eurostib.TTF");	// included this for our own custom font
				setTypeface( font );
				setTextColor(getContext().getResources().getColor(R.color.white));
				
			} catch ( Throwable t ) {}
		}
	}
}
