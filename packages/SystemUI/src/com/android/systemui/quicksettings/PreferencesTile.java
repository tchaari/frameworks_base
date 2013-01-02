/*
 * Copyright (C) 2012 The Android Open Source Project
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

package com.android.systemui.quicksettings;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;

import com.android.systemui.R;
import com.android.systemui.statusbar.phone.QuickSettingsContainerView;
import com.android.systemui.statusbar.phone.QuickSettingsController;

public class PreferencesTile extends QuickSettingsTile{
    public static QuickSettingsTile mInstance;

    public static QuickSettingsTile getInstance(Context context, LayoutInflater inflater,
            QuickSettingsContainerView container, final QuickSettingsController qsc, Handler handler) {
        if (mInstance == null) mInstance = new PreferencesTile(context, inflater, container, qsc);
        return mInstance;
    }

    public PreferencesTile(Context context, LayoutInflater inflater,
            QuickSettingsContainerView container, QuickSettingsController qsc) {
        super(context, inflater, container, qsc);

        mDrawable = R.drawable.ic_qs_settings;
        mLabel = mContext.getString(R.string.quick_settings_settings_label);

        mOnClick = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startSettingsActivity(android.provider.Settings.ACTION_SETTINGS);
            }
        };

        mOnLongClick = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName(
                        "com.android.settings",
                        "com.android.settings.Settings$QuickSettingsTilesActivity"));
                startSettingsActivity(intent);
                return true;
            }
        };
    }
}
