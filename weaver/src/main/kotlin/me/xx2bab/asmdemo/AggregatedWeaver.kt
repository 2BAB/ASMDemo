package me.xx2bab.asmdemo

import org.apache.commons.io.IOUtils
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.util.jar.JarFile
import java.util.jar.JarOutputStream
import java.util.zip.ZipEntry

fun main() {
    AggregatedWeaver().process()
}

class AggregatedWeaver {

    private val weavers = setOf(Weaver01AddRemoveFieldAndMethod(), Weaver02ReplaceNewThread())

    fun process() {
        DebugFlags.processSingleClass = false
//        DebugFlags.tempClassDir = "./app/build/libs/app-decompiled/me/xx2bab/asmdemo/"

        extractJarWhenAdded(File("./app/build/libs/app-1.0-SNAPSHOT.jar"))
    }

    private fun onProcess(className:String, inputStream: InputStream): ByteArray {
        val weaver = weavers.firstOrNull { className.contains(it.getClassName()) } ?: return IOUtils.toByteArray(inputStream)
        return weaver.onProcess(inputStream)
    }

    private fun extractJarWhenAdded(jarInput: File) {
        val jar = JarFile(jarInput)
        val enumeration = jar.entries()
        val tmpFile = File(jarInput.parent + File.separator
                + "classes_modified_${jarInput.nameWithoutExtension}.jar")
        if (tmpFile.exists()) {
            tmpFile.delete()
        }
        val jarOutputStream = JarOutputStream(FileOutputStream(tmpFile))
        while (enumeration.hasMoreElements()) {
            // Extracting input class
            val element = enumeration.nextElement()
            val className = element.name
            val zipEntry = ZipEntry(className)
            val inputStream = jar.getInputStream(zipEntry)
            // Processing input class
            val processedByteArray = onProcess(className, inputStream)
            // Output
            // to a new jar's entry
            jarOutputStream.putNextEntry(zipEntry)
            jarOutputStream.write(processedByteArray)
            jarOutputStream.closeEntry()
        }
        jarOutputStream.close()
        jar.close()
    }

}