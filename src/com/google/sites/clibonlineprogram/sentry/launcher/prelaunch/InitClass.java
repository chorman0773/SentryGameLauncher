package com.google.sites.clibonlineprogram.sentry.launcher.prelaunch;

public class InitClass {

	private String name;
	private ClassLoader cl;

	public InitClass(String name, ClassLoader cl) {
		this.name = name;
		this.cl = cl;
	}
	public void doLaunch() throws ClassNotFoundException{
		Class.forName(name, true, cl);
	}

}
