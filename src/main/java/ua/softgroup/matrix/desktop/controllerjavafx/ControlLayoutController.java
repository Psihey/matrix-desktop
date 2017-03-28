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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;


/**
 * @author Andrii Bei <sg.andriy2@gmail.com>
 */
public class ControlLayoutController implements Initializable {
    private static final String BASE_URL = "http://127.0.0.1:8094/api/v2/";
    @FXML
    public ListView listViewReportDetails;
    @FXML
    public ListView<UserProfile> listViewAllUsers;
    @FXML
    public DatePicker calendarFromDate;
    @FXML
    public DatePicker calendarToDate;
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

    public void show(Event event) {
        requestControls.clear();
        controlList.clear();
        getDataFromControlPanel();
        if(getDataFromControlPanel()!=null){
            for (ReportControlModel reportControlModel : getDataFromControlPanel()) {
                for (DayJson dayJson : reportControlModel.getWorkDays()) {
                        requestControls.add(new RequestControl(dayJson.getId(), dayJson.getDate(), dayJson.getStart(), dayJson.getEnd(), dayJson.getWorkSeconds(), dayJson.getIdleSeconds(),
                                dayJson.getIdlePercentage(), dayJson.isChecked(), dayJson.getCheckerId(), dayJson.getCoefficient(), dayJson.getReportText(), dayJson.getRate(), dayJson.getCurrencyId(),dayJson.getWorkPeriods()));

                }
            }
        }
        if(requestControls!=null&& !requestControls.isEmpty()){
            for (RequestControl requstControl : requestControls) {
                requstControl.setReportText(requstControl.getReportText()+"\n"+requstControl.getWorkPeriod());
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

}