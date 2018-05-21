package com.google.sites.clibonlineprogram.sentry.launcher;

import java.security.Permission;
import java.util.Arrays;

public class Sandbox extends SecurityManager {
	 private boolean enableGenericNetworking;
	 private boolean enableGenericIO;
	 private String[] networkingWhitelist = new String[256];
	 private String[] fileWhitelist = new String[256];
	 private String gameFolder;
	 private boolean allowsNatives;
	 private String[] libraryWhitelist = new String[256];

	 private boolean allowsControl;

	 private String sentryDomain = "sentryengine.net";
	public Sandbox(String folder) {
		gameFolder = folder;
	}



	/* (non-Javadoc)
	 * @see java.lang.SecurityManager#checkConnect(java.lang.String, int)
	 */
	@Override
	public void checkConnect(String host, int port) {
		if(checkDomain(host,sentryDomain))
			return;
		else if(enableGenericNetworking)
			return;
		else if(checkAllDomains(host,networkingWhitelist))
			return;
		throw new SecurityException("Sandboxing: illegal domain");
	}
	private static boolean checkDomain(String host, String domain) {
		if(host.equals(domain))
			return true;
		else if(host.matches("\\w+\\."+domain+"(\\\\\\w+)?"))
			return true;
		return false;
	}

	private static boolean checkAllDomains(String host,String[] domainWhitelist) {
		for(String h:domainWhitelist)
			if(checkDomain(host,h))
				return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see java.lang.SecurityManager#checkLink(java.lang.String)
	 */
	@Override
	public void checkLink(String lib) {
		if(lib=="sentry/native/serial")
			return;
		else if(allowsNatives)
			return;
		else if(Arrays.binarySearch(libraryWhitelist, lib, String.CASE_INSENSITIVE_ORDER)!=-1)
			return;
		throw new SecurityException("Sandbox: illegal native library");

	}
	/* (non-Javadoc)
	 * @see java.lang.SecurityManager#checkRead(java.lang.String)
	 */
	@Override
	public void checkRead(String file) {
		if(file.startsWith(gameFolder))
			return;
		else if(enableGenericIO)
			return;
		else if(checkFiles(file,fileWhitelist))
			return;
		throw new SecurityException("Sandbox: illegal IO read");
	}
	private boolean checkFiles(String file, String[] whitelist) {
		for(String s:whitelist)
			if(file.startsWith(s))
				return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see java.lang.SecurityManager#checkSecurityAccess(java.lang.String)
	 */
	@Override
	public void checkSecurityAccess(String target) {
		// TODO Auto-generated method stub
		super.checkSecurityAccess(target);
	}
	/* (non-Javadoc)
	 * @see java.lang.SecurityManager#checkWrite(java.lang.String)
	 */
	@Override
	public void checkWrite(String file) {
		checkRead(file);
	}

	/* (non-Javadoc)
	 * @see java.lang.SecurityManager#checkPermission(java.security.Permission)
	 */
	@Override
	public void checkPermission(Permission perm) {
		if(perm.getName().equals("setSecurityManager")&&!allowsControl)
			throw new SecurityException("Sandboxing: Illegal attempt to override the SecurityManager");
		else if(perm.getName().equals("sentry.game.totalcontrol")&&!allowsControl)
			throw new SecurityException("Sandboxing: Illegal attempt to gain control of the application");
		super.checkPermission(perm);

	}



}
