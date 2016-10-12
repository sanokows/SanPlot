import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game extends JPanel implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener{
		static double Xsize = 2*Math.PI;
		static double Ysize = 3*Math.PI;
		static Plot p = new Plot(Xsize, Ysize, true);
		static double observerDistance = 1000;
		static double[] observer = {-1,-1,1};
		public double projectionDistance = 1000;
		static int width = 1920;
		static int height = 1080;
		static int halfWidth = width/2;
		static int halfHeight = height/2;
		static int[][][] frameMatrix = new int[width][height][2];
		
		static JFrame frame = new JFrame("Game");
		static Game game = new Game();
		
		//mouse stuff
		public static int[] b1pPos = {0,0}; //button 1 pressed pos
		public static int[] b1rPos = {0,0}; // button 1 released pos
		public static boolean b1p = false; //button 1 pressed
		public static boolean b1r = false; // button 1 released
		public static boolean pressed = false;
		public static boolean released = false;
		public static int[] pressedP = {0,0};
		public static int[] releasedP = {0,0};
		public static boolean rotation = false;
		public static boolean zoom = false;
		public static double zoomValue = 1;
		final static double zoomMinValue = 0.1;
		final static double zoomMaxValue = 2*observerDistance;

		
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.BLACK);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		long startTime = System.currentTimeMillis();


		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

		//polar plot

		ArrayList<int[][]> list = new ArrayList<int[][]>();
		for (int i = 0; i < p.squares.size(); i++){
			Square s = p.squares.get(i);
			int k = s.length();
			int[] xkoord = new int[k+1];
			int[] ykoord = new int[k+1];
			int[] xR3 = new int[k+1];
			int[] yR3 = new int[k+1];
			int[] zR3 = new int[k+1];
			int[][] matrix = {xkoord,ykoord, xR3, yR3, zR3};
			Node h = p.squares.get(i).head;
			for(int j = 0; j < k; j++){
				if(h != null){
					int[] koordinates = {(int) h.vector[0],(int) h.vector[1],(int) h.vector[2]};
					int[] frameKoord = To2D.returnPlotVec(observer, koordinates, projectionDistance);	
					if(frameKoord != null){
						xkoord[j] = frameKoord[0] + halfWidth;
						ykoord[j] = -frameKoord[1] + halfHeight;
						xR3[j] = frameKoord[2];
						yR3[j] = frameKoord[3];
						zR3[j] = frameKoord[4];
					}
					h = h.next;
				}
			}
			xkoord[k] = xkoord[0];
			ykoord[k] = ykoord[0];
			xR3[k] = xR3[0];
			yR3[k] = yR3[0];
			zR3[k] = zR3[0];
			list.add(i, matrix);

			//frameMatrix[x][y] = stuff for distance
		}

		list = Rendering.sortSquares(list);
		for(int i = 0; i < list.size(); i++){
			/*Random r = new Random();
			Color c = new Color(
			    r.nextInt(256),
			    r.nextInt(256),
			    r.nextInt(256)
			);*/
			
			
			g2d.setColor(Color.BLUE);
			g2d.fillPolygon(list.get(i)[0],list.get(i)[1],list.get(i)[0].length);

			g2d.setColor(Color.BLACK);
			g2d.drawPolyline(list.get(i)[0],list.get(i)[1],list.get(i)[0].length);
			String y = Integer.toString(list.size()-i);
			g2d.drawString(y,list.get(i)[0][0]+ (list.get(i)[0][1]-list.get(i)[0][0])/2,list.get(i)[1][0]+ (list.get(i)[1][1]-list.get(i)[1][0])/2);
			

		}

		g2d.drawImage(img, 0, 0, null);
		
		System.out.println("Render time: " + (System.currentTimeMillis() - startTime) + " ms");
	}
	
	public static boolean checkWidth(int w){
		return (w >= 0) && (w < width);
	}
	
	public static boolean checkHeight(int h){
		return (h >= 0) && (h < height);
	}
	
	public static int clampWidth(int w){
		if (w < 0){
			return 0;
		} else if (w >= width){
			return width - 1;
		} else {
			return w;
		}
	}
	
	public static int clampHeight(int h){
		if (h < 0){
			return 0;
		} else if (h >= height){
			return height - 1;
		} else {
			return h;
		}
	}
	public static void pressed(){
		if(b1p){
			pressedP[0] = b1pPos[0]; 
			pressedP[1] = b1pPos[1]; 
			b1p = false;
		}
		if(b1r){
			releasedP[0] = b1rPos[0];
			releasedP[1] = b1rPos[1];
			b1r = false;
		}
	}
	public static void renew(){
		boolean change = false;
		if(rotation){
			pressed();
			rotation = false;
			int[] vector = {-(pressedP[0] - releasedP[0]),-(pressedP[1] - releasedP[1])};
			System.out.println(Math.abs(vector[0]) + Math.abs(vector[1]));
			if(Math.abs(vector[0]) + Math.abs(vector[1]) != 0){
				double[] n = {vector[1], 0,-vector[0]};
				n = VektorCalculus.normVector(n);
				double a = Math.sqrt((Math.pow(vector[0],2)+Math.pow(vector[1],2)))/width * 3.141;
				pressedP[0] = releasedP[0];
				pressedP[1] = releasedP[1];
				observer = VektorCalculus.rotate(observer,n, a);
				change = true;
			}
		}

		if(change){
			resetMatrix(frameMatrix);
		}
	}
	public static void main(String[] args) throws InterruptedException{
		frame.add(game);
		frame.setSize(width, height);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.setDoubleBuffered(false);
		frame.addKeyListener(game);
		frame.addMouseListener(game);
		frame.addMouseMotionListener(game);
		frame.addMouseWheelListener(game);

		observer = VektorCalculus.normVector(observer);
		observer = VektorCalculus.multiplicate(observer, observerDistance);
		while (true){
			game.repaint();
			renew();

			
			//Thread.sleep(100);
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		if(arg0.getButton() == 1){
			pressed = true;
			rotation = false;
			b1pPos[0] = arg0.getX();
			b1pPos[1] = arg0.getY();
			b1p = true;
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		if(arg0.getButton() == 1){
			pressed = false;
		}
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		if(pressed){
			rotation = true;
			b1r = true;
			b1rPos[0] = arg0.getX();
			b1rPos[1] = arg0.getY();
		}
	}
		
	

	@Override
	public void mouseMoved(MouseEvent arg0) {
		
		
	}
	public static void resetMatrix(int[][][] matrix){
		for(int i = 0; i < matrix.length; i ++){
			for(int j = 0; j < matrix[0].length; j ++){
				for(int k = 0; k < matrix[0][0].length; k ++){
					frameMatrix[i][j][k] = 0;
				}
			}
		}
		
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {
		projectionDistance -= 100*arg0.getWheelRotation();
	
	}

}