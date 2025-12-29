package programs;

import com.battle.heroes.army.Army;
import com.battle.heroes.army.Unit;
import com.battle.heroes.army.programs.SimulateBattle;
import com.battle.heroes.army.programs.PrintBattleLog;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SimulateBattleImpl implements SimulateBattle {

    // ОБЯЗАТЕЛЬНОЕ ПОЛЕ
    public PrintBattleLog printBattleLog;

    public SimulateBattleImpl() {
        // пустой конструктор
    }

    @Override
    public void simulate(Army playerArmy, Army computerArmy) throws InterruptedException {

        while (hasAlive(playerArmy) && hasAlive(computerArmy)) {

            List<Unit> queue = new ArrayList<>();
            addAlive(queue, playerArmy);
            addAlive(queue, computerArmy);

            queue.sort(Comparator.comparingInt(Unit::getBaseAttack).reversed());

            for (Unit unit : queue) {
                if (!unit.isAlive()) continue;

                Unit target = unit.getProgram().attack();

                if (target != null && printBattleLog != null) {
                    printBattleLog.printBattleLog(unit, target);
                }
            }
        }
    }

    private boolean hasAlive(Army army) {
        for (Unit u : army.getUnits()) {
            if (u.isAlive()) return true;
        }
        return false;
    }

    private void addAlive(List<Unit> list, Army army) {
        for (Unit u : army.getUnits()) {
            if (u.isAlive()) {
                list.add(u);
            }
        }
    }
}
