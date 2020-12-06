package me.xx2bab.asmdemo

import org.objectweb.asm.*
import org.objectweb.asm.ClassWriter.COMPUTE_MAXS
import org.objectweb.asm.Opcodes.*
import org.objectweb.asm.commons.AdviceAdapter
import java.io.File
import java.io.InputStream

fun main() {
    Weaver02ReplaceNewThread().process()
}

class Weaver02ReplaceNewThread : BaseWeaver() {

    override fun getClassName(): String {
        return "JavaTest02ReplaceNewThread.class"
    }

    override fun onProcess(inputStream: InputStream): ByteArray {
        val classReader = ClassReader(inputStream.readBytes())
        val classWriter = ClassWriter(COMPUTE_MAXS)
        val classVisitor = object : ClassVisitor(ASM9, classWriter) {

            private var tempClassName = ""

            override fun visit(
                version: Int,
                access: Int,
                name: String?,
                signature: String?,
                superName: String?,
                interfaces: Array<out String>?
            ) {
                super.visit(version, access, name, signature, superName, interfaces)
                println("visitClass $name")
                tempClassName = name ?: ""
            }

            override fun visitInnerClass(name: String?, outerName: String?, innerName: String?, access: Int) {
                super.visitInnerClass(name, outerName, innerName, access)
                println("visitInnerClass $name")
                try {
                    // For those Thread instance which was passed Runnable or overrided run function
                    // should decide if we want to replace it by check there super class.
                    // This can be extended to all anonymous class use cases which
                        // inherits from a target class (check `innerClassReader.superName`)
                        // or implement a target interface (check `innerClassReader.interfaces`).

                    val innerClassReader = ClassReader(File(
                        DebugFlags.tempClassDir + name!!.split("/").last() + ".class").readBytes())
                    println("visitInnerClass ${innerClassReader.superName} ")
                    println("visitInnerClass ${innerClassReader.interfaces.firstOrNull()} ")
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun visitMethod(
                access: Int,
                methodName: String?,
                descriptor: String?,
                signature: String?,
                exceptions: Array<out String>?
            ): MethodVisitor {
                val sv = super.visitMethod(access, methodName, descriptor, signature, exceptions)
                return ThreadReplaceMethodVisitor(access, descriptor, signature, sv, tempClassName, methodName)
            }

            override fun visitEnd() {
                super.visitEnd()
                println("visitEnd")
            }
        }

        classReader.accept(classVisitor, ClassReader.EXPAND_FRAMES)
        return classWriter.toByteArray()
    }

}

class ThreadReplaceMethodVisitor(
    access: Int,
    descriptor: String?,
    signature: String?,
    val superVisitor: MethodVisitor,
    val className: String,
    val methodName: String?
) : AdviceAdapter(ASM9, superVisitor, access, methodName, descriptor) {

    private var found = false

    override fun visitTypeInsn(opcode: Int, type: String?) {
        println("visitTypeInsn opcode $opcode $NEW")
        println("visitTypeInsn $type")
        if (opcode == NEW && type == "java/lang/Thread") {
            found = true
            superVisitor.visitTypeInsn(NEW, "me/xx2bab/asmdemo/helper02/RecordThread")
            return
        }
        super.visitTypeInsn(opcode, type)
    }

    override fun visitMethodInsn(
        opcode: Int,
        owner: String?,
        methodName: String?,
        descriptor: String?,
        isInterface: Boolean
    ) {
        println("visitMethodInsn $methodName")
        if (found && opcode == INVOKESPECIAL && owner == "java/lang/Thread"
            && className != "me/xx2bab/asmdemo/helper02/RecordThread") {
            found = false
            superVisitor.visitMethodInsn(INVOKESPECIAL, "me/xx2bab/asmdemo/helper02/RecordThread",
                methodName, descriptor, isInterface)
            return
        }
        super.visitMethodInsn(opcode, owner, methodName, descriptor, isInterface)
    }

}