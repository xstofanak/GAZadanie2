package stuba.fiit.parallel;

import stuba.fiit.entity.Boss;
import stuba.fiit.entity.Entity;
import stuba.fiit.entity.Pair;
import stuba.fiit.entity.Worker;
import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings({"unused"})
abstract class SMPSolverTemplate implements SMPSolver {
    private int divideFactor;
    Set<Boss> bossSet;
    Set<Worker> workerSet;

    SMPSolverTemplate(Set<Worker> workerSet, Set<Boss> bossSet, int divideFactor) {
        this.workerSet = workerSet;
        this.bossSet = bossSet;
        this.divideFactor = divideFactor;
    }

    @Override
    public Set<Pair<Entity, Entity>> findStablePairs() {
        Set<Pair<Entity, Entity>> initialPairs = findInitialPairs();
        return divideAndConquer(initialPairs);
    }

    private Set<Pair<Entity, Entity>> divideAndConquer(Set<Pair<Entity, Entity>> pairs) {
        if(pairs.size() >= 2 * divideFactor) {
            // divide
            Set<Set<Pair<Entity, Entity>>> dividedPairs = divide(pairs);
            // conquer
            Set<Set<Pair<Entity, Entity>>> processedPairs = dividedPairs.stream()
                    .map(this::divideAndConquer)
                    .collect(Collectors.toSet());
            // join
            return doJoinJob(dividedPairs);
        } else {
            Set<Set<Pair<Entity, Entity>>> singletonSet = new HashSet<>(Collections.singletonList(pairs));
            return doJoinJob(singletonSet);
        }
    }

    private Set<Set<Pair<Entity, Entity>>> divide(Set<Pair<Entity, Entity>> pairs) {
        // final set
        Set<Set<Pair<Entity, Entity>>> finalSet = new HashSet<>();
        // number of entries in one set
        int count = pairs.size() / divideFactor;
        // set of results
        Set<Set<Pair<Entity, Entity>>> result = new HashSet<>();
        // iteration
        int index = 0;
        Set<Pair<Entity, Entity>> runningSet = new HashSet<>();
        for(Pair<Entity, Entity> pair : pairs) {
            runningSet.add(pair);
            if(index == count - 1) {
                finalSet.add(new HashSet<>(runningSet));
                runningSet.clear();
                index = 0;
            } else {
                index++;
            }
        }
        if(!runningSet.isEmpty()) {
            finalSet.add(runningSet);
        }
        return finalSet;
    }

    private Set<Pair<Entity, Entity>> doJoinJob(Set<Set<Pair<Entity, Entity>>> sets) {
        // find conflicting pairs
        Set<Set<Pair<Entity, Entity>>> collisions = findCollisions(sets);
        if(collisions.isEmpty()) {
            // just join all input sets
            return joinAllGroups(sets);
        } else {
            // fix collisions
            fixCollisions(collisions);
            return doJoinJob(sets);
        }
    }

    private Set<Set<Pair<Entity, Entity>>> findCollisions(Set<Set<Pair<Entity, Entity>>> sets) {
        // get all second entities
        Set<Entity> allSecondEntities = sets.stream()
                .flatMap(group -> group.stream().map(Pair::getyElement))
                .collect(Collectors.toSet());
        // for each second entity, find occurrences and collisions
        return allSecondEntities.stream()
                .map(secondEntity -> sets.stream()
                        .flatMap(group -> group.stream()
                                .filter(pair -> pair.getyElement().equals(secondEntity)))
                        .collect(Collectors.toSet()))
                .filter(pairs -> pairs.size() >= 2)
                .collect(Collectors.toSet());
    }

    private Set<Pair<Entity, Entity>> joinAllGroups(Set<Set<Pair<Entity, Entity>>> inputSets) {
        Set<Pair<Entity, Entity>> finalSet = new HashSet<>();
        inputSets.forEach(finalSet::addAll);
        return finalSet;
    }

    private void fixCollisions(Set<Set<Pair<Entity, Entity>>> collisions) {
        collisions.forEach(groupOfCollisions -> {
            // finding of common second entity and list of second entities's priorities
            Entity secondEntity = groupOfCollisions.stream().findFirst().get().getyElement();
            List<Integer> secondEntityPriorities = secondEntity.getLinearPriority();
            // pair that is stale - it won't change because of the highest priority in second entities' list
            Pair<Entity, Entity> stalePair = groupOfCollisions.stream()
                    .min(Comparator.comparing(o -> findPriority(secondEntityPriorities, o.getxElement())))
                    .get();
            // every other pair must be modified - first entity will choose next second's entity
            groupOfCollisions.stream()
                .filter(pair -> !pair.equals(stalePair))
                .forEach(pair -> {
                    Entity firstEntity = pair.getxElement();
                    List<Integer> firstEntitiesPriorities = firstEntity.getLinearPriority();
                    int secondEntityPriority = findPriority(firstEntitiesPriorities, secondEntity);
                    int nextSecondEntityIndex = firstEntitiesPriorities.get(secondEntityPriority + 1);
                    Entity nextEntity = findEntityByIndex(nextSecondEntityIndex);
                    pair.setyElement(nextEntity);
                });
        });
    }

    private Integer findPriority(List<Integer> priorities, Entity entity) {
        Integer index = 0;
        while(true) {
            if(priorities.get(index) == entity.getID()) {
                return index;
            }
            index++;
        }
    }

    Worker findWorkerByIndex(int index) {
        return workerSet.stream()
                .filter(worker -> worker.getID() == index)
                .findAny()
                .get();
    }
    Boss findBossByIndex(int index) {
        return bossSet.stream()
                .filter(worker -> worker.getID() == index)
                .findAny()
                .get();
    }

    abstract Set<Pair<Entity, Entity>> findInitialPairs();
    abstract Entity findEntityByIndex(int index);
}
