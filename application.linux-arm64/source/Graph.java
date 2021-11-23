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
  
  
  public boolean isSelected = false;
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
  
  public boolean contains(Node n){
    return (from == n || to ==n);
  }
  
  public boolean equals(Edge a){
    return (a.from == from && a.to == to )||(a.from == to && a.to == from);
  }
}



int[][] graph;
ArrayList<Node> nodes;
ArrayList<Edge> edges;

public void setup(){
  
  
  nodes = new ArrayList<Node>();
  edges = new ArrayList<Edge>();

}

Node overNode = null;
boolean isOver =false;

final int NODE_RADIUS = 25;

public void draw(){
  background(255);
  fill(0);
  textSize(height*0.02f);
  textAlign(BOTTOM, BOTTOM);
  text("Created by Mirian Shilakadze", 0, height);
  
  for(Edge i : edges){
    line(i.from.x, i.from.y, i.to.x, i.to.y);
  }
  
  for(Node i: nodes){
    if(!i.isSelected){
      fill(255);
      circle(i.x, i.y, NODE_RADIUS);
    }
    else{
      fill(200);
      circle(i.x, i.y, NODE_RADIUS);
    }
    textSize(13);
    fill(0);
    textAlign(CENTER, CENTER);
    text(i.id+1, i.x, i.y-2); 

    if(!isOver && dist(mouseX,mouseY,i.x,i.y)< NODE_RADIUS - 5){
      isOver = true;
      overNode = i;
    }
  }
  
  if(overNode != null && dist(mouseX,mouseY,overNode.x,overNode.y)>NODE_RADIUS -5){
    isOver = false;
  }
}

boolean locked = false;
Node lastSelected = null;

public void mousePressed() {
  if(mouseButton == RIGHT && isOver){
    if(lastSelected == null){
      lastSelected = overNode;
      lastSelected.isSelected = true;
    }
    else{
     if(lastSelected == overNode){
        lastSelected.isSelected = false;
        lastSelected = null;
     }
     else{
       Edge adgeToAdd = new Edge(lastSelected, overNode);
       boolean canAdd = true;
       for(Edge i : edges){
          if(i.equals(adgeToAdd))  canAdd =false;
       }
       if(canAdd){
         edges.add(adgeToAdd);
         lastSelected.isSelected = false;
         lastSelected = null;
       }
     }
    }
  }
  
  if(mouseButton == LEFT && isOver) { 
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

boolean isPressed = false;

public void keyReleased(){
  isPressed = false;
}

public void keyPressed(){
  
  if(!isPressed &&!isOver&& key == 'a') {
    nodes.add(new Node(mouseX,mouseY,nodes.size()));
    isPressed = true;
  }
  
  if(!isPressed && lastSelected != null && key == 'r'){
    ArrayList<Edge> edgesToRemove = new ArrayList<Edge>();
    for(Edge i: edges){
      if(i.contains(lastSelected))
         edgesToRemove.add(i);
    }
    edges.removeAll(edgesToRemove);
    for(int i = nodes.indexOf(lastSelected); i < nodes.size(); i++){
      nodes.get(i).id--;
    }
    
    nodes.remove(lastSelected);
    
    lastSelected = null;
  }
  
}
  public void settings() {  size(1280,720); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Graph" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
