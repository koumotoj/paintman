import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class DrawGraphics extends JPanel implements ItemListener{

	static final int RECT = 1;
	static final int OVAL = 2;
	static final int LINE = 3;
	JRadioButton rb1, rb2, rb3;
	static int type = RECT;
	static DrawArea da;
	
	public DrawGraphics(){
		setBackground(Color.white);
		rb1 = new JRadioButton("長方形", true);
		rb2 = new JRadioButton("円");
		rb3 = new JRadioButton("線");
		ButtonGroup group = new ButtonGroup();
		group.add(rb1);
		group.add(rb2);
		group.add(rb3);
		rb1.addItemListener(this);
		rb2.addItemListener(this);
		rb3.addItemListener(this);
		da = new DrawArea();
		add(da);
		add(rb1);
		add(rb2);
		add(rb3);
	}
	
	public void itemStateChanged(ItemEvent e){
		if(e.getItemSelectable() == rb1){ type = RECT;}
		else if(e.getItemSelectable() == rb2){ type = OVAL;}
		else if(e.getItemSelectable() == rb3){ type = LINE;}
	}
	
	public static void main(String[] args){		//メイン関数
		JFrame frame = new JMenuGraphics();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		DrawGraphics e = new DrawGraphics();
		frame.add(e, BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);
	}
}

class JMenuGraphics extends JFrame implements ActionListener{
	JMenuItem  open, save, exit, onedelete;
	
	public JMenuGraphics(){
		JMenuBar menubar = new JMenuBar();
		JMenu file = new JMenu("ファイル(F)");
		JMenu edit = new JMenu("編集(E)");
		
		open = new JMenuItem("開く(O)");
  		save = new JMenuItem("保存(S)");
  		exit = new JMenuItem("終了(N)");
		onedelete = new JMenuItem("戻る(U)");
		
		file.add(open);
    	file.add(save);
    	file.add(exit);
		edit.add(onedelete);
		
		open.addActionListener(this);
		save.addActionListener(this);
		exit.addActionListener(this);
		onedelete.addActionListener(this);
		
		menubar.add(file);
		menubar.add(edit);
		
		setJMenuBar(menubar);
	}
	
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == exit){ //終了ボタン
			System.exit(1);
		}else if(e.getSource() == open){//オープンボタン
			File file = null;
			JFileChooser filechooser = new JFileChooser();
			int selected = filechooser.showOpenDialog(this);
			if (selected == JFileChooser.APPROVE_OPTION){
				file = filechooser.getSelectedFile();
			}
			try{
				DrawArea.bg.setColor(Color.white);
				DrawArea.bg.fillRect(0,0,250,250);		//一度消す
				DrawArea.bg.setColor(Color.black);
				DrawArea.list.clear();					//リストもクリア
				DrawArea.list = FileOperate1.fileRead(file);//ファイルを新しく読み込む
				DrawGraphics.da.repaint();
			}catch (IOException ex){
				System.err.println("ファイルが開けません。");
				ex.printStackTrace();
			}
			
		}else if(e.getSource() == save){//保存ボタン
			File file = null;
			JFileChooser filechooser = new JFileChooser();
			int selected = filechooser.showOpenDialog(this);
			if (selected == JFileChooser.APPROVE_OPTION){
				file = filechooser.getSelectedFile();
			}
			try{
				FileOperate1.fielWrite(DrawArea.list, file);
			}catch (IOException ex){
				System.err.println("ファイルが書き込ません。");
				ex.printStackTrace();
			}
		}else if(e.getSource() == onedelete){ //戻るボタン
			if(DrawArea.list.getSize() > DrawArea.list.getFileReadElements()){
				DrawArea.list.oneDelete();
				DrawArea.bg.setColor(Color.white);
				DrawArea.bg.fillRect(0,0,250,250);
				DrawArea.bg.setColor(Color.black);
				DrawArea.list.draw();
				DrawGraphics.da.repaint();
				System.out.println(DrawArea.list);
			}else {
				System.err.println("これ以上戻れません。");
			}
		}
	}
}

