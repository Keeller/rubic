package ru.keellere.service;

import org.springframework.stereotype.Component;
import ru.keellere.pojo.Cube;
import ru.keellere.pojo.Position;
import ru.keellere.pojo.Space;

import java.util.Map;
import java.util.Set;

@Component
public class HtmlVisualizer {

    public String getHtml(Map<Position, Cube> state, Map<Space, Set<Position>> spacePositionMap){
        String resultedHtml="";

        for (Map.Entry<Space, Set<Position>> spaceSetEntry : spacePositionMap.entrySet()) {
            String table="<table>";
            int i=3;
            for (Position position : spaceSetEntry.getValue()) {
            }
        }

        return null;

    }
}
