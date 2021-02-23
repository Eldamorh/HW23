import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;


public class Main {

    public static void task2(Object o) throws IllegalAccessException {
        Field[] fields = o.getClass().getDeclaredFields();

        for (Field field : fields) {
            if (!field.isAnnotationPresent(Ignore.class)) {
                StringBuilder stringBuilder = new StringBuilder();
                field.setAccessible(true);
                stringBuilder.append(Modifier.toString(field.getModifiers()));
                stringBuilder.append('|');
                stringBuilder.append(field.getName());
                stringBuilder.append('|');
                stringBuilder.append(field.get(o));
                System.out.println(stringBuilder.toString());
            }

        }
    }

    public static void main(String[] args) throws Exception {
        String s = "hello";

        s = String.class
                .getConstructor(String.class)
                .newInstance("EDCBA");
        System.out.println(s);


        Field field = String.class.getDeclaredField("value");
        field.setAccessible(true);

        // Из за этой строчки и пришлось даунгрейднуться с 13 в 9 , выдавало java.lang.NoSuchFieldException: modifiers, хотя очевидно что такое поле там было)
        Field modifiersField = Field.class.getDeclaredField("modifiers");

        modifiersField.setAccessible(true);
        modifiersField.setInt(field, Modifier.PUBLIC);
        byte[] value = {'A', 'B', 'C', 'D', 'E'};
        field.set(s, value);
        System.out.println(s);

        task2(new Test(new char[]{'A', 'B', 'C', 'D', 'E'}));


    }
}
