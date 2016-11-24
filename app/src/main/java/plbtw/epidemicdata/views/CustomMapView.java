package plbtw.epidemicdata.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapView;


/**
 * Created by eko on 3/22/2016.
 */
public class CustomMapView extends MapView {

    private OnTouchListener mListener;

    public CustomMapView(Context context) {
        super(context);
    }

    public CustomMapView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomMapView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public CustomMapView(Context context, GoogleMapOptions options) {
        super(context, options);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                // Disallow ScrollView to intercept touch events.
                if (mListener != null){
                    mListener.onTouchDown();
                }
                break;

            case MotionEvent.ACTION_UP:
                // Allow ScrollView to intercept touch events.
                if (mListener != null){
                    mListener.onTouchUp();
                }
                break;
        }

        super.dispatchTouchEvent(ev);
        return true;
    }

    public void setListener(OnTouchListener listener) {
        mListener = listener;
    }

    public interface OnTouchListener {
        public abstract void onTouchUp();
        public abstract void onTouchDown();
    }
}
