package MiniGame2D;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class MiniJogo2D{
	private static JFrame janela = new JFrame();
	private static GameEngine2D engine = new GameEngine2D();
	public static Canvas canvas = new Canvas();
	static GameEngine2D.KeyMapper keymanager;
	public static KeyEvent key;
	static int fps = 0;
	static float vetorW = 0;
	static float vetorA = 0;
	static float vetorS = 0;
	static float vetorD = 0;
	static float vetorC = 0;
	static float vetorV = 0;

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
		janela.setVisible(true);
		janela.add(cena);
		janela.addKeyListener(keymanager);
		Image tile = null;
		try {
			tile = ImageIO.read(MiniJogo2D.class.getResource("tile_map32.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		for (int iterator = 1; iterator <= 10;iterator++)  {
			GameEngine2D.GameObject go = new GameEngine2D.GameObject();
			go.setScale(1,iterator);
			go.setPosition(iterator,0);
			go.setImage(tile,go.STRETCH);
			cena.add(go);			
		}
		
		cena.setBounds(0,0,800,600);
		cena.setBackground(Color.blue);
		cena.renderObjects(20, 0, 0, false);
		
		engine.createRunnableStart(() -> {
			fps++;
		}, 99999);
		engine.createRunnableStart(() -> {
			System.out.println(fps + " fps");
			fps = 0;
		}, 1);
		engine.createRunnableStart(() -> {
			cena.renderObjects(cena.cameraPositionZ + vetorC + vetorV, cena.cameraPositionX + vetorA + vetorD, cena.cameraPositionY +  vetorW + vetorS, false);
		}, 120);
		
		keymanager = new GameEngine2D.KeyMapper() {
			@Override
			public void typed(KeyEvent e) {}

			@Override
			public void pressed(KeyEvent e) {
				char key = Character.toLowerCase(e.getKeyChar());
				switch (key) {
				case 'w':
					vetorW = 0.1f;
					break;
				case 'a':
					vetorA = -0.1f;
					break;
				case 's':
					vetorS = -0.1f;
					break;
				case 'd':
					vetorD = 0.1f;
					break;
				default:
					
					break;
				}
			}
			
			@Override
			public void released(KeyEvent e) {
				char key = Character.toLowerCase(e.getKeyChar());
				switch (key) {
				case 'w':
					vetorW = 0f;
					break;
				case 'a':
					vetorA = 0f;
					break;
				case 's':
					vetorS = 0f;
					break;
				case 'd':
					vetorD = 0f;
					break;
				default:
					
					break;
				}
			}
		};
		
		janela.addKeyListener(keymanager);
	}
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

class GameEngine2D{
	private static boolean sair = false;
	
	public void createRunnableStart(Runnable run, int FPS) {
		int FramePorSeconds = (FPS == 0) ? 1 : FPS;
		new Thread(() -> {
			while(!sair){
				try {
					Thread.sleep(1000 / FramePorSeconds);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				new Thread(run).start();
			}
		}).start();
	}
	
	public void stop() {
		sair = true;
	}

	static abstract class KeyMapper extends KeyAdapter{
		public abstract void typed(KeyEvent e);
		public abstract void pressed(KeyEvent e);
		public abstract void released(KeyEvent e);
		
		@Override
		public void keyTyped(KeyEvent e) {
			typed(e);
		}
		
		@Override
		public void keyPressed(KeyEvent e) {
			pressed(e);
		}
		
		@Override
		public void keyReleased(KeyEvent e) {
			released(e);
		}
	}
	
	static class Scene extends JPanel{
		private static final long serialVersionUID = 1L;
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
		}
	}
	
	static class GameObject extends JComponent{
		private static final long serialVersionUID = 1L;
		private Image imagem;
		public final int STRETCH = 0;
		public final int TILED = 1;
		
		public Rectangle r1 = new Rectangle(0,0,1,1);
		
		public void setImage(Image imagem_t, int style) {
			if(style == TILED) {
				Graphics g = imagem_t.getGraphics();
				
				int width = (int) r1.getWidth();
				int height = (int) r1.getHeight();
				
				Image scaled = imagem_t.getScaledInstance(width, height, Image.SCALE_SMOOTH);
				
				for (int x = 0; x < imagem_t.getWidth(null); x += width) {
					for (int y = 0; y < imagem_t.getHeight(null); y += height) {
						g.drawImage(scaled, x, y, null);
					}
				}
				g.dispose();
				this.imagem = imagem_t;				
			}else {
				this.imagem = imagem_t;
			}
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
}








