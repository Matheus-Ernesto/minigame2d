package MiniGame2D;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints.Key;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import MiniGame2D.GameEngine2D.KeyMapper;
import MiniGame2D.GameEngine2D.Vector2d;

public class MiniJogo2D{
	private static JFrame janela = new JFrame();
	private static GameEngine2D engine = new GameEngine2D();
	public static Canvas canvas = new Canvas();
	static float vetorX = 0;
	static float vetorY = 0;
	static float vetorZ = 0;

	public static void main(String[] args) {
		//Criar uma janela
		janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		janela.setTitle("Mini Jogo 2D");
		janela.setBounds(100, 100, 800, 600);
		janela.setLayout(new FlowLayout());
		janela.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				engine.stop();
			}
		});
		
		
		GameEngine2D.Scene cena = new GameEngine2D.Scene();
		GameEngine2D.GameObject go = new GameEngine2D.GameObject();
		GameEngine2D.GameObject go2 = new GameEngine2D.GameObject();
		janela.setVisible(true);
		janela.add(cena);
		Image tile = null;
		try {
			tile = ImageIO.read(MiniJogo2D.class.getResource("tile_map32.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		cena.add(go);
		//cena.add(go2);
		cena.setBounds(0,0,800,600);
		cena.setBackground(Color.blue);
		
		go.setScale(1,1);
		go.setPosition(0,0);
		go.setImage(tile);
		
		//go2.setScale(1,1);
		//go2.setImage(tile);
		//go2.setPosition(0,0);
		
		cena.renderObjects(20, 0, 0, false);
	
		engine.setFPS(30);
		engine.setBeforeframeFPS(30);
		engine.setRunnables(() -> {
			cena.renderObjects(20, cena.cameraPositionX, cena.cameraPositionY, false);
		}, () -> {}, () -> {});
		engine.start();
		
		AbstractAction moverUp =  new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("W 0");
				cena.cameraPositionY += 0.1;
			}
		};
		AbstractAction moverUpRel =  new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("W 1");
				cena.cameraPositionY += 0.05;
			}
		};
		AbstractAction moverDown =  new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cena.cameraPositionY -= 0.1;
			}
		};
		AbstractAction moverDownRel =  new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cena.cameraPositionY -= 0.05;
			}
		};
		AbstractAction moverRight =  new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("W 0");
				cena.cameraPositionX += 0.1;
			}
		};
		AbstractAction moverRightRel =  new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("W 1");
				cena.cameraPositionX += 0.05;
			}
		};
		AbstractAction moverLeft =  new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cena.cameraPositionX -= 0.1;
			}
		};
		AbstractAction moverLeftRel =  new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cena.cameraPositionX -= 0.05;
			}
		};
		AbstractAction zoomUp =  new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("C");
				cena.renderObjects(cena.cameraPositionZ+0.1, cena.cameraPositionX, cena.cameraPositionY, false);
			}
		};
		AbstractAction zoomDown =  new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("V");
				cena.renderObjects(cena.cameraPositionZ-0.1, cena.cameraPositionX, cena.cameraPositionY, false);
			}
		};
		
		GameEngine2D.KeyMapper kmp = new GameEngine2D.KeyMapper();
		kmp.setActionByKey(cena, cena.WHEN_IN_FOCUSED_WINDOW, moverUp, "moveUp",KeyStroke.getKeyStroke(KeyEvent.VK_W, 0,false));
		kmp.setActionByKey(cena, cena.WHEN_IN_FOCUSED_WINDOW, moverUpRel, "moveUpRel",KeyStroke.getKeyStroke(KeyEvent.VK_W, 0,true));
		kmp.setActionByKey(cena, cena.WHEN_IN_FOCUSED_WINDOW, moverDown, "moveDown",KeyStroke.getKeyStroke(KeyEvent.VK_S, 0,false));
		kmp.setActionByKey(cena, cena.WHEN_IN_FOCUSED_WINDOW, moverDownRel, "moveDownRel",KeyStroke.getKeyStroke(KeyEvent.VK_S, 0,true));
		kmp.setActionByKey(cena, cena.WHEN_IN_FOCUSED_WINDOW, moverRight, "moveRight",KeyStroke.getKeyStroke(KeyEvent.VK_D, 0,false));
		kmp.setActionByKey(cena, cena.WHEN_IN_FOCUSED_WINDOW, moverRightRel, "moveRightRel",KeyStroke.getKeyStroke(KeyEvent.VK_D, 0,true));
		kmp.setActionByKey(cena, cena.WHEN_IN_FOCUSED_WINDOW, moverLeft, "moveLeft",KeyStroke.getKeyStroke(KeyEvent.VK_A, 0,false));
		kmp.setActionByKey(cena, cena.WHEN_IN_FOCUSED_WINDOW, moverLeftRel, "moveLeftRel",KeyStroke.getKeyStroke(KeyEvent.VK_A, 0,true));
		kmp.setActionByKey(cena, cena.WHEN_IN_FOCUSED_WINDOW, zoomUp, "zoomUp",KeyStroke.getKeyStroke(KeyEvent.VK_C, 0,false));
		kmp.setActionByKey(cena, cena.WHEN_IN_FOCUSED_WINDOW, zoomDown, "zoomDown",KeyStroke.getKeyStroke(KeyEvent.VK_V, 0,false));
	}
	
}

class GameEngine2D{
	private static boolean sair = false;
	private static int framesPerSecond = 24;
	private static int framesPerTick = 24;
	
	private static Runnable beforeFrame_run;
	private static Runnable onFrame_run;
	private static Runnable afterSecond_run;
	
	public static void start() {
		Thread beforeFrame_Repeater = new Thread(() -> {
			while(!sair){
				try {
					Thread.sleep(1000 / framesPerTick);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				new Thread(beforeFrame_run).start();
			}
		});
		Thread onFrame_Repeater = new Thread(() -> {
			while(!sair){
				try {
					Thread.sleep(1000 / framesPerSecond);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				new Thread(onFrame_run).start();
			}
		});
		Thread afterSecond_Repeater = new Thread(() -> {
			while(!sair){
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				new Thread(afterSecond_run).start();
			}
		});
		beforeFrame_Repeater.start();
		onFrame_Repeater.start();
		afterSecond_Repeater.start();
	}
	
	public void stop() {
		sair = true;
	}
	
	public void setFPS(int frames) {
		framesPerSecond = frames;
	}
	
	public void setBeforeframeFPS(int frames) {
		framesPerTick = frames;
	}
	
	public void setRunnables(Runnable beforeFrame_run_t,Runnable onFrame_run_t,Runnable afterSecond_run_t) {
		beforeFrame_run = beforeFrame_run_t;
		onFrame_run = onFrame_run_t;
		afterSecond_run = afterSecond_run_t;
	}
	static class KeyMapper {
		public void setActionByKey(JComponent component,int focus, Action action, String name, KeyStroke key) {
			System.out.println(name + " action to " + key.getKeyChar());
			component.getInputMap(focus).put(key, name);
			component.getActionMap().put(name,action);
		}
	}
	
	static class Scene extends JPanel{
		public double cameraPositionX = 0;
		public double cameraPositionY = 0;
		public double cameraPositionZ = 0;
		
		public void renderObjects(double size, double x, double y,boolean stretch) {
			cameraPositionX = x;
			cameraPositionY = y;
			cameraPositionZ = size;
			
			double PartX = this.getWidth() / size;
			double PartY = (stretch) ? this.getHeight() / size: this.getWidth() / size;
			
			double X_cart = -x + size / 2;
			double Y_cart = y + size / 2;
			
			Component[] comps = this.getComponents();
			for (Component compt : comps) {
				GameObject comp_t = (GameObject) compt;
				int width = (int) (PartX * comp_t.r1.width);
				int height = (int) (PartY * comp_t.r1.height);
				
				int x_local = (int) (PartX * (comp_t.r1.x + X_cart) - width/2);
				int y_local = (int) (PartY * (comp_t.r1.y + Y_cart) - height/2);
				comp_t.setBounds(x_local,y_local,width,height);
			}
			
			//local:(1,2) e tamanho:(1,1) e camera:(0,0,7)
			/*PX = 800 / 7 				114.28
			 *PY = 600 / 7 ou 800 / 7	85.71 ou 114.28
			 *
			 *XC = 0 + 3.5 //3.5
			 * 
			 * */
		}
	}
	
	static class GameObject extends JComponent{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private Image imagem;
		public Rectangle r1 = new Rectangle(0,0,0,0);
		
		public void setImage(Image imagem_t) {
			this.imagem = imagem_t;
		}
		public void setScale(int x, int y) {
			r1.width = x;
			r1.height = y;
		}
		public void setPosition(int x, int y) {
			r1.x = x;
			r1.y = y;
		}
		
		@Override
		public void paint(Graphics g) {
			g.drawImage(imagem, 0, 0, this.getWidth(), this.getHeight(),null);
		}
	}
		
	public class Vector2d{
		int x;
		int y;
		public Vector2d(int x,int y) {
			this.x = x;
			this.y = y;
		}
		public Vector2d() {
			this.x = 0;
			this.y = 0;
		}
		public Vector2d add(int x_t,int y_t) {
			x_t = x + x_t;
			y_t = y + y_t;
			
			return new Vector2d(x_t, y_t);
		}
		public Vector2d subtract(int x_t,int y_t) {
			x_t = y - x_t;
			y_t = y - y_t;
			
			return new Vector2d(x_t, y_t);
		}
		public Vector2d multiply(int x_t,int y_t) {
			x_t = y * x_t;
			y_t = y * y_t;
			
			return new Vector2d(x_t, y_t);
		}
		public Vector2d divide(int x_t,int y_t) {
			x_t = y / x_t;
			y_t = y / y_t;
			
			return new Vector2d(x_t, y_t);
		}
	}

}








