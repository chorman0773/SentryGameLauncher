package com.google.sites.clibonlineprogram.sentry.launcher;

import java.applet.Applet;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.zip.ZipFile;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import static com.google.sites.clibonlineprogram.sentry.launcher.Main.*;
/**
 * GameLaunch is the main class for launching an handling games
 * @author Connor
 *
 */
public class GameLaunch {
	private URLClassLoader urlLoader;
	private ClassLoader cl;
	Map<String,String> values = new HashMap<>();
	public GameLaunch(){
		System.setProperty("clib.security.lockNum", "200");
	}
	
	private File sentryDir = new File("/Sentry/Games");
	private File common;
	private File games;
	private URL[] otherURLS;
	public GameFrame frame;
	public void createGame(URL gameurl, String gamename, String plt,boolean launch) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException{
		if(plt.toLowerCase().contains("windows")){
			
			if(!sentryDir.exists())runFileCreation();
			StatusBar.setText("Creating Game - 0% Complete - Creating Game Directories");
			File gameDir = new File(sentryDir,gamename);
			gameDir.mkdir();
			File binDir = new File(gameDir,"bin");
			binDir.mkdir();
			StatusBar.setText("Creating Game - 10% Complete - Creating Game Property File");
			File gameloader = new File(binDir,"sentry.launch");
			gameloader.createNewFile();
			FileOutputStream fos = new FileOutputStream(gameloader);
			InputStream io = gameurl.openStream();
			byte[] bytes = new byte[300];
			while(io.read(bytes)!=-1)fos.write(bytes);
			fos.flush();
			fos.close();
			Properties p = new Properties();
			p.load(new BufferedReader(new FileReader(gameloader)));
			StatusBar.setText("Creating Game - 25% Complete - Finding Assets");
			
			List<URL> otherURL = new ArrayList<>();
			otherURLS = new URL[]{new URL("https://sites.google.com/site/clibonlineprogram/home/core-downloads/CLib.jar?attredirects=0&d=1"),new URL("https://sites.google.com/site/clibonlineprogram/home/sentry/Sentry%20Game%20Engine.jar?attredirects=0&d=1"),new URL(p.getProperty("game")), new URL("http://www.2shared.com/file/mV1cc_AW/jinput.html")};
			 otherURL.addAll(Arrays.asList(otherURLS));
			 Map<URL,String> assets = new HashMap<>();
			 
			for(Object props:p.stringPropertyNames().stream().filter(s -> !s.endsWith(".type")&&!s.equals("game")&&!s.equals("game.class")).toArray())
			{
				String property = (String)props;
				String value = p.getProperty(property);
				String type = p.getProperty(property+".type", "application/java-archive");
				if(property.equals("sentry.auth.authlib.class"))
					values.put("auth.authlib.class", value);
				else if(property.equals("sentry.save.authlib.class"))
					values.put("save.authlib.class", value);
				else if(type.endsWith("/java-archive")||type.endsWith(".jar")||type.endsWith("jar"))
					otherURL.add(new URL(value));
				else if(type.endsWith("value")||type.endsWith("string"))
					values.put(property,value);
				else
					assets.put(new URL(value),value+mimeToExtension(type));
			}
			StatusBar.setText("Creating Game - 45% Complete - Building ClassLoader");
			values.put("game.class", p.getProperty("game.class"));
			otherURLS = otherURL.toArray(otherURLS);
			urlLoader = new URLClassLoader(otherURLS);
			downloadAssetsByProperties(assets,gameDir);
			StatusBar.setText("Creating Game - 100% Complete - Launching Game");
			try(PrintWriter pw = new PrintWriter(new FileWriter(games))){
				pw.println(gamename);
			}
			if(launch) launchGame(gamename);
			
		}else if(plt.toLowerCase().contains("osx")){
			JOptionPane.showMessageDialog(null, "I am sorry but at the moment your Operating System is unsupported please send an email containing the name and version of your operating system as well as your java version and datamodel to chorman64@gmail.com. I will look into adding your OS to the Sentry Launcher Soon. Sorry for the Inconvenience.", "OS Unsupported", JOptionPane.ERROR_MESSAGE);
		}
		else if(plt.toLowerCase().contains("linux")){
			JOptionPane.showMessageDialog(null, "I am sorry but at the moment your Operating System is unsupported please send an email containing the name and version of your operating system as well as your java version and datamodel to chorman64@gmail.com. I will look into adding your OS to the Sentry Launcher Soon. Sorry for the Inconvenience.", "OS Unsupported", JOptionPane.ERROR_MESSAGE);
		}
		else {
			JOptionPane.showMessageDialog(null, "I am sorry but at the moment your Operating System is unsupported please send an email containing the name and version of your operating system as well as your java version and datamodel to chorman64@gmail.com. I will look into adding your OS to the Sentry Launcher Soon. Sorry for the Inconvenience.", "OS Unsupported", JOptionPane.ERROR_MESSAGE);
		}
	}

	@SuppressWarnings("unchecked")
	public void launchGame(String gamename) throws FileNotFoundException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		StatusBar.setText("Launching Game "+gamename+"- Locating Jar");
		File gameDir = new File(sentryDir,"/"+gamename);
		if(otherURLS == null){
			
			File gameloader = new File(gameDir,"/bin/sentry.launch");
			Properties p = new Properties();
			p.load(new BufferedReader(new FileReader(gameloader)));
			List<URL> otherURL = new ArrayList<>();
			otherURLS = new URL[]{new URL("https://sites.google.com/site/clibonlineprogram/home/core-downloads/CLib.jar?attredirects=0&d=1"),new URL("https://sites.google.com/site/clibonlineprogram/home/sentry/SentryEngine.jar?attredirects=0&d=1"),new URL(p.getProperty("game")), new URL("http://www.2shared.com/file/mV1cc_AW/jinput.html")};
			 otherURL.addAll(Arrays.asList(otherURLS));
			 
			for(Object props:p.stringPropertyNames().stream().filter(s -> !s.endsWith(".type")&&!s.equals("game")&&!s.equals("game.class")).toArray())
			{
				String property = (String)props;
				String value = p.getProperty(property);
				String type = p.getProperty(property+".type", "application/java-archive");
				if(type.endsWith("/java-archive")||type.endsWith(".jar")||type.endsWith("jar"))
					otherURL.add(new URL(value));
				else if(type.endsWith("value")||type.endsWith("string"))
					values.put(property,value);
			}
			values.put("game.class", p.getProperty("game.class"));
			otherURLS = otherURL.toArray(otherURLS);
			urlLoader = new URLClassLoader(otherURLS);
		}
		Class<?> cl = Class.forName(values.get("game.class"),true,urlLoader);
		Class<?> game = Class.forName("com.google.sites.clibonlineprogram.sentry.GameBasic",true,urlLoader);
		if(!game.isAssignableFrom(cl)||!game.isAnnotationPresent((Class<? extends Annotation>)
		Class.forName("com.google.sites.clibonlineprogram.sentry.annotation.Game")))
			throw new IllegalArgumentException("Game Class not Valid, either Corrupt or invalid sentry.launch file, contact Connor, or the games creator for assistance\r\nError in loading, Not a Valid Game Class, possibly a missing annotation. Class Must"
					+ "be a Subclass of com.google.sites.clibonlineprogram.sentry.GameBasic, and possess the @Game Annotation. ");
		Applet gameplt = (Applet) cl.newInstance();
		frame = new GameFrame(gamename,gameplt, gameDir+"/resources", values);
		try {
			Method m = game.getMethod("getLauncherParameters", Map.class);
			m.setAccessible(true);
			m.invoke(cl, values);
		} catch (NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void runFileCreation() throws IOException {
		StatusBar.setText("Booting Sentry Launcher - Creating Assets");
		sentryDir = new File("/Sentry");
		sentryDir.mkdir();
		common = new File(sentryDir, "common");
		common.mkdir();
		games = new File(common,"games.sentry");
		games.createNewFile();
		
	}

	private String mimeToExtension(String type) {
		if(type.contains("."))return type;
		if(type.contains("/")){
			if(type.equalsIgnoreCase("video/x-ms-asf"))return ".avi";
			if(type.equalsIgnoreCase("application/octet-stream"))return ".bin";
			if(type.equalsIgnoreCase("application/java"))return ".class";
			if(type.equalsIgnoreCase("text/java"))return ".java";
			if(type.equalsIgnoreCase("text/javascript"))return ".js";
			if(type.equalsIgnoreCase("text/c"))return ".c";
			if(type.equalsIgnoreCase("text/h"))return ".h";
			if(type.equalsIgnoreCase("image/gif"))return ".gif";
			if(type.equalsIgnoreCase("application/x-gzip"))return ".gz";
			if(type.equalsIgnoreCase("image/jpeg"))return ".jpg";
			if(type.equalsIgnoreCase("video/mpeg"))return ".mpg";
			if(type.equalsIgnoreCase("application/vnd.ms-powerpoint"))return ".ppt";
			if(type.equalsIgnoreCase("text/plain"))return ".txt";
			if(type.equalsIgnoreCase("application/zip"))return ".zip";
			if(type.equalsIgnoreCase("application/x-rar-compressed"))return ".rar";
			if(type.equalsIgnoreCase("application/x-shockwave-flash"))return ".swf";
			if(type.equalsIgnoreCase("application/java-serialized-object")||type.equalsIgnoreCase("application/save-file"))return "."+values.getOrDefault("saveType",".sav");
			else throw new IllegalArgumentException("Mime Type Error, unsupported Mime Type"+type);
		}
		return "."+type;
	}

	private void downloadAssetsByProperties(Map<URL,String> assets, File gameDir) throws FileNotFoundException, IOException {
		StatusBar.setText("Creating Game - 55% Complete - Downloading Assets");
		for(URL u:assets.keySet()){
			StatusBar.setText("Creating Game - 55% Complete - Downloading File -resources/"+assets.get(u));
			InputStream i = u.openStream();
			File f = new File(gameDir+"/resources/"+assets.get(u));
			if(!f.exists())f.createNewFile();
			try(FileOutputStream out = new FileOutputStream(f)){
				int b;
				while(( b = i.read())!=-1){//byte at a time is better for compressed and serialized then block movement.
					//requires a straight internet connection
					out.write(b);
				}
			}
		}
		
	}

}
