package ch.yvu.dfa.main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.SwingUtilities;

import ch.yvu.dfa.controlflowgraph.ControlflowGraph;
import ch.yvu.dfa.gui.MainFrame;
import ch.yvu.dfa.parser.FormatException;
import ch.yvu.dfa.parser.dot.SimpleDOTParser;
import ch.yvu.dfa.parser.expression.ExpressionParser;
import ch.yvu.dfa.analysis.*;

public class Program {
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
	
	public void runWithGraphsFromFile(String[] filePaths){
		for(String filePath : filePaths){
			System.out.println(filePath);
			String input;
			try{
				input = readFromFile(filePath);
			} catch(IOException e){
				throw new RuntimeException(e);
			}
			
			SimpleDOTParser parser = new SimpleDOTParser(input, new ExpressionParser());
			ControlflowGraph graph;
			try{
				graph = parser.parse();
			} catch(FormatException e){
				throw new RuntimeException(e);
			}
			
			DataFlowAnalysis analysis = new DataFlowAnalysis(graph, new ReachingDefinitionsStrategy());
			analysis.analyse();
		}
	}
	
	private static String readFromFile(String filePath) throws IOException{
        StringBuffer fileData = new StringBuffer(1000);
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        char[] buf = new char[1024];
        int numRead=0;
        while((numRead=reader.read(buf)) != -1){
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
            buf = new char[1024];
        }
        reader.close();
        return fileData.toString();
    }
}
