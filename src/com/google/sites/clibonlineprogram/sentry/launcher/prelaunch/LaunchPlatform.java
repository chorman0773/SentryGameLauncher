package com.google.sites.clibonlineprogram.sentry.launcher.prelaunch;

import java.util.jar.JarFile;

import org.w3c.dom.Document;

import com.google.sites.clibonlineprogram.sentry.launcher.prelaunch.asm.ASMClassLoader;

public class LaunchPlatform {
	public static LaunchPlatform instance;
	public final ASMClassLoader loader;
	public GameDescriptor descriptor;
	public LaunchPlatform(Document d,JarFile f) {
		instance = this;
		loader = new ASMClassLoader(f);
	}

}
