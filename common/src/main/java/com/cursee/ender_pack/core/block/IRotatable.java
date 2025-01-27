package com.cursee.ender_pack.core.block;

import net.minecraft.Util;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public interface IRotatable {

    double ONE_SIXTEENTH = 0.0625d;

    double startX = ONE_SIXTEENTH * 3;
    double startY = ONE_SIXTEENTH * 0;
    double startZ = ONE_SIXTEENTH * 4 + (ONE_SIXTEENTH/8);
    double endX = ONE_SIXTEENTH * 13;
    double endY = ONE_SIXTEENTH * 12 - (ONE_SIXTEENTH / 2);
    double endZ = ONE_SIXTEENTH * 10;

    static VoxelShape rotateShape(Direction from, Direction to, VoxelShape shape) {

        VoxelShape[] buffer = new VoxelShape[] { shape, Shapes.empty() };

        int times = (to.get2DDataValue() - from.get2DDataValue() + 4) % 4;
        for (int i = 0; i < times; i++) {
            buffer[0].forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> buffer[1] = Shapes.joinUnoptimized(buffer[1],
                    Shapes.box(1 - maxZ, minY, minX, 1 - minZ, maxY, maxX),
                    BooleanOp.OR
            ));
            buffer[0] = buffer[1];
            buffer[1] = Shapes.empty();
        }
        return buffer[0];
    }

    Supplier<VoxelShape> voxelShapeSupplier = () -> {

        VoxelShape shape = Shapes.empty();
        shape = Shapes.joinUnoptimized(shape, Shapes.box(startX, startY, startZ, endX, endY, endZ), BooleanOp.OR);
        return shape;
    };

    Map<Direction, VoxelShape> SHAPE = Util.make(new HashMap<>(), map -> {

        for (Direction direction : Direction.Plane.HORIZONTAL.stream().toList()) {
            map.put(direction, rotateShape(Direction.NORTH, direction, voxelShapeSupplier.get()));
        }
    });
}
