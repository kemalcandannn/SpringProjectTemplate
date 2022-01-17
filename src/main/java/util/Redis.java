package util;

import java.io.File;
import java.io.IOException;

import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

//application scope a konulan değerler buraya konulmalı
public class Redis {
	
	private static RedissonClient redisson = null;
	
	public static RedissonClient getInstance() {
		if(redisson == null)
			configure();
		return redisson;
	}

	private static void configure() {
		try {
			Config config = Config.fromJSON(new File("src/main/resources/redisson/redisson.json"));
			redisson = Redisson.create(config);
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	public static void add(String key, Object value) {
		RBucket<Object> bucket = getInstance().getBucket(key);
		bucket.set(value);
	}
	
	public static Object get(String key) {
		RBucket<Object> bucket = getInstance().getBucket(key);
		return bucket.get();
	}

	public static <K, V> RMap<K, V> createRMap(String key) {
		try {//BelgeBD'de main çalıştırınca hata vermemesi için yazıldı
			return getInstance().getMap(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
