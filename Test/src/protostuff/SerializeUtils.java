package protostuff;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

/**
* @author tangjifu
* @date 2017年3月3日 下午6:02:34
*/
public class SerializeUtils {
	private static ThreadLocal<LinkedBuffer> tlBuffer = new ThreadLocal<LinkedBuffer>();

	public static <T> byte[] serialize(T t, Class<T> clazz) {
		return ProtobufIOUtil.toByteArray(t, RuntimeSchema.createFrom(clazz),
				LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
	}

	public static <T> T deSerialize(byte[] data, Class<T> clazz) {
		RuntimeSchema<T> runtimeSchema = RuntimeSchema.createFrom(clazz);
		T t = runtimeSchema.newMessage();
		ProtobufIOUtil.mergeFrom(data, t, runtimeSchema);
		return t;
	}
	
	public static byte[] encodeByProtostuff(Object obj) {
		LinkedBuffer buffer = tlBuffer.get();
		if (buffer == null) {
			buffer = LinkedBuffer.allocate(256);
			tlBuffer.set(buffer);
		}
		@SuppressWarnings("unchecked")
		Schema<Object> schema = (Schema<Object>) RuntimeSchema.getSchema(obj.getClass());
		byte[] data = ProtobufIOUtil.toByteArray(obj, schema, buffer);
		buffer.clear();
		return data;
	}

	public static <Obj> Obj decodeByProtostuff(Class<Obj> cls, byte[] data) {
		Schema<Obj> schema = RuntimeSchema.getSchema(cls);
		try {
			Obj obj = cls.newInstance();
			ProtobufIOUtil.mergeFrom(data, obj, schema);
			return obj;
		} catch (InstantiationException e) {
			System.err.println("decodeByProtostuff error:"+e);
		} catch (IllegalAccessException e) {
			System.err.println("decodeByProtostuff error:"+e);
		}
		return null;
	}
}
