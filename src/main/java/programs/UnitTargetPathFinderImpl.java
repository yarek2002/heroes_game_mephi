package programs;

import com.battle.heroes.army.programs.UnitTargetPathFinder;
import com.battle.heroes.army.programs.Edge;
import com.battle.heroes.army.Unit;

import java.util.*;

public class UnitTargetPathFinderImpl implements UnitTargetPathFinder {

    private static final int W = 27;
    private static final int H = 21;

    private static final int[] DX = {-1, -1, -1, 0, 0, 1, 1, 1};
    private static final int[] DY = {-1, 0, 1, -1, 1, -1, 0, 1};

    public UnitTargetPathFinderImpl() {
        // пустой конструктор обязателен
    }

    @Override
    public List<Edge> getTargetPath(Unit a, Unit t, List<Unit> units) {

        boolean[][] blocked = new boolean[W][H];
        for (Unit u : units) {
            if (!u.isAlive() || u == a || u == t) continue;
            blocked[u.getxCoordinate()][u.getyCoordinate()] = true;
        }

        boolean[][] vis = new boolean[W][H];
        Map<String, Edge> parent = new HashMap<>();
        Queue<Edge> q = new ArrayDeque<>();

        Edge start = new Edge(a.getxCoordinate(), a.getyCoordinate());
        Edge end   = new Edge(t.getxCoordinate(), t.getyCoordinate());

        q.add(start);
        vis[start.getX()][start.getY()] = true;

        while (!q.isEmpty()) {
            Edge cur = q.poll();

            if (cur.getX() == end.getX() && cur.getY() == end.getY()) {
                return build(parent, start, end);
            }

            for (int i = 0; i < 8; i++) {
                int nx = cur.getX() + DX[i];
                int ny = cur.getY() + DY[i];

                if (nx < 0 || ny < 0 || nx >= W || ny >= H) continue;
                if (vis[nx][ny] || blocked[nx][ny]) continue;

                vis[nx][ny] = true;
                Edge next = new Edge(nx, ny);
                parent.put(nx + ":" + ny, cur);
                q.add(next);
            }
        }

        return Collections.emptyList();
    }

    private List<Edge> build(Map<String, Edge> p, Edge s, Edge e) {
        List<Edge> path = new ArrayList<>();
        Edge cur = e;

        while (!(cur.getX() == s.getX() && cur.getY() == s.getY())) {
            path.add(cur);
            cur = p.get(cur.getX() + ":" + cur.getY());
            if (cur == null) return Collections.emptyList();
        }

        path.add(s);
        Collections.reverse(path);
        return path;
    }
}
