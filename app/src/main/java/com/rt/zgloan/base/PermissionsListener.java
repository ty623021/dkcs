package com.rt.zgloan.base;

import java.util.List;

/**
 * 6.0权限封装接口
 */
public interface PermissionsListener {
    void onGranted();

    /**
     * @param deniedPermissions 被拒绝的权限集合
     * @param isNeverAsk    是否所有被拒绝权限都勾选不再询问
     */
    void onDenied(List<String> deniedPermissions, boolean isNeverAsk);
}
