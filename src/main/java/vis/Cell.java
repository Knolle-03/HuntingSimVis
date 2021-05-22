package vis;

import processing.core.PApplet;
import processing.core.PConstants;

public class Cell {

  PApplet sketch;

  int col;
  int row;
  int size;

  boolean hasPrey = false;
  boolean hasObstacle = false;
  boolean hasHighGrass = false;
  boolean hasPredator = false;


  public Cell(PApplet sketch, int col, int row, int size) {
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
    sketch.strokeWeight(5);

    // top
    sketch.line(x, y, x + size, y);
    //right
    sketch.line(x + size, y, x + size, y + size);
    //bottom
    sketch.line(x, y + size, x + size, y + size);
    // left
    sketch.line(x, y, x, y + size);

    if (hasObstacle) {
      sketch.stroke(0, 255);
      sketch.textAlign(PConstants.CENTER, PConstants.CENTER);
      //sketch.textSize();
      sketch.text("Rock", x + size / 2f, y + 1.5f *size / 2f);
    }

    if (hasPredator) {
      sketch.noStroke();
      sketch.fill(255, 100);
      sketch.rectMode(PConstants.CORNER);
      sketch.rect(x, y, size, size);
    }

    if (hasPrey) {
      sketch.noStroke();
      sketch.fill(255, 100);
      sketch.rectMode(PConstants.CORNER);
      sketch.rect(x, y, size, size);
    }

  }

  public void highlight() {
    int x = col * size;
    int y = row * size;
    sketch.noStroke();
    sketch.fill(255,0,0, 100);
    sketch.rect(x, y, size, size);

  }

  @Override
  public String toString() {
    return "Cell{" +
            ", col=" + col +
            ", row=" + row +
            '}';
  }

  //############# Getters and Setters ###################


  public PApplet getSketch() {
    return sketch;
  }

  public void setSketch(PApplet sketch) {
    this.sketch = sketch;
  }

  public String get2dIndex() {
    return String.format("col: %s row: %s", col, row);
  }



}
