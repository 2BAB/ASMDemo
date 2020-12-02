package me.xx2bab.asmdemo

import me.xx2bab.asmdemo.DebugFlags.tempClassDir
import me.xx2bab.asmdemo.DebugFlags.processSingleClass
import java.io.File
import java.io.InputStream

abstract class BaseWeaver {

    fun process() {
        val classFile = File(tempClassDir + getClassName())
        val result = onProcess(classFile.inputStream())
        val prefix = if (processSingleClass) "Modified_" else ""
        val newFile = File(tempClassDir + prefix + getClassName())
        newFile.writeBytes(result)
    }

    abstract fun getClassName(): String

    abstract fun onProcess(inputStream: InputStream): ByteArray

}