package ua.softgroup.matrix.desktop.controllerjavafx;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import ua.softgroup.matrix.desktop.api.ControlAPI;
import ua.softgroup.matrix.desktop.model.DayJson;
import ua.softgroup.matrix.desktop.model.ReportControlModel;
import ua.softgroup.matrix.desktop.model.UserProfile;
import ua.softgroup.matrix.desktop.model.WorkPeriod;
import ua.softgroup.matrix.desktop.model.localModel.RequestControl;

import javax.jws.soap.SOAPBinding;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


/**
 * @author Andrii Bei <sg.andriy2@gmail.com>
 */
public class ControlLayoutController implements Initializable {
    @FXML
    public ListView listViewReportDetails;
    @FXML
    public ListView<UserProfile> listViewAllUsers;
    @FXML
    public DatePicker calendarFromDate;
    @FXML
    public DatePicker calendarToDate;
    private static final String BASE_URL = "http://127.0.0.1:8094/api/v2/";
    private static final String REPORT_TEXT="Report text:";
    private static final String WORK_PERIOD="Work period:";
    private ObservableList<RequestControl> controlList = FXCollections.observableArrayList();
    private ObservableList<UserProfile> usersList = FXCollections.observableArrayList();
    private List<RequestControl> requestControls = new ArrayList<>();
    private ControlAPI controlApi;
    private UserProfile userProfile;
    private static final Logger logger = LoggerFactory.getLogger(ControlLayoutController.class);

    @SuppressWarnings("unchecked")
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createRetrofit();
        initializeAllUsers();
        setValueInDatePicker();

    }

    private void setValueInDatePicker() {
        calendarFromDate.setValue(LocalDate.now().minusMonths(1));
        calendarToDate.setValue(LocalDate.now());
    }

    private void initializeAllUsers() {
        getAllUsers();
        if (getAllUsers()!=null){
            for (UserProfile users:getAllUsers()) {
                usersList.add(users);
            }
        }
        listViewAllUsers.setItems(usersList);
    }

    private void createRetrofit() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(new PropertyNamingStrategy.PascalCaseStrategy());
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(JacksonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        controlApi = retrofit.create(ControlAPI.class);
    }

    private List<UserProfile> getAllUsers() {
        Call<List<UserProfile>> responseUserProfile = controlApi.loadAllUsers();
        try {
            return responseUserProfile.execute().body();
        } catch (IOException e) {
            logger.debug("Error at response All Users" + e);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public void show(Event event) {
        requestControls.clear();
        controlList.clear();
        getDataFromControlPanel();
        if(getDataFromControlPanel()!=null){
            for (ReportControlModel reportControlModel : getDataFromControlPanel()) {
                for (DayJson dayJson : reportControlModel.getWorkDays()) {
                        requestControls.add(new RequestControl(reportControlModel.getTotalWorkSeconds()/60,reportControlModel.getTotalIdleSeconds()/60,Math.round(reportControlModel.getTotalIdlePercentage()),
                                dayJson.getId(), dayJson.getDate(), dayJson.getStart(), dayJson.getEnd(), dayJson.getWorkSeconds()/60, dayJson.getIdleSeconds()/60,Math.round(dayJson.getIdlePercentage())
                               , dayJson.isChecked(), dayJson.getCheckerId(), dayJson.getCoefficient(), dayJson.getReportText(), dayJson.getRate(),
                                dayJson.getCurrencyId(), dayJson.getWorkPeriods()));
                }
            }
        }
        if(requestControls!=null&& !requestControls.isEmpty()){
            for (RequestControl requstControl : requestControls) {
                TreeSet<WorkPeriod> workPeriod= new TreeSet<>(this::compare);
                for (WorkPeriod work:requstControl.getWorkPeriod()) {

                    workPeriod.add(work);
                }
                if (requstControl.getReportText()!=null&&!requstControl.getReportText().isEmpty()){
                    requstControl.setReportText(REPORT_TEXT+"\n"+requstControl.getReportText()+"\n"+"\n"+WORK_PERIOD+"\n"+workPeriod);
                }else  requstControl.setReportText(WORK_PERIOD+"\n"+workPeriod);
                controlList.add(requstControl);
            }
        }

        listViewReportDetails.setItems(controlList);
        listViewReportDetails.setCellFactory(new Callback<ListView<RequestControl>, ListCell<RequestControl>>() {
            @Override
            public ListCell<RequestControl> call(ListView<RequestControl> param) {
                return new ControlListViewCell();
            }
        });
    }

    private List<ReportControlModel> getDataFromControlPanel() {
        if ( userProfile!=null&&userProfile.getId()!=null&&calendarFromDate.getValue()!=null&&calendarToDate.getValue()!=null){
            Call<List<ReportControlModel>> call = controlApi.loadSummaryByUser(userProfile.getId(), calendarFromDate.getValue().toString(), calendarToDate.getValue().toString());
            try {
                return call.execute().body();
            } catch (IOException e) {
                logger.debug("Error at response Report Control Model" + e);
            }
        }
      return null;
    }

    public void chooseUser(Event event) {
        if (listViewAllUsers.getSelectionModel().getSelectedItem()!=null){
            userProfile = listViewAllUsers.getSelectionModel().getSelectedItem();
        }
    }


    public int compare(WorkPeriod o1, WorkPeriod o2) {
        DateTimeFormatter todayStartTime = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime ac;
        LocalTime lim;
            System.out.println(o1.getStart());
            System.out.println(o2.getStart());
                ac = LocalTime.from(todayStartTime.parse(o1.getStart()));
                System.out.println(ac);
                lim=LocalTime.from(todayStartTime.parse(o2.getStart()));
                System.out.println(lim);
                if(ac.isAfter(lim)){
                    return 1;
                }
           return -1;
    }
}
