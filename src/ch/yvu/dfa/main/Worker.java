package ch.yvu.dfa.main;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;

import ch.yvu.dfa.analysis.AnalysisStrategy;
import ch.yvu.dfa.analysis.DataFlowAnalysis;
import ch.yvu.dfa.controlflowgraph.ControlflowGraph;
import ch.yvu.dfa.gui.GraphViz;
import ch.yvu.dfa.gui.MainFrame;
import ch.yvu.dfa.parser.FormatException;
import ch.yvu.dfa.parser.dot.SimpleDOTParser;
import ch.yvu.dfa.parser.expression.ExpressionParser;

public class Worker implements Runnable{

	private MainFrame frame;
	private String input;
	private AnalysisStrategy strategy;
	
	
	public Worker(MainFrame frame, String input, AnalysisStrategy strategy){
		this.frame = frame;
		this.input = input;
		this.strategy = strategy;
	}
	
	@Override
	public void run() {
		final String html;
		final BufferedImage image;
		try{
			html = runAnalysis();
			image = createImage();
		}catch(FormatException e){
			invokeErrorMessage("Syntax to describe graph not recognized. Use DOT Syntax.");
			return;
		} catch(IOException e){
			invokeErrorMessage("A problem occured while generationg the graph.");
			return;
		}

		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				frame.updateOutput(html, image);
			}
		});
	}
	
	private void invokeErrorMessage(final String message){
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				frame.showErrorMessage(message);
			}
		});
	}
	
	private String runAnalysis() throws FormatException{
		SimpleDOTParser parser = new SimpleDOTParser(this.input, new ExpressionParser());
		ControlflowGraph graph = parser.parse();
		DataFlowAnalysis analysis = new DataFlowAnalysis(graph, this.strategy);
		return analysis.analyse();
	}
	
	private BufferedImage createImage() throws IOException{
		Properties properties = new Properties();
		try {
			properties.load(new BufferedInputStream(new FileInputStream(Program.GRAPHVIZ_PROPERTIES_FILE)));
		} catch (IOException e){
			throw e;
		}
		
		String dotBinary = properties.getProperty(Program.GRAPHVIZ_PROPERTY_DOTBIN);
		String tempDir = properties.getProperty(Program.GRAPHVIZ_PROPERTY_TMPDIR);
		
		GraphViz graphViz = new GraphViz(dotBinary, tempDir);
		byte[] imageBytes = graphViz.getGraph(this.input, "png");
		if(imageBytes == null) throw new IOException();
		ByteArrayInputStream bais = new ByteArrayInputStream(imageBytes);
		return ImageIO.read(bais);
	}
}
