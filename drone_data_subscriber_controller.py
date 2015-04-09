#!/usr/bin/env python

# The Keyboard Controller Node for the tutorial "Up and flying with the AR.Drone and ROS | Getting Started"
# https://github.com/mikehamer/ardrone_tutorials

# This controller extends the base DroneVideoDisplay class, adding a keypress handler to enable keyboard control of the drone

# Import the ROS libraries, and load the manifest file which through <depend package=... /> will give us access to the project dependencies
import roslib; roslib.load_manifest('ardrone_tutorials')
import rospy
from std_msgs.msg import String


# Load the DroneController class, which handles interactions with the drone, and the DroneVideoDisplay class, which handles video display
from drone_controller import BasicDroneController
from drone_video_display2 import DroneVideoDisplay

# Finally the GUI libraries
from PySide import QtCore, QtGui

class Data:
    pass

D = Data()
D.subscriber = False


# Here we define the keyboard map for our controller (note that python has no enums, so we use a class)
class KeyMapping(object):
    PitchForward     = QtCore.Qt.Key.Key_E
    PitchBackward    = QtCore.Qt.Key.Key_D
    RollLeft         = QtCore.Qt.Key.Key_S
    RollRight        = QtCore.Qt.Key.Key_F
    YawLeft          = QtCore.Qt.Key.Key_W
    YawRight         = QtCore.Qt.Key.Key_R
    IncreaseAltitude = QtCore.Qt.Key.Key_Q
    DecreaseAltitude = QtCore.Qt.Key.Key_A
    Takeoff          = QtCore.Qt.Key.Key_Y
    Hover            = QtCore.Qt.Key.Key_Space
    Land             = QtCore.Qt.Key.Key_L
    Subscriber       = QtCore.Qt.Key.Key_P   # toggle listening to the subscriber
    Emergency        = QtCore.Qt.Key.Key_M


# Our controller definition, note that we extend the DroneVideoDisplay class
class KeyboardController(DroneVideoDisplay):
    def __init__(self):
        super(KeyboardController,self).__init__()
        self.pitch = 0
        self.roll = 0
        self.yaw_velocity = 0 
        self.z_velocity = 0


# We add a keyboard handler to the DroneVideoDisplay to react to keypresses
    def keyPressEvent(self, event):
        key = event.key()

        # If we have constructed the drone controller and the key is not generated from an auto-repeating key
        if controller is not None and not event.isAutoRepeat():
            # Handle the important cases first!
            if key == KeyMapping.Emergency:
                controller.SendEmergency()
            elif key == KeyMapping.Takeoff:
                controller.SendTakeoff()
            elif key == KeyMapping.Land:
                controller.SendLand()
            elif key == KeyMapping.Subscriber:
                D.subscriber = not D.subscriber
                if D.subscriber == True:
                    print "CONTROL ON",
                else:
                    print "CONTROL OFF",
                print "using the drone_data stream is now", D.subscriber
            else:
                # Now we handle moving, notice that this section is the opposite (+=) of the keyrelease section
                if key == KeyMapping.YawLeft:
                    self.yaw_velocity += 1
                elif key == KeyMapping.YawRight:
                    self.yaw_velocity += -1

                elif key == KeyMapping.PitchForward:
                    self.pitch += 1
                elif key == KeyMapping.PitchBackward:
                    self.pitch += -1

                elif key == KeyMapping.RollLeft:
                    self.roll += 1
                elif key == KeyMapping.RollRight:
                    self.roll += -1

                elif key == KeyMapping.IncreaseAltitude:
                    self.z_velocity += 1
                elif key == KeyMapping.DecreaseAltitude:
                    self.z_velocity += -1
                elif key == KeyMapping.Hover:
                    D.subscriber = False
                    self.hover()

            # finally we set the command to be sent. The controller handles sending this at regular intervals
            controller.SetCommand(self.roll, self.pitch, self.yaw_velocity, self.z_velocity)


    def keyReleaseEvent(self,event):
        key = event.key()

        # If we have constructed the drone controller and the key is not generated from an auto-repeating key
        if controller is not None and not event.isAutoRepeat():
            # Note that we don't handle the release of emergency/takeoff/landing keys here, there is no need.
            # Now we handle moving, notice that this section is the opposite (-=) of the keypress section
            if key == KeyMapping.YawLeft:
                self.yaw_velocity -= 1
            elif key == KeyMapping.YawRight:
                self.yaw_velocity -= -1

            elif key == KeyMapping.PitchForward:
                self.pitch -= 1
            elif key == KeyMapping.PitchBackward:
                self.pitch -= -1

            elif key == KeyMapping.RollLeft:
                self.roll -= 1
            elif key == KeyMapping.RollRight:
                self.roll -= -1

            elif key == KeyMapping.IncreaseAltitude:
                self.z_velocity -= 1
            elif key == KeyMapping.DecreaseAltitude:
                self.z_velocity -= -1

            # finally we set the command to be sent. The controller handles sending this at regular intervals
            controller.SetCommand(self.roll, self.pitch, self.yaw_velocity, self.z_velocity)

    def hover(self):
        self.pitch = 0
        self.roll = 0
        self.yaw_velocity = 0 
        self.z_velocity = 0

    def callback(self, data):
        raw_message = data.data
        print "    message received: ", raw_message

        # here is where you translate the raw_message into a standard message

        if True:
            message = raw_message
        else:
            message = raw_message


        # leave this stuff...
        # this handles messages appropriately (we hope!)

        if True:
            if message == "up":
                self.z_velocity = 1
            elif message == "down":
                self.z_velocity = -1
            elif message == "lturn":
                self.yaw_velocity = 1
            elif message == "rturn":
                self.yaw_velocity = -1
            elif message == "forward":
                self.pitch = 1
            elif message == "backward":
                self.pitch = -1
            elif message == "left":
                self.roll = 1
            elif message == "right":
                self.roll = -1
            elif message == "land":
                controller.SendLand()
            elif message == "hover":
                self.hover()
            else:
                print "ignoring message", message


        
        if controller is not None and D.subscriber == True:
            controller.SetCommand(self.roll, self.pitch, self.yaw_velocity, self.z_velocity)
            print "CONTROL ON - drone command IS (roll,pitch,yaw,z):", \
              (self.roll, self.pitch, self.yaw_velocity, self.z_velocity)
        else:
            print "CONTROL OFF - drone command would be (roll,pitch,yaw,z):", \
              (self.roll, self.pitch, self.yaw_velocity, self.z_velocity)


# Setup the application
if __name__=='__main__':
    import sys
    # Firstly we setup a ros node, so that we can communicate with the other packages
    rospy.init_node('ardrone_keyboard_controller')

    # Now we construct our Qt Application and associated controllers and windows
    app = QtGui.QApplication(sys.argv)
    controller = BasicDroneController()
    display = KeyboardController()

    display.show()
    rospy.Subscriber('drone_data', String, display.callback)

    # executes the QT application
    status = app.exec_()

    # and only progresses to here once the application has been shutdown
    rospy.signal_shutdown('Great Flying!')
    sys.exit(status)
