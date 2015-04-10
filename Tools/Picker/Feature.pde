class Feature{
  final static String delimiter = ",";
  String imagePath;
  int x;
  int y;
  int sx;
  int sy;
  public Feature (String data){
    String[] split = data.split(delimiter);
    imagePath = split[0];
    x = Integer.parseInt(split[1]);
    y = Integer.parseInt(split[2]);
    sx = Integer.parseInt(split[3]);
    sy = Integer.parseInt(split[4]);
  }
  public Feature (String imagePath, int x, int y, int sx, int sy){
    this.imagePath = imagePath;
    this.x = x;
    this.y = y;
    this.sx = sx;
    this.sy = sy;
  }
  public void draw(PGraphics canvas){
    canvas.rect(x, y, sx, sy);
  }
  public String toString(){
    String[] data = {imagePath, ""+x, ""+y, ""+sx, ""+sy};
    return strJoin(data, delimiter);
  }
}
