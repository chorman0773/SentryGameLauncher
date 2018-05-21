package com.google.sites.clibonlineprogram.sentry.launcher;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.net.URL;
import java.util.*;

import javax.swing.*;

public class Main extends JFrame {
	static JCheckBox launchGame = new JCheckBox("Launch Game",true);
	static JCheckBox dynamic = new JCheckBox("Dynamic Game Updates Allowed?");
	private boolean lgdefault = true;
	private boolean dyndefault = false;
	static File config;
	Properties sentry = new Properties();
	public JTextField gamename;
	public JTextField gameurl;
	public class SubFrame extends JFrame {
		/**
		 * 
		 */
		private static final long serialVersionUID = 3732964778536653763L;

		public SubFrame(){
			super("Adding Game");
			setSize(400,250);
			this.setResizable(false);
			this.setLayout(new FlowLayout(FlowLayout.CENTER));
			this.add(new JLabel("Game URL, Provided:"));
			gameurl = new JTextField();
			this.add(gameurl);
			
			this.add(launchGame);
			this.add(dynamic);
			launchGame.setSelected(lgdefault);
			dynamic.setSelected(dyndefault);
			this.add(new JLabel("Game Name:"));
			gamename = new JTextField();
			this.add(gamename);
			this.add(new JButton(new AbstractAction("Create Game"){

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						launchers[launchGame.isSelected()?n++:n].createGame(new URL(gameurl.getText()), gamename.getText(), System.getProperty("os.name"),launchGame.isSelected());
					} catch (ClassNotFoundException | InstantiationException
							| IllegalAccessException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						
					}
					finally{
						Main.this.setFocusable(true);
						SubFrame.this.dispose();
					}
					
				}
				
			}));
			this.add(new JButton(new AbstractAction("Cancel"){

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(ActionEvent e) {
					Main.this.setFocusable(true);
					SubFrame.this.dispose();
					
				}
				
			}));
		}
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = -1635141044467697693L;
	public static final JLabel StatusBar = new JLabel("done");
	public static void main(String[] args) {
		EventQueue.invokeLater(()->new Main().setVisible(true));

	}
	private GameLaunch[] launchers = new GameLaunch[10];
	private int n;
	private Main(){
		try {
			this.setExtendedState(MAXIMIZED_BOTH);
			this.setLayout(new BorderLayout());
			JPanel p = new JPanel();
			p.setSize(600, 150);
			p.add(StatusBar);
			p.add(new JButton(new AbstractAction("Add Game"){

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(ActionEvent e) {
					new SubFrame();
					Main.this.setFocusable(false);
					
				}
				
			}));
			add(p,BorderLayout.SOUTH);
			launchers[n] = new GameLaunch();
			File sentryDir = new File("/Sentry");
			if(!sentryDir.exists())
				launchers[0].runFileCreation();
			final JTextArea outArea = new JTextArea();
			outArea.setEditable(false);
			outArea.setAutoscrolls(true);
			JScrollPane outPanel = new JScrollPane(outArea);
			System.setErr(new PrintStream("dummy String"){

				/* (non-Javadoc)
				 * @see java.io.PrintStream#print(boolean)
				 */
				@Override
				public void print(boolean b) {
					print(Boolean.toString(b));
				}

				/* (non-Javadoc)
				 * @see java.io.PrintStream#print(char)
				 */
				@Override
				public void print(char c) {
					append(c);
				}

				/* (non-Javadoc)
				 * @see java.io.PrintStream#print(int)
				 */
				@Override
				public void print(int i) {
					print(Integer.toString(i));
				}

				/* (non-Javadoc)
				 * @see java.io.PrintStream#print(long)
				 */
				@Override
				public void print(long l) {
					print(Long.toString(l));
				}

				/* (non-Javadoc)
				 * @see java.io.PrintStream#print(float)
				 */
				@Override
				public void print(float f) {
					print(Float.toString(f));
				}

				/* (non-Javadoc)
				 * @see java.io.PrintStream#print(double)
				 */
				@Override
				public void print(double d) {
					print(Double.toString(d));
				}

				/* (non-Javadoc)
				 * @see java.io.PrintStream#print(char[])
				 */
				@Override
				public void print(char[] s) {
					// TODO Auto-generated method stub
					print(new String(s));
				}

				/* (non-Javadoc)
				 * @see java.io.PrintStream#print(java.lang.String)
				 */
				@Override
				public void print(String s) {
					// TODO Auto-generated method stub
					outArea.append(s);
				}

				/* (non-Javadoc)
				 * @see java.io.PrintStream#print(java.lang.Object)
				 */
				@Override
				public void print(Object obj) {
					// TODO Auto-generated method stub
					print(obj.toString());
				}

				/* (non-Javadoc)
				 * @see java.io.PrintStream#println()
				 */
				@Override
				public void println() {
					// TODO Auto-generated method stub
					print("\r\n");
				}

				/* (non-Javadoc)
				 * @see java.io.PrintStream#println(boolean)
				 */
				@Override
				public void println(boolean x) {
					// TODO Auto-generated method stub
					print(x);
					println();
				}

				/* (non-Javadoc)
				 * @see java.io.PrintStream#println(char)
				 */
				@Override
				public void println(char x) {
					// TODO Auto-generated method stub
					print(x);
					println();
				}

				/* (non-Javadoc)
				 * @see java.io.PrintStream#println(int)
				 */
				@Override
				public void println(int x) {
					// TODO Auto-generated method stub
					print(x);
					println();
				}

				/* (non-Javadoc)
				 * @see java.io.PrintStream#println(long)
				 */
				@Override
				public void println(long x) {
					// TODO Auto-generated method stub
					print(x);
					println();
				}

				/* (non-Javadoc)
				 * @see java.io.PrintStream#println(float)
				 */
				@Override
				public void println(float x) {
					// TODO Auto-generated method stub
					print(x);
					println();
				}

				/* (non-Javadoc)
				 * @see java.io.PrintStream#println(double)
				 */
				@Override
				public void println(double x) {
					// TODO Auto-generated method stub
					print(x);
					println();
				}

				/* (non-Javadoc)
				 * @see java.io.PrintStream#println(char[])
				 */
				@Override
				public void println(char[] x) {
					// TODO Auto-generated method stub
					print(x);
					println();
				}

				/* (non-Javadoc)
				 * @see java.io.PrintStream#println(java.lang.String)
				 */
				@Override
				public void println(String x) {
					// TODO Auto-generated method stub
					print(x);
					println();
				}

				/* (non-Javadoc)
				 * @see java.io.PrintStream#println(java.lang.Object)
				 */
				@Override
				public void println(Object x) {
					// TODO Auto-generated method stub
					print(x);
					println();
				}

				/* (non-Javadoc)
				 * @see java.io.PrintStream#printf(java.lang.String, java.lang.Object[])
				 */
				@Override
				public PrintStream printf(String format, Object... args) {
					print(String.format(format, args));
					return this;
				}

				/* (non-Javadoc)
				 * @see java.io.PrintStream#printf(java.util.Locale, java.lang.String, java.lang.Object[])
				 */
				@Override
				public PrintStream printf(Locale l, String format,
						Object... args) {
					this.print(String.format(l, format, args));
					return this;
				}

				/* (non-Javadoc)
				 * @see java.io.PrintStream#append(java.lang.CharSequence)
				 */
				@Override
				public PrintStream append(CharSequence csq) {
					
					return append(csq,0,csq.length());
				}

				/* (non-Javadoc)
				 * @see java.io.PrintStream#append(java.lang.CharSequence, int, int)
				 */
				@Override
				public PrintStream append(CharSequence csq, int start, int end) {
					outArea.append(csq.toString().substring(start, end));
					return this;
				}

				/* (non-Javadoc)
				 * @see java.io.PrintStream#append(char)
				 */
				@Override
				public PrintStream append(char c) {
					outArea.append(""+c);
					return this;
				}
				
			});
			System.setOut(new PrintStream("dummy String"){

				/* (non-Javadoc)
				 * @see java.io.PrintStream#print(boolean)
				 */
				@Override
				public void print(boolean b) {
					print(Boolean.toString(b));
				}

				/* (non-Javadoc)
				 * @see java.io.PrintStream#print(char)
				 */
				@Override
				public void print(char c) {
					append(c);
				}

				/* (non-Javadoc)
				 * @see java.io.PrintStream#print(int)
				 */
				@Override
				public void print(int i) {
					print(Integer.toString(i));
				}

				/* (non-Javadoc)
				 * @see java.io.PrintStream#print(long)
				 */
				@Override
				public void print(long l) {
					print(Long.toString(l));
				}

				/* (non-Javadoc)
				 * @see java.io.PrintStream#print(float)
				 */
				@Override
				public void print(float f) {
					print(Float.toString(f));
				}

				/* (non-Javadoc)
				 * @see java.io.PrintStream#print(double)
				 */
				@Override
				public void print(double d) {
					print(Double.toString(d));
				}

				/* (non-Javadoc)
				 * @see java.io.PrintStream#print(char[])
				 */
				@Override
				public void print(char[] s) {
					// TODO Auto-generated method stub
					print(new String(s));
				}

				/* (non-Javadoc)
				 * @see java.io.PrintStream#print(java.lang.String)
				 */
				@Override
				public void print(String s) {
					// TODO Auto-generated method stub
					outArea.append(s);
				}

				/* (non-Javadoc)
				 * @see java.io.PrintStream#print(java.lang.Object)
				 */
				@Override
				public void print(Object obj) {
					// TODO Auto-generated method stub
					print(obj.toString());
				}

				/* (non-Javadoc)
				 * @see java.io.PrintStream#println()
				 */
				@Override
				public void println() {
					// TODO Auto-generated method stub
					print(System.lineSeparator());
				}

				/* (non-Javadoc)
				 * @see java.io.PrintStream#println(boolean)
				 */
				@Override
				public void println(boolean x) {
					// TODO Auto-generated method stub
					print(x);
					println();
				}

				/* (non-Javadoc)
				 * @see java.io.PrintStream#println(char)
				 */
				@Override
				public void println(char x) {
					// TODO Auto-generated method stub
					print(x);
					println();
				}

				/* (non-Javadoc)
				 * @see java.io.PrintStream#println(int)
				 */
				@Override
				public void println(int x) {
					// TODO Auto-generated method stub
					print(x);
					println();
				}

				/* (non-Javadoc)
				 * @see java.io.PrintStream#println(long)
				 */
				@Override
				public void println(long x) {
					// TODO Auto-generated method stub
					print(x);
					println();
				}

				/* (non-Javadoc)
				 * @see java.io.PrintStream#println(float)
				 */
				@Override
				public void println(float x) {
					// TODO Auto-generated method stub
					print(x);
					println();
				}

				/* (non-Javadoc)
				 * @see java.io.PrintStream#println(double)
				 */
				@Override
				public void println(double x) {
					// TODO Auto-generated method stub
					print(x);
					println();
				}

				/* (non-Javadoc)
				 * @see java.io.PrintStream#println(char[])
				 */
				@Override
				public void println(char[] x) {
					// TODO Auto-generated method stub
					print(x);
					println();
				}

				/* (non-Javadoc)
				 * @see java.io.PrintStream#println(java.lang.String)
				 */
				@Override
				public void println(String x) {
					// TODO Auto-generated method stub
					print(x);
					println();
				}

				/* (non-Javadoc)
				 * @see java.io.PrintStream#println(java.lang.Object)
				 */
				@Override
				public void println(Object x) {
					// TODO Auto-generated method stub
					print(x);
					println();
				}

				/* (non-Javadoc)
				 * @see java.io.PrintStream#printf(java.lang.String, java.lang.Object[])
				 */
				@Override
				public PrintStream printf(String format, Object... args) {
					print(String.format(format, args));
					return this;
				}

				/* (non-Javadoc)
				 * @see java.io.PrintStream#printf(java.util.Locale, java.lang.String, java.lang.Object[])
				 */
				@Override
				public PrintStream printf(Locale l, String format,
						Object... args) {
					this.print(String.format(l, format, args));
					return this;
				}

				/* (non-Javadoc)
				 * @see java.io.PrintStream#append(java.lang.CharSequence)
				 */
				@Override
				public PrintStream append(CharSequence csq) {
					
					return append(csq,0,csq.length());
				}

				/* (non-Javadoc)
				 * @see java.io.PrintStream#append(java.lang.CharSequence, int, int)
				 */
				@Override
				public PrintStream append(CharSequence csq, int start, int end) {
					outArea.append(csq.toString().substring(start, end));
					return this;
				}

				/* (non-Javadoc)
				 * @see java.io.PrintStream#append(char)
				 */
				@Override
				public PrintStream append(char c) {
					outArea.append(""+c);
					return this;
				}
				
			});
			this.add(outPanel,BorderLayout.CENTER);
			JMenuBar jmb = new JMenuBar();
			this.setJMenuBar(jmb);
			JMenu file = new JMenu("Application");
			jmb.add(file);
			JMenu games = new JMenu("Launch Existing Game");
			try(BufferedReader br = new BufferedReader(new FileReader(new File(sentryDir,"/common/games.sentry")))){
				Properties props = new Properties();
				if(props.stringPropertyNames().size()!=0){
					jmb.add(games);
				}
			for(final String prop:props.stringPropertyNames()){
				games.add(new JMenuItem(new AbstractAction(prop){

					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							launchers[n++].launchGame(prop);
						} catch (ClassNotFoundException
								| InstantiationException
								| IllegalAccessException | IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
					}
					
				}));
			}
			file.add(new JMenuItem(new AbstractAction("Exit"){

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
				
			}));
			this.setDefaultCloseOperation(EXIT_ON_CLOSE);
			}
		} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

}
