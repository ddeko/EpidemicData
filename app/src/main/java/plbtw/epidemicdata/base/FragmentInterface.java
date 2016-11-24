package plbtw.epidemicdata.base;

import android.view.View;

/**
 * Created by dedeeko on 8/5/16.
 */
public interface FragmentInterface {
    public void initView(View view);
    public void setUICallbacks();
    public void updateUI();
    public String getPageTitle();
    public int getFragmentLayout();
    public String getTag();
}
