package stuba.fiit.parallel;

import stuba.fiit.entity.Entity;
import stuba.fiit.entity.Pair;

import java.util.Set;

public interface SMPSolver {
    Set<Pair<Entity, Entity>> findStablePairs();
}
