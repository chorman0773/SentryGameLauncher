package com.google.sites.clibonlineprogram.sentry.launcher.linkage;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.util.HashMap;
import java.util.Map;

import org.objectweb.asm.Handle;

public class Bootstraps {

	private static final Map<Handle,CallSite> hooks = new HashMap<>();
	private static final Lookup l = MethodHandles.lookup();
	public Bootstraps() {
		// TODO Auto-generated constructor stub
	}

	public static void executeHook(CallSite cs, Object[] args) {
		//TODO Implement Dynamic Call Hooks.
	}

}
