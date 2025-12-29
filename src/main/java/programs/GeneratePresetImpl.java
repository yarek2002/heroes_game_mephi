package programs;

import com.battle.heroes.army.Army;
import com.battle.heroes.army.Unit;
import com.battle.heroes.army.programs.GeneratePreset;

import java.util.*;

public class GeneratePresetImpl implements GeneratePreset {

    private static final int MAX_PER_TYPE = 11;

    public GeneratePresetImpl() {
    }

    @Override
    public Army generate(List<Unit> unitList, int maxPoints) {
        Army army = new Army();
        Map<String, Integer> count = new HashMap<>();

        unitList.sort(Comparator.comparingDouble(
                u -> -(double) u.getBaseAttack() / u.getCost()
        ));

        int points = 0;

        while (true) {
            boolean added = false;

            for (Unit proto : unitList) {
                String type = proto.getUnitType();
                int c = count.getOrDefault(type, 0);

                if (c >= MAX_PER_TYPE) continue;
                if (points + proto.getCost() > maxPoints) continue;

                Unit u = new Unit(
                        type + "_" + c,
                        proto.getUnitType(),
                        proto.getHealth(),
                        proto.getBaseAttack(),
                        proto.getCost(),
                        proto.getAttackType(),
                        proto.getAttackBonuses(),
                        proto.getDefenceBonuses(),
                        0,
                        0
                );


                u.setName(type + "_" + c);

                army.getUnits().add(u);
                count.put(type, c + 1);
                points += proto.getCost();
                added = true;
                break;
            }

            if (!added) break;
        }

        army.setPoints(points);
        return army;
    }
}
