package com.absinthe.anywhere_.utils.handler;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import com.absinthe.anywhere_.R;
import com.absinthe.anywhere_.constants.Const;
import com.absinthe.anywhere_.constants.GlobalValues;
import com.absinthe.anywhere_.interfaces.OnAppDefrostListener;
import com.absinthe.anywhere_.receiver.AdminReceiver;
import com.absinthe.anywhere_.utils.CommandUtils;
import com.absinthe.anywhere_.utils.ToastUtil;
import com.blankj.utilcode.util.AppUtils;
import com.catchingnow.delegatedscopeclient.DSMClient;
import com.catchingnow.icebox.sdk_client.IceBox;

import java.util.List;

public class DefrostHandler {

    public static boolean defrost(@NonNull Context context, @NonNull String packageName, OnAppDefrostListener listener) {
        ToastUtil.makeText(R.string.toast_defrosting);

        switch (GlobalValues.sDefrostMode) {
            case Const.DEFROST_MODE_DSM:
                return defrostWithDelegatedScopeManager(context, packageName, listener);
            case Const.DEFROST_MODE_ICEBOX_SDK:
                return defrostWithIceBoxSDK(context, packageName, listener);
            case Const.DEFROST_MODE_DPM:
                return defrostWithDevicePolicyManager(context, packageName, listener);
            case Const.DEFROST_MODE_ROOT:
                return defrostWithRoot(packageName, listener);
            case Const.DEFROST_MODE_SHIZUKU:
                return defrostWithShizuku(packageName, listener);
        }
        return false;
    }

    private static boolean defrostWithDelegatedScopeManager(@NonNull Context context, @NonNull String packageName, OnAppDefrostListener listener) {

        if (DSMClient.getOwnerSDKVersion(context) < DSMClient.SDK_VERSION) return false;
        List<String> scopes = DSMClient.getDelegatedScopes(context);
        if (!scopes.contains(DevicePolicyManager.DELEGATION_PACKAGE_ACCESS)) return false;

        DevicePolicyManager devicePolicyManager = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);

        if (devicePolicyManager != null) {
            new Handler().post(() -> {
                devicePolicyManager.setApplicationHidden(null, packageName, false);
                new Handler(Looper.getMainLooper()).post(listener::onAppDefrost);
            });
        } else {
            return false;
        }

        return true;
    }

    private static boolean defrostWithIceBoxSDK(@NonNull Context context, @NonNull String packageName, OnAppDefrostListener listener) {
        if (AppUtils.getAppInfo("com.catchingnow.icebox") == null) return false;

        IceBox.WorkMode workMode = IceBox.queryWorkMode(context);
        if (workMode == IceBox.WorkMode.MODE_NOT_AVAILABLE) return false;

        new Handler().post(() -> {
            IceBox.setAppEnabledSettings(context, true, packageName);
            new Handler(Looper.getMainLooper()).post(listener::onAppDefrost);
        });

        return true;
    }

    private static boolean defrostWithDevicePolicyManager(@NonNull Context context, @NonNull String packageName, OnAppDefrostListener listener) {
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);

        if (devicePolicyManager != null && devicePolicyManager.isAdminActive(AdminReceiver.Companion.getComponentName())) {
            new Handler().post(() -> {
                devicePolicyManager.setApplicationHidden(AdminReceiver.Companion.getComponentName(), packageName, false);
                new Handler(Looper.getMainLooper()).post(listener::onAppDefrost);
            });
        } else {
            return false;
        }

        return true;
    }

    private static boolean defrostWithRoot(@NonNull String packageName, OnAppDefrostListener listener) {
        return defrostWithAbd(packageName, listener);
    }

    private static boolean defrostWithShizuku(@NonNull String packageName, OnAppDefrostListener listener) {
        return defrostWithAbd(packageName, listener);
    }

    private static boolean defrostWithAbd(@NonNull String packageName, OnAppDefrostListener listener) {
        String cmd = "pm enable " + packageName;
        String result = CommandUtils.execAdbCmd(cmd);

        if (result.equals("Package " + packageName + " new state: enabled")) {
            listener.onAppDefrost();
            return true;
        } else {
            return false;
        }
    }
}