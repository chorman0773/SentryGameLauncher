package com.google.sites.clibonlineprogram.sentry.launcher;

import java.io.PrintStream;
import java.io.PrintWriter;

public class FatalLaunchException extends RuntimeException {

	public static final int ASM_ERROR = 1;
	public static final int GAME_ERROR = 2;
	public static final int SYSTEM_ERROR = 3;
	public static final int OTHER_ERROR = 4;
	public static final int GAME_ANNOTATION_ERROR = 5;
	private int type;
	private String msg;

	public FatalLaunchException(String msg) {
		this(msg,OTHER_ERROR);
		// TODO Auto-generated constructor stub
	}
	public FatalLaunchException(String msg,int type){
		this.type = type;
		this.msg = msg;
	}
	/* (non-Javadoc)
	 * @see java.lang.Throwable#printStackTrace()
	 */
	@Override
	public void printStackTrace() {
		printStackTrace(System.err);
	}
	/* (non-Javadoc)
	 * @see java.lang.Throwable#printStackTrace(java.io.PrintStream)
	 */
	@Override
	public void printStackTrace(PrintStream s) {
		s.println("A fatal error occured, preventing the game from launching");
		switch(type){
		case ASM_ERROR:
			s.println("There was an Error with the ASM which caused irrepreable damage to the launch");
		}
	}
	/* (non-Javadoc)
	 * @see java.lang.Throwable#printStackTrace(java.io.PrintWriter)
	 */
	@Override
	public void printStackTrace(PrintWriter s) {
		// TODO Auto-generated method stub
		super.printStackTrace(s);
	}


}
