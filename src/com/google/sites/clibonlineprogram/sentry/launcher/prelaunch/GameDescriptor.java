package com.google.sites.clibonlineprogram.sentry.launcher.prelaunch;

import java.applet.Applet;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

import com.google.sites.clibonlineprogram.sentry.launcher.prelaunch.asm.ASMClassLoader;
import com.google.sites.clibonlineprogram.sentry.launcher.reflection.ReflectedAnnotation;

public class GameDescriptor {

	private final UUID uuid;
	private final String name;
	private final String version;
	private final long serialId;
	private final String gameId;
	private final boolean allowMods;
	private final boolean displayVersion;
	private final String[] classesToInit;
	private final String[] dependancyUrls;

	@SuppressWarnings("unchecked")
	public GameDescriptor(Class<?> cl) throws Throwable {
		ClassLoader load = cl.getClassLoader();
		Class<? extends Annotation> an = (Class<? extends Annotation>) Class.forName("com.google.sites.clibonlineprogram.sentry.annotation.Game", false, load);
		ReflectedAnnotation ra = new ReflectedAnnotation(cl,an);
		uuid = UUID.fromString(ra.getStringValue("uuid"));
		name = ra.getStringValue("name");
		version = ra.getStringValue("version");
		serialId = ra.getLongValue("serialId");
		gameId = ra.getStringValue("gameId");
		allowMods = ra.getBooleanValue("allowsMods");
		displayVersion = ra.getBooleanValue("displayVersion");
		classesToInit = (String[]) ra.getArrayValue("classesToInit");
		dependancyUrls = (String[]) ra.getArrayValue("dependancyUrls");
	}

	/**
	 * @return the uuid
	 */
	public UUID getUuid() {
		return uuid;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @return the serialId
	 */
	public long getSerialId() {
		return serialId;
	}

	/**
	 * @return the gameId
	 */
	public String getGameId() {
		return gameId;
	}

	/**
	 * @return the allowMods
	 */
	public boolean isAllowMods() {
		return allowMods;
	}

	/**
	 * @return the displayVersion
	 */
	public boolean isDisplayVersion() {
		return displayVersion;
	}

	/**
	 * @return the classesToInit
	 */
	public String[] getClassesToInit() {
		return classesToInit.clone();
	}

	/**
	 * @return the dependancyUrls
	 */
	public String[] getDependancyUrls() {
		return dependancyUrls.clone();
	}


}
