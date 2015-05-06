// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: control.proto

package com.github.huvers.adb_client.gen;

public final class ControlProtos {
  private ControlProtos() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
  }
  public interface DroneControlOrBuilder extends
      // @@protoc_insertion_point(interface_extends:adb_client.DroneControl)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>optional .adb_client.DroneControl.Command command = 1 [default = UNKNOWN];</code>
     */
    boolean hasCommand();
    /**
     * <code>optional .adb_client.DroneControl.Command command = 1 [default = UNKNOWN];</code>
     */
    com.github.huvers.adb_client.gen.ControlProtos.DroneControl.Command getCommand();
  }
  /**
   * Protobuf type {@code adb_client.DroneControl}
   */
  public static final class DroneControl extends
      com.google.protobuf.GeneratedMessage implements
      // @@protoc_insertion_point(message_implements:adb_client.DroneControl)
      DroneControlOrBuilder {
    // Use DroneControl.newBuilder() to construct.
    private DroneControl(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
      super(builder);
      this.unknownFields = builder.getUnknownFields();
    }
    private DroneControl(boolean noInit) { this.unknownFields = com.google.protobuf.UnknownFieldSet.getDefaultInstance(); }

    private static final DroneControl defaultInstance;
    public static DroneControl getDefaultInstance() {
      return defaultInstance;
    }

    public DroneControl getDefaultInstanceForType() {
      return defaultInstance;
    }

    private final com.google.protobuf.UnknownFieldSet unknownFields;
    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
        getUnknownFields() {
      return this.unknownFields;
    }
    private DroneControl(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      initFields();
      int mutable_bitField0_ = 0;
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
            case 8: {
              int rawValue = input.readEnum();
              com.github.huvers.adb_client.gen.ControlProtos.DroneControl.Command value = com.github.huvers.adb_client.gen.ControlProtos.DroneControl.Command.valueOf(rawValue);
              if (value == null) {
                unknownFields.mergeVarintField(1, rawValue);
              } else {
                bitField0_ |= 0x00000001;
                command_ = value;
              }
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e.getMessage()).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.github.huvers.adb_client.gen.ControlProtos.internal_static_adb_client_DroneControl_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.github.huvers.adb_client.gen.ControlProtos.internal_static_adb_client_DroneControl_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.github.huvers.adb_client.gen.ControlProtos.DroneControl.class, com.github.huvers.adb_client.gen.ControlProtos.DroneControl.Builder.class);
    }

    public static com.google.protobuf.Parser<DroneControl> PARSER =
        new com.google.protobuf.AbstractParser<DroneControl>() {
      public DroneControl parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new DroneControl(input, extensionRegistry);
      }
    };

    @java.lang.Override
    public com.google.protobuf.Parser<DroneControl> getParserForType() {
      return PARSER;
    }

    /**
     * Protobuf enum {@code adb_client.DroneControl.Command}
     */
    public enum Command
        implements com.google.protobuf.ProtocolMessageEnum {
      /**
       * <code>UNKNOWN = 0;</code>
       */
      UNKNOWN(0, 0),
      /**
       * <code>PITCH_FORWARD = 1;</code>
       */
      PITCH_FORWARD(1, 1),
      /**
       * <code>PITCH_BACKWARD = 2;</code>
       */
      PITCH_BACKWARD(2, 2),
      /**
       * <code>ROLL_LEFT = 3;</code>
       */
      ROLL_LEFT(3, 3),
      /**
       * <code>ROLL_RIGHT = 4;</code>
       */
      ROLL_RIGHT(4, 4),
      /**
       * <code>YAW_LEFT = 5;</code>
       */
      YAW_LEFT(5, 5),
      /**
       * <code>YAW_RIGHT = 6;</code>
       */
      YAW_RIGHT(6, 6),
      /**
       * <code>INCREASE_ALTITUDE = 7;</code>
       */
      INCREASE_ALTITUDE(7, 7),
      /**
       * <code>DECREASE_ALTITUDE = 8;</code>
       */
      DECREASE_ALTITUDE(8, 8),
      /**
       * <code>TAKEOFF = 9;</code>
       */
      TAKEOFF(9, 9),
      /**
       * <code>LAND = 10;</code>
       */
      LAND(10, 10),
      /**
       * <code>LEAP = 11;</code>
       */
      LEAP(11, 11),
      /**
       * <code>PICTURE = 12;</code>
       */
      PICTURE(12, 12),
      /**
       * <code>EMERGENCY = 13;</code>
       */
      EMERGENCY(13, 13),
      ;

      /**
       * <code>UNKNOWN = 0;</code>
       */
      public static final int UNKNOWN_VALUE = 0;
      /**
       * <code>PITCH_FORWARD = 1;</code>
       */
      public static final int PITCH_FORWARD_VALUE = 1;
      /**
       * <code>PITCH_BACKWARD = 2;</code>
       */
      public static final int PITCH_BACKWARD_VALUE = 2;
      /**
       * <code>ROLL_LEFT = 3;</code>
       */
      public static final int ROLL_LEFT_VALUE = 3;
      /**
       * <code>ROLL_RIGHT = 4;</code>
       */
      public static final int ROLL_RIGHT_VALUE = 4;
      /**
       * <code>YAW_LEFT = 5;</code>
       */
      public static final int YAW_LEFT_VALUE = 5;
      /**
       * <code>YAW_RIGHT = 6;</code>
       */
      public static final int YAW_RIGHT_VALUE = 6;
      /**
       * <code>INCREASE_ALTITUDE = 7;</code>
       */
      public static final int INCREASE_ALTITUDE_VALUE = 7;
      /**
       * <code>DECREASE_ALTITUDE = 8;</code>
       */
      public static final int DECREASE_ALTITUDE_VALUE = 8;
      /**
       * <code>TAKEOFF = 9;</code>
       */
      public static final int TAKEOFF_VALUE = 9;
      /**
       * <code>LAND = 10;</code>
       */
      public static final int LAND_VALUE = 10;
      /**
       * <code>LEAP = 11;</code>
       */
      public static final int LEAP_VALUE = 11;
      /**
       * <code>PICTURE = 12;</code>
       */
      public static final int PICTURE_VALUE = 12;
      /**
       * <code>EMERGENCY = 13;</code>
       */
      public static final int EMERGENCY_VALUE = 13;


      public final int getNumber() { return value; }

      public static Command valueOf(int value) {
        switch (value) {
          case 0: return UNKNOWN;
          case 1: return PITCH_FORWARD;
          case 2: return PITCH_BACKWARD;
          case 3: return ROLL_LEFT;
          case 4: return ROLL_RIGHT;
          case 5: return YAW_LEFT;
          case 6: return YAW_RIGHT;
          case 7: return INCREASE_ALTITUDE;
          case 8: return DECREASE_ALTITUDE;
          case 9: return TAKEOFF;
          case 10: return LAND;
          case 11: return LEAP;
          case 12: return PICTURE;
          case 13: return EMERGENCY;
          default: return null;
        }
      }

      public static com.google.protobuf.Internal.EnumLiteMap<Command>
          internalGetValueMap() {
        return internalValueMap;
      }
      private static com.google.protobuf.Internal.EnumLiteMap<Command>
          internalValueMap =
            new com.google.protobuf.Internal.EnumLiteMap<Command>() {
              public Command findValueByNumber(int number) {
                return Command.valueOf(number);
              }
            };

      public final com.google.protobuf.Descriptors.EnumValueDescriptor
          getValueDescriptor() {
        return getDescriptor().getValues().get(index);
      }
      public final com.google.protobuf.Descriptors.EnumDescriptor
          getDescriptorForType() {
        return getDescriptor();
      }
      public static final com.google.protobuf.Descriptors.EnumDescriptor
          getDescriptor() {
        return com.github.huvers.adb_client.gen.ControlProtos.DroneControl.getDescriptor().getEnumTypes().get(0);
      }

      private static final Command[] VALUES = values();

      public static Command valueOf(
          com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
        if (desc.getType() != getDescriptor()) {
          throw new java.lang.IllegalArgumentException(
            "EnumValueDescriptor is not for this type.");
        }
        return VALUES[desc.getIndex()];
      }

      private final int index;
      private final int value;

      private Command(int index, int value) {
        this.index = index;
        this.value = value;
      }

      // @@protoc_insertion_point(enum_scope:adb_client.DroneControl.Command)
    }

    private int bitField0_;
    public static final int COMMAND_FIELD_NUMBER = 1;
    private com.github.huvers.adb_client.gen.ControlProtos.DroneControl.Command command_;
    /**
     * <code>optional .adb_client.DroneControl.Command command = 1 [default = UNKNOWN];</code>
     */
    public boolean hasCommand() {
      return ((bitField0_ & 0x00000001) == 0x00000001);
    }
    /**
     * <code>optional .adb_client.DroneControl.Command command = 1 [default = UNKNOWN];</code>
     */
    public com.github.huvers.adb_client.gen.ControlProtos.DroneControl.Command getCommand() {
      return command_;
    }

    private void initFields() {
      command_ = com.github.huvers.adb_client.gen.ControlProtos.DroneControl.Command.UNKNOWN;
    }
    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      getSerializedSize();
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        output.writeEnum(1, command_.getNumber());
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;

      size = 0;
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        size += com.google.protobuf.CodedOutputStream
          .computeEnumSize(1, command_.getNumber());
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    private static final long serialVersionUID = 0L;
    @java.lang.Override
    protected java.lang.Object writeReplace()
        throws java.io.ObjectStreamException {
      return super.writeReplace();
    }

    public static com.github.huvers.adb_client.gen.ControlProtos.DroneControl parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.github.huvers.adb_client.gen.ControlProtos.DroneControl parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.github.huvers.adb_client.gen.ControlProtos.DroneControl parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.github.huvers.adb_client.gen.ControlProtos.DroneControl parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.github.huvers.adb_client.gen.ControlProtos.DroneControl parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static com.github.huvers.adb_client.gen.ControlProtos.DroneControl parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }
    public static com.github.huvers.adb_client.gen.ControlProtos.DroneControl parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input);
    }
    public static com.github.huvers.adb_client.gen.ControlProtos.DroneControl parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input, extensionRegistry);
    }
    public static com.github.huvers.adb_client.gen.ControlProtos.DroneControl parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static com.github.huvers.adb_client.gen.ControlProtos.DroneControl parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }

    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(com.github.huvers.adb_client.gen.ControlProtos.DroneControl prototype) {
      return newBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() { return newBuilder(this); }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code adb_client.DroneControl}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:adb_client.DroneControl)
        com.github.huvers.adb_client.gen.ControlProtos.DroneControlOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return com.github.huvers.adb_client.gen.ControlProtos.internal_static_adb_client_DroneControl_descriptor;
      }

      protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.github.huvers.adb_client.gen.ControlProtos.internal_static_adb_client_DroneControl_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.github.huvers.adb_client.gen.ControlProtos.DroneControl.class, com.github.huvers.adb_client.gen.ControlProtos.DroneControl.Builder.class);
      }

      // Construct using com.github.huvers.adb_client.gen.ControlProtos.DroneControl.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessage.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
        }
      }
      private static Builder create() {
        return new Builder();
      }

      public Builder clear() {
        super.clear();
        command_ = com.github.huvers.adb_client.gen.ControlProtos.DroneControl.Command.UNKNOWN;
        bitField0_ = (bitField0_ & ~0x00000001);
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(buildPartial());
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.github.huvers.adb_client.gen.ControlProtos.internal_static_adb_client_DroneControl_descriptor;
      }

      public com.github.huvers.adb_client.gen.ControlProtos.DroneControl getDefaultInstanceForType() {
        return com.github.huvers.adb_client.gen.ControlProtos.DroneControl.getDefaultInstance();
      }

      public com.github.huvers.adb_client.gen.ControlProtos.DroneControl build() {
        com.github.huvers.adb_client.gen.ControlProtos.DroneControl result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public com.github.huvers.adb_client.gen.ControlProtos.DroneControl buildPartial() {
        com.github.huvers.adb_client.gen.ControlProtos.DroneControl result = new com.github.huvers.adb_client.gen.ControlProtos.DroneControl(this);
        int from_bitField0_ = bitField0_;
        int to_bitField0_ = 0;
        if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
          to_bitField0_ |= 0x00000001;
        }
        result.command_ = command_;
        result.bitField0_ = to_bitField0_;
        onBuilt();
        return result;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof com.github.huvers.adb_client.gen.ControlProtos.DroneControl) {
          return mergeFrom((com.github.huvers.adb_client.gen.ControlProtos.DroneControl)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.github.huvers.adb_client.gen.ControlProtos.DroneControl other) {
        if (other == com.github.huvers.adb_client.gen.ControlProtos.DroneControl.getDefaultInstance()) return this;
        if (other.hasCommand()) {
          setCommand(other.getCommand());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public final boolean isInitialized() {
        return true;
      }

      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        com.github.huvers.adb_client.gen.ControlProtos.DroneControl parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (com.github.huvers.adb_client.gen.ControlProtos.DroneControl) e.getUnfinishedMessage();
          throw e;
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      private com.github.huvers.adb_client.gen.ControlProtos.DroneControl.Command command_ = com.github.huvers.adb_client.gen.ControlProtos.DroneControl.Command.UNKNOWN;
      /**
       * <code>optional .adb_client.DroneControl.Command command = 1 [default = UNKNOWN];</code>
       */
      public boolean hasCommand() {
        return ((bitField0_ & 0x00000001) == 0x00000001);
      }
      /**
       * <code>optional .adb_client.DroneControl.Command command = 1 [default = UNKNOWN];</code>
       */
      public com.github.huvers.adb_client.gen.ControlProtos.DroneControl.Command getCommand() {
        return command_;
      }
      /**
       * <code>optional .adb_client.DroneControl.Command command = 1 [default = UNKNOWN];</code>
       */
      public Builder setCommand(com.github.huvers.adb_client.gen.ControlProtos.DroneControl.Command value) {
        if (value == null) {
          throw new NullPointerException();
        }
        bitField0_ |= 0x00000001;
        command_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional .adb_client.DroneControl.Command command = 1 [default = UNKNOWN];</code>
       */
      public Builder clearCommand() {
        bitField0_ = (bitField0_ & ~0x00000001);
        command_ = com.github.huvers.adb_client.gen.ControlProtos.DroneControl.Command.UNKNOWN;
        onChanged();
        return this;
      }

      // @@protoc_insertion_point(builder_scope:adb_client.DroneControl)
    }

    static {
      defaultInstance = new DroneControl(true);
      defaultInstance.initFields();
    }

    // @@protoc_insertion_point(class_scope:adb_client.DroneControl)
  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_adb_client_DroneControl_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_adb_client_DroneControl_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\rcontrol.proto\022\nadb_client\"\261\002\n\014DroneCon" +
      "trol\022:\n\007command\030\001 \001(\0162 .adb_client.Drone" +
      "Control.Command:\007UNKNOWN\"\344\001\n\007Command\022\013\n\007" +
      "UNKNOWN\020\000\022\021\n\rPITCH_FORWARD\020\001\022\022\n\016PITCH_BA" +
      "CKWARD\020\002\022\r\n\tROLL_LEFT\020\003\022\016\n\nROLL_RIGHT\020\004\022" +
      "\014\n\010YAW_LEFT\020\005\022\r\n\tYAW_RIGHT\020\006\022\025\n\021INCREASE" +
      "_ALTITUDE\020\007\022\025\n\021DECREASE_ALTITUDE\020\010\022\013\n\007TA" +
      "KEOFF\020\t\022\010\n\004LAND\020\n\022\010\n\004LEAP\020\013\022\013\n\007PICTURE\020\014" +
      "\022\r\n\tEMERGENCY\020\rB1\n com.github.huvers.adb" +
      "_client.genB\rControlProtos"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
    internal_static_adb_client_DroneControl_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_adb_client_DroneControl_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessage.FieldAccessorTable(
        internal_static_adb_client_DroneControl_descriptor,
        new java.lang.String[] { "Command", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}