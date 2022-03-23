package MiniGame2D;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class MiniJogo2D{
	private static JFrame janela = new JFrame();
	private static GameEngine engine = new GameEngine();
	static Image imagem;
	public static Canvas canvas = new Canvas();

	public static void main(String[] args) {
		try {
			imagem = ImageIO.read(MiniJogo2D.class.getResource("tile_map32.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

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
		
		//icone.
		
		//Criar e atribuir a engine
		engine.setFPS(30);
		engine.setBeforeframeFPS(10);
		engine.setRunnables(() -> beforeFrame(), () -> onFrame(), () -> afterSeconds());

		//Criar canvas
		mapa m = new mapa();
		m.setBounds(0,0,150,150);
		m.setBackground(Color.LIGHT_GRAY);
		
		janela.add(m);
		janela.setVisible(true);
	}
	
	public static void beforeFrame() {
		
	}
	
	public static void onFrame() {
		
	}
	
	public static void afterSeconds() {
		
	}
}

class GameEngine{
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
	
	public void setActionByKey(JComponent component,int focus, Action action, String name, KeyStroke key) {
		component.getInputMap(focus).put(key, name);
		component.getActionMap().put(name,action);
	}
}









