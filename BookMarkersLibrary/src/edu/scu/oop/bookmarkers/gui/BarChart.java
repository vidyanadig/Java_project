package edu.scu.oop.bookmarkers.gui;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.scu.oop.bookmarkers.model.Fiction;
import edu.scu.oop.bookmarkers.model.Library;
import edu.scu.oop.bookmarkers.model.NonFiction;
import edu.scu.oop.bookmarkers.model.Video;


public class BarChart extends JPanel
{
	private Map<Color, Integer> bars =
            new LinkedHashMap<Color, Integer>();

	String names[]= {"Fiction" , "NonFiction" , "Video"};
	public BarChart()
	{	
		this.setBackground(Color.CYAN  ); 
		
		this.addBar(Color.red, Fiction.getNumOfItems());
		this.addBar(Color.green, NonFiction.getNumOfItems());
		this.addBar(Color.blue, Video.getNumOfItems());
		setPreferredSize(new Dimension(400,400));
		setVisible(true);
		// cannot call paintComponent() or paint() directly
		repaint();

	}
	/**
	 * Add new bar to chart
	 * @param color color to display bar
	 * @param value size of bar
	 */
	public void addBar(Color color, int value)
	{
		bars.put(color, value);
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		// determine longest bar

		int max = Integer.MIN_VALUE;
		for (Integer value : bars.values())
		{
			max = Math.max(max, value);
		}

		// paint bars
		int height = (getHeight() / bars.size()) - 100;
		int y = 1;
		int i = 0;
		for (Color color : bars.keySet())
		{
			int value = bars.get(color);
			int width = (int)
                    ((getWidth()-100) * ((double)value / max));
	
			System.out.println("0, " + (getHeight() - y) + ",  " + width + ", " + height + "value : " +value);
			g.setColor(color);
			g.fillRect(0, getHeight() - y - height, width, height );
			g.setColor(Color.black);
			g.drawRect(0, getHeight() - y - height, width, height);
		    g.drawString(names[i], width + 5, getHeight() - y - 10);
			i++;
		    y += (height + 5);
		}
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(bars.size() * 10 + 2, 50);
	}
	
}
