import redis.clients.jedis.Jedis;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        try(Jedis jedis = new Jedis("127.0.0.1",6379)) {

            //to clear all
            //Use the flushdb method to clear all keys from
            // the current Redis database, or flushall to clear all keys from all databases.
            // jedis.flushDB();

            jedis.set("name", "johny");
            System.out.println(jedis.get("name"));

            Boolean existance = jedis.exists("key");
            System.out.println("there "+ (existance ? "is":"isn't")+" key");

            jedis.del("name");
            System.out.println(jedis.get("name"));//it's be null

            //rpush and rrange is available
            jedis.lpush("theList","one","2","3","four");
            //is used to retrieve a subset of elements from a list stored in Redis.
            // This method is particularly useful for accessing elements within a specific range,
            // allowing you to work with a portion of the list without fetching the entire list.
            List<String> items = jedis.lrange("theList",0,-1);
            System.out.println("items get with lRange : " + items );

            jedis.hset("myhash", "field1", "value1"); // Set field in hash
            String fieldValue = jedis.hget("myhash", "field1"); // Get field value
            System.out.println("Field Value: " + fieldValue);

            jedis.setex("tempKey", 1, "tempValue"); // Set key with 10 seconds expiry
            System.out.println("Temporary key set");


            try{
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("tempKey after 3 seconds : "+jedis.get("tempKey"));
            jedis.close();
        }catch (Error error){
            System.out.println(error.getMessage());
        }
    }
}