
package DD.Android.FixComputer.service;

import DD.Android.FixComputer.R;
import DD.Android.FixComputer.core.*;
import DD.Android.FixComputer.ui.ActivityProblem;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import roboguice.service.RoboService;
import roboguice.util.RoboAsyncTask;

import java.io.IOException;
import java.util.Calendar;

import static com.github.kevinsawicki.http.HttpRequest.get;
import static DD.Android.FixComputer.core.Constants.Extra.PROBLEM;

public class ProblemsService extends RoboService {

    //      @Inject
//      protected String authToken;
//    @Inject
//    protected ZhaohaiServiceProvider serviceProvider;
//    @InjectExtra(APIKEY)

    private ProblemStatus ps;

    //获取消息线程
    private MessageThread messageThread = null;

    //点击查看
    private Intent messageIntent = null;
    private PendingIntent messagePendingIntent = null;

    //通知栏消息
    private Notification messageNotification = null;
    private NotificationManager messageNotificatioManager = null;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();    //To change body of overridden methods use File | Settings | File Templates.

        PropertiesController.readConfiguration();
        //初始化
        messageNotification = new Notification();
        messageNotification.icon = R.drawable.ic_launcher;
        messageNotification.tickerText = "新消息";
        int style_notifi = 0;
        if (Settings.getFactory().isSoundNotifi)
            style_notifi = style_notifi | Notification.DEFAULT_SOUND;
        if (Settings.getFactory().isShockNotifi)
            style_notifi = style_notifi | Notification.DEFAULT_VIBRATE;
        if (Settings.getFactory().isLightNotifi)
            style_notifi = style_notifi | Notification.DEFAULT_LIGHTS;
        messageNotification.defaults = style_notifi;
        messageNotificatioManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        //开启线程
        messageThread = new MessageThread();
        messageThread.isRunning = true;
        messageThread.start();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public class MessageBinder extends Binder {
        public ProblemsService getService() {
            return ProblemsService.this;
        }
    }

    @Override
    public void onDestroy() {
        System.exit(0);
        //或者，二选一，推荐使用System.exit(0)，这样进程退出的更干净
//        //messageThread.isRunning = false;
        super.onDestroy();    //To change body of overridden methods use File | Settings | File Templates.
    }

    /**
     * 从服务器端获取消息
     */
    class MessageThread extends Thread {
        //运行状态，下一步骤有大用
        public boolean isRunning = true;

        public void run() {
            while (isRunning) {
                if (Settings.getFactory().isNotifi) {
                    Calendar cal = Calendar.getInstance();
                    if (!(Settings.getFactory().isNotDisturb && (cal.get(Calendar.HOUR_OF_DAY) > 22 || cal.get(Calendar.HOUR_OF_DAY) < 8))) {
                        try {
                            ProblemStatus get_ps = ServiceFC.getStatus(new DeviceUuidFactory(ProblemsService.this).getDeviceUuid().toString());
                            if (
                                    ps == null ||
                                            (
                                                    ps != null && get_ps.getLast_changed_at() != null && ps.getLast_changed_at() != null && get_ps.getLast_changed_at().after(ps.getLast_changed_at())
                                            )
                                    ) {
                                ps = get_ps;
                                if (ps.getLast() != null) {
                                    String str_status = ps.getLast().getStatus();
                                    if (!str_status.equals("cancel") && !str_status.equals("order")) {
                                        messageIntent = new Intent(ProblemsService.this, ActivityProblem.class).putExtra(PROBLEM, ps.getLast());
                                        messagePendingIntent = PendingIntent.getActivity(ProblemsService.this, 0, messageIntent, 0);
                                        //更新通知栏
                                        messageNotification.setLatestEventInfo(
                                                ProblemsService.this,
                                                "柳州电脑上门维修",
                                                "您的故障单状态已更新为" + ps.getLast().getStatusStr() + ",点击查看", messagePendingIntent);
                                        messageNotificatioManager.notify(R.string.app_name, messageNotification);
                                    }
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                        }
                    }
                }
                try {
                    Thread.sleep(Constants.Delay.GET_STATUS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}