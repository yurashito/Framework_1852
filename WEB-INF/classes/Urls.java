package annotation;
import java.lang.annotation.*;
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.Method)
 public @interface Urls{
    String value();
}
