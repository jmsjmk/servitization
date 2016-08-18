package com.servitization.commons.socket.message;

import com.google.protobuf.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectStreamException;

public final class SocketResponse {
    private SocketResponse() {
    }

    public static void registerAllExtensions(ExtensionRegistry registry) {
    }

    public interface ResponseOrBuilder extends MessageOrBuilder {

        // optional string serviceName = 1;

        /**
         * <code>optional string serviceName = 1;</code>
         * <p>
         * <pre>
         * 接口名称
         * </pre>
         */
        boolean hasServiceName();

        /**
         * <code>optional string serviceName = 1;</code>
         * <p>
         * <pre>
         * 接口名称
         * </pre>
         */
        String getServiceName();

        /**
         * <code>optional string serviceName = 1;</code>
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
         * 标记
         * </pre>
         */
        boolean hasMarking();

        /**
         * <code>required string marking = 3;</code>
         * <p>
         * <pre>
         * 标记
         * </pre>
         */
        String getMarking();

        /**
         * <code>required string marking = 3;</code>
         * <p>
         * <pre>
         * 标记
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

        /**
         * <p>
         * <pre>
         * 结果状态码
         * </pre>
         */
        boolean hasStatus();

        /**
         * <p>
         * <pre>
         * 结果状态码
         * </pre>
         */
        Response.Status getStatus();

        // optional bytes result = 10;

        /**
         * <code>optional bytes result = 10;</code>
         * <p>
         * <pre>
         * 结果数据
         * </pre>
         */
        boolean hasResult();

        /**
         * <code>optional bytes result = 10;</code>
         * <p>
         * <pre>
         * 结果数据
         * </pre>
         */
        ByteString getResult();
    }

    public static final class Response extends GeneratedMessage implements ResponseOrBuilder {
        // Use Response.newBuilder() to construct.
        private Response(GeneratedMessage.Builder<?> builder) {
            super(builder);
            this.unknownFields = builder.getUnknownFields();
        }

        private Response(boolean noInit) {
            this.unknownFields = UnknownFieldSet.getDefaultInstance();
        }

        private static final Response defaultInstance;

        public static Response getDefaultInstance() {
            return defaultInstance;
        }

        public Response getDefaultInstanceForType() {
            return defaultInstance;
        }

        private final UnknownFieldSet unknownFields;

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private Response(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
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
                        case 72: {
                            int rawValue = input.readEnum();
                            Response.Status value = Response.Status.valueOf(rawValue);
                            if (value == null) {
                                unknownFields.mergeVarintField(9, rawValue);
                            } else {
                                bitField0_ |= 0x00000100;
                                status_ = value;
                            }
                            break;
                        }
                        case 82: {
                            bitField0_ |= 0x00000200;
                            result_ = input.readBytes();
                            break;
                        }
                    }
                }
            } catch (InvalidProtocolBufferException e) {
                throw e.setUnfinishedMessage(this);
            } catch (IOException e) {
                throw new InvalidProtocolBufferException(
                        e.getMessage()).setUnfinishedMessage(this);
            } finally {
                this.unknownFields = unknownFields.build();
                makeExtensionsImmutable();
            }
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_socket_message_Response_descriptor;
        }

        protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_socket_message_Response_fieldAccessorTable
                    .ensureFieldAccessorsInitialized(
                            Response.class, Response.Builder.class);
        }

        public static Parser<Response> PARSER = new com.google.protobuf.AbstractParser<Response>() {
            public Response parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
                    throws InvalidProtocolBufferException {
                return new Response(input, extensionRegistry);
            }
        };

        @Override
        public Parser<Response> getParserForType() {
            return PARSER;
        }

        public enum Status implements ProtocolMessageEnum {
            /**
             * <code>SUCCESS = 0;</code>
             * <p>
             * <pre>
             * 成功
             * </pre>
             */
            SUCCESS(0, 0),
            /**
             * <code>FAIL = 1;</code>
             * <p>
             * <pre>
             * 失败
             * </pre>
             */
            FAIL(1, 1),
            /**
             * <code>REQUEST_ADAPTER_ERROR = 2;</code>
             * <p>
             * <pre>
             * 请求适配器错误
             * </pre>
             */
            REQUEST_ADAPTER_ERROR(2, 2),
            /**
             * <code>RESULT_ADAPTER_ERROR = 3;</code>
             * <p>
             * <pre>
             * 返回结果适配器错误
             * </pre>
             */
            RESULT_ADAPTER_ERROR(3, 3),
            /**
             * <code>NO_SERVICE = 4;</code>
             * <p>
             * <pre>
             * 没有找到服务
             * </pre>
             */
            NO_SERVICE(4, 4),
            /**
             * <code>RESULT_CONVERT_ERROR = 5;</code>
             * <p>
             * <pre>
             * 结果信息转换异常
             * </pre>
             */
            RESULT_CONVERT_ERROR(5, 5),
            /**
             * <code>REQUEST_DECOMPRESS_ERROR = 6;</code>
             * <p>
             * <pre>
             * 请求信息解压错误
             * </pre>
             */
            REQUEST_DECOMPRESS_ERROR(6, 6),
            /**
             * <code>REQUEST_DECRYPT_ERROR = 7;</code>
             * <p>
             * <pre>
             * 请求信息解密错误
             * </pre>
             */
            REQUEST_DECRYPT_ERROR(7, 7),
            /**
             * <code>REQUEST_CHARSET_ERROR = 8;</code>
             * <p>
             * <pre>
             * 请求信息编码错误
             * </pre>
             */
            REQUEST_CHARSET_ERROR(8, 8),
            /**
             * <code>RESULT_CHARSET_ERROR = 9;</code>
             * <p>
             * <pre>
             * 结果信息编码错误
             * </pre>
             */
            RESULT_CHARSET_ERROR(9, 9),
            /**
             * <code>RESULT_ENCRYPT_ERROR = 10;</code>
             * <p>
             * <pre>
             * 结果信息加密错误
             * </pre>
             */
            RESULT_ENCRYPT_ERROR(10, 10),
            /**
             * <code>RESULT_COMPRESS_ERROR = 11;</code>
             * <p>
             * <pre>
             * 结果信息压缩错误
             * </pre>
             */
            RESULT_COMPRESS_ERROR(11, 11),
            /**
             * <code>RUN_SERVICE_ERROR = 12;</code>
             * <p>
             * <pre>
             * 运行服务错误
             * </pre>
             */
            RUN_SERVICE_ERROR(12, 12),
            /**
             * <code>OTHER_ERROR = 13;</code>
             * <p>
             * <pre>
             * 其他错误
             * </pre>
             */
            OTHER_ERROR(13, 13),;

            /**
             * <code>SUCCESS = 0;</code>
             * <p>
             * <pre>
             * 成功
             * </pre>
             */
            public static final int SUCCESS_VALUE = 0;
            /**
             * <code>FAIL = 1;</code>
             * <p>
             * <pre>
             * 失败
             * </pre>
             */
            public static final int FAIL_VALUE = 1;
            /**
             * <code>REQUEST_ADAPTER_ERROR = 2;</code>
             * <p>
             * <pre>
             * 请求适配器错误
             * </pre>
             */
            public static final int REQUEST_ADAPTER_ERROR_VALUE = 2;
            /**
             * <code>RESULT_ADAPTER_ERROR = 3;</code>
             * <p>
             * <pre>
             * 返回结果适配器错误
             * </pre>
             */
            public static final int RESULT_ADAPTER_ERROR_VALUE = 3;
            /**
             * <code>NO_SERVICE = 4;</code>
             * <p>
             * <pre>
             * 没有找到服务
             * </pre>
             */
            public static final int NO_SERVICE_VALUE = 4;
            /**
             * <code>RESULT_CONVERT_ERROR = 5;</code>
             * <p>
             * <pre>
             * 结果信息转换异常
             * </pre>
             */
            public static final int RESULT_CONVERT_ERROR_VALUE = 5;
            /**
             * <code>REQUEST_DECOMPRESS_ERROR = 6;</code>
             * <p>
             * <pre>
             * 请求信息解压错误
             * </pre>
             */
            public static final int REQUEST_DECOMPRESS_ERROR_VALUE = 6;
            /**
             * <code>REQUEST_DECRYPT_ERROR = 7;</code>
             * <p>
             * <pre>
             * 请求信息解密错误
             * </pre>
             */
            public static final int REQUEST_DECRYPT_ERROR_VALUE = 7;
            /**
             * <code>REQUEST_CHARSET_ERROR = 8;</code>
             * <p>
             * <pre>
             * 请求信息编码错误
             * </pre>
             */
            public static final int REQUEST_CHARSET_ERROR_VALUE = 8;
            /**
             * <code>RESULT_CHARSET_ERROR = 9;</code>
             * <p>
             * <pre>
             * 结果信息编码错误
             * </pre>
             */
            public static final int RESULT_CHARSET_ERROR_VALUE = 9;
            /**
             * <code>RESULT_ENCRYPT_ERROR = 10;</code>
             * <p>
             * <pre>
             * 结果信息加密错误
             * </pre>
             */
            public static final int RESULT_ENCRYPT_ERROR_VALUE = 10;
            /**
             * <code>RESULT_COMPRESS_ERROR = 11;</code>
             * <p>
             * <pre>
             * 结果信息压缩错误
             * </pre>
             */
            public static final int RESULT_COMPRESS_ERROR_VALUE = 11;
            /**
             * <code>RUN_SERVICE_ERROR = 12;</code>
             * <p>
             * <pre>
             * 运行服务错误
             * </pre>
             */
            public static final int RUN_SERVICE_ERROR_VALUE = 12;
            /**
             * <code>OTHER_ERROR = 13;</code>
             * <p>
             * <pre>
             * 其他错误
             * </pre>
             */
            public static final int OTHER_ERROR_VALUE = 13;

            public final int getNumber() {
                return value;
            }

            public static Status valueOf(int value) {
                switch (value) {
                    case 0:
                        return SUCCESS;
                    case 1:
                        return FAIL;
                    case 2:
                        return REQUEST_ADAPTER_ERROR;
                    case 3:
                        return RESULT_ADAPTER_ERROR;
                    case 4:
                        return NO_SERVICE;
                    case 5:
                        return RESULT_CONVERT_ERROR;
                    case 6:
                        return REQUEST_DECOMPRESS_ERROR;
                    case 7:
                        return REQUEST_DECRYPT_ERROR;
                    case 8:
                        return REQUEST_CHARSET_ERROR;
                    case 9:
                        return RESULT_CHARSET_ERROR;
                    case 10:
                        return RESULT_ENCRYPT_ERROR;
                    case 11:
                        return RESULT_COMPRESS_ERROR;
                    case 12:
                        return RUN_SERVICE_ERROR;
                    case 13:
                        return OTHER_ERROR;
                    default:
                        return null;
                }
            }

            public static Internal.EnumLiteMap<Status> internalGetValueMap() {
                return internalValueMap;
            }

            private static Internal.EnumLiteMap<Status> internalValueMap =
                    new Internal.EnumLiteMap<Status>() {
                        public Status findValueByNumber(int number) {
                            return Status.valueOf(number);
                        }
                    };

            public final Descriptors.EnumValueDescriptor getValueDescriptor() {
                return getDescriptor().getValues().get(index);
            }

            public final Descriptors.EnumDescriptor getDescriptorForType() {
                return getDescriptor();
            }

            public static final Descriptors.EnumDescriptor getDescriptor() {
                return Response.getDescriptor().getEnumTypes().get(0);
            }

            private static final Status[] VALUES = values();

            public static Status valueOf(Descriptors.EnumValueDescriptor desc) {
                if (desc.getType() != getDescriptor()) {
                    throw new IllegalArgumentException(
                            "EnumValueDescriptor is not for this type.");
                }
                return VALUES[desc.getIndex()];
            }

            private final int index;
            private final int value;

            Status(int index, int value) {
                this.index = index;
                this.value = value;
            }
        }

        private int bitField0_;
        // optional string serviceName = 1;
        public static final int SERVICENAME_FIELD_NUMBER = 1;
        private Object serviceName_;

        /**
         * <code>optional string serviceName = 1;</code>
         * <p>
         * <pre>
         * 接口名称
         * </pre>
         */
        public boolean hasServiceName() {
            return ((bitField0_ & 0x00000001) == 0x00000001);
        }

        /**
         * <code>optional string serviceName = 1;</code>
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
         * <code>optional string serviceName = 1;</code>
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

        // required string marking = 3;
        public static final int MARKING_FIELD_NUMBER = 3;
        private Object marking_;

        /**
         * <code>required string marking = 3;</code>
         * <p>
         * <pre>
         * 标记
         * </pre>
         */
        public boolean hasMarking() {
            return ((bitField0_ & 0x00000004) == 0x00000004);
        }

        /**
         * <code>required string marking = 3;</code>
         * <p>
         * <pre>
         * 标记
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
         * 标记
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

        public static final int STATUS_FIELD_NUMBER = 9;
        private Response.Status status_;

        /**
         * <p>
         * <pre>
         * 结果状态码
         * </pre>
         */
        public boolean hasStatus() {
            return ((bitField0_ & 0x00000100) == 0x00000100);
        }

        /**
         * <p>
         * <pre>
         * 结果状态码
         * </pre>
         */
        public Response.Status getStatus() {
            return status_;
        }

        // optional bytes result = 10;
        public static final int RESULT_FIELD_NUMBER = 10;
        private ByteString result_;

        /**
         * <code>optional bytes result = 10;</code>
         * <p>
         * <pre>
         * 结果数据
         * </pre>
         */
        public boolean hasResult() {
            return ((bitField0_ & 0x00000200) == 0x00000200);
        }

        /**
         * <code>optional bytes result = 10;</code>
         * <p>
         * <pre>
         * 结果数据
         * </pre>
         */
        public ByteString getResult() {
            return result_;
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
            status_ = Response.Status.SUCCESS;
            result_ = ByteString.EMPTY;
        }

        private byte memoizedIsInitialized = -1;

        public final boolean isInitialized() {
            byte isInitialized = memoizedIsInitialized;
            if (isInitialized != -1) return isInitialized == 1;

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
            if (!hasStatus()) {
                memoizedIsInitialized = 0;
                return false;
            }
            memoizedIsInitialized = 1;
            return true;
        }

        public void writeTo(CodedOutputStream output) throws IOException {
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
                output.writeEnum(9, status_.getNumber());
            }
            if (((bitField0_ & 0x00000200) == 0x00000200)) {
                output.writeBytes(10, result_);
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
                size += CodedOutputStream.computeEnumSize(9, status_.getNumber());
            }
            if (((bitField0_ & 0x00000200) == 0x00000200)) {
                size += CodedOutputStream.computeBytesSize(10, result_);
            }
            size += getUnknownFields().getSerializedSize();
            memoizedSerializedSize = size;
            return size;
        }

        private static final long serialVersionUID = 0L;

        @Override
        protected Object writeReplace() throws ObjectStreamException {
            return super.writeReplace();
        }

        public static Response parseFrom(ByteString data)
                throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Response parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry)
                throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Response parseFrom(byte[] data)
                throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Response parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry)
                throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Response parseFrom(InputStream input)
                throws IOException {
            return PARSER.parseFrom(input);
        }

        public static Response parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry)
                throws IOException {
            return PARSER.parseFrom(input, extensionRegistry);
        }

        public static Response parseDelimitedFrom(InputStream input)
                throws IOException {
            return PARSER.parseDelimitedFrom(input);
        }

        public static Response parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry)
                throws IOException {
            return PARSER.parseDelimitedFrom(input, extensionRegistry);
        }

        public static Response parseFrom(CodedInputStream input)
                throws IOException {
            return PARSER.parseFrom(input);
        }

        public static Response parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
                throws IOException {
            return PARSER.parseFrom(input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder(Response prototype) {
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
                implements ResponseOrBuilder {
            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_socket_message_Response_descriptor;
            }

            protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_socket_message_Response_fieldAccessorTable
                        .ensureFieldAccessorsInitialized(
                                Response.class, Response.Builder.class);
            }

            // Construct using Response.newBuilder()
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
                status_ = Response.Status.SUCCESS;
                bitField0_ = (bitField0_ & ~0x00000100);
                result_ = com.google.protobuf.ByteString.EMPTY;
                bitField0_ = (bitField0_ & ~0x00000200);
                return this;
            }

            public Builder clone() {
                return create().mergeFrom(buildPartial());
            }

            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_socket_message_Response_descriptor;
            }

            public Response getDefaultInstanceForType() {
                return Response.getDefaultInstance();
            }

            public Response build() {
                Response result = buildPartial();
                if (!result.isInitialized()) {
                    throw newUninitializedMessageException(result);
                }
                return result;
            }

            public Response buildPartial() {
                Response result = new Response(this);
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
                result.status_ = status_;
                if (((from_bitField0_ & 0x00000200) == 0x00000200)) {
                    to_bitField0_ |= 0x00000200;
                }
                result.result_ = result_;
                result.bitField0_ = to_bitField0_;
                onBuilt();
                return result;
            }

            public Builder mergeFrom(Message other) {
                if (other instanceof Response) {
                    return mergeFrom((Response) other);
                } else {
                    super.mergeFrom(other);
                    return this;
                }
            }

            public Builder mergeFrom(Response other) {
                if (other == Response.getDefaultInstance()) return this;
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
                if (other.hasStatus()) {
                    setStatus(other.getStatus());
                }
                if (other.hasResult()) {
                    setResult(other.getResult());
                }
                this.mergeUnknownFields(other.getUnknownFields());
                return this;
            }

            public final boolean isInitialized() {
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
                if (!hasStatus()) {

                    return false;
                }
                return true;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
                    throws IOException {
                Response parsedMessage = null;
                try {
                    parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
                } catch (InvalidProtocolBufferException e) {
                    parsedMessage = (Response) e.getUnfinishedMessage();
                    throw e;
                } finally {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                }
                return this;
            }

            private int bitField0_;

            // optional string serviceName = 1;
            private Object serviceName_ = "";

            /**
             * <code>optional string serviceName = 1;</code>
             * <p>
             * <pre>
             * 接口名称
             * </pre>
             */
            public boolean hasServiceName() {
                return ((bitField0_ & 0x00000001) == 0x00000001);
            }

            /**
             * <code>optional string serviceName = 1;</code>
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
             * <code>optional string serviceName = 1;</code>
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
             * <code>optional string serviceName = 1;</code>
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
             * <code>optional string serviceName = 1;</code>
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
             * <code>optional string serviceName = 1;</code>
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
             * 标记
             * </pre>
             */
            public boolean hasMarking() {
                return ((bitField0_ & 0x00000004) == 0x00000004);
            }

            /**
             * <code>required string marking = 3;</code>
             * <p>
             * <pre>
             * 标记
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
             * 标记
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
             * 标记
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
             * 标记
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
             * 标记
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

            private Response.Status status_ = Response.Status.SUCCESS;

            /**
             * <p>
             * <pre>
             * 结果状态码
             * </pre>
             */
            public boolean hasStatus() {
                return ((bitField0_ & 0x00000100) == 0x00000100);
            }

            /**
             * <p>
             * <pre>
             * 结果状态码
             * </pre>
             */
            public Response.Status getStatus() {
                return status_;
            }

            /**
             * <p>
             * <pre>
             * 结果状态码
             * </pre>
             */
            public Builder setStatus(Response.Status value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                bitField0_ |= 0x00000100;
                status_ = value;
                onChanged();
                return this;
            }

            /**
             * <p>
             * <pre>
             * 结果状态码
             * </pre>
             */
            public Builder clearStatus() {
                bitField0_ = (bitField0_ & ~0x00000100);
                status_ = Response.Status.SUCCESS;
                onChanged();
                return this;
            }

            // optional bytes result = 10;
            private ByteString result_ = ByteString.EMPTY;

            /**
             * <code>optional bytes result = 10;</code>
             * <p>
             * <pre>
             * 结果数据
             * </pre>
             */
            public boolean hasResult() {
                return ((bitField0_ & 0x00000200) == 0x00000200);
            }

            /**
             * <code>optional bytes result = 10;</code>
             * <p>
             * <pre>
             * 结果数据
             * </pre>
             */
            public ByteString getResult() {
                return result_;
            }

            /**
             * <code>optional bytes result = 10;</code>
             * <p>
             * <pre>
             * 结果数据
             * </pre>
             */
            public Builder setResult(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                bitField0_ |= 0x00000200;
                result_ = value;
                onChanged();
                return this;
            }

            /**
             * <code>optional bytes result = 10;</code>
             * <p>
             * <pre>
             * 结果数据
             * </pre>
             */
            public Builder clearResult() {
                bitField0_ = (bitField0_ & ~0x00000200);
                result_ = getDefaultInstance().getResult();
                onChanged();
                return this;
            }
        }

        static {
            defaultInstance = new Response(true);
            defaultInstance.initFields();
        }
    }

    private static Descriptors.Descriptor internal_static_socket_message_Response_descriptor;

    private static GeneratedMessage.FieldAccessorTable internal_static_socket_message_Response_fieldAccessorTable;

    public static Descriptors.FileDescriptor getDescriptor() {
        return descriptor;
    }

    private static Descriptors.FileDescriptor descriptor;

    static {
        String[] descriptorData = {
                "\n\024SocketResponse.proto\022\037com.servitization.commons" +
                        ".socket.message\"\303\004\n\010Response\022\023\n\013serviceN" +
                        "ame\030\001 \001(\t\022\026\n\016serviceVersion\030\002 \001(\t\022\017\n\007mar" +
                        "king\030\003 \002(\t\022\017\n\007charset\030\004 \002(\005\022\020\n\010compress\030" +
                        "\005 \002(\005\022\017\n\007encrypt\030\006 \002(\005\022\020\n\010protocol\030\007 \002(\005" +
                        "\022\025\n\rsocketVersion\030\010 \002(\005\022@\n\006status\030\t \002(\0162" +
                        "0.com.servitization.commons.socket.message.Respon" +
                        "se.Status\022\016\n\006result\030\n \001(\014\"\311\002\n\006Status\022\013\n\007" +
                        "SUCCESS\020\000\022\010\n\004FAIL\020\001\022\031\n\025REQUEST_ADAPTER_E" +
                        "RROR\020\002\022\030\n\024RESULT_ADAPTER_ERROR\020\003\022\016\n\nNO_S",
                "ERVICE\020\004\022\030\n\024RESULT_CONVERT_ERROR\020\005\022\034\n\030RE" +
                        "QUEST_DECOMPRESS_ERROR\020\006\022\031\n\025REQUEST_DECR" +
                        "YPT_ERROR\020\007\022\031\n\025REQUEST_CHARSET_ERROR\020\010\022\030" +
                        "\n\024RESULT_CHARSET_ERROR\020\t\022\030\n\024RESULT_ENCRY" +
                        "PT_ERROR\020\n\022\031\n\025RESULT_COMPRESS_ERROR\020\013\022\025\n" +
                        "\021RUN_SERVICE_ERROR\020\014\022\017\n\013OTHER_ERROR\020\rB3\n" +
                        "\037com.servitization.commons.socket.messageB\016Socket" +
                        "ResponseH\001"
        };
        Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
                new Descriptors.FileDescriptor.InternalDescriptorAssigner() {
                    public ExtensionRegistry assignDescriptors(Descriptors.FileDescriptor root) {
                        descriptor = root;
                        internal_static_socket_message_Response_descriptor =
                                getDescriptor().getMessageTypes().get(0);
                        internal_static_socket_message_Response_fieldAccessorTable = new
                                com.google.protobuf.GeneratedMessage.FieldAccessorTable(
                                internal_static_socket_message_Response_descriptor,
                                new String[]{"ServiceName", "ServiceVersion", "Marking", "Charset", "Compress", "Encrypt", "Protocol", "SocketVersion", "Status", "Result",});
                        return null;
                    }
                };
        Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData,
                new Descriptors.FileDescriptor[]{}, assigner);
    }
    // @@protoc_insertion_point(outer_class_scope)
}
