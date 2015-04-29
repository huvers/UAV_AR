import socket
import subprocess

from google.protobuf.internal import encoder

from gen import frame_pb2


class AdbClient(object):
    def __init__(self, host_name, host_port):
        self.socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.host_name = host_name
        self.host_port = host_port

    def _forward_device_port(self, device_port):
        """Forwards the Android device port to the host computer's port."""
        subprocess.call(['adb', 'forward',
                         'tcp:{}'.format(self.host_port),
                         'tcp:{}'.format(device_port)])

    def connect(self, device_port):
        """Forward the android device port to the host port and prepares the
        socket for communication."""
        self._forward_device_port(device_port)
        self.socket.connect((self.host_name, self.host_port))

    def send_frame(self, frame):
        """Sends an OpenCV Mat over the socket."""
        frame_message = frame_pb2.Frame()
        frame_message.data = str(frame.data)
        frame_message.rows, frame_message.cols = frame.shape
        self.socket.send(delimit_protobuf(frame_message))


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
