package sia.calcudoku;

import java.io.IOException;

import sia.gps.AStarEngine;
import sia.gps.BFSEngine;
import sia.gps.DFSEngine;
import sia.gps.GPSEngine;
import sia.gps.GreedyEngine;
import sia.gps.IterativeDFSEngine;

public class CalcuDoku {

	public static void main(String args[]) throws IOException {
		if (args == null || args.length < 3) {
			System.out.println("Formato de argumentos invalido");
			System.out
					.println("Ingrese [BFS|DFS|IDFS|HIDFS|Greedy|Astar] [QuadOp3x3|QuadOp4x4|QuadOp4x4tricky|5x5|QuadOp6x6|DualOp8x8] [width] [Blocks|rowsNCols|both] [limitIDFS]");
			return;
		}

		printArguments(args);

		CalcuDokuProblem problem = new CalcuDokuProblem(
				"src/main/resources/levels/" + args[1],
				"src/main/resources/levels/" + args[2],
				args.length > 3 ? Heuristic.valueOf(args[3].toUpperCase())
						: Heuristic.BOTH);
		GPSEngine engine = null;

		String engine_name = args[0].toUpperCase();
		if (engine_name.equals("BFS")) {
			engine = new BFSEngine();
		} else if (engine_name.equals("DFS")) {
			engine = new DFSEngine();
		} else if (engine_name.equals("ASTAR")) {
			engine = new AStarEngine();
		} else if (engine_name.equals("GREEDY")) {
			engine = new GreedyEngine();
		} else if (engine_name.equals("ITERATIVE_DFS")) {
			engine = new IterativeDFSEngine(Integer.parseInt(args[4]));
		}

		engine.engine(problem);
	}

	private static void printArguments(String[] args) {
		System.out.println("Engine: " + args[0]);
		System.out.println("Initial board: " + args[1]);
		System.out.println("Goal board: " + args[2]);
		System.out.println("Heuristic: " + args[3]);
	}
}
