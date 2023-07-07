package etu1852.annotation;
import java.lang.annotation.*;
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
 public @interface Auth{
    String admin();
}
