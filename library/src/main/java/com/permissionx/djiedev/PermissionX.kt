package com.permissionx.djiedev

import androidx.fragment.app.FragmentActivity

object PermissionX {
    private const val TAG = "InvisibleFragment"

    fun request(
        activity: FragmentActivity,
        vararg permissions: String,
        callback: PermissionCallback
    ) {
        val fragmentManager = activity.supportFragmentManager
        // 用于获取传入的Activity中是否已经包含了指定TAG的Fragment
        val existedFragment = fragmentManager.findFragmentByTag(TAG)
        val fragment = if (existedFragment != null) { // 如果已经包含则直接使用该Fragment
            existedFragment as InvisibleFragment
        } else { // 不包含就创建一个新的InvisibleFragment实例，并将它添加到Activity中
            val invisibleFragment = InvisibleFragment()
            // 这里必须调用commitNow()方法，不能调用commit()方法，因为commit()方法不会立即执行添加操作，因而无法保证下一行代码能执行成功
            fragmentManager.beginTransaction().add(invisibleFragment, TAG).commitNow()
            invisibleFragment
        }
        // permissions参数在这里实际上是一个数组，无法直接将它传递给另外一个接收可变长度参数的方法，所以在permissions参数前面加上一个*
        // 代表将一个数组转换成可变长度参数传递过去
        fragment.requestNow(callback, *permissions)
    }
}