#!/usr/bin/env python
import roslib; roslib.load_manifest('irobot_mudd')
import rospy
import sensor_msgs.msg as sm
import cv_bridge
import cv
#import numpy
import math

# class for a generic data holder
class Data:
    def __init__(self): pass    # empty constructor
    
# object (of class Data) to hold the global state of the system
D = Data()


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
    cv.Rectangle(D.color_image, (25,25), (100,50), cv.RGB(0,.42,0), cv.CV_FILLED)
    # place some text there
    cv.PutText(D.color_image, "Hi!", (30,40), D.font, cv.RGB(1,1,1))

        

if __name__ == "__main__":
    """Main function, sets up stuff the class needs to run and runs it"""
    
    #Initialize our node
    rospy.init_node('distanceReader')
    
    #Create a SegmentFinder
    startup()
    
    #Subscribe to the image topic
    rospy.Subscriber('/camera/depth/image',sm.Image,handle_next_image)
    
    #Run until something stops us
    rospy.spin()

    