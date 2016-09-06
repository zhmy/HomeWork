package com.zmy.library;

/**
 * 通用shared preferences文件的key列表
 * 
 * @author guagua
 */
public class SharedPrefConfig {


	public static final String OPERATE_CALL_FANS_LAST_TIME = "operate_call_fans_last_time";

	public static final String USER_OPEN_YUNPAN_PREFIX = "user_open_yunpan_";

	public static final String USER_BLOCK_GROUP_PREFIX = "user_bolck_group_";



	/*—————————————————————————————————————————————————————————————*/

	/**
	 * 插件资源的网络开关
	 */
	public static final String IS_PLUGIN_RESOURCE_OPEN_NET = "is_plugin_resource_open_net";

	/**
	 * 插件资源的本地开关
	 */
	public static final String IS_PLUGIN_RESOURCE_OPEN_LOCAL = "is_plugin_resource_open_local";

	/**
	 * 退出客户端后，是否可以启动长连接
	 */
	public static final String IS_EXIT_APP_NOT_START_WEBSOCKET = "is_exit_app_not_start_websocket";
	/**
	 * 日志上传时间
	 */
	public static final String NEW_LOG_UPLOAD_TIME_PREFIX = "new_log_upload_time_";

	public static final String STAT_STAT_UPLOAD_TIME = "log_stat_upload_time ";

	/**
	 * 日志Debug时间
	 */
	public static final String STAT_DEBUG_UPLOAD_TIME = "log_stat_debug_time";
	/**
	 * 日志开关数据
	 */
	public static final String STAT_SWITCH_DATA = "log_stat_switch_data";
	/**
	 * 日志error时间
	 */
	public static final String STAT_ERROR_UPLOAD_TIME = "log_stat_error_time";
	/**
	 * 日志performance时间
	 */
	public static final String STAT_PERFOR_UPLOAD_TIME = "log_stat_perfor_time";

	/**
	 * 流量统计日志时间
	 */
	public static final String STAT_TRAFFIC_START_TIME = "log_stat_traffic_time";

	/**
	 * 流量统计网络类型
	 */
	public static final String STAT_TRAFFIC_NETWORK_TYPE = "log_stat_traffic_network_type";

	/**
	 * 流量统计前后台类型
	 */
	public static final String STAT_TRAFFIC_FRONT_BACKGROUND = "log_stat_traffic_front_background";

	/**
	 * 主进程名
	 */
	public static String MAIN_PROCESS_NAME = "com.baidu.tieba";
	/**
	 * remote进程名
	 */
	public static String REMOTE_PROCESS_NAME = "com.baidu.tieba:remote";
	/**
	 * bdservice进程名
	 */
	public static String BDSERVICE_PROCESS_NAME = "com.baidu.tieba:bdservice_v1";
	/**
	 * 插件安装进程名
	 */
	public static String PLUGININSTALLER_PROCESS_NAME = "com.baidu.tieba:pluginInstaller";
	/**
	 * daemon进程名
	 */
	public static String DAEMON_PROCESS_NAME = "com.baidu.tieba:daemon";
	/**
	 * cdnTachometer进程名
	 */
	public static String CDNTACHOMETER_PROCESS_NAME = "com.baidu.tieba:cdnTachometer";

	/**
	 * 通用的sharedPref文件名
	 */
	public static final String COMMON_SETTINGS_NAME = "common_settings";
	/**
	 * 主进程的sharedPref文件名
	 */
	public static final String MAIN_SETTINGS_NAME = "settings";
	/**
	 * remote进程的sharedPref文件名
	 */
	public static final String REMOTE_SETTINGS_NAME = "remote_settings";
	/**
	 * bdservice进程的sharedPref文件名
	 */
	public static final String BDSERVICE_SETTINGS_NAME = "bdservice_settings";
	/**
	 * 插件安装进程的sharedPref文件名
	 */
	public static String PLUGININSTALLER_SETTINGS_NAME = "plugininstaller_settings";
	/**
	 * daemon进程的sharedPref文件名
	 */
	public static String DAEMON_SETTINGS_NAME = "daemon_settings";
	/**
	 * cdnTachometer的sharedPref文件名
	 */
	public static String CDNTACHOMETER_SETTINGS_NAME = "cdnTachometer_settings";

	public static final String INTENT_KEY = "intent_key";

	public static final String INTENT_VALUE = "intent_value";

	/**
	 * 补丁包hook崩溃次数
	 */
	public static final String PATCH_FATAL_ERROR_COUNT = "plugin_patch_hook_failed_count";

	// ------------------通用部分

	/**
	 * 上次打开app时间
	 */
	public static final String LAST_START_APP_TIME = "last_start_app_time";

	/**
	 * 皮肤模式，白天、夜间和皮肤模式
	 */
	public static final String PREFS_SKIN_TYPE = "skin_";


	/**
	 * 定位功能crash次数
	 */
	public static final String BD_LOC_CRASH_COUNT = "bd_loc_crash_count";

	/**
	 * GPU开关
	 */
	public final static String GPU_OPEN = "gpu_open";

	/**
	 * 加载图片是否使用httpclient开关
	 */
	public final static String USE_HTTPCLIENT = "httpclient";
	/**
	/**
	 * 渠道号
	 */
	public final static String FROM_ID = "from_id";

	/**
	 * WebView的crash次数，全局计算
	 */
	public final static String WEBVIEW_CRASH_COUNT = "webview_crash_count";

	/**
	 * 定位切换，是否开启百度定位
	 */
	public static final String PREFS_BD_LOC_SWITCHER = "bd_loc_switcher";

	/**
	 * 是否同时下载了第三方app
	 */
	public final static String INSTALL_OTHER_APP_FILE_NAME = "install_other_app_file_name";

	/**
	 * CUID
	 */
	public final static String CUID = "cuid";

	/**
	 * CLIENT ID
	 */
	public final static String CLIENT_ID = "client_id";

	/**
	 * wifi下是否打开keepalive
	 */
	public final static String KEEPALIVE_WIFI = "keepalive_wifi";
	/**
	 * 非wifi下是否打开keepalive
	 */
	public final static String KEEPALIVE_NONWIFI = "keepalive_nonwifi";

	/**
	 * 重连策略
	 */
	public final static String SOCKET_RECONN_STRATEGY = "socket_reconn_strategy";

	/**
	 * 客户端连接lcs的心跳时间间隔
	 */
	public final static String SOCKET_HEARTBEAT_STRATEGY = "socket_heartbeat_strategy";

	/**
	 * 拉im消息的前台与后台时间间隔
	 */


	/**
	 * 客户端lcs超时
	 */
	public final static String SOCKET_TIME_OUT = "socket_time_out";


	/**
	 * 经度
	 */
	public final static String LOCATION_LAT = "location_lat";
	/**
	 * 纬度
	 */
	public final static String LOCATION_LNG = "location_lng";
	/**
	 * 地理位置
	 */
	public final static String LOCATION_POS = "location_pos";

	/**
	 * 网络内核版本
	 */
	public final static String NETWORKCORE_TYPE = "networkcore_type";

	/**
	 * 上传图片质量
	 */
	public static final String PREFS_IMAGE_QUALITY = "image_quality";


	/**
	 * 系统是否有能力支持解析WEBP图像格式
	 */
	public static final String CAPABLE_OF_WEBP_FORMAT = "capable_of_webp_format";
	/**
	 * WEBP格式解析失败的累积次数。内部自己检查失败的话会直接置MAX+1。-1表示未经过内部检查。
	 */
	public static final String WEBP_FAILURE_COUNT = "webp_failure_count";

	/**
	 * 魔图是否禁用
	 */
	public final static String IS_MOTO_FORBIDDEN = "is_motu_forbidden";

	// cdn列表存储的key
	public static final String CDN_IPLIST_CACHE_KEY = "cdn_iplist_cache_key_three";
	/**
	 * 是否上报用户信息的时间控制key
	 */
	public static final String REPORT_USER_IFNO_TIME_KEY = "report_user_info_time_key";

	/**
	 * 下拉刷新图片url地址
	 */
	public static final String PULL_IMAGE_URL = "pull_image_url";
	/**
	 * 下拉刷新图片的个数
	 */
	public static final String PULL_IAMGE_NUM = "pull_image_num";

	/**
	 * 下拉刷新背景色值白天
	 */
	public static final String PULL_VIEW_BACKGROUND_COLOR_DAY = "pullview_background_color_day";

	/**
	 * 下拉刷新背景色值夜间
	 */
	public static final String PULL_VIEW_BACKGROUND_COLOR_NIGHT = "pullview_background_color_night";


	/**
	 * 控制显示查看大图的tipView
	 */
	public static final String IMAGE_VIEWER_TIP = "image_viewer_tip";
	public static final String DISCOVER_TIP_SHOW = "discover_tip_show_";
	public static final String GAME_TIP_SHOW = "game_is_show_tip";




	/**
	 * 显示正文头像
	 */
	public final static String DISPLAY_PHOTO = "new_display_photo";


	/**
	 * 是否开启App推荐
	 */
	public final static String APP_ON = "app_switcher";



	/**
	 * 是否开启定位
	 */
	public final static String LOCATION_ON = "location_on";


	/**
	 * 正文浏览图片质量
	 */
	public static final String PREFS_VIEW_IMAGE_QUALITY = "view_image_quality";
	/**
	 * 正文是否展示图片
	 */
	public static final String PREFS_SHOW_IMAGES = "show_images";


	/**
	 * Push Channel id
	 */
	public final static String PUSH_CHANNEL_ID = "push_channel_id";
	/**
	 * Push Channel userId
	 */
	public final static String PUSH_CHANNEL_UserId = "push_channel_userId";

	/**
	 * 最新版本
	 */
	public final static String LATEST_VERSION = "lase_version";



	/**
	 * crash每天上传的最大次数
	 */
	public static final String CRASH_LIMIT_ACOUNT_KEY = "crash_limit_count";



	// ------------------end of 主进程部分


	// ------------------end of 主进程部分

	// remote进程部分

	// bdservice进程部分

	// ------------------end of 主进程部分

	// remote进程部分

	// bdservice进程部分


	/**
	 * versioncode 用来判断是否需要重新计算 apk的md5
	 */
	public final static String VERSION_NAME = "version_name";
	/**
	 * apk的md5
	 */
	public final static String APK_MD5 = "apk_md5";

	/**
	 * 阿拉丁crash次数
	 */
	public final static String LIVE_SDK_CRASH_COUNT = "live_sdk_crash_count_";

	/**
	 * pass crash次数
	 */
	public final static String PASSPORT_CRASH_COUNT = "passport_crash_count_";

	/**
	 * crab sdk crash次数
	 */
	public final static String CRAB_SDK_CRASH_COUNT = "crab_sdk_crash_count_";

	/**
	 * 小米 push sdk crash次数
	 */
	public final static String XIAOMI_PUSH_SDK_CRASH_COUNT = "xiaomi_push_sdk_crash_count_";

	/**
	 * hao123桌面助手的
	 */
	public final static String HAO123_HELPER__CRASH_COUNT = "hao123_helper_crash_count";

	/**
	 * 抱抱的
	 */
	public final static String BAOBAO_CRASH_COUNT = "baobao_crash_count";
	/**
	 * 点券
	 */
	public final static String DQ_CRASH_COUNT = "dq_crash_count";
	/**
	 * 声纹登陆
	 */
	public final static String VOICE_LOGIN_CRASH = "voice_login_crash";

	/**
	 * 百度钱包
	 */
	public final static String WAllET_CRASH_COUNT = "wallet_crash_count";
	/**
	 * 一起嗨CRASH次数
	 */
	public final static String TOGETHERHI_CRASH_COUNT = "togetherhi_crash_count";

	/**
	 * /** 小影CRASH次数
	 */
	public final static String XIAOYING_CRASH_COUNT = "xiaoying_crash_count";
	/**
	 * 一个三方功能在crash多少次以后自动关闭。
	 */
	public final static String FEATURE_CRASH_AUTO_CLOSE_LIMIT = "feature_crash_auto_close_limit";

	/**
	 * 从Server拉取全量联系人数据的开关
	 */
	public final static String GET_ADDRESSLIST_SWITCH = "get_addresslist_switch";

	/**
	 * AD_TIME
	 */
	public final static String AD_TIME = "ad_time";

	/**
	 * AD_START_TIME
	 */
	public final static String AD_START_TIME = "ad_start_time";

	/**
	 * AD_END_TIME
	 */
	public final static String AD_END_TIME = "ad_end_time";

	/**
	 * AD_URL
	 */
	public final static String AD_URL = "ad_url";

	/**
	 * Ad_Enabled
	 */
	public final static String Ad_Enabled = "ad_enabled";

	/**
	 * frs明星票务点击时间戳记录的key
	 */
	public final static String FRS_STARTICKET_LAST_CLICK_TIME = "FRS_STARTICKET_LAST_CLICK_TIME";
	/**
	 * 陌生人聊天中的提示
	 */
	public final static String STRANGER_TIPS = "stranger_tips";
	/**
	 * 某帐号第一次登录客户端，需要在登录后跳转到联系人tab SharedPrefs key=ACCOUNT_FIRST_LOGIN+account
	 * 例如：account_first_login_11702232
	 */
	public static final String ACCOUNT_FIRST_LOGIN = "account_first_login_";
	/**
	 * 第一次进入联系人tab，在“新的好友”显示小红点 key=SHOW_NEW_ICON_FOR_NEW_FRIEND+account
	 */
	public static final String SHOW_NEW_ICON_FOR_NEW_FRIEND = "show_new_icon_for_new_friend_";

	/**
	 * 发帖页和pb回复时不再显示地理位置
	 */
	public static final String NO_LONGER_SHOW_ADDRESS = "no_longer_show_address";

	// 运营消息的点击率和到达率统计日期
	public static final String OPERATE_MSG_ARRIVE_CLICK_DATE = "operate_msg_arrive_click_date";
	// 运营消息的点击率和到达率24小时内的次数
	public static final String OPERATE_MSG_ARRIVE_CLICK_COUNT = "operate_msg_arrive_click_count";

	/**
	 * 游戏提醒时间的KEY
	 */
	public final static String GAME_LAST_TIME_KEY = "game_last_time";

	public final static String KEY_DISCOVER_SHOW_PLUGIN_LIST = "key_discover_show_plugin_list";
	/**
	 * 是否显示滑动返回引导的KEY
	 */
	public final static String SHOW_SWIPE_BACK_GUIDE_KEY = "show_swipe_back_key";

	/**
	 * 当前直播状态是否为主播
	 */
	public final static String LIVE_IS_HOST = "live_is_host";

	/**
	 * 当前直播流id
	 */
	public final static String LIVE_STREAM_ID = "live_stream_id";

	/**
	 * 正常完整直播功能的次数
	 */
	public final static String LIVE_NO_ERROR_TIME = "live_no_error_time";

	/**
	 * 上次关闭感兴趣的吧的时间
	 */
	public final static String LAST_CLOSE_RECOMMEND_TIME = "last_close_recommend_time";
	/**
	 * mini版本左侧边栏是否展示过“浏览贴吧客户端”
	 */
	public final static String HAS_SHOW_UPDATE_FULL_APP = "has_show_update_full_app";
	/**
	 * 主导航-侧边栏会员中心，展示new标签
	 */
	public final static String SHOW_MEMBER_NEW_ICON = "show_member_new_icon_";

	/**
	 * 主导航-侧边栏会员中心右侧小红点
	 */
	public final static String SHOW_MEMBER_RED_TIP = "show_member_red_tip_";
	/**
	 * 主导航-侧边栏我的直播，展示new标签
	 */
	public final static String SHOW_MY_LIVE_NEW_ICON = "show_my_new_icon";

	/**
	 * 清理data／data/com.baidu.tieba/plugins下的冗余文件的时间
	 */
	public final static String CLEAR_REDUNDANCE_FILES_TIME = "clear_redundance_files_time";

	/**
	 * native crash 对应的版本。
	 */
	public final static String NATIVE_CRASH_DUMP_VERSION = "native_crash_dump_version";

	/**
	 * 是否进入过精编PB页的KEY
	 */
	public static final String KEY_ENTER_RECOMMEND_PB = "key_enter_recommend_pb";

	/**
	 * 是否弹出过调查问卷
	 */
	public static final String KEY_SHOW_QUESTIONNAIRE = "key_show_questionnaire";

	/**
	 * 搜贴tab在第一次展示时需要展示下拉菜单，不区分用户
	 */
	public static final String SEARCH_POST_SHOW_DROPMENU = "search_post_show_dropmenu";
	/**
	 * 页面停留时长统计开关
	 * 
	 */
	public static final String PAGE_STAY_DURATION_SWITCH = "page_stay_duration_switch";
	/**
	 * 页面停留时长最大路径长度
	 * 
	 */
	public static final String PAGE_STY_MAX_COST = "page_stay_max_cost";
	// 新注册的key 在通用key字段之上注册，不要在通用key字段和需要广播的key列表之后注册
	// 通用key字段列表
	public static final String[] COMMON_KEYS = { PREFS_SKIN_TYPE, FROM_ID,
			BD_LOC_CRASH_COUNT, WEBVIEW_CRASH_COUNT, PREFS_BD_LOC_SWITCHER,
			INSTALL_OTHER_APP_FILE_NAME, CUID, GPU_OPEN, CLIENT_ID,
			KEEPALIVE_WIFI, KEEPALIVE_NONWIFI, NETWORKCORE_TYPE,
			SOCKET_RECONN_STRATEGY, PREFS_IMAGE_QUALITY,
			CAPABLE_OF_WEBP_FORMAT, WEBP_FAILURE_COUNT,
			STAT_STAT_UPLOAD_TIME, STAT_DEBUG_UPLOAD_TIME, STAT_SWITCH_DATA,
			STAT_ERROR_UPLOAD_TIME, IS_MOTO_FORBIDDEN, CDN_IPLIST_CACHE_KEY,
			REPORT_USER_IFNO_TIME_KEY, IMAGE_VIEWER_TIP, LOCATION_LAT, LOCATION_LNG, LOCATION_POS, LOCATION_ON,
			XIAOYING_CRASH_COUNT, STAT_TRAFFIC_START_TIME, STAT_TRAFFIC_NETWORK_TYPE, STAT_TRAFFIC_FRONT_BACKGROUND
			, PATCH_FATAL_ERROR_COUNT, PAGE_STAY_DURATION_SWITCH, PAGE_STY_MAX_COST };

	// 修改后需要广播的key列表
	public static final String[] BROADCAST_KEYS = {};

	/**
	 * 跳转手百的白名单
	 */
	public static final String KEY_WHITE_LIST = "key_white_list";

	/**
	 * 广告图记录的version code
	 */
	public static final String KEY_AD_VERSION_CODE = "key_ad_version_code";

	/**
	 * 是否第一次进入个人中心，第一次则显示印记Toast提示。
	 */
	public static final String PERSON_CENTER_FIRST_VISIT = "person_center_first_visit";

	/**
	 * 气泡H5跳转地址
	 */
	public static final String BUBBLE_LINK = "bubble_link";

	/**
	 * 小尾巴H5跳转地址
	 */
	public static final String TAIL_LINK = "tail_link";

	/**
	 * 进吧页是否编辑模式(统计用)
	 */
	public static final String ENTER_FORUM_EDIT_MODE = "enter_forum_edit_mode";

	/**
	 * 看帖提醒本地开关
	 */
	public final static String REMIND_RECOMMEND_SWITCH = "remind_recommend_switch";

	/**
	 * 看帖提醒server详细数据
	 */
	public final static String REMIND_RECOMMEND_INFO = "remind_recommend_info";

	/**
	 * server看帖提醒开关
	 */
	public final static String REMIND_RECOMMEND_SERVER_SWITCH = "remind_recommend_server_switch";

	/**
	 * server看帖提醒弹窗时间
	 */
	public final static String REMIND_RECOMMEND_DLALOG_TIME = "remind_recommend_dialog_time";

	/**
	 * 上一次成功拉取数据时间
	 */
	public final static String REMIND_RECOMMEND_DATA_TIME = "remind_recommend_data_time";

	/**
	 * 本地上一次内容的索引
	 */
	public final static String REMIND_RECOMMEND_LOCAL_INDEX = "remind_recommend_index";

	/**
	 * 上传原图的提示
	 */
	public final static String ORIGINAL_IMG_UP_TIP = "original_img_up_tip";

	/**
	 * 查看原图的提示
	 */
	public final static String ORIGINAL_IMG_DOWN_TIP = "original_img_down_tip";

	/**
	 * 装扮中心主页面listview中小红点的显示（上次显示时间）
	 */
	public static final String DRESSUP_CENTER_RED_TIP = "dressup_center_red_tip_";
	/**
	 * maintab底部bar会员中心icon上小红点的显示（上次显示时间）
	 */
	public static final String MAINTAB_MEMBER_CENTER_RED_TIP = "maintab_member_center_red_tip_";

	/**
	 * maintab左侧导航栏上装扮中心入口的小红点(上次显示时间)
	 */
	public static final String LEFT_NAV_DRESSUP_CENTER = "left_nav_dressup_center_";

    /**
     * 会员中心各个item上小红点的显示（上次显示时间）
     */
    public static final String MEMBER_CENTER_ITEM_RED_TIP = "member_center_item_red_tip_";

	/**
	 * 热搜词
	 */
	public static final String HOT_SEARCH_Title = "hot_search_title";
	public static final String HOT_SEARCH_Type = "hot_search_type";
	public static final String HOT_SEARCH_Id = "hot_search_id";
	public static final String HOT_SEARCH_Name = "hot_search_name";

	/**
	 * 小影icon是否点击过
	 */
	public static final String XIAOYING_HAS_CLICK = "xiaoying_has_click";
	/**
	 * frs发贴入口是否点击过
	 */
	public static final String FRS_WRITE_HAS_CLICK = "frs_write_has_click";
	/**
	 * 首页用户卡片配置信息
	 */
	public static final String HOME_PAGE_USER_MODULE_CONFIG = "home_page_user_module_config";

	/**
	 * 图文直播，主播等级
	 */
	public static final String PHOTOLIVE_HOSTLEVEL = "photolive_hostLevel";

	/**
	 * Frs区域，收藏卡片
	 */
	public static final String FRS_STORECARD_CLOSETIME = "frs_storecard_closetime";

	/**
	 * 图文直播任务详情页收益列表
	 */
	public static final String MISSON_DETAILS_PROFITS_SHOWN_STATE = "misson_details_profits_shown_state";

	/**
	 * App下载进度
	 */
	public static final String APP_DOWNLOAD_PROGRESS = "app_download_progress";
	/**
	 * 视频功能第一次在Editor上显示的提示信息
	 */
	public static final String VIDEO_TIPS_SHOW_CONFIG = "video_tips_show_config";

	/**
	 * 视频录制在FRS的浮层是否展示过
	 */
	public static final String VIDEO_FRS_GUIDE = "video_frs_guide";

	/**
	 * 是否进行过视频录制
	 */
	public static final String VIDEO_RECORDED = "video_recorded";
	/**
	 * 添加图片水印的tip是否展示
	 */
	public static final String ADD_IMG_WATER_TIP_SHOW = "add_img_water_tip_show";

	/**
	 * sync接口同步的弹出框开关
	 */
	public static final String SYNC_LOCAL_DOALOG = "sync_local_dialog";

	/**
	 * 贴吧最近一次活跃时间（活跃包括：打开过/切换到前台）
	 */
	public static final String TIEBA_LAST_ACTIVE_TIME = "tieba_last_active_time";
	/**
	 * 最近一次调用getOnlineInfo的时间
	 */
	public static final String GET_ONLINE_INFO_LAST_TIME = "get_online_info_last_time";

	/**
	 * logo页启动动画执行时间超时的次数
	 */
	public static final String LOGO_ANIMATION_OVETRTIME_COUNT = "logo_animation_overtime_count";

	/**
	 * 看贴推荐上次刷新时间
	 */
	public static final String RECOMMEND_FRS_REFRESH_TIME = "recommend_frs_refresh_time";
	/**
	 * 看贴推荐刷新引导的基准时间
	 */
	public static final String RECOMMEND_FRS_GUIDE_TIME = "recommend_frs_guide_time";

	/**
	 * 看贴推荐缓存失效时间
	 */
	public static final String RECOMMEND_FRS_CACHE_TIME = "recommend_frs_cache_time";

	/**
	 * 看贴首页贴子最大数量
	 */
	public static final String HOME_PAGE_MAX_THREAD_COUNT = "home_page_max_thread_count";
	/**
	 * 沉浸式状态栏开关状态
	 */
	public static final String SWITCH_IMMERSIVE_STICKY_STATUS = "switch_immersive_sticky_status";
	/**
	 * 大神主页客态否展开
	 */
	public static final String GOD_ENTER_INFO_EXPAND = "god_enter_info_expand";
	/**
	 * PB大神只看楼主浮层展示过
	 */
	public static final String PB_GOD_FLOOR_OWNER_TIP_HAS_SHOWN = "pb_god_floor_owner_tip_has_shown_";
	public static final String PB_GOD_FLOOR_OWNER_NOT_LOGIN_IN_TIP_HAS_SHOWN = "pb_god_floor_owner_not_login_tip_has_shown_";
	/**
	 * 是否显示访谈直播特型页原贴按钮引导的KEY
	 */
	public final static String SHOW_INTERVIEW_ORIGINAL_POST_GUIDE_KEY = "show_interview_original_post_key";
	/**
	 * 吧广播消息提醒本地开关
	 */
	public final static String REMIND_FORUM_BROADCAST_SWITCH = "remind_forum_broadcast_switch";
	/**
	 * 涂鸦功能前三次在吸底Editor上显示的提示信息
	 */
	public static final String GRAFFITI_TIPS_SHOW_CONFIG = "graffiti_tips_show_config";

	/**
	 * 涂鸦功能在回复发贴工具栏显示的提示信息
	 */
	public static final String GRAFFITI_REPLY_TIP_SHOW = "graffiti_reply_tip_show";

	/**
	 * 塗鴉功能在回复发贴工具栏更多面板的红点
	 */
	public static final String GRAFFITI_REPLY_NOTICE_SHOW = "graffiti_reply_notice_show";

	/**
	 * 塗鴉功能在发贴工具栏更多面板的红点
	 */
	public static final String GRAFFITI_WRITE_NOTICE_SHOW = "graffiti_write_notice_show";

	/**
	 * 涂鸦功能在发贴工具栏显示的提示信息
	 */
	public static final String GRAFFITI_WRITE_TIP_SHOW = "graffiti_write_tip_show";

	/**
	 * 是否显示过贴吧介绍轮播图
	 */
	public static final String HAS_SHOWN_APP_GUIDE = "has_shown_app_guide";

	/**
	 * 是否关闭首页新用户提示
	 */
	public static final String CLOSE_NEW_USER_GUIDE_TIP = "close_new_user_guide_tip";

	/**
	 * 是否已经请求过首页新用户提示
	 */
	public static final String HAS_REQUESTED_NEW_USER_GUIDE = "has_requested_new_user_guide";

	/**
	 * 是否已经显示过进吧页登录提示
	 */
	public static final String ENTER_FORUM_LOGIN_TIP = "enter_forum_login_tip";
	/**
	 * 是否已经显示过frs页登录提示
	 */
	public static final String FRS_LOGIN_TIP = "frs_login_tip";

	/** 本地视频上传开关，只对普通用户有效 */
	public static final String LOCAL_VIDEO_OPEN = "localvideo_open";
	/**
	 * 会员去广告，我的tab是否点击过
	 */
	public static final String MEMBER_CLOSE_AD_MINE_CLICKED = "member_close_ad_mine_clicked";

	public static final String MEMBER_CLOSE_AD_SETTING_CLICKED = "member_close_ad_setting_clicked";

	/**
	 * 发贴页存储最新扑捉图片的路径
	 */
	public static final String WRITE_REC_PIC_PATH = "write_rec_pic_path";

	/**
	 * 抓包请求收到的时间
	 */
	public static final String CAPTURE_INITIAL_TIME = "tbpp_key_capture_initial_time";
	/**
	 * 抓包请求短连接地址
	 */
	public static final String CAPTURE_SERVER_HOST = "tbpp_key_capture_server_host";
	/**
	 * 抓包请求长连接地址
	 */
	public static final String CAPTURE_CHAT_HOST = "tbpp_key_capture_chat_host";

	/**
	 * FRS页关注大神帖子有更新的Toast弹出次数
	 */
	public static final String FRS_GOD_NEW_POST_TIP_COUNT = "frs_god_new_post_tip_count";


	/**
	 * FRS页吧头视频动画跳动引导和浮层引导次数（头两次是动画跳动引导，第三次是浮层引导，以后不再显示引导）
	 */
	public static final String FRS_HEAD_VIDEO_GUIDE_BY_COUNT = "frs_head_video_guide_by_count";

    /**
     * 首页阅读进度条
     */
    public static final String READ_PROGRESS_PREFIX = "read_progress_";

    /**
     * app 启动跳转 tab
     */
    public static final String APP_ENTRANCE_PREFIX = "app_entrance_";

    public static final String APP_ENTRANCE_NO_LOGIN_KEY = APP_ENTRANCE_PREFIX + "nologin";

	/**视频列表页确认在移动网络下点击可以播放，只在当前Activity有效*/
	public static final String VIDEO_LIST_CONFIRM_PLAY_IN_MOBILE = "video_list_confirm_play_in_mobile";

	/**设置常逛的吧气泡提醒次数*/
	public static final String FREQUENTLY_FORUM_TIPS_COUNT = "frequently_forum_tips_count";

	/**记录指定的吧是否展示过 设置常逛的吧气泡提醒*/
	public static final String FORUM_HAS_SHOW_FREQUENTLY_FORUM_TIPS = "forum_has_show_frequently_forum_tips";



	/**  ---------  聊天室相关 ----------------------- */
	/** 是否为首次进入聊天室页面 **/
	public static final String CHAT_ROOM_FIRST_ENTER = "chat_room_first_enter";
	/** 是否为群成员首次发送资源 **/
	public static final String CHAT_ROOM_FIRST_SEND = "chat_room_first_send";
	/** 是否为群主首次发送资源 **/
	public static final String CHAT_ROOM_OWNER_FIRST_SEND = "chat_room_owner_first_send";
	/** 是否为显示强制下墙提示 **/
	public static final String CHAT_ROOM_FORCE_DOWN = "chat_room_force_down";
}
