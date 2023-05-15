import tester.*;
import javalib.worldimages.*; // images, like RectangleImage or OverlayImages
import javalib.funworld.*; // the abstract World class and the big-bang library
import javalib.worldcanvas.*; // so we can view our images
import java.awt.Color; // general colors (as triples of red,green,blue values)



// to represent mobile 
interface IMobile {

  // computes the total weight of this IMobile
  int totalWeight();

  // computes the height of this IMobile
  int totalHeight();

  // computes whether this IMobile is balanced?
  boolean isBalanced();

  // combines this balanced IMobile with
  // the given balanced mobile and produces a new mobile
  IMobile buildMobile(IMobile mobile, int strLength, int structLength);

  // computes the struct length of this final IMobile
  int buildMobileHelp(IMobile mobile, int structLength);
  
  // computes the current width of this IMobile
  int curWidth();

  // computes the current width of the left side of this IMobile
  int curWidthLeft();

  // computes the current width of the right side of this IMobile
  int curWidthRight();

  //produces the image of this IMobile if 
  //it were hanging from the nail at some Posn
  WorldImage drawMobile();
}

// to represent Simple mobile
class Simple implements IMobile {
  int length;
  int weight;
  Color color;
  
  // the constructor
  Simple(int length, int weight, Color color) {
    this.length = length;
    this.weight = weight;
    this.color = color;

  }
  
  /* fields:
   * this.length ..........int 
   * this.weight ..........int 
   * this.color ......... Color
    methods:
    this.totalWeight () ...... int
    this.totalHeight ()  ...... int
    this.isBalanced ()......... boolean
    this.buildMobile (IMobile,int, int) ...... IMobile
    this.buildMobileHelp (IMobile, int)....... int
    this.curWidth ()  ............. int 
    this.curWidthLeft() ........... int
    this.curWidthRight()........... int
    this.drawMobileHelper() .........WorldImage
    this.drawMobile .................WorldImage
    methods for fields (none): 
   */
  
  // computes the total weight of this Simple mobile.
  public int totalWeight() {
    return this.weight;
  }

  // computes the height of this Simple mobile
  public int totalHeight() {
    return this.length + (this.weight / 10);
  }

  // computes whether this Simple Mobile is balanced?
  public boolean isBalanced() {
    return true;
  }

  // combines this balanced Simple mobile with
  // the given balanced mobile and produces a new mobile
  public IMobile buildMobile(IMobile mobile, int strLength, int structLength) {
    return new Complex(strLength, this.buildMobileHelp(mobile, structLength),
        structLength - this.buildMobileHelp(mobile, structLength), this, mobile);
  }

  // returns the strut length of this Simple mobile
  public int buildMobileHelp(IMobile mobile, int structLength) {
    return (this.totalWeight() * structLength) / (mobile.totalWeight() + this.totalWeight());
  }

  // computes the current width of this Simple Mobile
  public int curWidth() {
    if ((this.weight / 10 * 10) == this.weight) {
      return (this.weight / 10);
    }

    else {
      return (this.weight / 10) + 1;
    }
  }
  
  // computes the current width of the left side of this Simple Mobile
  public int curWidthLeft() {
    if ((this.weight / 10 * 10) == this.weight) {
      return ((this.weight / 10) / 2);
    }

    else {
      return (((this.weight / 10) + 1) / 2);
    }
  }

  //computes the current width of the right side of this Simple Mobile
  public int curWidthRight() {
    if ((this.weight / 10 * 10) == this.weight) {
      return ((this.weight / 10) / 2);
    }

    else {
      return (((this.weight / 10) + 1) / 2);
    }
  }

  //produces the image of this Simple Mobile if 
  //it were hanging from the nail at some Posn
  public WorldImage drawMobile() {
    return new AboveImage(new LineImage(new Posn(0, this.length * 70), Color.BLACK),
        new RectangleImage(this.curWidth() * 20, this.curWidth() * 20, OutlineMode.SOLID,
            this.color));
  }
}

// to represent a complex mobile
class Complex implements IMobile {
  int length;
  int leftside;
  int rightside;
  IMobile left;
  IMobile right;
  
  // the constructor
  Complex(int length, int leftside, int rightside, IMobile left, IMobile right) {
    this.length = length;
    this.leftside = leftside;
    this.rightside = rightside;
    this.left = left;
    this.right = right;

  }
  
  /* fields:
   * this.length ..........int 
   * this.weight ..........int 
   * this.color ......... Color
    methods:
    this.totalWeight () ...... int
    this.totalHeight ()  ...... int
    this.isBalanced ()......... boolean
    this.buildMobile (IMobile,int, int) ...... IMobile
    this.buildMobileHelp (IMobile, int)........int
    this.curWidth ()  ............. int 
    this.curWidthLeft() ........... int
    this.curWidthRight()........... int
    this.drawMobileHelper() .........WorldImage
    this.drawMobile .................WorldImage
    methods for fields:
    this.left.totalWeight () ...... int
    this.left.totalHeight ()  ...... int
    this.left.isBalanced ()......... boolean
    this.left.buildMobile (IMobile,int, int) ...... IMobile
    this.left.buildMobileHelp (IMobile, int)........int
    this.left.curWidth ()  ............. int 
    this.left.curWidthLeft() ........... int
    this.left.curWidthRight()........... int
    this.left.drawMobileHelper() .........WorldImage
    this.left.drawMobile .................WorldImage
    this.right.totalWeight () ...... int
    this.right.totalHeight ()  ...... int
    this.right.isBalanced ()......... boolean
    this.right.buildMobile (IMobile,int, int) ...... IMobile
    this.right.buildMobileHelp (IMobile, int)........int
    this.right.curWidth ()  ............. int 
    this.right.curWidthLeft() ........... int
    this.right.curWidthRight()........... int
    this.right.drawMobileHelper() .........WorldImage
    this.right.drawMobile .................WorldImage
   */
  
  // computes the total weight of this Complex mobile.
  public int totalWeight() {
    return this.left.totalWeight() + this.right.totalWeight();
  }

  // computes the height of this Complex mobile
  public int totalHeight() {
    return Math.max(this.left.totalHeight(), this.right.totalHeight()) + this.length;
  }

  // computes whether this Complex Mobile is balanced?
  public boolean isBalanced() {
    return this.left.totalWeight() * this.leftside == this.right.totalWeight() * this.rightside;
  }

  // combines this balanced Complex Mobile with
  // the given balanced mobile and produces a new mobile
  public IMobile buildMobile(IMobile mobile, int strLength, int structLength) {
    return new Complex(strLength, this.buildMobileHelp(mobile, structLength),
        structLength - this.buildMobileHelp(mobile, structLength), this, mobile);
  }

  // returns the strut length of this Complex mobile given the weights of both
  public int buildMobileHelp(IMobile mobile, int structLength) {
    return (Math.round(mobile.totalWeight() * structLength)
        / (mobile.totalWeight() + this.totalWeight()));
  }

  // computes the current width of this Complex Mobile
  public int curWidth() {

    if (this.curWidthLeft() < this.right.curWidthLeft()) {
      return this.right.curWidthLeft() + this.right.curWidthRight();
    }
    else if (this.curWidthRight() < this.left.curWidthRight()) {
      return this.left.curWidthRight() + this.left.curWidthLeft();
    }
    else {
      return this.curWidthLeft() + this.curWidthRight();
    }
  }

  // computes the current width of the left side of this Complex mobile
  public int curWidthLeft() {
    return this.leftside + this.left.curWidthLeft();
  }

  // computes the current width of the right side of this Complex mobile
  public int curWidthRight() {
    return this.rightside + this.right.curWidthRight();
  }

  //produces the image of this Complex Mobile if 
  public WorldImage drawMobile() {
    return new AboveAlignImage(AlignModeX.PINHOLE,
        new LineImage(new Posn(0, this.length * 40), Color.BLACK),
        (new BesideAlignImage(AlignModeY.TOP, left.drawMobile(),
            new BesideImage(new LineImage(new Posn(this.leftside * 30, 0), Color.BLACK),
                new LineImage(new Posn(this.rightside * 30, 0), Color.BLACK)),
            right.drawMobile())));
  }
}

// examples and test the represent Simple and Complex Mobiles
class ExamplesMobiles {

  // examples of the class Simple
  IMobile exampleSimple = new Simple(2, 20, Color.BLUE);
  IMobile simple1 = new Simple(1, 36, Color.BLUE);
  IMobile simple2 = new Simple(1, 12, Color.RED);
  IMobile simple3 = new Simple(2, 36, Color.RED);
  IMobile simple4 = new Simple(1, 60, Color.GREEN);
  IMobile simple5 = new Simple(2, 50, Color.BLACK);
  IMobile simple6 = new Simple(5, 70, Color.CYAN);
  IMobile simple7 = new Simple(8, 90, Color.DARK_GRAY);

  // examples of the class Complex
  IMobile exampleComplex = new Complex(1, 9, 3, this.simple1,
      new Complex(2, 8, 1, this.simple2, new Complex(2, 5, 3, this.simple3, this.simple4)));

  IMobile example3 = new Complex(2, 3, 4, this.exampleComplex,
      new Complex(3, 2, 3, this.simple5, this.simple6));

  IMobile build1 = new Complex(1, 2, 5, this.exampleSimple, this.simple1);
  IMobile build2 = new Complex(2, 4, 5, this.exampleComplex, this.exampleComplex);
  
  // example drawings of complex mobiles
  WorldImage complexDraw1 = 
      new AboveAlignImage(AlignModeX.PINHOLE,
      new LineImage(new Posn(0, 80), Color.BLACK),
      (new BesideAlignImage(AlignModeY.TOP, this.simple3.drawMobile(),
          new BesideImage(new LineImage(new Posn(150, 0), Color.BLACK),
              new LineImage(new Posn(90, 0), Color.BLACK)),
          this.simple4.drawMobile())));
  
  WorldImage complexDraw2 = 
      new AboveAlignImage(AlignModeX.PINHOLE,
          new LineImage(new Posn(0, 80), Color.BLACK),
          (new BesideAlignImage(AlignModeY.TOP, this.simple2.drawMobile(),
              new BesideImage(new LineImage(new Posn(240, 0), Color.BLACK),
                  new LineImage(new Posn(30, 0), Color.BLACK)),
              this.complexDraw1)));
  
  WorldImage complexDraw3 = 
      new AboveAlignImage(AlignModeX.PINHOLE,
          new LineImage(new Posn(0, 40), Color.BLACK),
          (new BesideAlignImage(AlignModeY.TOP, this.simple1.drawMobile(),
              new BesideImage(new LineImage(new Posn(270, 0), Color.BLACK),
                  new LineImage(new Posn(90, 0), Color.BLACK)),
              this.complexDraw2)));
  
  WorldImage build1Draw = 
      new AboveAlignImage(AlignModeX.PINHOLE,
          new LineImage(new Posn(0, 40), Color.BLACK),
          (new BesideAlignImage(AlignModeY.TOP, this.exampleSimple.drawMobile(),
              new BesideImage(new LineImage(new Posn(60, 0), Color.BLACK),
                  new LineImage(new Posn(150, 0), Color.BLACK)),
              this.simple1.drawMobile())));

  // shows image at the center of an equally-sized canvas,
  // and the text at the top of the frame is given by description
  boolean showImage(WorldImage image, String description) {
    int width = (int) Math.ceil(image.getWidth());
    int height = (int) Math.ceil(image.getHeight());
    WorldCanvas canvas = new WorldCanvas(width, height, description);
    WorldScene scene = new WorldScene(width, height);
    return canvas.drawScene(scene.placeImageXY(image, width / 2, height / 2)) && canvas.show();
  }

  // examples of WorldImage
  WorldImage testSimple = simple1.drawMobile();
  WorldImage testComplex = exampleComplex.drawMobile();

  // test for the totalWeight method for class Simple
  boolean testSimpleTotalWeight(Tester t) {
    return t.checkExpect(this.exampleSimple.totalWeight(), 20)
        && t.checkExpect(this.simple2.totalWeight(), 12)
        && t.checkExpect(this.simple4.totalWeight(), 60);
  }
  
  // test for the totalWeight method for class Complex
  boolean testComplexTotalWeight(Tester t) {
    return  t.checkExpect(this.exampleComplex.totalWeight(), 144)
        &&  t.checkExpect(this.example3.totalWeight(), 264)
        && t.checkExpect(this.build1.totalWeight(), 56);
  }

  // testing for the totalHeight method for class Simple
  boolean testSimpleTotalHeight(Tester t) {
    return t.checkExpect(this.exampleSimple.totalHeight(), 4)
        && t.checkExpect(this.simple1.totalHeight(), 4)
        && t.checkExpect(this.simple7.totalHeight(), 17)
        && t.checkExpect(this.simple5.totalHeight(), 7);

  }
  
  // testing for the totalHeight method for class Complex
  boolean testComplexTotalHeight(Tester t) {
    return t.checkExpect(this.exampleComplex.totalHeight(), 12)
        && t.checkExpect(this.example3.totalHeight(), 17)
        && t.checkExpect(this.build1.totalHeight(), 5);

  }

  // testing for the isBalanced method for class Simple
  boolean testSimpleIsBalanced(Tester t) {
    return t.checkExpect(this.exampleSimple.isBalanced(), true)
        && t.checkExpect(this.simple1.isBalanced(), true)
        && t.checkExpect(this.simple3.isBalanced(), true);
  }
  
  // testing for the isBalanced method for class Complex
  boolean testComplexIsBalanced(Tester t) {    
    return t.checkExpect(this.exampleComplex.isBalanced(), true)
        && t.checkExpect(this.example3.isBalanced(), false)
        && t.checkExpect(this.build1.isBalanced(), false);
  }

  // testing for the method CurWidth for class Simple
  boolean testSimpleCurWidth(Tester t) {
    return t.checkExpect(this.exampleSimple.curWidth(), 2)
        && t.checkExpect(this.simple1.curWidth(), 4)
        && t.checkExpect(this.simple5.curWidth(), 5);
  }
  
  // testing for the method CurWidth for class Complex
  boolean testComplexCurWidth(Tester t) {
    return t.checkExpect(this.exampleComplex.curWidth(), 21)
        && t.checkExpect(this.example3.curWidth(), 24)
        && t.checkExpect(this.build1.curWidth(), 10);
  }

  // testing for the method CurWidthLeft for class Simple
  boolean testSimpleCurWidthLeft(Tester t) {
    return t.checkExpect(this.exampleSimple.curWidthLeft(), 1)
        && t.checkExpect(this.simple1.curWidthLeft(), 2)
        && t.checkExpect(this.simple7.curWidthLeft(), 4);
  }
  
  // testing for the method CurWidthLeft for class Complex
  boolean testComplexCurWidthLeft(Tester t) {
    return t.checkExpect(this.exampleComplex.curWidthLeft(), 11)
        && t.checkExpect(this.example3.curWidthLeft(), 14) 
        && t.checkExpect(this.build1.curWidthLeft(), 3);
  }
  
  // testing for the method CurWidthRight for class Simple
  boolean testSimpleCurWidthRight(Tester t) {
    return t.checkExpect(this.exampleSimple.curWidthRight(), 1)
        && t.checkExpect(this.simple1.curWidthRight(), 2)
        && t.checkExpect(this.simple3.curWidthRight(), 2);
  }
  
  // testing for the method CurWidthRight for class Complex
  boolean testComplexCurWidthRight(Tester t) {
    return t.checkExpect(this.exampleComplex.curWidthRight(), 10)
        && t.checkExpect(this.example3.curWidthRight(), 10)
        && t.checkExpect(this.build1.curWidthRight(), 7);
  }
  
  // testing for the buildMobile() method
  boolean tesComplexBuild(Tester t) {
    return t.checkExpect(this.exampleSimple.buildMobile(this.simple1, 1, 7), this.build1)
        && t.checkExpect(this.simple1.buildMobile(this.exampleSimple, 2, 4), this.build2)
        && t.checkExpect(this.exampleComplex.buildMobile(this.exampleComplex, 2, 9), this.build2);
  }

  // testing for the method buildMobileHelp
  boolean testBuildHelp(Tester t) {
    return t.checkExpect(this.exampleSimple.buildMobileHelp(this.simple1, 7), 2)
        && t.checkExpect(this.simple1.buildMobileHelp(this.simple2, 5), 3)
        && t.checkExpect(this.exampleComplex.buildMobileHelp(this.exampleComplex, 9), 4);
  }

  // testing for the drawMobile() method
  boolean testImage(Tester t) {
    return t.checkExpect(this.simple1.drawMobile(),
        new AboveImage(new LineImage(new Posn(0, 70), Color.BLACK),
            new RectangleImage(80, 80, OutlineMode.SOLID,
                Color.BLUE)))
        && t.checkExpect(this.simple2.drawMobile(),
            new AboveImage(new LineImage(new Posn(0, 70), Color.BLACK),
                new RectangleImage(40, 40, OutlineMode.SOLID,
                    Color.RED)))
        && t.checkExpect(this.exampleComplex.drawMobile(), this.complexDraw3)
        && t.checkExpect(build1.drawMobile(), this.build1Draw)
        && showImage(testSimple, "Tester Image Simple")
        && showImage(testComplex, "Tester Image Complex");
  }
}