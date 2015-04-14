#!/usr/bin/env python2.7

from __future__ import print_function

import json
import os

from bluetooth import (advertise_service, BluetoothSocket, RFCOMM, PORT_ANY,
                       SERIAL_PORT_CLASS, SERIAL_PORT_PROFILE)


def main():
    config_file = os.path.abspath(
        os.path.join(os.path.dirname(__file__), '..', 'btconfig.json'))

    with open(config_file) as config_data:
        btconfig = json.load(config_data)

    server_socket = BluetoothSocket(RFCOMM)
    server_socket.bind(('', PORT_ANY))
    server_socket.listen(1)

    port = server_socket.getsockname()[1]
    uuid = btconfig['SERVICE_UUID']

    advertise_service(server_socket, 'UAVBluetoothServer', service_id=uuid,
                      service_classes=[uuid, SERIAL_PORT_CLASS],
                      profiles=[SERIAL_PORT_PROFILE])

    print('Listening for connections on port', port)

    client_socket, client_info = server_socket.accept()
    print('Accepted connection from', client_info)

    try:
        while True:
            data = client_socket.recv(1024)
            if len(data) == 0:
                break
            print('received [%s]' % data)
    except IOError:
        pass

    print('Disconnected from client')

    client_socket.close()
    server_socket.close()


if __name__ == '__main__':
    main()
