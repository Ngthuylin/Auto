package git.artdeell.autowax;

import git.artdeell.aw4c.CanvasMain;
import local.json.JSONObject;

public class GiftTask implements Runnable{
    public final AutoWax host;
    public final String friendId;
    public final String nickname;
    public final String giftType;
    public final boolean paid;
    public GiftTask(Gift cobj, AutoWax host, boolean paid) {
        this.host = host;
        this.friendId = cobj.targetId;
        this.nickname = cobj.username;
        this.giftType = cobj.giftType;
        this.paid = paid;
    }

    @Override
    public void run() {
        try {
            byte retries = 0;
            JSONObject giftRq = host.genInitial();
            giftRq.put("target", friendId);
            giftRq.put("gift_type", giftType);
            String result = host.doPost(paid ? "/account/send_message" : "/service/relationship/api/v1/free_gifts/send",giftRq).optString("result", "Unknown");
            CanvasMain.submitLogString(nickname + ": " + result);
        }catch(Exception e) {
            e.printStackTrace();
            CanvasMain.submitLogString(nickname + ": " + e.toString());
        }
    }
}
