package com.servitization.commons.socket.message;

import com.google.protobuf.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectStreamException;

public final class SocketRequest {
    private SocketRequest() {
    }

    public static void registerAllExtensions(
            com.google.protobuf.ExtensionRegistry registry) {
    }

    public interface RequestOrBuilder
            extends com.google.protobuf.MessageOrBuilder {
        // required string serviceName = 1;

        /**
         * <code>required string serviceName = 1;</code>
         * <p>
         * <pre>
         * 接口名称
         * </pre>
         */
        boolean hasServiceName();

        /**
         * <code>required string serviceName = 1;</code>
         * <p>
         * <pre>
         * 接口名称
         * </pre>
         */
        String getServiceName();

        /**
         * <code>required string serviceName = 1;</code>
         * <p>
         * <pre>
         * 接口名称
         * </pre>
         */
        ByteString getServiceNameBytes();

        // optional string serviceVersion = 2;

        /**
         * <code>optional string serviceVersion = 2;</code>
         * <p>
         * <pre>
         * 接口版本
         * </pre>
         */
        boolean hasServiceVersion();

        /**
         * <code>optional string serviceVersion = 2;</code>
         * <p>
         * <pre>
         * 接口版本
         * </pre>
         */
        String getServiceVersion();

        /**
         * <code>optional string serviceVersion = 2;</code>
         * <p>
         * <pre>
         * 接口版本
         * </pre>
         */
        ByteString getServiceVersionBytes();

        // required string marking = 3;

        /**
         * <code>required string marking = 3;</code>
         * <p>
         * <pre>
         * 请求唯一标示rpcId
         * </pre>
         */
        boolean hasMarking();

        /**
         * <code>required string marking = 3;</code>
         * <p>
         * <pre>
         * 请求唯一标示rpcId
         * </pre>
         */
        String getMarking();

        /**
         * <code>required string marking = 3;</code>
         * <p>
         * <pre>
         * 请求唯一标示rpcId
         * </pre>
         */
        ByteString getMarkingBytes();

        // required int32 charset = 4;

        /**
         * <code>required int32 charset = 4;</code>
         * <p>
         * <pre>
         * 编码格式
         * </pre>
         */
        boolean hasCharset();

        /**
         * <code>required int32 charset = 4;</code>
         * <p>
         * <pre>
         * 编码格式
         * </pre>
         */
        int getCharset();

        // required int32 compress = 5;

        /**
         * <code>required int32 compress = 5;</code>
         * <p>
         * <pre>
         * 压缩
         * </pre>
         */
        boolean hasCompress();

        /**
         * <code>required int32 compress = 5;</code>
         * <p>
         * <pre>
         * 压缩
         * </pre>
         */
        int getCompress();

        // required int32 encrypt = 6;

        /**
         * <code>required int32 encrypt = 6;</code>
         * <p>
         * <pre>
         * 加密
         * </pre>
         */
        boolean hasEncrypt();

        /**
         * <code>required int32 encrypt = 6;</code>
         * <p>
         * <pre>
         * 加密
         * </pre>
         */
        int getEncrypt();

        // required int32 protocol = 7;

        /**
         * <code>required int32 protocol = 7;</code>
         * <p>
         * <pre>
         * 协议
         * </pre>
         */
        boolean hasProtocol();

        /**
         * <code>required int32 protocol = 7;</code>
         * <p>
         * <pre>
         * 协议
         * </pre>
         */
        int getProtocol();

        // required int32 socketVersion = 8;

        /**
         * <code>required int32 socketVersion = 8;</code>
         * <p>
         * <pre>
         * 请求协议版本
         * </pre>
         */
        boolean hasSocketVersion();

        /**
         * <code>required int32 socketVersion = 8;</code>
         * <p>
         * <pre>
         * 请求协议版本
         * </pre>
         */
        int getSocketVersion();

        // optional bytes request = 10;

        /**
         * <code>optional bytes request = 10;</code>
         * <p>
         * <pre>
         * 请求数据
         * </pre>
         */
        boolean hasRequest();

        /**
         * <code>optional bytes request = 10;</code>
         * <p>
         * <pre>
         * 请求数据
         * </pre>
         */
        ByteString getRequest();

        // optional string traceId = 11;

        /**
         * <code>optional string traceId = 11;</code>
         * <p>
         * <pre>
         * 追踪id
         * </pre>
         */
        boolean hasTraceId();

        /**
         * <code>optional string traceId = 11;</code>
         * <p>
         * <pre>
         * 追踪id
         * </pre>
         */
        String getTraceId();

        /**
         * <code>optional string traceId = 11;</code>
         * <p>
         * <pre>
         * 追踪id
         * </pre>
         */
        ByteString getTraceIdBytes();

        // optional string span = 12;

        /**
         * <code>optional string span = 12;</code>
         * <p>
         * <pre>
         * 追踪路径
         * </pre>
         */
        boolean hasSpan();

        /**
         * <code>optional string span = 12;</code>
         * <p>
         * <pre>
         * 追踪路径
         * </pre>
         */
        String getSpan();

        /**
         * <code>optional string span = 12;</code>
         * <p>
         * <pre>
         * 追踪路径
         * </pre>
         */
        ByteString getSpanBytes();
    }

    public static final class Request extends GeneratedMessage
            implements RequestOrBuilder {
        // Use Request.newBuilder() to construct.
        private Request(GeneratedMessage.Builder<?> builder) {
            super(builder);
            this.unknownFields = builder.getUnknownFields();
        }

        private Request(boolean noInit) {
            this.unknownFields = com.google.protobuf.UnknownFieldSet.getDefaultInstance();
        }

        private static final Request defaultInstance;

        public static Request getDefaultInstance() {
            return defaultInstance;
        }

        public Request getDefaultInstanceForType() {
            return defaultInstance;
        }

        private final UnknownFieldSet unknownFields;

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private Request(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
                throws InvalidProtocolBufferException {
            initFields();
            UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
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
                        case 10: {
                            bitField0_ |= 0x00000001;
                            serviceName_ = input.readBytes();
                            break;
                        }
                        case 18: {
                            bitField0_ |= 0x00000002;
                            serviceVersion_ = input.readBytes();
                            break;
                        }
                        case 26: {
                            bitField0_ |= 0x00000004;
                            marking_ = input.readBytes();
                            break;
                        }
                        case 32: {
                            bitField0_ |= 0x00000008;
                            charset_ = input.readInt32();
                            break;
                        }
                        case 40: {
                            bitField0_ |= 0x00000010;
                            compress_ = input.readInt32();
                            break;
                        }
                        case 48: {
                            bitField0_ |= 0x00000020;
                            encrypt_ = input.readInt32();
                            break;
                        }
                        case 56: {
                            bitField0_ |= 0x00000040;
                            protocol_ = input.readInt32();
                            break;
                        }
                        case 64: {
                            bitField0_ |= 0x00000080;
                            socketVersion_ = input.readInt32();
                            break;
                        }
                        case 82: {
                            bitField0_ |= 0x00000100;
                            request_ = input.readBytes();
                            break;
                        }
                        case 90: {
                            bitField0_ |= 0x00000200;
                            traceId_ = input.readBytes();
                            break;
                        }
                        case 98: {
                            bitField0_ |= 0x00000400;
                            span_ = input.readBytes();
                            break;
                        }
                    }
                }
            } catch (InvalidProtocolBufferException e) {
                throw e.setUnfinishedMessage(this);
            } catch (java.io.IOException e) {
                throw new InvalidProtocolBufferException(
                        e.getMessage()).setUnfinishedMessage(this);
            } finally {
                this.unknownFields = unknownFields.build();
                makeExtensionsImmutable();
            }
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_socket_message_Request_descriptor;
        }

        protected FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_socket_message_Request_fieldAccessorTable
                    .ensureFieldAccessorsInitialized(
                            Request.class, Request.Builder.class);
        }

        public static Parser<Request> PARSER = new AbstractParser<Request>() {
            public Request parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
                    throws InvalidProtocolBufferException {
                return new Request(input, extensionRegistry);
            }
        };

        @Override
        public Parser<Request> getParserForType() {
            return PARSER;
        }

        private int bitField0_;
        // required string serviceName = 1;
        public static final int SERVICENAME_FIELD_NUMBER = 1;
        private Object serviceName_;

        /**
         * <code>required string serviceName = 1;</code>
         * <p>
         * <pre>
         * 接口名称
         * </pre>
         */
        public boolean hasServiceName() {
            return ((bitField0_ & 0x00000001) == 0x00000001);
        }

        /**
         * <code>required string serviceName = 1;</code>
         * <p>
         * <pre>
         * 接口名称
         * </pre>
         */
        public String getServiceName() {
            Object ref = serviceName_;
            if (ref instanceof String) {
                return (String) ref;
            } else {
                ByteString bs = (ByteString) ref;
                String s = bs.toStringUtf8();
                if (bs.isValidUtf8()) {
                    serviceName_ = s;
                }
                return s;
            }
        }

        /**
         * <code>required string serviceName = 1;</code>
         * <p>
         * <pre>
         * 接口名称
         * </pre>
         */
        public ByteString getServiceNameBytes() {
            Object ref = serviceName_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String) ref);
                serviceName_ = b;
                return b;
            } else {
                return (ByteString) ref;
            }
        }

        // optional string serviceVersion = 2;
        public static final int SERVICEVERSION_FIELD_NUMBER = 2;
        private Object serviceVersion_;

        /**
         * <code>optional string serviceVersion = 2;</code>
         * <p>
         * <pre>
         * 接口版本
         * </pre>
         */
        public boolean hasServiceVersion() {
            return ((bitField0_ & 0x00000002) == 0x00000002);
        }

        /**
         * <code>optional string serviceVersion = 2;</code>
         * <p>
         * <pre>
         * 接口版本
         * </pre>
         */
        public String getServiceVersion() {
            Object ref = serviceVersion_;
            if (ref instanceof String) {
                return (String) ref;
            } else {
                ByteString bs = (ByteString) ref;
                String s = bs.toStringUtf8();
                if (bs.isValidUtf8()) {
                    serviceVersion_ = s;
                }
                return s;
            }
        }

        /**
         * <code>optional string serviceVersion = 2;</code>
         * <p>
         * <pre>
         * 接口版本
         * </pre>
         */
        public com.google.protobuf.ByteString
        getServiceVersionBytes() {
            Object ref = serviceVersion_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String) ref);
                serviceVersion_ = b;
                return b;
            } else {
                return (ByteString) ref;
            }
        }

        // required string marking = 3;
        public static final int MARKING_FIELD_NUMBER = 3;
        private Object marking_;

        /**
         * <code>required string marking = 3;</code>
         * <p>
         * <pre>
         * 请求唯一标示rpcId
         * </pre>
         */
        public boolean hasMarking() {
            return ((bitField0_ & 0x00000004) == 0x00000004);
        }

        /**
         * <code>required string marking = 3;</code>
         * <p>
         * <pre>
         * 请求唯一标示rpcId
         * </pre>
         */
        public String getMarking() {
            Object ref = marking_;
            if (ref instanceof String) {
                return (String) ref;
            } else {
                ByteString bs = (ByteString) ref;
                String s = bs.toStringUtf8();
                if (bs.isValidUtf8()) {
                    marking_ = s;
                }
                return s;
            }
        }

        /**
         * <code>required string marking = 3;</code>
         * <p>
         * <pre>
         * 请求唯一标示rpcId
         * </pre>
         */
        public ByteString getMarkingBytes() {
            Object ref = marking_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String) ref);
                marking_ = b;
                return b;
            } else {
                return (ByteString) ref;
            }
        }

        // required int32 charset = 4;
        public static final int CHARSET_FIELD_NUMBER = 4;
        private int charset_;

        /**
         * <code>required int32 charset = 4;</code>
         * <p>
         * <pre>
         * 编码格式
         * </pre>
         */
        public boolean hasCharset() {
            return ((bitField0_ & 0x00000008) == 0x00000008);
        }

        /**
         * <code>required int32 charset = 4;</code>
         * <p>
         * <pre>
         * 编码格式
         * </pre>
         */
        public int getCharset() {
            return charset_;
        }

        // required int32 compress = 5;
        public static final int COMPRESS_FIELD_NUMBER = 5;
        private int compress_;

        /**
         * <code>required int32 compress = 5;</code>
         * <p>
         * <pre>
         * 压缩
         * </pre>
         */
        public boolean hasCompress() {
            return ((bitField0_ & 0x00000010) == 0x00000010);
        }

        /**
         * <code>required int32 compress = 5;</code>
         * <p>
         * <pre>
         * 压缩
         * </pre>
         */
        public int getCompress() {
            return compress_;
        }

        // required int32 encrypt = 6;
        public static final int ENCRYPT_FIELD_NUMBER = 6;
        private int encrypt_;

        /**
         * <code>required int32 encrypt = 6;</code>
         * <p>
         * <pre>
         * 加密
         * </pre>
         */
        public boolean hasEncrypt() {
            return ((bitField0_ & 0x00000020) == 0x00000020);
        }

        /**
         * <code>required int32 encrypt = 6;</code>
         * <p>
         * <pre>
         * 加密
         * </pre>
         */
        public int getEncrypt() {
            return encrypt_;
        }

        // required int32 protocol = 7;
        public static final int PROTOCOL_FIELD_NUMBER = 7;
        private int protocol_;

        /**
         * <code>required int32 protocol = 7;</code>
         * <p>
         * <pre>
         * 协议
         * </pre>
         */
        public boolean hasProtocol() {
            return ((bitField0_ & 0x00000040) == 0x00000040);
        }

        /**
         * <code>required int32 protocol = 7;</code>
         * <p>
         * <pre>
         * 协议
         * </pre>
         */
        public int getProtocol() {
            return protocol_;
        }

        // required int32 socketVersion = 8;
        public static final int SOCKETVERSION_FIELD_NUMBER = 8;
        private int socketVersion_;

        /**
         * <code>required int32 socketVersion = 8;</code>
         * <p>
         * <pre>
         * 请求协议版本
         * </pre>
         */
        public boolean hasSocketVersion() {
            return ((bitField0_ & 0x00000080) == 0x00000080);
        }

        /**
         * <code>required int32 socketVersion = 8;</code>
         * <p>
         * <pre>
         * 请求协议版本
         * </pre>
         */
        public int getSocketVersion() {
            return socketVersion_;
        }

        // optional bytes request = 10;
        public static final int REQUEST_FIELD_NUMBER = 10;
        private ByteString request_;

        /**
         * <code>optional bytes request = 10;</code>
         * <p>
         * <pre>
         * 请求数据
         * </pre>
         */
        public boolean hasRequest() {
            return ((bitField0_ & 0x00000100) == 0x00000100);
        }

        /**
         * <code>optional bytes request = 10;</code>
         * <p>
         * <pre>
         * 请求数据
         * </pre>
         */
        public ByteString getRequest() {
            return request_;
        }

        // optional string traceId = 11;
        public static final int TRACEID_FIELD_NUMBER = 11;
        private Object traceId_;

        /**
         * <code>optional string traceId = 11;</code>
         * <p>
         * <pre>
         * 追踪id
         * </pre>
         */
        public boolean hasTraceId() {
            return ((bitField0_ & 0x00000200) == 0x00000200);
        }

        /**
         * <code>optional string traceId = 11;</code>
         * <p>
         * <pre>
         * 追踪id
         * </pre>
         */
        public String getTraceId() {
            Object ref = traceId_;
            if (ref instanceof String) {
                return (String) ref;
            } else {
                ByteString bs = (ByteString) ref;
                String s = bs.toStringUtf8();
                if (bs.isValidUtf8()) {
                    traceId_ = s;
                }
                return s;
            }
        }

        /**
         * <code>optional string traceId = 11;</code>
         * <p>
         * <pre>
         * 追踪id
         * </pre>
         */
        public ByteString getTraceIdBytes() {
            Object ref = traceId_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String) ref);
                traceId_ = b;
                return b;
            } else {
                return (ByteString) ref;
            }
        }

        // optional string span = 12;
        public static final int SPAN_FIELD_NUMBER = 12;
        private Object span_;

        /**
         * <code>optional string span = 12;</code>
         * <p>
         * <pre>
         * 追踪路径
         * </pre>
         */
        public boolean hasSpan() {
            return ((bitField0_ & 0x00000400) == 0x00000400);
        }

        /**
         * <code>optional string span = 12;</code>
         * <p>
         * <pre>
         * 追踪路径
         * </pre>
         */
        public String getSpan() {
            Object ref = span_;
            if (ref instanceof String) {
                return (String) ref;
            } else {
                ByteString bs = (ByteString) ref;
                String s = bs.toStringUtf8();
                if (bs.isValidUtf8()) {
                    span_ = s;
                }
                return s;
            }
        }

        /**
         * <code>optional string span = 12;</code>
         * <p>
         * <pre>
         * 追踪路径
         * </pre>
         */
        public ByteString getSpanBytes() {
            Object ref = span_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String) ref);
                span_ = b;
                return b;
            } else {
                return (ByteString) ref;
            }
        }

        private void initFields() {
            serviceName_ = "";
            serviceVersion_ = "";
            marking_ = "";
            charset_ = 0;
            compress_ = 0;
            encrypt_ = 0;
            protocol_ = 0;
            socketVersion_ = 0;
            request_ = ByteString.EMPTY;
            traceId_ = "";
            span_ = "";
        }

        private byte memoizedIsInitialized = -1;

        public final boolean isInitialized() {
            byte isInitialized = memoizedIsInitialized;
            if (isInitialized != -1) return isInitialized == 1;

            if (!hasServiceName()) {
                memoizedIsInitialized = 0;
                return false;
            }
            if (!hasMarking()) {
                memoizedIsInitialized = 0;
                return false;
            }
            if (!hasCharset()) {
                memoizedIsInitialized = 0;
                return false;
            }
            if (!hasCompress()) {
                memoizedIsInitialized = 0;
                return false;
            }
            if (!hasEncrypt()) {
                memoizedIsInitialized = 0;
                return false;
            }
            if (!hasProtocol()) {
                memoizedIsInitialized = 0;
                return false;
            }
            if (!hasSocketVersion()) {
                memoizedIsInitialized = 0;
                return false;
            }
            memoizedIsInitialized = 1;
            return true;
        }

        public void writeTo(CodedOutputStream output)
                throws java.io.IOException {
            getSerializedSize();
            if (((bitField0_ & 0x00000001) == 0x00000001)) {
                output.writeBytes(1, getServiceNameBytes());
            }
            if (((bitField0_ & 0x00000002) == 0x00000002)) {
                output.writeBytes(2, getServiceVersionBytes());
            }
            if (((bitField0_ & 0x00000004) == 0x00000004)) {
                output.writeBytes(3, getMarkingBytes());
            }
            if (((bitField0_ & 0x00000008) == 0x00000008)) {
                output.writeInt32(4, charset_);
            }
            if (((bitField0_ & 0x00000010) == 0x00000010)) {
                output.writeInt32(5, compress_);
            }
            if (((bitField0_ & 0x00000020) == 0x00000020)) {
                output.writeInt32(6, encrypt_);
            }
            if (((bitField0_ & 0x00000040) == 0x00000040)) {
                output.writeInt32(7, protocol_);
            }
            if (((bitField0_ & 0x00000080) == 0x00000080)) {
                output.writeInt32(8, socketVersion_);
            }
            if (((bitField0_ & 0x00000100) == 0x00000100)) {
                output.writeBytes(10, request_);
            }
            if (((bitField0_ & 0x00000200) == 0x00000200)) {
                output.writeBytes(11, getTraceIdBytes());
            }
            if (((bitField0_ & 0x00000400) == 0x00000400)) {
                output.writeBytes(12, getSpanBytes());
            }
            getUnknownFields().writeTo(output);
        }

        private int memoizedSerializedSize = -1;

        public int getSerializedSize() {
            int size = memoizedSerializedSize;
            if (size != -1) return size;

            size = 0;
            if (((bitField0_ & 0x00000001) == 0x00000001)) {
                size += CodedOutputStream.computeBytesSize(1, getServiceNameBytes());
            }
            if (((bitField0_ & 0x00000002) == 0x00000002)) {
                size += CodedOutputStream.computeBytesSize(2, getServiceVersionBytes());
            }
            if (((bitField0_ & 0x00000004) == 0x00000004)) {
                size += CodedOutputStream.computeBytesSize(3, getMarkingBytes());
            }
            if (((bitField0_ & 0x00000008) == 0x00000008)) {
                size += CodedOutputStream.computeInt32Size(4, charset_);
            }
            if (((bitField0_ & 0x00000010) == 0x00000010)) {
                size += CodedOutputStream.computeInt32Size(5, compress_);
            }
            if (((bitField0_ & 0x00000020) == 0x00000020)) {
                size += CodedOutputStream.computeInt32Size(6, encrypt_);
            }
            if (((bitField0_ & 0x00000040) == 0x00000040)) {
                size += CodedOutputStream.computeInt32Size(7, protocol_);
            }
            if (((bitField0_ & 0x00000080) == 0x00000080)) {
                size += CodedOutputStream.computeInt32Size(8, socketVersion_);
            }
            if (((bitField0_ & 0x00000100) == 0x00000100)) {
                size += CodedOutputStream.computeBytesSize(10, request_);
            }
            if (((bitField0_ & 0x00000200) == 0x00000200)) {
                size += CodedOutputStream.computeBytesSize(11, getTraceIdBytes());
            }
            if (((bitField0_ & 0x00000400) == 0x00000400)) {
                size += CodedOutputStream.computeBytesSize(12, getSpanBytes());
            }
            size += getUnknownFields().getSerializedSize();
            memoizedSerializedSize = size;
            return size;
        }

        private static final long serialVersionUID = 0L;

        @Override
        protected Object writeReplace()
                throws ObjectStreamException {
            return super.writeReplace();
        }

        public static Request parseFrom(ByteString data)
                throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Request parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry)
                throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Request parseFrom(byte[] data)
                throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Request parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry)
                throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Request parseFrom(InputStream input)
                throws IOException {
            return PARSER.parseFrom(input);
        }

        public static Request parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry)
                throws IOException {
            return PARSER.parseFrom(input, extensionRegistry);
        }

        public static Request parseDelimitedFrom(InputStream input)
                throws IOException {
            return PARSER.parseDelimitedFrom(input);
        }

        public static Request parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry)
                throws IOException {
            return PARSER.parseDelimitedFrom(input, extensionRegistry);
        }

        public static Request parseFrom(CodedInputStream input)
                throws IOException {
            return PARSER.parseFrom(input);
        }

        public static Request parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
                throws IOException {
            return PARSER.parseFrom(input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder(Request prototype) {
            return newBuilder().mergeFrom(prototype);
        }

        public Builder toBuilder() {
            return newBuilder(this);
        }

        @Override
        protected Builder newBuilderForType(GeneratedMessage.BuilderParent parent) {
            Builder builder = new Builder(parent);
            return builder;
        }

        public static final class Builder extends GeneratedMessage.Builder<Builder>
                implements RequestOrBuilder {
            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_socket_message_Request_descriptor;
            }

            protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_socket_message_Request_fieldAccessorTable
                        .ensureFieldAccessorsInitialized(
                                Request.class, Request.Builder.class);
            }

            // Construct using Request.newBuilder()
            private Builder() {
                maybeForceBuilderInitialization();
            }

            private Builder(GeneratedMessage.BuilderParent parent) {
                super(parent);
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (GeneratedMessage.alwaysUseFieldBuilders) {
                }
            }

            private static Builder create() {
                return new Builder();
            }

            public Builder clear() {
                super.clear();
                serviceName_ = "";
                bitField0_ = (bitField0_ & ~0x00000001);
                serviceVersion_ = "";
                bitField0_ = (bitField0_ & ~0x00000002);
                marking_ = "";
                bitField0_ = (bitField0_ & ~0x00000004);
                charset_ = 0;
                bitField0_ = (bitField0_ & ~0x00000008);
                compress_ = 0;
                bitField0_ = (bitField0_ & ~0x00000010);
                encrypt_ = 0;
                bitField0_ = (bitField0_ & ~0x00000020);
                protocol_ = 0;
                bitField0_ = (bitField0_ & ~0x00000040);
                socketVersion_ = 0;
                bitField0_ = (bitField0_ & ~0x00000080);
                request_ = com.google.protobuf.ByteString.EMPTY;
                bitField0_ = (bitField0_ & ~0x00000100);
                traceId_ = "";
                bitField0_ = (bitField0_ & ~0x00000200);
                span_ = "";
                bitField0_ = (bitField0_ & ~0x00000400);
                return this;
            }

            public Builder clone() {
                return create().mergeFrom(buildPartial());
            }

            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_socket_message_Request_descriptor;
            }

            public Request getDefaultInstanceForType() {
                return Request.getDefaultInstance();
            }

            public Request build() {
                Request result = buildPartial();
                if (!result.isInitialized()) {
                    throw newUninitializedMessageException(result);
                }
                return result;
            }

            public Request buildPartial() {
                Request result = new Request(this);
                int from_bitField0_ = bitField0_;
                int to_bitField0_ = 0;
                if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
                    to_bitField0_ |= 0x00000001;
                }
                result.serviceName_ = serviceName_;
                if (((from_bitField0_ & 0x00000002) == 0x00000002)) {
                    to_bitField0_ |= 0x00000002;
                }
                result.serviceVersion_ = serviceVersion_;
                if (((from_bitField0_ & 0x00000004) == 0x00000004)) {
                    to_bitField0_ |= 0x00000004;
                }
                result.marking_ = marking_;
                if (((from_bitField0_ & 0x00000008) == 0x00000008)) {
                    to_bitField0_ |= 0x00000008;
                }
                result.charset_ = charset_;
                if (((from_bitField0_ & 0x00000010) == 0x00000010)) {
                    to_bitField0_ |= 0x00000010;
                }
                result.compress_ = compress_;
                if (((from_bitField0_ & 0x00000020) == 0x00000020)) {
                    to_bitField0_ |= 0x00000020;
                }
                result.encrypt_ = encrypt_;
                if (((from_bitField0_ & 0x00000040) == 0x00000040)) {
                    to_bitField0_ |= 0x00000040;
                }
                result.protocol_ = protocol_;
                if (((from_bitField0_ & 0x00000080) == 0x00000080)) {
                    to_bitField0_ |= 0x00000080;
                }
                result.socketVersion_ = socketVersion_;
                if (((from_bitField0_ & 0x00000100) == 0x00000100)) {
                    to_bitField0_ |= 0x00000100;
                }
                result.request_ = request_;
                if (((from_bitField0_ & 0x00000200) == 0x00000200)) {
                    to_bitField0_ |= 0x00000200;
                }
                result.traceId_ = traceId_;
                if (((from_bitField0_ & 0x00000400) == 0x00000400)) {
                    to_bitField0_ |= 0x00000400;
                }
                result.span_ = span_;
                result.bitField0_ = to_bitField0_;
                onBuilt();
                return result;
            }

            public Builder mergeFrom(Message other) {
                if (other instanceof Request) {
                    return mergeFrom((Request) other);
                } else {
                    super.mergeFrom(other);
                    return this;
                }
            }

            public Builder mergeFrom(Request other) {
                if (other == Request.getDefaultInstance()) return this;
                if (other.hasServiceName()) {
                    bitField0_ |= 0x00000001;
                    serviceName_ = other.serviceName_;
                    onChanged();
                }
                if (other.hasServiceVersion()) {
                    bitField0_ |= 0x00000002;
                    serviceVersion_ = other.serviceVersion_;
                    onChanged();
                }
                if (other.hasMarking()) {
                    bitField0_ |= 0x00000004;
                    marking_ = other.marking_;
                    onChanged();
                }
                if (other.hasCharset()) {
                    setCharset(other.getCharset());
                }
                if (other.hasCompress()) {
                    setCompress(other.getCompress());
                }
                if (other.hasEncrypt()) {
                    setEncrypt(other.getEncrypt());
                }
                if (other.hasProtocol()) {
                    setProtocol(other.getProtocol());
                }
                if (other.hasSocketVersion()) {
                    setSocketVersion(other.getSocketVersion());
                }
                if (other.hasRequest()) {
                    setRequest(other.getRequest());
                }
                if (other.hasTraceId()) {
                    bitField0_ |= 0x00000200;
                    traceId_ = other.traceId_;
                    onChanged();
                }
                if (other.hasSpan()) {
                    bitField0_ |= 0x00000400;
                    span_ = other.span_;
                    onChanged();
                }
                this.mergeUnknownFields(other.getUnknownFields());
                return this;
            }

            public final boolean isInitialized() {
                if (!hasServiceName()) {

                    return false;
                }
                if (!hasMarking()) {

                    return false;
                }
                if (!hasCharset()) {

                    return false;
                }
                if (!hasCompress()) {

                    return false;
                }
                if (!hasEncrypt()) {

                    return false;
                }
                if (!hasProtocol()) {

                    return false;
                }
                if (!hasSocketVersion()) {

                    return false;
                }
                return true;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
                    throws IOException {
                Request parsedMessage = null;
                try {
                    parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
                } catch (InvalidProtocolBufferException e) {
                    parsedMessage = (Request) e.getUnfinishedMessage();
                    throw e;
                } finally {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                }
                return this;
            }

            private int bitField0_;

            // required string serviceName = 1;
            private Object serviceName_ = "";

            /**
             * <code>required string serviceName = 1;</code>
             * <p>
             * <pre>
             * 接口名称
             * </pre>
             */
            public boolean hasServiceName() {
                return ((bitField0_ & 0x00000001) == 0x00000001);
            }

            /**
             * <code>required string serviceName = 1;</code>
             * <p>
             * <pre>
             * 接口名称
             * </pre>
             */
            public String getServiceName() {
                Object ref = serviceName_;
                if (!(ref instanceof String)) {
                    String s = ((ByteString) ref).toStringUtf8();
                    serviceName_ = s;
                    return s;
                } else {
                    return (String) ref;
                }
            }

            /**
             * <code>required string serviceName = 1;</code>
             * <p>
             * <pre>
             * 接口名称
             * </pre>
             */
            public ByteString getServiceNameBytes() {
                Object ref = serviceName_;
                if (ref instanceof String) {
                    ByteString b = ByteString.copyFromUtf8((String) ref);
                    serviceName_ = b;
                    return b;
                } else {
                    return (ByteString) ref;
                }
            }

            /**
             * <code>required string serviceName = 1;</code>
             * <p>
             * <pre>
             * 接口名称
             * </pre>
             */
            public Builder setServiceName(
                    String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                bitField0_ |= 0x00000001;
                serviceName_ = value;
                onChanged();
                return this;
            }

            /**
             * <code>required string serviceName = 1;</code>
             * <p>
             * <pre>
             * 接口名称
             * </pre>
             */
            public Builder clearServiceName() {
                bitField0_ = (bitField0_ & ~0x00000001);
                serviceName_ = getDefaultInstance().getServiceName();
                onChanged();
                return this;
            }

            /**
             * <code>required string serviceName = 1;</code>
             * <p>
             * <pre>
             * 接口名称
             * </pre>
             */
            public Builder setServiceNameBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                bitField0_ |= 0x00000001;
                serviceName_ = value;
                onChanged();
                return this;
            }

            // optional string serviceVersion = 2;
            private Object serviceVersion_ = "";

            /**
             * <code>optional string serviceVersion = 2;</code>
             * <p>
             * <pre>
             * 接口版本
             * </pre>
             */
            public boolean hasServiceVersion() {
                return ((bitField0_ & 0x00000002) == 0x00000002);
            }

            /**
             * <code>optional string serviceVersion = 2;</code>
             * <p>
             * <pre>
             * 接口版本
             * </pre>
             */
            public String getServiceVersion() {
                Object ref = serviceVersion_;
                if (!(ref instanceof String)) {
                    String s = ((ByteString) ref).toStringUtf8();
                    serviceVersion_ = s;
                    return s;
                } else {
                    return (String) ref;
                }
            }

            /**
             * <code>optional string serviceVersion = 2;</code>
             * <p>
             * <pre>
             * 接口版本
             * </pre>
             */
            public ByteString getServiceVersionBytes() {
                Object ref = serviceVersion_;
                if (ref instanceof String) {
                    ByteString b = ByteString.copyFromUtf8((String) ref);
                    serviceVersion_ = b;
                    return b;
                } else {
                    return (ByteString) ref;
                }
            }

            /**
             * <code>optional string serviceVersion = 2;</code>
             * <p>
             * <pre>
             * 接口版本
             * </pre>
             */
            public Builder setServiceVersion(
                    String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                bitField0_ |= 0x00000002;
                serviceVersion_ = value;
                onChanged();
                return this;
            }

            /**
             * <code>optional string serviceVersion = 2;</code>
             * <p>
             * <pre>
             * 接口版本
             * </pre>
             */
            public Builder clearServiceVersion() {
                bitField0_ = (bitField0_ & ~0x00000002);
                serviceVersion_ = getDefaultInstance().getServiceVersion();
                onChanged();
                return this;
            }

            /**
             * <code>optional string serviceVersion = 2;</code>
             * <p>
             * <pre>
             * 接口版本
             * </pre>
             */
            public Builder setServiceVersionBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                bitField0_ |= 0x00000002;
                serviceVersion_ = value;
                onChanged();
                return this;
            }

            // required string marking = 3;
            private Object marking_ = "";

            /**
             * <code>required string marking = 3;</code>
             * <p>
             * <pre>
             * 请求唯一标示rpcId
             * </pre>
             */
            public boolean hasMarking() {
                return ((bitField0_ & 0x00000004) == 0x00000004);
            }

            /**
             * <code>required string marking = 3;</code>
             * <p>
             * <pre>
             * 请求唯一标示rpcId
             * </pre>
             */
            public String getMarking() {
                Object ref = marking_;
                if (!(ref instanceof String)) {
                    String s = ((ByteString) ref).toStringUtf8();
                    marking_ = s;
                    return s;
                } else {
                    return (String) ref;
                }
            }

            /**
             * <code>required string marking = 3;</code>
             * <p>
             * <pre>
             * 请求唯一标示rpcId
             * </pre>
             */
            public ByteString getMarkingBytes() {
                Object ref = marking_;
                if (ref instanceof String) {
                    ByteString b = ByteString.copyFromUtf8((String) ref);
                    marking_ = b;
                    return b;
                } else {
                    return (ByteString) ref;
                }
            }

            /**
             * <code>required string marking = 3;</code>
             * <p>
             * <pre>
             * 请求唯一标示rpcId
             * </pre>
             */
            public Builder setMarking(
                    String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                bitField0_ |= 0x00000004;
                marking_ = value;
                onChanged();
                return this;
            }

            /**
             * <code>required string marking = 3;</code>
             * <p>
             * <pre>
             * 请求唯一标示rpcId
             * </pre>
             */
            public Builder clearMarking() {
                bitField0_ = (bitField0_ & ~0x00000004);
                marking_ = getDefaultInstance().getMarking();
                onChanged();
                return this;
            }

            /**
             * <code>required string marking = 3;</code>
             * <p>
             * <pre>
             * 请求唯一标示rpcId
             * </pre>
             */
            public Builder setMarkingBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                bitField0_ |= 0x00000004;
                marking_ = value;
                onChanged();
                return this;
            }

            // required int32 charset = 4;
            private int charset_;

            /**
             * <code>required int32 charset = 4;</code>
             * <p>
             * <pre>
             * 编码格式
             * </pre>
             */
            public boolean hasCharset() {
                return ((bitField0_ & 0x00000008) == 0x00000008);
            }

            /**
             * <code>required int32 charset = 4;</code>
             * <p>
             * <pre>
             * 编码格式
             * </pre>
             */
            public int getCharset() {
                return charset_;
            }

            /**
             * <code>required int32 charset = 4;</code>
             * <p>
             * <pre>
             * 编码格式
             * </pre>
             */
            public Builder setCharset(int value) {
                bitField0_ |= 0x00000008;
                charset_ = value;
                onChanged();
                return this;
            }

            /**
             * <code>required int32 charset = 4;</code>
             * <p>
             * <pre>
             * 编码格式
             * </pre>
             */
            public Builder clearCharset() {
                bitField0_ = (bitField0_ & ~0x00000008);
                charset_ = 0;
                onChanged();
                return this;
            }

            // required int32 compress = 5;
            private int compress_;

            /**
             * <code>required int32 compress = 5;</code>
             * <p>
             * <pre>
             * 压缩
             * </pre>
             */
            public boolean hasCompress() {
                return ((bitField0_ & 0x00000010) == 0x00000010);
            }

            /**
             * <code>required int32 compress = 5;</code>
             * <p>
             * <pre>
             * 压缩
             * </pre>
             */
            public int getCompress() {
                return compress_;
            }

            /**
             * <code>required int32 compress = 5;</code>
             * <p>
             * <pre>
             * 压缩
             * </pre>
             */
            public Builder setCompress(int value) {
                bitField0_ |= 0x00000010;
                compress_ = value;
                onChanged();
                return this;
            }

            /**
             * <code>required int32 compress = 5;</code>
             * <p>
             * <pre>
             * 压缩
             * </pre>
             */
            public Builder clearCompress() {
                bitField0_ = (bitField0_ & ~0x00000010);
                compress_ = 0;
                onChanged();
                return this;
            }

            // required int32 encrypt = 6;
            private int encrypt_;

            /**
             * <code>required int32 encrypt = 6;</code>
             * <p>
             * <pre>
             * 加密
             * </pre>
             */
            public boolean hasEncrypt() {
                return ((bitField0_ & 0x00000020) == 0x00000020);
            }

            /**
             * <code>required int32 encrypt = 6;</code>
             * <p>
             * <pre>
             * 加密
             * </pre>
             */
            public int getEncrypt() {
                return encrypt_;
            }

            /**
             * <code>required int32 encrypt = 6;</code>
             * <p>
             * <pre>
             * 加密
             * </pre>
             */
            public Builder setEncrypt(int value) {
                bitField0_ |= 0x00000020;
                encrypt_ = value;
                onChanged();
                return this;
            }

            /**
             * <code>required int32 encrypt = 6;</code>
             * <p>
             * <pre>
             * 加密
             * </pre>
             */
            public Builder clearEncrypt() {
                bitField0_ = (bitField0_ & ~0x00000020);
                encrypt_ = 0;
                onChanged();
                return this;
            }

            // required int32 protocol = 7;
            private int protocol_;

            /**
             * <code>required int32 protocol = 7;</code>
             * <p>
             * <pre>
             * 协议
             * </pre>
             */
            public boolean hasProtocol() {
                return ((bitField0_ & 0x00000040) == 0x00000040);
            }

            /**
             * <code>required int32 protocol = 7;</code>
             * <p>
             * <pre>
             * 协议
             * </pre>
             */
            public int getProtocol() {
                return protocol_;
            }

            /**
             * <code>required int32 protocol = 7;</code>
             * <p>
             * <pre>
             * 协议
             * </pre>
             */
            public Builder setProtocol(int value) {
                bitField0_ |= 0x00000040;
                protocol_ = value;
                onChanged();
                return this;
            }

            /**
             * <code>required int32 protocol = 7;</code>
             * <p>
             * <pre>
             * 协议
             * </pre>
             */
            public Builder clearProtocol() {
                bitField0_ = (bitField0_ & ~0x00000040);
                protocol_ = 0;
                onChanged();
                return this;
            }

            // required int32 socketVersion = 8;
            private int socketVersion_;

            /**
             * <code>required int32 socketVersion = 8;</code>
             * <p>
             * <pre>
             * 请求协议版本
             * </pre>
             */
            public boolean hasSocketVersion() {
                return ((bitField0_ & 0x00000080) == 0x00000080);
            }

            /**
             * <code>required int32 socketVersion = 8;</code>
             * <p>
             * <pre>
             * 请求协议版本
             * </pre>
             */
            public int getSocketVersion() {
                return socketVersion_;
            }

            /**
             * <code>required int32 socketVersion = 8;</code>
             * <p>
             * <pre>
             * 请求协议版本
             * </pre>
             */
            public Builder setSocketVersion(int value) {
                bitField0_ |= 0x00000080;
                socketVersion_ = value;
                onChanged();
                return this;
            }

            /**
             * <code>required int32 socketVersion = 8;</code>
             * <p>
             * <pre>
             * 请求协议版本
             * </pre>
             */
            public Builder clearSocketVersion() {
                bitField0_ = (bitField0_ & ~0x00000080);
                socketVersion_ = 0;
                onChanged();
                return this;
            }

            // optional bytes request = 10;
            private ByteString request_ = ByteString.EMPTY;

            /**
             * <code>optional bytes request = 10;</code>
             * <p>
             * <pre>
             * 请求数据
             * </pre>
             */
            public boolean hasRequest() {
                return ((bitField0_ & 0x00000100) == 0x00000100);
            }

            /**
             * <code>optional bytes request = 10;</code>
             * <p>
             * <pre>
             * 请求数据
             * </pre>
             */
            public ByteString getRequest() {
                return request_;
            }

            /**
             * <code>optional bytes request = 10;</code>
             * <p>
             * <pre>
             * 请求数据
             * </pre>
             */
            public Builder setRequest(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                bitField0_ |= 0x00000100;
                request_ = value;
                onChanged();
                return this;
            }

            /**
             * <code>optional bytes request = 10;</code>
             * <p>
             * <pre>
             * 请求数据
             * </pre>
             */
            public Builder clearRequest() {
                bitField0_ = (bitField0_ & ~0x00000100);
                request_ = getDefaultInstance().getRequest();
                onChanged();
                return this;
            }

            // optional string traceId = 11;
            private Object traceId_ = "";

            /**
             * <code>optional string traceId = 11;</code>
             * <p>
             * <pre>
             * 追踪id
             * </pre>
             */
            public boolean hasTraceId() {
                return ((bitField0_ & 0x00000200) == 0x00000200);
            }

            /**
             * <code>optional string traceId = 11;</code>
             * <p>
             * <pre>
             * 追踪id
             * </pre>
             */
            public String getTraceId() {
                Object ref = traceId_;
                if (!(ref instanceof String)) {
                    String s = ((ByteString) ref).toStringUtf8();
                    traceId_ = s;
                    return s;
                } else {
                    return (String) ref;
                }
            }

            /**
             * <code>optional string traceId = 11;</code>
             * <p>
             * <pre>
             * 追踪id
             * </pre>
             */
            public ByteString getTraceIdBytes() {
                Object ref = traceId_;
                if (ref instanceof String) {
                    ByteString b = ByteString.copyFromUtf8((String) ref);
                    traceId_ = b;
                    return b;
                } else {
                    return (ByteString) ref;
                }
            }

            /**
             * <code>optional string traceId = 11;</code>
             * <p>
             * <pre>
             * 追踪id
             * </pre>
             */
            public Builder setTraceId(
                    String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                bitField0_ |= 0x00000200;
                traceId_ = value;
                onChanged();
                return this;
            }

            /**
             * <code>optional string traceId = 11;</code>
             * <p>
             * <pre>
             * 追踪id
             * </pre>
             */
            public Builder clearTraceId() {
                bitField0_ = (bitField0_ & ~0x00000200);
                traceId_ = getDefaultInstance().getTraceId();
                onChanged();
                return this;
            }

            /**
             * <code>optional string traceId = 11;</code>
             * <p>
             * <pre>
             * 追踪id
             * </pre>
             */
            public Builder setTraceIdBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                bitField0_ |= 0x00000200;
                traceId_ = value;
                onChanged();
                return this;
            }

            // optional string span = 12;
            private Object span_ = "";

            /**
             * <code>optional string span = 12;</code>
             * <p>
             * <pre>
             * 追踪路径
             * </pre>
             */
            public boolean hasSpan() {
                return ((bitField0_ & 0x00000400) == 0x00000400);
            }

            /**
             * <code>optional string span = 12;</code>
             * <p>
             * <pre>
             * 追踪路径
             * </pre>
             */
            public String getSpan() {
                Object ref = span_;
                if (!(ref instanceof String)) {
                    String s = ((ByteString) ref).toStringUtf8();
                    span_ = s;
                    return s;
                } else {
                    return (String) ref;
                }
            }

            /**
             * <code>optional string span = 12;</code>
             * <p>
             * <pre>
             * 追踪路径
             * </pre>
             */
            public ByteString getSpanBytes() {
                Object ref = span_;
                if (ref instanceof String) {
                    ByteString b = ByteString.copyFromUtf8((String) ref);
                    span_ = b;
                    return b;
                } else {
                    return (ByteString) ref;
                }
            }

            /**
             * <code>optional string span = 12;</code>
             * <p>
             * <pre>
             * 追踪路径
             * </pre>
             */
            public Builder setSpan(
                    String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                bitField0_ |= 0x00000400;
                span_ = value;
                onChanged();
                return this;
            }

            /**
             * <code>optional string span = 12;</code>
             * <p>
             * <pre>
             * 追踪路径
             * </pre>
             */
            public Builder clearSpan() {
                bitField0_ = (bitField0_ & ~0x00000400);
                span_ = getDefaultInstance().getSpan();
                onChanged();
                return this;
            }

            /**
             * <code>optional string span = 12;</code>
             * <p>
             * <pre>
             * 追踪路径
             * </pre>
             */
            public Builder setSpanBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                bitField0_ |= 0x00000400;
                span_ = value;
                onChanged();
                return this;
            }
        }

        static {
            defaultInstance = new Request(true);
            defaultInstance.initFields();
        }
    }

    private static Descriptors.Descriptor internal_static_socket_message_Request_descriptor;
    private static GeneratedMessage.FieldAccessorTable internal_static_socket_message_Request_fieldAccessorTable;

    public static Descriptors.FileDescriptor getDescriptor() {
        return descriptor;
    }

    private static Descriptors.FileDescriptor descriptor;

    static {
        String[] descriptorData = {
                "\n\023SocketRequest.proto\022\037com.servitization.commons." +
                        "socket.message\"\324\001\n\007Request\022\023\n\013serviceNam" +
                        "e\030\001 \002(\t\022\026\n\016serviceVersion\030\002 \001(\t\022\017\n\007marki" +
                        "ng\030\003 \002(\t\022\017\n\007charset\030\004 \002(\005\022\020\n\010compress\030\005 " +
                        "\002(\005\022\017\n\007encrypt\030\006 \002(\005\022\020\n\010protocol\030\007 \002(\005\022\025" +
                        "\n\rsocketVersion\030\010 \002(\005\022\017\n\007request\030\n \001(\014\022\017" +
                        "\n\007traceId\030\013 \001(\t\022\014\n\004span\030\014 \001(\tB2\n\037com.elo" +
                        "ng.mobile.socket.messageB\rSocketRequestH" +
                        "\001"
        };
        Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
                new Descriptors.FileDescriptor.InternalDescriptorAssigner() {
                    public ExtensionRegistry assignDescriptors(Descriptors.FileDescriptor root) {
                        descriptor = root;
                        internal_static_socket_message_Request_descriptor =
                                getDescriptor().getMessageTypes().get(0);
                        internal_static_socket_message_Request_fieldAccessorTable = new
                                GeneratedMessage.FieldAccessorTable(
                                internal_static_socket_message_Request_descriptor,
                                new String[]{"ServiceName", "ServiceVersion", "Marking", "Charset", "Compress", "Encrypt", "Protocol", "SocketVersion", "Request", "TraceId", "Span",});
                        return null;
                    }
                };
        Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData,
                new Descriptors.FileDescriptor[]{}, assigner);
    }
    // @@protoc_insertion_point(outer_class_scope)
}
