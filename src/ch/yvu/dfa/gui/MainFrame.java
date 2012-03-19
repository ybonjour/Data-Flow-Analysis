package ch.yvu.dfa.gui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Component;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.JEditorPane;
import javax.swing.text.Document;
import javax.swing.BoxLayout;

import ch.yvu.dfa.analysis.AvailableExpressionStrategy;
import ch.yvu.dfa.analysis.LiveVariableStrategy;
import ch.yvu.dfa.analysis.ReachingDefinitionsStrategy;
import ch.yvu.dfa.analysis.AnalysisStrategy;
import ch.yvu.dfa.analysis.StronglyLiveVariableStrategy;

import ch.yvu.dfa.main.Worker;

import java.util.HashMap;
import java.util.Map;

import java.net.URL;

public class MainFrame extends JFrame implements ActionListener {

	private Map<String, AnalysisStrategy> strategies = new HashMap<String, AnalysisStrategy>();
	
	private JPanel panel = new JPanel();
	private JButton analyzeButton = new JButton("Analyze");
	private JTextArea input = new JTextArea(30,20);
	private ImagePanel image = new ImagePanel();
	private JEditorPane output = new JEditorPane();
	private JComboBox strategyList = new JComboBox();
	
	public MainFrame(){
		super("Dataflow Analysis");
		initializeStrategies();
		
		setIcon();
		
		Container con = this.getContentPane();
		con.add(this.panel);
		
		this.panel.setLayout(new BoxLayout(this.panel, BoxLayout.LINE_AXIS));
		
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.PAGE_AXIS));	
		
		JScrollPane inputScrollPane = new JScrollPane(this.input);
		inputPanel.add(inputScrollPane);
		
		JPanel buttonPanel = new JPanel();
		this.strategyList = new JComboBox(this.strategies.keySet().toArray());
		buttonPanel.add(this.strategyList);	
		this.analyzeButton.setMnemonic('A');
		this.analyzeButton.addActionListener(this);		
		buttonPanel.add(this.analyzeButton);
		
		inputPanel.add(buttonPanel);
		
		this.panel.add(inputPanel);
		
		JPanel outputPanel = new JPanel();
		outputPanel.setLayout(new BoxLayout(outputPanel, BoxLayout.PAGE_AXIS));
		
		outputPanel.add(this.image);
		
		this.output.setEditable(false);
		HTMLEditorKit kit = new HTMLEditorKit();
		this.output.setEditorKit(kit);
		Document doc = kit.createDefaultDocument();
		this.output.setDocument(doc);
		this.output.setText("No output yet");
		
		JScrollPane outputScrollPane = new JScrollPane(this.output);
		outputPanel.add(outputScrollPane);
		
		JScrollPane totalOutputScrollPane = new JScrollPane(outputPanel);
		
		this.panel.add(totalOutputScrollPane);
		
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    pack();
	    setVisible(true);
	}
	
	public void updateOutput(String html, BufferedImage image){
		this.image.setImage(image);
		String htmlExpressions = html;
		this.output.setText(htmlExpressions);
		
		pack();
		repaint();
	}
	
	private void setIcon(){
		URL url = ClassLoader.getSystemResource("ch/yvu/dfa/ressources/icon.png");
		Toolkit kit = Toolkit.getDefaultToolkit();
		Image img = kit.createImage(url);
		this.setIconImage(img);
	}
	
	private void initializeStrategies(){
		this.strategies.put("Available Expressions", new AvailableExpressionStrategy());
		this.strategies.put("Reaching Definitions", new ReachingDefinitionsStrategy());
		this.strategies.put("Live Variable", new LiveVariableStrategy());
		this.strategies.put("Strongly Live Variables", new StronglyLiveVariableStrategy());
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		//TODO: own thread
		Worker worker = new Worker(this, this.input.getText(), this.strategies.get(this.strategyList.getSelectedItem()));
		Thread t = new Thread(worker);
		t.start();
	}
	
	private class ImagePanel extends Component{
		private BufferedImage image;
		
		@Override
		public Dimension getPreferredSize() {
			if(this.image == null){
				return new Dimension(200, 200);
			} else{
				return new Dimension(this.image.getWidth(), this.image.getHeight());
			}
		}
		
		public void setImage(BufferedImage image){
			this.image = image;
			setSize(image.getWidth(), image.getHeight());
		}
		
		@Override
		public void paint(Graphics g) {
			g.drawImage(image, 0, 0, null);
	    }
	}
}
