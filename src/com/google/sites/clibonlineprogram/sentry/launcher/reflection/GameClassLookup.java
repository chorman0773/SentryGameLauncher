package com.google.sites.clibonlineprogram.sentry.launcher.reflection;

import java.lang.annotation.Annotation;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.MethodType;
import java.lang.module.Configuration;
import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleFinder;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.UUID;

import com.google.sites.clibonlineprogram.sentry.launcher.FatalLaunchException;
import com.google.sites.clibonlineprogram.sentry.launcher.prelaunch.asm.ASMClassLoader;

public class GameClassLookup {
	private Object provider;

	private static final String providerName = "getGameClass";
	private static final String providerDescriptor = "()Ljava/lang/Class;";
	private static final String gameAnnotationName = "getGameAnnotation";
	private static final String gameAnnotationDescriptor = "()Lcom/google/sites/clibonlineprogram/annotation/Game;";
	private static final String providerClassName = "com/google/sites/clibonlineprogram/GameProvider";
	private static final String sentryModuleName = "com.google.sites.clibonlineprogram.sentry";
	private static final String gameAnnotationClassName = "com/google/sites/clibonlineprogram/annotation/Game";
	private static final String gameUUIDDName = "uuid";
	private static final String gameUUIDDescriptor = "()Ljava/lang/String;";
	private static final Lookup methodLookup;
	private final Class<?> providerClass;
	private final Module sentryModule;
	private final Class<?> gameAnnotationClass;
	private MethodHandle providerHandle;


	static {
			methodLookup = MethodHandles.lookup();
	}

	public GameClassLookup(ASMClassLoader acl,UUID targetGameId) throws Throwable {
		ModuleFinder base = ModuleFinder.ofSystem();
		ModuleLayer parent = ModuleLayer.boot();
		Configuration configuration = parent.configuration().resolve(base, ModuleFinder.of(), Set.of(sentryModuleName)); // 'curious' being the name of the module
		ModuleLayer layer = parent.defineModulesWithOneLoader(configuration, acl);
		parent = layer;
		sentryModule = layer.findModule(sentryModuleName).orElse(null);
		providerClass = Class.forName(sentryModule, providerClassName);
		gameAnnotationClass = Class.forName(sentryModule,gameAnnotationClassName);
		MethodHandle getGameAnnotation = methodLookup.findVirtual(providerClass, gameAnnotationName, MethodType.fromMethodDescriptorString(gameAnnotationDescriptor, acl));
		MethodHandle getGameUUID = methodLookup.findVirtual(gameAnnotationClass, gameUUIDDName, MethodType.fromMethodDescriptorString(gameUUIDDescriptor, acl));
		providerHandle = methodLookup.findVirtual(providerClass, providerName, MethodType.fromMethodDescriptorString(providerDescriptor, acl));
		ServiceLoader<?> providers = ServiceLoader.load(providerClass, acl);
		Object currAnnotation;
		for(Object o:providers) {
			MethodHandle currGetAnnotation = getGameAnnotation.bindTo(o);
			currAnnotation = currGetAnnotation.invoke();
			MethodHandle currGetGameUUID = getGameUUID.bindTo(currAnnotation);
			String uuid = (String) currGetGameUUID.invoke();
			if(UUID.fromString(uuid).equals(targetGameId))
			{
				provider = o;
				break;
			}
		}
		if(provider ==null)
			throw new FatalLaunchException("Could not start, no game provider on module path");
		providerHandle = providerHandle.bindTo(provider);
	}


}
