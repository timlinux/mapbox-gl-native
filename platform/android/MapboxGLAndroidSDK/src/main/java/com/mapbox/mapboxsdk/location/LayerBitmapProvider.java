package com.mapbox.mapboxsdk.location;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.mapbox.mapboxsdk.R;

import static com.mapbox.mapboxsdk.location.Utils.generateShadow;
import static com.mapbox.mapboxsdk.location.Utils.getBitmapFromDrawable;
import static com.mapbox.mapboxsdk.location.Utils.getDrawable;

class LayerBitmapProvider {

  private final Context context;

  LayerBitmapProvider(Context context) {
    this.context = context;
  }

  Bitmap generateBitmap(@DrawableRes int drawableRes, @ColorInt Integer tintColor) {
    Drawable drawable = getDrawable(context, drawableRes, tintColor);
    return getBitmapFromDrawable(drawable);
  }

  Bitmap generateShadowBitmap(@NonNull LocationComponentOptions options) {
    Drawable shadowDrawable = ContextCompat.getDrawable(context, R.drawable.mapbox_user_icon_shadow);
    return generateShadow(shadowDrawable, options.elevation());
  }
}
