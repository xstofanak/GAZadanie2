package stuba.fiit.parallel;

import stuba.fiit.entity.Boss;
import stuba.fiit.entity.Entity;
import stuba.fiit.entity.Pair;
import stuba.fiit.entity.Worker;
import java.util.Set;
import java.util.stream.Collectors;

public class SMPSolverBoss extends SMPSolverTemplate {
    public SMPSolverBoss(int capacity, Set<Worker> workerSet, Set<Boss> bossSet) {
        super(capacity, workerSet, bossSet);
    }

    @Override
    Set<Pair<Entity, Entity>> findInitialPairs() {
        return bossSet.stream()
                .map(boss -> {
                    int workerIndex = boss.getLinearPriority().get(0);
                    Worker worker = findWorkerByIndex(workerIndex);
                    return new Pair<Entity, Entity>(boss, worker);
                })
                .collect(Collectors.toSet());
    }

    @Override
    Entity findEntityByIndex(int index) {
        return findBossByIndex(index);
    }
}
