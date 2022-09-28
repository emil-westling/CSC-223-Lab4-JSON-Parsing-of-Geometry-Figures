package input.parser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import input.components.*;
import input.components.point.PointNode;
import input.components.point.PointNodeDatabase;
import input.components.segment.SegmentNodeDatabase;
import input.exception.ParseException;

public class JSONParser
{
	protected ComponentNode  _astRoot;

	public JSONParser()
	{
		_astRoot = null;
	}

	private void error(String message)
	{
		throw new ParseException("Parse error: " + message);
	}
	
	/**
	 * 
	 * @param str
	 * @return FigureNode
	 * @throws ParseException
	 */
	public ComponentNode parse(String str) throws ParseException
	{
		// Parsing is accomplished via the JSONTokenizer class.
		JSONTokener tokenizer = new JSONTokener(str);
		JSONObject  JSONroot = (JSONObject)tokenizer.nextValue();
		
		getPoints(JSONroot);
		
		return null;

        // TODO: Build the whole AST, check for return class object, and return the root
	}
    // TODO: implement supporting functionality
	
	private PointNodeDatabase getPoints(JSONObject obj) {
		JSONArray points = obj.getJSONArray("Points");
		System.out.println(points.toString());
		PointNodeDatabase pointDB = new PointNodeDatabase();
		
		
		for(Object o : points) {
			String name = ((JSONObject) o).getString("name");
			Double x = ((JSONObject) o).getDouble("x");
			Double y = ((JSONObject) o).getDouble("y");
			PointNode node = new PointNode(name, x, y);
			
			pointDB.put(node);
		}
		return pointDB;
	}
	

	public static void main(String[] args ) {
		JSONParser p = new JSONParser();
		String figureStr = utilities.io.FileUtilities.readFileFilterComments("/CSC-223-Lab4/collinear_line_segments.json");
		ComponentNode n = p.parse(figureStr);
	}
	
	

}

