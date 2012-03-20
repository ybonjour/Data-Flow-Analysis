package ch.yvu.dfa.main;

import javax.swing.SwingUtilities;

import ch.yvu.dfa.gui.MainFrame;

public class Program {
	public static final String GRAPHVIZ_PROPERTIES_FILE = "graphviz.properties";
	public static final String GRAPHVIZ_PROPERTY_TMPDIR = "graphvizTempDir";
	public static final String GRAPHVIZ_PROPERTY_DOTBIN = "graphvizDOTBinary";
		
	public static void main(String[] args){
		
		
		Program p = new Program();
		//p.runWithGraphsFromFile(args);
		p.runGUI();
	}
	
	public void runGUI(){
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new MainFrame();
			}
		});
	}
}
