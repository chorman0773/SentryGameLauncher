package com.google.sites.clibonlineprogram.sentry.launcher;

import java.applet.*;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.*;

public class GameFrame extends JFrame implements AppletStub, AppletContext {


	public static void handleTotalControl(Applet game) {

	}
	private Map<String, String> parameters;

	private Applet applet;

	/**
	 *
	 */
	private static final long serialVersionUID = -150859218612425520L;

	public GameFrame(String title,Applet game,String assetsDirectory, Map<String,String> parameters) {
		super(title);
		this.parameters = parameters;
		game.setStub(this);
		game.init();
		this.setSize(game.getSize());
		this.setPreferredSize(game.getSize());
		this.setResizable(false);
		this.setVisible(true);
		game.start();
		parameters.put("assets", assetsDirectory);
		applet = game;
	}

	@Override
	public AudioClip getAudioClip(URL url) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Image getImage(URL url) {
		// TODO Auto-generated method stub
		try {
			return ImageIO.read(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}

	@Override
	public Applet getApplet(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Enumeration<Applet> getApplets() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void showDocument(URL url) {
		// TODO Auto-generated method stub

	}

	@Override
	public void showDocument(URL url, String target) {
		// TODO Auto-generated method stub

	}

	@Override
	public void showStatus(String status) {
		Main.StatusBar.setText(status);

	}

	@Override
	public void setStream(String key, InputStream stream) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public InputStream getStream(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<String> getStreamKeys() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public URL getDocumentBase() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public URL getCodeBase() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getParameter(String name) {
		// TODO Auto-generated method stub
		return parameters.get(name);
	}

	@Override
	public AppletContext getAppletContext() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public void appletResize(int width, int height) {
		// TODO Auto-generated method stub

	}

	public void exit(int i) {
		this.applet.stop();
		this.applet.destroy();

	}

}
