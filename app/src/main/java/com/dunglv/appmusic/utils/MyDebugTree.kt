package com.dunglv.appmusic.utils

import timber.log.Timber.DebugTree
class MyDebugTree : DebugTree() {
    override fun createStackElementTag(element: StackTraceElement): String {
        return String.format(
            "(%s:%s)#%s",
            element.fileName,
            element.lineNumber,
            element.methodName
        )
    }
}