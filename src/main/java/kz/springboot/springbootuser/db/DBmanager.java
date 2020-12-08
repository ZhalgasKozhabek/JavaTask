package kz.springboot.springbootuser.db;

import java.util.ArrayList;

public class DBmanager {

    private static ArrayList<Items> items = new ArrayList<>();
    private static Long id = 6L;
    private static String d = "Lorem ipsum llalala" +"\n";

    private static String Url = "https://i.picsum.photos/id/0/5616/3744.jpg?hmac=3GAAioiQziMGEtLbfrdbcoenXoWAW-zlyEAMkfEdBzQ";
    static {
        items.add(new Items(1L, "Mack book", d, "Laptop", 7000000, 7, 4, Url));
        items.add(new Items(1L, "NOUT", d,"Laptop",120,12 ,1,Url));
        items.add(new Items(2L, "MAC OS", d,"Laptop",130,150 ,14,Url));
        items.add(new Items(3L, "DEF", d,"Laptop",900,16 ,1,Url));
        items.add(new Items(4L, "Iphone", d,"Phone",877,13 ,1,Url));
        items.add(new Items(5L, "Samsung", d,"Phone",677,100 ,1,Url));
    }
    public static Items getItem(Long id){
        for(Items item: items){
            if(item.getId().equals(id)){
                return item;
            }
        }
        return null;
    }

    public static ArrayList<Items> getAllItems(){
        for(Items item: items){
            ArrayList<Boolean> startArr = new ArrayList();
            for(int i = 1; i < 6; i++) {
                if (item.getStars() >= i)
                    startArr.add(true);
                else startArr.add(false);
            }

            item.setStartArr(startArr);
        }
        return items;
    }

    public static void addTask(Items item){
        item.setId(id);
        items.add(item);
        id++;
    }

}
