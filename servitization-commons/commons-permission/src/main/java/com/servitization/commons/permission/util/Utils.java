package com.servitization.commons.permission.util;

import com.servitization.commons.permission.http.entity.PermissionEntity;

import java.util.Arrays;

public class Utils {
    /**
     * ---------找出两个数组中是否有相同的数据-------
     */
    public static boolean compareArray(String[] permissionArray, PermissionEntity[] permissionEntity) {
        if (null == permissionArray || permissionArray.length <= 0) {
            return false;
        }
        if (null == permissionEntity || permissionEntity.length <= 0) {
            return false;
        }

        Arrays.sort(permissionArray);
        Arrays.sort(permissionEntity);
        int p1 = 0;
        int p2 = 0;
        while (p1 < permissionArray.length && p2 < permissionEntity.length) {
            if (permissionArray[p1].equals(permissionEntity[p2].getName())) {
                return true;
            } else if (permissionArray[p1].compareTo(permissionEntity[p2].getName()) < 0) {
                p1++;
            } else {
                p2++;
            }
        }
        return false;
    }

}
