package ru.keellere.service;

import lombok.Getter;
import org.springframework.stereotype.Component;
import ru.keellere.pojo.*;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
@Getter
public class Environment {

    private Map<Position, Cube> state = new HashMap<>();
    private Map<Space, Set<Position>> spacePositionMap = new HashMap<>();

    @PostConstruct
    private void init() {
        initFirstLayer();
        initSecondLayer();
        initThirdLayer();
        initSpaceMap();

    }

    public void shuffle(int shufflePower) {
        for (int i = 0; i < shufflePower; i++) {
            var ring=rotateRing(Position.random(),Space.randomSpace(),Position.randomDirection());
            updateState(ring);
        }
    }

    public void updateState(List<Cube> list){
        for (Cube cube : list) {
            state.put(cube.getPosition(),cube);
        }
    }

    public List<Cube> rotateRing(Position position, Space startSpace, Position direction) {
        var ring = getRing(position, startSpace, direction);
        var first=ring.get(0);
        for (int i = 0; i < ring.size()-1; i++) {
            var l=ring.get(i);
            var r=ring.get(i+1);
            l.setColors(r.getColors());
            ring.set(i,l);
        }
        var last=ring.get(ring.size()-1);
        last.setColors(first.getColors());
        ring.set(ring.size()-1,last);
        return ring;
    }

    public List<Cube> getRing(Position position, Space startSpace, Position direction) {
        var result = new ArrayList<Cube>();
        var currentPosition = position;
        var lastPosition = position;
        var currSpace = startSpace;
        var flag = true;
        result.add(state.get(currentPosition));
        while (flag) {
            currSpace = getNextSpace(currSpace, currentPosition, direction);
            var newPosition = getNextPosition(currentPosition, currSpace, direction);
            result.add(state.get(newPosition));

            if (lastPosition.equals(newPosition)) {
                flag = false;
            }

        }
        return result;
    }

    public Space getNextSpace(Space currState, Position currentPosition, Position direction) {
        if ((new Position(0, 0, 1)).equals(direction)) {
            if (Space.FORWARD.equals(currState) && currentPosition.getY() == 2) {
                return Space.UP;
            }

            if (Space.UP.equals(currState) && currentPosition.getZ() == 2) {
                return Space.BACKWARD;
            }

            if (Space.BACKWARD.equals(currState) && currentPosition.getY() == 0) {
                return Space.DOWN;
            }

            if (Space.DOWN.equals(currState) && currentPosition.getZ() == 0) {
                return Space.FORWARD;
            }
        }


        if ((new Position(1, 0, 0)).equals(direction)) {
            if (Space.LEFT.equals(currState) && currentPosition.getY() == 2) {
                return Space.UP;
            }

            if (Space.UP.equals(currState) && currentPosition.getX() == 2) {
                return Space.RIGHT;
            }

            if (Space.RIGHT.equals(currState) && currentPosition.getY() == 0) {
                return Space.DOWN;
            }

            if (Space.DOWN.equals(currState) && currentPosition.getX() == 0) {
                return Space.LEFT;
            }
        }

        if ((new Position(0, 1, 0)).equals(direction)) {
            if (Space.LEFT.equals(currState) && currentPosition.getZ() == 2) {
                return Space.BACKWARD;
            }

            if (Space.BACKWARD.equals(currState) && currentPosition.getX() == 2) {
                return Space.RIGHT;
            }

            if (Space.RIGHT.equals(currState) && currentPosition.getZ() == 0) {
                return Space.FORWARD;
            }

            if (Space.FORWARD.equals(currState) && currentPosition.getX() == 0) {
                return Space.LEFT;
            }
        }

        return currState;
    }

    public Position getNextPosition(Position currentPosition, Space currState, Position direction) {

        if ((new Position(0, 0, 1)).equals(direction)) {
            if (Space.FORWARD.equals(currState) && currentPosition.getY() < 2) {
                return new Position(currentPosition.getX(), currentPosition.getY() + 1, currentPosition.getZ());
            }

            if (Space.UP.equals(currState) && currentPosition.getZ() < 2) {
                return new Position(currentPosition.getX(), currentPosition.getY(), currentPosition.getZ() + 1);
            }

            if (Space.BACKWARD.equals(currState) && currentPosition.getY() > 0) {
                return new Position(currentPosition.getX(), currentPosition.getY() - 1, currentPosition.getZ());
            }

            if (Space.DOWN.equals(currState) && currentPosition.getZ() > 0) {
                return new Position(currentPosition.getX(), currentPosition.getY(), currentPosition.getZ() - 1);
            }
        }

        if ((new Position(1, 0, 0)).equals(direction)) {
            if (Space.LEFT.equals(currState) && currentPosition.getY() < 2) {
                return new Position(currentPosition.getX(), currentPosition.getY() + 1, currentPosition.getZ());
            }

            if (Space.UP.equals(currState) && currentPosition.getX() < 2) {
                return new Position(currentPosition.getX() + 1, currentPosition.getY(), currentPosition.getZ());
            }

            if (Space.RIGHT.equals(currState) && currentPosition.getY() > 0) {
                return new Position(currentPosition.getX(), currentPosition.getY() - 1, currentPosition.getZ());
            }

            if (Space.DOWN.equals(currState) && currentPosition.getX() > 0) {
                return new Position(currentPosition.getX() - 1, currentPosition.getY(), currentPosition.getZ());
            }
        }


        if ((new Position(0, 1, 0)).equals(direction)) {
            if (Space.LEFT.equals(currState) && currentPosition.getZ() < 2) {
                return new Position(currentPosition.getX(), currentPosition.getY(), currentPosition.getZ() + 1);
            }

            if (Space.BACKWARD.equals(currState) && currentPosition.getX() < 2) {
                return new Position(currentPosition.getX() + 1, currentPosition.getY(), currentPosition.getZ());
            }

            if (Space.RIGHT.equals(currState) && currentPosition.getZ() > 0) {
                return new Position(currentPosition.getX(), currentPosition.getY(), currentPosition.getZ() - 1);
            }

            if (Space.FORWARD.equals(currState) && currentPosition.getX() > 0) {
                return new Position(currentPosition.getX() - 1, currentPosition.getY(), currentPosition.getZ());
            }
        }

        throw new IllegalStateException("Unknown direction vector");

    }

    public void initThirdLayer() {
        var pos = new Position(0, 2, 0);
        var pos2 = new Position(0, 2, 1);
        var pos3 = new Position(0, 2, 2);
        var pos4 = new Position(1, 2, 0);
        var pos5 = new Position(1, 2, 1);
        var pos6 = new Position(1, 2, 2);
        var pos7 = new Position(2, 2, 0);
        var pos8 = new Position(2, 2, 1);
        var pos9 = new Position(2, 2, 2);
        state.put(pos, Cube.builder()
                .position(pos)
                .colors(
                        Map.of(
                                Navigation.FORWARD, Color.ORANGE,
                                Navigation.LEFT, Color.GREEN,
                                Navigation.RIGHT, Color.BLACK,
                                Navigation.DOWN, Color.BLACK,
                                Navigation.UP, Color.WHITE,
                                Navigation.BACKWARD, Color.BLACK
                        )
                )
                .build());
        state.put(pos2, Cube.builder()
                .position(pos2)
                .colors(
                        Map.of(
                                Navigation.FORWARD, Color.BLACK,
                                Navigation.LEFT, Color.GREEN,
                                Navigation.RIGHT, Color.BLACK,
                                Navigation.DOWN, Color.BLACK,
                                Navigation.UP, Color.WHITE,
                                Navigation.BACKWARD, Color.BLACK
                        )
                )
                .build());


        state.put(pos3, Cube.builder()
                .position(pos3)
                .colors(
                        Map.of(
                                Navigation.FORWARD, Color.BLACK,
                                Navigation.LEFT, Color.GREEN,
                                Navigation.RIGHT, Color.BLACK,
                                Navigation.DOWN, Color.BLACK,
                                Navigation.UP, Color.WHITE,
                                Navigation.BACKWARD, Color.RED
                        )
                )
                .build());

        state.put(pos4, Cube.builder()
                .position(pos4)
                .colors(
                        Map.of(
                                Navigation.FORWARD, Color.ORANGE,
                                Navigation.LEFT, Color.BLACK,
                                Navigation.RIGHT, Color.BLACK,
                                Navigation.DOWN, Color.BLACK,
                                Navigation.UP, Color.WHITE,
                                Navigation.BACKWARD, Color.BLACK
                        )
                )
                .build());

        state.put(pos5, Cube.builder()
                .position(pos5)
                .colors(
                        Map.of(
                                Navigation.FORWARD, Color.BLACK,
                                Navigation.LEFT, Color.BLACK,
                                Navigation.RIGHT, Color.BLACK,
                                Navigation.DOWN, Color.BLACK,
                                Navigation.UP, Color.WHITE,
                                Navigation.BACKWARD, Color.BLACK
                        )
                )
                .build());

        state.put(pos6, Cube.builder()
                .position(pos6)
                .colors(
                        Map.of(
                                Navigation.FORWARD, Color.BLACK,
                                Navigation.LEFT, Color.BLACK,
                                Navigation.RIGHT, Color.BLACK,
                                Navigation.DOWN, Color.BLACK,
                                Navigation.UP, Color.WHITE,
                                Navigation.BACKWARD, Color.RED
                        )
                )
                .build());

        state.put(pos7, Cube.builder()
                .position(pos7)
                .colors(
                        Map.of(
                                Navigation.FORWARD, Color.ORANGE,
                                Navigation.LEFT, Color.BLACK,
                                Navigation.RIGHT, Color.BLUE,
                                Navigation.DOWN, Color.BLACK,
                                Navigation.UP, Color.WHITE,
                                Navigation.BACKWARD, Color.BLACK
                        )
                )
                .build());

        state.put(pos8, Cube.builder()
                .position(pos8)
                .colors(
                        Map.of(
                                Navigation.FORWARD, Color.BLACK,
                                Navigation.LEFT, Color.BLACK,
                                Navigation.RIGHT, Color.BLUE,
                                Navigation.DOWN, Color.BLACK,
                                Navigation.UP, Color.WHITE,
                                Navigation.BACKWARD, Color.BLACK
                        )
                )
                .build());

        state.put(pos9, Cube.builder()
                .position(pos9)
                .colors(
                        Map.of(
                                Navigation.FORWARD, Color.BLACK,
                                Navigation.LEFT, Color.BLACK,
                                Navigation.RIGHT, Color.BLUE,
                                Navigation.DOWN, Color.BLACK,
                                Navigation.UP, Color.WHITE,
                                Navigation.BACKWARD, Color.RED
                        )
                )
                .build());
    }

    public void initSecondLayer() {
        var pos = new Position(0, 1, 0);
        var pos2 = new Position(0, 1, 1);
        var pos3 = new Position(0, 1, 2);
        var pos4 = new Position(1, 1, 0);
        var pos5 = new Position(1, 1, 1);
        var pos6 = new Position(1, 1, 2);
        var pos7 = new Position(2, 1, 0);
        var pos8 = new Position(2, 1, 1);
        var pos9 = new Position(2, 1, 2);
        state.put(pos, Cube.builder()
                .position(pos)
                .colors(
                        Map.of(
                                Navigation.FORWARD, Color.ORANGE,
                                Navigation.LEFT, Color.GREEN,
                                Navigation.RIGHT, Color.BLACK,
                                Navigation.DOWN, Color.BLACK,
                                Navigation.UP, Color.BLACK,
                                Navigation.BACKWARD, Color.BLACK
                        )
                )
                .build());
        state.put(pos2, Cube.builder()
                .position(pos2)
                .colors(
                        Map.of(
                                Navigation.FORWARD, Color.BLACK,
                                Navigation.LEFT, Color.GREEN,
                                Navigation.RIGHT, Color.BLACK,
                                Navigation.DOWN, Color.BLACK,
                                Navigation.UP, Color.BLACK,
                                Navigation.BACKWARD, Color.BLACK
                        )
                )
                .build());


        state.put(pos3, Cube.builder()
                .position(pos3)
                .colors(
                        Map.of(
                                Navigation.FORWARD, Color.BLACK,
                                Navigation.LEFT, Color.GREEN,
                                Navigation.RIGHT, Color.BLACK,
                                Navigation.DOWN, Color.BLACK,
                                Navigation.UP, Color.BLACK,
                                Navigation.BACKWARD, Color.RED
                        )
                )
                .build());

        state.put(pos4, Cube.builder()
                .position(pos4)
                .colors(
                        Map.of(
                                Navigation.FORWARD, Color.ORANGE,
                                Navigation.LEFT, Color.BLACK,
                                Navigation.RIGHT, Color.BLACK,
                                Navigation.DOWN, Color.BLACK,
                                Navigation.UP, Color.BLACK,
                                Navigation.BACKWARD, Color.BLACK
                        )
                )
                .build());

        state.put(pos5, Cube.builder()
                .position(pos5)
                .colors(
                        Map.of(
                                Navigation.FORWARD, Color.BLACK,
                                Navigation.LEFT, Color.BLACK,
                                Navigation.RIGHT, Color.BLACK,
                                Navigation.DOWN, Color.BLACK,
                                Navigation.UP, Color.BLACK,
                                Navigation.BACKWARD, Color.BLACK
                        )
                )
                .build());

        state.put(pos6, Cube.builder()
                .position(pos6)
                .colors(
                        Map.of(
                                Navigation.FORWARD, Color.BLACK,
                                Navigation.LEFT, Color.BLACK,
                                Navigation.RIGHT, Color.BLACK,
                                Navigation.DOWN, Color.BLACK,
                                Navigation.UP, Color.BLACK,
                                Navigation.BACKWARD, Color.RED
                        )
                )
                .build());

        state.put(pos7, Cube.builder()
                .position(pos7)
                .colors(
                        Map.of(
                                Navigation.FORWARD, Color.ORANGE,
                                Navigation.LEFT, Color.BLACK,
                                Navigation.RIGHT, Color.BLUE,
                                Navigation.DOWN, Color.BLACK,
                                Navigation.UP, Color.BLACK,
                                Navigation.BACKWARD, Color.BLACK
                        )
                )
                .build());

        state.put(pos8, Cube.builder()
                .position(pos8)
                .colors(
                        Map.of(
                                Navigation.FORWARD, Color.BLACK,
                                Navigation.LEFT, Color.BLACK,
                                Navigation.RIGHT, Color.BLUE,
                                Navigation.DOWN, Color.BLACK,
                                Navigation.UP, Color.BLACK,
                                Navigation.BACKWARD, Color.BLACK
                        )
                )
                .build());

        state.put(pos9, Cube.builder()
                .position(pos9)
                .colors(
                        Map.of(
                                Navigation.FORWARD, Color.BLACK,
                                Navigation.LEFT, Color.BLACK,
                                Navigation.RIGHT, Color.BLUE,
                                Navigation.DOWN, Color.BLACK,
                                Navigation.UP, Color.BLACK,
                                Navigation.BACKWARD, Color.RED
                        )
                )
                .build());
    }

    public void initFirstLayer() {
        var pos = new Position(0, 0, 0);
        var pos2 = new Position(0, 0, 1);
        var pos3 = new Position(0, 0, 2);
        var pos4 = new Position(1, 0, 0);
        var pos5 = new Position(1, 0, 1);
        var pos6 = new Position(1, 0, 2);
        var pos7 = new Position(2, 0, 0);
        var pos8 = new Position(2, 0, 1);
        var pos9 = new Position(2, 0, 2);
        state.put(pos, Cube.builder()
                .position(pos)
                .colors(
                        Map.of(
                                Navigation.FORWARD, Color.ORANGE,
                                Navigation.LEFT, Color.GREEN,
                                Navigation.RIGHT, Color.BLACK,
                                Navigation.DOWN, Color.YELLOW,
                                Navigation.UP, Color.BLACK,
                                Navigation.BACKWARD, Color.BLACK
                        )
                )
                .build());
        state.put(pos2, Cube.builder()
                .position(pos2)
                .colors(
                        Map.of(
                                Navigation.FORWARD, Color.BLACK,
                                Navigation.LEFT, Color.GREEN,
                                Navigation.RIGHT, Color.BLACK,
                                Navigation.DOWN, Color.YELLOW,
                                Navigation.UP, Color.BLACK,
                                Navigation.BACKWARD, Color.BLACK
                        )
                )
                .build());


        state.put(pos3, Cube.builder()
                .position(pos3)
                .colors(
                        Map.of(
                                Navigation.FORWARD, Color.BLACK,
                                Navigation.LEFT, Color.GREEN,
                                Navigation.RIGHT, Color.BLACK,
                                Navigation.DOWN, Color.YELLOW,
                                Navigation.UP, Color.BLACK,
                                Navigation.BACKWARD, Color.RED
                        )
                )
                .build());

        state.put(pos4, Cube.builder()
                .position(pos4)
                .colors(
                        Map.of(
                                Navigation.FORWARD, Color.ORANGE,
                                Navigation.LEFT, Color.BLACK,
                                Navigation.RIGHT, Color.BLACK,
                                Navigation.DOWN, Color.YELLOW,
                                Navigation.UP, Color.BLACK,
                                Navigation.BACKWARD, Color.BLACK
                        )
                )
                .build());

        state.put(pos5, Cube.builder()
                .position(pos5)
                .colors(
                        Map.of(
                                Navigation.FORWARD, Color.BLACK,
                                Navigation.LEFT, Color.BLACK,
                                Navigation.RIGHT, Color.BLACK,
                                Navigation.DOWN, Color.YELLOW,
                                Navigation.UP, Color.BLACK,
                                Navigation.BACKWARD, Color.BLACK
                        )
                )
                .build());

        state.put(pos6, Cube.builder()
                .position(pos6)
                .colors(
                        Map.of(
                                Navigation.FORWARD, Color.BLACK,
                                Navigation.LEFT, Color.BLACK,
                                Navigation.RIGHT, Color.BLACK,
                                Navigation.DOWN, Color.YELLOW,
                                Navigation.UP, Color.BLACK,
                                Navigation.BACKWARD, Color.RED
                        )
                )
                .build());

        state.put(pos7, Cube.builder()
                .position(pos7)
                .colors(
                        Map.of(
                                Navigation.FORWARD, Color.ORANGE,
                                Navigation.LEFT, Color.BLACK,
                                Navigation.RIGHT, Color.BLUE,
                                Navigation.DOWN, Color.YELLOW,
                                Navigation.UP, Color.BLACK,
                                Navigation.BACKWARD, Color.BLACK
                        )
                )
                .build());

        state.put(pos8, Cube.builder()
                .position(pos8)
                .colors(
                        Map.of(
                                Navigation.FORWARD, Color.BLACK,
                                Navigation.LEFT, Color.BLACK,
                                Navigation.RIGHT, Color.BLUE,
                                Navigation.DOWN, Color.YELLOW,
                                Navigation.UP, Color.BLACK,
                                Navigation.BACKWARD, Color.BLACK
                        )
                )
                .build());

        state.put(pos9, Cube.builder()
                .position(pos9)
                .colors(
                        Map.of(
                                Navigation.FORWARD, Color.BLACK,
                                Navigation.LEFT, Color.BLACK,
                                Navigation.RIGHT, Color.BLUE,
                                Navigation.DOWN, Color.YELLOW,
                                Navigation.UP, Color.BLACK,
                                Navigation.BACKWARD, Color.RED
                        )
                )
                .build());
    }


    public void initSpaceMap() {
        spacePositionMap.put(
                Space.UP,
                Set.of(
                        new Position(0, 2, 0),
                        new Position(0, 2, 1),
                        new Position(0, 2, 2),
                        new Position(1, 2, 0),
                        new Position(1, 2, 1),
                        new Position(1, 2, 2),
                        new Position(2, 2, 0),
                        new Position(2, 2, 1),
                        new Position(2, 2, 2)
                )
        );

        spacePositionMap.put(
                Space.DOWN,
                Set.of(
                        new Position(0, 0, 0),
                        new Position(0, 0, 1),
                        new Position(0, 0, 2),
                        new Position(1, 0, 0),
                        new Position(1, 0, 1),
                        new Position(1, 0, 2),
                        new Position(2, 0, 0),
                        new Position(2, 0, 1),
                        new Position(2, 0, 2)
                )
        );

        spacePositionMap.put(
                Space.FORWARD,
                Set.of(
                        new Position(0, 0, 0),
                        new Position(0, 1, 0),
                        new Position(0, 2, 0),
                        new Position(1, 0, 0),
                        new Position(1, 1, 0),
                        new Position(1, 2, 0),
                        new Position(2, 0, 0),
                        new Position(2, 1, 0),
                        new Position(2, 2, 0)
                )
        );

        spacePositionMap.put(
                Space.BACKWARD,
                Set.of(
                        new Position(0, 0, 2),
                        new Position(0, 1, 2),
                        new Position(0, 2, 2),
                        new Position(1, 0, 2),
                        new Position(1, 1, 2),
                        new Position(1, 2, 2),
                        new Position(2, 0, 2),
                        new Position(2, 1, 2),
                        new Position(2, 2, 2)
                )
        );

        spacePositionMap.put(
                Space.LEFT,
                Set.of(
                        new Position(0, 0, 0),
                        new Position(0, 0, 1),
                        new Position(0, 0, 2),
                        new Position(0, 1, 0),
                        new Position(0, 1, 1),
                        new Position(0, 1, 2),
                        new Position(0, 2, 0),
                        new Position(0, 2, 1),
                        new Position(0, 2, 2)
                )
        );

        spacePositionMap.put(
                Space.RIGHT,
                Set.of(
                        new Position(2, 0, 0),
                        new Position(2, 0, 1),
                        new Position(2, 0, 2),
                        new Position(2, 1, 0),
                        new Position(2, 1, 1),
                        new Position(2, 1, 2),
                        new Position(2, 2, 0),
                        new Position(2, 2, 1),
                        new Position(2, 2, 2)
                )
        );
    }


}
