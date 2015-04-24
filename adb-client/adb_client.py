#!/usr/bin/env python2

import socket
import subprocess

import cv2
from google.protobuf.internal import encoder

from gen import frame_pb2


LOCAL_PORT = 6100
DEVICE_PORT = 38300


def forward_device_port():
    subprocess.call(['adb', 'forward',
                     'tcp:{}'.format(LOCAL_PORT),
                     'tcp:{}'.format(DEVICE_PORT)])


def delimit_protobuf(message):
    """
    Prepares a protobuf to be sent over a socket. This method *must* be called
    before sending the message over the network.

    For some reason, the Python protocol buffer API does not provide a method
    to read delimited messages, like the Java and C++ ones do. So, we write our
    own. Perhaps we should move to capnproto once the Java API is up to snuff.

    http://zombietetris.de/blog/building-your-own-writedelimitedto-for-python-protobuf/
    """
    serialized_message = message.SerializeToString()
    delimiter = encoder._VarintBytes(len(serialized_message))
    return delimiter + serialized_message


def connect_socket():
    sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    sock.connect(('localhost', LOCAL_PORT))
    return sock


def send_capture_data(sock, capture):
    while True:
        ret, frame = capture.read()
        gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)

        frame_message = frame_pb2.Frame()
        frame_message.data = str(gray.data)
        sock.send(delimit_protobuf(frame_message))


def main():
    forward_device_port()
    sock = connect_socket()

    cap = cv2.VideoCapture(0)
    send_capture_data(sock, cap)


if __name__ == '__main__':
    main()
