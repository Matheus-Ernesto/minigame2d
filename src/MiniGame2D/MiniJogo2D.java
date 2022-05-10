package MiniGame2D;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.util.Random;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Point;

import javax.swing.*;

public class MiniJogo2D extends Thread{
	private static JFrame janela = new JFrame();
	private static GameEngine2D engine = new GameEngine2D();
	static GameEngine2D.KeyMapper keymanager;
	public static KeyEvent key;
	static int fps = 0;
	static Point pl;
	static int pontos;
	static double multPLayer = 0.01;
	static Random rando = new Random();
	static String move = "moveRight";
	static String mission = "Get the blue points";

	public static void main(String[] args) {
		//Cria uma janela
		janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		janela.setTitle("Mini Jogo 2D");
		janela.setBounds(100, 100, 800, 600);
		janela.setLayout(new BorderLayout());
		janela.setVisible(true);
		janela.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				engine.stop();
			}
		});
		JLabel instrucoes = new JLabel();
		instrucoes.setText("w,a,s,d or arrows = move; r = restart; points:" + pontos + "; fps: " + fps + ";" + mission);
		instrucoes.setFont(instrucoes.getFont().deriveFont((float) 24.0));
		janela.add(instrucoes, BorderLayout.NORTH);
		
		
		keymanager = new GameEngine2D.KeyMapper() {
			@Override
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				switch (key) {
				case 87:
					move = "moveUp";
					break;
				case 38:
					move = "moveUp";
					break;
				case 65:
					move = "moveLeft";
					break;
				case 37:
					move = "moveLeft";
					break;
				case 83:
					move = "moveDown";
					break;
				case 40:
					move = "moveDown";
					break;
				case 68:
					move = "moveRight";
					break;
				case 39:
					move = "moveRight";
					break;
				case 82:
					move = "restart";
					break;
				default:
					System.out.println(e.getKeyCode());
					break;
				}
			}
		};
		janela.addKeyListener(keymanager);
		
		GameEngine2D.Scene scene = new GameEngine2D.Scene();
		GameEngine2D.Texture texture = new GameEngine2D.Texture();
		GameEngine2D.RenderWindow window = new GameEngine2D.RenderWindow();
		GameEngine2D.Camera camera = new GameEngine2D.Camera();
		janela.add(window, BorderLayout.CENTER);
		
		texture.setBaseColor(Color.GREEN);
		
		GameEngine2D.GameObject border1 = new GameEngine2D.GameObject();
		border1.setPosition(new GameEngine2D.Vector2d(0, 5));
		border1.setScale(new GameEngine2D.Vector2d(10.5,0.5));
		border1.setTexture(texture);
		border1.setName("Border");
		scene.addObject(border1);
		
		GameEngine2D.GameObject border2 = new GameEngine2D.GameObject();
		border2.setPosition(new GameEngine2D.Vector2d(0,-5));
		border2.setScale(new GameEngine2D.Vector2d(10.5,0.5));
		border2.setTexture(texture);
		border2.setName("Border");
		scene.addObject(border2);
		
		GameEngine2D.GameObject border3 = new GameEngine2D.GameObject();
		border3.setPosition(new GameEngine2D.Vector2d(5, 0));
		border3.setScale(new GameEngine2D.Vector2d(0.5,10.5));
		border3.setTexture(texture);
		border3.setName("Border");
		scene.addObject(border3);
		
		GameEngine2D.GameObject border4 = new GameEngine2D.GameObject();
		border4.setPosition(new GameEngine2D.Vector2d(- 5, 0));
		border4.setScale(new GameEngine2D.Vector2d(0.5,10.5));
		border4.setTexture(texture);
		border4.setName("Border");
		scene.addObject(border4);

		GameEngine2D.Texture texturePlayer = new GameEngine2D.Texture();
		texturePlayer.setBaseColor(Color.RED);
		
		GameEngine2D.GameObject player = new GameEngine2D.GameObject();
		player.setPosition(new GameEngine2D.Vector2d(0,0));
		player.setScale(new GameEngine2D.Vector2d(0.5,0.5));
		player.setTexture(texturePlayer);
		player.setName("Player");
		scene.addObject(player);
		
		GameEngine2D.Texture texturePoint = new GameEngine2D.Texture();
		texturePoint.setBaseColor(Color.BLUE);
		
		GameEngine2D.GameObject point = new GameEngine2D.GameObject();
		point.setPosition(new GameEngine2D.Vector2d(rando.nextInt(10) - 4.5,rando.nextInt(10) - 4.5));
		point.setScale(new GameEngine2D.Vector2d(0.5,0.5));
		point.setTexture(texturePoint);
		point.setName("point");
		scene.addObject(point);
		
		camera.setPosition(new GameEngine2D.Vector2d(0,0));
		camera.setRadius(15);
		
		window.setBackground(Color.GRAY);

		window.setScene(scene);
		window.setCamera(camera);
		window.RenderFrame(99999);
		
		engine.createRunnableStart(() -> {
			instrucoes.setText("w,a,s,d or arrows = move; r = restart; points:" + pontos + "; fps: " + window.getFramerate() + ";" + mission);
		}, 1);
		
		engine.createRunnableStart(() -> {
			//Border Game Over
			if(player.getPosition().getX() >= 4.5 || player.getPosition().getY() >= 4.5 || player.getPosition().getX() <= -4.5 || player.getPosition().getY() <= -4.5) {
				multPLayer=0;
				mission = "You died!";
			}
			
			//Move Player
			switch (move) {
			case "moveUp":
				player.setPosition(new GameEngine2D.Vector2d(player.getPosition().getX(), player.getPosition().getY() - multPLayer));
				break;
			case "moveRight":
				player.setPosition(new GameEngine2D.Vector2d(player.getPosition().getX() + multPLayer, player.getPosition().getY()));
				break;
			case "moveDown":
				player.setPosition(new GameEngine2D.Vector2d(player.getPosition().getX(), player.getPosition().getY() + multPLayer));
				break;
			case "restart":
				point.setPosition(new GameEngine2D.Vector2d(rando.nextInt(10) - 4.5,rando.nextInt(10) - 4.5));
				pontos = 0;
				multPLayer = 0.01;
				move = "moveRight";
				mission = "Get the blue points";
				player.setPosition(new GameEngine2D.Vector2d(0,0));
				break;
			default:
				player.setPosition(new GameEngine2D.Vector2d(player.getPosition().getX() - multPLayer, player.getPosition().getY() ));
				break;
			}
			
			//Point
			if(player.getPosition().getY() >= point.getPosition().getY()-0.5 && player.getPosition().getY() <= point.getPosition().getY()+0.5 
					&& player.getPosition().getX() >= point.getPosition().getX()-0.5 && player.getPosition().getX() <= point.getPosition().getX()+0.5) {
				multPLayer+=0.01;
				point.setPosition(new GameEngine2D.Vector2d(rando.nextInt(10) - 4.5,rando.nextInt(10) - 4.5));
				pontos++;
			}
		}, 120);
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		janela.setMaximizedBounds(env.getMaximumWindowBounds());
		janela.setExtendedState(janela.getExtendedState() | Frame.MAXIMIZED_BOTH);
         
	}
}

class GameEngine2D{
	private static boolean sair = false;
	
	public void createRunnableStart(Runnable run, int FPS){
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
	
	static abstract class MouseMapper extends MouseAdapter{}
	
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
				if(gameObj.getPosition().getX() <= radius || gameObj.getPosition().getY() <= radius) {
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
		private Vector2d position;
		private Vector2d scale;
		private Vector2d rotation;
		private String name;
		
		public void setName(String name_t) {
			this.name = name_t;
		}
		
		public String getName(){
			return this.name;
		}
		
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
		private double radius = 0;
		
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
	
	static class Texture{
		private Color baseColor;
		
		public void setBaseColor(Color color_t){
			this.baseColor = color_t;
		}
		
		public Color getBaseColor(){
			return this.baseColor;
		}
	}
	
	static class RenderWindow extends JPanel {
		
		private static final long serialVersionUID = 1L;
		
		private Scene scene = new Scene();
		private Camera camera = new Camera();
		private boolean stop = false;
		private int fps;

		ArrayList<GameObject> gameArray = new ArrayList<GameObject>();
		
		public void setScene(Scene scene_t) {
			this.scene = scene_t;
		}
		
		public void setCamera(Camera camera_t) {
			this.camera = camera_t;
		}
		
		public Vector2d pixelToMeter(Point p) {
			double z = camera.getRadius();
			
			double MX = ((p.getX() / this.getWidth()) * 100 * z) / 100 - (z/2);
			double MY = ((p.getY() / this.getHeight()) * 100 * z) / 100 - (z/2);
			
			return new Vector2d(MX,MY);
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			this.fps++;
			g.clearRect(0, 0, this.getWidth(), this.getHeight());
			double x = camera.getPosition().getX();
			double y = camera.getPosition().getY();
			double z = camera.getRadius();

			double PartX = this.getHeight() / z;
			double PartY = this.getHeight() / z;
			double X_cart = x + z;
			double Y_cart = -y + z / 2;
			
			ArrayList<GameObject> comps = scene.getObjectsOnRadius(new Vector2d(x,y), z);
			for (GameObject comp : comps) {
				int width = (int) Math.round(PartX * comp.getScale().getX());
				int height = (int) Math.round(PartY * comp.getScale().getY());
				
				int x_local = (int) Math.round(PartX * (comp.getPosition().getX() + X_cart) - width/2);
				int y_local = (int) Math.round(PartY * (comp.getPosition().getY() + Y_cart) - height/2);
				
				g.setColor(comp.getTexture().getBaseColor());
				g.fillRect(x_local, y_local, width, height);
			}
		}
		
		public void RenderFrame(int FPS){
				int FramePorSeconds = 1000 / ((FPS == 0) ? 1 : FPS);
				new Thread(() -> {
					while(!stop){
						try {
							Thread.sleep(FramePorSeconds);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						this.repaint();
					}
			}).start();
		}
		
		public void stopRender() {
			this.stop = true;
		}
		
		public int getFramerate() {
			int fps_t = this.fps;
			fps = 0;
			return fps_t;
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
		
		public double hipotenusa(Vector2d catect) {
			double side_a = Math.abs(this.getX() - catect.getX());
			double side_b = Math.abs(this.getY() - catect.getY());
			double side_hipo = (side_a * side_a) + (side_b * side_b);
			return side_hipo;
		}
	}
}








