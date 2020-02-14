package IE.P1;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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
