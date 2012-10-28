package com.greendev.flickr;

import com.greendev.image.Images;
import com.greendev.ldlmarketing.PhotoActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public class MyParcelableObjectArray implements Parcelable {
	private Object[] a;
	private Context context;


	
	public MyParcelableObjectArray(Context c, Object[] a) {
		this.a = a;	
		context = c;
	}

	public Object[] getArray(){
		return a;
	}
 
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeArray(a);

	}

	// this is used to regenerate your object. All Parcelables must have a
	// CREATOR that implements these two methods
	public static final Parcelable.Creator<MyParcelableObjectArray> CREATOR = new Parcelable.Creator<MyParcelableObjectArray>() {
		public MyParcelableObjectArray createFromParcel(Parcel a ) {
			return new MyParcelableObjectArray(a);
		}

		public MyParcelableObjectArray[] newArray(int size) {
			return new MyParcelableObjectArray[size];
		}

	};


	public MyParcelableObjectArray(Parcel in) {
		a = (Object[])in.readArray(PhotoActivity.class.getClassLoader());
	}

}
