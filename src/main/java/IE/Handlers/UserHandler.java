package IE.Handlers;

import IE.models.User;
import io.javalin.http.Context;

import java.util.Objects;

import static io.javalin.plugin.rendering.template.TemplateUtil.model;

public class UserHandler {
    public static void GetUserInfo(Context context) {
        User user = context.cookieStore("user");
        context.render("/user.html", model("user", user,"url","/user"));
    }

    public static void IncreaseWallet(Context context) {
        User user = context.cookieStore("user");
        user.AddToWallet(Float.parseFloat(Objects.requireNonNull(context.formParam("credit"))));
        context.cookieStore("user",user);
        context.result("value is:"+ Float.valueOf(Objects.requireNonNull(context.formParam("credit"))));
    }
}
