package me.xx2bab.asmdemo

import org.objectweb.asm.*
import org.objectweb.asm.ClassWriter.COMPUTE_MAXS
import org.objectweb.asm.Opcodes.ASM9
import org.objectweb.asm.util.CheckClassAdapter
import java.io.InputStream

fun main(args: Array<String>) {
    Weaver01AddRemoveFieldAndMethod().process()
}

class Weaver01AddRemoveFieldAndMethod: BaseWeaver() {

    override fun getClassName(): String {
        return "JavaTest01AddRemoveFieldAndMethod.class"
    }

    override fun onProcess(inputStream: InputStream): ByteArray {
        val classReader = ClassReader(inputStream)
        val classWriter = ClassWriter(0) // classReader, COMPUTE_MAXS or COMPUTE_FRAMES
        val classVisitor = object : ClassVisitor(ASM9, classWriter) {
            override fun visitEnd() {
                super.visitEnd()
//                val fieldVisitor = visitField(
//                    Opcodes.ACC_PUBLIC,
//                    "newFieldName",
//                    "Ljava/lang/String;",
//                    null,
//                    null
//                )
//                fieldVisitor?.visitEnd()
//                val methodVisitor = visitMethod(
//                    Opcodes.ACC_PUBLIC,
//                    "newMethodName",
//                    "(ILjava/lang/String;)V",
//                    null,
//                    null
//                )
//                methodVisitor?.visitEnd()
            }

            override fun visitMethod(
                access: Int,
                name: String?,
                descriptor: String?,
                signature: String?,
                exceptions: Array<out String>?
            ): MethodVisitor? {
                if (name == "outputTobeRemoved") {
                    println("output method detected")
                    return null
                }
                return super.visitMethod(access, name, descriptor, signature, exceptions)
            }
        }
        classReader.accept(classVisitor, 0)
        return classWriter.toByteArray()
    }

}