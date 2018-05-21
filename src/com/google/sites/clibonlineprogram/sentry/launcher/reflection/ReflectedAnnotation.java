package com.google.sites.clibonlineprogram.sentry.launcher.reflection;

import java.lang.annotation.Annotation;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.MethodType;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Api for reading properties of Annotations loaded dynamically.<br/>
 * Update Version 1.1: Updated to use MethodHandles instead of Reflection
 * @author Connor Horman
 *
 */
public class ReflectedAnnotation {
	private static final Lookup methodLookup = MethodHandles.lookup();
	private static final MethodType stringValueType = MethodType.methodType(String.class);
	private static final MethodType intValueType = MethodType.methodType(int.class);
	private static final MethodType doubleValueType = MethodType.methodType(double.class);
	private static final MethodType floatValueType = MethodType.methodType(float.class);
	private static final MethodType byteValueType = MethodType.methodType(byte.class);
	private static final MethodType shortValueType = MethodType.methodType(short.class);
	private Annotation a;
	private Class<? extends Annotation> cl;

	public ReflectedAnnotation(AnnotatedElement e,Class<? extends Annotation> cl) {
		a = e.getAnnotation(cl);
		this.cl = cl;

	}
	public String getStringValue(String name) throws Throwable{
		MethodHandle target = methodLookup.findVirtual(cl, name, stringValueType);
		return (String) target.invoke();
	}
	public Enum<?> getEnumValue(String name) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		Method m = cl.getDeclaredMethod(name);

		return (Enum<?>)m.invoke(a);
	}
	public Annotation getAnnotationValue(String name) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		Method m = cl.getDeclaredMethod(name);
		return (Annotation)m.invoke(a);
	}

	public int getIntValue(String name) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		Method m = cl.getDeclaredMethod(name);
		return (Integer)m.invoke(a);
	}
	public long getLongValue(String name) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		Method m = cl.getDeclaredMethod(name);
		return (Long)m.invoke(a);
	}
	public short getShortValue(String name) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		Method m = cl.getDeclaredMethod(name);
		return (Short)m.invoke(a);
	}
	public byte getByteValue(String name) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		Method m = cl.getDeclaredMethod(name);
		return (Byte)m.invoke(a);
	}
	public float getFloatValue(String name) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		Method m = cl.getDeclaredMethod(name);
		return (Float)m.invoke(a);
	}
	public double getDoubleValue(String name) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		Method m = cl.getDeclaredMethod(name);
		return (Double)m.invoke(a);
	}

	public Class<?> getClassValue(String name) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		Method m = cl.getDeclaredMethod(name);
		return (Class<?>)m.invoke(a);
	}

	public Object getArrayValue(String name) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		Method m = cl.getDeclaredMethod(name);
		return m.invoke(a);
	}

	public boolean getBooleanValue(String name) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		Method m = cl.getDeclaredMethod(name);
		return (Boolean) m.invoke(a);
	}
}
