import controlP5.*;
import java.util.Arrays;

ControlP5 cp5;

// GUI size variables
float workspaceScale = .5;
float workspaceDefaultFixedHeight = 400;
int sidebarWidth = 100;
int fileSelectionBarHeight = 60;
int bufferSize = 15;
int screenWidth = 400;
int screenHeight = 400;
int minScreenWidth;
int minScreenHeight;

// Control field size variables
// Bottom bar
int pathTextFieldWidth = 200;
int buttonWidth = 40;
// Sidebar
int numberboxHeight = 40;
int buttonHeight = 30;

// Workspace variables
PImage workspaceImage = null;
String workspaceImagePath = null;

// Menu objects
controlP5.Textfield pathTextfield;
controlP5.Button selectButton;
controlP5.Button openButton;
controlP5.Button nextButton;
controlP5.Button prevButton;
controlP5.Numberbox scaleNumberbox;
controlP5.Button exportButton;
controlP5.Toggle autoSaveToggle;
controlP5.Button clearButton;
controlP5.Toggle fixHeightToggle;
controlP5.Numberbox heightNumberbox;
controlP5.Button undoButton;

controlP5.Controller[] fileSelectionBar;
controlP5.Controller[] sidebar;

int sizeOffsetX;
int sizeOffsetY;

int editorMode = 0;
final int MODE_CREATE = 0;
final int MODE_SELECT = 1;
final int MODE_MOVE = 2;
final int MODE_RESIZE = 3;

ArrayList<Feature> features = new ArrayList<Feature>();
ArrayList<ArrayList<Feature>> featuresHistory = new ArrayList<ArrayList<Feature>>();
Feature focusedFeature = null;
String featureFileExtension = ".dat";

String[] possibleImageExtensions = {
  ".jpg", ".jpeg", ".tiff", ".gif", ".bmp", ".png"
};

void setup() {
  size(screenWidth, screenHeight);
  frame.setResizable(true);

  // Create menus
  // Bottom bar
  cp5 = new ControlP5(this);
  pathTextfield = cp5.addTextfield("Path").setSize(pathTextFieldWidth, fileSelectionBarHeight-2*bufferSize).setAutoClear(false);
  selectButton = cp5.addButton("Select").setSize(buttonWidth, fileSelectionBarHeight-2*bufferSize);    
  openButton = cp5.addButton("Open").setSize(buttonWidth, fileSelectionBarHeight-2*bufferSize);    
  prevButton = cp5.addButton("Prev").setSize(buttonWidth, fileSelectionBarHeight-2*bufferSize);    
  nextButton = cp5.addButton("Next").setSize(buttonWidth, fileSelectionBarHeight-2*bufferSize);
  fileSelectionBar = new controlP5.Controller[] {
    pathTextfield, selectButton, openButton, prevButton, nextButton
  };
  minScreenWidth = 2*bufferSize;
  for (controlP5.Controller c : fileSelectionBar)
    minScreenWidth += bufferSize + c.getWidth();

// Sidebar
  scaleNumberbox = cp5.addNumberbox("Scale").setSize(sidebarWidth-2*bufferSize, numberboxHeight).setValue(workspaceScale).setRange(0, 1000).setMultiplier(0.01);
  exportButton = cp5.addButton("Export").setSize(sidebarWidth-2*bufferSize, buttonHeight);
  autoSaveToggle = cp5.addToggle("Auto Save").setSize(sidebarWidth-2*bufferSize, buttonHeight).setState(true);
  clearButton = cp5.addButton("Clear").setSize(sidebarWidth-2*bufferSize, buttonHeight);
  fixHeightToggle = cp5.addToggle("Fix Height").setSize(sidebarWidth-2*bufferSize, buttonHeight).setState(true);
  heightNumberbox = cp5.addNumberbox("Height").setSize(sidebarWidth-2*bufferSize, numberboxHeight).setValue(workspaceDefaultFixedHeight).setRange(0, 2000).setMultiplier(10);
  undoButton = cp5.addButton("Undo").setSize(sidebarWidth-2*bufferSize, buttonHeight);
  sidebar = new controlP5.Controller[] {
    exportButton, clearButton, undoButton, autoSaveToggle, fixHeightToggle, scaleNumberbox, heightNumberbox
  };
  minScreenHeight = 2*bufferSize;
  for (controlP5.Controller c : sidebar)
    minScreenHeight += bufferSize + c.getHeight();
}
void draw() {
  if (sizeOffsetX == 0) {
    sizeOffsetX = screenWidth - width;
  }
  if (sizeOffsetY == 0) {
    sizeOffsetY = screenHeight - height;
  }
  updateScale();
  resizeScreen(screenWidth, screenHeight);
  
  background(55);
// Drawing the workspace
  if (workspaceImage != null) {
    PGraphics workspace = createGraphics(workspaceImage.width, workspaceImage.height);
    workspace.beginDraw();
    workspace.background(0);
    workspace.image(workspaceImage, 0, 0, workspaceImage.width, workspaceImage.height);
    workspace.fill(255, 255, 255, 32);
    workspace.stroke(0);
    workspace.strokeWeight(1/workspaceScale);
    for (Feature f : features) {
      f.draw(workspace);
    }
    workspace.endDraw();
    image(workspace, 0, 0, workspaceScale*workspaceImage.width, workspaceScale*workspaceImage.height);
  }
}

void resizeScreen(int newWidth, int newHeight) {
  screenWidth = max(newWidth, minScreenWidth);
  screenHeight = max(newHeight, minScreenHeight);
  frame.setSize(screenWidth+sizeOffsetX, screenHeight+sizeOffsetY);

  // Resizing the menus
  // Bottom bar
  int bottomX = 0;
  int bottomY = screenHeight-fileSelectionBarHeight+bufferSize;
  for (controlP5.Controller c : fileSelectionBar) {
    bottomX += bufferSize;
    c.setPosition(bottomX, bottomY);
    bottomX += c.getWidth();
  }

  // Sidebar
  int sideX = screenWidth-sidebarWidth+bufferSize;
  int sideY = 0;
  for (controlP5.Controller c : sidebar) {
    sideY += bufferSize;
    c.setPosition(sideX, sideY);
    sideY += c.getHeight();
  }
}

// Gets information from the menu and scales accordingly
void updateScale() {
  if (workspaceImage == null) {
    return;
  }
  if (cp5.get(Toggle.class, "Fix Height").getState()) {
    workspaceScale = cp5.get(Numberbox.class, "Height").getValue()/workspaceImage.height;
    cp5.get(Numberbox.class, "Scale").setValue(workspaceScale);
  } else {
    workspaceScale = cp5.get(Numberbox.class, "Scale").getValue();
    cp5.get(Numberbox.class, "Height").setValue(workspaceImage.height*workspaceScale);
  }
  if (workspaceImage != null) {
    resizeScreen((int)(workspaceScale * workspaceImage.width + sidebarWidth), (int)(workspaceScale * workspaceImage.height + fileSelectionBarHeight));
    redraw();
  }
}

// File operations
void readFeatureFile(String path) {
  try {
    String[] lines = loadStrings(path);
    for (String line : lines) {
      features.add(new Feature(line));
    }
  }
  catch(Exception e) {
  }
}

void writeFeaturesFile(String path) {
  if (workspaceImage == null) {
    return;
  }
  ArrayList<String> lines = new ArrayList<String>();
  for (Feature f : features) {
    lines.add(f.toString());
  }
  saveStrings(path, lines.toArray(new String[lines.size()]));
}

// Opens the image in the workspace
void openImage(String imagePath) {
  if (cp5.get(Toggle.class, "Auto Save").getState()) {
    writeFeaturesFile(workspaceImagePath + featureFileExtension);
  }
  workspaceImage = loadImage(imagePath);
  workspaceImagePath = imagePath;
  features = new ArrayList<Feature>();
  clearFeatureHistory();
  readFeatureFile(imagePath + featureFileExtension);
  resizeScreen((int)(workspaceScale * workspaceImage.width + sidebarWidth), (int)(workspaceScale * workspaceImage.height + fileSelectionBarHeight));
}

// Control methods
void Select() {
  selectInput("Select an image", "imageSelected");
}

void imageSelected(File selection) {
  if (selection != null) {
    cp5.get(Textfield.class, "Path").setText(selection.getAbsolutePath());
  }

  Open();
}

void Open() {
  String path = cp5.get(Textfield.class, "Path").getText();
  println("Directory: " + path);
  openImage(path);
}

// Lexicographically opens the next image in the directory 
void Next() {
  String imageFilename = new File(workspaceImagePath).getName();
  String imageDirectory = new File(workspaceImagePath).getParent();

  String[] imageFilenames = imageFilenamesInDirectory(imageDirectory);
  Arrays.sort(imageFilenames);
  int index = Arrays.asList(imageFilenames).indexOf(imageFilename);
  if (index != -1) {
    int nextIndex = (index+1)%imageFilenames.length;
    println(new File(imageDirectory, imageFilenames[nextIndex]).getPath());
    openImage(new File(imageDirectory, imageFilenames[nextIndex]).getPath());
  }
}

// Lexicographically opens the previous image in the directory 
void Prev() {
  String imageFilename = new File(workspaceImagePath).getName();
  String imageDirectory = new File(workspaceImagePath).getParent();

  String[] imageFilenames = imageFilenamesInDirectory(imageDirectory);
  Arrays.sort(imageFilenames);
  int index = Arrays.asList(imageFilenames).indexOf(imageFilename);
  if (index != -1) {
    int nextIndex = (index-1+imageFilenames.length)%imageFilenames.length;
    println(new File(imageDirectory, imageFilenames[nextIndex]).getPath());
    openImage(new File(imageDirectory, imageFilenames[nextIndex]).getPath());
  }
}

// Gets the filenames of images in the directory
String[] imageFilenamesInDirectory(String path) {
  File dir = new File(path);
  String[] list = dir.list();
  ArrayList<String> imageFilenames = new ArrayList<String>();
  for (String str : list) {
    String[] parts = str.split("\\.");
    String extension = "." + parts[parts.length-1];
    if (Arrays.asList(possibleImageExtensions).contains(extension)) {
      imageFilenames.add(str);
    }
  }
  return imageFilenames.toArray(new String[imageFilenames.size()]);
}

// Helper string method
public static String strJoin(String[] aArr, String sSep) {
  StringBuilder sbStr = new StringBuilder();
  for (int i = 0, il = aArr.length; i < il; i++) {
    if (i > 0)
      sbStr.append(sSep);
    sbStr.append(aArr[i]);
  }
  return sbStr.toString();
}

void Export() {
  writeFeaturesFile(workspaceImagePath + featureFileExtension);
}

void Clear() {
  focusedFeature = null;
  clearFeatures();
}

void Undo() {
  if (featuresHistory.isEmpty()) {
    return;
  }
  features = featuresHistory.remove(featuresHistory.size()-1);
}

// Feature operations
void addFeature(Feature f) {
  featuresHistory.add(new ArrayList<Feature>(features));
  features.add(f);
}

void clearFeatures() {
  featuresHistory.add(new ArrayList<Feature>(features));
  features = new ArrayList<Feature>();
}

void clearFeatureHistory() {
  featuresHistory = new ArrayList<ArrayList<Feature>>();
}

// Input
int mouseClickX = -1;
int mouseClickY = -1;

boolean isMouseInWorkspace() {
  return mouseX > 0 && mouseX < screenWidth - sidebarWidth && mouseY > 0 && mouseY < screenHeight - fileSelectionBarHeight;
}

void mouseDragged() {
  updateScale();

  if (focusedFeature != null && isMouseInWorkspace()) {
    switch (editorMode) {
    case MODE_CREATE:
      focusedFeature.x = (int)min(mouseClickX/workspaceScale, mouseX/workspaceScale);
      focusedFeature.y = (int)min(mouseClickY/workspaceScale, mouseY/workspaceScale);
      focusedFeature.sx = (int)abs(mouseX/workspaceScale - mouseClickX/workspaceScale);
      focusedFeature.sy = (int)abs(mouseY/workspaceScale - mouseClickY/workspaceScale);
      break;
    }
  }
}

void mousePressed() {
  mouseClickX = mouseX;
  mouseClickY = mouseY;

  if (isMouseInWorkspace()) {
    focusedFeature = new Feature(workspaceImagePath, (int)(mouseClickX/workspaceScale), (int)(mouseClickY/workspaceScale), 0, 0);
    addFeature(focusedFeature);
  }
}

void mouseReleased() {
  updateScale();
  mouseClickX = -1;
  mouseClickY = -1;

  if (isMouseInWorkspace()) {
    switch (editorMode) {
    case MODE_CREATE:
      focusedFeature = null;
      break;
    }
  }
}
