package programs;

import com.battle.heroes.army.programs.SuitableForAttackUnitsFinder;
import com.battle.heroes.army.Unit;

import java.util.ArrayList;
import java.util.List;

public class SuitableForAttackUnitsFinderImpl implements SuitableForAttackUnitsFinder {

    public SuitableForAttackUnitsFinderImpl() {
        // пустой конструктор обязателен
    }

    @Override
    public List<Unit> getSuitableUnits(List<List<Unit>> unitsByRow, boolean isLeftArmyTarget) {
        List<Unit> result = new ArrayList<>();

        for (List<Unit> row : unitsByRow) {
            for (Unit u : row) {
                if (u == null || !u.isAlive()) continue;

                boolean blocked = false;

                for (Unit other : row) {
                    if (other == null || !other.isAlive()) continue;
                    if (other == u) continue;

                    if (isLeftArmyTarget
                            && other.getyCoordinate() == u.getyCoordinate() - 1) {
                        blocked = true;
                        break;
                    }

                    if (!isLeftArmyTarget
                            && other.getyCoordinate() == u.getyCoordinate() + 1) {
                        blocked = true;
                        break;
                    }
                }

                if (!blocked) {
                    result.add(u);
                }
            }
        }

        return result;
    }
}
