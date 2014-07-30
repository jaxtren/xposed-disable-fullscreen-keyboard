/*
 * Disable Fullscreen Keyboard
 *
 * Xposed module for android to disable keyboard and text input field
 * covering the whole screen when the device is in landscape orientation
 *
 * Copyright (c) 2014 Shubhang Rathore
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.shubhangrathore.xposed.disablefullscreenkeyboard;

import android.view.Window;

import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

/**
 * Created by Shubhang Rathore on 29/7/2014.
 */
public class XposedDisableFullscreenKeyboard implements IXposedHookZygoteInit {

    public static final String CLASS_INPUT_METHOD_SERVICE = "android.inputmethodservice.InputMethodService";
    public static String MODULE_PATH = null;

    @Override
    public void initZygote(IXposedHookZygoteInit.StartupParam startupParam) throws Throwable {

        MODULE_PATH = startupParam.modulePath;

        final Class<?> mInputMethodServiceClass = XposedHelpers.findClass(CLASS_INPUT_METHOD_SERVICE, null);

        XposedHelpers.findAndHookMethod(mInputMethodServiceClass, "onEvaluateFullscreenMode", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(final MethodHookParam methodHookParam) throws Throwable {
                methodHookParam.setResult(false);
            }
        });

        XposedHelpers.findAndHookMethod(mInputMethodServiceClass, "isFullscreenMode", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(final MethodHookParam methodHookParam) throws Throwable {
                methodHookParam.setResult(false);
            }
        });

        XposedHelpers.findAndHookMethod(mInputMethodServiceClass, "isExtractViewShown", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(final MethodHookParam methodHookParam) throws Throwable {
                methodHookParam.setResult(false);
            }
        });

        XposedHelpers.findAndHookMethod(mInputMethodServiceClass, "onConfigureWindow", Window.class, boolean.class, boolean.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(final MethodHookParam methodHookParam) throws Throwable {
                methodHookParam.args[1] = false;
            }
        });
    }
}
