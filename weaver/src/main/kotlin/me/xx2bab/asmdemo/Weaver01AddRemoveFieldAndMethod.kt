package me.xx2bab.asmdemo

import org.objectweb.asm.*
import org.objectweb.asm.Opcodes.ASM9
import java.io.File

fun main() {
    Weaver01AddRemoveFieldAndMethod().process()
}

class Weaver01AddRemoveFieldAndMethod: BaseWeaver() {

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

            override fun visitMethod(
                access: Int,
                name: String?,
                descriptor: String?,
                signature: String?,
                exceptions: Array<out String>?
            ): MethodVisitor? {
                if (name == "output") {
                    println("output method detected")
                    return null
                }
                return super.visitMethod(access, name, descriptor, signature, exceptions)
            }
        }
        classReader.accept(classVisitor, ClassReader.SKIP_CODE or ClassReader.SKIP_DEBUG)
        return classWriter.toByteArray()
    }

}