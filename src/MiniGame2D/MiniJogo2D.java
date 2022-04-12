package MiniGame2D;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
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
		GameEngine2D.GameObject go = new GameEngine2D.GameObject();
		janela.setVisible(true);
		janela.add(cena);
		Image tile = null;
		try {
			tile = ImageIO.read(MiniJogo2D.class.getResource("tile_map32.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		cena.add(go);
		cena.setBounds(0,0,800,600);
		cena.setBackground(Color.blue);
		
		go.setScale(1,1);
		go.setPosition(0,0);
		go.setImage(tile,go.TILED);
		
		cena.renderObjects(20, 0, 0, false);
	
		engine.createRunnableStart(() -> {
			cena.renderObjects(cena.cameraPositionZ + vetorC + vetorV, cena.cameraPositionX + vetorA + vetorD, cena.cameraPositionY +  vetorW + vetorS, false);
		}, 30);
		
		AbstractAction moverUp =  new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				vetorW = 0.1f;
			}
		};
		AbstractAction moverUpRel =  new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				vetorW = 0f;
			}
		};
		AbstractAction moverDown =  new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				vetorS = -0.1f;
			}
		};
		AbstractAction moverDownRel =  new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				vetorS = 0f;
			}
		};
		AbstractAction moverRight =  new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				vetorD = 0.1f;
			}
		};
		AbstractAction moverRightRel =  new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				vetorD = 0f;
			}
		};
		AbstractAction moverLeft =  new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				vetorA = -0.1f;
			}
		};
		AbstractAction moverLeftRel =  new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				vetorA = 0f;
			}
		};
		AbstractAction zoomUp =  new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				vetorC = 0.2f;
			}
		};
		AbstractAction zoomUpRel =  new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				vetorC = 0f;
			}
		};
		AbstractAction zoomDown =  new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				vetorV = -0.2f;
			}
		};
		AbstractAction zoomDownRel =  new AbstractAction() {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void actionPerformed(ActionEvent e) {
				vetorV = 0f;
			}
		};
		
		GameEngine2D.KeyMapper kmp = new GameEngine2D.KeyMapper();
		kmp.setActionByKey(cena, JComponent.WHEN_IN_FOCUSED_WINDOW, moverUp, "moveUp",KeyStroke.getKeyStroke(KeyEvent.VK_W, 0,false));
		kmp.setActionByKey(cena, JComponent.WHEN_IN_FOCUSED_WINDOW, moverUpRel, "moveUpRel",KeyStroke.getKeyStroke(KeyEvent.VK_W, 0,true));
		kmp.setActionByKey(cena, JComponent.WHEN_IN_FOCUSED_WINDOW, moverDown, "moveDown",KeyStroke.getKeyStroke(KeyEvent.VK_S, 0,false));
		kmp.setActionByKey(cena, JComponent.WHEN_IN_FOCUSED_WINDOW, moverDownRel, "moveDownRel",KeyStroke.getKeyStroke(KeyEvent.VK_S, 0,true));
		kmp.setActionByKey(cena, JComponent.WHEN_IN_FOCUSED_WINDOW, moverRight, "moveRight",KeyStroke.getKeyStroke(KeyEvent.VK_D, 0,false));
		kmp.setActionByKey(cena, JComponent.WHEN_IN_FOCUSED_WINDOW, moverRightRel, "moveRightRel",KeyStroke.getKeyStroke(KeyEvent.VK_D, 0,true));
		kmp.setActionByKey(cena, JComponent.WHEN_IN_FOCUSED_WINDOW, moverLeft, "moveLeft",KeyStroke.getKeyStroke(KeyEvent.VK_A, 0,false));
		kmp.setActionByKey(cena, JComponent.WHEN_IN_FOCUSED_WINDOW, moverLeftRel, "moveLeftRel",KeyStroke.getKeyStroke(KeyEvent.VK_A, 0,true));
		kmp.setActionByKey(cena, JComponent.WHEN_IN_FOCUSED_WINDOW, zoomUp, "zoomUp",KeyStroke.getKeyStroke(KeyEvent.VK_C, 0,false));
		kmp.setActionByKey(cena, JComponent.WHEN_IN_FOCUSED_WINDOW, zoomUpRel, "zoomUpRel",KeyStroke.getKeyStroke(KeyEvent.VK_C, 0,true));
		kmp.setActionByKey(cena, JComponent.WHEN_IN_FOCUSED_WINDOW, zoomDown, "zoomDown",KeyStroke.getKeyStroke(KeyEvent.VK_V, 0,false));
		kmp.setActionByKey(cena, JComponent.WHEN_IN_FOCUSED_WINDOW, zoomDownRel, "zoomDownRel",KeyStroke.getKeyStroke(KeyEvent.VK_V, 0,true));
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

	static class KeyMapper {
		public void setActionByKey(JComponent component,int focus, Action action, String name, KeyStroke key) {
			component.getInputMap(focus).put(key, name);
			component.getActionMap().put(name,action);
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
		public final int STRETCH = 1;
		public final int TILED = 1;
		
		public Rectangle r1 = new Rectangle(0,0,1,1);
		
		public void setImage(Image imagem_t, int style) {
			if(style == TILED) {
				/*Graphics g = imagem_t.getGraphics();
				
				int width = this.getWidth()/ r1.width;
				int height = this.getHeight() / r1.height;
				
				Image scaled = imagem_t.getScaledInstance(width, height, Image.SCALE_SMOOTH);
				
				for (int x = 0; x < imagem_t.getWidth(null); x += width) {
					for (int y = 0; y < imagem_t.getHeight(null); y += height) {
						g.drawImage(scaled, x, y, null);
					}
				}
				g.dispose();*/
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








