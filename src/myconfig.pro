-injars       ../build/libs/fuga.jar
-outjars      ../build/proguard/fuga-min.jar
-libraryjars  <java.home>/jmods/java.base.jmod(!**.jar;!module-info.class)
#-printmapping build/proguard/fuga-min.map

-keep public class calculator.Main {
    public static void main(java.lang.String[]);
}