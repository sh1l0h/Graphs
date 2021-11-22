import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.Scanner; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Graph extends PApplet {

class Node {
  
  public int id = 0;
  public int x;
  public int y;
  
  public Node(int x, int y, int id){
    this.id = id;
    this.x = x;
    this.y = y;
  }

}

class Edge {
  
  public Node from;
  public Node to;

  public Edge(Node from, Node to){
    this.from = from;
    this.to = to;
  }
}



int[][] graph;
Node[] nodes;
ArrayList<Edge> edges;

public void settings(){
    Scanner in = new Scanner(System.in);
    size(in.nextInt(),in.nextInt());
    int nodesNumber = in.nextInt();
    graph = new int[nodesNumber][nodesNumber];
    
    for(int i =0; i < graph.length; i++){
      for(int j = 0; j < graph.length; j++) graph[i][j] = in.nextInt();
    }
    in.close();
}

public void setup(){
  nodes = new Node[graph.length];
  
  for(int i = 0; i < graph.length; i++) {
    nodes[i] = new Node((int)random(0,width),(int)random(0,height),i);
  }
  
  edges = new ArrayList<Edge>();
  
  for(int i =0; i < graph.length; i++){
    for(int j = i+1; j < graph.length; j++){
     if(graph[j][i] == 1){
        edges.add(new Edge(nodes[i],nodes[j]));
      }
    }
  }
}

Node overNode = null;
boolean isOver =false;

public void draw(){
  background(255);
  fill(0);
  textSize(height*0.02f);
  text("Created by Mirian Shilakadze", 0, height*0.99f);
  
  for(Edge i : edges){
    line(i.from.x, i.from.y, i.to.x, i.to.y);
  }
  
  for(Node i: nodes){
    fill(255);
    circle(i.x, i.y, 20);
    textSize(13);
    fill(0);
    text(i.id+1, i.x-3, i.y+4.5f); 

    if(!isOver && dist(mouseX,mouseY,i.x,i.y)<15){
      isOver = true;
      overNode = i;
    }
  }
  
  if(overNode != null && dist(mouseX,mouseY,overNode.x,overNode.y)>15){
    isOver = false;
  }
}

boolean locked = false;

public void mousePressed() {
  if(isOver) { 
    locked = true; 
  } else {
    locked = false;
  }
}

public void mouseDragged() {
  if(locked) {
    overNode.x = mouseX;
    overNode.y = mouseY;
  }
}

public void mouseReleased() {
  locked = false;
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Graph" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
