package com.dirinc.classgrouper;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NavigationDrawerFragment extends Fragment implements NavigationDrawerCallbacks {

    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";

    private NavigationDrawerCallbacks callbacks;

    private ActionBarDrawerToggle actionBarDrawerToggle;

    private NavigationDrawerAdapter adapter;
    private DrawerLayout drawerLayout;
    private RecyclerView drawerList;
    private View fragmentContainerView;

    private List<NavigationItem> items = new ArrayList<>();

    private int currentSelectedPosition = 0;
    private boolean fromSavedInstanceState;
    private boolean userLearnedDrawer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        userLearnedDrawer = sp.getBoolean(PREF_USER_LEARNED_DRAWER, false);

        if (savedInstanceState != null) {
            currentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
            fromSavedInstanceState = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        drawerList = (RecyclerView) view.findViewById(R.id.drawerList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        drawerList.setLayoutManager(layoutManager);
        drawerList.setHasFixedSize(true);

        final List<NavigationItem> navigationItems = getMenu();
        adapter = new NavigationDrawerAdapter(navigationItems);
        adapter.setNavigationDrawerCallbacks(this);
        drawerList.setAdapter(adapter);
        selectItem(currentSelectedPosition);

        return view;
    }

    public boolean isDrawerOpen() {
        return drawerLayout != null && drawerLayout.isDrawerOpen(fragmentContainerView);
    }

    public ActionBarDrawerToggle getActionBarDrawerToggle() {
        return actionBarDrawerToggle;
    }

    public DrawerLayout getDrawerLayout() {
        return drawerLayout;
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        selectItem(position);
    }

    public List<NavigationItem> getMenu() {
        //items.add(new NavigationItem("item 1", getResources().getDrawable(R.drawable.ic_class)));
        return items;
    }

    public void addItem(String name, Drawable image) {
        adapter.addItem(name, image);
    }

    public void setup(int fragmentId, final DrawerLayout drawerLayout, final Toolbar toolbar, boolean toggle) {
        fragmentContainerView = getActivity().findViewById(fragmentId);
        this.drawerLayout = drawerLayout;

        this.drawerLayout.setStatusBarBackgroundColor(getResources().getColor(R.color.primary_dark));

        if (toggle) {
            actionBarDrawerToggle = new ActionBarDrawerToggle(
                    getActivity(),
                    NavigationDrawerFragment.this.drawerLayout,
                    toolbar,
                    R.string.drawer_open,
                    R.string.drawer_close) {

                @Override
                public void onDrawerClosed(View drawerView) {
                    super.onDrawerClosed(drawerView);
                    if (!isAdded()) return;
                    getActivity().invalidateOptionsMenu(); // calls onPrepareOptionsMenu()
                }

                @Override
                public void onDrawerOpened(View drawerView) {
                    super.onDrawerOpened(drawerView);
                    if (!isAdded()) return;
                    if (!userLearnedDrawer) {
                        userLearnedDrawer = true;
                        SharedPreferences sp = PreferenceManager
                                .getDefaultSharedPreferences(getActivity());
                        sp.edit().putBoolean(PREF_USER_LEARNED_DRAWER, true).apply();
                    }
                    getActivity().invalidateOptionsMenu(); // calls onPrepareOptionsMenu()
                }
            };
        } else  {
            actionBarDrawerToggle = new ActionBarDrawerToggle(
                    getActivity(),
                    NavigationDrawerFragment.this.drawerLayout,
                    R.string.drawer_open,
                    R.string.drawer_close) {

                @Override
                public void onDrawerClosed(View drawerView) {
                    super.onDrawerClosed(drawerView);
                    if (!isAdded()) return;
                    getActivity().invalidateOptionsMenu(); // calls onPrepareOptionsMenu()
                }

                @Override
                public void onDrawerOpened(View drawerView) {
                    super.onDrawerOpened(drawerView);
                    if (!isAdded()) return;
                    if (!userLearnedDrawer) {
                        userLearnedDrawer = true;
                        SharedPreferences sp = PreferenceManager
                                .getDefaultSharedPreferences(getActivity());
                        sp.edit().putBoolean(PREF_USER_LEARNED_DRAWER, true).apply();
                    }
                    getActivity().invalidateOptionsMenu(); // calls onPrepareOptionsMenu()
                }
            };
            actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                        intent = new Intent(getContext(), ActivityMain.class);
                    } else {
                        // LMFAO BEST HACK AROUND EVER
                        intent = new Intent(toolbar.getContext(), ActivityMain.class);
                    }
                    startActivity(intent);
                }
            });
        }

        if (!userLearnedDrawer && !fromSavedInstanceState) {
            this.drawerLayout.openDrawer(fragmentContainerView);
        }

        this.drawerLayout.post(new Runnable() {
            @Override
            public void run() {
                actionBarDrawerToggle.syncState();
            }
        });

        this.drawerLayout.setDrawerListener(actionBarDrawerToggle);
    }

    private void selectItem(int position) {
        currentSelectedPosition = position;
        if (drawerLayout != null) {
            drawerLayout.closeDrawer(fragmentContainerView);
        }
        if (callbacks != null) {
            callbacks.onNavigationDrawerItemSelected(position);
        }
        ((NavigationDrawerAdapter) drawerList.getAdapter()).selectPosition(position);
    }

    public void openDrawer() {
        drawerLayout.openDrawer(fragmentContainerView);
    }

    public void closeDrawer() {
        drawerLayout.closeDrawer(fragmentContainerView);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            callbacks = (NavigationDrawerCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callbacks = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, currentSelectedPosition);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    public void setUserData(String user, String email, Bitmap avatar) {
        ImageView avatarContainer = (ImageView) fragmentContainerView.findViewById(R.id.imgAvatar);
        ((TextView) fragmentContainerView.findViewById(R.id.txtUserEmail)).setText(email);
        ((TextView) fragmentContainerView.findViewById(R.id.txtUsername)).setText(user);
        avatarContainer.setImageDrawable(new RoundImage(avatar));
    }

    public View getGoogleDrawer() {
        return fragmentContainerView.findViewById(R.id.googleDrawer);
    }

    public static class RoundImage extends Drawable {
        private final Bitmap mBitmap;
        private final Paint mPaint;
        private final RectF mRectF;
        private final int mBitmapWidth;
        private final int mBitmapHeight;

        public RoundImage(Bitmap bitmap) {
            mBitmap = bitmap;
            mRectF = new RectF();
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setDither(true);
            final BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            mPaint.setShader(shader);

            mBitmapWidth = mBitmap.getWidth();
            mBitmapHeight = mBitmap.getHeight();
        }

        @Override
        public void draw(Canvas canvas) {
            canvas.drawOval(mRectF, mPaint);
        }

        @Override
        protected void onBoundsChange(Rect bounds) {
            super.onBoundsChange(bounds);
            mRectF.set(bounds);
        }

        @Override
        public void setAlpha(int alpha) {
            if (mPaint.getAlpha() != alpha) {
                mPaint.setAlpha(alpha);
                invalidateSelf();
            }
        }

        @Override
        public void setColorFilter(ColorFilter cf) {
            mPaint.setColorFilter(cf);
        }

        @Override
        public int getOpacity() {
            return PixelFormat.TRANSLUCENT;
        }

        @Override
        public int getIntrinsicWidth() {
            return mBitmapWidth;
        }

        @Override
        public int getIntrinsicHeight() {
            return mBitmapHeight;
        }

        public void setAntiAlias(boolean aa) {
            mPaint.setAntiAlias(aa);
            invalidateSelf();
        }

        @Override
        public void setFilterBitmap(boolean filter) {
            mPaint.setFilterBitmap(filter);
            invalidateSelf();
        }

        @Override
        public void setDither(boolean dither) {
            mPaint.setDither(dither);
            invalidateSelf();
        }

        public Bitmap getBitmap() {
            return mBitmap;
        }
    }
}