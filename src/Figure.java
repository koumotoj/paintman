import java.util.*;
import java.io.*;

class FileOperate1 {
	static FigureList fileRead(File file) throws IOException{
		 FigureList list = new FigureList();
		 if(file == null) return list;
		 BufferedReader din = new BufferedReader(new FileReader(file));
		 String s;
		 while((s = din.readLine()) != null){
			StringTokenizer st = new StringTokenizer(s, " ");
			String array[] = new String[5];
			for(int i = 0; i < 5; i++){
				array[i] = st.nextToken();
			}
				int x1 = Integer.parseInt(array[1]);
				int y1 = Integer.parseInt(array[2]);
				int x2 = Integer.parseInt(array[3]);
				int y2 = Integer.parseInt(array[4]);
				if(array[0].equals("Line")){
					Line line = new Line(x1, y1, x2, y2);
					list.add(line);
					line.draw();
				}else if (array[0].equals("Rect")){
					Rect rect = new Rect(x1, y1, x2, y2);
					list.add(rect);
					rect.draw();
			 	}else if(array[0].equals("Oval")){
					Oval oval = new Oval(x1, y1, x2, y2);
					list.add(oval);
					oval.draw();
				}
				list.addFileReadElements();
		 }
		 return list;
	}
	static void fielWrite(FigureList list, File file) throws IOException{
		//listの内容をファイルに書き込み
		if(file == null) return;
		BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		Iterator iterator = list.getList().iterator();
		while(iterator.hasNext()) {
			Figure fig = (Figure) iterator.next();
			bw.write(fig.toString());
			bw.newLine();
		}
		bw.close();
	}
}

public abstract class Figure{
	public Figure(){
	}
	abstract public void draw();	//描画メソッド
	abstract public void write();	//デバッグ用のメソッド
}

class FigureList{
	private int fileReadElements = 0; //ファイルから読み込んだ要素数
	private ArrayList<Figure> list = new ArrayList<Figure>();
	
	public void add(Figure figure){
		list.add(figure);
	}
	
	public ArrayList<Figure> getList(){
		return list;
	}
	
	public void clear(){
		list.clear();
	}
	
	public void oneDelete(){
		if(list.size() == 0) return;
		list.remove(list.size()-1);
	}
	
	public String toString(){
		String retval ="";
		for(int i = 0; i< list.size(); i++){
			Figure fig = list.get(i);
			retval += fig.toString() + "\n";
		}
		return retval;
	}
	
	public void draw(){
		for(int i = 0; i< list.size(); i++){
			Figure fig = list.get(i);
			fig.draw();
		}
	}
	
	public void addFileReadElements(){
		fileReadElements++;
	}
	
	public int getFileReadElements(){
		return fileReadElements;
	}
	
	public int getSize(){
		return list.size();
	}
}

class Line extends Figure{
	private int x1, y1, x2, y2;
	
	public Line(int x1,int y1,int x2,int y2){
		super();
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
	}
	
	public void draw(){
		DrawArea.bg.drawLine(x1, y1, x2, y2);
	}
	
	public void write(){
		System.out.println(this);
	}
	
	public String toString(){
		return "Line "+x1+" "+y1+" "+x2+" "+y2;
	}
}

class Rect extends Figure{
	private int x1, y1, x2, y2;
	
	public Rect(int x1,int y1,int x2,int y2){
		super();
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
	}
	
	public void draw(){
		DrawArea.bg.drawRect(x1, y1, x2-x1, y2-y1);
	}
	
	public void write(){
		System.out.println(this);
	}
	
	public String toString(){
		return  "Rect "+x1+" "+y1+" "+x2+" "+y2;
	}
}

class Oval extends Figure{
	private int x1, y1, x2, y2;
	
	public Oval(int x1,int y1,int x2,int y2){
		super();
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
	}
	
	public void draw(){
		DrawArea.bg.drawOval(x1, y1, x2-x1, y2-y1);
	}
	
	public void write(){
		System.out.println(this);
	}
	
	public String toString(){
		return  "Oval "+x1+" "+y1+" "+x2+" "+y2;
	}
}