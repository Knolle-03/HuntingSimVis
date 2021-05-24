package vis;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

public class Cell {




  SimVis sketch;

  int col;
  int row;
  int size;

//  PLAIN,                          // 0
//  OBSTACLE,                       // 1
//  TARGET,                         // 2
//  HIGH_GRASS,                     // 3
//  PREDATOR,                       // 4
//  HIGH_GRASS_TARGET,              // 5
//  HIGH_GRASS_PREDATOR,            // 6
//  HIGH_GRASS_PREDATOR_TARGET,     // 7
//  TARGET_PREDATOR,                // 8

  public void updateCellState(int state) {

    resetCell();
    if (state == 0) hasNothing = true;
    else if(state == 1) hasObstacle = true;
    else if(state == 2) hasPrey = true;
    else if(state == 3) hasHighGrass = true;
    else if(state == 4) hasPredator = true;
    else if(state == 5) {
      hasHighGrass = true;
      hasPrey = true;
    }
    else if(state == 6) {
      hasHighGrass = true;
      hasPredator = true;
    }
    else if(state == 7) {
      hasHighGrass = true;
      hasPrey = true;
      hasPredator = true;
    }
    else if(state == 8) {
      hasPrey = true;
      hasPredator = true;
    }
  }

  private void resetCell() {
    hasNothing = false;
    hasObstacle = false;
    hasPrey = false;
    hasHighGrass = false;
    hasPredator = false;
  }

  boolean hasNothing = false;
  boolean hasObstacle = false;
  boolean hasPrey = false;
  boolean hasHighGrass = false;
  boolean hasPredator = false;


  public Cell(SimVis sketch, int col, int row, int size) {
    this.sketch = sketch;
    this.col = col;
    this.row = row;
    this.size = size;
  }

  // copy constructor
  public Cell(Cell cell) {
    this.sketch = cell.sketch;
    this.col = cell.col;
    this.row = cell.row;
    this.size = cell.size;
  }

  public void show() {
    int x = col * size;
    int y = row * size;
   // System.out.println("Cell's x: " + x + "    Cells y: " + y);
    sketch.stroke(0);
    sketch.strokeWeight(4);



    // top
    sketch.line(x, y, x + size, y);
    //right
    sketch.line(x + size, y, x + size, y + size);
    //bottom
    sketch.line(x, y + size, x + size, y + size);
    // left
    sketch.line(x, y, x, y + size);

    if (hasNothing) {
      sketch.noStroke();
      sketch.image(sketch.plainImage,x + 2, y + 2, size - 4, size - 4);
    }

    if (hasObstacle) {
      sketch.noStroke();
      sketch.image(sketch.obstacleImage, x + 2, y + 2, size - 4, size - 4);
    }

    if (hasPrey) {
      sketch.noStroke();
      sketch.image(sketch.deerImage, x + 3, y + 3, size / 2, size / 2);
    }

    if (hasHighGrass) {
      sketch.noStroke();
      sketch.image(sketch.highGrassImage, x + 3,y + 2 + (size / 2), size / 2, size / 2);
    }

    if (hasPredator) {
      sketch.noStroke();
      sketch.image(sketch.wolfImage, x + 2 + (size / 2), y + 3, size / 2, size / 2);
    }

  }


  @Override
  public String toString() {
    return "Cell{" +
            ", col=" + col +
            ", row=" + row +
            '}';
  }

  //############# Getters and Setters ###################


  public SimVis getSketch() {
    return sketch;
  }

  public void setSketch(SimVis sketch) {
    this.sketch = sketch;
  }

  public String get2dIndex() {
    return String.format("col: %s row: %s", col, row);
  }



}
