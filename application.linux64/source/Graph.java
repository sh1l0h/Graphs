import processing.core.*; 

import java.util.ArrayList; 

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
  public int getId() {
	  return this.id;
  }
  

}

class Edge {
  
  public Node from;
  public Node to;

  public Edge(Node from, Node to){
    this.from = from;
    this.to = to;
    
  }
  
  public boolean equals(Edge a){
    return (a.from == from && a.to == to )||(a.from == to && a.to == from);
  }
  public Node getFrom() {
	  return this.from;
  }
  public Node getTo() {
	  return this.to;
  }
  public String toString() {
	  return (from.id+1)+" -> "+(to.id+1);
  }
  
}



int[][] graph = new int[0][0];
ArrayList<Node> nodes;
ArrayList<Edge> edges;
ArrayList<Integer> matrix;

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
         graph[adgeToAdd.getFrom().id][adgeToAdd.getTo().id]=1;
         graph[adgeToAdd.getTo().id][adgeToAdd.getFrom().id]=1;
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
  this.createMatrix();
}
public int[][] createMatrix() {

    System.out.println("------------------");
    for(int i = 0; i<nodes.size();i++) {
 	   for(int j = 0;j<nodes.size();j++) {
 		   System.out.print(graph[i][j] + " ");
 	   }
 	   System.out.print("\n");
    }
    return graph;
}



public void mouseDragged() {
  if(mouseButton == LEFT && locked) {
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
  
  if(key == 'a' && !isPressed) {
    nodes.add(new Node(mouseX,mouseY,nodes.size()));
    graph = generateNewMatrix();
    isPressed = true;
  }
}
public int[][] generateNewMatrix() {
	int[][] tmp = new int[graph.length+1][graph.length+1];
	for(int i = 0; i<graph.length;i++) {
		for(int j = 0;j<graph[0].length;j++ ) {
			tmp[i][j] = graph[i][j];
		}
	}
	return tmp;
}

  public void settings() {  size(1200,600); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Graph" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
    
  }
}
