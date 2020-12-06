package me.xx2bab.asmdemo

import org.objectweb.asm.*
import org.objectweb.asm.ClassWriter.COMPUTE_FRAMES
import org.objectweb.asm.ClassWriter.COMPUTE_MAXS
import org.objectweb.asm.Opcodes.*
import org.objectweb.asm.util.CheckClassAdapter
import java.io.InputStream


fun main() {
    Weaver01AddRemoveFieldAndMethod().process()
}

class Weaver01AddRemoveFieldAndMethod : BaseWeaver() {

    override fun getClassName(): String {
        return "JavaTest01AddRemoveFieldAndMethod.class"
    }

    override fun onProcess(inputStream: InputStream): ByteArray {
        val classReader = ClassReader(inputStream)
        val classWriter = ClassWriter(classReader, COMPUTE_FRAMES or COMPUTE_MAXS)
        val classVisitor = object : ClassVisitor(
            ASM9,
            CheckClassAdapter(classWriter, true)
        ) {
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

        // Add a new field
        classWriter.visitField(
            Opcodes.ACC_PUBLIC,
            "newFieldName",
            "Ljava/lang/String;",
            null,
            null
        ).visitEnd()

        // Add a new method
        val mv = classWriter.visitMethod(
            Opcodes.ACC_PUBLIC,
            "create",
            "()V",
            null,
            null
        )
        mv.visitFieldInsn(
            GETSTATIC,
            "java/lang/System",
            "out",
            "Ljava/io/PrintStream;"
        )
        mv.visitLdcInsn("this is add method print!")
        mv.visitMethodInsn(
            INVOKEVIRTUAL,
            "java/io/PrintStream",
            "println",
            "(Ljava/lang/String;)V",
            false
        )
        mv.visitInsn(RETURN)
        // this code uses a maximum of two stack elements and two local
        // variables
        mv.visitMaxs(0, 0)
        mv.visitEnd()

        return classWriter.toByteArray()
    }

}