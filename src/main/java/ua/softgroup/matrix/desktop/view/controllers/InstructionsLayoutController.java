package ua.softgroup.matrix.desktop.view.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import ua.softgroup.matrix.api.model.datamodels.InstructionsModel;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Andrii Bei <sg.andriy2@gmail.com>
 */
public class InstructionsLayoutController {
    @FXML
    public Label labelInstructions;
    @FXML
    public ListView<InstructionsModel> lvInstructions;
    private List<InstructionsModel> listInstructionsModel = new ArrayList<>();

    /**
     * After Load/Parsing fxml call this method
     */
    @FXML
    public void initialize() {
        initArray();
        setFocusOnListView();
    }

    /**
     * Hears when user click on listView select item ,get data from ObservableList and set in label
     *
     * @param event callback click on list item
     */
    public void clickOnCurrentInstruction(Event event) {
        chooseCurrentInstruction();

    }

    private void setFocusOnListView() {
        lvInstructions.requestFocus();
        lvInstructions.getSelectionModel().select(0);
        lvInstructions.getFocusModel().focus(0);
        chooseCurrentInstruction();
    }

    private void chooseCurrentInstruction(){
        if (lvInstructions.getSelectionModel().getSelectedItem()!=null){
            InstructionsModel selectProject = lvInstructions.getSelectionModel().getSelectedItem();
            labelInstructions.setText(selectProject.getContent());
        }
    }

    /**
     * Init {@link InstructionsModel} data into arrayList then set this data in Observable list,
     * and display in ListView
     */
    private void initArray() {
        listInstructionsModel.add(new InstructionsModel("Пересчет ЗП", "А теперь серьезно :)\n" +
                "Многие могли заметить, что ЗП поменялась в меньшую сторону на пару копеек в час. Связано это с тем, что до этого было не правильно посчитано. " +
                "Было посчитано количество рабочих дней в этом году 250, а правильно надо было 251. Т.е.вы пол года получали ЗП чуть больше, чем надо :)\n" +
                "\n" +
                "Chance"));
        listInstructionsModel.add(new InstructionsModel("Новая методика подсчета зп. Matrix ERP", "\n" +
                "-----\n" +
                "\n" +
                "Случилось обновление программы Matrix до версии Matrix ERP  ( http://ru.wikipedia.org/wiki/ERP )\n" +
                "\n" +
                "\n" +
                "как было : \n" +
                "\n" +
                "каждый сотрудник в конце дня записывал количество проработанного времени по тому или иному заданию и писал отчет и каждый месяц получал зп. \n" +
                "\n" +
                "\n" +
                "\n" +
                "как стало : \n" +
                "\n" +
                "1. На против каждого задания появилась кнопка старт \\ стоп. \n" +
                "\n" +
                "При выполнении задания - вы нажимаете старт , при завершении   - стоп , или просто старт другого задания . Программа автоматически считает время по каждому заданию и привязывает к нему всю информацию по этому периоду из мониторинга, для контроля. Итого - когда что то делаете - всегда нажата кнопка старт , отошли на обед - нажали кнопку стоп\n" +
                "\n" +
                "\n" +
                "2. Ваше рабочее время считается путем умножения проработанных часов в матриксе на часовой рейт (для каждого свой)\n" +
                "\n" +
                "\n" +
                "3. Если сотрудник опаздывает на работу \\ это видно по тому когда нажалась кнопка старт \\ - коеффициент оплаты в тот день равен 0.90\n" +
                "\n" +
                "\n" +
                "4. Если сотрудник не пишет отчет расширенный \\  коеффициент оплаты за тот период - день - по которому не было отчёта  равен 0.85\n" +
                "\n" +
                "\n" +
                "5. Если сотрудник не укладывается в деадлайн - коеффициент оплаты по всему заданию за этот период  0.80 \n" +
                "\n" +
                "6. \\удалено\\\n" +
                "\n" +
                "7. Контролёры , а их минимум два - ваш руководитель отдела и главный контролер ,  так же выставляют коеффициенты еффективности. То есть если например простое задание делалось неадекватно долго , например вместо одного часа - два часа - то коеффициент выставится 0.5 , соовтетственно вам будет оплачено не два часа а один час работы\n" +
                "\n" +
                "8. Важно - по каждому заданию контролеры смотрят мониторинг и сверяют  ваш отчет - и время потраченное на задание - и что по мониторингу делалось в это время  \n" +
                "\n" +
                "Оспаривание этих коеффициентов - только по вашим письменным заявлениям\n" +
                "\n" +
                "Все коеффициенты перемножаются\n" +
                "\n" +
                "\n" +
                "9. Включение модуля мониторинга - обязательное для всех\n" +
                "\n" +
                "10. Вашу зп - там с опозданием сутки - вы можете видеть ТОЛЬКО ПО ВКЛАДКЕ учет -> часы . Зп выдаваться будет только по этой вкладке.\n" +
                "\n" +
                "Если вы забывали нажимать кнопку старт,  все исправления в бугалтерию по вас - только через ваши письменные заявления.\n" +
                "\n" +
                "11. Важно - будет расширенная инструкция так же что и какая вкладка значит- в инструкциях по матриксу.\n" +
                "\n" +
                "12. Передайте так же всем вашим сотрудникам что б они прочитали это уведомление\n" +
                "\n" +
                "------------------\n" +
                "\n" +
                "Важно - система будет вводится в ближайшие дни , неделя будет дана что б все привыкли и поняли что к чему, Вам будет звонить Андрей Шевченко -ответственный за внедрение этой программы, или вы ему - что б задавать вопросы , его номер 0966656594"));
        listInstructionsModel.add(new InstructionsModel("Краткое руководство по программе", "ПОЖАЛУЙСТА, ОТНЕСИТЕСЬ СПОКОЙНО К НЕТОЧНОСТЯМ  В ПОКАЗАТЕЛЯХ МОНИТОРИНГА. ПРОГРАММА ПРОХОДИТ ТЕСТИРОВАНИЕ (c).\n" +
                "\n" +
                "\n" +
                "Дополнение по обновленной программе - в конце инструкции\n" +
                "\n" +
                "Интерфейс главного окна программы\n" +
                "\n" +
                "=================================\n" +
                "\n" +
                "Главное меню\n" +
                "\n" +
                "---------------------------------\n" +
                "\n" +
                "Добавить задание - открыть окно задания для добавления\n" +
                "Инструкции - инструкции по программе / роботе\n" +
                "Рабочее время - старт / стоп рабочего времени\n" +
                "What's new - список изменений в последних версиях, форма обратной связи\n" +
                "\n" +
                "=================================\n" +
                "\n" +
                "Вкладки\n" +
                "\n" +
                "---------------------------------\n" +
                "\n" +
                "Вкладка Текущие задания - отображает списки текущих заданий; колонки:\n" +
                "\n" +
                "1) Приоритет задания (приоритеты в порядке воздастания: серые, зеленые, желтые, красные); иконка \"ролей\" задания (карандаш - задание к исполнению; листок - требуется прикрепить файлы к заданию; ЛУПА - требуется проконтролировать выполнение задания; стрелка - требуется принять файлы / информацию по заданию) \n" +
                "2) ИД задания\n" +
                "3) Категория - категория задания\n" +
                "4) Задача - задача задания\n" +
                "5) Краткое описание - краткое описание задания (первые ~50 символов из текста по заданию)\n" +
                "6) Выдано - дата / время выдачи задания\n" +
                "7) Готово - % готовности задания (пользователя программы)\n" +
                "\n" +
                "Формат строк в таблице:\n" +
                "черный жирный - новое задание\n" +
                "черный - активное задание\n" +
                "серый - задание еще не выполнено исполнителями предыдущих \"ролей\"\n" +
                "\n" +
                "Верхний список (\"К исполнению\") - список заданий к исполнению непосредственно.\n" +
                "Нижний список (\"Разное\") - список заданий по контролю / получению файлов и т.п. (\"роль\" в задании отображена в иконке)\n" +
                "\n" +
                "---------------------------------\n" +
                "\n" +
                "Вкладка \"Завершенные\" - аналог вкладки \"Текущие задания\" для завершенных заданий\n" +
                "\n" +
                "---------------------------------\n" +
                "\n" +
                "Вкладка \"Архив\" - аналог вкладки \"Текущие задания\" для архивных заданий (архивные задания изменять нельзя)\n" +
                "\n" +
                "---------------------------------\n" +
                "\n" +
                "Настройка - вкладка с настройками программы\n" +
                "\n" +
                "Вкладка \"Основные\"\n" +
                "* Папка сохранения файлов по умолчанию - папка для сохранения файлов, полученных по заданию / от сотрудников.\n" +
                "* Прервать скачку / загрузку файлов - при зависаниях при передаче файлов может помочь, но проще перезапустить программу\n" +
                "* Группа \"Подтверждать\" - включить / выключить подтверждение указанных действий\n" +
                "* Группа \"Всплывающее окно при\" - включение / выключения всплывающего окна при выполнеии условия из группы\n" +
                "* Сворачивать при закрытии - сворачивать программу вместо закрытия (закрывать через меню иконки в трее)\n" +
                "* Группа \"При изменении разметов окна задания\" - выбор способа изменения размера текстовых полей в окне задания\n" +
                "* Группа \"Размеры, сортировка etc\" - запоминать размеры главного окна / колонок и т.п.\n" +
                "* Группа \"Разное\"\n" +
                "** Вести лог - сохранять лог работы программы в файл (см. папку log)\n" +
                "** При завершении задания ставить 100% готовности - вроде понятно\n" +
                "** При завершении задания добавлять пустой отчет за текущий день (если еще нет) - не актуально\n" +
                "** Предупреждать о закрытии задания с несохраненным отчетом - ...\n" +
                "** Не синхронизировать файлы при изменении задания или описания категрии / задачи - при изменении заданий измененные файлы обновляются на сервере; для отмены автоматического обновления измененных файлов уставить данную опцию\n" +
                "** Предлагать начать рабочее время при запуске - ...\n" +
                "** Предлагать завершить рабочее время при закрытии - ...\n" +
                "** Подтверждать окончание рабочего времени - ...\n" +
                "** Отображать время работы в заголовке и подсказке (нужен перезапуск) - ...\n" +
                "** Разворачивать окна задания при окрытии - ...\n" +
                "\n" +
                "Вкладка \"Вкладки сотрудников и проектов\"\n" +
                "* Группа \"Стартовая  вкладка\" - выбор начальной вкладки при запуске программы\n" +
                "* Группа \"Таблицы\" - установка размеров колонок в таблицах, кол-ство файлов для отображения в ячейках\n" +
                "* Группа \"Вкладки заданий сотрудников\" - типы вкладок для отображения и т.п.\n" +
                "\n" +
                "Вкладка \"Разное\"\n" +
                "* Группа \"Количество отработанных часов\" - просмотр / подсчет часов по отчетам / времени начала-окончании работы\n" +
                "* Группа \"Логин и пароль\":\n" +
                "** Запомнить логин / пароль - установить/снять для запоминания логина/пароля.\n" +
                "** Изменить пароль - изменение пароля текущего пользователя.\n" +
                "=================================\n" +
                "\n" +
                "Интерфейс окна заданий\n" +
                "\n" +
                "=================================\n" +
                "\n" +
                "Верхняя панель:\n" +
                "\n" +
                "* Мой приоритет - приоритет задания\n" +
                "* Готово, % - % готовности для заданий к исполнению (изменять по ходу выполнения задания)\n" +
                "* Описание категории / задачи / группы - описания категории / задачи / группы выбранного задания, могут быть пустыми.\n" +
                "* Выдано / Архивировано - даты / времена выдачи / архивации задания\n" +
                "* Статус - список участников задания: иконка роли, имя, % готовности (для исполнителей), статус выполнения (\"...\" - участник ожидает выполнения задания на предыдущем этапе; \"?\" - новое задание; \"-\" - задание выполняется; \"+\" - задание выполнено)\n" +
                "* Нужно времени: время, необходимое для выполнения задания (можно менять)\n" +
                "* Список исполнителей - список исполнителей задания, можно менять (давать / передавать / делиться и т.п. заданием)\n" +
                "* Списки категорий / задач (видны при добавлении задания) - выбор категории / задачи для добавляемого задания\n" +
                "\n" +
                "Средняя панель - панель отчетов по заданию:\n" +
                "\n" +
                "* Список уже созданных отчетов; недавние отчеты можно изменять\n" +
                "* Поле текст отчета\n" +
                "* Дата отчета\n" +
                "* чч:мм - время, потраченное на выполнение задания; если время не указано – сбросится в 00:00\n" +
                "* Добавить / Изменить - Добавить отчет за указанную дату / Изменить выбранный отчет\n" +
                "* Добавить файл - добавление файла от сотрудников\n" +
                "Нижняя панель - тексты и файлы по заданию:\n" +
                "\n" +
                "* Текст. сообщение по заданию - текст. сообщение по заданию\n" +
                "* Текст. сообщение от сотрудников - текст. сообщения от участников задания (изменяется по ходу выполнения)\n" +
                "* Файлы по заданию / Файлы от сотрудников - файлы; при добавлении файлов выбирать более подходящий по смыслу список\n" +
                "* Кнопки управления файлами (см. подсказки)\n" +
                "\n" +
                "Кнопки:\n" +
                "* Добавить - добавление нового задания\n" +
                "* Изменить - изменить текст. сообщение / файлы / % готовности  задания / приоритет / исполнителей / время задания\n" +
                "* Готово / Проверено / Получено - завершение своего задания (отметить задание как выполненное)\n" +
                "* Доработать - возврат задания себе на доработку\n" +
                "* Повторно - возврат задания на доработку предыдущим \"ролям\" (если такие есть)\n" +
                "* В архив - \"архивация\" задания (перенос на вкладку \"Архив\" в невозможностью дальнейшего изменения задания)\n" +
                "* Из архива (для архивных заданий) - возврат задания из архива (если возможно)\n" +
                "* ОК - аналог кнопки \"Добавить\" (если активна) / \"Изменить\" (если активна) / закрытие окна (для архивного задания)\n" +
                "* Отмена - закрыть текущее окно задания без сохранения изменений\n" +
                "\n" +
                "=================================\n" +
                "\n" +
                "Разное:\n" +
                "\n" +
                "* задания в списке можно сортировать кликом по колонке (желательно придерживаться сортировки по приоритету)\n" +
                "* завершенные задания можно изменять (текст. сообщение / файлы)\n" +
                "* описания задач / категорий можно изменять (если были указания)\n" +
                "* Виды \"ролей\" в задании в порядке исполнения и соответствующие иконки в списке заданий / списке участников в задании: \n" +
                "- листок - задание требует прикрепить файлы (\"информатор\" задания); встречается редко;\n" +
                "- карандаш - задание к исполнению (исполнитель задания);\n" +
                "- лупа - задание требует проконтролировать выполнение другими участниками (\"контролер\" задания)\n" +
                "- стрелка - задание требует принять файлы / информацию по заданию (\"получатель\" задания)\n" +
                "\n" +
                "==================================================================\n" +
                "==================================================================\n" +
                "==================================================================\n" +
                "==================================================================\n" +
                "==================================================================\n" +
                "\n" +
                "Дополнение по MatrixEmp 3+:\n" +
                "\n" +
                "=================================\n" +
                "\n" +
                "Вкладки\n" +
                "\n" +
                "---------------------------------\n" +
                "\n" +
                "Вкладка Сотрудники (если видна)\n" +
                "\n" +
                "Колонки сотрудников с текущими / выполненными заданиями:\n" +
                "\n" +
                "Заголовок - Имя сотрудника + сумма часов, необходимых на выполнение всех текущих заданий\n" +
                "\n" +
                "Формат ячейки текущих заданий:\n" +
                "[времени потрачено на задание]/[нужно времени на задание] [задача] / [категория] [текст по заданию]\n" +
                "Формат ячейки заданий в группах по датам:\n" +
                "[времени потрачено на задание в указанный день]/[нужно времени на задание] ...\n" +
                "\n" +
                "Ctrl+вверх / Ctrl+вниз - увеличение / уменьшение индивидуально приоритета сотрудника для выделенного задания\n" +
                "\n" +
                "Меню правой кнопкой на задании:\n" +
                "Редактировать - открыть окно задания\n" +
                "Удалить - удалить задание (можно выделить несколько)\n" +
                "Приоритет + / Приоритет - - повышение / понижение индивидуального приоритета\n" +
                "\n" +
                "Меню правой кнопкой на пустой ячейке:\n" +
                "Добавить - открыть окно добавления задания\n" +
                "\n" +
                "---------------------------------\n" +
                "\n" +
                "Вкладки проектов (если есть)\n" +
                "\n" +
                "Сверху вниз - задачи (если есть), слева направо - категории\n" +
                "\n" +
                "Меню правой кнопкой на задании:\n" +
                "Добавить - открыть окно добавления задания в выбранные (можно несколько) ячейки\n" +
                "Редактировать - открыть окно задания (если выделено)\n" +
                "Удалить - удалить задание (можно выделить несколько)\n" +
                "\n" +
                "Даблклик по категории / задаче - открыть окно с описанием\n" +
                "\n" +
                "В ячейках вкладок сотрудников / проектов отображаются файлы от сотрудников (первые X штук); правый клик по иконке файла - скачать + открыть файл; левый клик - скачать все файлы + открыть папку с файлами\n" +
                "\n" +
                "=================================\n" +
                "\n" +
                "Окно задания для вкладок сотрудников / проектов\n" +
                "\n" +
                "Списки исполнителей/контролеров и т.д.\n" +
                "Общий приоритет задания (при создании задания определяет индивидуальные приоритеты)\n" +
                "Список участников / статусов выполнения и т.д. (можно менять индивидуальные приоритеты через меню правой кнопкой)\n" +
                "Список всех отчетов по заданию\n" +
                "Поля Текст. сообщений по заданию / от сотрудников\n" +
                "Поля Файлов по заданию / от сотрудников\n" +
                "Время исполнения - общее время выполнения задания для исполнителей (при установке делится на всех исполнителей)\n" +
                "\n" +
                "Кнопки\n" +
                "\n" +
                "Добавить - добавить задание (задания)\n" +
                "Изменить - сохранить внесенные изменения\n" +
                "Удалить - удалить задание\n" +
                "В архив - перенести задание в архив\n" +
                "Повторно - отправить задание на доработку всем участникам\n" +
                "\n" +
                "=================================\n" +
                "\n" +
                "Доп. настройки на вкладке настроек:\n" +
                "\n" +
                "Группа \"Всплывающее окно при\" - определение условий для всплывающего окна\n" +
                "\n" +
                "Стартовая вкладка - выбранная вкладка при запуске программы (Мои задания или Сотрудники)\n" +
                "\n" +
                "Группа \"Таблицы\" - определение ширины колонок в таблицах вкладок сотрудников и проектов; определение количества отображаемых файлов от сотрудников в ячейках таблиц\n" +
                "\n" +
                "Группа \"Вкладки заданий сотрудников\" - определение отображаемых субвкладок для вкладки сотрудников; определение диапазона дат для отображения последних заданий; определения обратного порядка сотрудников в таблицах\n" +
                "\n" +
                "==================================================================\n" +
                "==================================================================\n" +
                "==================================================================\n" +
                "\n" +
                "Дополнение по пункту меню \"Мониторинг\" (если доступен):\n" +
                "\n" +
                "==================================================================\n" +
                "\n" +
                "Запустить / Остановить - запуск / остановка мониторинга\n" +
                "При мониторинге логируются нажатия клавиш, учитывается время простоя и периодически снимаются скрншоты рабочего стола. При активном (включенном) мониторинге в трее добавляется иконка планеты (синяя). При неактивном мониторинге иконка серая или ее нет. Включить / выключить мониторинг можно так же через меню по иконке с планетой, либо двойным кликом по этой иконке.\n" +
                "\n" +
                "\n" +
                "\n" +
                "==================================================================\n" +
                "==================================================================\n" +
                "==================================================================\n" +
                "\n" +
                "Дополнение по обновленной программе (версии >=3.9)\n" +
                "\n" +
                "==================================================================\n" +
                "\n" +
                "Основные изменения:\n" +
                "\n" +
                "- старт-стоп времени заменен старт-стопом отдельных заданий (количество старт-стопов за день не ограничено, ограничение на ип-адрес снято), перед уходом на запланированное продолжительное время (обед и т.п) – время останавливать\n" +
                "\n" +
                "- старт-стоп доступен только для текущих + ожидаемых заданий (не завершенных), при необходимости \"запустить\" завершенное задание – задание восстановить до текущих, начать выполнение\n" +
                "\n" +
                "- отчеты добавляются автоматически при старте задания; при необходимости добавления отчета для задания – нажать старт (+ стоп), отчет добавится\n" +
                "\n" +
                "- отчет можно дописывать из главного окна, кликнув по ИД задания в списке\n" +
                "\n" +
                "- пункт \"Отработано\" в меню - вывод информации по времени онлайн, простою и т.п., обновляется (уточняется) раз в ~15 минут\n" +
                "\n" +
                "- колонки потрачено / всего – отображают время, потраченное на задания сегодня / за все время (время старых отчетов будет добавлено в пункт \"всего\" позже), уточняется также раз ~15 минут\n" +
                "\n" +
                "- вкладка история (после вкладки архива) - список выполненных на последние дни заданий\n" +
                "\n" +
                "- вкладка учет, вкладки:\n" +
                "  - отчеты кратко  - оценка потраченного времени на задания за период\n" +
                "  - отчеты расширено  (колонки: задание - отчет - данные мониторинга (доступны только проверяющим) - статус проверки отчета ) - просмотр статуса проверки / оценки своих отчетов\n" +
                "  - часы - разнообразная информация за период (перечислена в первой строчке)\n" +
                "\n" +
                "- поиск (снизу справа от \"Открыть задание #\") - поиск заданий по слову (в тексте, отчетах) \n" +
                "\n" +
                "- несколько новых настроек, выделены жирным\n" +
                "\n" +
                "- для проверяющих: оценка отчетов доступна либо из окна задания (\"Применить\" – оценка выбранного отчета, \"Ко всем отчетам\" – оценка всех отчетов выбранного сотрудника по открытому заданию), либо из вкладки \"Отчеты расширенно\" – левый клик по колонке \"Проверка\"). Данные мониторинга доступны либо из окна задания (\"Мониторинг\" – данные за выбранный день, \"По всем дням\" – данные за весь период выполнения задания сотрудником, отчет которого выбран), либо из вкладки \"Отчеты расширенно\" (двойной клик по колонке \"М (…)\"). Следует обратить внимание на то, что время и простой отчетов в окне задания отображаются с вычетом допустимого простоя, а время и простой в окне мониторинга отображаются в \"сыром\" виде.\n" +
                "\n" +
                "- примерная схема автоматического расчета зарплаты:\n" +
                "  - ежедневные отчеты оцениваются проверяющими (с задержкой в день, возможно больше)\n" +
                "  - установленный коэффициент умножается на ставку и время выполнения задания (время = время старт – стопа и время по мониторингу минус простой, за день разрешено на данный момент 10% простоя) \n" +
                "  - полученная сумма может быть уменьшена (опозданием более чем на 10 минут (на данный момент), пустым отчетом, просрочкой дедлайна) / увеличена (коэф. за работу в вечернее / ночное время , коэф. за работу в выходные дни, данные увеличения ставки включаются в индивидуальном порядке)\n" +
                "\n" +
                "- в ближайшее время будет добавлена система восстановления рабочего времени (отсутствие интернета и т.п.)"));
        listInstructionsModel.add(new InstructionsModel("Политика откртия дверей в кабинет","\n" +
                "1. Двери всегда закрыты, у всех есть свой ключь\n" +
                "\n" +
                "2. Стучать теоретически может только :\n" +
                "a) Василичь\n" +
                "b) Тарас\n" +
                "c) и кто то из сотрудников из другого кабинета\n" +
                "однако со след недели все должны знать, что попасть в кабинет по стуку нельзя , можно только позвонив или написав предварительно в icq.\n" +
                "\n" +
                "3.\n" +
                "Если кто то долго стучит , можно позвонить любому сотруднику из другого кабинета ,\n" +
                "или же Тарасу 0979042504  или в Аркаду 0362 262961\n" +
                "или Васильевичу 0964453303\n" +
                "что б подошли посмотрели , кто стучит там.\n" +
                "в крайнем случае мой моб 0503392452 перезвонить мне\n" +
                "\n" +
                "4. итого : Открывать по стуку нельзя. Вообще. Ходят проверки.\n" +
                "\n" +
                "П.С. Открывать можно только по кодовому стуку."));
        listInstructionsModel.add(new InstructionsModel("Политика конфиденциальности","\n" +
                "Все работы и проекты не обсуждаются с людьми, не имеющими к ним отношения, т.е НЕ сотрудниками\n" +
                "\n" +
                "В частности с друзьями и знакомыми.\n" +
                "Это обычная политика конфиденциальности почти в любой организации\n" +
                "\n" +
                "Утечки информации ни к чему, это может навредить. И тем более в контексте разного рода проверок.\n" +
                "\n" +
                "Считайте что работаете на военном обьекте, где всё засекречено :)\n" +
                "\n" +
                "Рассчитываю на ваше понимание и поддержку, сенкс."));
        listInstructionsModel.add(new InstructionsModel("Политика конфиденциальности МЕЖДУ СОТРУДНИКАМИ","\n" +
                "Все работы и проекты (или часть работ или проектов )не обсуждаются с сотрудниками , кроме обсуждения( при надобности) с теми ,  которые явно указаны в задаче в программе по данному отрезку работ\n" +
                "\n" +
                "Когда каждый сотрудник знает обо всех проектах - это может навредить всему колллективу.\n" +
                "\n" +
                "при проверке, при желании , один сотрудник сможет рассказать обо всех проектах , как проверкам разного рода , так и потенциальным конкурентам. Тем более, в случае прекращения сотрудничества.\n" +
                "\n" +
                "Итого :\n" +
                "\n" +
                "Это четкая инструкция. Инфа по проектам не обсуждается в кругу сотрудников.\n" +
                "\n" +
                "Нарушение этого пункта - это тот редкий случай, который может повлечь вынужденное прекращение дальнейшего сотрудничества - т.е увольнение\n" +
                "\n" +
                "Данные шаги сделаны в целях безопастности.\n" +
                "\n" +
                "Рассчитываю на ваше понимание и поддержку, сенкс."));
        listInstructionsModel.add(new InstructionsModel("Испытательный срок для новых сотрудников - 1-1,5 мес","Ноыве сотрудники считаются постоянно принятыми на работу после дополнительного разговора - который проходит через 1-1,5 мес после тестового периода работы"));
        listInstructionsModel.add(new InstructionsModel("Возможные ПРИЧИНЫ УВОЛЬНЕНИЯ","\n" +
                "В компании могут работать только отвественные и грамотные сотруднки.\n" +
                "Для оценки качетсва работы используется два параметра :\n" +
                "\n" +
                "I). качество выполненных заданий  :\n" +
                "\n" +
                "т.е на сколько грамотно было выполнено то или иное задание  (например - правильно подобраны исполнители , грамотно реализован поиск и внедрение того или иного пункта , систематизация найденной информации и т.д )\n" +
                "\n" +
                "   Сотрудники несут остветсвенность за качество работ по ЛЮБОМУ сделанному ими пункту. В случае ошибок, сотруднику не поручается уже выполнение заданий , ввиду риска того , что в дальнейшем остальные пункты будут делатся так жене правильно. Зачастую, в  случае ошибок (больших ошибок) и (или) наненсённого ущерба (который измеряется так женедополученной прибылью) такой сотрудник увольняется.\n" +
                "\n" +
                "----------\n" +
                "\n" +
                "2. количество времени работы в течении дня (интенсивность работы)\n" +
                "\n" +
                "II)\n" +
                "    В течении рабочтего дня, допускается небольшой отрезок времени на перерывы. В случае злоупотреблений, когда этот отрезок выходит за рамки  разумного  т.е. много ничего не делается, или делания не того , сотрудник увольняется.\n" +
                "\n" +
                "1. По умолчанию, считается что он так плохо работал не только этот день , а всё время.\n" +
                "\n" +
                "2. Далее, если сотрудник плох в этот аспекте, значит тот отрезок работ , когда что то делается - там делается возможно так же все не очень  добросовестно .  Такой сотрудник не нужен и не ценен. Намного рациональней потратить неделювремени на замену на нового  добросовестного сотрудника и работать в дальнейшем уже с ним.\n" +
                "\n" +
                "3. Увольнение происходит без предупреждений, с возможным штрафом на всю не выданную зп (для компенсации оплаченной зп, на которую не  делалось всё в полном обьёме.)\n" +
                "\n" +
                "\n" +
                "p.s\n" +
                "\n" +
                "Увольнение происходит не по личным мотивам, а ввиду не рациональности работы с данным сотрудником.\n" +
                "Так что без обид. Спасибо.\n" +
                "\n" +
                "\n" +
                "Данные правила частично могут не распространятся на особо ценных сотрудников"));
        listInstructionsModel.add(new InstructionsModel("О мониторинге.Важно.","1.  Необходимым условием работы есть мониторинг всех ваших действий в рабочее время, на компьютере.\n" +
                "\n" +
                "Это условие планировалось ввести изначально, и обусловлено оно обективной необходиомстью контроля.\n" +
                "У работодателя есть полное моральное право контролировать работу сотрудников в рабочее время\n" +
                "\n" +
                "Это условие не обсуждается и изменятся не будет и есть абсолютно обязательным.\n" +
                "\n" +
                "По сотрудникам, которые забывают включить мониторинг, в тот день в сводной таблице нет отчёта о действиях,\n" +
                "соответственно зарплата в тот день не насчитывается\n" +
                "\n" +
                "Безусловно мы понимаем что работать кряду 6-8 часов тяжело , поэтому нет проблемы в том что некоторое НЕБОЛЬШОЕ время в процессе рабочего дня вы тратите на общение в контакте , в icq и прочие мелочи, однако их длительность будет мониторится\n" +
                "\n" +
                "Для сотрудников , кто работает хорошо и ответственно , новость о мониторинге не вызовет каких либо неудобств.\n" +
                "\n" +
                "Мониторинг может быть неудобным сотрудникам, кто работает не очень хорошо, однако задачей мониторинга и есть выявить таких сотрудников,  а задачей компании, как вариант, заменить их на более отвественных. Мониторинг будет вестись постоянно и обьективно.\n" +
                "\n" +
                "Запускается мониторинг следующим способом  - после запуска программы Matrix , в ней надо нажать Мониторинг -> Запустить и запустится еще одна дополнительная программа, которая и ведет мониторинг. Для завершения мониторинга -в конце рабочего дня нажимается Мониторинг -> Остановить\n" +
                "\n" +
                "Фаерволы и антивирусы будут пытатся удалить эту програму так как она есть с их точки зреня программой-шпионом ,\n" +
                "поэтому в них обязательно надо разрешить ее запуск\n" +
                "\n" +
                "\n" +
                "2.  По работе и ежедневных отчётах в программу Matrix.\n" +
                "\n" +
                "Ежедневные отчеты обязательны.\n" +
                "\n" +
                "в задание каждое обязательно вписывать надо количественные величины  :\n" +
                "\n" +
                "пример :  Надо писать - Был выполнен поиск райтеров, отослано 20 сообщений , найдено 5 рерайтеров и т.д (а не просто  - был выполнен поиск рерайтеров) не пишется - подача обявлений. Пишется - подано 4 обьявления ,  и т.д"));
        listInstructionsModel.add(new InstructionsModel("Оффтопик - Научные доказательнства существования БОГА.","Познавательная НАУЧНАЯ  книга про доказательства существования БОГА ,\n" +
                "\n" +
                "1. Файл книги\n" +
                "\n" +
                "2. Программа для открытия\n" +
                "\n" +
                "Книга очень интерестная ,\n" +
                "\n" +
                "Убедительная и НАСТОЯТЕЛЬНАЯ просьба прочитать хотя бы первых 50-70 странц , дальше по\n" +
                "желанию\n" +
                "Очень легко читается .\n" +
                "\n" +
                "Не думать , а просто начать читать .\n" +
                "\n" +
                "\n" +
                "http://dl.dropbox.com/u/16323004/BOOK.rar"));
        ObservableList<InstructionsModel> content = FXCollections.observableArrayList(listInstructionsModel);
        lvInstructions.setItems(content);
    }

}
