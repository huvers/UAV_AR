Installing ROS and ardrone_autonomy on the Jetson

1. Install ROS Indigo for ARM:
	Follow instructions 2.1 - 2.7 here: http://wiki.ros.org/indigo/Installation/UbuntuARM

2. Create a catkin workspace:
	mkdir -p ~/catkin_ws/src
	cd ~/catkin_ws/src
	catkin_init_workspace

3. Compile ardrone_autonomy:
	cd ~/catkin_ws/src
	git clone https://github.com/AutonomyLab/ardrone_autonomy.git -b indigo-devel
	cd ~/catkin_ws
	rosdep install --from-paths src -i
	catkin_make

3.1 Patch ardrone_autonomy build files and recompile:
	https://gist.github.com/euclio/2d4a8cbcb4433853a510
	download armv7_catkin.patch to ~/catkin_ws
	patch -p1 < armv7_catkin.patch
	catkin_make

4. Compile ardrone_tutorials:
	cd ~/catkin_ws/src
	git clone https://github.com/mikehamer/ardrone_tutorials
	cd ~/catkin_ws
	catkin_make

5. Install additional ROS packages:
	sudo apt-get install ros-indigo-joy
	sudo apt-get install ros-indigo-cv-bridge

6. Install CUDA:
	Download CUDA toolkit for the version of Linux4Tegra you're running: https://developer.nvidia.com/linux-tegra
	Follow the instructions here: http://elinux.org/Jetson/Installing_CUDA

7. Install OpenCV:
	Download OpenCV4Tegra for the version of Linux4Tegra you're running: https://developer.nvidia.com/linux-tegra
	Follow the instructions here: http://elinux.org/Jetson/Installing_OpenCV

7.1 Install the OpenCV4Tegra Python bindings:
	sudo apt-get install libopencv4tegra-python

8. Install Qt and Python bindings:
	sudo apt-get install python-pyside
	
To control the drone:

Connect to the drone's wifi
source ~/catkin_ws/devel/setup.bash
roscore
rosrun ardrone_autonomy ardrone_driver
python keyboard_controller.py
