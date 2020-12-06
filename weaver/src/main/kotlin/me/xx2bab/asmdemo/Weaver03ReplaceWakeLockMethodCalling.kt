package me.xx2bab.asmdemo

import org.objectweb.asm.*
import org.objectweb.asm.ClassWriter.COMPUTE_MAXS
import org.objectweb.asm.Opcodes.*
import org.objectweb.asm.commons.AdviceAdapter
import java.io.InputStream
import java.lang.StringBuilder

fun main() {
    Weaver03ReplaceWakeLockMethodCalling().process()
}

class Weaver03ReplaceWakeLockMethodCalling : BaseWeaver() {

    override fun getClassName(): String {
        return "JavaTest03ReplaceWakeLockMethodCalling.class"
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

            override fun visitMethod(
                access: Int,
                methodName: String?,
                descriptor: String?,
                signature: String?,
                exceptions: Array<out String>?
            ): MethodVisitor {
                val sv = super.visitMethod(access, methodName, descriptor, signature, exceptions)
                return WakeLockReplaceMethodVisitor(access, descriptor, signature, sv, tempClassName, methodName)
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

class WakeLockReplaceMethodVisitor(
    access: Int,
    descriptor: String?,
    signature: String?,
    val superVisitor: MethodVisitor,
    val className: String,
    val methodName: String?
) : AdviceAdapter(ASM9, superVisitor, access, methodName, descriptor) {

//    private var found = false
//
//    override fun visitTypeInsn(opcode: Int, type: String?) {
//        println("visitTypeInsn opcode $opcode $NEW")
//        println("visitTypeInsn $type")
//        if (opcode == NEW && type == "java/lang/Thread") {
//            found = true
//            superVisitor.visitTypeInsn(NEW, "me/xx2bab/asmdemo/helper02/RecordThread")
//            return
//        }
//        super.visitTypeInsn(opcode, type)
//    }

    override fun visitMethodInsn(
        opcode: Int,
        owner: String?,
        methodName: String?,
        descriptor: String?,
        isInterface: Boolean
    ) {
        println("visitMethodInsn $methodName")
        if (opcode == INVOKEVIRTUAL
            && owner == "me/xx2bab/asmdemo/helper03/WakeLock"
            && (methodName == "acquire" || methodName == "release")
            && descriptor != null
            && className != "me/xx2bab/asmdemo/helper03/WakeLockProxy"
        ) {
            val desc = StringBuilder().append(descriptor.substring(0, 1))
                .append("Lme/xx2bab/asmdemo/helper03/WakeLock;")
                .append(descriptor.substring(1, descriptor.length))
                .toString()
            superVisitor.visitMethodInsn(
                INVOKESTATIC, "me/xx2bab/asmdemo/helper03/WakeLockProxy",
                methodName, desc, false
            )
            return
        }
        super.visitMethodInsn(opcode, owner, methodName, descriptor, isInterface)
    }

}