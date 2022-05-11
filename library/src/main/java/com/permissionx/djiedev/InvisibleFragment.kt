package com.permissionx.djiedev

import android.content.pm.PackageManager
import androidx.fragment.app.Fragment

// P677 typealias关键字可以用于给任意类型指定一个别名，比如这里把(Boolean, List<String>) -> Unit的别名指定成了PermissionCallback
// 这样就可以使用PermissionCallback来替代所有使用(Boolean, List<String>) -> Unit的地方，从而让代码变得更加简洁易懂
typealias PermissionCallback = (Boolean, List<String>) -> Unit

class InvisibleFragment : Fragment() {
    private var callback: PermissionCallback? = null

    /**
     * vararg 关键字代表可变长度
     */
    fun requestNow(cb: PermissionCallback, vararg permissions: String) {
        callback = cb
        requestPermissions(permissions, 1)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 1) {
            val deniedList = ArrayList<String>()
            for ((index, result) in grantResults.withIndex()) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    deniedList.add(permissions[index])
                }
            }

            val allGranted = deniedList.isEmpty()
            callback?.let {
                it(allGranted, deniedList)
            }
        }
    }
}