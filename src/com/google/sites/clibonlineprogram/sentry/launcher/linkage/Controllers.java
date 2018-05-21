package com.google.sites.clibonlineprogram.sentry.launcher.linkage;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.MethodType;

public class Controllers {
	private static final Lookup l = MethodHandles.lookup();
	public Controllers() {
		// TODO Auto-generated constructor stub
	}
	public static MethodHandle constructorHandle(Class<?> cl) {
		try {
			return l.findConstructor(cl, MethodType.fromMethodDescriptorString("()V", cl.getClassLoader()));
		} catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException
				| TypeNotPresentException e) {
			throw new LinkageError("Can't link to Constructor");
		}
	}

}
