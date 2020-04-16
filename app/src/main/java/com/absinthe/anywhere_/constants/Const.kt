package com.absinthe.anywhere_.constants

import io.michaelrocks.paranoid.Obfuscate

@Obfuscate
object Const {

    const val SP_NAME = "com.absinthe.anywhere__preferences"
    const val SP_NAME_DEBUG = "com.absinthe.anywhere_.debug_preferences"
    const val TOKEN_SP_NAME = "AnywhereToken"
    const val PREF_TOKEN = "token"
    const val PREF_FIRST_LAUNCH = "isFirstLaunch"
    const val PREF_WORKING_MODE = "workingMode"
    const val PREF_DARK_MODE = "darkMode"
    const val PREF_CHANGE_BACKGROUND = "changeBackground"
    const val PREF_RESET_BACKGROUND = "resetBackground"
    const val PREF_DARK_MODE_OLED = "darkModeOLED"
    const val PREF_STREAM_CARD_MODE = "streamCardMode"
    const val PREF_STREAM_CARD_SINGLE_LINE = "streamCardModeSingleLine"
    const val PREF_CARD_BACKGROUND = "cardBackgroundMode"
    const val PREF_ACTION_BAR_TYPE = "actionBarType"
    const val PREF_HELP = "help"
    const val PREF_BETA = "beta"
    const val PREF_CLEAR_SHORTCUTS = "clearShortcuts"
    const val PREF_ICON_PACK = "iconPack"
    const val PREF_TILES = "tiles"
    const val PREF_SORT_MODE = "sortMode"
    const val PREF_BACKUP = "backup"
    const val PREF_BACKUP_SHARE = "backupShare"
    const val PREF_RESTORE = "restore"
    const val PREF_RESTORE_APPLY = "restoreApply"
    const val PREF_MD2_TOOLBAR = "md2Toolbar"
    const val PREF_PAGES = "pages"
    const val PREF_TILE_ONE = "tileOne"
    const val PREF_TILE_TWO = "tileTwo"
    const val PREF_TILE_THREE = "tileThree"
    const val PREF_TILE_ONE_LABEL = "tileOneLabel"
    const val PREF_TILE_TWO_LABEL = "tileTwoLabel"
    const val PREF_TILE_THREE_LABEL = "tileThreeLabel"
    const val PREF_TILE_ONE_CMD = "tileOneCmd"
    const val PREF_TILE_TWO_CMD = "tileTwoCmd"
    const val PREF_TILE_THREE_CMD = "tileThreeCmd"
    const val PREF_CURR_CATEGORY = "currCategory"
    const val PREF_CURR_PAGE_NUM = "currPageNum"
    const val PREF_COLLECTOR_PLUS = "collectorPlus"
    const val PREF_EXCLUDE_FROM_RECENT = "excludeFromRecent"
    const val PREF_SHOW_SHELL_RESULT = "showShellResult"
    const val PREF_DUMP_INTERVAL = "dumpInterval"
    const val PREF_AUTO_DARK_MODE_START = "autoDarkModeStart"
    const val PREF_AUTO_DARK_MODE_END = "autoDarkModeEnd"
    const val PREF_GIFT = "gift"
    const val PREF_DEFROST_MODE = "defrostMode"

    const val WORKING_MODE_URL_SCHEME = "url_scheme"
    const val WORKING_MODE_ROOT = "root"
    const val WORKING_MODE_SHIZUKU = "shizuku"

    const val ACTION_BAR_TYPE_LIGHT = "light"
    const val ACTION_BAR_TYPE_DARK = "dark"

    const val CARD_BG_MODE_OFF = "off"
    const val CARD_BG_MODE_PURE = "pure"
    const val CARD_BG_MODE_GRADIENT = "gradient"

    const val DARK_MODE_OFF = "off"
    const val DARK_MODE_ON = "on"
    const val DARK_MODE_AUTO = "auto"
    const val DARK_MODE_SYSTEM = "system"
    const val DARK_MODE_BATTERY = "battery"

    const val SORT_MODE_TIME_ASC = "TIME_ASC"
    const val SORT_MODE_TIME_DESC = "TIME_DESC"
    const val SORT_MODE_NAME_ASC = "NAME_ASC"
    const val SORT_MODE_NAME_DESC = "NAME_DESC"

    const val INTENT_EXTRA_PARAM_1 = "param1"
    const val INTENT_EXTRA_PARAM_2 = "param2"
    const val INTENT_EXTRA_PARAM_3 = "param3"
    const val INTENT_EXTRA_SHORTCUTS_CMD = "shortcutsCmd"

    const val INTENT_EXTRA_WIDGET_ENTITY = "entity"
    const val INTENT_EXTRA_WIDGET_COMMAND = "command"

    const val INTENT_EXTRA_APP_NAME = "appName"
    const val INTENT_EXTRA_PKG_NAME = "pkgName"

    const val CMD_GET_TOP_STACK_ACTIVITY = "dumpsys activity activities | grep mResumedActivity"
    const val CMD_OPEN_URL_SCHEME = "am start -a android.intent.action.VIEW -d "
    const val CMD_OPEN_URL_SCHEME_FORMAT = "am start -a android.intent.action.VIEW -d %s"
    const val CMD_OPEN_ACTIVITY_FORMAT = "am start -n %s/%s"

    const val REQUEST_CODE_SHIZUKU_PERMISSION = 1001
    const val REQUEST_CODE_ACTION_MANAGE_OVERLAY_PERMISSION = 1002
    const val REQUEST_CODE_IMAGE_CAPTURE = 1004
    const val REQUEST_CODE_WRITE_FILE = 1005
    const val REQUEST_CODE_RESTORE_BACKUPS = 1006

    const val DEFROST_MODE_ROOT = "root"
    const val DEFROST_MODE_ICEBOX_SDK = "icebox"
    const val DEFROST_MODE_DSM = "dsm"
    const val DEFROST_MODE_DPM = "dpm"
    const val DEFROST_MODE_SHIZUKU = "shizuku"
}