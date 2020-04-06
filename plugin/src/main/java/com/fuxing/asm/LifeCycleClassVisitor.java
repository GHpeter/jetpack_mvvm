package com.fuxing.asm;


import groovyjarjarasm.asm.ClassVisitor;
import groovyjarjarasm.asm.MethodVisitor;
import groovyjarjarasm.asm.Opcodes;

/**
 * @author ：Create by lichunfu
 * @Date : 2020-04-06
 * Description:
 **/
public class LifeCycleClassVisitor  extends ClassVisitor {
    private  String className;
    private  String superName;
    public LifeCycleClassVisitor( ClassVisitor cv) {
        super(Opcodes.ASM5, cv);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        this.className=name;
        this.superName=superName;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        System.out.println("classVisitor visitorMethod name:"+name+",superName is"+superName);
        MethodVisitor mv=cv.visitMethod(access, name, descriptor, signature, exceptions);
        if (superName.equals("android/support/v7/app/AppCompatActivity")
        | superName.equals("android/app/Activity")){
            if (name.startsWith("onCreate")){
                return  new LifeCycleMethodVisitor(mv,className,name);
            }
        }
        return  mv;

    }

    @Override
    public void visitEnd() {
        super.visitEnd();
    }
}



