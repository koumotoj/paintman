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
		rb1 = new JRadioButton("�����`", true);
		rb2 = new JRadioButton("�~");
		rb3 = new JRadioButton("��");
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
	
	public static void main(String[] args){		//���C���֐�
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
		JMenu file = new JMenu("�t�@�C��(F)");
		JMenu edit = new JMenu("�ҏW(E)");
		
		open = new JMenuItem("�J��(O)");
  		save = new JMenuItem("�ۑ�(S)");
  		exit = new JMenuItem("�I��(N)");
		onedelete = new JMenuItem("�߂�(U)");
		
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
		if(e.getSource() == exit){ //�I���{�^��
			System.exit(1);
		}else if(e.getSource() == open){//�I�[�v���{�^��
			File file = null;
			JFileChooser filechooser = new JFileChooser();
			int selected = filechooser.showOpenDialog(this);
			if (selected == JFileChooser.APPROVE_OPTION){
				file = filechooser.getSelectedFile();
			}
			try{
				DrawArea.bg.setColor(Color.white);
				DrawArea.bg.fillRect(0,0,250,250);		//��x����
				DrawArea.bg.setColor(Color.black);
				DrawArea.list.clear();					//���X�g���N���A
				DrawArea.list = FileOperate1.fileRead(file);//�t�@�C����V�����ǂݍ���
				DrawGraphics.da.repaint();
			}catch (IOException ex){
				System.err.println("�t�@�C�����J���܂���B");
				ex.printStackTrace();
			}
			
		}else if(e.getSource() == save){//�ۑ��{�^��
			File file = null;
			JFileChooser filechooser = new JFileChooser();
			int selected = filechooser.showOpenDialog(this);
			if (selected == JFileChooser.APPROVE_OPTION){
				file = filechooser.getSelectedFile();
			}
			try{
				FileOperate1.fielWrite(DrawArea.list, file);
			}catch (IOException ex){
				System.err.println("�t�@�C�����������܂���B");
				ex.printStackTrace();
			}
		}else if(e.getSource() == onedelete){ //�߂�{�^��
			if(DrawArea.list.getSize() > DrawArea.list.getFileReadElements()){
				DrawArea.list.oneDelete();
				DrawArea.bg.setColor(Color.white);
				DrawArea.bg.fillRect(0,0,250,250);
				DrawArea.bg.setColor(Color.black);
				DrawArea.list.draw();
				DrawGraphics.da.repaint();
				System.out.println(DrawArea.list);
			}else {
				System.err.println("����ȏ�߂�܂���B");
			}
		}
	}
}

