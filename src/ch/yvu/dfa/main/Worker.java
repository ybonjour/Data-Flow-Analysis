package ch.yvu.dfa.main;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

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
		final String html = runAnalysis();
		final BufferedImage image = createImage();
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				frame.updateOutput(html, image);
			}
		});
	}
	
	private String runAnalysis(){
		SimpleDOTParser parser = new SimpleDOTParser(this.input, new ExpressionParser());
		ControlflowGraph graph;
		try{
			graph = parser.parse();
		} catch(FormatException e){
			throw new RuntimeException(e);
		}

		DataFlowAnalysis analysis = new DataFlowAnalysis(graph, this.strategy);
		return analysis.analyse();
	}
	
	private BufferedImage createImage(){
		GraphViz graphViz = new GraphViz();
		byte[] imageBytes = graphViz.getGraph(this.input, "png");
		ByteArrayInputStream bais = new ByteArrayInputStream(imageBytes);
		BufferedImage buffImage;
		try {
			buffImage = ImageIO.read(bais);
		} catch (IOException e) {
			throw new RuntimeException();
		}
		
		return buffImage;
	}
}
