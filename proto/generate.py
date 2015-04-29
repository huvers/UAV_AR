#!/usr/bin/env python2

import os
import subprocess


def main():
    project_root = os.path.dirname(os.path.dirname(os.path.realpath(__file__)))
    java_out = os.path.join(project_root,
                            'adb-server', 'app', 'src', 'main', 'java')
    python_out = os.path.join(project_root,
                              'adb_client', 'gen')
    subprocess.call(['protoc',
                     '--java_out={}'.format(java_out),
                     '--python_out={}'.format(python_out),
                     'frame.proto'])

if __name__ == '__main__':
    main()
