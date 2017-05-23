import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Tennis {

	public static void main(String[] args) {
		try {
			String path = args[0];
			File file = new File(path);
			TennisScoreboard scoreboard = new TennisScoreboard("A", "B");
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			List<String> rawDataList = new ArrayList<>();
			while ((line = br.readLine()) != null) {
				rawDataList.add(line);
			}
			for (String string : scoreboard.generateScore(rawDataList)) {
				System.out.println(string);
			}
			br.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	static class TennisScoreboard {

		private final String[] SCORE_DISPLAY = { "0", "15", "30", "40" };
		private final String ADVANTAGE = "ADV";
		private final String DUCE = "DUCE";
		private final String WIN = "WIN";
		private final String SEPARATOR_OF_GAME = ":";
		private final String SEPARATOR_OF_POINT = " ";
		private final String BANK_STRING = "";
		private String playerHome;
		private String playerAway;
		private int pointOfGameHome;
		private int pointOfGameAway;
		private int gameOfSetHome;
		private int gameOfSetAway;

		// private String gameName;
		private boolean tieBreakFlag;
		List<String> result;

		public TennisScoreboard(String playerHome, String playerAway) {
			super();
			initBorad();
			this.playerHome = playerHome;
			this.playerAway = playerAway;
		}

		private void initBorad() {
			this.tieBreakFlag = false;
			this.result = new ArrayList<String>();
			this.gameOfSetHome = 0;
			this.gameOfSetAway = 0;
			clearGame();
		}

		private void clearGame() {
			this.pointOfGameHome = 0;
			this.pointOfGameAway = 0;
		}

		public List<String> generateScore(List<String> rawDataList) throws Exception {
			for (String rawData : rawDataList) {
				if (rawData.trim().indexOf(SEPARATOR_OF_GAME) > -1) {
					clearGame();
					String[] dataArr = rawData.split(SEPARATOR_OF_GAME);
					String gameName = dataArr[0];
					printLine(gameName);
					String rawPoint = dataArr[1].trim();
					String[] pointArr = rawPoint.split(SEPARATOR_OF_POINT);
					for (String win : pointArr) {
						int point = addPointToPlayer(win);
						if (!isTieBreakFlag()) {
							if ((getPointOfGameHome() > 3 || getPointOfGameAway() > 3)
									&& (Math.abs(getPointOfGameHome() - getPointOfGameAway()) <= 1)) {
								printPoint(win, ADVANTAGE);
								if (isDuce()) {
									printLine(DUCE);
								}
							} else if (getPointOfGameHome() > 3 || getPointOfGameAway() > 3) {
								setGameWiner(win);
								break;
							} else {
								printPoint(win, SCORE_DISPLAY[point]);
							}
						} else {
							if ((getPointOfGameHome() >= 6 && getPointOfGameAway() >= 6)
									&& (Math.abs(getPointOfGameHome() - getPointOfGameAway()) == 2)) {
								setGameWiner(win);
							} else if ((getPointOfGameHome() >= 6 && getPointOfGameAway() >= 6) && (isDuce())) {
								printPoint(win, String.valueOf(point));
								printLine(DUCE);
							} else if ((getPointOfGameHome() > 6 || getPointOfGameAway() > 6)
									&& (Math.abs(getPointOfGameHome() - getPointOfGameAway()) >= 2)) {
								setGameWiner(win);
								break;
							} else {
								printPoint(win, String.valueOf(point));
							}
						}
					}
					processScoreBoard();
				} else {

				}
			}
			return getResult();
		}

		private void setGameWiner(String player) throws Exception {
			printPoint(player, WIN);
			printLine(BANK_STRING);
			addGameToPlayer(player);
		}

		private void processScoreBoard() {
			if (getGameOfSetHome() >= 6 && getGameOfSetAway() >= 6) {
				this.tieBreakFlag = true;
			}

		}

		private void printLine(String str) {
			this.result.add(str);
		}

		private boolean isDuce() {
			return getPointOfGameHome() == getPointOfGameAway();
		}

		private void printPoint(String player, String score) {
			this.result.add(player + ": " + score);
		}

		private int addPointPlayerHome() {
			return ++this.pointOfGameHome;
		}

		private int addPointPlayerAway() {
			return ++this.pointOfGameAway;
		}

		private void addGamePlayerHome() {
			this.gameOfSetHome++;
		}

		private void addGamePlayerAway() {
			this.gameOfSetAway++;
		}

		private int addPointToPlayer(String win) throws Exception {
			int point = 0;
			if (win != null) {
				if (win.equals(this.playerHome)) {
					point = addPointPlayerHome();
				} else if (win.equals(this.playerAway)) {
					point = addPointPlayerAway();
				} else {

				}
			} else {

			}
			return point;
		}

		private void addGameToPlayer(String win) throws Exception {
			if (win != null) {
				if (win.equals(this.playerHome)) {
					addGamePlayerHome();
				} else if (win.equals(this.playerAway)) {
					addGamePlayerAway();
				} else {

				}
			} else {

			}
		}

		private int getPointOfGameHome() {
			return pointOfGameHome;
		}

		private int getPointOfGameAway() {
			return pointOfGameAway;
		}

		private boolean isTieBreakFlag() {
			return tieBreakFlag;
		}

		private List<String> getResult() {
			return result;
		}

		private int getGameOfSetHome() {
			return gameOfSetHome;
		}

		private int getGameOfSetAway() {
			return gameOfSetAway;
		}

	}

}