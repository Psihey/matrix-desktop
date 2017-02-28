package ua.softgroup.matrix.desktop.controllerjavafx;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import ua.softgroup.matrix.desktop.currentsessioninfo.CurrentSessionInfo;
import ua.softgroup.matrix.desktop.sessionmanagers.ReportServerSessionManager;
import ua.softgroup.matrix.server.desktop.model.ProjectModel;
import ua.softgroup.matrix.server.desktop.model.ReportModel;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Set;

/**
 * @author Andrii Bei <sg.andriy2@gmail.com>
 */
public class ProjectsLayoutController {

    @FXML
    public TableView<ProjectModel> tvProjectsTable;
    @FXML
    public TableColumn<ProjectModel, Long> tcIdProject;
    @FXML
    public TableColumn<ProjectModel, String> tcAuthorName;
    @FXML
    public TableColumn<ProjectModel, String> tcTitle;
    @FXML
    public TableColumn<ProjectModel, String> tcDescription;
    @FXML
    public TableColumn tcStatus;
    @FXML
    public PieChart missPieCharts;
    @FXML
    public Label labelDayInWord;
    @FXML
    public Label labelDayInNumber;
    @FXML
    public Label labelNameProject;
    @FXML
    public Label labelDiscribeProject;
    @FXML
    public Label labelStartWorkToday;
    @FXML
    public Label labelTodayTotalTime;
    @FXML
    public Label labelTotalTime;
    @FXML
    public Label labelDateStartProject;
    @FXML
    public Label labelDeadLineProject;
    @FXML
    public Button btnStart;
    @FXML
    public Button btnStop;
    @FXML
    public TextArea taWriteReport;
    @FXML
    public Button btnAttachFile;
    @FXML
    public Button btnSendReport;
    @FXML
    public Label labelSymbolsNeedsToReport;
    @FXML
    public Label labelCurrentSymbols;
    static ObservableList<ProjectModel> projectsData = FXCollections.observableArrayList();
    private static DateTimeFormatter dateFormatNumber = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static DateTimeFormatter dateFormatText = DateTimeFormatter.ofPattern("EEEE", Locale.ENGLISH);
    private static final String ID_COLUMN = "id";
    private static final String AUTHOR_NAME_COLUMN = "authorName";
    private static final String TITLE_COLUMN = "title";
    private static final String DESCRIPTION_COLUMN = "description";
    private static final int LIMITER_TEXT_COUNT = 999;
    private static final int MIN_TEXT_FOR_REPORT = 70;
    private ReportServerSessionManager reportServerSessionManager;

    /**
     * After Load/Parsing fxml call this method
     * Create {@link ReportServerSessionManager}
     *
     * @throws IOException
     */
    @FXML
    private void initialize() throws IOException {
        reportServerSessionManager = new ReportServerSessionManager();
        initPieChart();
        initProjectInTable();
        getTodayDayAndSetInView();
        setFocusOnTableView();
        countTextAndSetInView();
        addTextLimiter(taWriteReport, LIMITER_TEXT_COUNT);
    }

    /**
     * Hears when text input in TextArea and if this text count >= {@value MIN_TEXT_FOR_REPORT}
     * button for send report became active
     */
    @FXML
    private void countTextAndSetInView() {
        taWriteReport.textProperty().addListener((observable, oldValue, newValue) -> {
            int size = newValue.length();
            labelCurrentSymbols.setText(String.valueOf(size));
            if (size >= MIN_TEXT_FOR_REPORT) {
                btnSendReport.setDisable(false);
            } else btnSendReport.setDisable(true);
        });
    }

    /**
     * At start project window select last item in Set of user active project{@link CurrentSessionInfo}
     *
     * @throws IOException
     */
    private void setFocusOnTableView() throws IOException {
        tvProjectsTable.requestFocus();
        tvProjectsTable.getSelectionModel().select(CurrentSessionInfo.getUserActiveProjects().size() - 1);
        tvProjectsTable.getFocusModel().focus(CurrentSessionInfo.getUserActiveProjects().size() - 1);
        ProjectModel projectModel = tvProjectsTable.getSelectionModel().getSelectedItem();
        setOtherProjectInfoInView(projectModel);
        new Thread(() -> {
            try {
                setReportInfoInTextAreaAndButton(projectModel);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * Get system current date and display this data in label view
     */
    private void getTodayDayAndSetInView() {
        LocalDate date = LocalDate.now();
        String dayOfWeekText = date.format(dateFormatText);
        String dayOfWeekNumber = date.format(dateFormatNumber);
        labelDayInWord.setText(dayOfWeekText);
        labelDayInNumber.setText(dayOfWeekNumber);
    }

    /**
     * Set connect table column with {@link ProjectModel} for future pull date in this field
     */
    private void initProjectInTable() {
        tcIdProject.setCellValueFactory(new PropertyValueFactory<>(ID_COLUMN));
        tcAuthorName.setCellValueFactory(new PropertyValueFactory<>(AUTHOR_NAME_COLUMN));
        tcTitle.setCellValueFactory(new PropertyValueFactory<>(TITLE_COLUMN));
        tcDescription.setCellValueFactory(new PropertyValueFactory<>(DESCRIPTION_COLUMN));
        setProjectInTable();
    }

    /**
     * Get in{@link CurrentSessionInfo} Set of  all active project and set their in table view
     */
    private void setProjectInTable() {
        Set<ProjectModel> projectModelSet = CurrentSessionInfo.getUserActiveProjects();
        if (projectModelSet != null && !projectModelSet.isEmpty()) {
            projectModelSet.forEach(projectsData::add);
//        for (ProjectModel projectModel : projectModelSet) {
//            projectsData.add(projectModel);
//        }
            tvProjectsTable.setItems(projectsData);
        }
    }

    /**
     * Get from current project information's and set their in label view element
     *
     * @param projectModel current project what user choose in table view
     */
    private void setOtherProjectInfoInView(ProjectModel projectModel) {
        CurrentSessionInfo.setProjectId(projectModel.getId());
        labelNameProject.setText(projectModel.getTitle());
        labelDiscribeProject.setText(projectModel.getDescription());
        if ((projectModel.getStartDate() != null && projectModel.getEndDate() != null)) {
            labelDateStartProject.setText(projectModel.getStartDate().format(dateFormatNumber));
            labelDeadLineProject.setText(projectModel.getEndDate().format(dateFormatNumber));
        } else {
            labelDateStartProject.setText("Unknown");
            labelDeadLineProject.setText("Unknown");
        }
    }

    /**
     * Get DownTime and CleanTime and set this information in Pie Chart
     */
    private void initPieChart() {
        ObservableList<PieChart.Data> pieChartList = FXCollections.observableArrayList(new PieChart.Data("Down Time", 7),
                new PieChart.Data("Clean Time", 93));
        missPieCharts.setData(pieChartList);
    }

    /**
     * Hears when user click on table view select project and set TextArea Editable
     *
     * @param event callback click on table view
     * @throws IOException
     */
    public void chosenProject(Event event) throws IOException {
        openReportWindowOnTwoMouseClick(event);
        taWriteReport.setText("");
        taWriteReport.setEditable(true);
        if (tvProjectsTable.getSelectionModel().getSelectedItem() != null) {
            ProjectModel selectProject = tvProjectsTable.getSelectionModel().getSelectedItem();
            setReportInfoInTextAreaAndButton(selectProject);
            setOtherProjectInfoInView(selectProject);
        }
    }

    /**
     * Hears fast two click on table view and open report window on what project user click
     *
     * @param event callback click on table view
     */
    private void openReportWindowOnTwoMouseClick(Event event) {
        MouseEvent mouseEvent = (MouseEvent) event;
        if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
            if (mouseEvent.getClickCount() == 2) {
                MainLayoutController main = new MainLayoutController();
                main.startReport(labelCurrentSymbols.getScene().getWindow());
            }
        }
    }

    /**
     * Gets all reports for chosen project and if today user already saved report
     * displays this information in TextArea
     *
     * @param projectModel current project what user choose in table view
     * @throws IOException
     */
    private void setReportInfoInTextAreaAndButton(ProjectModel projectModel) throws IOException {
        Set<ReportModel> reportModel = null;
        reportModel = reportServerSessionManager.sendProjectDataAndGetReportById(projectModel.getId());
        for (ReportModel model :
                reportModel) {
            if (model.getDate().equals(LocalDate.now())) {
                taWriteReport.setText("You have already saved a report today");
                btnSendReport.setDisable(true);
                taWriteReport.setEditable(false);
            }
        }
    }

    /**
     * Hears when user click on button and send information about report to {@link ReportServerSessionManager}
     *
     * @param actionEvent callback click on button
     * @throws IOException
     */
    public void saveReport(ActionEvent actionEvent) throws IOException {
        ReportModel reportModel = new ReportModel(CurrentSessionInfo.getTokenModel().getToken(), taWriteReport.getText(), CurrentSessionInfo.getProjectId(), LocalDate.now());
        reportModel.setTitle("kaban gay");
        reportServerSessionManager.saveReportToServer(reportModel);
        btnSendReport.setDisable(true);
        taWriteReport.setEditable(false);
    }

    /**
     * Limit of amount on entry text
     *
     * @param ta  TextField in what input text
     * @param maxLength int number of max text amount
     */
    public static void addTextLimiter(final TextArea ta, final int maxLength) {
        ta.textProperty().addListener((ov, oldValue, newValue) -> {
            if (ta.getText().length() > maxLength) {
                String s = ta.getText().substring(0, maxLength);
                ta.setText(s);
            }
        });
    }

}
