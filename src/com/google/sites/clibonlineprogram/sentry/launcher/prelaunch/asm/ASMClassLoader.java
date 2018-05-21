package com.google.sites.clibonlineprogram.sentry.launcher.prelaunch.asm;

import java.io.IOException;
import java.io.InputStream;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.google.sites.clibonlineprogram.sentry.GameBasic;
import com.google.sites.clibonlineprogram.sentry.annotation.ForceLoading;

public class ASMClassLoader extends ClassLoader {

	private Set<Class<?>> classSet = new TreeSet<>((c1,c2)->c1.getName().compareTo(c2.getName()));
	private Map<String,Class<?>> classes = new TreeMap<>();
	private Map<String,URL> resources = new TreeMap<>();
	private List<JarFile> jars;
	private ASMInstrumentationImpl instrument;

	private class JarConnection extends URLConnection{
		private JarEntry target;
		private JarFile file;
		protected JarConnection(URL arg0,JarFile file,JarEntry target) {
			super(arg0);
			this.target = target;
			this.file = file;
		}

		@Override
		public void connect() throws IOException {
			file.getInputStream(target);//Test for closed

		}

		/* (non-Javadoc)
		 * @see java.net.URLConnection#getInputStream()
		 */
		@Override
		public InputStream getInputStream() throws IOException {
			// TODO Auto-generated method stub
			return file.getInputStream(target);
		}

	}

	private class JarStreamHandler extends URLStreamHandler{
		private JarEntry target;
		private JarFile file;
		@Override
		protected URLConnection openConnection(URL arg0) throws IOException {
			// TODO Auto-generated method stub
			return new JarConnection(arg0,file,target);
		}
		protected JarStreamHandler(JarEntry e,JarFile file) {
			this.target = e;
			this.file = file;
		}

	}

	private class ASMInstrumentationImpl implements Instrumentation{
		private List<ClassFileTransformer> transformers = new ArrayList<>();
		@Override
		public void addTransformer(ClassFileTransformer arg0) {
			transformers.add(arg0);

		}

		@Override
		public void addTransformer(ClassFileTransformer arg0, boolean arg1) {
			transformers.add(arg0);

		}

		@Override
		public void appendToBootstrapClassLoaderSearch(JarFile arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void appendToSystemClassLoaderSearch(JarFile arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public Class[] getAllLoadedClasses() {
			// TODO Auto-generated method stub
			return getClasses();
		}

		@Override
		public Class[] getInitiatedClasses(ClassLoader arg0) {
			if(arg0==ASMClassLoader.this)
				return getClasses();
			return null;
		}

		@Override
		public long getObjectSize(Object arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public boolean isModifiableClass(Class<?> arg0) {
			// TODO Auto-generated method stub
			return classSet.contains(arg0);
		}

		@Override
		public boolean isModifiableModule(Module arg0) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isNativeMethodPrefixSupported() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isRedefineClassesSupported() {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public boolean isRetransformClassesSupported() {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public void redefineClasses(ClassDefinition... arg0) throws ClassNotFoundException, UnmodifiableClassException {
			for(ClassDefinition def:arg0) {
				String binName = def.getClass().getName().replace('.', '/');
				byte[] data = def.getDefinitionClassFile();
				try {

					for(ClassFileTransformer transformer:transformers)
						data = transformer.transform(ASMClassLoader.this,binName, def.getDefinitionClass(), def.getDefinitionClass().getProtectionDomain(), data);
				}catch(Exception e)
				{
					throw new RuntimeException(e);
				}
				addClass(binName,data);
			}
		}

		@Override
		public void redefineModule(Module arg0, Set<Module> arg1, Map<String, Set<Module>> arg2,
				Map<String, Set<Module>> arg3, Set<Class<?>> arg4, Map<Class<?>, List<Class<?>>> arg5) {
			// TODO Auto-generated method stub

		}

		@Override
		public boolean removeTransformer(ClassFileTransformer arg0) {
			// TODO Auto-generated method stub
			return transformers.remove(arg0);
		}

		@Override
		public void retransformClasses(Class<?>... arg0) throws UnmodifiableClassException {
			for(Class<?> def:arg0) {
				byte[] data = getClassData(def);
				String binName = def.getClass().getName().replace('.', '/');
				try {

					for(ClassFileTransformer transformer:transformers)
						 data = transformer.transform(ASMClassLoader.this,binName, def, def.getProtectionDomain(),data);
				}catch(Exception e)
				{
					throw new RuntimeException(e);
				}
				addClass(binName,data);
			}

		}

		@Override
		public void setNativeMethodPrefix(ClassFileTransformer arg0, String arg1) {
			throw new UnsupportedOperationException("Cannot preform this operation");

		}

	}

	private byte[] getClassData(Class<?> cl) {
		try {
			String clName = cl.getName().replace('.', '/')+".class";
			return this.getResourceAsStream(clName).readAllBytes();
		}catch(IOException e) {
			throw new RuntimeException(e);
		}
	}

	public ASMClassLoader(JarFile... jars) {
		this.jars = new ArrayList<>();
		this.jars.addAll(Arrays.asList(jars));
		for(JarFile jar:jars)
			loadClasses(jar);
	}

	private void loadClasses(JarFile jar) {
		Enumeration<JarEntry> entries = jar.entries();
		while(!entries.hasMoreElements())
		{
			JarEntry e  = entries.nextElement();
			if(e.isDirectory())
				continue;
			else if(e.getName().endsWith(".class"))
			{
				try {
					InputStream i = jar.getInputStream(e);
					byte[] data = i.readAllBytes();
					String name = e.getName();
					name = name.substring(0, name.length()-5);
					addClass(name,data);
				}catch(IOException ex) {}
			}
		}
	}


	/* (non-Javadoc)
	 * @see java.lang.ClassLoader#findClass(java.lang.String)
	 */
	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		if(classes.containsKey(name))
			return classes.get(name);
		return this.getParent().loadClass(name);
	}

	/* (non-Javadoc)
	 * @see java.lang.ClassLoader#findResource(java.lang.String)
	 */
	@Override
	protected URL findResource(String name) {
		JarEntry e;
		for(JarFile jar:jars)
			if((e= jar.getJarEntry(name))!=null)
				try {
					return new URL(null, "file:///"+name, new JarStreamHandler(e,jar));
				} catch (MalformedURLException e1) {
				}
		return this.getParent().getResource(name);
	}

	public Class<?> addClass(String name, byte[] data){
		Class<?> cl = super.defineClass(name, data, 0, data.length);
		classes.put(name, cl);
		classSet.remove(cl);
		classSet.add(cl);
		return cl;
	}


	public Instrumentation createInstrumentation() {
		if(instrument==null)
			instrument = new ASMInstrumentationImpl();
		return instrument;
	}
	private Class[] getClasses() {
		return classSet.stream().toArray(Class[]::new);
	}

	public void applyPatches() throws UnmodifiableClassException {
		createInstrumentation();//Ensure that an instrumentation is created
		instrument.retransformClasses(getClasses());
	}


	@SuppressWarnings("unchecked")
	public Class<? extends GameBasic> getGameClass(){
		for(Class<?> cl:classSet)
			if(cl.isAssignableFrom(GameBasic.class))
				return (Class<? extends GameBasic>) cl;
		return null;
	}
	public Set<Class<?>> getInitClasses(){
		return classSet.stream().filter(c->c.getAnnotation(ForceLoading.class)!=null).collect(TreeSet::new, TreeSet::add, TreeSet::addAll);
	}



}
