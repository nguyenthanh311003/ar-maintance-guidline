package com.capstone.ar_guideline.util;

import java.util.List;
import java.util.Set;
import org.springframework.data.redis.core.RedisTemplate;

public class UtilService {

  public static boolean IsNotNull(Object obj) {
    return obj != null;
  }

  public static boolean IsNotNullOrBlank(String str) {
    return str != null && !str.isBlank();
  }

  public static <T> boolean IsNotNullOrEmptyForSet(Set<T> set) {
    return set != null && !set.isEmpty();
  }

  public static <T> boolean IsNotNullOrEmptyForList(List<T> list) {
    return list != null && !list.isEmpty();
  }

  public static int getTotalPage(int total, int size) {
    return (int) Math.ceil((double) total / size);
  }

  public static void deleteCache(RedisTemplate<String, Object> redisTemplate, Set<String> keys) {
    if (UtilService.IsNotNullOrEmptyForSet(keys)) {
      redisTemplate.delete(keys);
    }
  }
}
