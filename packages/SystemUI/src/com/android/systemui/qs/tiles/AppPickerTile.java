/*
 * Copyright (C) 2015 The Dirty Unicorns Project
 * Copyright (C) 2018 MSM-Xtended Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.systemui.qs.tiles;

import android.content.Context;
import android.content.Intent;
import android.content.ComponentName;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;

import android.service.quicksettings.Tile;
import com.android.systemui.R;
import com.android.systemui.Dependency;
import com.android.systemui.plugins.qs.QSTile.BooleanState;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.plugins.ActivityStarter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.nano.MetricsProto.MetricsEvent;


public class AppPickerTile extends QSTileImpl<BooleanState> {

    private boolean mListening;
    private final ActivityStarter mActivityStarter;

     private static final String TAG = "AppPickerTile";

    private static final Intent APP_PICKER = new Intent().setComponent(new ComponentName(
            "com.android.systemui", "com.android.systemui.liquid.apppicker.AppPickerActivity"));

    public AppPickerTile(QSHost host) {
        super(host);
	mActivityStarter = Dependency.get(ActivityStarter.class);
    }

    @Override
    public BooleanState newTileState() {
        return new BooleanState();
    }


    @Override
    public int getMetricsCategory() {
        return MetricsEvent.LIQUID;
    }

    @Override
    public CharSequence getTileLabel() {
        return mContext.getString(R.string.quick_settings_apppicker);
    }

    @Override
    protected void handleClick() {
        mHost.collapsePanels();
        startAppPicker();
        refreshState();
    }

    @Override
    protected void handleSecondaryClick() {
	    handleClick();
    }

    @Override
    public void handleLongClick() {
        handleClick();
    }

    @Override
    public Intent getLongClickIntent() {
        return null;
    }

    protected void startAppPicker() {
        mActivityStarter.postStartActivityDismissingKeyguard(APP_PICKER, 0);
    }

    @Override
    protected void handleUpdateState(BooleanState state, Object arg) {
        state.icon = ResourceIcon.get(R.drawable.ic_qs_app_picker);
        state.label = mContext.getString(R.string.quick_settings_apppicker);
    }

    public void setListening(boolean listening) {
        if (mListening == listening) return;
        mListening = listening;
    }

    @Override
    public void handleSetListening(boolean listening) {
    // Do nothing
    }
}
