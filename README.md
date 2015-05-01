# UAV AR

## Installing ROS and ardrone_autonomy on the Jetson

The client requires Python 2 to be installed.

1.  Install ROS Indigo for ARM by following instructions 2.1-2.7 [here][rosARM].

2.  Create a catkin workspace:
    ```sh
    $ mkdir -p ~/catkin_ws/src
    $ cd ~/catkin_ws/src
    $ catkin_init_workspace
    ```

3.  Compile ardrone_autonomy:
    ```sh
    $ cd ~/catkin_ws/src
    $ git clone https://github.com/AutonomyLab/ardrone_autonomy.git -b indigo-devel
    $ cd ~/catkin_ws
    $ rosdep install --from-paths src -i
    $ catkin_make
    ```

    The build will fail with a message about an invalid instruction due to
    being on ARM.

4.  Patch ardrone_autonomy build files and recompile:
    ```sh
    $ cd ~/catkin_ws
    $ wget https://gist.githubusercontent.com/euclio/2d4a8cbcb4433853a510/raw/b691bdc59ae3e05164e17a620d33424604eff11e/armv7_catkin.patch
    $ patch -p1 < armv7_catkin.patch
    $ catkin_make
    ```

5.  Compile ardrone_tutorials:
    ```sh
    $ cd ~/catkin_ws/src
    $ git clone https://github.com/mikehamer/ardrone_tutorials
    $ cd ~/catkin_ws
    $ catkin_make
    ```

6.  Install additional ROS packages:
    ```sh
    $ sudo apt-get install ros-indigo-joy ros-indigo-cv-bridge
    ```

7.  Install CUDA. Download the [CUDA toolkit] for toolkit for the version of
    Linux4Tegra you're running, and then follow [the instructions][installing
    CUDA].

8.  Install OpenCV. Download [OpenCV4Tegra] for the version of Linux4Tegra
    you're running, and then follow [the instructions][installing OpenCV].

9.  Install the OpenCV4Tegra Python bindings:
    ```sh
    $ sudo apt-get install libopencv4tegra-python
    ```

10. Install Qt and Python bindings:
    ```sh
    $ sudo apt-get install python-pyside
    ```

11. Install adb.
    ```sh
    $ sudo apt-get install android-tools-adb
    ```

12. Install the protobuf library.
    ```sh
    $ sudo easy_install pip
    $ sudo pip install protobuf
    ```

## Running the client

1. Connect to the drone's Wi-Fi.

2. Prepare ROS.
    ```sh
    $ source ~/catkin_ws/devel/setup.bash
    $ roscore
    $ rosrun ardrone_autonomy ardrone_driver
    ```

3. Run the client.
    ```sh
    $ python launch.py
    ```

[rosARM]: http://wiki.ros.org/indigo/Installation/UbuntuARM
[CUDA toolkit]: https://developer.nvidia.com/linux-tegra
[installing CUDA]: http://elinux.org/Jetson/Installing_CUDA
[OpenCV4Tegra]: https://developer.nvidia.com/linux-tegra
[installing OpenCV]: http://elinux.org/Jetson/Installing_OpenCV
