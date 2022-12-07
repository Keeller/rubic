package ru.keellere.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cube {
    private Position position;
    private Map<Navigation,Color> colors;

    public List<Navigation> getImmutableNavigation(){
        return colors.entrySet().stream()
                .filter(c->Color.BLACK.equals(c.getValue()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
