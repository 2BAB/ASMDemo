package me.xx2bab.asmdemo

import org.objectweb.asm.*
import org.objectweb.asm.Opcodes.ASM9
import java.io.File

fun main() {
    Weaver01AddFieldAndMethod().process()
}

class Weaver01AddFieldAndMethod: BaseWeaver() {

    override fun getClassName(): String {
        return "JavaTest01AddFieldAndMethod.class"
    }

    override fun onProcess(classFile: File): ByteArray {
        val classReader = ClassReader(classFile.readBytes())
        val classWriter = ClassWriter(0) // classReader, COMPUTE_MAXS or COMPUTE_FRAMES
        val classVisitor = object : ClassVisitor(ASM9, classWriter) {
            override fun visitEnd() {
                super.visitEnd()
                val fieldVisitor = visitField(
                    Opcodes.ACC_PUBLIC,
                    "newFieldName",
                    "Ljava/lang/String",
                    null,
                    null
                )
                fieldVisitor?.visitEnd()
                val methodVisitor = visitMethod(
                    Opcodes.ACC_PUBLIC,
                    "newMethodName",
                    "(ILjava/lang/String;)V",
                    null,
                    null
                )
                methodVisitor?.visitEnd()
            }
        }
        classReader.accept(classVisitor, ClassReader.SKIP_CODE or ClassReader.SKIP_DEBUG)
        return classWriter.toByteArray()
    }

}