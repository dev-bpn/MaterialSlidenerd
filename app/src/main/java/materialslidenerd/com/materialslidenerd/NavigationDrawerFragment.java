package materialslidenerd.com.materialslidenerd;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class NavigationDrawerFragment extends Fragment {

    private RecyclerView recyclerView;

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private View containerView;

    private boolean mUserLearnedDrawer;
    private boolean mFromSavedInstanceState;
    private static final String PREFS_FILE_NAME="my_prefs";
    private static final String KEY_USER_LEARNED_DRAWER = "user_learned_drawer";

    public NavigationDrawerFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserLearnedDrawer = Boolean.valueOf(readFromPreferences(getActivity() , KEY_USER_LEARNED_DRAWER , "false"));
        if(savedInstanceState != null){
            mFromSavedInstanceState = true;
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_view);
        return layout;

    }

    public void setup(int fragmentId , DrawerLayout drawerLayout , Toolbar toolbar ) {
        containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity() , drawerLayout , toolbar , R.string.drawer_open , R.string.drawer_close)
        {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if(!mUserLearnedDrawer){
                    mUserLearnedDrawer = true;
                    saveToPreferences(getActivity() , KEY_USER_LEARNED_DRAWER , mUserLearnedDrawer+"");
                }
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }
        };

        // directly open fragment for first time
        if(!mUserLearnedDrawer && !mFromSavedInstanceState){
            mDrawerLayout.openDrawer(containerView);
        }

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
    }

    public static void saveToPreferences(Context context , String preferenceName , String preferenceValue){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_FILE_NAME , Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(preferenceName , preferenceValue);
        editor.apply();
    }

    public static String readFromPreferences(Context context , String preferenceName , String defaultValue){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(preferenceName , defaultValue);
    }
}
