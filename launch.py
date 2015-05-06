from adb_client import AdbClient
from drone_drivers import keyboard_controller

LOCAL_PORT = 6100
DEVICE_PORT = 38300


def main():
    client = AdbClient('localhost', LOCAL_PORT)
    client.connect(DEVICE_PORT)
    keyboard_controller.main(client)
    

if __name__ == '__main__':
    main()
