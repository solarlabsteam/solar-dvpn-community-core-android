// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: tendermint/crypto/keys.proto

package tendermint.crypto;

public final class Keys {
  private Keys() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface PublicKeyOrBuilder extends
      // @@protoc_insertion_point(interface_extends:tendermint.crypto.PublicKey)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>bytes ed25519 = 1;</code>
     * @return Whether the ed25519 field is set.
     */
    boolean hasEd25519();
    /**
     * <code>bytes ed25519 = 1;</code>
     * @return The ed25519.
     */
    com.google.protobuf.ByteString getEd25519();

    /**
     * <code>bytes secp256k1 = 2;</code>
     * @return Whether the secp256k1 field is set.
     */
    boolean hasSecp256K1();
    /**
     * <code>bytes secp256k1 = 2;</code>
     * @return The secp256k1.
     */
    com.google.protobuf.ByteString getSecp256K1();

    public PublicKey.SumCase getSumCase();
  }
  /**
   * <pre>
   * PublicKey defines the keys available for use with Tendermint Validators
   * </pre>
   *
   * Protobuf type {@code tendermint.crypto.PublicKey}
   */
  public static final class PublicKey extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:tendermint.crypto.PublicKey)
      PublicKeyOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use PublicKey.newBuilder() to construct.
    private PublicKey(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private PublicKey() {
    }

    @Override
    @SuppressWarnings({"unused"})
    protected Object newInstance(
        UnusedPrivateParameter unused) {
      return new PublicKey();
    }

    @Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private PublicKey(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      this();
      if (extensionRegistry == null) {
        throw new NullPointerException();
      }
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
            case 10: {
              sumCase_ = 1;
              sum_ = input.readBytes();
              break;
            }
            case 18: {
              sumCase_ = 2;
              sum_ = input.readBytes();
              break;
            }
            default: {
              if (!parseUnknownField(
                  input, unknownFields, extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return Keys.internal_static_tendermint_crypto_PublicKey_descriptor;
    }

    @Override
    protected FieldAccessorTable
        internalGetFieldAccessorTable() {
      return Keys.internal_static_tendermint_crypto_PublicKey_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              PublicKey.class, Builder.class);
    }

    private int sumCase_ = 0;
    private Object sum_;
    public enum SumCase
        implements com.google.protobuf.Internal.EnumLite,
            InternalOneOfEnum {
      ED25519(1),
      SECP256K1(2),
      SUM_NOT_SET(0);
      private final int value;
      private SumCase(int value) {
        this.value = value;
      }
      /**
       * @param value The number of the enum to look for.
       * @return The enum associated with the given number.
       * @deprecated Use {@link #forNumber(int)} instead.
       */
      @Deprecated
      public static SumCase valueOf(int value) {
        return forNumber(value);
      }

      public static SumCase forNumber(int value) {
        switch (value) {
          case 1: return ED25519;
          case 2: return SECP256K1;
          case 0: return SUM_NOT_SET;
          default: return null;
        }
      }
      public int getNumber() {
        return this.value;
      }
    };

    public SumCase
    getSumCase() {
      return SumCase.forNumber(
          sumCase_);
    }

    public static final int ED25519_FIELD_NUMBER = 1;
    /**
     * <code>bytes ed25519 = 1;</code>
     * @return Whether the ed25519 field is set.
     */
    @Override
    public boolean hasEd25519() {
      return sumCase_ == 1;
    }
    /**
     * <code>bytes ed25519 = 1;</code>
     * @return The ed25519.
     */
    @Override
    public com.google.protobuf.ByteString getEd25519() {
      if (sumCase_ == 1) {
        return (com.google.protobuf.ByteString) sum_;
      }
      return com.google.protobuf.ByteString.EMPTY;
    }

    public static final int SECP256K1_FIELD_NUMBER = 2;
    /**
     * <code>bytes secp256k1 = 2;</code>
     * @return Whether the secp256k1 field is set.
     */
    @Override
    public boolean hasSecp256K1() {
      return sumCase_ == 2;
    }
    /**
     * <code>bytes secp256k1 = 2;</code>
     * @return The secp256k1.
     */
    @Override
    public com.google.protobuf.ByteString getSecp256K1() {
      if (sumCase_ == 2) {
        return (com.google.protobuf.ByteString) sum_;
      }
      return com.google.protobuf.ByteString.EMPTY;
    }

    private byte memoizedIsInitialized = -1;
    @Override
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;
      return true;
    }

    @Override
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (sumCase_ == 1) {
        output.writeBytes(
            1, (com.google.protobuf.ByteString) sum_);
      }
      if (sumCase_ == 2) {
        output.writeBytes(
            2, (com.google.protobuf.ByteString) sum_);
      }
      unknownFields.writeTo(output);
    }

    @Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (sumCase_ == 1) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(
              1, (com.google.protobuf.ByteString) sum_);
      }
      if (sumCase_ == 2) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(
              2, (com.google.protobuf.ByteString) sum_);
      }
      size += unknownFields.getSerializedSize();
      memoizedSize = size;
      return size;
    }

    @Override
    public boolean equals(final Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof PublicKey)) {
        return super.equals(obj);
      }
      PublicKey other = (PublicKey) obj;

      if (!getSumCase().equals(other.getSumCase())) return false;
      switch (sumCase_) {
        case 1:
          if (!getEd25519()
              .equals(other.getEd25519())) return false;
          break;
        case 2:
          if (!getSecp256K1()
              .equals(other.getSecp256K1())) return false;
          break;
        case 0:
        default:
      }
      if (!unknownFields.equals(other.unknownFields)) return false;
      return true;
    }

    @Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      switch (sumCase_) {
        case 1:
          hash = (37 * hash) + ED25519_FIELD_NUMBER;
          hash = (53 * hash) + getEd25519().hashCode();
          break;
        case 2:
          hash = (37 * hash) + SECP256K1_FIELD_NUMBER;
          hash = (53 * hash) + getSecp256K1().hashCode();
          break;
        case 0:
        default:
      }
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static PublicKey parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static PublicKey parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static PublicKey parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static PublicKey parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static PublicKey parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static PublicKey parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static PublicKey parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static PublicKey parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static PublicKey parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static PublicKey parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static PublicKey parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static PublicKey parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(PublicKey prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    @Override
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @Override
    protected Builder newBuilderForType(
        BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * <pre>
     * PublicKey defines the keys available for use with Tendermint Validators
     * </pre>
     *
     * Protobuf type {@code tendermint.crypto.PublicKey}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:tendermint.crypto.PublicKey)
        PublicKeyOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return Keys.internal_static_tendermint_crypto_PublicKey_descriptor;
      }

      @Override
      protected FieldAccessorTable
          internalGetFieldAccessorTable() {
        return Keys.internal_static_tendermint_crypto_PublicKey_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                PublicKey.class, Builder.class);
      }

      // Construct using tendermint.crypto.Keys.PublicKey.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessageV3
                .alwaysUseFieldBuilders) {
        }
      }
      @Override
      public Builder clear() {
        super.clear();
        sumCase_ = 0;
        sum_ = null;
        return this;
      }

      @Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return Keys.internal_static_tendermint_crypto_PublicKey_descriptor;
      }

      @Override
      public PublicKey getDefaultInstanceForType() {
        return PublicKey.getDefaultInstance();
      }

      @Override
      public PublicKey build() {
        PublicKey result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @Override
      public PublicKey buildPartial() {
        PublicKey result = new PublicKey(this);
        if (sumCase_ == 1) {
          result.sum_ = sum_;
        }
        if (sumCase_ == 2) {
          result.sum_ = sum_;
        }
        result.sumCase_ = sumCase_;
        onBuilt();
        return result;
      }

      @Override
      public Builder clone() {
        return super.clone();
      }
      @Override
      public Builder setField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          Object value) {
        return super.setField(field, value);
      }
      @Override
      public Builder clearField(
          com.google.protobuf.Descriptors.FieldDescriptor field) {
        return super.clearField(field);
      }
      @Override
      public Builder clearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof) {
        return super.clearOneof(oneof);
      }
      @Override
      public Builder setRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          int index, Object value) {
        return super.setRepeatedField(field, index, value);
      }
      @Override
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          Object value) {
        return super.addRepeatedField(field, value);
      }
      @Override
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof PublicKey) {
          return mergeFrom((PublicKey)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(PublicKey other) {
        if (other == PublicKey.getDefaultInstance()) return this;
        switch (other.getSumCase()) {
          case ED25519: {
            setEd25519(other.getEd25519());
            break;
          }
          case SECP256K1: {
            setSecp256K1(other.getSecp256K1());
            break;
          }
          case SUM_NOT_SET: {
            break;
          }
        }
        this.mergeUnknownFields(other.unknownFields);
        onChanged();
        return this;
      }

      @Override
      public final boolean isInitialized() {
        return true;
      }

      @Override
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        PublicKey parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (PublicKey) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int sumCase_ = 0;
      private Object sum_;
      public SumCase
          getSumCase() {
        return SumCase.forNumber(
            sumCase_);
      }

      public Builder clearSum() {
        sumCase_ = 0;
        sum_ = null;
        onChanged();
        return this;
      }


      /**
       * <code>bytes ed25519 = 1;</code>
       * @return Whether the ed25519 field is set.
       */
      public boolean hasEd25519() {
        return sumCase_ == 1;
      }
      /**
       * <code>bytes ed25519 = 1;</code>
       * @return The ed25519.
       */
      public com.google.protobuf.ByteString getEd25519() {
        if (sumCase_ == 1) {
          return (com.google.protobuf.ByteString) sum_;
        }
        return com.google.protobuf.ByteString.EMPTY;
      }
      /**
       * <code>bytes ed25519 = 1;</code>
       * @param value The ed25519 to set.
       * @return This builder for chaining.
       */
      public Builder setEd25519(com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  sumCase_ = 1;
        sum_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>bytes ed25519 = 1;</code>
       * @return This builder for chaining.
       */
      public Builder clearEd25519() {
        if (sumCase_ == 1) {
          sumCase_ = 0;
          sum_ = null;
          onChanged();
        }
        return this;
      }

      /**
       * <code>bytes secp256k1 = 2;</code>
       * @return Whether the secp256k1 field is set.
       */
      public boolean hasSecp256K1() {
        return sumCase_ == 2;
      }
      /**
       * <code>bytes secp256k1 = 2;</code>
       * @return The secp256k1.
       */
      public com.google.protobuf.ByteString getSecp256K1() {
        if (sumCase_ == 2) {
          return (com.google.protobuf.ByteString) sum_;
        }
        return com.google.protobuf.ByteString.EMPTY;
      }
      /**
       * <code>bytes secp256k1 = 2;</code>
       * @param value The secp256k1 to set.
       * @return This builder for chaining.
       */
      public Builder setSecp256K1(com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  sumCase_ = 2;
        sum_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>bytes secp256k1 = 2;</code>
       * @return This builder for chaining.
       */
      public Builder clearSecp256K1() {
        if (sumCase_ == 2) {
          sumCase_ = 0;
          sum_ = null;
          onChanged();
        }
        return this;
      }
      @Override
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.setUnknownFields(unknownFields);
      }

      @Override
      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.mergeUnknownFields(unknownFields);
      }


      // @@protoc_insertion_point(builder_scope:tendermint.crypto.PublicKey)
    }

    // @@protoc_insertion_point(class_scope:tendermint.crypto.PublicKey)
    private static final PublicKey DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new PublicKey();
    }

    public static PublicKey getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<PublicKey>
        PARSER = new com.google.protobuf.AbstractParser<PublicKey>() {
      @Override
      public PublicKey parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new PublicKey(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<PublicKey> parser() {
      return PARSER;
    }

    @Override
    public com.google.protobuf.Parser<PublicKey> getParserForType() {
      return PARSER;
    }

    @Override
    public PublicKey getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_tendermint_crypto_PublicKey_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_tendermint_crypto_PublicKey_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    String[] descriptorData = {
      "\n\034tendermint/crypto/keys.proto\022\021tendermi" +
      "nt.crypto\032\024gogoproto/gogo.proto\"D\n\tPubli" +
      "cKey\022\021\n\007ed25519\030\001 \001(\014H\000\022\023\n\tsecp256k1\030\002 \001" +
      "(\014H\000:\010\350\241\037\001\350\240\037\001B\005\n\003sumB:Z8github.com/tend" +
      "ermint/tendermint/proto/tendermint/crypt" +
      "ob\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          com.google.protobuf2.GoGoProtos.getDescriptor(),
        });
    internal_static_tendermint_crypto_PublicKey_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_tendermint_crypto_PublicKey_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_tendermint_crypto_PublicKey_descriptor,
        new String[] { "Ed25519", "Secp256K1", "Sum", });
    com.google.protobuf.ExtensionRegistry registry =
        com.google.protobuf.ExtensionRegistry.newInstance();
    registry.add(com.google.protobuf2.GoGoProtos.compare);
    registry.add(com.google.protobuf2.GoGoProtos.equal);
    com.google.protobuf.Descriptors.FileDescriptor
        .internalUpdateFileDescriptor(descriptor, registry);
    com.google.protobuf2.GoGoProtos.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
