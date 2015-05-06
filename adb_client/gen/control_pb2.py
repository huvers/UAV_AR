# Generated by the protocol buffer compiler.  DO NOT EDIT!
# source: control.proto

import sys
_b=sys.version_info[0]<3 and (lambda x:x) or (lambda x:x.encode('latin1'))
from google.protobuf import descriptor as _descriptor
from google.protobuf import message as _message
from google.protobuf import reflection as _reflection
from google.protobuf import symbol_database as _symbol_database
from google.protobuf import descriptor_pb2
# @@protoc_insertion_point(imports)

_sym_db = _symbol_database.Default()




DESCRIPTOR = _descriptor.FileDescriptor(
  name='control.proto',
  package='adb_client',
  serialized_pb=_b('\n\rcontrol.proto\x12\nadb_client\"\xb1\x02\n\x0c\x44roneControl\x12:\n\x07\x63ommand\x18\x01 \x01(\x0e\x32 .adb_client.DroneControl.Command:\x07UNKNOWN\"\xe4\x01\n\x07\x43ommand\x12\x0b\n\x07UNKNOWN\x10\x00\x12\x11\n\rPITCH_FORWARD\x10\x01\x12\x12\n\x0ePITCH_BACKWARD\x10\x02\x12\r\n\tROLL_LEFT\x10\x03\x12\x0e\n\nROLL_RIGHT\x10\x04\x12\x0c\n\x08YAW_LEFT\x10\x05\x12\r\n\tYAW_RIGHT\x10\x06\x12\x15\n\x11INCREASE_ALTITUDE\x10\x07\x12\x15\n\x11\x44\x45\x43REASE_ALTITUDE\x10\x08\x12\x0b\n\x07TAKEOFF\x10\t\x12\x08\n\x04LAND\x10\n\x12\x08\n\x04LEAP\x10\x0b\x12\x0b\n\x07PICTURE\x10\x0c\x12\r\n\tEMERGENCY\x10\rB1\n com.github.huvers.adb_client.genB\rControlProtos')
)
_sym_db.RegisterFileDescriptor(DESCRIPTOR)



_DRONECONTROL_COMMAND = _descriptor.EnumDescriptor(
  name='Command',
  full_name='adb_client.DroneControl.Command',
  filename=None,
  file=DESCRIPTOR,
  values=[
    _descriptor.EnumValueDescriptor(
      name='UNKNOWN', index=0, number=0,
      options=None,
      type=None),
    _descriptor.EnumValueDescriptor(
      name='PITCH_FORWARD', index=1, number=1,
      options=None,
      type=None),
    _descriptor.EnumValueDescriptor(
      name='PITCH_BACKWARD', index=2, number=2,
      options=None,
      type=None),
    _descriptor.EnumValueDescriptor(
      name='ROLL_LEFT', index=3, number=3,
      options=None,
      type=None),
    _descriptor.EnumValueDescriptor(
      name='ROLL_RIGHT', index=4, number=4,
      options=None,
      type=None),
    _descriptor.EnumValueDescriptor(
      name='YAW_LEFT', index=5, number=5,
      options=None,
      type=None),
    _descriptor.EnumValueDescriptor(
      name='YAW_RIGHT', index=6, number=6,
      options=None,
      type=None),
    _descriptor.EnumValueDescriptor(
      name='INCREASE_ALTITUDE', index=7, number=7,
      options=None,
      type=None),
    _descriptor.EnumValueDescriptor(
      name='DECREASE_ALTITUDE', index=8, number=8,
      options=None,
      type=None),
    _descriptor.EnumValueDescriptor(
      name='TAKEOFF', index=9, number=9,
      options=None,
      type=None),
    _descriptor.EnumValueDescriptor(
      name='LAND', index=10, number=10,
      options=None,
      type=None),
    _descriptor.EnumValueDescriptor(
      name='LEAP', index=11, number=11,
      options=None,
      type=None),
    _descriptor.EnumValueDescriptor(
      name='PICTURE', index=12, number=12,
      options=None,
      type=None),
    _descriptor.EnumValueDescriptor(
      name='EMERGENCY', index=13, number=13,
      options=None,
      type=None),
  ],
  containing_type=None,
  options=None,
  serialized_start=107,
  serialized_end=335,
)
_sym_db.RegisterEnumDescriptor(_DRONECONTROL_COMMAND)


_DRONECONTROL = _descriptor.Descriptor(
  name='DroneControl',
  full_name='adb_client.DroneControl',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  fields=[
    _descriptor.FieldDescriptor(
      name='command', full_name='adb_client.DroneControl.command', index=0,
      number=1, type=14, cpp_type=8, label=1,
      has_default_value=True, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
    _DRONECONTROL_COMMAND,
  ],
  options=None,
  is_extendable=False,
  extension_ranges=[],
  oneofs=[
  ],
  serialized_start=30,
  serialized_end=335,
)

_DRONECONTROL.fields_by_name['command'].enum_type = _DRONECONTROL_COMMAND
_DRONECONTROL_COMMAND.containing_type = _DRONECONTROL
DESCRIPTOR.message_types_by_name['DroneControl'] = _DRONECONTROL

DroneControl = _reflection.GeneratedProtocolMessageType('DroneControl', (_message.Message,), dict(
  DESCRIPTOR = _DRONECONTROL,
  __module__ = 'control_pb2'
  # @@protoc_insertion_point(class_scope:adb_client.DroneControl)
  ))
_sym_db.RegisterMessage(DroneControl)


DESCRIPTOR.has_options = True
DESCRIPTOR._options = _descriptor._ParseOptions(descriptor_pb2.FileOptions(), _b('\n com.github.huvers.adb_client.genB\rControlProtos'))
# @@protoc_insertion_point(module_scope)