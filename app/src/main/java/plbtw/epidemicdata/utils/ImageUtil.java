package plbtw.epidemicdata.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Looper;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;


import java.io.File;
import java.io.FileOutputStream;

import plbtw.epidemicdata.R;

public class ImageUtil {
    // display image from URL or path using Glide library
    public static void display(final Context context, final String urlOrPath, final ImageView target, final boolean needToRoundCorner) {
        if(urlOrPath == null || urlOrPath.isEmpty()) {
            Glide.with(context).load(R.drawable.no_image).asBitmap().centerCrop().into(new BitmapImageViewTarget(target) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable rbd = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                    rbd.setCircular(true);
                    target.setImageDrawable(rbd);
                }
            });

            return;
        }

        Object object;
        if(urlOrPath.contains("www.") || urlOrPath.contains("http://") || urlOrPath.contains("https://"))
            object = urlOrPath;
        else
            object = new File(urlOrPath);

        if(needToRoundCorner) {
            Glide.with(context).load(object).asBitmap().centerCrop()
                    .placeholder(R.drawable.no_image).error(R.drawable.no_image)
                    .into(new BitmapImageViewTarget(target) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            RoundedBitmapDrawable rbd = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                            rbd.setCircular(true);
                            target.setImageDrawable(rbd);
                        }
                    });
        }
        else {
            Glide.with(context).load(object).asBitmap().centerCrop()
                    .placeholder(R.drawable.no_image).error(R.drawable.no_image)
                    .into(target);
        }
    }

    // display image from resource ID using Glide library
    public static void display(final Context context, final Integer resId, final ImageView target, final boolean needToRoundCorner) {
        if(needToRoundCorner) {
            Glide.with(context).load(resId).asBitmap().centerCrop()
                    .placeholder(R.drawable.no_image).error(R.drawable.no_image)
                    .into(new BitmapImageViewTarget(target) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            RoundedBitmapDrawable rbd = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                            rbd.setCircular(true);
                            target.setImageDrawable(rbd);
                        }
                    });
        }
        else {
            Glide.with(context).load(resId).asBitmap().centerCrop()
                    .placeholder(R.drawable.no_image).error(R.drawable.no_image)
                    .into(target);
        }
    }

    public static void display(final Context context, final Integer resId, final ImageView target, final boolean needToRoundCorner, final boolean isUserAvatar) {
        if(isUserAvatar) {
            Glide.with(context).load(resId).asBitmap().centerCrop()
                    .placeholder(R.drawable.no_image).error(R.drawable.no_image)
                    .into(new BitmapImageViewTarget(target) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            RoundedBitmapDrawable rbd = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                            rbd.setCircular(true);
                            target.setImageDrawable(rbd);
                        }
                    });
        }
        else {
            if (needToRoundCorner) {
                Glide.with(context).load(resId).asBitmap().centerCrop()
                        .placeholder(R.drawable.no_image).error(R.drawable.no_image)
                        .into(new BitmapImageViewTarget(target) {
                            @Override
                            protected void setResource(Bitmap resource) {
                                RoundedBitmapDrawable rbd = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                                rbd.setCircular(true);
                                target.setImageDrawable(rbd);
                            }
                        });
            } else {
                Glide.with(context).load(resId).asBitmap().centerCrop()
                        .placeholder(R.drawable.no_image).error(R.drawable.no_image)
                        .into(target);
            }
        }
    }

//    // decode Uri, perfect for uploading initialize, must not be called on UI thread
//    public static Uri decode(final Context context, final Uri source) {
//        if(Looper.myLooper() == Looper.getMainLooper())
//            throw new IllegalStateException("Must not be invoked from the main thread.");
//
//        try {
//            File file = new File(Config.TEMP_FOLDER, "pick" + String.valueOf(System.currentTimeMillis()) + ".jpg");
//            Bitmap loaded = Glide.with(context)
//                    .load(source)
//                    .asBitmap()
//                    .into(Config.BITMAP_IMAGE_WIDTH, Config.BITMAP_IMAGE_HEIGHT)
//                    .get();
//            write(loaded, file);
//            return Uri.fromFile(file);
//        }
//        catch(Exception ex) {
//            ex.printStackTrace();
//        }
//
//        return null;
//    }

    // write bitmap from memory to file, must not be called on UI thread
    public static void write(Bitmap bitmap, File file) {
        if(Looper.myLooper() == Looper.getMainLooper())
            throw new IllegalStateException("Must not be invoked from the main thread.");

        if(file.exists())
            file.delete();

        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
