package com.google.sites.clibonlineprogram.sentry.launcher.prelaunch.asm;

import java.io.IOException;
import java.io.InputStream;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.google.sites.clibonlineprogram.sentry.launcher.prelaunch.LaunchPlatform;

public class SentryPatch {

	private ClassReader gameBasicR;
	private ClassWriter gameBasicW;

	public SentryPatch(ClassLoader generic) throws IOException {
		InputStream gameBasicClass = generic.getResourceAsStream("com/google/sites/clibonlineprogram/sentry/GameBasic.class");
		gameBasicR = new ClassReader(gameBasicClass);
		gameBasicW = new ClassWriter(gameBasicR,ClassWriter.COMPUTE_FRAMES);
	}
	public void patchIsWindowed(){
		MethodVisitor isWindowed = gameBasicW.visitMethod(Opcodes.ACC_PROTECTED|Opcodes.ACC_FINAL, "isWindowed", "()Z", null, null);
		isWindowed.visitCode();
		Label l0 = new Label();
		isWindowed.visitLabel(l0);
		isWindowed.visitLineNumber(194, l0);
		isWindowed.visitLdcInsn(1);
		isWindowed.visitInsn(Opcodes.IRETURN);
		Label l1 = new Label();
		isWindowed.visitLabel(l1);
		isWindowed.visitLocalVariable("this", "Lcom/google/sites/clibonlineprogram/sentry/GameBasic;", null, l0, l1, 0);
		isWindowed.visitEnd();
	}
	public void exportGameBasic(){
		byte[] bytes = gameBasicW.toByteArray();
		LaunchPlatform.instance.loader.addClass("com.google.sites.clibonlineprogram.sentry.GameBasic", bytes);
	}

	public void patchHandleTotalControl() {
		MethodVisitor handleTotalControl = gameBasicW.visitMethod(Opcodes.ACC_PRIVATE, "handleTotalControl", "()V", null, null);
		handleTotalControl.visitCode();
		Label l0 = new Label();
		handleTotalControl.visitLabel(l0);
		handleTotalControl.visitLineNumber(317, l0);
		handleTotalControl.visitVarInsn(Opcodes.ALOAD, 0);
		handleTotalControl.visitMethodInsn(Opcodes.INVOKESTATIC, "com/google/sites/clibonlineprogram/sentry/launcher/GameFrame", "handleTotalControl", "(Ljava/applet/Applet;)V", false);
		handleTotalControl.visitInsn(Opcodes.RETURN);
		Label l1 = new Label();
		handleTotalControl.visitLabel(l1);
		handleTotalControl.visitLocalVariable("this", "Lcom/google/sites/clibonlineprogram/sentry/GameBasic;", null, l0, l1, 0);
	}


}
