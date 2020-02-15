package IE.P1.Handlers;

import IE.P1.models.User;
import io.javalin.http.Context;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static io.javalin.plugin.rendering.template.TemplateUtil.model;

public class UserHandler {
    public static void GetUserInfo(Context context) throws IOException {
        User user = context.cookieStore("user");
        context.render("/user.html", model("user", user,"url","/user"));
    }

    public static void IncreaseWallet(Context context) throws IOException {
        User user = context.cookieStore("user");
        user.AddToWallet(Float.parseFloat(Objects.requireNonNull(context.formParam("credit"))));
        context.cookieStore("user",user);
        context.result("value is:"+ Float.valueOf(Objects.requireNonNull(context.formParam("credit"))));
    }
}
