import socket
import subprocess


LOCAL_PORT = 6100
DEVICE_PORT = 38300


def forward_device_port():
    subprocess.call(['adb', 'forward',
                    'tcp:{}'.format(LOCAL_PORT),
                    'tcp:{}'.format(DEVICE_PORT)])


def connect_socket():
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    s.connect(('localhost', LOCAL_PORT))
    return s


def main():
    forward_device_port()
    sock = connect_socket()
    sock.sendall('Hello, server!')


if __name__ == '__main__':
    main()
