package cn.xt.base.auth.service;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.CodecSupport;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.util.ByteSource;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;

/**
 * 解决：
 *
 *  采用redis缓存shiro的认证信息，并且要对这些信息进行序列化后再存储，但是序列化的时候，
 *  SimpleByteSource类没有实现Serializable接口，导致序列化失败
 *  SimpleByteSource没有默认构造方法，导致反序列化的时候失败
 *
 *  导致：
 *  shiro 使用缓存时出现：java.io.NotSerializableException: org.apache.shiro.util.SimpleByteSource
 *
 *  http://www.jianshu.com/p/62fa85791bf3
 */
public class SerializableSimpleByteSource implements ByteSource, Serializable {

    private  byte[] bytes;
    private String cachedHex;
    private String cachedBase64;

    public SerializableSimpleByteSource(){
    }

    public SerializableSimpleByteSource(byte[] bytes) {
        this.bytes = bytes;
    }

    public SerializableSimpleByteSource(char[] chars) {
        this.bytes = CodecSupport.toBytes(chars);
    }

    public SerializableSimpleByteSource(String string) {
        this.bytes = CodecSupport.toBytes(string);
    }

    public SerializableSimpleByteSource(ByteSource source) {
        this.bytes = source.getBytes();
    }

    public SerializableSimpleByteSource(File file) {
        this.bytes = (new SerializableSimpleByteSource.BytesHelper()).getBytes(file);
    }

    public SerializableSimpleByteSource(InputStream stream) {
        this.bytes = (new SerializableSimpleByteSource.BytesHelper()).getBytes(stream);
    }

    @Override
    public byte[] getBytes() {
        return this.bytes;
    }

    @Override
    public String toHex() {
        if(this.cachedHex == null) {
            this.cachedHex = Hex.encodeToString(this.getBytes());
        }
        return this.cachedHex;
    }

    @Override
    public String toBase64() {
        if(this.cachedBase64 == null) {
            this.cachedBase64 = Base64.encodeToString(this.getBytes());
        }
        return this.cachedBase64;
    }

    @Override
    public boolean isEmpty() {
        return this.bytes == null || this.bytes.length == 0;
    }

    private static final class BytesHelper extends CodecSupport {
        private BytesHelper() {
        }

        public byte[] getBytes(File file) {
            return this.toBytes(file);
        }

        public byte[] getBytes(InputStream stream) {
            return this.toBytes(stream);
        }
    }
}
