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
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;

import com.android.systemui.R;
import com.android.systemui.statusbar.phone.QuickSettingsController;
import com.android.systemui.statusbar.phone.QuickSettingsContainerView;

public class AlarmTile extends QuickSettingsTile{

    private boolean enabled = false;
    public static QuickSettingsTile mInstance;

    public static QuickSettingsTile getInstance(Context context, LayoutInflater inflater,
            QuickSettingsContainerView container, final QuickSettingsController qsc, Handler handler) {
        if (mInstance == null) mInstance = new AlarmTile(context, inflater, container, qsc, handler);
        return mInstance;
    }

    public AlarmTile(Context context, LayoutInflater inflater,
            QuickSettingsContainerView container,
            QuickSettingsController qsc, Handler handler) {
        super(context, inflater, container, qsc);

        mDrawable = R.drawable.ic_qs_alarm_on;
        String nextAlarmTime = Settings.System.getString(mContext.getContentResolver(), Settings.System.NEXT_ALARM_FORMATTED);
        if(nextAlarmTime != null){
            mLabel = nextAlarmTime;
        }

        mOnClick = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName(
                        "com.android.deskclock",
                        "com.android.deskclock.AlarmClock"));
                startSettingsActivity(intent);
            }
        };
        qsc.registerAction(Intent.ACTION_ALARM_CHANGED, this);
        qsc.registerObservedContent(Settings.System.getUriFor(
                Settings.System.NEXT_ALARM_FORMATTED), this);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        onAlarmChanged(intent);
    }

    @Override
    public void onChangeUri(ContentResolver resolver, Uri uri) {
        onNextAlarmChanged();
    }

    void onAlarmChanged(Intent intent) {
        enabled = intent.getBooleanExtra("alarmSet", false);
        updateQuickSettings();
    }

    void onNextAlarmChanged() {
        mLabel = Settings.System.getString(mContext.getContentResolver(),
                Settings.System.NEXT_ALARM_FORMATTED);
        updateQuickSettings();
    }

    @Override
    void updateQuickSettings() {
        mTile.setVisibility(enabled ? View.VISIBLE : View.GONE);
        super.updateQuickSettings();
    }

}
