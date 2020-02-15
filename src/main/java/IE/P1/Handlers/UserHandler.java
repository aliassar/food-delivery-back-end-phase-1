package IE.P1.Handlers;

import IE.P1.models.User;
import io.javalin.http.Context;

import java.io.IOException;

public class UserHandler {
    public static void GetUserInfo(Context context) throws IOException {
        User user = context.cookieStore("user");
        context.json(user);
    }

    public static void IncreaseWallet(Context context) throws IOException {
        User user = context.cookieStore("user");
        user.AddToWallet(Float.valueOf(context.pathParam("value")));
        context.cookieStore("user",user);
        context.result("value is:"+ Float.valueOf(context.pathParam("value")));
    }
}
