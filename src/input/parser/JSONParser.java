package input.parser;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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
		
		// Set the Description by getting it from the JSON file
		String description = JSONroot.getString("Description");
		
		// Construct the PointNodeDatabase from the "Points" values in the JSON file
		PointNodeDatabase pointDB = getPoints(JSONroot);
		
		// Construct the PointNodeDatabase from the "Points" values in the JSON file
		SegmentNodeDatabase segmentDB = getSegments(JSONroot, pointDB);
		
		// Construct the figure by passing each of the above 
		// items: the description, the PointNodeDatabase, and the SegmentNodeDatabase 
		// into the FigureNode constructor.
		FigureNode figure = new FigureNode(description, pointDB, segmentDB);
		
		return figure;

        // TODO: Build the whole AST, check for return class object, and return the root
	}
    // TODO: implement supporting functionality
	
	/**
	 * 
	 * @param obj
	 * @return PointNodeDatabase
	 */
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
	
	private SegmentNodeDatabase getSegments(JSONObject obj, PointNodeDatabase pointDB) {
		// Get the array of segments that is assigned to the "Segments" key in JSON
		JSONArray segments = obj.getJSONArray("Segments");
		
		// Create an empty SegmentNodeDatabase
		SegmentNodeDatabase segmentDB = new SegmentNodeDatabase();
		
		// Loop through the segments in the array
		for(Object o : segments) {
			
			// For each segment in the array extract the key
			String keyStr = ((JSONObject) o).keySet().toArray().toString();
			PointNode key = pointDB.getPoint(keyStr);
			
			// Get the point corresponding with that key from the PointNodeDatabase
			
			// Get the array of values assigned to that key
			JSONArray points = obj.getJSONArray(keyStr);
			// Loop through that array and get each value 
			for(Object str : points) {
				// Get the point corresponding to that value from the PointNodeDatabase 
				String pointStr = str.toString();
				PointNode point = pointDB.getPoint(pointStr);
				// Add that point and the key to a the SegmentNodeDatabase
				segmentDB.addUndirectedEdge(key, point);
			}
		}
		
		return segmentDB;
	}
	
	

	public static void main(String[] args ) {
		JSONParser p = new JSONParser();
		String figureStr = utilities.io.FileUtilities.readFileFilterComments("/CSC-223-Lab4/collinear_line_segments.json");
		ComponentNode n = p.parse(figureStr);
	}
	
	

}

