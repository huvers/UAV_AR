#!/usr/bin/env python
import roslib; roslib.load_manifest('irobot_mudd')
import rospy
import sensor_msgs.msg as sm
import cv_bridge
import cv
#import numpy
import math
import sensor_msgs.msg as sm
from std_msgs.msg import String

# class for a generic data holder
class Data:
    def __init__(self): pass    # empty constructor
    
# object (of class Data) to hold the global state of the system
D = Data()
D.top = (320,330)
D.bottom = (320, 150)
D.left = (200,240)
D.right = (440,240) 
D.left_angle = 0.0
D.right_angle = 0.0
D.totalDistanceH = 0
D.thetaH = 0


D.top_angle = 0
D.bottom_angle = 0
D.totalDistanceV = 0
D.thetaV = 0

def startup():
    """Sets up things needed for the class"""
    #Create cv stuff
    cv.NamedWindow('dist')
    D.bridge = cv_bridge.CvBridge()
    cv.SetMouseCallback('dist', onMouse, None)
    D.color_image = None

    D.scale = 1
    D.left_x = 100
    make_slider_window()
    D.font = cv.InitFont(cv.CV_FONT_HERSHEY_SIMPLEX, .5, .5, 0, 1)


def make_slider_window():
        """ a method to make a window full of sliders """
        #Create slider window
        cv.NamedWindow('sliders')
        cv.MoveWindow('sliders', 800, 0)
        
        #Create sliders
        cv.CreateTrackbar('left_x', 'sliders', D.left_x,\
                          320, change_left_x)
        cv.CreateTrackbar('scale', 'sliders', D.scale,\
                          10, change_scale)

#Functions for changing the slider values  
def change_left_x(newval):
    """ change the left-hand x-coordinate of the "pressure points" """
    D.left_x = newval
    
def change_scale(newval):
    D.scale = newval
    if D.scale < 1: D.scale = 1
        
def handle_next_image(data):
    """Displays the image, calls find_info"""        
    # get the image from the Kinect
    D.image = D.bridge.imgmsg_to_cv(data, "32FC1")
    cv.Flip( D.image, D.image, 1 )  # flip it!

    # change the scale
    cv.ConvertScale( D.image, D.image, scale=1.0/D.scale, shift=0.0 )

    # create a new color image, if it does not yet exist
    if D.color_image == None:
        D.color_image = cv.CreateImage(cv.GetSize(D.image),cv.IPL_DEPTH_32F, 3)

    # create *this frame's* color image, now that we know one exists
    cv.Merge( D.image, D.image, D.image, None, D.color_image )

    # handle key presses
    key_press = cv.WaitKey(5) & 255
    if key_press != 255: check_key_press( key_press )

    # computation and other processing can go here!
    cv.Circle(D.color_image, (D.left_x,142),
              8,  cv.RGB(0,0,255),  thickness=1, lineType=8, shift=0)

    
    cv.Circle(D.color_image, D.left,
              8,  cv.RGB(255,0,0),  thickness=2, lineType=8, shift=0)
    cv.Circle(D.color_image, D.right,
              8,  cv.RGB(255,0,0),  thickness=2, lineType=8, shift=0)
    cv.Circle(D.color_image, D.top,
              8,  cv.RGB(255, 0,0),  thickness=2, lineType=8, shift=0)
    cv.Circle(D.color_image, D.bottom,
              8,  cv.RGB(255, 0,0),  thickness=2, lineType=8, shift=0)
    pixel_left_depth = D.image[D.left[1],D.left[0]]
    pixel_right_depth= D.image[D.right[1],D.right[0]]
    pixel_top_depth = D.image[D.top[1],D.top[0]]
    pixel_bottom_depth= D.image[D.bottom[1],D.bottom[0]]
    #print "the pixel's left depth value is", pixel_left_depth
    #print "the pixel's right depth value is", pixel_right_depth
    totalFOV = 57 # field of view
    D.left_angle = totalFOV/2.0 - (D.left[0]/640.0)*57
    D.right_angle = (D.right[0]/640.0)*57 - totalFOV/2.0
    D.dist_left = math.tan((math.pi/180)*D.left_angle)*pixel_left_depth
    D.dist_right = math.tan((math.pi/180)*D.right_angle)*pixel_right_depth
    D.totalDistanceH = D.dist_right + D.dist_left
    D.thetaH = math.atan((pixel_right_depth - pixel_left_depth)/D.totalDistanceH)
    #print "the horizontal angle is", + D.thetaH

    totalVerticalFOV = 43
    D.top_angle = totalVerticalFOV/2.0 - (D.top[1]/480.0)*43
    D.bottom_angle = (D.bottom[1]/480.0)*43 - totalVerticalFOV/2.0
    D.dist_top = math.tan((math.pi/180)*D.top_angle)*pixel_top_depth
    D.dist_bottom = math.tan((math.pi/180)*D.bottom_angle)*pixel_bottom_depth
    D.totalDistanceV = D.dist_bottom + D.dist_top
    D.thetaV = math.atan((pixel_bottom_depth - pixel_top_depth)/D.totalDistanceV)
    #print "the vertical angle is", + D.thetaV

    if math.isnan(D.thetaH) or math.isnan(D.thetaV):
         D.pub.publish("hover")
    else:
        if ((180/math.pi)*D.thetaH) > 10:
            D.pub.publish("lturn")
        elif -10 > ((180/math.pi)*D.thetaH):
            D.pub.publish("rturn")
        elif ((180/math.pi)*D.thetaV) > 10:
            D.pub.publish("forward")
        elif -10 > ((180/math.pi)*D.thetaV):
            D.pub.publish("backward")

        elif 10 >= ((180/math.pi)*D.thetaH) >= -10 and 10 >= ((180/math.pi)*D.thetaV) >= -10:
            D.pub.publish("hover")

    # show text...
    draw_text_to_image()

    # display the image
    cv.ShowImage('dist', D.color_image)


def onMouse(event,x,y,flags,param):
    """ the method called when the mouse is clicked """
    # if the left button was clicked
    if event==cv.CV_EVENT_LBUTTONDOWN: 
        print "x, y are", x, y,
        pixel_value = D.image[y,x]
        print "the pixel's depth value is", pixel_value




# key-press handler
def check_key_press(key_press):
    """ this method handles user key presses appropriately """
    # if a 'q' or Esc was pressed
    if key_press == 27 or key_press == ord('q'): 
        print 'quitting'
        rospy.signal_shutdown( "Quit requested from keyboard" )


def draw_text_to_image():
    """ A function to handle drawing things to the image """
    # draw a rectangle under the text to make it more visible
    cv.Rectangle(D.color_image, (25,25), (200,80), cv.RGB(0,.42,0), cv.CV_FILLED)
    # place some text there
    cv.PutText(D.color_image, "Pan Angle: "+str((180/math.pi)*D.thetaH), (30,40), D.font, cv.RGB(1,1,1))
    cv.PutText(D.color_image, "Tilt Angle"+str((180/math.pi)*D.thetaV), (30,70), D.font, cv.RGB(1,1,1))

        

if __name__ == "__main__":
    """Main function, sets up stuff the class needs to run and runs it"""
    
    #Initialize our node
    rospy.init_node('distanceReader')
    D.pub = rospy.Publisher('drone_data',String)
    #Create a SegmentFinder
    startup()
    
    #Subscribe to the image topic
    rospy.Subscriber('/camera/depth/image',sm.Image,handle_next_image)
    
    #Run until something stops us
    rospy.spin()