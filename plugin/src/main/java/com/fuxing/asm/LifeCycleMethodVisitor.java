package com.fuxing.asm;

import groovyjarjarasm.asm.MethodVisitor;
import groovyjarjarasm.asm.Opcodes;

/**
 * @author ：Create by lichunfu
 * @Date : 2020-04-06
 * Description:
 **/
public class LifeCycleMethodVisitor extends MethodVisitor {
    private  String className;
    private  String methodName;

    public LifeCycleMethodVisitor(MethodVisitor mv, String className, String methodName) {
        super(Opcodes.ASM5, mv);
        this.className = className;
        this.methodName = methodName;
    }

    @Override
    public void visitCode() {
        super.visitCode();
        System.out.println("MethodVisitor visitor------");
        mv.visitLdcInsn("TAG");
        mv.visitLdcInsn(className+"------>"+methodName);
        mv.visitMethodInsn(Opcodes.INVOKESTATIC,"android/util/Log","i","(Ljava/lang/String;Ljava/lang/String;)I",false);
        mv.visitInsn(Opcodes.POP);


    }

}
