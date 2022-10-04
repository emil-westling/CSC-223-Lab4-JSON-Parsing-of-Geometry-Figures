package input.components;

import java.util.Set;

import input.components.point.PointNode;
import input.components.point.PointNodeDatabase;
import input.components.segment.SegmentNodeDatabase;

/**
 * A basic figure consists of points, segments, and an optional description
 * 
 * Each figure has distinct points and segments (thus unique database objects).
 * 
 */
public class FigureNode implements ComponentNode
{
	protected String              _description;
	protected PointNodeDatabase   _points;
	protected SegmentNodeDatabase _segments;

	public String              getDescription()    { return _description; }
	public PointNodeDatabase   getPointsDatabase() { return _points; }
	public SegmentNodeDatabase getSegments()       { return _segments; }
	
	public FigureNode(String description, PointNodeDatabase points, SegmentNodeDatabase segments)
	{
		_description = description;
		_points = points;
		_segments = segments;
	}

	@Override
	public void unparse(StringBuilder sb, int level)
	{
		// Print the key "Figure" follow by a semicolon and a new line character
		// Print the description segment beginning with an indent character 
        // For each point in the PointNodeDatabase 
		// Print "{ "name" : " + the name of the point followed by the x and y and their values
		// If it's not the last point in the database print a comma
		
    }
	
	private void printPoints() {
		// TODO
	}
	
	private void printSegments() {
		// TODO
	}
	
	
}