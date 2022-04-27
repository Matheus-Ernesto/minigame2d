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
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

public class MiniJogo2D{
	private static JFrame janela = new JFrame();
	private static GameEngine2D engine = new GameEngine2D();
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
		janela.setVisible(true);
		janela.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				engine.stop();
			}
		});
		
		engine.createRunnableStart(() -> {
			fps++;
		}, 99999);
		engine.createRunnableStart(() -> {
			System.out.println(fps + " fps");
			fps = 0;
		}, 1);
		
		keymanager = new GameEngine2D.KeyMapper() {
			@Override
			public void keyTyped(KeyEvent e) {
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
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
				case 'c':
					vetorC = -0.1f;
					break;
				case 'v':
					vetorV = 0.1f;
					break;
				default:
					
					break;
				}
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
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
				case 'c':
					vetorC = 0f;
					break;
				case 'v':
					vetorV = 0f;
					break;
				default:
					
					break;
				}
			}
		};
		
		janela.addKeyListener(keymanager);
		
		GameEngine2D.Scene scene = new GameEngine2D.Scene();
		GameEngine2D.GameObject gmObject1 = new GameEngine2D.GameObject();
		GameEngine2D.Camera camera = new GameEngine2D.Camera();
		GameEngine2D.Texture texture = new GameEngine2D.Texture();
		GameEngine2D.RenderWindow window = new GameEngine2D.RenderWindow();
		
		texture.setBaseColor(Color.GREEN);
		
		gmObject1.setPosition(new GameEngine2D.Vector2d(0,0));
		gmObject1.setScale(new GameEngine2D.Vector2d(1,1));
		gmObject1.setTexture(texture);
		
		camera.setPosition(new GameEngine2D.Vector2d(0,0));
		camera.setRadius(15);
		
		scene.addObject(gmObject1);

		window.setBounds(0,0,800,600);
		window.setBackground(Color.red);

		janela.add(window);
		
		window.paintAllObjects(camera, scene);
		
	}
}

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

	static abstract class KeyMapper extends KeyAdapter{}
	
	static class Scene{
		ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();
		
		public void addObject(GameObject gm_t) {
			this.gameObjects.add(gm_t);
		}
		
		public void removeObject(int index) {
			this.gameObjects.remove(index);
		}
		
		public void setObjects(ArrayList<GameObject> gm_array) {
			this.gameObjects = gm_array;
		}
		
		public ArrayList<GameObject> getObjects(){
			return gameObjects;
		}
		
		public ArrayList<GameObject> getObjectsOnRadius(Vector2d local, double radius){
			ArrayList<GameObject> radiusObjs = new ArrayList<GameObject>();
			
			for (GameObject gameObj : gameObjects) {
				if(gameObj.position.getX() <= radius || gameObj.position.getY() <= radius) {
					radiusObjs.add(gameObj);
				}
			}
			
			return radiusObjs;
		}
		
		public ArrayList<GameObject> getGameOBject() {
			return gameObjects;
		}
	}
	
	static class Object{
		public Vector2d position;
		public Vector2d scale;
		public Vector2d rotation;
		
		public void setPosition(Vector2d pos_t) {
			this.position = pos_t;
		}
		
		public Vector2d getPosition(){
			return this.position;
		}
		
		public void setScale(Vector2d scal_t) {
			this.scale = scal_t;
		}
		
		public Vector2d getScale(){
			return this.scale;
		}
		
		public void setRotation(Vector2d rot_t) {
			this.rotation = rot_t;
		}
		
		public Vector2d getRotation(){
			return this.rotation;
		}
	}
	
	static class Camera extends Object{
		public double radius = 0;
		
		public void setRadius(double rad_t) {
			this.radius = rad_t;
		}
		
		public double getRadius(){
			return this.radius;
		}
	}
	
	static class GameObject extends Object{
		private Texture texture;
		
		public void setTexture(Texture tex_t) {
			this.texture = tex_t;
		}
		
		public Texture getTexture() {
			return texture;
		}
		
	}
	
	static class Light extends Object{
		
	}
	
	static class Texture{
		private Color baseColor;
		
		public void setBaseColor(Color color_t){
			this.baseColor = color_t;
		}
		
		public Color getBaseColor(){
			return this.baseColor;
		}
	}
	
	static class RenderWindow extends JPanel{
		
		private static final long serialVersionUID = 1L;
		private Graphics graphics;

		public void paintAllObjects(Camera camera, Scene scene) {
			graphics = this.getGraphics();
			//Camera Position
			double x = camera.getPosition().getX();//0
			double y = camera.getPosition().getY();//0
			double z = camera.getRadius();//15
			
			double PartX = getWidth() / z * 2;
			//double PartY = (stretch) ? this.getHeight() / z: this.getWidth() / z;
			double PartY = this.getHeight() / z * 2;
			
			double X_cart = -x + z / 2;//7,5
			double Y_cart = y + z / 2;//7,5
			System.out.println("Part(" + PartX + "," + PartY + "), Cart(" + X_cart + "," + Y_cart + ")");
			ArrayList<GameObject> comps = scene.getObjectsOnRadius(new Vector2d(x,y), z);
			for (GameObject comp : comps) {
				int width = (int) (PartX * comp.getScale().getX());
				int height = (int) (PartY *  comp.getScale().getY());
				
				int x_local = (int) (PartX * (comp.getPosition().getX() + X_cart) - width/2);
				int y_local = (int) (PartY * (comp.getPosition().getY() + Y_cart) - height/2);
				graphics.setColor(comp.getTexture().getBaseColor());
				graphics.fillRect(x_local, y_local, width, height);
				System.out.println("G(" + x_local + "," + y_local + "," + width + "," + height + "), C(" + comp.getTexture().getBaseColor().toString());
			}
			repaint();
		}
	}
	
	static class Vector2d {
		private double x = 0;
		private double y = 0;
		
		public Vector2d(double x_t, double y_t) {
			this.x = x_t;
			this.y = y_t;
		}
		
		public void set(double x_t, double y_t) {
			this.x = x_t;
			this.y = y_t;
		}
		
		public void setX(double x_t) {
			 this.x = x_t;
		}
		
		public void setY(double y_t) {
			this.y = y_t;
		}
		
		public double getX() {
			return this.x;
		}
		
		public double getY() {
			return this.y;
		}
	}
}








