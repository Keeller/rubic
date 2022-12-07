package ru.keellere.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Random;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Position {
    private int x;
    private int y;
    private int z;

    public static Position random(){
        var pos=new Position(1,1,1);
        var rnd=new Random();
        Position result=null;
        while (result==null||result.equals(pos)){
            result=new Position(rnd.nextInt(0,1),rnd.nextInt(0,1),rnd.nextInt(0,1));
        }
        return result;
    }

    public static Position randomDirection(){
        var rnd=new Random();
        var directions=new ArrayList<Position>(){{
            add(new Position(0, 0, 1));
            add(new Position(1, 0, 0));
            add(new Position(0, 1, 0));
        }};
        return directions.get(rnd.nextInt(directions.size()));
    }
}
