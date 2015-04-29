#!/usr/bin/env python2

import cv2

from client import AdbClient


LOCAL_PORT = 6100
DEVICE_PORT = 38300


def send_capture_data(capture):
    client = AdbClient('localhost', LOCAL_PORT)
    client.connect(DEVICE_PORT)

    while True:
        ret, frame = capture.read()
        gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
        gray = cv2.resize(gray, (640, 400))
        client.send_frame(gray)


def main():
    cap = cv2.VideoCapture(0)
    send_capture_data(cap)


if __name__ == '__main__':
    main()
