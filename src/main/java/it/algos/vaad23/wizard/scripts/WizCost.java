package it.algos.vaad23.wizard.scripts;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: lun, 13-apr-2020
 * Time: 05:09
 */
public abstract class WizCost {

    public static final String VALORE_MANCANTE = "ATTENZIONE - Manca un valore indispensabile";

    //--flag per stampare info di debug
    public static final boolean FLAG_DEBUG_WIZ = true;

    public static final String DIR_PROJECTS = "IdeaProjects/";

    public static final String DIR_OPERATIVI = "operativi/";


    //--valore standard che verrà controllato in funzione di AEDIR.pathCurrent effettivo
    //--potrebbe essere diverso
    public static final String PATH_ROOT = "/Users/gac/Documents/";

    //--regolata inizialmente da WizCost, indipendentemente dall' apertura dei dialoghi, con un valore fisso codificato
    //--tutte le property il cui nome inizia con 'path' iniziano e finiscono con uno SLASH
    //--directory che contiene i nuovi programmi appena creati da Idea
    //--dovrebbe essere PATH_PROJECTS_DIR_STANDARD
    //--posso spostarla (è successo) senza che cambi nulla
    public static final String PATH_PROJECTS_DIR_STANDARD = PATH_ROOT + DIR_PROJECTS;

    //--regolata inizialmente da WizCost, indipendentemente dall' apertura dei dialoghi, con un valore fisso codificato
    //--tutte le property il cui nome inizia con 'path' iniziano e finiscono con uno SLASH
    //--directory che contiene i programmi operativi in uso
    //--dovrebbe essere PATH_PROJECTS_DIR_STANDARD
    //--posso spostarla (è successo) senza che cambi nulla
    public static final String PATH_OPERATIVI_DIR_STANDARD = PATH_PROJECTS_DIR_STANDARD + DIR_OPERATIVI;


    public static final String DIR_RESOURCES = "resources/";

    public static final String DIR_META = "META-INF/";

    public static final String DIR_ALGOS = "java/it/algos/";

    public static final String DIR_VAADFLOW = "vaadflow14/";

    public static final String DIR_WIZARD = "wizard/";

    public static final String DIR_SOURCES = "sources/";

    public static final String DIR_BACKEND = "backend/";

    public static final String DIR_PACKAGES = "packages/";

    public static final String DIR_UI = "ui/";

    //--parte dal livello di root del progetto
    public static final String ROOT_DIR_CONFIG = "config/";

    //--parte dal livello di root del progetto
    public static final String ROOT_DIR_DOC = "doc/";

    //--parte dal livello di root del progetto
    public static final String ROOT_DIR_LINKS = "links/";

    //--parte dal livello di root del progetto
    public static final String ROOT_DIR_SNIPPETS = "snippets/";

    //--parte dal livello di root del progetto
    //--contiene images/ (di solito)
    //--contiene src/ (di solito)
    //--contiene styles/ (sempre)
    public static final String ROOT_DIR_FRONTEND = "frontend/";

    //--parte dal livello di root del progetto
    //--contiene java e resources di ogni progetto
    public static final String ROOT_DIR_MAIN = "src/main/";

    //--parte dal livello di root del progetto
    //--contiene i moduli, di solito due (vaadFlow e vaadTest)
    public static final String ROOT_DIR_ALGOS = ROOT_DIR_MAIN + "java/it/algos/";

    //--parte dal livello di root del progetto
    //--contiene META_INF
    //--contiene application.properties
    //--contiene banner.txt (di solito)
    public static final String ROOT_DIR_RESOURCES = ROOT_DIR_MAIN + "resources/";

    //    //--parte dal livello di root del progetto
    //    //--contiene META_INF
    //    //--contiene application.properties
    //    //--contiene banner.txt (di solito)
    //    public static final String DIR_RESOURCES = ROOT_DIR_MAIN + ROOT_DIR_RESOURCES;

    public static final String DIR_IDEAPROJECTS = "IdeaProjects/";


    public static final String PROJECT_VAADFLOW = "vaadflow14/";

    public static final String NAME_VAADFLOW = "vaadflow14";

    public static final String VAADFLOW_STANDARD = "IdeaProjects/operativi/vaadflow14";

    public static final String VAADFLOW_DIR_STANDARD = VAADFLOW_STANDARD + "/";

    public static final String PATH_VAADFLOW_DIR_STANDARD = PATH_ROOT + VAADFLOW_DIR_STANDARD;

    //    public static final String PATH_OPERATIVI = PATH_PROJECTS_DIR_STANDARD + "operativi/";


    public static final String FILE_WIZARD = "Wizard";

    public static final String NORMAL_WIDTH = "9em";

    public static final String NORMAL_HEIGHT = "3em";

    public static final String TITOLO_START_PROGETTO = "Inizializzazione progetto";

    public static final String TITOLO_NUOVO_PROGETTO = "Nuovo progetto";

    public static final String TITOLO_MODIFICA_PROGETTO = "Update progetto";

    public static final String TITOLO_MODIFICA_MODULO = "Modifica di un modulo esistente";

    public static final String TITOLO_MODIFICA_QUESTO_PROGETTO = "Modifica progetto";

    public static final String TITOLO_NEW_PACKAGE = "Nuovo package";

    public static final String TITOLO_UPDATE_PACKAGE = "Update package";

    public static final String TITOLO_DOC_PACKAGES = "Doc packages";

    public static final String TITOLO_FEEDBACK_PROGETTO = "Feedback di wizard";

    public static final String DIR_APPLICATION = "application/";

    public static final String DIR_BOOT = "boot/";

    public static final String DIR_DATA = "data/";

    public static final String APP_NAME = "Application";

    public static final String FILE_READ = "README";

    public static final String FILE_ENTIY = "";

    public static final String FILE_COST = "Cost";

    public static final String FILE_BOOT = "Boot";

    public static final String FILE_DATA = "Data";

    public static final String FILE_PREFIX_ENUMERATION = "AE";

    public static final String FILE_PREFERENZA = "Preferenza";

    public static final String FILE_BUTTON = "Button";

    public static final String FILE_VERS = "Vers";

    public static final String FILE_HOME = "Home";

    public static final String FILE_POM = "pom";

    public static final String FILE_BANNER = "banner";

    public static final String FILE_GIT = ".gitignore";

    public static final String FILE_PROPERTIES = "properties";

    public static final String FILE_PROPERTIES_DEST = "application.properties";

    public static final String TXT_SUFFIX = ".txt";

    public static final String XML_SUFFIX = ".xml";

    public static final String JAVA_SUFFIX = ".java";

    public static final String MD_SUFFIX = ".md";

    public static final String SOURCE_ENTITY = "Entity";

    public static final String SOURCE_LIST = "LogicList";

    public static final String SOURCE_FORM = "LogicForm";

    public static final String SOURCE_SERVICE = "Service";

    //--parte dal livello di root del progetto
    //--contiene resources/ (sempre)
    //--contiene resources/ (sempre)
    //    public static final String DIR_META = DIR_RESOURCES + DIR_META_NAME;


    //--parte dal livello di root del progetto
    //--valida SOLO per progetto vaadFlow
    public static final String DIR_VAADFLOW_SOURCES = ROOT_DIR_ALGOS + PROJECT_VAADFLOW + "wizard/sources/";


}
