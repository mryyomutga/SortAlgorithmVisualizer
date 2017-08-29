import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import ddf.minim.*; 
import ddf.minim.signals.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class SortAlgorithmVisualizer extends PApplet {




SortData sd;

Minim minim;
AudioOutput out;
SineWave sine;

public void setup(){
	
	background(0);
	// noStroke();
	// strokeWeight(0.2);
	
	frameRate(60);
	sd = new SortData();
	// for(int i = 0; i < data.length; i++){
	// 	println("data[" + i + "]:" + data[i]);
	// }
	for(int i = 0; i < sd.data.length; i++){
		rect(i,height,1, -(sd.data[i]));
	}
	minim = new Minim(this);
  	out = minim.getLineOut(Minim.STEREO);
	sine = new SineWave(440, 0.01f, out.sampleRate());
	sine.portamento(10);
	out.addSignal(sine);
}

public void draw(){
	sd.Sort2();
	sd.update();
}

public void stop(){
  //\u30d7\u30ed\u30b0\u30e9\u30e0\u306e\u7d42\u4e86\u51e6\u7406
  out.close();
  minim.stop();
  super.stop();
}

public void keyPressed(){
	sd = new SortData();
}

class SortData{
	int[] data = new int[width/4];
	int current = 0;
	int prev;

	SortData(){
		setData();
	}
	public void setData(){
		data[0] = PApplet.parseInt(random(0, width));
		int rand;
		boolean flag = false;
		for(int i = 1; i < data.length; i++){
			rand = PApplet.parseInt(random(0, width));
			for(int j = 0; j < i; j++){
				if(data[j] == rand && flag == false){
					flag = true;
				}
				if(flag){
					flag = false;
					i -= 1;
				}
				else{
					data[i] = rand;
				}
			}
		}
	}
	public void update(){
		background(0);
		for(int i = 0; i < data.length; i++){
			if(i == current){
				fill(255, 0, 0);
				rect(current * 4, height, 1 * 4, -(sd.data[current]));
				float freq = map(data[prev], 0, width, 220, 1320);
				sine.setFreq(freq);
				// sine.setAmp(0.02);
			}
			else{
				fill(255, 255, 255);
				rect(i * 4, height, 1 * 4, -(sd.data[i]));
			}
		}
		prev = current - 1;
	}
	public void finish(){
		background(0);
		for(int i = 0; i < data.length; i++){
			if(i == current){
				fill(255, 0, 0);
				rect(current * 4, height, 1 * 4, -(sd.data[current]));
			}
			else if(i < current){
				fill(0, 255, 0);
				rect(i * 4, height, 1 * 4, -(sd.data[i]));
			}
			else{
				fill(255, 255, 255);
				rect(i * 4, height, 1 * 4, -(sd.data[i]));
			}

		}
	}
	public void Sort1(){
		int tmp = 0;
		int hc = current;
		for(int i = 0; i < data.length-1; i++){
			if(data[i] > data[i + 1]){
				tmp = data[i];
				data[i] = data[i + 1];
				data[i + 1] = tmp;
				current = i;
			}
		}
		current++;
	}
	public void Sort2(){
		int tmp = 0;
		int hc = current;
		// out.addSignal(data[current]);
		for(int i = 0; i < hc; i++){
			if(data[i] > data[i + 1]){
				tmp = data[i];
				data[i] = data[i + 1];
				data[i + 1] = tmp;
				current = i;
			}
			// update();
		}
		if(current < data.length){
			current++;
		}
	}
}
  public void settings() { 	size(640, 640); 	smooth(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "SortAlgorithmVisualizer" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
