package ch.yvu.dfa.tests;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.Assert;
import org.junit.Test;

public class ExplorationalTests {

	@Test
	public void test_string_split(){
		//Arrange
		String input = "digraph testGraph {0->1; 0->2;}";
		
		//Act
		String[] tokens = input.split("[{]");
		String[] tokens2 = tokens[1].split("[}]");

		//Assert
		Assert.assertEquals(2, tokens.length);
		Assert.assertEquals(1, tokens2.length);
		Assert.assertEquals("0->1; 0->2;", tokens2[0]);
	}
	
	@Test
	public void test_replace_whitespaces(){
		//Arrange
		String input = "diagraph testGraph  {\n 0->1;\t\n0->2; } ";

		//Act
		String trimmed = input.replaceAll("\\s","");
		
		//Assert
		Assert.assertEquals("diagraphtestGraph{0->1;0->2;}", trimmed);
	}
	
	@Test
	public void test_regex_edge(){
		String regex = "([0-9]+)->([0-9]+)";
		String edge1 = "1->2";
		String edge2 = "10->20";
		String edge3 = "1->2[label=\"bla\"]";
		
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher1 = pattern.matcher(edge1);
		Matcher matcher2 = pattern.matcher(edge2);
		Matcher matcher3 = pattern.matcher(edge3);
		
		Assert.assertTrue(matcher1.find());
		Assert.assertEquals(2, matcher1.groupCount());
		Assert.assertEquals("1", matcher1.group(1));
		Assert.assertEquals("2", matcher1.group(2));
		
		Assert.assertTrue(matcher2.find());
		Assert.assertEquals(2, matcher2.groupCount());
		Assert.assertEquals("10", matcher2.group(1));
		Assert.assertEquals("20", matcher2.group(2));
		
		Assert.assertTrue(matcher3.find());
		Assert.assertEquals(2, matcher3.groupCount());
		Assert.assertEquals("1", matcher3.group(1));
		Assert.assertEquals("2", matcher3.group(2));
	}
	
	@Test
	public void test_regex_node(){
		String regex = "([0-9]+)\\[label=\"([^\"]+)\"\\]";
		String node1 = "1[label=\"bla\"]";
		String node2 = "10[label=\"bla\"]";
		String node3 = "1";
		
		
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher1 = pattern.matcher(node1);
		Matcher matcher2 = pattern.matcher(node2);
		Matcher matcher3 = pattern.matcher(node3);
		
		Assert.assertTrue(matcher1.find());
		Assert.assertEquals(2, matcher1.groupCount());
		Assert.assertEquals("1", matcher1.group(1));
		Assert.assertEquals("bla", matcher1.group(2));
		
		Assert.assertTrue(matcher2.find());
		Assert.assertEquals(2, matcher2.groupCount());
		Assert.assertEquals("10", matcher2.group(1));
		Assert.assertEquals("bla", matcher2.group(2));
		
		Assert.assertFalse(matcher3.find());
	}
}
