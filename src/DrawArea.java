import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.*;
import java.util.*;
import java.io.*;

public class DrawArea extends JPanel implements MouseListener, MouseMotionListener {
	
	static final int RECT = 1;
	static final int OVAL = 2;
	static final int LINE = 3;
	static int x1, y1, x2, y2;
	int fX, fY, sX, sY; 
	BufferedImage bi; 			 //オフスクリーンイメージ
    static Graphics bg ;         //バックグラウンド
  	boolean firsttime = true;    
   	boolean confirm = false;
	boolean biVisible = false;
	static FigureList list = new FigureList();
	
	DrawArea(){
		Dimension dim = new Dimension(250, 250);
		addMouseListener(this);
		addMouseMotionListener(this);
		setPreferredSize(dim);
	}
	
	protected void paintComponent(Graphics g){    
		super.paintComponent(g);
		if(firsttime){
           	bi = (BufferedImage)createImage(getWidth(), getHeight());
           	bg = bi.createGraphics(); //Graphicsコンテキストを得る
           	bg.setColor(Color.white);
	   		bg.fillRect(0, 0, getWidth(), getHeight());
           	bg.setColor(Color.black);
           	firsttime = false;
       	}
		if(confirm){
	   		if(DrawGraphics.type == RECT){
	   			bg.drawRect(x1, y1, x2-x1, y2-y1);
	   	 	} else if (DrawGraphics.type == OVAL){
	   			bg.drawOval(x1, y1, x2-x1, y2-y1);
	   		} else if (DrawGraphics.type == LINE){
	   			bg.drawLine(x1,y1,x2,y2);
	   	}
           	confirm=false;
      	}
      	g.drawImage(bi, 0, 0, this);
		
		if(biVisible){
	 		if(DrawGraphics.type == RECT){
	   			g.drawRect(x1, y1, x2-x1, y2-y1);
	 		} else if (DrawGraphics.type == OVAL){
	   			g.drawOval(x1, y1, x2-x1, y2-y1);
	 		} else if (DrawGraphics.type == LINE){
	 			g.drawLine(x1,y1,x2,y2);
			}
		}
	}
			
	public void mousePressed(MouseEvent e){ 
		fX = e.getX();
		fY = e.getY();
		biVisible = true;
	}

	public void mouseDragged(MouseEvent e){ 
		sX = e.getX();
		sY = e.getY();
		
		if(DrawGraphics.type == LINE){
			 x1=fX; x2=sX; y1=fY; y2=sY;
		}else{
 			adjustPoint();
		}
		setForeground(Color.red);
		repaint();
	}
		
	public void adjustPoint(){
		if(sX >= fX && sY >= fY){//右下
			x1 = fX; x2 = sX;
			y1 = fY; y2 = sY;
		}else if(sX<fX && sY<fY){//左上
			x1 = sX; x2 = fX;
			y1 = sY; y2 = fY;
		}else if(sX>=fX && sY<fY){//右上
			x1 = fX; x2 = sX;
			y1 = sY; y2 = fY;
		}else if(sX<fX && sY>=fY){
			x1 = sX; x2 = fX;
			y1 = fY; y2 = sY;
		}
	}
	
	public void mouseReleased(MouseEvent e){ 
		sX = e.getX();
		sY = e.getY();
		setForeground(Color.black);
		confirm = true;
		biVisible = false;
		if(DrawGraphics.type == LINE){
			Line line = new Line(x1,y1,x2,y2);
			line.write();
			list.add(line);
		}else if (DrawGraphics.type == RECT){
			Rect rect = new Rect(x1,y1,x2,y2);
			rect.write();
			list.add(rect);
		}else if(DrawGraphics.type == OVAL){
			Oval oval = new Oval(x1,y1,x2,y2);
			oval.write();
			list.add(oval);
		}
		repaint();
	}
	
	public void mouseMoved(MouseEvent e) { }
	public void mouseClicked(MouseEvent e){ }
	public void mouseEntered(MouseEvent e){ }
	public void mouseExited(MouseEvent e) { }
	
}