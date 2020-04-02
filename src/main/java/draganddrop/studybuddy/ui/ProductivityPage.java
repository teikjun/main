package draganddrop.studybuddy.ui;

import java.util.List;
import java.util.logging.Logger;

import draganddrop.studybuddy.commons.core.LogsCenter;
import draganddrop.studybuddy.model.statistics.Statistics;
import draganddrop.studybuddy.model.statistics.StatsUtil;
import draganddrop.studybuddy.model.task.Task;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * Productivity page that contains usage statistics
 */
public class ProductivityPage extends UiPart<Region> {
    private static final String FXML = "ProductivityPage.fxml";
    private static Statistics statistics;

    private final Logger logger = LogsCenter.getLogger(ProductivityPage.class);
    private ObservableList<Task> taskList;

    //daily
    @FXML
    private Label dailyStatsLabel;
    @FXML
    private Label streakLabel;

    //weekly
    @FXML
    private BarChart<String, Integer> weeklyBarChart;
    @FXML
    private Label weeklyStatsLabel;

    //score
    @FXML
    private Label ppLabel;
    @FXML
    private Label rankLabel;
    @FXML
    private Label nextRankLabel;

    public ProductivityPage(ObservableList<Task> taskList) {
        super(FXML);
        this.taskList = taskList;
        generateProductivityPage();
        taskList.addListener(new ListChangeListener<Task>() {
            @Override
            public void onChanged(Change<? extends Task> t) {
                generateProductivityPage();
            }
        });
    }

    public static void setStatistics(Statistics statistics) {
        ProductivityPage.statistics = statistics;
    }

    /**
     * generates the productivity page
     */
    public void generateProductivityPage() {
        renderDailyTab();
        renderWeeklyTab();
        renderScoreTab();
    }

    /**
     * renders the daily tab
     */
    public void renderDailyTab() {
        renderDailyStats();
        renderStreak();
    }

    /**
     * renders the weekly tab
     */
    public void renderWeeklyTab() {
        renderWeeklyStats();
        renderWeeklyBarChart();
    }


    /**
     * renders the score tab
     */
    public void renderScoreTab() {
        renderScore();
        renderRank();
        renderNextRank();
    }

    // daily

    /**
     * renders daily stats
     */
    public void renderDailyStats() {
        int taskCompletedCount = statistics.getCompleteCountToday();
        int goal = statistics.getGoal();
        String completedString = taskCompletedCount + " / " + goal + " tasks\n";

        dailyStatsLabel.setText(completedString);
    }

    /**
     * renders user's streak
     */
    public void renderStreak() {
        int streak = statistics.getStreak();
        String streakString = "You've completed your goal " + streak + " days in a row";

        streakLabel.setText(streakString);
    }

    //weekly

    /**
     * renders weekly stats
     */
    public void renderWeeklyStats() {
        int weeklyTaskCompleted = statistics.getCompleteCountThisWeek();
        int weeklyTaskOverdue = statistics.getOverdueCountThisWeek();
        String weeklyStatsString = "You have completed " + weeklyTaskCompleted + " tasks this week\n"
            + weeklyTaskOverdue + " tasks were overdue";
        weeklyStatsLabel.setText(weeklyStatsString);
    }

    /**
     * renders the weekly bar chart
     */
    public void renderWeeklyBarChart() {
        List<String> dayList = StatsUtil.getDayList();
        List<Integer> weeklyCompleteCountList = statistics.getWeeklyCompleteCountList();
        if (!weeklyBarChart.getData().isEmpty()) {
            weeklyBarChart.getData().clear();
        }

        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        for (int i = 0; i < 7; i++) {
            XYChart.Data<String, Integer> data = new XYChart.Data<>(dayList.get(i), weeklyCompleteCountList.get(i));
            series.getData().add(data);
        }
        weeklyBarChart.getData().add(series);
    }

    // score

    /**
     * renders score
     */
    public void renderScore() {
        int score = statistics.getScoreToday();
        String scoreString = score + " PP";
        ppLabel.setText(scoreString);
    }

    /**
     * renders the user's rank
     */
    public void renderRank() {
        rankLabel.setText(statistics.getRank());
    }

    /**
     * renders the user's next rank
     */
    public void renderNextRank() {
        nextRankLabel.setText(statistics.getNextRank());
    }
}
