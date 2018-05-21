package com.google.sites.clibonlineprogram.sentry.launcher.prelaunch.asm.gamespecific;

import java.util.UUID;

import com.google.sites.clibonlineprogram.sentry.launcher.FatalLaunchException;
import com.google.sites.clibonlineprogram.sentry.launcher.prelaunch.GameDescriptor;
import com.google.sites.clibonlineprogram.sentry.launcher.prelaunch.LaunchPlatform;

public class PatchPkmnSms {

	public PatchPkmnSms() {
		GameDescriptor desc = LaunchPlatform.instance.descriptor;
		if(!desc.getUuid().equals(UUID.fromString("75da4118-32a9-11e7-93ae-92361f002671")))
			throw new FatalLaunchException("Can't patch Pokemon SMS if the game is not pokemon sms");
	}

}
